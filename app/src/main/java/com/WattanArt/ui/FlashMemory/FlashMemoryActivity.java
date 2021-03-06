package com.WattanArt.ui.FlashMemory;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.WattanArt.AndroidDialogTools;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.RoundedBitmapClass;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.ViewHelper;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.BitmapUtils;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.VerifyDialogInterfaceForText;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.ColorUtils;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.SampleMokupItems;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.Category.ColorBgMobileAdapter;
import com.WattanArt.ui.PublicShipping.PublicBitmapsModel;
import com.WattanArt.ui.PublicShipping.PublicShippingActivity;
import com.WattanArt.ui.ShippingForFlashMemory.ShippingFlashMemoryActivity;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.base.BaseActivity;
import com.WattanArt.ui.mobileCase.ComponentActivity;
import com.WattanArt.ui.mobileCase.FontTypeAdapter;
import com.WattanArt.ui.mobileCase.FontTypeModel;
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

import static com.WattanArt.ui.PublicShipping.PublicShippingActivity.publicBitmapsModels;

public class FlashMemoryActivity extends BaseActivity implements FlashMemoryMvpView, ColorStyleFlashAdapter.ItemListenerOfItems,View.OnTouchListener,
        FontTypeAdapter.ItemListener,FlashMemorySizeAdapter.ItemListenerOfFlashMemorSize{


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

    CustomeTextView submit;
    CustomeTextViewBold mToolbarTitleTextView;
    ImageView mToolbarBackImageView;

    LinearLayout linear_img_front;
    LinearLayout linear_img_back;
    List<String> General_Style ,General_Color;
    ColorStyleFlashAdapter colorBgMobileAdapter;
    FlashMemorySizeAdapter flashMemorySizeAdapter;
    int colorOrStyleType;
    File fileGetingFront;
    File fileGetingBack;

    int colorPositionFront=-1 ,stylePostionFront=-1;
    int colorPositionback=-1 ,stylePostionback=-1;


    UserData userData;
    String imgFront ="http://23.236.154.106:8063/img/flashf.png";
    String imgBack ="http://23.236.154.106:8063/img/flashb.png";

    String frontFilename , frontCoverFileName , backFileilename ,backCoverFileilename;
    String styleNameFront,styleNameBack,colorNameFront,colorNameback;
    Intent intent;
    String prod_Id ,priceIn,priceOut;

    int actW, actH;
    float scaleX, scaleY;
    Matrix mat;
    float[] pts;

    boolean show=false;
    double centreX, centreY;
    float a;
    int colorOrStyle=0;

    public static EditText txt_back ,txt_front;
    RelativeLayout relative_for_text,fontBottomHolder,sizeBottomHolder,colorsBottomHolder,above_bottom_linear,WritingBottomHolder;
    LinearLayout bar_font_size_holder, bar_color_holder,bottom_linear;
    LinearLayout linear_color_style;
    SeekBar hueSeekBar,font_sizeSeekBar;
    private int _xDelta;
    private int _yDelta;
    CoverView cover_view_text_front,cover_view_text_back;
    RelativeLayout applyBottomHolder;
    RelativeLayout textBottomHolder;
    RelativeLayout SizeFlashBottomHolder;
    RecyclerView font_recycler_view;
    FontTypeAdapter fontTypeAdapter;
    List< FontTypeModel> fontTypeModelList;
    int seekbar_value,seekbar_color_value;

    List<FlashMemorySizeModel>flashMemorySizeModelList=new ArrayList<>();

    MobileOrderRequest mobileOrderRequest;
    MobileOrderRequest.OrderBean orderBean;
    MobileOrderRequest.OrderDetilsBean orderDetilsBean;
    List<MobileOrderRequest.OrderDetilsBean> orderDetilsBeanList;
    List<MobileOrderRequest.OrderDetilsBean.ImagesBean> images;
    MobileOrderRequest.OrderDetilsBean.ImagesBean imagesBean,imagesBean2;
    File fileForFront, fileForCoverFront ,fileForBack ,fileForCoverBack;
    String filename, filename2,filename3,filename4;
    int btnConfirmType=0;
    RelativeLayout sampleMokupBottomHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_memory);

        ButterKnife.bind(this);
        showImage = findViewById(R.id.showImage);

        mobileOrderRequest=new MobileOrderRequest();
        orderBean=new MobileOrderRequest.OrderBean();
        orderDetilsBean=new MobileOrderRequest.OrderDetilsBean();
        images=new ArrayList<>();
        orderDetilsBeanList=new ArrayList<>();
        imagesBean=new MobileOrderRequest.OrderDetilsBean.ImagesBean();
        imagesBean2=new MobileOrderRequest.OrderDetilsBean.ImagesBean();

        fontTypeModelList = new ArrayList<>();
        sampleMokupBottomHolder = findViewById(R.id.sampleMokupBottomHolder);
        txt_front = findViewById(R.id.txt_front);
        txt_back = findViewById(R.id.txt_back);
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

        applyBottomHolder = findViewById(R.id.applyBottomHolder);
        textBottomHolder = findViewById(R.id.textBottomHolder);
        WritingBottomHolder = findViewById(R.id.WritingBottomHolder);
        above_bottom_linear = findViewById(R.id.above_bottom_linear);
        bottom_linear = findViewById(R.id.bottom_linear);
        linear_color_style = findViewById(R.id.linear_color_style);
        colorsBottomHolder = findViewById(R.id.colorsBottomHolder);
        sizeBottomHolder = findViewById(R.id.sizeBottomHolder);
        bar_color_holder = findViewById(R.id.bar_color_holder);
        bar_font_size_holder = findViewById(R.id.bar_font_size_holder);
        relative_for_text = findViewById(R.id.relative_for_text);
        fontBottomHolder = findViewById(R.id.fontBottomHolder);
        SizeFlashBottomHolder = findViewById(R.id.SizeFlashBottomHolder);
        hueSeekBar = findViewById(R.id.hueSeekBar);
        font_sizeSeekBar = findViewById(R.id.font_sizeSeekBar);

        cover_view_text_front = findViewById(R.id.cover_view_text_front);
        cover_view_text_back = findViewById(R.id.cover_view_text_back);
        font_recycler_view = findViewById(R.id.font_recycler_view);

        txt_front.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_front.setRawInputType(InputType.TYPE_CLASS_TEXT);

        txt_back.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_back.setRawInputType(InputType.TYPE_CLASS_TEXT);

        ViewHelper.hideKeyboard(FlashMemoryActivity.this);


        initFrontView();
        initBackView();
        txtProperties();
        ViewHelper.hideKeyboard(FlashMemoryActivity.this);
        cover_front = (CoverView) componentView_front.getComponentView(R.id.cover);
        accessoriesView_front = (AccessoriesView) componentView_front.getComponentView(R.id.accessories);
        objectView_front = (ObjectView) componentView_front.getComponentView(R.id.component);
        objectView_back = (ObjectView) componentView_back.getComponentView(R.id.component);
        cover_back = (CoverView) componentView_back.getComponentView(R.id.cover);

        objectView_back.setBackgroundColor(Color.parseColor("#cf878787"));
        objectView_front.setBackgroundColor(Color.parseColor("#cf878787"));
        initView();
        getIntentData();

        initFontRecyclerView();
        writingOnCover();
        clickOnTextBottomHolder();
        clickCancelBottomHolder();
        clickColorsBottomHolder();
        clickFontsBottomHolder();
        clickSizesBottomHolder();
        clickFontBottomHolder();
        font_sizeSeekBar.setProgress(12);
        hueSeekBar.setProgress(0);
        updateNow();
        updateColorNow();
        applyColorTextMethod();
        applyFontSizeMethod();
        clickSizeFlashmemory();
        showMokupData();
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {


    }


    void getIntentData(){
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
        if (intent.hasExtra("BUNDLE")){
            Bundle args = intent.getBundleExtra("BUNDLE");
            flashMemorySizeModelList = (ArrayList<FlashMemorySizeModel>) args.getSerializable("FlashMemoryArrayList");
//           Log.e("flashMemorySizeModelListOmar",flashMemorySizeModelList+"");
        }

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
//        measureImageDimensions();
//        if (flashType.equals("front")){
//            initFrontView();
//        }else if (flashType.equals("back")){
//            initBackView();
//        }
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.flash_memory);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
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

    public void txtProperties(){
        txt_front.setVisibility(View.GONE);
        txt_back.setVisibility(View.GONE);
//        txt_back.setBackgroundColor(Color.TRANSPARENT);
        ViewHelper.hideKeyboard(this);
        txt_front.setOnTouchListener(this);
        txt_back.setOnTouchListener(this);
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
            styleNameFront="";
        }else if (flashType.equals("back")){
            objectView_back.setData(null);
            Log.e("position->>",position+"");
            if (position!=colorPositionback) {
                Log.e("colorPosotion",colorPositionback+"");
                objectView_back.setBackgroundColor(Color.parseColor("#"+General_Color.get(position)));
                colorPositionback=position;
            }
            colorNameback=General_Color.get(position);
            styleNameBack="";
        }

    }

    @Override
    public void onStyleItemsClickFromAdapter(int position) {
        colorOrStyle=2;
        if (flashType.equals("front")){
            objectView_front.setBackgroundColor(0x00000000);
//            String imgName="http://23.236.154.106:8063/UploadedImages/"+General_Style.get(position);
            String imgName=Constants.BASE_URL+"UploadedImages/" +General_Style.get(position);
            Log.e("colorPosotion",stylePostionFront+"");
            Log.e("position",position+"");
            if (position!=stylePostionFront) {
                objectView_front.setData(imgName);
                stylePostionFront=position;
                colorNameFront="";
            }
            styleNameFront=General_Style.get(position);
        }else if (flashType.equals("back")){
            objectView_back.setBackgroundColor(0x00000000);
//            String imgName="http://23.236.154.106:8063/UploadedImages/"+General_Style.get(position);
            String imgName=Constants.BASE_URL+"UploadedImages/"+General_Style.get(position);
            Log.e("colorPosotion",stylePostionback+"");
            Log.e("position",position+"");
            if (position!=stylePostionback) {
                objectView_back.setData(imgName);
                stylePostionback=position;
            }
            styleNameBack=General_Style.get(position);
            colorNameback="";
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
        if (flashMemorySizeModelList!=null) {
            if (!flashMemorySizeModelList.isEmpty())
                flashMemorySizeModelList.clear();
        }

//        if (publicBitmapsModels!=null){
//            publicBitmapsModels.clear();
//        }
//        if (MobileOrderRequest.getInstance().getOrderDetils()!=null){
//            MobileOrderRequest.getInstance().getOrderDetils().clear();
//        }

        ViewHelper.hideKeyboard(FlashMemoryActivity.this);
        finish();
    }


    public void replaceCOverImage(View view) {
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


            mobileBitmapFront = loadBitmapFromView(componentView_front);
            currentBitmapFront=loadBitmapFromCover(cover_front);

            mobileBitmapFront = RoundedBitmapClass.getRoundedCornerBitmap(mobileBitmapFront,(int) getResources().getDimension(R.dimen.radius_flash));
            Log.e("FrontBitmaps",currentBitmapFront+"    "+mobileBitmapFront);

            if (mobileBitmapFront!=null){
                saveTempBitmapForFront(mobileBitmapFront);
            }
            if (currentBitmapFront!=null){
                saveTempBitmapForCoverFront(currentBitmapFront);
            }

            BitmapFlashHelper.getInstance().setBitmapFront(mobileBitmapFront);
            BitmapFlashHelper.getInstance().setBitmapFrontCover(currentBitmapFront);

//            PublicBitmapsModel.getInstance().setBitmapFront(mobileBitmapFront);
//
//            publicBitmapsModels.add(new PublicBitmapsModel(1+"",prod_Id+"",priceIn,priceOut,PublicBitmapsModel.getInstance().getBitmapFront()));

            HelperFlashRequest.getInstance().getIds().add(Integer.parseInt(prod_Id));
            HelperFlashRequest.getInstance().getFlashCoverBitmapsFront().add(currentBitmapFront);
            HelperFlashRequest.getInstance().getFlashscreenShootBitmapsFront().add(mobileBitmapFront);
            if (!txt_front.getText().toString().isEmpty()) {
                txt_front.setVisibility(View.VISIBLE);
            }
        }
    }
    public void createBackBitmaps(){
        if (cover_back.getDrawable() != null) {
            mobileBitmapBack = loadBitmapFromView(componentView_back);
            currentBitmapBack=loadBitmapFromCover(cover_back);
            mobileBitmapBack = RoundedBitmapClass.getRoundedCornerBitmap(mobileBitmapBack,(int) getResources().getDimension(R.dimen.radius_flash));
            Log.e("BackBitmaps",currentBitmapBack+"    "+mobileBitmapBack);

            if (mobileBitmapBack!=null){
                saveTempBitmapForBack(mobileBitmapBack);
            }
            if (currentBitmapBack!=null){
                saveTempBitmapForBackCover(currentBitmapBack);
            }

            BitmapFlashHelper.getInstance().setBitmapBack(mobileBitmapBack);
            BitmapFlashHelper.getInstance().setBitmapBackCover(currentBitmapBack);
            HelperFlashRequest.getInstance().getFlashCoverBitmapsBack().add(currentBitmapBack);
            HelperFlashRequest.getInstance().getFlashscreenShootBitmapsBack().add(mobileBitmapBack);

            if (!txt_back.getText().toString().isEmpty()) {
                txt_back.setVisibility(View.VISIBLE);
            }
        }
    }


    public void submit(View view) {
        if (cover_front.getDrawable() != null ) {
            if (cover_back.getDrawable() != null){

                showConfirmationDialog(FlashMemoryActivity.this);

            }else {
                hideLoaderDialog();
                Toast.makeText(this, getString(R.string.choose_bg_back), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideLoaderDialog();
            Toast.makeText(this, getString(R.string.choose_bg_back), Toast.LENGTH_SHORT).show();
        }
    }



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
        Canvas canvas = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(canvas);
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
                initColorRecyclerView();
                image_front.setImageResource(R.drawable.ic_front_flash);
                loadImage(imgBack,image_back);
                flashType="front";
                componentView_front.setVisibility(View.VISIBLE);
//                txt_front.setVisibility(View.INVISIBLE);
                componentView_back.setVisibility(View.INVISIBLE);
                linear_color_style.setVisibility(View.VISIBLE);
                above_bottom_linear.setVisibility(View.GONE);
                Log.e("flashType",flashType);
//                txt_back.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void clickImageback(){

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColorRecyclerView();

                image_back.setImageResource(R.drawable.ic_back_flash);
                loadImage(imgFront,image_front);
                flashType="back";
                componentView_front.setVisibility(View.INVISIBLE);
//                txt_front.setVisibility(View.INVISIBLE);
                componentView_back.setVisibility(View.VISIBLE);
//                txt_back.setVisibility(View.INVISIBLE);
                linear_color_style.setVisibility(View.VISIBLE);
                above_bottom_linear.setVisibility(View.GONE);
                Log.e("flashType",flashType);
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
    }

    public void sendBitmapImage() {
        //                txt_front.setFocusable(false);
//                txt_back.setFocusable(false);

        txt_front.setBackground(null);
        txt_back.setBackground(null);

        if (flashType.equals("back")){
//                    txt_back.setVisibility(View.VISIBLE);
//                    txt_front.setVisibility(View.VISIBLE);
//                    componentView_back.setVisibility(View.VISIBLE);
//                    componentView_front.setVisibility(View.VISIBLE);
            createBackBitmaps();
            createFrontBitmaps();
        }else {
//                    txt_front.setVisibility(View.VISIBLE);
//                    txt_back.setVisibility(View.VISIBLE);
//                    componentView_front.setVisibility(View.VISIBLE);
//                    componentView_back.setVisibility(View.VISIBLE);
            createFrontBitmaps();
            createBackBitmaps();
        }

        Log.e("BitmapFlashHelper1", BitmapFlashHelper.getInstance().getBitmapFront() + "");


        if (colorOrStyleType==1) {
            colorBgMobileAdapter = new ColorStyleFlashAdapter(colorOrStyleType, this, General_Color, this);
        }else {
            colorBgMobileAdapter = new ColorStyleFlashAdapter(colorOrStyleType, this, General_Style, this);
        }
        color_recyclerview.setAdapter(colorBgMobileAdapter);

        if (fileForFront!=null) {
            mPresenter.returnUploadedImageForFront(photosFront, fileForFront);
        }
    }

    void navigateTo(){
        if (flashType.equals("front")){
            componentView_back.setVisibility(View.INVISIBLE);
//            txt_back.setVisibility(View.INVISIBLE);
            componentView_front.setVisibility(View.VISIBLE);
//            txt_front.setVisibility(View.VISIBLE);
            image_front.setImageResource(R.drawable.ic_front_flash);
            loadImage(imgBack,image_back);
        }else {
            componentView_back.setVisibility(View.VISIBLE);
//            txt_back.setVisibility(View.VISIBLE);
            componentView_front.setVisibility(View.INVISIBLE);
//            txt_front.setVisibility(View.INVISIBLE);
            image_back.setImageResource(R.drawable.ic_back_flash);
            loadImage(imgFront,image_front);

        }
        linear_color_style.setVisibility(View.VISIBLE);
        above_bottom_linear.setVisibility(View.GONE);
        if (colorBgMobileAdapter!=null) {
            color_recyclerview.setAdapter(colorBgMobileAdapter);
        }

        MobileOrderRequest.getInstance().getOrderDetils().add(orderDetilsBean);
        PublicBitmapsModel.getInstance().setBitmapFront(mobileBitmapFront);
        publicBitmapsModels.add(new PublicBitmapsModel(1+"",prod_Id+"",priceIn,priceOut,PublicBitmapsModel.getInstance().getBitmapFront()));

        if (btnConfirmType==1) {
//        Intent intent = new Intent(this, ShippingFlashMemoryActivity.class);
            Intent intent = new Intent(this, PublicShippingActivity.class);

//        intent.putExtra("frontFilename", frontFilename+"");
//        intent.putExtra("frontCoverFileName", frontCoverFileName+"");
//        intent.putExtra("backFileilename", backFileilename+"");
//        intent.putExtra("backCoverFileilename", backCoverFileilename+"");

            intent.putExtra("styleNameFront", styleNameFront + "");
            intent.putExtra("styleNameBack", styleNameBack + "");
            intent.putExtra("colorNameFront", colorNameFront + "");
            intent.putExtra("colorNameback", colorNameback + "");
            intent.putExtra("photosFront", photosFront.get(0) + "");
            intent.putExtra("photosBack", photosBack.get(0) + "");
            intent.putExtra("prod_Id", prod_Id + "");
            intent.putExtra("priceIn", priceIn + "");
            intent.putExtra("priceOut", priceOut + "");
            Log.e("photosFront", photosFront.get(0) + "");
            Log.e("photosBack", photosBack.get(0) + "");
            startActivity(intent);
            finish();
        }else if (btnConfirmType==2){
            onBackPressed();
        }
    }


    void initFontRecyclerView(){
        fontTypeModelList();
        Log.e("fontTypeModelList",fontTypeModelList+" "+fontTypeModelList.size());
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        font_recycler_view.setLayoutManager(manager);
        font_recycler_view.setItemAnimator(new DefaultItemAnimator());
        font_recycler_view.setNestedScrollingEnabled(false);
        font_recycler_view.setHasFixedSize(true);
        font_recycler_view.scrollToPosition(0);
        fontTypeAdapter = new FontTypeAdapter(this, fontTypeModelList, this);
        font_recycler_view.setAdapter(fontTypeAdapter);
    }
    public List<FontTypeModel> fontTypeModelList(){
        fontTypeModelList.add(new FontTypeModel("GOTHIC_2.TTF",R.drawable.ic_remove));
        fontTypeModelList.add(new FontTypeModel("GOTHICB_1.TTF",R.drawable.ic_replace));
        fontTypeModelList.add(new FontTypeModel("GOTHICBI_2.TTF",R.drawable.ic_refresh_black_24dp));
        fontTypeModelList.add(new FontTypeModel("GOTHICI_2.TTF",R.drawable.ic_edit_profile));
        fontTypeModelList.add(new FontTypeModel("android_insomnia_regular.ttf",R.drawable.ic_chekbox_active));
        fontTypeModelList.add(new FontTypeModel("doridrobot.ttf",R.drawable.ic_chekbox_unactive));
        return fontTypeModelList;
    }


    void applyFontSizeMethod(){
        font_sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateNow();
            }
        });
    }
    public void updateNow(){
        if (flashType.equals("front")) {
            seekbar_value = font_sizeSeekBar.getProgress();
            Log.e("seekbarff", seekbar_value + "");
            txt_front.setTextSize(seekbar_value + 1);
            imagesBean.setText_size(String.valueOf(seekbar_value+1));
//            textSize = String.valueOf(seekbar_value + 1);
        }else {
            imagesBean2.setText_size(String.valueOf(seekbar_value+1));
            seekbar_value = font_sizeSeekBar.getProgress();
            Log.e("seekbarff", seekbar_value + "");
            txt_back.setTextSize(seekbar_value + 1);
//            textSize = String.valueOf(seekbar_value + 1);
        }
    }


    void applyColorTextMethod(){
        hueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                updateColorNow();
            }
        });
    }

    public void updateColorNow(){
        seekbar_color_value=hueSeekBar.getProgress();
        if (flashType.equals("front")) {
            for (int i = 0; i < ColorUtils.colorList().length; i++) {
                Log.e("lengthh", ColorUtils.colorList().length + "\n");
                if (seekbar_color_value == i) {
                    Log.e("i", seekbar_color_value + "\n" + i);
                    txt_front.setTextColor(Color.parseColor("#" + ColorUtils.colorList()[seekbar_color_value]));
                    imagesBean.setText_color("#" + ColorUtils.colorList()[seekbar_color_value]);
//                    textColor = ColorUtils.colorList()[seekbar_color_value];
                    break;
                }
            }
        }else {
            for (int i = 0; i < ColorUtils.colorList().length; i++) {
                Log.e("lengthh", ColorUtils.colorList().length + "\n");
                if (seekbar_color_value == i) {
                    Log.e("i", seekbar_color_value + "\n" + i);
                    txt_back.setTextColor(Color.parseColor("#" + ColorUtils.colorList()[seekbar_color_value]));
                    imagesBean2.setText_color("#" + ColorUtils.colorList()[seekbar_color_value]);
//                    textColor = ColorUtils.colorList()[seekbar_color_value];
                    break;
                }
            }
        }
    }

    public void clickOnTextBottomHolder() {
        textBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashType.equals("front")) {
                    linear_color_style.setVisibility(View.GONE);
                    above_bottom_linear.setVisibility(View.VISIBLE);
                    txt_front.setVisibility(View.VISIBLE);
//                txt_front.setBackground(getResources().getDrawable(R.drawable.edit_text_background));
                    cover_view_text_front.setVisibility(View.GONE);
                }else {
                    linear_color_style.setVisibility(View.GONE);
                    above_bottom_linear.setVisibility(View.VISIBLE);
                    txt_back.setVisibility(View.VISIBLE);
//                    txt_back.setBackground(getResources().getDrawable(R.drawable.edit_text_background));
                    cover_view_text_back.setVisibility(View.GONE);
                }
            }
        });
    }

    public void clickCancelBottomHolder(){
        applyBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashType.equals("front")) {
                    txt_front.setVisibility(View.VISIBLE);
//                    txt_front.setBackground(null);
//                    cover_view_text_front.setVisibility(View.VISIBLE);
                    linear_color_style.setVisibility(View.VISIBLE);
                    above_bottom_linear.setVisibility(View.GONE);
//                    txt_front.buildDrawingCache();
//                    cover_view_text_front.setImageBitmap(txt_front.getDrawingCache());
                }else {
                    txt_back.setVisibility(View.VISIBLE);
//                    txt_back.setBackground(null);
//                    cover_view_text_back.setVisibility(View.VISIBLE);
                    linear_color_style.setVisibility(View.VISIBLE);
                    above_bottom_linear.setVisibility(View.GONE);
//                    txt_back.buildDrawingCache();
//                    cover_view_text_back.setImageBitmap(txt_back.getDrawingCache());
                }
            }
        });
    }
    public void clickColorsBottomHolder(){
        colorsBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_color_holder.setVisibility(View.VISIBLE);
                bar_font_size_holder.setVisibility(View.GONE);
            }
        });
    }
    public void clickFontsBottomHolder(){
        colorsBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_color_holder.setVisibility(View.VISIBLE);
                bar_font_size_holder.setVisibility(View.GONE);
                font_recycler_view.setVisibility(View.GONE);
            }
        });
    }

    public void clickSizesBottomHolder(){
        sizeBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar_color_holder.setVisibility(View.GONE);
                bar_font_size_holder.setVisibility(View.VISIBLE);
                font_recycler_view.setVisibility(View.GONE);
            }
        });
    }

    public void clickFontBottomHolder(){
        fontBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                initFontRecyclerView();
                bar_color_holder.setVisibility(View.GONE);
                bar_font_size_holder.setVisibility(View.GONE);
                font_recycler_view.setVisibility(View.VISIBLE);
            }
        });
    }


    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        txt_front.invalidate();
        txt_back.invalidate();
        return true;
    }

    public void writingOnCover(){
        WritingBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextClick();
            }
        });
    }

    public void editTextClick(){
        if (flashType.equals("front")) {
//            txt_front.setFocusable(true);
//            txt_front.setBackgroundColor(Color.TRANSPARENT);
            if (txt_front.requestFocus()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(txt_front, InputMethodManager.SHOW_IMPLICIT);
            }
//            txt_front.requestFocus();
//            txt_front.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        }else {
//            txt_back.setFocusable(true);
//            txt_back.setBackgroundColor(Color.TRANSPARENT);
            if (txt_back.requestFocus()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(txt_back, InputMethodManager.SHOW_IMPLICIT);
            }
//            txt_back.requestFocus();
//            txt_back.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void onFontTypeItemClick(FontTypeModel item, int position) {
        String path="fonts/"+item.getFontName();
        Typeface face = Typeface.createFromAsset(getAssets(),
                path);
//        textFont=item.getFontName();
        if (flashType.equals("front")) {
            txt_front.setTypeface(face);
            imagesBean.setText_font(face+"");
        }else {
            txt_back.setTypeface(face);
            imagesBean2.setText_font(face+"");
        }
    }

    public void clickSizeFlashmemory(){
        SizeFlashBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSizeRecyclerView();
            }
        });
    }
    private void initSizeRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        color_recyclerview.setLayoutManager(manager);
        color_recyclerview.setItemAnimator(new DefaultItemAnimator());
        color_recyclerview.setNestedScrollingEnabled(false);
        color_recyclerview.setHasFixedSize(true);
        color_recyclerview.scrollToPosition(0);
        flashMemorySizeAdapter = new FlashMemorySizeAdapter(this, flashMemorySizeModelList, this);
        color_recyclerview.setAdapter(flashMemorySizeAdapter);
    }

    @Override
    public void onSizeItemsClickFromAdapter(int position, String priceFlashIn, String priceFlashOut,List<FlashMemorySizeModel> flashMemorySizeModelList2) {
        priceIn=flashMemorySizeModelList2.get(position).getPriceIn();
        priceOut=flashMemorySizeModelList2.get(position).getPriceOut();
//        Toast.makeText(this, position+"\n"+priceIn+"\n"+priceOut+"", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void returnUploadedImageForFront(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFont", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            String imageFrontName = imageUploadResponseModel.getFileName();
            imagesBean.setDesignerFlag(0);
            imagesBean.setDesignerID("0");
            imagesBean.setStyle(styleNameFront);
            imagesBean.setColor(colorNameFront);


            imagesBean.setText(txt_front.getText().toString());
            imagesBean.setPrintscreenImg(imageFrontName);
            orderDetilsBean.setMainImage(imageFrontName);
            if (imageFrontName !=null){
                imagesBean.setPrintscreenImg(imageFrontName);
            }
            orderDetilsBean.setProductID(Integer.parseInt(prod_Id));
            orderDetilsBean.setImages(images);
            if (fileForCoverFront!=null){
                mPresenter.returnUploadedImageForCoverFront(photosFront, fileForCoverFront);
            }else {
                Toast.makeText(this, "هناك خطا ما", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void returnUploadedImageForCoverFront(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            String imageCoverFrontName = imageUploadResponseModel.getFileName();
            if (imageCoverFrontName !=null){
                imagesBean.setImage(imageCoverFrontName);
            }
            images.add(imagesBean);
            mPresenter.returnUploadedImageForBack(photosFront, fileForBack);
        }
    }

    @Override
    public void returnUploadedImageForBack(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromBack", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            String imageBackName = imageUploadResponseModel.getFileName();
//            imagesBean2=new MobileOrderRequest.OrderDetilsBean.ImagesBean();
            imagesBean2.setDesignerFlag(1);
            imagesBean2.setDesignerID("1");
            imagesBean2.setStyle(styleNameBack);
            imagesBean2.setColor(colorNameback);
            imagesBean2.setText(txt_back.getText().toString());
            if (imageBackName !=null){
                imagesBean2.setPrintscreenImg(imageBackName);
            }
            orderDetilsBean.setImages(images);
            mPresenter.returnUploadedImageForCoverBack(photosFront, fileForCoverBack);
        }
    }

    @Override
    public void returnUploadedImageForCoverBack(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            String imageCoverBackName = imageUploadResponseModel.getFileName();
            if (imageCoverBackName !=null){
                imagesBean2.setImage(imageCoverBackName);
            }
            images.add(imagesBean2);

//            MobileOrderRequest.getInstance().getOrderDetils().add(orderDetilsBean);

            navigateTo();


        }
    }


    public void saveTempBitmapForFront(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForFront(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForCoverFront(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForCoverFront(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForBack(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForBack(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForBackCover(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForCoverBack(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public File saveImageForFront(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images1");
        myDir.mkdirs();

        String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        filename = timeStamp + ".jpg";

        fileForFront = new File(myDir, filename);

        if (fileForFront.exists()) fileForFront.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForFront);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("fileGetting", fileForFront + "");
//            saveImageForMobileForSending(finalBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForFront;
    }

    public File saveImageForCoverFront(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images2");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename2 = timeStamp + ".jpg";

        fileForCoverFront = new File(myDir, filename2);

        if (fileForCoverFront.exists()) fileForCoverFront.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCoverFront);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForCoverFront + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCoverFront;
    }


    public File saveImageForBack(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images3");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename3 = timeStamp + ".jpg";

        fileForBack = new File(myDir, filename3);

        if (fileForBack.exists()) fileForBack.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForBack);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForBack + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForBack;
    }


    public File saveImageForCoverBack(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images4");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename4 = timeStamp + ".jpg";

        fileForCoverBack = new File(myDir, filename4);

        if (fileForCoverBack.exists()) fileForCoverBack.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCoverBack);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForCoverBack + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCoverBack;
    }


    @Override
    public void showLoadingInner() {
        showLoaderDialog();
    }

    @Override
    public void hideLoadingInner() {
        hideLoaderDialog();
    }

    private void showConfirmationDialog(Context context) {
        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.complete_shipping_popup);
        mDialog.setCancelable(false);


        mDialog.findViewById(R.id.complete_shipping_btn).setOnClickListener(v -> {
            try {
                btnConfirmType=1;

                sendBitmapImage();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.continue_shopping_btn).setOnClickListener(v -> {
            try {
                btnConfirmType=2;

                sendBitmapImage();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.close_popup).setOnClickListener(view -> {
            mDialog.dismiss();
        });
        if(!((Activity) context).isFinishing())
        {
            mDialog.show();
        }
    }

    public void showMokupData(){
        sampleMokupBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidDialogTools.getMokupSamples(FlashMemoryActivity.this, new VerifyDialogInterfaceForText() {
                    @Override
                    public void onConfirmClick(String string) {

                    }

                    @Override
                    public void onCancelClick(String string) {

                    }
                }, getMokupData());
            }
        });
    }

    public List<SampleMokupItems> getMokupData(){

        List<SampleMokupItems>sampleMokupItemsList=new ArrayList<>();
        SampleMokupItems sampleMokupItems1=new SampleMokupItems();
        SampleMokupItems sampleMokupItems2=new SampleMokupItems();
        SampleMokupItems sampleMokupItems3=new SampleMokupItems();
        sampleMokupItems1.setMokupImage(R.drawable.tshirt_test);
        sampleMokupItems2.setMokupImage(R.drawable.test);
        sampleMokupItems3.setMokupImage(R.drawable.demo);
        sampleMokupItemsList.add(sampleMokupItems1);
        sampleMokupItemsList.add(sampleMokupItems2);
        sampleMokupItemsList.add(sampleMokupItems3);

        return sampleMokupItemsList;
    }

}

