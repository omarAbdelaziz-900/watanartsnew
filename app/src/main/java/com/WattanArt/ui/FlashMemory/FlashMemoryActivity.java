package com.WattanArt.ui.FlashMemory;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.BitmapUtils;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.Category.ColorBgMobileAdapter;
import com.WattanArt.ui.ShippingForFlashMemory.ShippingFlashMemoryActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.WattanArt.ui.mobileCase.ComponentActivity;
import com.WattanArt.ui.mobileCase.MobileMvpPresenter;
import com.WattanArt.ui.mobileCase.MobileMvpView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

public class FlashMemoryActivity extends BaseActivity implements FlashMemoryMvpView, ColorStyleFlashAdapter.ItemListenerOfItems{


    CustomeTextViewBold pick_from_gallery_tv;
    CustomeTextViewBold complete_tv;
    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;

//    Front//
    CoverView cover_front;
    ImageCaseComponent componentView_front;
    ObjectView objectView_front;
    DimensionData dimensionData_front;
    AccessoriesView accessoriesView_front;

    //    back//
    CoverView cover_back;
    ImageCaseComponent componentView_back;
    ObjectView objectView_back;
    DimensionData dimensionData_back;
    AccessoriesView accessoriesView_back;

    String flashType;

    ImageView image_front;

    ImageView image_back;

    // Fields
    private String TAG = this.getClass().getSimpleName();
    ArrayList<String> photosFront,photosBack;

    Bitmap currentBitmapFront, mobileBitmapFront;
    Bitmap currentBitmapBack, mobileBitmapBack;

    ImageView showImage;

    @Inject
    FlashMemoryMvpPresenter<FlashMemoryMvpView> mPresenter;

    public  static boolean backFromShipping=false;
    RelativeLayout styleBottomHolder;
    RelativeLayout rotateBottomHolder;
    RelativeLayout replaceBottomHolder;
    RelativeLayout colorBottomHolder;
    RecyclerView color_recyclerview;
    ImageView mToolbarBackImageView;
    CustomeTextView submit;
    CustomeTextViewBold mToolbarTitleTextView;

    LinearLayout linear_img_front;
    LinearLayout linear_img_back;
    List<String> General_Style ,General_Color;
    ColorStyleFlashAdapter colorBgMobileAdapter;
    int colorOrStyleType;
    File fileGetingFront;
    File fileGetingBack;

    int colorPositionFront=-1 ,stylePostionFront=-1;
    int colorPositionback=-1 ,stylePostionback=-1;


    UserData userData;
    String imgFront ="http://23.236.154.106:8063/img/flashf.png";
    String imgBack ="http://23.236.154.106:8063/img/flashb.png";

    String frontFilename , frontCoverFileName , backFileilename ,backCoverFileilename;
    File fileForFront ,fileForCoverFront ,fileForback ,fileForcoverBack;
    String styleNameFront,styleNameBack,colorNameFront,colorNameback;
    Intent intent;
    String prod_Id ,priceIn,priceOut;

    int actW, actH;
    float scaleX, scaleY;
    Matrix mat;
    float[] pts;
    ImageView lowPixelsIv,lowPixelsIv_front;

    boolean show=false;
    double centreX, centreY;
    float a;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_memory);

        ButterKnife.bind(this);
        showImage = findViewById(R.id.showImage);

        pick_from_gallery_tv = findViewById(R.id.pick_from_gallery_tv);
        complete_tv = findViewById(R.id.order_design_tv);
        image_front = findViewById(R.id.image_front);
        image_back = findViewById(R.id.image_back);
        componentView_front = findViewById(R.id.componentView_front);
        styleBottomHolder=findViewById(R.id.styleBottomHolder);
        rotateBottomHolder=findViewById(R.id.rotateBottomHolder);
        replaceBottomHolder=findViewById(R.id.replaceBottomHolder);
        colorBottomHolder=findViewById(R.id.colorBottomHolder);
        color_recyclerview=findViewById(R.id.color_recyclerview);
        mToolbarBackImageView=findViewById(R.id.toolbar_image_view);
        mToolbarTitleTextView=findViewById(R.id.toolbar_tv_title);
        submit=findViewById(R.id.submit);
        lowPixelsIv = findViewById(R.id.lowPixelsIv);
        lowPixelsIv_front = findViewById(R.id.lowPixelsIv_front);
        linear_img_front=(LinearLayout)findViewById(R.id.linear_img_front);
        linear_img_back=(LinearLayout)findViewById(R.id.linear_img_back);
        componentView_back = findViewById(R.id.componentView_back);
        accessoriesView_back = (AccessoriesView) componentView_back.getComponentView(R.id.accessories);


        initFrontView();
