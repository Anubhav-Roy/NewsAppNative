package roy.anubhav.newsapp.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * Simple Threader Class
 *
 */
public class Threader {

    private static String TAG = "Threader";
    //Threading variables
    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    int KEEP_ALIVE_TIME = 5;
    TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    //Task queue for all the threads
    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();

    //Executor service for all the threads that are running
    //Setting the corePoolSize to 1 since we only want to process a single download at a time.
    //This is done since downloading multiple files at the same time only hinders the process and
    //will mostly have a higher fail rate in comparison to a single file download at a time.
    private ExecutorService executorService = new ThreadPoolExecutor(1,
            NUMBER_OF_CORES * 2,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            taskQueue,
            new BackgroundThreadFactory());

    //Maintains all the running tasks.
    private static HashMap<String, Future> mRunningTaskList=new HashMap<>();

    /**
     *
     * ThreadFactory to create new threads for each callable that is being added
     * to the ExecutorService.
     *
     */
    private class BackgroundThreadFactory  implements ThreadFactory {
        private int sTag = 1;
        private String TAG = "BackgroundThreadFactory";

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("CustomThread" + sTag + "."+ Math.round(Math.random()*1000));


            // A exception handler is created to log the exception from threads
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.e(TAG, thread.getName() + " encountered an error: " + ex.getMessage());
                }
            });
            return thread;
        }
    }

    /**
     *
     * Adds a task to the ExecutorService, whose progress ,viz finished or not, can be later tracked using
     * the key with which it's registered with in the task list.
     *
     * If the task is already submitted, the @param overrideExistingTask will over write the previous task's result.
     *
     * @param runnable
     * @param key
     * @param overrideExistingTask
     */
    public void submitTask(Runnable runnable,String key, boolean overrideExistingTask){
        //The task has already been submitted and we want to override it , irrespective of whether the task was
        //completed or running
        if(mRunningTaskList.containsKey(key) && overrideExistingTask)
            cancelTask(key);
        else if(mRunningTaskList.containsKey(key) && !overrideExistingTask)
            return;

        Future future = executorService.submit(runnable);
        mRunningTaskList.put(key, future);
    }

    /**
     *
     * Adds a list of tasks to the ExecutorService, whose progress ,viz finished or not, can be later tracked using
     * the key with which it's registered with in the task list.
     *
     * If the task is already submitted, the @param overrideExistingTask will over write the previous task's result.
     *
     * @param runnables
     * @param key
     * @param overrideExistingTask
     */
    public void submitTask(List<Runnable> runnables, String key, boolean overrideExistingTask){

        //The task has already been submitted and we want to override it , irrespective of whether the task was
        //completed or running
        if(mRunningTaskList.containsKey(key) && overrideExistingTask)
            cancelTask(key);
        else
            return;

        for(Runnable runnable: runnables) {
            Future future = executorService.submit(runnable);
            mRunningTaskList.put(key, future);
        }
    }

    /**
     *
     * Checks if a submitted task is completed.
     *
     * @throws IllegalArgumentException when the key requested doesn't exist.
     * @param key The key with which it was registered .
     * @return Boolean If the task has been completed
     */
    public boolean isTaskCompleted(String key){
        //Check if the key has been submitted
        if(!mRunningTaskList.containsKey(key))
            throw new IllegalArgumentException("Key doesn't exist");

        return mRunningTaskList.get(key).isDone();
    }

    /**
     *
     * Cancels a task for a given key.
     *
     *
     * @throws IllegalArgumentException when the key requested doesn't exist.
     * @param key The key with which it was registered
     * @return Boolean if the task was cancelled
     */
    public boolean cancelTask(String key){
        //Check if the key has been submitted
        if (!mRunningTaskList.containsKey(key))
            throw new IllegalArgumentException("Key doesn't exist");

        //Cancelling the task
        mRunningTaskList.get(key).cancel(true);

        //Checking the status if the task was cancelled
        boolean cancelledTask = mRunningTaskList.get(key).isCancelled();

        //If the task was cancelled, then we remove it from the running tasks list
        if(cancelledTask)
            mRunningTaskList.remove(key);

        return cancelledTask;
    }

    /**
     *
     * Cancels all the running tasks
     *
     * @return If all the tasks were successfully cancelled.
     */
    public boolean cancelAllTasks(){
        boolean cancelledAllTasks = false;
        taskQueue.clear();
        for(String key:mRunningTaskList.keySet()){
            if (!mRunningTaskList.get(key).isDone()) {
                mRunningTaskList.get(key).cancel(true);
            }
        }
        mRunningTaskList.clear();
        cancelledAllTasks = true;
        return cancelledAllTasks;
    }

}
