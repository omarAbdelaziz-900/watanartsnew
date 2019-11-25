package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
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

import javax.inject.Inject;

import me.iwf.photopicker.PhotoPicker;


public class ComponentActivity extends BaseActivity implements MobileMvpView{

    CustomeTextViewBold pick_from_gallery_tv ,rotate_tv;
    CustomeTextViewBold complete_tv;
    ImageView toolbar_image_view;
    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;
    ArrayList<String> photos;
    String path;
//    String[] photos;
    File file;
    public static List<ImageModel> imageModelList = new ArrayList<>();
    ProgressDialog progressDialog;
//    CoverView cover;
    CoverView cover;
    ImageCaseComponent component;
    ImageView view_image;
    DimensionData dimensionData;
    AccessoriesView accessoriesView;
    // Fields
    private String TAG = this.getClass().getSimpleName();

    UserData userData = new UserData();
    double centreX ,centreY ,a;

    int actualWidth, actualHeight;
    float scaleX,scaleY;

    @Inject
    MobilePresenterMvp<MobileMvpView> mPresenter;


    float scalediff;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;

    private int rotation;
    Bitmap scaledBitmap;
    Bitmap imageBitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_layout_view);
        if (savedInstanceState != null) {
            rotation = savedInstanceState.getInt("ANGLE");
        }
        ActivityComponent activityComponent = getActivityComponent();

        if (activityComponent != null) {
            activityComponent.inject(this);
            mPresenter.onAttach(this);
        }

        initView();
        pickFromGalleryAction();
        completeClickListener();
        measureImageDimensions();
        rotateImageClick();
//        moveImage();

    }

    void initView(){

        photos=new ArrayList<>();
        toolbar_image_view=findViewById(R.id.toolbar_image_view);
        pick_from_gallery_tv=findViewById(R.id.pick_from_gallery_tv);
        complete_tv=findViewById(R.id.order_design_tv);
        component = findViewById(R.id.componentView);
        view_image = findViewById(R.id.view_image);
        rotate_tv = findViewById(R.id.rotate_tv);

        actualWidth =430;
        actualHeight =1000;

        dimensionData = new DimensionData(700, 1024, 430,
                1000, 0, 0, 40, 700,
                1024, 0, 0);
        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9,R.drawable.huaweiy9_2));
        cover = (CoverView) component.getComponentView(R.id.cover);
        accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
        accessoriesView.setData(R.drawable.huaweiy9_2);

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

//                   getBitmab(data);
                    getPhoto();
                }
            }
        }
    }

    void getBitmab(Intent data) {
        if (data != null) {
            ArrayList<String> photos =
                    data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

            File imgFile = new File(photos.get(0));
            if (imgFile.exists()) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imgFile), options);
                if (bitmap != null) {
                    Log.e("mn,mn","jjjkh");

                    cover.setImageBitmap(bitmap);
//                    if (intentCode == REQUEST_GALLERY_ID_PHOTO) {
//                        healthRequestsForm1FragmentPresenter.uploadImage(imgFile, IdIntent);
//
//                    } else if (intentCode == REQUEST_GALLERY_PASSPORT_PHOTO) {
//                        healthRequestsForm1FragmentPresenter.uploadImage(imgFile, passportIntent);
//                    }
                }
            }
        }

    }

    public void getPhoto(){
        showLoadingcustom(ComponentActivity.this);

        for (int i=0;i<photos.size();i++){
            path=photos.get(0);
        }
        Log.e("photosSizing",photos.size()+"");

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

                                                        Log.e("bitmapp",bitmap+"");
                                                        Log.e("bitmapp",ComponentActivity.imageModelList.get(finalPosition).getFilteredBitmap()+"");

                                                        ComponentActivity.imageModelList.get(finalPosition).setScreenShotImage(bitmap);

//                                                        ComponentActivity.imageModelList.get(finalPosition).setmCurrentImageCorners();

                                                        Matrix m = cover.getImageMatrix();
                                                        RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
                                                        RectF viewRect = new RectF(0, 0, actualWidth, actualHeight);
                                                        m.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);

                                                        ComponentActivity.imageModelList.get(finalPosition).setMatrix(m);

                                                        Log.e("fsfsfsffff",imageModelList.get(0).getScreenShotImage()+"\n");


//                                                        Bitmap bitmap1 = getRoundedCornerBitmap(imageModelList.get(0).getScreenShotImage(),1000);
                                                        cover.setBitmapData(imageModelList.get(0).getScreenShotImage());
                                                        if (imageModelList.get(0).getScreenShotImage()!=null) {
                                                            imageBitmap = imageModelList.get(0).getScreenShotImage();
                                                            scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, actualWidth, actualHeight, true);
//                                                            cover.setImageBitmap(getRotatedBitmap(scaledBitmap));
                                                        }
//
//                                                        cover.setMinZoom(2f);
                                                        cover.setMaxZoom(12f);