//        }else if (flashType.equals("back")){
        initBackView();
//        componentView_back.initUrlData(dimensionData_front, new Pair<>( imgBack,imgBack));
        cover_front = (CoverView) componentView_front.getComponentView(R.id.cover);
        accessoriesView_front = (AccessoriesView) componentView_front.getComponentView(R.id.accessories);
        objectView_front = (ObjectView) componentView_front.getComponentView(R.id.component);
        objectView_back = (ObjectView) componentView_back.getComponentView(R.id.component);
        cover_back = (CoverView) componentView_back.getComponentView(R.id.cover);

        objectView_back.setBackgroundColor(Color.parseColor("#cf878787"));
        objectView_front.setBackgroundColor(Color.parseColor("#cf878787"));
        initView();
        intent=getIntent();
        if (intent.hasExtra("prod_Id")){
            prod_Id=intent.getStringExtra("prod_Id");
        }
        if (intent.hasExtra("priceIn")){
            priceIn=intent.getStringExtra("priceIn");
        }
        if (intent.hasExtra("priceOut")){
            priceOut=intent.getStringExtra("priceOut");
        }
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {


    }


    public void initView(){
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            mPresenter.onAttach(this);
        }

//        loadImage(imgFront,image_front);
        image_front.setImageResource(R.drawable.ic_front_flash);
        loadImage(imgBack,image_back);

        userData = new UserData();
        if (isNetworkConnected()) {
            mPresenter.getSubCategory(userData.getLocalization(this),1);
        } else {
            showMessage(getString(R.string.error_no_internet_connection));
        }

        flashType="front";
        componentView_front.setVisibility(View.VISIBLE);

        actionColorOrStyle();
        clickImageback();
        clickImageFront();
        measureImageDimensions();
