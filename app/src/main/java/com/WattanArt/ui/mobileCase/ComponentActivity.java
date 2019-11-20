package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
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
import com.yalantis.ucrop.view.GestureCropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.iwf.photopicker.PhotoPicker;


public class ComponentActivity extends BaseActivity implements MobileMvpView{

    CustomeTextViewBold pick_from_gallery_tv;
    CustomeTextViewBold complete_tv;
    ImageView toolbar_image_view;
    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;
    ArrayList<String> photos;
    String path;
//    String[] photos;
    File file;
    public static List<ImageModel> imageModelList = new ArrayList<>();
    ProgressDialog progressDialog;

    CoverView cover;
    ImageCaseComponent component;
    DimensionData dimensionData;
    AccessoriesView accessoriesView;

    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    public static String fileNAME;
    public static int framePos = 0;

    private float scale = 0;
    private float newDist = 0;

    // Fields
    private String TAG = this.getClass().getSimpleName();

    // We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    // Remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    UserData userData = new UserData();

    @Inject
    MobilePresenterMvp<MobileMvpView> mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_layout_view);

        photos=new ArrayList<>();
        toolbar_image_view=findViewById(R.id.toolbar_image_view);
        pick_from_gallery_tv=findViewById(R.id.pick_from_gallery_tv);
        complete_tv=findViewById(R.id.order_design_tv);
         component = findViewById(R.id.componentView);

        ActivityComponent activityComponent = getActivityComponent();

        if (activityComponent != null) {
            activityComponent.inject(this);
            mPresenter.onAttach(this);
        }

        dimensionData = new DimensionData(700, 1024, 430,
                1000, 0, 0, 40, 700,
                1024, 0, 0);
        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9,R.drawable.huaweiy9_2));
         cover = (CoverView) component.getComponentView(R.id.cover);
         accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
        accessoriesView.setData(R.drawable.huaweiy9_2);

        pickFromGalleryAction();
        completeClickListener();
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
                if (data != null) {
                    if (imageModelList!=null) {
                        imageModelList.clear();
                        cover.setImageBitmap(null);
                        cover.setZoom(0f);
                    }
                   photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                   data.getIntExtra(PhotoPicker.KEY_SELECTED_PHOTOS,0);
                   Log.e("datata",photos.get(0)+"");


                    getPhoto();
                }
            }
        }
    }

    public void getPhoto(){
        showLoadingcustom(ComponentActivity.this);

        for (int i=0;i<photos.size();i++){
            path=photos.get(0);
        }

            file = new File(path);
            ImageModel imageModel = new ImageModel(Uri.fromFile(file), 0f);
            Glide.with(ComponentActivity.this)
                    .asBitmap()
                    .load(path)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap,
                                                    Transition<? super Bitmap> transition) {
                            int imageWidth = bitmap.getWidth();
                            int imageHeight = bitmap.getHeight();

                            Log.e("ImageAttrShipping", "-->" + path + "       width = " + imageWidth + "       height = " + imageHeight);

                            imageModel.setCurrentScale(1f);
                            imageModel.setCurrentAngle(0f);
                            imageModel.setMainImageHeight(imageHeight);
                            imageModel.setMainImageWidth(imageWidth);
                            imageModel.setImageHeight(imageHeight);
                            imageModel.setImageWidth(imageWidth);
                            imageModel.setQuantity(1);
                            imageModel.setSegmented(false);
                            imageModel.setPath(path);
                            imageModelList.add(imageModel);

                                    try {
                                        int finalPosition =0;

                                        BitmapLoadUtils.decodeBitmapInBackground(ComponentActivity.this,
                                                imageModelList.get(finalPosition).getUri(),
                                                imageModelList.get(finalPosition).getUri(),
                                                BitmapLoadUtils.calculateMaxBitmapSize(ComponentActivity.this)
                                                , BitmapLoadUtils.calculateMaxBitmapSize(ComponentActivity.this),
                                                new BitmapLoadCallback() {

                                                    @Override
                                                    public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo,
                                                                               @NonNull String imageInputPath,
                                                                               @Nullable String imageOutputPath) {

                                                        final double factorHeight = ((double) imageModelList.get(finalPosition).getMainImageHeight()) / ((double) bitmap.getHeight());
                                                        final double factorWidth = ((double) imageModelList.get(finalPosition).getMainImageWidth()) / ((double) bitmap.getWidth());

                                                        ComponentActivity.imageModelList.get(finalPosition).setFactorWidth(factorWidth);
                                                        ComponentActivity.imageModelList.get(finalPosition).setFactorHeight(factorHeight);

                                                        ComponentActivity.imageModelList.get(finalPosition).setBitmap(bitmap);
                                                        ComponentActivity.imageModelList.get(finalPosition).setFilteredBitmap(bitmap);
                                                        ComponentActivity.imageModelList.get(finalPosition).setScreenShotImage(bitmap);
                                                        Log.e("fsfsfsffff",imageModelList.get(0).getScreenShotImage()+"\n");


//                                                        Bitmap bitmap1 = getRoundedCornerBitmap(imageModelList.get(0).getScreenShotImage(),1000);
                                                        cover.setBitmapData(imageModelList.get(0).getScreenShotImage());

//                                                        cover.setImageBitmap(imageModelList.get(0).getScreenShotImage());
                                                        cover.setMaxZoom(12f);

                                                        Log.e("uriiiiii",imageModelList.get(0).getUri()+"\n");

                                                        ComponentActivity.imageModelList.get(finalPosition).setmExifInfo(exifInfo);
                                                        ComponentActivity.imageModelList.get(finalPosition).
                                                                setmCropRect(new RectF(0, 0, imageModelList.get(finalPosition).getMainImageWidth(),
                                                                        imageModelList.get(finalPosition).getMainImageHeight()));
                                                        if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight()
                                                                == ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) {

                                                            ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);

                                                        } else if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight() >
                                                                ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) {

                                                            if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight() >= 1000 &&
                                                                    ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth() >= 1000 &&
                                                                    (((float) ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight()) / 1.5f) >= 1000) {
                                                                ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(8f / 12);
                                                            } else {
                                                                ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
                                                            }
                                                        } else if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight()
                                                                < ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) {
                                                            if (ComponentActivity.imageModelList.get(finalPosition).getMainImageHeight() >= 1000 &&
                                                                    ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth() >= 1000 &&
                                                                    (((float) ComponentActivity.imageModelList.get(finalPosition).getMainImageWidth()) / 1.5f) >= 1000
                                                            ) {
                                                                ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(12f / 8f);
                                                            } else {
                                                                ComponentActivity.imageModelList.get(finalPosition).setCurrentRatio(1f);
                                                            }
                                                        }

                                                        progressDialog.dismiss();
                                                    }


                                                    @Override
                                                    public void onFailure(@NonNull Exception bitmapWorkerException) {
                                                        Log.e("setImageUriSecond", "onFailure: setImageUri", bitmapWorkerException);
                                                    }

                                                    @Override
                                                    public void afterLoadComplete() {

                                                    }
                                                });

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

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

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public void completeClickListener(){
        complete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder();
            }
        });
    }

    @Override
    public void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList) {

    }

    @Override
    public void afterLogin() {
        makeOrder();
    }

    @Override
    public void baackPress(int orderId) {

    }

    @Override
    public void onDeleteAllImages() {

    }

    @Override
    public void onAddNewImages() {

    }

    @Override
    public void logOrderToAnalytics(ShippingRequest shippingRequest) {

    }

    @Override
    public void showLoading() {
        showLoadingcustom(ComponentActivity.this);
    }

    @Override
    public void showLoadingFragment() {

    }

    @Override
    public void hideLoadingFragment() {

    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    public void makeOrder(){
        userData = new UserData();
        if (userData.getUserID(this) == null || userData.getUserID(this).isEmpty()) {
            Toast.makeText(this, getString(R.string.login_first), Toast.LENGTH_LONG).show();
            mPresenter.showLoginPopup(this, imageModelList,
                    1, 2, 1);
        } else {
            mPresenter.checkImageHasLowResolution(ComponentActivity.this, imageModelList
                    , 1, 2, 1);
        }
    }
}
