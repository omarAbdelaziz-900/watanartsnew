package com.WattanArt.ui.Coaster;

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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.FlashMemory.ColorStyleFlashAdapter;
import com.WattanArt.ui.ShippingCoaster.ShippingCoasterActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

public class CoasterActivity extends BaseActivity implements CoasterMvpView, ColorStyleFlashAdapter.ItemListenerOfItems {

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
    CoasterMvpPresenter<CoasterMvpView> mPresenter;



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
    int colorOrStyle=0;
    int colorPositionFront=-1 ,stylePostionFront=-1;
    int colorPositionback=-1 ,stylePostionback=-1;


    UserData userData;
    String url ="http://23.236.154.106:8063/UploadedImages/";
//    String imgBack ="http://23.236.154.106:8063/img/flashb.png";

    String frontFilename , frontCoverFileName , backFileilename ,backCoverFileilename;
    File fileForFront ,fileForCoverFront ,fileForback ,fileForcoverBack;
    String styleNameFront,styleNameBack,colorNameFront,colorNameback;
    Intent intent;
    String prod_Id ,priceIn,priceOut;
    int cateId;
    int actW, actH;
    float scaleX, scaleY;
    Matrix mat;
    float[] pts;
    ImageView lowPixelsIv,lowPixelsIv_front;

    boolean show=false;
    double centreX, centreY;
    float a;

    int prod_id_circle,prod_id_square;
    String  price_in_circle ,price_out_circle;
    String   price_in_square ,price_out_square;
    String Coaster_circle,Coaster_square;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaster);


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

