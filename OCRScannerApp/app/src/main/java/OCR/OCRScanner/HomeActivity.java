package OCR.OCRScanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mihinduranasinhe.OCR.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;


import OCR.util.FileCreateUtil;
import OCR.util.HttpClient;
import OCR.util.Permission;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity {

    private Context context;
    private Uri photoURI1;

    private static final String errorFileCreate = "Error file create!";

    private static final int CaptureImageRequest = 1;
    private static final int uploadImageRequest = 2;

 //   private static final String backendurl = "http://192.168.8.100:4000/api/image";
    private static final String backendurl = "https://ocr-backend-mihindu.herokuapp.com/api/image";

    private ProgressDialog progressDialog;


    @BindView(R.id.ocr_image)
    ImageView firstImage;

    @BindView(R.id.ocr_text)
    TextView ocrText;

    boolean flagPermissions = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;


        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (!flagPermissions) {
            flagPermissions = Permission.checkPermissions(this);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Waiting for server response!");
        progressDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {//CaptureImageRequest
                if (resultCode == RESULT_OK) {
                    CropImage.activity(photoURI1).start(this);
                }
            }
            break;

            case 2: {//UploadImageRequest
                if (resultCode == RESULT_OK) {

                    if (resultCode == Activity.RESULT_OK) {
                        Uri selectedImage = data.getData();
                        photoURI1 = selectedImage;
                        CropImage.activity(photoURI1).start(this);
                    }
                }
            }
            break;

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                photoURI1 = CropImage.getActivityResult(data).getUri();

                HttpClient.DoOCR(progressDialog, this, photoURI1, backendurl);
            }
        }
    }


    @OnClick(R.id.scan_button)
    void onClickScanButton() {
        // check permissions
        if (!flagPermissions) {
            flagPermissions = Permission.checkPermissions(this);
            return;
        }
        //prepare intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = FileCreateUtil.createImageFile(this);
            } catch (IOException ex) {
                Toast.makeText(context, errorFileCreate, Toast.LENGTH_SHORT).show();
                Log.i("File error", ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI1 = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI1);
                startActivityForResult(takePictureIntent, CaptureImageRequest);
            }
        }
    }


    @OnClick(R.id.upload_button)
    void onClickSUploadButton() {

        Log.d("upload", "Try to show photo selector");
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, uploadImageRequest);
    }
}
