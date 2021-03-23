package roy.anubhav.newsapp.utils;

import android.util.Log;

public class Logger {

    public enum LEVEL {
        DEBUG,VERBOSE,PRODUCTION
    }

    private final LEVEL level;

    public Logger(Logger.LEVEL level){
        this.level = level;
    }


    public void log(Class className,String... args){

        switch (level){
            case DEBUG:
                Log.d(className.getSimpleName(), String.valueOf(args));
                break;
            case VERBOSE:
                Log.v(className.getSimpleName(), String.valueOf(args));
                break;
            case PRODUCTION:
                break;
        }
    }
}