//        objectView_back.setBackgroundColor(Color.parseColor("#cf878787"));
//        objectView_front.setBackgroundColor(Color.parseColor("#cf878787"));
        objectView_front.setBackgroundDrawable(getResources().getDrawable(R.drawable.coaster_circle_bg));
        objectView_back.setBackgroundDrawable(getResources().getDrawable(R.drawable.coaster_square_bg));
        initView();
        getIntentData();
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
        loadImage(url+Coaster_square,image_back);

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

        pickFromGalleryAction();
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.coster);
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
        dimensionData_front = new DimensionData((int) getResources().getDimension(R.dimen.coaster_width),
                (int)getResources().getDimension(R.dimen.coaster_height),
                (int) getResources().getDimension(R.dimen.coaster_width)-10,
                (int)getResources().getDimension(R.dimen.coaster_height)-10,
                (int) getResources().getDimension(R.dimen.radius_coaster),
                (int) getResources().getDimension(R.dimen.coaster_width),
                (int)getResources().getDimension(R.dimen.coaster_height), 0, 0);
        componentView_front.initUrlData(dimensionData_front, new Pair<>( url+Coaster_circle,url+Coaster_circle));

    }

    void initBackView() {
        dimensionData_back  = new DimensionData((int) getResources().getDimension(R.dimen.coaster_width),
                (int)getResources().getDimension(R.dimen.coaster_height),
                (int) getResources().getDimension(R.dimen.coaster_width)-10,
                (int)getResources().getDimension(R.dimen.coaster_height)-10,
                (int) getResources().getDimension(R.dimen.radius_sq_coaster),
                (int) getResources().getDimension(R.dimen.coaster_width),
                (int)getResources().getDimension(R.dimen.coaster_height), 0, 0);
        componentView_back.initUrlData(dimensionData_back, new Pair<>( url+Coaster_square,url+Coaster_square));

    }


    public void pickFromGalleryAction() {
        pick_from_gallery_tv.setOnClickListener(v -> pickFromGallery());
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery() {
        if (ActivityCompat.checkSelfPermission(CoasterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPreviewEnabled(false).setShowCamera(false)
                    .start(CoasterActivity.this);
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
                if (colorOrStyle==1){
                    colorOrStyleType=1;
                    styleBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
                    colorBottomHolder.setBackground(getDrawable(R.drawable.background_selected));
                    replaceBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
                    initColorRecyclerView();
                }else {
                    colorOrStyleType=2;
                    styleBottomHolder.setBackground(getDrawable(R.drawable.background_selected));
                    colorBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
                    replaceBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
                    initColorRecyclerView();
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
        colorOrStyle=1;
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
        colorOrStyle=2;
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
//        styleBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
//        colorBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
//        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_selected_bordered));
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
//            Bitmap bitmap = ((BitmapDrawable) cover_front.getDrawable()).getBitmap();

//            currentBitmapFront = Bitmap.createBitmap(bitmap,
//                    0, 0, bitmap.getWidth(),
//                    bitmap.getHeight(), cover_front.getImageMatrix(), true);

            mobileBitmapFront = loadBitmapFromView(componentView_front);
            currentBitmapFront= loadBitmapFromCover(cover_front);
            Log.e("FrontBitmaps",currentBitmapFront+"    "+mobileBitmapFront);

            BitmapCoasterHelper.getInstance().setBitmapCoasterCircle(mobileBitmapFront);
            BitmapCoasterHelper.getInstance().setBitmapCoasterCircleCover(currentBitmapFront);
        }
    }
    public void createBackBitmaps(){
        if (cover_back.getDrawable() != null) {
//            Bitmap bitmap = ((BitmapDrawable) cover_back.getDrawable()).getBitmap();

//            currentBitmapBack = Bitmap.createBitmap(bitmap,
//                    0, 0, bitmap.getWidth(),
//                    bitmap.getHeight(), cover_back.getImageMatrix(), true);

            mobileBitmapBack = loadBitmapFromView(componentView_back);
            currentBitmapBack= loadBitmapFromCover(cover_back);
            Log.e("BackBitmaps",currentBitmapBack+"    "+mobileBitmapBack);
            BitmapCoasterHelper.getInstance().setBitmapCoasterSquare(mobileBitmapBack);
            BitmapCoasterHelper.getInstance().setBitmapCoasterSquareCover(currentBitmapBack);

        }
    }


    public void submit(View view) {
//        if (!show){
        if (cover_front.getDrawable() != null ) {

            if (cover_back.getDrawable() != null){
//                showLoaderDialog();

                componentView_front.setVisibility(View.VISIBLE);
                componentView_back.setVisibility(View.VISIBLE);

                createFrontBitmaps();
                createBackBitmaps();
                Log.e("BitmapFlashHelper1", BitmapCoasterHelper.getInstance().getBitmapCoasterCircle() + "");

                sendBitmapImage();
            }else {
                hideLoaderDialog();
                Toast.makeText(this, getString(R.string.choose_bg_back), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideLoaderDialog();
            Toast.makeText(this, getString(R.string.choose_bg_front), Toast.LENGTH_SHORT).show();
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
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
//        Canvas canvas = new Canvas(b);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
//        v.draw(canvas);
        return b;
    }

    public static Bitmap loadBitmapFromCover(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        return b;
    }

    public void clickImageFront(){
        image_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                color_recyclerview.setAdapter(colorBgMobileAdapter);

                image_front.setImageResource(R.drawable.ic_front_flash);
                loadImage(url+Coaster_square,image_back);
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
                color_recyclerview.setAdapter(colorBgMobileAdapter);
                image_back.setImageResource(R.drawable.ic_back_flash);
                loadImage(url+Coaster_circle,image_front);
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
//        hideLoaderDialog();
        initColorRecyclerView();
        if (flashType.equals("front")){
            componentView_back.setVisibility(View.INVISIBLE);
            componentView_front.setVisibility(View.VISIBLE);
            image_front.setImageResource(R.drawable.ic_front_flash);
            loadImage(url+Coaster_square,image_back);
        }else {
            componentView_back.setVisibility(View.VISIBLE);
            componentView_front.setVisibility(View.INVISIBLE);
            image_back.setImageResource(R.drawable.ic_back_flash);
            loadImage(url+Coaster_circle,image_front);

        }

        Intent intent = new Intent(this, ShippingCoasterActivity.class);

        intent.putExtra("styleNameFront", styleNameFront+"");
        intent.putExtra("styleNameBack", styleNameBack+"");
        intent.putExtra("colorNameFront", colorNameFront+"");
        intent.putExtra("colorNameback", colorNameback+"");
        intent.putExtra("photosFront", photosFront.get(0)+"");
        intent.putExtra("photosBack", photosBack.get(0)+"");

        intent.putExtra("catId", cateId+"");
        intent.putExtra("prod_id_circle", prod_id_circle+"");
        intent.putExtra("Coaster_circle", Coaster_circle+"");
        intent.putExtra("price_in_circle", price_in_circle+"");
        intent.putExtra("price_out_circle", price_out_circle+"");
        intent.putExtra("prod_id_square", prod_id_square+"");
        intent.putExtra("Coaster_square", Coaster_square+"");
        intent.putExtra("price_in_square", price_in_square+"");
        intent.putExtra("price_out_square", price_out_square+"");
        Log.e("photosFront",photosFront.get(0)+"");
        Log.e("photosBack",photosBack.get(0)+"");
        startActivity(intent);
    }

    void getIntentData(){
        Intent intent=getIntent();
        if (intent.hasExtra("catId")){
             cateId=intent.getIntExtra("catId",0);
            Log.e("cateId",cateId+"");
        }
        if (intent.hasExtra("prod_id_circle")){
            prod_id_circle=intent.getIntExtra ("prod_id_circle",0);
            Log.e("prod_id_circle",prod_id_circle+"");
        }
        if (intent.hasExtra("Coaster_circle")){
            Coaster_circle=intent.getStringExtra("Coaster_circle");
            Log.e("Coaster_circle",Coaster_circle+"");
        }
        if (intent.hasExtra("price_in_circle")){
            price_in_circle=intent.getStringExtra("price_in_circle");
            Log.e("price_in_circle",price_in_circle+"");
        }
        if (intent.hasExtra("price_out_circle")){
            price_out_circle=intent.getStringExtra("price_out_circle");
            Log.e("price_out_circle",price_out_circle+"");
        }

        if (intent.hasExtra("prod_id_square")){
            prod_id_square=intent.getIntExtra("prod_id_square",0);
            Log.e("prod_id_square",prod_id_square+"");
        }
        if (intent.hasExtra("Coaster_square")){
            Coaster_square=intent.getStringExtra("Coaster_square");
            Log.e("Coaster_square",Coaster_square+"");
        }
        if (intent.hasExtra("price_in_square")){
            price_in_square=intent.getStringExtra("price_in_square");
            Log.e("price_in_square",price_in_square+"");
        }
        if (intent.hasExtra("price_out_square")){
            price_out_square=intent.getStringExtra("price_out_square");
            Log.e("price_out_square",price_out_square+"");
        }

    }
}

