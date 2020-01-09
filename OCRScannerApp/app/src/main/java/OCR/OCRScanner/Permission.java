//package OCR.OCRScanner;
//
//
//
//public class Permission {
//
//
//    void checkPermissions() {
//        if (!hasPermissions(context, PERMISSIONS)) {
//            requestPermissions(PERMISSIONS,
//                    PERMISSION_ALL);
//            flagPermissions = false;
//        }
//        flagPermissions = true;
//
//    }
//
//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//
//
//
//}
