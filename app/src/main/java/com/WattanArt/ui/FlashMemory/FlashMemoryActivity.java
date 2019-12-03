package com.WattanArt.ui.FlashMemory;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
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

    @Inject
    FlashMemoryMvpPresenter<FlashMemoryMvpView> mPresenter;

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



    double centreX ,centreY ,a;

    String mobileImage ,mobileType;
    List<String> General_Style ,General_Color;
    ColorStyleFlashAdapter colorBgMobileAdapter;
    int colorOrStyleType;
    File fileGetingFront;
    File fileGetingBack;

    int colorPositionFront=-1 ,stylePostionFront=-1;
    int colorPositionback=-1 ,stylePostionback=-1;


    Intent intent;

    UserData userData;
    int cateId=0;
    String imgFront ="http://23.236.154.106:8063/img/flashf.png";
    String imgBack ="http://23.236.154.106:8063/img/flashb.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_memory);
        ButterKnife.bind(this);
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

        loadImage(imgFront,image_front);
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
    }


    void initFrontView() {
            dimensionData_front = new DimensionData(900, 700, 890,
                    700, 40, 900,
                    700, 0, 0);
        componentView_front.initUrlData(dimensionData_front, new Pair<>( imgFront,imgFront));

    }

    void initBackView() {
        dimensionData_back  = new DimensionData(900, 700, 890,
                700, 40, 900,
                700, 0, 0);
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
        }else if (flashType.equals("back")){
            objectView_back.setData(null);
            Log.e("position->>",position+"");
            if (position!=colorPositionback) {
                Log.e("colorPosotion",colorPositionback+"");
                objectView_back.setBackgroundColor(Color.parseColor("#"+General_Color.get(position)));
                colorPositionback=position;
            }
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
        }else if (flashType.equals("back")){
            objectView_back.setBackgroundColor(0x00000000);
            String imgName="http://23.236.154.106:8063/UploadedImages/"+General_Style.get(position);
            Log.e("colorPosotion",stylePostionback+"");
            Log.e("position",position+"");
            if (position!=stylePostionback) {
                objectView_back.setData(imgName);
                stylePostionback=position;
            }
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
        color_recyclerview.setAdapter(null);
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

    public void submit(View view) {
        if (flashType.equals("front")){
            if (cover_front.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) cover_front.getDrawable()).getBitmap();
                currentBitmapFront = Bitmap.createBitmap(bitmap,
                        0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), cover_front.getImageMatrix(), true);
                mobileBitmapFront = loadBitmapFromView(componentView_front);
                float rorationAngle = cover_front.getCurrentAngle();
                float zoom = cover_front.getCurrentScale();
                saveTempBitmap(BitmapUtils.scaleDown(mobileBitmapFront, 1000, true));
                Log.i(TAG, String.format("Size (%d , %d) allMobile Size (%d , %d) current Rottion Angle %f and Zoom %f", currentBitmapFront.
                                getWidth(), currentBitmapFront.getHeight()
                        , mobileBitmapFront.getWidth(), mobileBitmapFront.getHeight(), rorationAngle, zoom));

                if (photosFront != null && fileGetingFront != null) {
                    mPresenter.returnUploadedImage(photosFront, fileGetingFront);
                } else {
                    Toast.makeText(this, "قم باختيار الخلفيه", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "قم باختيار الخلفيه", Toast.LENGTH_SHORT).show();
            }

    }else if (flashType.equals("back")){
            if (cover_back.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) cover_back.getDrawable()).getBitmap();
                currentBitmapBack = Bitmap.createBitmap(bitmap,
                        0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), cover_back.getImageMatrix(), true);
                mobileBitmapBack = loadBitmapFromView(componentView_back);
                float rorationAngle = cover_back.getCurrentAngle();
                float zoom = cover_back.getCurrentScale();
                saveTempBitmap(BitmapUtils.scaleDown(mobileBitmapBack, 1000, true));
                Log.i(TAG, String.format("Size (%d , %d) allMobile Size (%d , %d) current Rottion Angle %f and Zoom %f", currentBitmapBack.getWidth(), currentBitmapBack.getHeight()
                        , mobileBitmapBack.getWidth(), mobileBitmapBack.getHeight(), rorationAngle, zoom));

                if (photosBack != null && fileGetingBack != null) {
                    mPresenter.returnUploadedImage(photosBack, fileGetingBack);
                } else {
                    Toast.makeText(this, "قم باختيار الخلفيه", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "قم باختيار الخلفيه", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public  void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        }else{
        }
    }

    public  File saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Shutta_"+ timeStamp +".jpg";

        if (flashType.equals("front")){
            fileGetingFront = new File(myDir, fname);
            if (fileGetingFront.exists()) fileGetingFront.delete ();
            try {
                FileOutputStream out = new FileOutputStream(fileGetingFront);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Log.e("kljkjkjk",fileGetingFront+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileGetingFront;
        }else {
            fileGetingBack = new File(myDir, fname);
            if (fileGetingBack.exists()) fileGetingBack.delete ();
            try {
                FileOutputStream out = new FileOutputStream(fileGetingBack);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Log.e("kljkjkjk",fileGetingBack+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileGetingBack;
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

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        canvas.drawColor(Color.TRANSPARENT);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(canvas);
        return b;
    }

    public void clickImageFront(){
        image_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                linear_img_front.setBackground(getDrawable(R.drawable.background_with_big_border_selected));
//                linear_img_back.setBackground(getDrawable(R.drawable.background_with_big_border_unselected));
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
//                linear_img_front.setBackground(getDrawable(R.drawable.background_with_big_border_unselected));
//                linear_img_back.setBackground(getDrawable(R.drawable.background_with_big_border_selected));
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

            color_recyclerview.setVisibility(View.GONE);
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
}

