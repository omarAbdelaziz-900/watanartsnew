package com.WattanArt;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.WattanArt.model.AppModels.ImageModel;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import me.iwf.photopicker.PhotoPicker;

public class TestActivity extends AppCompatActivity {


    UCropView uCropView1, uCropView2;
    Button btn;

    int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 112;
    ImageModel imageModel;
    File file;
    BitmapFactory.Options options = new BitmapFactory.Options();
    Matrix savedMatrix = new Matrix();
    Matrix initialTranslation;

    RectF initialImageRect = new RectF();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        options.inJustDecodeBounds = true;

        uCropView1 = findViewById(R.id.crop1);
        uCropView2 = findViewById(R.id.crop2);
        btn = findViewById(R.id.btn);

        pickFromGallery();


        btn.setOnClickListener(v -> {

            Bitmap original = uCropView1.getCropImageView().getViewBitmap();
            Log.e("1111111", "----"+ uCropView1.getCropImageView().getmCurrentImageMatrix());
            Matrix matrix = new Matrix(uCropView1.getCropImageView().getmCurrentImageMatrix());
            Log.e("2222222", "----"+ matrix);


            Bitmap result = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, false);
//            Bitmap result = Bitmap.createBitmap(uCropView1.getCropImageView().getWidth(), uCropView1.getCropImageView().getHeight(), Bitmap.Config.RGB_565);
//            Canvas c = new Canvas(result);
            uCropView2.getCropImageView().setImageBitmap(result);

//            Bitmap bitmap = uCropView1.getDrawingCache();
//            uCropView2.getCropImageView().setImageBitmap(bitmap);
//            resetImageMatrix();
//            Matrix m = uCropView1.getCropImageView().getmCurrentImageMatrix();
//            RectF drawableRect =uCropView1.getCropImageView().getMCropRect();
//            RectF viewRect = uCropView2.getCropImageView().getMCropRect();
//            m.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
//            uCropView2.getCropImageView().
//                    zoomImageToPositionMine(m,
//                            uCropView1.getCropImageView().getCurrentScale(),
//                            uCropView2.getCropImageView().getMCropRect().centerX(),
//                            uCropView2.getCropImageView().getMCropRect().centerY());
        });
    }


    private void setImageTransformation(float tx, float ty, float scale) {
        savedMatrix.postTranslate(-300f / 2f, -300f / 2f);
        savedMatrix.postScale(scale, scale);
        savedMatrix.postTranslate(tx, ty);
        uCropView2.getCropImageView().setImageMatrix(savedMatrix, false);
    }

    public void resetImageMatrix() {

        float imageWidth = 300f;
        float imageHeight = 300f;

        float displayWidth = 150f;
        float displayHeight = 150f;

        float scaleX = 0.5f;
        float scaleY = 0.5f;


        initialImageRect.set(0, 0, imageWidth, imageHeight);

        setImageTransformation(Math.max(0,
                scaleX * imageWidth / 2f
                        + 0.5f * (displayWidth - (imageWidth))), Math.max(0,
                imageHeight / 2f
                        + 0.5f * (displayHeight - (imageHeight))),
                0.5f);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> photos = null;
        String path = "";
        if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
        }
        path = photos.get(0);
        file = new File(photos.get(0));
        imageModel = new ImageModel(Uri.fromFile(file), 0f);
        BitmapFactory.decodeFile(path, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        Log.e("imageHeight", "" + imageHeight + "  " + imageWidth);
        imageModel.setCurrentScale(1f);
        imageModel.setCurrentAngle(0f);
//            imageModel.setmCropRect(new RectF(0, 0, imageWidth, imageHeight));
        imageModel.setMainImageHeight(imageHeight);
        imageModel.setMainImageWidth(imageWidth);
        imageModel.setImageHeight(imageHeight);
        imageModel.setImageWidth(imageWidth);
        imageModel.setQuantity(1);
        imageModel.setPath(path);

        setdata(uCropView1);
        setdata(uCropView2);

    }


    private void setdata(UCropView ucropview) {
        try {
            ucropview.getCropImageView().setImageUriSecond(
                    imageModel.getUri(), imageModel.getUri(),
                    1000,
                    1000,
                    (bitmap, factorW, factorH) -> {

                        imageModel.setFactorWidth(factorW);
                        imageModel.setFactorHeight(factorH);

                        imageModel.setBitmap(bitmap);
                        imageModel.setFilteredBitmap(bitmap);
                        ucropview.getCropImageView().setImageBitmap(bitmap);
                        ucropview.getCropImageView().setDrawingCacheEnabled(true);



                        if (imageModel.getmCurrentImageCorners() == null)
                            imageModel.setmCurrentImageCorners(ucropview.getCropImageView().getMCurrentImageCorners());

                        imageModel.setmImageInputPath(ucropview.getCropImageView().getImageInputPath());
                        imageModel.setmImageOutputPath(ucropview.getCropImageView().getImageOutputPath());

                        ucropview.getCropImageView().setTargetAspectRatio(8f / 12f);
//                        imageModel.setmCropRect(ucropview.getCropImageView().getMCropRect());
//                        if (/*imageModel.getPositionX() > 0 && imageModel.getPositionY() > 0
//                            ||*/
//                                imageModel.getMatrix() != null) {

                        if (imageModel.getPositionX() < 0) {
                            imageModel.setPositionX(0);
                        }
                        if (imageModel.getPositionY() < 0) {
                            imageModel.setPositionY(0);
                        }


                        TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
                            @Override
                            public void onRotate(float currentAngle) {
                            }

                            @Override
                            public void newRotateListner(float currentAngle) {

                            }


                            @Override
                            public void onScale(float currentScale) {
                            }


                            @Override
                            public void onDrawNewScales() {
                            }


                            @Override
                            public void onLoadComplete() {

                                Log.e("mainImageModel.Width", "" + imageModel.getImageWidth());
                                Log.e("mainImageModel.ImgHei", "" + imageModel.getImageHeight());
                                Log.e("mainImageMo.PositionX", "" + imageModel.getPositionX());
                                Log.e("mainImageMo.PositionY", "" + imageModel.getPositionY());

                                if (imageModel.getMatrix() != null) {


                                    Matrix m = new Matrix(imageModel.getMatrix());
                                    RectF drawableRect = new RectF(0, 0, imageModel.getViewWidth()
                                            , imageModel.getViewHeight());
                                    RectF viewRect = new RectF(0, 0, ucropview.getCropImageView().getWidth()
                                            , ucropview.getCropImageView().getHeight());
                                    m.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
//                                  ucropview.getCropImageView().setImageMatrix(m , false);
                                    ucropview.getCropImageView().
                                            zoomImageToPositionMine(m,
                                                    0, //change this scale
                                                    ucropview.getCropImageView().getMCropRect().centerX(),
                                                    ucropview.getCropImageView().getMCropRect().centerY());

//                                    int width = imageModel.getViewWidth();
//                                    int height = imageModel.getViewHeight();
//
//                                    Log.e("Old" , " " + width+"       "+height);
//
//                                    int newWidth = ucropview.getCropImageView().getWidth();
//                                    int newHeight = ucropview.getCropImageView().getHeight();
//                                    Log.e("New" , ""+ newWidth+"      "+newHeight);
//
//
//                                    // calculate the scale - in this case = 0.4f
//                                    float scaleWidth = ((float) newWidth) / width;
//                                    float scaleHeight = ((float) newHeight) / height;
//
//                                    // createa matrix for the manipulation
//                                    Matrix matrix = new Matrix(imageModel.getMatrix());
//                                    // resize the bit map
//                                    matrix.postScale(scaleWidth, scaleHeight);
//                                    ucropview.getCropImageView().setImageMatrix(matrix);
//
//                                    // rotate the Bitmap
////                                    matrix.postRotate(imageModel.getCurrentRotate());
//                                    // recreate the new Bitmap
////                                    Bitmap resizedBitmap = Bitmap.createBitmap(imageModel.getFilteredBitmap(),
////                                            0,
////                                            0,
////                                            newWidth, newHeight, matrix, true);
////
////                                    ucropview.getCropImageView().setImageBitmap(resizedBitmap);

//                                    ucropview.getCropImageView().
//                                            zoomImageToPositionMine(m,
//                                                    imageModel.getCurrentScale(),
//                                                    ucropview.getCropImageView().getMCropRect().centerX(),
//                                                    ucropview.getCropImageView().getMCropRect().centerY());

                                } else {
                                    ucropview.getCropImageView().
                                            setImageBitmap(imageModel.getFilteredBitmap());
                                }


                            }


                            @Override
                            public void onLoadFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        };
                        ucropview.getCropImageView().setTransformImageListener(mImageListener);

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
