package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import me.iwf.photopicker.PhotoPicker;


public class ComponentActivity extends BaseActivity {

    CustomeTextViewBold pick_from_gallery_tv, rotate_tv;
    CustomeTextViewBold complete_tv;
    ImageView toolbar_image_view;
    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;

    //    CoverView cover;
    CoverView cover;
    ImageCaseComponent component;
    ImageView view_image;
    DimensionData dimensionData;
    AccessoriesView accessoriesView;
    // Fields
    private String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_layout_view);
        setUpActivityOrFragmentRequirment();

    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {
        ActivityComponent activityComponent = getActivityComponent();

        if (activityComponent != null) {
            activityComponent.inject(this);
        }

        initView();
        pickFromGalleryAction();

    }

    void initView() {

        toolbar_image_view = findViewById(R.id.toolbar_image_view);
        pick_from_gallery_tv = findViewById(R.id.pick_from_gallery_tv);
        complete_tv = findViewById(R.id.order_design_tv);
        component = findViewById(R.id.componentView);
        view_image = findViewById(R.id.view_image);
        rotate_tv = findViewById(R.id.rotate_tv);


        dimensionData = new DimensionData(600, 1420, 590,
                1410, 40, 700,
                1420, 0, 0);
        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9, R.drawable.huaweiy9_2));
        cover = (CoverView) component.getComponentView(R.id.cover);
        accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
        accessoriesView.setData(R.drawable.huaweiy9_2);

    }

    public void pickFromGalleryAction() {
        pick_from_gallery_tv.setOnClickListener(v -> pickFromGallery());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {

        if (ActivityCompat.checkSelfPermission(ComponentActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(ComponentActivity.this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromGallery();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos == null || photos.isEmpty()) return;
                cover.setData(photos.get(0));

            }
        }
    }


    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }


    public void makeOrder(View view) {
        Bitmap bitmap = ((BitmapDrawable)cover.getDrawable()).getBitmap();
        Bitmap currentBitmap = Bitmap.createBitmap(bitmap,
                0, 0, bitmap.getWidth(),
                bitmap.getHeight(), cover.getImageMatrix(), true);
        Bitmap mobileBitmap = loadBitmapFromView(component);
        float rorationAngle = cover.getCurrentAngle();
        float zoom = cover.getCurrentScale();

        Log.i(TAG, String.format("Size (%d , %d) allMobile Size (%d , %d) current Rottion Angle %f and Zoom %f", currentBitmap.getWidth(), currentBitmap.getHeight()
                , mobileBitmap.getWidth(), mobileBitmap.getHeight(), rorationAngle, zoom));

    }
}