//        if (flashType.equals("front")){
//            initFrontView();
//        }else if (flashType.equals("back")){
//            initBackView();
//        }
        pickFromGalleryAction();
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.flash_memory);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });

        lowPixelsIv.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, getString(R.string.img_resoluyion), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });

        lowPixelsIv_front.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, getString(R.string.img_resoluyion), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }


    void initFrontView() {
        Log.e("hhhshhs",getResources().getDimension(R.dimen.flash_width)+"");
            dimensionData_front = new DimensionData((int) getResources().getDimension(R.dimen.flash_width),
                    (int)getResources().getDimension(R.dimen.flash_height),
                    (int) getResources().getDimension(R.dimen.flash_width)-15,
                    (int)getResources().getDimension(R.dimen.flash_height),
                    (int) getResources().getDimension(R.dimen.radius_flash),
                    (int) getResources().getDimension(R.dimen.flash_width),
                    (int)getResources().getDimension(R.dimen.flash_height), 0, 0);
        componentView_front.initUrlData(dimensionData_front, new Pair<>( imgFront,imgFront));

    }

    void initBackView() {
        dimensionData_back  = new DimensionData((int) getResources().getDimension(R.dimen.flash_width),
                (int)getResources().getDimension(R.dimen.flash_height),
                (int) getResources().getDimension(R.dimen.flash_width)-15,
                (int)getResources().getDimension(R.dimen.flash_height),
                (int) getResources().getDimension(R.dimen.radius_flash),
                (int) getResources().getDimension(R.dimen.flash_width),
                (int)getResources().getDimension(R.dimen.flash_height), 0, 0);
        componentView_back.initUrlData(dimensionData_back, new Pair<>( imgBack,imgBack));

    }


    public void pickFromGalleryAction() {
        pick_from_gallery_tv.setOnClickListener(v -> pickFromGallery());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {
        if (ActivityCompat.checkSelfPermission(FlashMemoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(FlashMemoryActivity.this);
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

                if (flashType.equals("front")) {
                    photosFront = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photosFront == null || photosFront.isEmpty()) return;
                    cover_front.setData(photosFront.get(0));
                }else if (flashType.equals("back")){
                    photosBack = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photosBack == null || photosBack.isEmpty()) return;
                    cover_back.setData(photosBack.get(0));
                }
            }
        }
    }

    private void initColorRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        color_recyclerview.setLayoutManager(manager);
        color_recyclerview.setItemAnimator(new DefaultItemAnimator());
        color_recyclerview.setNestedScrollingEnabled(false);
        color_recyclerview.setHasFixedSize(true);
        color_recyclerview.scrollToPosition(0);
        if (colorOrStyleType==1) {
            colorBgMobileAdapter = new ColorStyleFlashAdapter(colorOrStyleType, this, General_Color, this);
        }else {
            colorBgMobileAdapter = new ColorStyleFlashAdapter(colorOrStyleType, this, General_Style, this);
        }
        color_recyclerview.setAdapter(colorBgMobileAdapter);
    }

    @Override
    public void returnUploadedImage(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageName",imageUploadResponseModel.getFileName());
    }

    @Override
    public void onColorItemsClickFromAdapter( int position) {

        if (flashType.equals("front")) {
            objectView_front.setData(null);
            Log.e("position->>",position+"");
            if (position!=colorPositionFront) {
                Log.e("colorPosotion",colorPositionFront+"");
                objectView_front.setBackgroundColor(Color.parseColor("#"+General_Color.get(position)));
                colorPositionFront=position;
            }
            colorNameFront=General_Color.get(position);
        }else if (flashType.equals("back")){
            objectView_back.setData(null);
            Log.e("position->>",position+"");
            if (position!=colorPositionback) {
                Log.e("colorPosotion",colorPositionback+"");
                objectView_back.setBackgroundColor(Color.parseColor("#"+General_Color.get(position)));
                colorPositionback=position;
            }
            colorNameback=General_Color.get(position);
        }

    }

    @Override
    public void onStyleItemsClickFromAdapter(int position) {
        if (flashType.equals("front")){
            objectView_front.setBackgroundColor(0x00000000);
            String imgName="http://23.236.154.106:8063/UploadedImages/"+General_Style.get(position);
            Log.e("colorPosotion",stylePostionFront+"");
            Log.e("position",position+"");
            if (position!=stylePostionFront) {
                objectView_front.setData(imgName);
                stylePostionFront=position;
            }
            styleNameFront=General_Style.get(position);
        }else if (flashType.equals("back")){
            objectView_back.setBackgroundColor(0x00000000);
            String imgName="http://23.236.154.106:8063/UploadedImages/"+General_Style.get(position);
            Log.e("colorPosotion",stylePostionback+"");
            Log.e("position",position+"");
            if (position!=stylePostionback) {
                objectView_back.setData(imgName);
                stylePostionback=position;
            }
            styleNameBack=General_Style.get(position);
        }
    }





    public void actionColorOrStyle(){
        colorBottomHolder.setOnClickListener(v -> colorBottomClick());
        styleBottomHolder.setOnClickListener(v -> styleBottomClick());
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (General_Color!=null) {
            if (!General_Color.isEmpty())
                General_Color.clear();
        }
        if (General_Style!=null) {
            if (!General_Style.isEmpty())
                General_Style.clear();
        }
        finish();
    }


    public void replaceCOverImage(View view) {
//        color_recyclerview.setAdapter(null);
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        colorBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_selected_bordered));
        pickFromGallery();
    }

    public void colorBottomClick(){
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        colorBottomHolder.setBackground(getDrawable(R.drawable.background_selected_bordered));
        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        if (colorBgMobileAdapter!=null){
            colorBgMobileAdapter.notifyDataSetChanged();
        }
        color_recyclerview.setVisibility(View.VISIBLE);
        colorOrStyleType=1;
        if (General_Color!=null){
            initColorRecyclerView();
        }
    }

    public void styleBottomClick(){
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_selected_bordered));
        colorBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        if (colorBgMobileAdapter!=null){
            colorBgMobileAdapter.notifyDataSetChanged();
        }
        color_recyclerview.setVisibility(View.VISIBLE);
        colorOrStyleType=2;
        if (General_Style!=null){
            initColorRecyclerView();
        }
    }

    public void createFrontBitmaps(){
        if (cover_front.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) cover_front.getDrawable()).getBitmap();

            currentBitmapFront = Bitmap.createBitmap(bitmap,
                    0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), cover_front.getImageMatrix(), true);

            mobileBitmapFront = loadBitmapFromView(componentView_front,bitmap);
            Log.e("FrontBitmaps",currentBitmapFront+"    "+mobileBitmapFront);

            BitmapFlashHelper.getInstance().setBitmapFront(mobileBitmapFront);
            BitmapFlashHelper.getInstance().setBitmapFrontCover(currentBitmapFront);
        }
    }
    public void createBackBitmaps(){
        if (cover_back.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) cover_back.getDrawable()).getBitmap();

            currentBitmapBack = Bitmap.createBitmap(bitmap,
                    0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), cover_back.getImageMatrix(), true);

            mobileBitmapBack = loadBitmapFromView(componentView_back,bitmap);
            Log.e("BackBitmaps",currentBitmapBack+"    "+mobileBitmapBack);
            BitmapFlashHelper.getInstance().setBitmapBack(mobileBitmapBack);
            BitmapFlashHelper.getInstance().setBitmapBackCover(currentBitmapBack);

        }
    }


    public void submit(View view) {
        if (!show){
        if (cover_front.getDrawable() != null && cover_back.getDrawable() != null) {

            componentView_front.setVisibility(View.VISIBLE);
            componentView_back.setVisibility(View.VISIBLE);

            createFrontBitmaps();
            createBackBitmaps();
            Log.e("BitmapFlashHelper1", BitmapFlashHelper.getInstance().getBitmapFront() + "");
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        createFrontBitmaps();
//                        createBackBitmaps();
//
//                    if (mobileBitmapFront!=null && currentBitmapFront!=null && mobileBitmapBack!=null && currentBitmapBack!=null){
//
//
//                    }
//                    }
//                }, 10);

            sendBitmapImage();
        } else {
            hideLoaderDialog();
            Toast.makeText(this, getString(R.string.choose_bg), Toast.LENGTH_SHORT).show();
        }

    }
    }







    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



    public static Bitmap loadBitmapFromView(View v,Bitmap bitmap) {
//        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        Canvas canvas = new Canvas(b);
//        canvas.drawColor(Color.TRANSPARENT);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(canvas);
        return b;
    }

    public void clickImageFront(){
        image_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_front.setImageResource(R.drawable.ic_front_flash);
                loadImage(imgBack,image_back);
//                image_front.setBackground(getDrawable(R.drawable.background_with_big_border_selected));
//                image_back.setBackground(getDrawable(R.drawable.background_with_big_border_unselected));
//                createBackBitmaps();
                flashType="front";
                componentView_front.setVisibility(View.VISIBLE);
                componentView_back.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void clickImageback(){
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_back.setImageResource(R.drawable.ic_back_flash);
                loadImage(imgFront,image_front);
//                linear_img_front.setBackground(getDrawable(R.drawable.background_with_big_border_unselected));
//                linear_img_back.setBackground(getDrawable(R.drawable.background_with_big_border_selected));
//                createFrontBitmaps();
                flashType="back";
                componentView_front.setVisibility(View.INVISIBLE);
                componentView_back.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void returnSubCategory(CategoryMobileRsponseModel responseModel) {

        if (responseModel.getResult()!=null) {
            General_Style = responseModel.getResult().get(0).getGeneral_Style();

            General_Color = responseModel.getResult().get(0).getGeneral_Color();

            color_recyclerview.setVisibility(View.INVISIBLE);
        }else {

        }

        initColorRecyclerView();
        color_recyclerview.setVisibility(View.VISIBLE);
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_selected_bordered));
//        linear_img_front.setBackground(getDrawable(R.drawable.background_with_big_border_selected));
//        linear_img_back.setBackground(getDrawable(R.drawable.background_with_big_border_unselected));
    }

    public void loadImage(String image, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.img_not_available);
        options.error(R.drawable.img_not_available);

        String path = image;
        Log.e("path", path);

        Glide.with(this)
                .asBitmap()
                .load(path.trim())
                .apply(options).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                imageView.setImageBitmap(resource);
                return true;
            }
        }).into(imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (backFromShipping){
//            componentView_back.setVisibility(View.INVISIBLE);
//            linear_img_front.setBackground(getDrawable(R.drawable.background_selected_bordered));
//        }
    }

    public void sendBitmapImage() {
        initColorRecyclerView();
//        if (colorBgMobileAdapter != null) {
//            color_recyclerview.setAdapter(colorBgMobileAdapter);
//        }
        if (flashType.equals("front")){
            componentView_back.setVisibility(View.INVISIBLE);
            componentView_front.setVisibility(View.VISIBLE);
            image_front.setImageResource(R.drawable.ic_front_flash);
            loadImage(imgBack,image_back);
        }else {
            componentView_back.setVisibility(View.VISIBLE);
            componentView_front.setVisibility(View.INVISIBLE);
            image_back.setImageResource(R.drawable.ic_back_flash);
            loadImage(imgFront,image_front);

        }

        Intent intent = new Intent(this, ShippingFlashMemoryActivity.class);

//        intent.putExtra("frontFilename", frontFilename+"");
//        intent.putExtra("frontCoverFileName", frontCoverFileName+"");
//        intent.putExtra("backFileilename", backFileilename+"");
//        intent.putExtra("backCoverFileilename", backCoverFileilename+"");

        intent.putExtra("styleNameFront", styleNameFront+"");
        intent.putExtra("styleNameBack", styleNameBack+"");
        intent.putExtra("colorNameFront", colorNameFront+"");
        intent.putExtra("colorNameback", colorNameback+"");
        intent.putExtra("photosFront", photosFront.get(0)+"");
        intent.putExtra("photosBack", photosBack.get(0)+"");
        intent.putExtra("prod_Id", prod_Id+"");
        intent.putExtra("priceIn", priceIn+"");
        intent.putExtra("priceOut", priceOut+"");
        Log.e("photosFront",photosFront.get(0)+"");
        Log.e("photosBack",photosBack.get(0)+"");
        startActivity(intent);
    }

    public void measureImageDimensions() {
        cover_front.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float[] f = new float[9];
                cover_front.getImageMatrix().getValues(f);

                // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)

                scaleX = f[Matrix.MSCALE_X];
                scaleY = f[Matrix.MSCALE_Y];

                final int coverWidth = cover_front.getMeasuredWidth();
                final int coverHeight = cover_front.getMeasuredHeight();

                // Calculate the actual dimensions
                actW = Math.round(coverWidth * scaleX);
                actH = Math.round(coverHeight * scaleY);

                Log.e("DBG", "[" + coverWidth + "," + coverHeight + "] -> [" + actW + "," + actH + "] & scales: x=" + scaleX + " y=" + scaleY);

                float wScale = (float) actW / (float) coverWidth;
                float hScale = (float) actH / (float) coverHeight;
                float scale = Math.min(wScale, hScale);

                if (actH<0){
                    actH=(actH*-1)+600;
                }
                if (actW<0){
                    actW=(actW*-1+600);
                }

                zoomEffectFront();

                Log.e("calculateScaleImage", "" + scale);


                float aspect_ratio = (float) coverWidth / (float) coverHeight;
                float aspect_ratio2 = (float) actW / (float) actH;

                Log.e("calculateAspectRatio", "" + aspect_ratio + " && " + aspect_ratio2);


                centreX = cover_front.getX() + cover_front.getWidth() / 2;
                centreY = cover_front.getY() + cover_front.getHeight() / 2;

                double tx = scaleX - centreX;
                double ty = scaleY - centreY;

                double t_length = Math.sqrt(tx * tx + ty * ty);
                a = (float) Math.acos(ty / t_length);

                Log.e("calculateRotate", "" + a);


//                Bitmap bMap = BitmapFactory.decodeFile(selectedImagePath);
//                cover.setImageBitmap(bMap);
                mat = cover_front.getImageMatrix();

//                RectF drawableRect = new RectF(0, 0, actW, actH);
//                RectF viewRect = new RectF(0, 0, cover.getWidth(), cover.getHeight());
//                mat.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL);
                Log.e("matrixCalc", "" + mat);
//                cover.setImageMatrix(mat);

                pts = new float[]{event.getX(), event.getY()};
                Log.e("ptspts", "" + pts);
                return false;
            }
        });


        cover_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float[] f = new float[9];
                cover_back.getImageMatrix().getValues(f);

                // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)

                scaleX = f[Matrix.MSCALE_X];
                scaleY = f[Matrix.MSCALE_Y];

                final int coverWidth = cover_back.getMeasuredWidth();
                final int coverHeight = cover_back.getMeasuredHeight();

                // Calculate the actual dimensions
                actW = Math.round(coverWidth * scaleX);
                actH = Math.round(coverHeight * scaleY);

                Log.e("DBG", "[" + coverWidth + "," + coverHeight + "] -> [" + actW + "," + actH + "] & scales: x=" + scaleX + " y=" + scaleY);

                float wScale = (float) actW / (float) coverWidth;
                float hScale = (float) actH / (float) coverHeight;
                float scale = Math.min(wScale, hScale);

                if (actH<0){
                    actH=(actH*-1)+600;
                }
                if (actW<0){
                    actW=(actW*-1+600);
                }

                zoomEffectack();

                Log.e("calculateScaleImage", "" + scale);


                float aspect_ratio = (float) coverWidth / (float) coverHeight;
                float aspect_ratio2 = (float) actW / (float) actH;

                Log.e("calculateAspectRatio", "" + aspect_ratio + " && " + aspect_ratio2);


                centreX = cover_back.getX() + cover_back.getWidth() / 2;
                centreY = cover_back.getY() + cover_back.getHeight() / 2;

                double tx = scaleX - centreX;
                double ty = scaleY - centreY;

                double t_length = Math.sqrt(tx * tx + ty * ty);
                a = (float) Math.acos(ty / t_length);

                Log.e("calculateRotate", "" + a);


