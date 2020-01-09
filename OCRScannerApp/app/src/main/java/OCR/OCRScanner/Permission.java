package OCR.OCRScanner;


import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {

    public Context context;
    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };


//    void checkPermissions() {
//        if (!hasPermissions(context, PERMISSIONS)) {
//            requestPermissions(PERMISSIONS,
//                    PERMISSION_ALL);
//            flagPermissions = false;
//        }
//        flagPermissions = true;
//
//    }

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
