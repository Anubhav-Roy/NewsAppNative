package roy.anubhav.newsapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Usage :-
 *
 *     @Inject
 *     PermissionsHelper permissionsHelper;
 *
 *      public onCreate(){
 *          permissionsHelper.requestDeniedPermissions(this);
 *      }
 *
 *    @Override
 *     public void onRequestPermissionsResult(int requestCode,
 *                                            String[] permissions, int[] grantResults) {
 *          boolean allPermissionsGranted = permissionsHelper.handlePermissions(requestCode,permissions,grantResults);
 *
 *
 *     }
 *
 *
 */

public class PermissionsHelper {

    private static final String TAG = "PermissionsHelper";

    //Singleton instance
    private static PermissionsHelper instance ;

    //List of all required permissions
    //Needs to stated in manifest as well.
    private String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    //List of all the denied permissions
    private HashMap<String,Boolean> permissionsState = new HashMap<>();

    //Permission codes
    private static int REQUEST_PERMISSIONS = 9001;

    public void requestDeniedPermissions(Activity activity){
        ArrayList<String> deniedPermissions = new ArrayList<>();
        permissionsState.clear();

        for(String permission : permissions){
            if (ContextCompat.checkSelfPermission(activity,permission)
                    != PackageManager.PERMISSION_GRANTED){
                //The permission is not granted. We add it to the
                //request list.
                deniedPermissions.add(permission);
                permissionsState.put(permission,false);
            }else
                permissionsState.put(permission,true);      //The permission is already granted
        }

        //Check if there are any denied permissions
        if(deniedPermissions.size()!=0) {
            //Denied permissions as an Array
            String[] deniedPermissionsAsArray = new String[deniedPermissions.size()];

            for (int i = 0; i < deniedPermissions.size(); i++)
                deniedPermissionsAsArray[i] = deniedPermissions.get(i);


            ActivityCompat.requestPermissions(activity,
                    deniedPermissionsAsArray,
                    REQUEST_PERMISSIONS);
        }
    }

    public boolean hasAllPermissions(Activity activity){
        for(String permission : permissions){
            if (ContextCompat.checkSelfPermission(activity,permission)
                    != PackageManager.PERMISSION_GRANTED){
                //The permission is not granted.
                return false;
            }
        }

        return true;
    }

    public boolean handlePermissions(int requestCode,
                                     String[] permissions, int[] grantResults){

        boolean allPermissionsGranted = true;
        for(int i =0 ; i < grantResults.length; i++){
            //Update the permissions state in the hashmap after the user interaction
            permissionsState.put(permissions[i],(grantResults[i] == PackageManager.PERMISSION_GRANTED) );

            if((grantResults[i] != PackageManager.PERMISSION_GRANTED))
                allPermissionsGranted=false;
        }

        return allPermissionsGranted;
    }

}