//
                                                        Log.e("uriiiiii",imageModelList.get(0).getUri()+"\n");

                                                        ComponentActivity.imageModelList.get(finalPosition).setmExifInfo(exifInfo);
                                                        ComponentActivity.imageModelList.get(finalPosition).
                                                                setmCropRect(new RectF(0, 0, imageModelList.get(finalPosition).getMainImageWidth(),
                                                                        imageModelList.get(finalPosition).getMainImageHeight()));

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

    public void completeClickListener(){
        complete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOrder();
//              Bitmap bitmap=  takeScreenShot(component);
//                component.setVisibility(View.GONE);
//                view_image.setImageBitmap(bitmap);
//                view_image.setVisibility(View.VISIBLE);
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
        imageModelList.clear();
        getPhoto();
        userData = new UserData();
        if (userData.getUserID(this) == null || userData.getUserID(this).isEmpty()) {
            Toast.makeText(this, getString(R.string.login_first), Toast.LENGTH_LONG).show();
            mPresenter.showLoginPopup(this, imageModelList,
                    1, 2, 1);
        } else {
            for (int i=0;i<imageModelList.size();i++)
            Log.e("okokkoko",imageModelList.get(i).getFilteredBitmap()+"");
            mPresenter.checkImageHasLowResolution(ComponentActivity.this, imageModelList
                    , 1, 2, 1);
        }
    }


    public Bitmap takeScreenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        Bitmap newBitmap=Bitmap.createBitmap(bitmap,10,20,70,80);
        Log.e("bitmapjddd",bitmap+"");
        return newBitmap;
    }

    public void measureImageDimensions(){
        cover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float[] f = new float[9];
                cover.getImageMatrix().getValues(f);

                // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)

                  scaleX = f[Matrix.MSCALE_X];
                  scaleY = f[Matrix.MSCALE_Y];

                final int coverWidth = cover.getMeasuredWidth();
                final int coverHeight = cover.getMeasuredHeight();

                // Calculate the actual dimensions
                actualWidth = Math.round(coverWidth * scaleX);
                 actualHeight = Math.round(coverHeight * scaleY);

                Log.e("DBG", "["+coverWidth+","+coverHeight+"] -> ["+ actualWidth +","+ actualHeight +"] & scales: x="+scaleX+" y="+scaleY);

                float wScale = (float) actualWidth /(float)coverWidth;
                float hScale = (float) actualHeight /(float)coverHeight;
                float scale = Math.min(wScale, hScale);

                Log.e("calculateScaleImage",""+scale);


                float aspect_ratio = (float)coverWidth / (float)coverHeight ;
                float aspect_ratio2 = (float) actualWidth / (float) actualHeight;

                Log.e("calculateAspectRatio",""+aspect_ratio+" && "+aspect_ratio2);


                centreX=cover.getX() + cover.getWidth()  / 2;
                centreY=cover.getY() + cover.getHeight() / 2;

                double tx = scaleX - centreX ;
                double ty = scaleY - centreY;

                double t_length = Math.sqrt(tx*tx + ty*ty);
                a = Math.acos(ty / t_length);

                Log.e("calculateRotate",""+a);

                return false;
            }
        });
    }


    public void rotateImageClick(){
        rotate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scaledBitmap!=null){
                    scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, actualWidth, actualHeight, true);
                }
                rotation += 90;
                rotation %= 360;
                if (scaledBitmap!=null) {
                    Bitmap bitmap = getRotatedBitmap(scaledBitmap);
                    cover.setBitmapData(bitmap);
//                    cover.setImageBitmap(bitmap);
                }

            }
        });
    }

    public void moveImage(){

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;
//        if (imageModelList.get(0).getScreenShotImage()!=null) {
//            imageBitmap = imageModelList.get(0).getScreenShotImage();
//            scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, true);
//            cover.setImageBitmap(getRotatedBitmap(scaledBitmap));
//        }

        cover.setOnTouchListener(new View.OnTouchListener() {

            ConstraintLayout.LayoutParams parms;
            int startwidth;
            int startheight;
            float dx = 0, dy = 0, x = 0, y = 0;
            float angle = 0;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ImageView view = (ImageView) v;

//                ((BitmapDrawable) view.getDrawable()).setAntiAlias(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        parms = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                        startwidth = parms.width;
                        startheight = parms.height;
                        dx = event.getRawX() - parms.leftMargin;
                        dy = event.getRawY() - parms.topMargin;
                        mode = DRAG;
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            mode = ZOOM;
                        }

//                        d = rotation(event);

                        break;
                    case MotionEvent.ACTION_UP:

                        Log.e("kkkkkkkk","kkkkkkkkkkkkkkk");
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {

                            x = event.getRawX();
                            y = event.getRawY();

                            parms.leftMargin = (int) (x - dx);
                            parms.topMargin = (int) (y - dy);

                            parms.rightMargin = 0;
                            parms.bottomMargin = 0;
                            parms.rightMargin = parms.leftMargin + (5 * parms.width);
                            parms.bottomMargin = parms.topMargin + (10 * parms.height);

                            view.setLayoutParams(parms);

                        } else if (mode == ZOOM) {

                            if (event.getPointerCount() == 2) {

//                                newRot = rotation(event);
                                float r = newRot - d;
                                angle = r;

                                x = event.getRawX();
                                y = event.getRawY();

                                float newDist = spacing(event);
                                if (newDist > 10f) {
                                    float scale = newDist / oldDist * view.getScaleX();
                                    if (scale > 0.6) {
                                        scalediff = scale;
                                        view.setScaleX(scale);
                                        view.setScaleY(scale);

                                    }
                                }

                                view.animate().rotationBy(angle).setDuration(0).setInterpolator(new LinearInterpolator()).start();

                                x = event.getRawX();
                                y = event.getRawY();

                                parms.leftMargin = (int) ((x - dx) + scalediff);
                                parms.topMargin = (int) ((y - dy) + scalediff);

                                parms.rightMargin = 0;
                                parms.bottomMargin = 0;
                                parms.rightMargin = parms.leftMargin + (5 * parms.width);
                                parms.bottomMargin = parms.topMargin + (10 * parms.height);

                                view.setLayoutParams(parms);


                            }
                        }
                        break;
                }

                return true;

            }
        });
    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private Bitmap getRotatedBitmap(Bitmap bitmap) {
        if (rotation % 360 == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();


            matrix.postRotate(rotation, bitmap.getWidth() ,
                    bitmap.getHeight() );

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight() , matrix, true);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("ANGLE", rotation);
        super.onSaveInstanceState(outState);
    }

}