//                Bitmap bMap = BitmapFactory.decodeFile(selectedImagePath);
//                cover.setImageBitmap(bMap);
                mat = cover_back.getImageMatrix();

//                RectF drawableRect = new RectF(0, 0, actW, actH);
//                RectF viewRect = new RectF(0, 0, cover.getWidth(), cover.getHeight());
//                mat.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL);
                Log.e("matrixCalc", "" + mat);
//                cover.setImageMatrix(mat);

                pts = new float[]{event.getX(), event.getY()};
                Log.e("ptspts", "" + pts);
                return false;
            }
        });


    }

    public boolean zoomEffectFront(){
        if (1200<actW && 1200<actH ){
            lowPixelsIv_front.setVisibility(View.VISIBLE);
            show=true;
        }else if (actW<500 &&   actH<500){
            lowPixelsIv_front.setVisibility(View.VISIBLE);
            show=true;
        }else {
            lowPixelsIv_front.setVisibility(View.GONE);
            show=false;
        }
        return show;
    }
    public boolean zoomEffectack(){
        if (1200<actW && 1200<actH ){
            lowPixelsIv.setVisibility(View.VISIBLE);
            show=true;
        }else if (actW<500 &&   actH<500){
            lowPixelsIv.setVisibility(View.VISIBLE);
            show=true;
        }else {
            lowPixelsIv.setVisibility(View.GONE);
            show=false;
        }
        return show;
    }
}

