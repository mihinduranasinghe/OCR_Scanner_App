package OCR.util;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permission {

    public static Context context;
    static int PERMISSION_ALL = 1;
    static boolean flagPermissions = false;

    static String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };


    public static boolean checkPermissions(Activity activity) {
        if (!hasPermissions(context, PERMISSIONS)) {
            activity.requestPermissions(PERMISSIONS,
                    PERMISSION_ALL);
            flagPermissions = false;
        }
        flagPermissions = true;
        return flagPermissions;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }




}
