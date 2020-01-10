package OCR.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mihinduranasinhe.OCR.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class HttpClient {
    public static void DoOCR(ProgressDialog progressDialog, Activity activity, Uri photoURI1, String backendurl){
        progressDialog.show();
        File file;
        file = new File(PathGenerator.getPath(activity, photoURI1));
        ImageView imageView = activity.findViewById(R.id.ocr_image);

        imageView.setImageURI(photoURI1);

        final SyncHttpClient client = new SyncHttpClient();
        final RequestParams params = new RequestParams();

        client.setConnectTimeout(60000);
        client.setResponseTimeout(120000);

        try {
            params.put("user_id", "mobile_user");
            params.put("image", file, PathGenerator.getContentType(PathGenerator.getPath(activity, photoURI1)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                client.post(backendurl, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        final JSONObject json;
                        try {
                            json = new JSONObject(responseString);
                            activity.runOnUiThread(() -> {
                                TextView textView = activity.findViewById(R.id.ocr_text);
                                try {
                                    textView.setText(json.get("text").toString());
                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Looper.loop();
            }
        }.start();
    }
}
