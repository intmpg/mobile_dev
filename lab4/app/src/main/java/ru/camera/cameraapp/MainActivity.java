package ru.camera.cameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * a simple app to test a phone camera
 */

public class MainActivity extends ActionBarActivity {

    private static final String KEY_BITMAP = "bitmap";
    private static final int REQUEST_PHOTO = 1;

    private File mPhotoFile;
    private Bitmap mBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage();
            }
        });

        if (savedInstanceState != null){
            mBitmap = savedInstanceState.getParcelable(KEY_BITMAP);
            mImageView.setImageBitmap(mBitmap);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_BITMAP, mBitmap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK) {
             if (requestCode == REQUEST_PHOTO) {
                if (mCurrentPhotoPath != null) {
                    bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                    Toast.makeText(this, R.string.take_photo, Toast.LENGTH_SHORT).show();
                }
            }

            mBitmap = bitmap;
            mImageView.setImageBitmap(bitmap);
        }

    }


    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // создание файла
            if (mPhotoFile == null) {
                try {
                    mPhotoFile = createImageFile();
                    Toast.makeText(this, mPhotoFile.getName().toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(this, R.string.file_error, Toast.LENGTH_SHORT).show();
                }
            }

            // если файл создался
            if (mPhotoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                startActivityForResult(takePictureIntent, REQUEST_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // имя файла
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // путь
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void rotateImage() {
        if (mBitmap == null){
            return;
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        mImageView.setImageBitmap(mBitmap);
    }


}
