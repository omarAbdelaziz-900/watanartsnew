package com.WattanArt;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.Shipping.ShippingActivity;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class Main2Activity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        img = findViewById(R.id.img);
        img.setScaleType(ImageView.ScaleType.MATRIX);


        PhotoPicker.builder()
                .setGridColumnCount(3)
                .setPreviewEnabled(false)
                .setShowCamera(false)
                .start(this);


        float scale = 1.3548f;
        float centerX = 200f;
        float centerY = 300f;

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = img.getImageMatrix();
                matrix.postScale((float) scale, (float) scale, centerX, centerY);


                float[] array  = new float[9];
                matrix.getValues(array);
                array[Matrix.MTRANS_X] = centerX;
                array[Matrix.MTRANS_Y] = centerY;
                matrix.setValues(array);
                img.setImageMatrix(matrix);
                img.invalidate();


////Get the image's rect
//                RectF drawableRect = new RectF(0, 0, img.getWidth(),
//                        img.getHeight());
////Get the image view's rect
//                RectF viewRect = new RectF(0, 0, img.getWidth(),
//                        img.getHeight());
////draw the image in the view
//                m.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
////set scale
//                m.postScale(scale, scale, centerX, centerY);
//                img.setImageMatrix(m);
//                img.invalidate();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    img.setImageBitmap(BitmapFactory.decodeFile(photos.get(0)));

                }
            }
        }
    }
}
