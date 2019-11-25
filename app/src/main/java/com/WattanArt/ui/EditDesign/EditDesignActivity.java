package com.WattanArt.ui.EditDesign;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.ui.base.BaseActivity;
import com.WattanArt.ui.mobileCase.ComponentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

public class EditDesignActivity extends BaseActivity implements T_ShirtAdapter.ItemListener ,T_ShirtColorAdapter.ItemListener{

    @BindView(R.id.recycler_view_shirt)
    RecyclerView recycler_view_shirt;

    @BindView(R.id.recycler_view_color)
    RecyclerView recycler_view_color;

    @BindView(R.id.pick_from_gallery_tv)
    CustomeTextViewBold pick_from_gallery_tv;

    @BindView(R.id.t_shirt_img)
    ImageView t_shirt_img;

    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;

    T_ShirtAdapter t_shirtAdapter;
    ArrayList<T_ShirtModel> t_shirtModels;

    T_ShirtColorAdapter t_shirtColorAdapter;
    ArrayList<T_ShirtModel> t_shirtColorModels;

    ArrayList<String> photos;
    String path;
    //    String[] photos;
    File file;
//    public static List<ImageModel> imageModelList = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_design);

        ButterKnife.bind(this);
        initRecyclerView();
        initColorRecyclerView();

        pickFromGalleryAction();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    private void initRecyclerView() {
        t_shirtModels =new ArrayList<>();
        t_shirtModels.add(new T_ShirtModel("S"));
        t_shirtModels.add(new T_ShirtModel("M"));
        t_shirtModels.add(new T_ShirtModel("L"));
        t_shirtModels.add(new T_ShirtModel("XL"));
        t_shirtModels.add(new T_ShirtModel("XXl"));
        t_shirtModels.add(new T_ShirtModel("XXXL"));


        LinearLayoutManager manager = new LinearLayoutManager(EditDesignActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_shirt.setLayoutManager(manager);
        recycler_view_shirt.setItemAnimator(new DefaultItemAnimator());
        recycler_view_shirt.setNestedScrollingEnabled(false);
        recycler_view_shirt.setHasFixedSize(true);
        recycler_view_shirt.scrollToPosition(0);
        t_shirtAdapter = new T_ShirtAdapter(EditDesignActivity.this, t_shirtModels,this);
        recycler_view_shirt.setAdapter(t_shirtAdapter);
    }

    private void initColorRecyclerView() {
        t_shirtColorModels =new ArrayList<>();
//        t_shirtColorModels.add(new T_ShirtModel(R.color.yellow));
//        t_shirtColorModels.add(new T_ShirtModel(R.color.black));
//        t_shirtColorModels.add(new T_ShirtModel(R.color.white));
//        t_shirtColorModels.add(new T_ShirtModel(R.color.red_color_picker));
//        t_shirtColorModels.add(new T_ShirtModel(R.color.green_color_picker));
//        t_shirtColorModels.add(new T_ShirtModel(R.color.blue));

        int[] colors = getResources().getIntArray(R.array.colors);

        for (int i = 0; i < colors.length; i++) {
            t_shirtColorModels.add(new T_ShirtModel(colors[i]));
        }

        LinearLayoutManager manager = new LinearLayoutManager(EditDesignActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_color.setLayoutManager(manager);
        recycler_view_color.setItemAnimator(new DefaultItemAnimator());
        recycler_view_color.setNestedScrollingEnabled(false);
        recycler_view_color.setHasFixedSize(true);
        recycler_view_color.scrollToPosition(0);
        t_shirtColorAdapter = new T_ShirtColorAdapter(EditDesignActivity.this, t_shirtColorModels,this);
        recycler_view_color.setAdapter(t_shirtColorAdapter);
    }

    @Override
    public void onSizeTypeClickFromAdapter(int position) {
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onColorTypeClickFromAdapter(int position) {
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
    }

    public void pickFromGalleryAction(){
        pick_from_gallery_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {

        if (ActivityCompat.checkSelfPermission(EditDesignActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(EditDesignActivity.this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                Uri uri = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    // Log.d(TAG, String.valueOf(bitmap));
//                    t_shirt_img.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }


    public void getPhoto(){
        showLoadingcustom(EditDesignActivity.this);

        for (int i=0;i<photos.size();i++){
            path=photos.get(0);
        }

        file = new File(path);
//        ImageModel imageModel = new ImageModel(Uri.fromFile(file), 0f);
        Glide.with(EditDesignActivity.this)
                .asBitmap()
                .load(path)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
//                        int imageWidth = bitmap.getWidth();
//                        int imageHeight = bitmap.getHeight();
//
//                        Log.e("ImageAttrShipping", "-->" + path + "       width = " + imageWidth + "       height = " + imageHeight);
//
//                        imageModel.setCurrentScale(1f);
//                        imageModel.setCurrentAngle(0f);
//                        imageModel.setMainImageHeight(imageHeight);
//                        imageModel.setMainImageWidth(imageWidth);
//                        imageModel.setImageHeight(imageHeight);
//                        imageModel.setImageWidth(imageWidth);
//                        imageModel.setQuantity(1);
//                        imageModel.setSegmented(false);
//                        imageModel.setPath(path);
//                        imageModelList.add(imageModel);
//
//                        try {
//                            int finalPosition =0;
//
//                            BitmapLoadUtils.decodeBitmapInBackground(EditDesignActivity.this,
//                                    imageModelList.get(finalPosition).getUri(),
//                                    imageModelList.get(finalPosition).getUri(),
//                                    BitmapLoadUtils.calculateMaxBitmapSize(EditDesignActivity.this)
//                                    , BitmapLoadUtils.calculateMaxBitmapSize(EditDesignActivity.this),
//                                    new BitmapLoadCallback() {
//
//                                        @Override
//                                        public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo,
//                                                                   @NonNull String imageInputPath,
//                                                                   @Nullable String imageOutputPath) {
//
//                                            final double factorHeight = ((double) imageModelList.get(finalPosition).getMainImageHeight()) / ((double) bitmap.getHeight());
//                                            final double factorWidth = ((double) imageModelList.get(finalPosition).getMainImageWidth()) / ((double) bitmap.getWidth());
//
//                                            ComponentActivity.imageModelList.get(finalPosition).setFactorWidth(factorWidth);
//                                            ComponentActivity.imageModelList.get(finalPosition).setFactorHeight(factorHeight);
//
//                                            ComponentActivity.imageModelList.get(finalPosition).setBitmap(bitmap);
//                                            ComponentActivity.imageModelList.get(finalPosition).setFilteredBitmap(bitmap);
//                                            ComponentActivity.imageModelList.get(finalPosition).setScreenShotImage(bitmap);
//                                            Log.e("fsfsfsffff",imageModelList.get(0).getScreenShotImage()+"\n");
//
//
////                                                        Bitmap bitmap1 = getRoundedCornerBitmap(imageModelList.get(0).getScreenShotImage(),1000);
//                                            cover.setBitmapData(imageModelList.get(0).getScreenShotImage());
//
////                                                        cover.setImageBitmap(imageModelList.get(0).getScreenShotImage());
//                                            cover.setMaxZoom(12f);
//
//                                            Log.e("uriiiiii",imageModelList.get(0).getUri()+"\n");
//
//                                            ComponentActivity.imageModelList.get(finalPosition).setmExifInfo(exifInfo);
//                                            ComponentActivity.imageModelList.get(finalPosition).
//                                                    setmCropRect(new RectF(0, 0, imageModelList.get(finalPosition).getMainImageWidth(),
//                                                            imageModelList.get(finalPosition).getMainImageHeight()));
//                                            if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight()
//                                                    == ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) {
//
//                                                ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
//
//                                            } else if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight() >
//                                                    ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) {
//
//                                                if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight() >= 1000 &&
//                                                        ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth() >= 1000 &&
//                                                        (((float) ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight()) / 1.5f) >= 1000) {
//                                                    ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(8f / 12);
//                                                } else {
//                                                    ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
//                                                }
//                                            } else if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight()
//                                                    < ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) {
//                                                if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight() >= 1000 &&
//                                                        ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth() >= 1000 &&
//                                                        (((float) ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) / 1.5f) >= 1000
//                                                ) {
//                                                    ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(12f / 8f);
//                                                } else {
//                                                    ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
//                                                }
//                                            }
//
//                                            progressDialog.dismiss();
//                                        }
//
//
//                                        @Override
//                                        public void onFailure(@NonNull Exception bitmapWorkerException) {
//                                            Log.e("setImageUriSecond", "onFailure: setImageUri", bitmapWorkerException);
//                                        }
//
//                                        @Override
//                                        public void afterLoadComplete() {
//
//                                        }
//                                    });
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                    }
                });
    }

    ProgressDialog showLoadingcustom(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.layout_dialog_loading);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
