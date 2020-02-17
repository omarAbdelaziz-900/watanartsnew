package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.AndroidDialogTools;
import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.RoundedBitmapClass;
import com.WattanArt.Utils.ViewHelper;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.VerifyDialogInterfaceForText;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.ColorUtils;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.MyEditText;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.SampleMokupItems;
import com.WattanArt.ui.Category.ColorBgMobileAdapter;
import com.WattanArt.ui.EditImage.ColorMatrixUtils;
import com.WattanArt.ui.PublicShipping.PublicBitmapsModel;
import com.WattanArt.ui.PublicShipping.PublicShippingActivity;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.ShippingForMobile.ShippingMobileActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

import static com.WattanArt.ui.PublicShipping.PublicShippingActivity.publicBitmapsModels;


public class ComponentActivity extends BaseActivity implements MobileMvpView, ColorBgMobileAdapter.ItemListenerOfItems ,View
        .OnTouchListener ,FontTypeAdapter.ItemListener{

    CustomeTextViewBold complete_tv;
    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;

    //    CoverView cover;
    CoverView cover;

    ImageCaseComponent component;
    ObjectView objectView;

    DimensionData dimensionData;
    AccessoriesView accessoriesView;
    // Fields
    private String TAG = this.getClass().getSimpleName();
    ArrayList<String> photos;
    Bitmap currentBitmap, mobileBitmap;
    Uri imageUri;
    @Inject
    MobileMvpPresenter<MobileMvpView> mPresenter;

    @BindView(R.id.styleBottomHolder)
    RelativeLayout styleBottomHolder;

    @BindView(R.id.applyBottomHolder)
    RelativeLayout applyBottomHolder;

    @BindView(R.id.textBottomHolder)
    RelativeLayout textBottomHolder;

    RelativeLayout relative_for_text,fontBottomHolder,sizeBottomHolder,colorsBottomHolder,above_bottom_linear,WritingBottomHolder;
    LinearLayout bar_font_size_holder, bar_color_holder,bottom_linear;

    @BindView(R.id.rotateBottomHolder)
    RelativeLayout rotateBottomHolder;

    @BindView(R.id.replaceBottomHolder)
    RelativeLayout replaceBottomHolder;

    @BindView(R.id.colorBottomHolder)
    RelativeLayout colorBottomHolder;

    @BindView(R.id.color_recyclerview)
    RecyclerView color_recyclerview;


    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;

    @BindView(R.id.submit)
    CustomeTextView submit;

    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;
    String mobileImage, mobileType,mobileId ,priceIn ,priceOut ;
    ArrayList<Object> General_Style, General_Color;
    ColorBgMobileAdapter colorBgMobileAdapter;
    int colorOrStyleType;
    int colorPosition = -1, stylePostion = -1;
    String mobileImageUrl;
    Intent intent;
    String colorSend="";
    String styleSend="";
    int colorOrStyle=0;
    SeekBar hueSeekBar,font_sizeSeekBar;
    MyEditText txt_mobile;
//    LinearLayout mLinearLayout;
    EditText editText;
    View viewCreatedEdutText;
    int seekbar_value,seekbar_color_value;

    LinearLayout linear_color_style;
    ArrayList<String> colorsArrayList;
    private int _xDelta;
    private int _yDelta;

    private List<EditText> editTextList = new ArrayList<EditText>();

    FontTypeAdapter fontTypeAdapter;
    List< FontTypeModel> fontTypeModelList;
    RecyclerView font_recycler_view;
    CoverView cover_view_text;

    String text,textColor,textFont,textSize;
    String filename, filename2;
    File fileForMobile, fileForCover;

    List<MobileOrderRequest.OrderDetilsBean.ImagesBean> image=new ArrayList<>();
    MobileOrderRequest.OrderDetilsBean orderDetilsBean=new MobileOrderRequest.OrderDetilsBean();
    MobileOrderRequest.OrderDetilsBean.ImagesBean imagesBean=new MobileOrderRequest.OrderDetilsBean.ImagesBean();

    int btnConfirmType=0;
    RelativeLayout sampleMokupBottomHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_layout_view);
        ButterKnife.bind(this);
        setUpActivityOrFragmentRequirment();

    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            mPresenter.onAttach(this);
        }
        initView();

        ViewHelper.hideKeyboard(ComponentActivity.this);
        getData();
        actionColorOrStyle();
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.mobile_cover);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });


//        addEditView();
//        for (int i=0;i<mLinearLayout.getChildCount();i++) {
//            mLinearLayout.getChildAt(i).setOnTouchListener(this);
//        }
    }


    void initView() {
        colorsArrayList = new ArrayList<>();
        fontTypeModelList = new ArrayList<>();
        sampleMokupBottomHolder = findViewById(R.id.sampleMokupBottomHolder);
        cover_view_text = findViewById(R.id.cover_view_text);
        font_recycler_view = findViewById(R.id.font_recycler_view);
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
        txt_mobile = findViewById(R.id.txt_mobile);
        hueSeekBar = findViewById(R.id.hueSeekBar);
        font_sizeSeekBar = findViewById(R.id.font_sizeSeekBar);
//        mLinearLayout = new LinearLayout(ComponentActivity.this);
//        mLinearLayout = (LinearLayout) findViewById(R.id.linear_mobile);

        font_sizeSeekBar.setProgress(12);
        hueSeekBar.setProgress(0);
        updateNow();
        updateColorNow();
        complete_tv = findViewById(R.id.order_design_tv);
        component = findViewById(R.id.componentView);

        txt_mobile.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txt_mobile.setRawInputType(InputType.TYPE_CLASS_TEXT);

        applyFontSizeMethod();
        applyColorTextMethod();
        intent = getIntent();
        if (intent.hasExtra("mobileType")) {
            mobileType = intent.getStringExtra("mobileType");
            Log.e("mobileType", mobileType + "");
        }
        if (intent.hasExtra("mobileId")) {
            mobileId = intent.getStringExtra("mobileId");
            Log.e("mobileType", mobileType + "");
        }
        if (intent.hasExtra("priceIn")) {
            priceIn = intent.getStringExtra("priceIn");
            Log.e("priceIn", priceIn + "");
        }
        if (intent.hasExtra("priceOut")) {
            priceOut = intent.getStringExtra("priceOut");
            Log.e("priceOut", priceOut + "");
        }
        if (mobileType != null) {
            checkisMobileOrTablet();
        }
        initFontRecyclerView();
        cover = (CoverView) component.getComponentView(R.id.cover);
        accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
        objectView = (ObjectView) component.getComponentView(R.id.component);
        txtProperties();
        doneOfTextClick();
        writingOnCover();

        showMokupData();
    }

    public void checkisMobileOrTablet() {

        if (mobileType.equals(Constants.MOBLIE_TYPE)) {

            dimensionData = new DimensionData(
                    (int) getResources().getDimension(R.dimen.mobile_width),
                    (int) getResources().getDimension(R.dimen.mobile_height),
                    (int) getResources().getDimension(R.dimen.mobile_width)-15,
                    (int) getResources().getDimension(R.dimen.mobile_height),
                    (int) getResources().getDimension(R.dimen.radius),
                    (int) getResources().getDimension(R.dimen.mobile_width),
                    (int) getResources().getDimension(R.dimen.mobile_height),
                    0,
                    0);

        } else if (mobileType.equals(Constants.TAbLET_TYPE)) {
            dimensionData = new DimensionData((int) getResources().getDimension(R.dimen.tablet_width)
                    , (int) getResources().getDimension(R.dimen.tablet_height),
                    (int) getResources().getDimension(R.dimen.tablet_width)-15,
                    (int) getResources().getDimension(R.dimen.tablet_height),
                    (int) getResources().getDimension(R.dimen.radius),
                    (int) getResources().getDimension(R.dimen.tablet_width),
                    (int) getResources().getDimension(R.dimen.tablet_height),
                    0,
                    0);
        }

        clickOnTextBottomHolder();
        clickCancelBottomHolder();
        clickColorsBottomHolder();
        clickFontsBottomHolder();
        clickSizesBottomHolder();
        clickFontBottomHolder();
        ViewHelper.hideKeyboard(this);

    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
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
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos == null || photos.isEmpty()) return;
                cover.setData(photos.get(0));


                File file = new File(photos.get(0));
                imageUri = Uri.fromFile(file);
                Log.e("imageUri",imageUri+"");
                Log.e("imageUri",imageUri.getPath()+"");
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
        if (colorOrStyleType == 1) {
            colorBgMobileAdapter = new ColorBgMobileAdapter(colorOrStyleType, this, General_Color, this);
        } else {
            colorBgMobileAdapter = new ColorBgMobileAdapter(colorOrStyleType, this, General_Style, this);
        }
        color_recyclerview.setAdapter(colorBgMobileAdapter);
    }

    @Override
    public void returnUploadedImageForMobile(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromMobile", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            String mobileName = imageUploadResponseModel.getFileName();
            orderDetilsBean.setMainImage(mobileName);
            if (mobileName != null) {
                imagesBean.setPrintscreenImg(mobileName);
            }
            mPresenter.returnUploadedImageForCover(photos, fileForCover);
        }
    }

    @Override
    public void returnUploadedImageForCover(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
           String coverName = imageUploadResponseModel.getFileName();
            if (coverName != null) {
                imagesBean.setImage(coverName);
            }
//            sendBitmapImage();
            navigateTo();
            showConfirmationDialog(ComponentActivity.this);
        }
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void saveTempBitmapForMobile(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForMobile(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public void saveTempBitmapForCover(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForCover(bitmap);
        } else {
            //prompt the user or do something
        }
    }

    public File saveImageForMobile(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        filename = timeStamp + ".jpg";
        fileForMobile = new File(myDir, filename);
        if (fileForMobile.exists()) fileForMobile.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForMobile);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("fileGetting", fileForMobile + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForMobile;
    }

    public File saveImageForCover(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images2");
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        filename2 = timeStamp + ".jpg";
        fileForCover = new File(myDir, filename2);
        if (fileForCover.exists()) fileForCover.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCover);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("fileGetting", fileForCover + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCover;
    }

    public void returnBitmapFromHelper(Bitmap mobile ,Bitmap cover) {
        if (mobile != null) {
            saveTempBitmapForMobile(mobile);
        }
        if (cover != null) {
            saveTempBitmapForCover(cover);
        }
    }

    @Override
    public void showLoadingInner() {
        showLoaderDialog();
//        showLoading();
    }

    @Override
    public void hideLoadingInner() {
        hideLoaderDialog();
    }

    @Override
    public void onColorItemsClickFromAdapter(int position) {
        colorOrStyle=1;
//        cover.layout(0, 0, linear_container.getLayoutParams().width, linear_container.getLayoutParams().height);
        objectView.setData(null);
        Log.e("position->>", position + "");
        if (position != colorPosition) {
            Log.e("colorPosotion", colorPosition + "");
            objectView.setBackgroundColor(Color.parseColor("#" + General_Color.get(position)));
            colorPosition = position;
        }
        if (General_Color.get(position)!=null)
            colorSend=General_Color.get(position)+"";
        styleSend="";
    }

    @Override
    public void onStyleItemsClickFromAdapter(int position) {
        colorOrStyle=2;
        objectView.setBackgroundColor(0x00000000);
//        cover.layout(0, 0, linear_container.getLayoutParams().width, linear_container.getLayoutParams().height);
//        String imgName = "http://23.236.154.106:8063/UploadedImages/" + General_Style.get(position);
        String imgName = Constants.BASE_URL+"UploadedImages/" + General_Style.get(position);
        Log.e("colorPosotion", stylePostion + "");
        Log.e("position", position + "");
        if (position != stylePostion) {
//            accessoriesView.setData(imgName);
            objectView.setData(imgName);
            stylePostion = position;
        }
        if (General_Style.get(position)!=null)
            styleSend=General_Style.get(position)+"";
        colorSend="";
    }


    public void getData() {

        Bundle args = intent.getBundleExtra("BUNDLE");
        if (intent.hasExtra("mobileImage")) {
            mobileImage = intent.getStringExtra("mobileImage");
//            mobileImageUrl = "http://23.236.154.106:8063/UploadedImages/" + mobileImage;
            mobileImageUrl =Constants.BASE_URL+"UploadedImages/" + mobileImage;
            component.initUrlData(dimensionData, new Pair<>(mobileImageUrl, mobileImageUrl));
            objectView.setBackgroundColor(Color.parseColor("#cf878787"));
            Log.e("mobileImage", mobileImage + "");

        }


        if (args != null) {
            General_Style = (ArrayList<Object>) args.getSerializable("General_Style");
            Log.e("General_Style", General_Style + "");
            General_Color = (ArrayList<Object>) args.getSerializable("General_Color");
            Log.e("General_Color", General_Color + "");
        }

        color_recyclerview.setVisibility(View.VISIBLE);
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_selected_bordered));
        initColorRecyclerView();
    }

    public void actionColorOrStyle() {
        colorBottomHolder.setOnClickListener(v -> colorBottomClick());
        styleBottomHolder.setOnClickListener(v -> styleBottomClick());
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (General_Color != null) {
            if (!General_Color.isEmpty())
                General_Color.clear();
        }
        if (General_Style != null) {
            if (!General_Style.isEmpty())
                General_Style.clear();
        }
//        if (publicBitmapsModels!=null){
//            publicBitmapsModels.clear();
//        }
//        if (MobileOrderRequest.getInstance().getOrderDetils()!=null){
//            MobileOrderRequest.getInstance().getOrderDetils().clear();
//        }

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void replaceCOverImage(View view) {
        pickFromGallery();
    }

    public void colorBottomClick() {
//        colorOrStyle=1;
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        colorBottomHolder.setBackground(getDrawable(R.drawable.background_selected));
        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        if (colorBgMobileAdapter != null) {
            colorBgMobileAdapter.notifyDataSetChanged();
        }
        color_recyclerview.setVisibility(View.VISIBLE);
        colorOrStyleType = 1;
        if (General_Color != null) {
            initColorRecyclerView();
        }
    }

    public void styleBottomClick() {
//        colorOrStyle=2;
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_selected));
        colorBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));

        if (colorBgMobileAdapter != null) {
            colorBgMobileAdapter.notifyDataSetChanged();
        }
        color_recyclerview.setVisibility(View.VISIBLE);
        colorOrStyleType = 2;
        if (General_Style != null) {
            initColorRecyclerView();
        }
    }

    public static Bitmap loadBitmapFromView2(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        Canvas canvas = new Canvas(b);
        v.layout(0, 0, v.getWidth(), v.getHeight());
        v.draw(canvas);
        return b;
    }




    public void submit(View view) {
        if (cover.getDrawable() != null) {

        showConfirmationDialog(ComponentActivity.this);
        } else {
            hideLoaderDialog();
            Toast.makeText(this, getString(R.string.choose_bg), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showLoading() {
//        super.showLoading();
    }

    @Override
    public void hideLoading() {
//        super.hideLoading();
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

    @Override
    public void onFontTypeItemClick(FontTypeModel item, int position) {
        String path="fonts/"+item.getFontName();
        Typeface face = Typeface.createFromAsset(getAssets(),
                path);
//        if (editText!=null)
//            for(int m = 0; m < editTextList.size(); ++m){
//                if (editText.getId()==editTextList.get(m).getId()) {
                textFont=item.getFontName();
                    txt_mobile.setTypeface(face);
//                }
//            }
    }

    public void sendBitmapImage() {

        txt_mobile.setBackground(null);
        if (BitmapMobileHelper.getInstance().getBitmapMobile()!=null){
            BitmapMobileHelper.getInstance().setBitmapMobile(null);
            BitmapMobileHelper.getInstance().clearInstance();
        }
        mobileBitmap = loadBitmapFromView2(component);
        currentBitmap = loadBitmapFromCover(cover);

//        txt_mobile.setVisibility(View.GONE);
//        cover_view_text.setVisibility(View.VISIBLE);
//        txt_mobile.buildDrawingCache();
//        cover_view_text.setImageBitmap(txt_mobile.getDrawingCache());

        mobileBitmap= RoundedBitmapClass.getRoundedCornerBitmap(mobileBitmap,(int) getResources().getDimension(R.dimen.radius)+60);
        returnBitmapFromHelper(mobileBitmap,currentBitmap);

        if (mobileBitmap != null && currentBitmap != null) {
            BitmapMobileHelper.getInstance().setBitmapMobile(mobileBitmap);
            BitmapMobileHelper.getInstance().setBitmapCover(currentBitmap);
            PublicBitmapsModel.getInstance().setBitmapFront(mobileBitmap);
        }

//        publicBitmapsModels.add(new PublicBitmapsModel(1+"",mobileId,priceIn,priceOut,PublicBitmapsModel.getInstance().getBitmapFront()));

        initColorRecyclerView();
        if (colorBgMobileAdapter != null) {
            color_recyclerview.setAdapter(colorBgMobileAdapter);
        }
        if (fontTypeAdapter != null) {
            font_recycler_view.setAdapter(fontTypeAdapter);
        }




        if (fileForMobile!=null) {
            mPresenter.returnUploadedImageForMobile(photos, fileForMobile);
        }else {
            Toast.makeText(this, "هناك خطا ما", Toast.LENGTH_SHORT).show();
        }
    }


    void navigateTo(){
        initColorRecyclerView();
        if (colorBgMobileAdapter != null) {
            color_recyclerview.setAdapter(colorBgMobileAdapter);
        }
        if (fontTypeAdapter != null) {
            font_recycler_view.setAdapter(fontTypeAdapter);
        }
        orderDetilsBean.setProductID(Integer.parseInt(mobileId));
        imagesBean.setText(txt_mobile.getText().toString());
        imagesBean.setText_size(textSize);
        imagesBean.setText_font(textFont);
        imagesBean.setText_color(textColor);
        imagesBean.setStyle(colorSend);
        imagesBean.setColor(styleSend);
        imagesBean.setDesignerID("0");
        imagesBean.setDesignerFlag(0);
        image.add(imagesBean);
        orderDetilsBean.setImages(image);


        publicBitmapsModels.add(new PublicBitmapsModel(1+"",mobileId,priceIn,priceOut,PublicBitmapsModel.getInstance().getBitmapFront()));
        MobileOrderRequest.getInstance().getOrderDetils().add(orderDetilsBean);

        if (btnConfirmType==1) {
            Intent intent = new Intent(this, PublicShippingActivity.class);
//        Intent intent = new Intent(this, ShippingMobileActivity.class);
            intent.putExtra("color", colorSend + "");
            intent.putExtra("style", styleSend + "");
            intent.putExtra("photos", photos.get(0) + "");
            intent.putExtra("mobileId", mobileId + "");
            intent.putExtra("priceIn", priceIn + "");
            intent.putExtra("priceOut", priceOut + "");
            intent.putExtra("text", txt_mobile.getText().toString() + "");
            intent.putExtra("textColor", textColor + "");
            intent.putExtra("textSize", textSize + "");
            intent.putExtra("textFont", textFont + "");
            Log.e("photos", photos.get(0) + "");
            startActivity(intent);
            finish();
        }else if (btnConfirmType==2){
            onBackPressed();
        }
    }


    public Bitmap cropImage(){
//        HighlightView highlightView=new HighlightView(cover);
//        highlightView.getCropRect();
        Bitmap b = Bitmap.createBitmap(cover.getWidth(), cover.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        canvas.drawColor(Color.TRANSPARENT);
        cover.layout(0, 0, cover.getLayoutParams().width, cover.getLayoutParams().height);
        cover.draw(canvas);
//        view_image.setImageBitmap(b);
        return b;
    }

    public static Bitmap loadBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        Canvas canvas = new Canvas(b);
//        canvas.drawColor(Color.WHITE);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(canvas);
        return b;

//        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(b);
////        Paint q = new Paint(Paint.ANTI_ALIAS_FLAG);
//        canvas.drawColor(Color.TRANSPARENT);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
////        v.setLayerType(LAYER_TYPE_HARDWARE, q);
//        v.draw(canvas);
//        return b;
    }

    public static Bitmap loadBitmapFromCover(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        return b;
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
        seekbar_value=font_sizeSeekBar.getProgress();
        Log.e("seekbarff",seekbar_value+"");
//        if (editText!=null)
//            for(int m = 0; m < mLinearLayout.getChildCount(); ++m){
//                if (mLinearLayout.getChildAt(m).hasFocus()) {
        txt_mobile.setTextSize(seekbar_value + 1);
        textSize=String.valueOf(seekbar_value + 1);
//                }
//            }
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
        for (int i = 0; i< ColorUtils.colorList().length; i++){
            Log.e("lengthh",ColorUtils.colorList().length+"\n");
            if (seekbar_color_value==i){
                Log.e("i",seekbar_color_value+"\n"+i);
//                if (editText!=null)
//                    for(int m = 0; m < mLinearLayout.getChildCount(); ++m){
//                        if (mLinearLayout.getChildAt(m).hasFocus()) {
                txt_mobile.setTextColor(Color.parseColor("#" + ColorUtils.colorList()[seekbar_color_value]));
                textColor = "#" +ColorUtils.colorList()[seekbar_color_value];
//                        }
//                    }

                break;
            }
        }
    }


    public void clickOnTextBottomHolder(){
        textBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_color_style.setVisibility(View.GONE);
                above_bottom_linear.setVisibility(View.VISIBLE);
//                addEditView();
                txt_mobile.setVisibility(View.VISIBLE);
//                txt_mobile.setBackground(getResources().getDrawable(R.drawable.edit_text_background));
                txt_mobile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_white_transparent_50));
                cover_view_text.setVisibility(View.GONE);
            }
        });
    }

//    public void clickOnTextBottomHolder(){
//        textBottomHolder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                linear_color_style.setVisibility(View.GONE);
////                above_bottom_linear.setVisibility(View.VISIBLE);
////                txt_mobile.setVisibility(View.VISIBLE);
////                cover_view_text.setVisibility(View.GONE);
//                textBottomHolder.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        txt_mobile.setVisibility(View.VISIBLE);
//                        linear_color_style.setVisibility(View.GONE);
//                        above_bottom_linear.setVisibility(View.VISIBLE);
//                        cover_view_text.setVisibility(View.GONE);
//
//                        AndroidDialogTools.getTextOfUser(ComponentActivity.this, new VerifyDialogInterfaceForText() {
//                            @Override
//                            public void onConfirmClick(String string) {
//                                txt_mobile.setVisibility(View.VISIBLE);
//                                cover_view_text.setVisibility(View.VISIBLE);
//                                txt_mobile.setText(string);
//                                txt_mobile.buildDrawingCache();
//                                cover_view_text.setImageBitmap(txt_mobile.getDrawingCache());
//                                txt_mobile.setBackground(null);
//                                Log.e("ggggg",""+string);
//                                ViewHelper.hideKeyboard(ComponentActivity.this);
//                            }
//
//                            @Override
//                            public void onCancelClick(String string) {
//                                txt_mobile.setVisibility(View.VISIBLE);
//                                cover_view_text.setVisibility(View.VISIBLE);
////                                txt_mobile.setText(string);
//                                txt_mobile.buildDrawingCache();
//                                cover_view_text.setImageBitmap(txt_mobile.getDrawingCache());
//                                txt_mobile.setBackground(null);
//                                Log.e("ggggg",""+string);
//                                ViewHelper.hideKeyboard(ComponentActivity.this);
//                                Log.e("cccccc","ccccc");
//                            }
//                        });
//                    }
//                });
//            }
//        });
//    }

    public void clickCancelBottomHolder(){
        applyBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_mobile.setVisibility(View.VISIBLE);
//                txt_mobile.setBackground(null);
                cover_view_text.setVisibility(View.GONE);
                linear_color_style.setVisibility(View.VISIBLE);
                above_bottom_linear.setVisibility(View.GONE);

//                txt_mobile.buildDrawingCache();
//                cover_view_text.setImageBitmap(txt_mobile.getDrawingCache());
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
    public void txtProperties(){
        txt_mobile.setVisibility(View.GONE);
        txt_mobile.setBackgroundColor(Color.TRANSPARENT);
        ViewHelper.hideKeyboard(this);
//        if (editText!=null)
//            action();
            txt_mobile.setOnTouchListener(this);
    }

    private void addEditView() {
        editText = new EditText(ComponentActivity.this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.mobile_width),
//                (int)getResources().getDimension(R.dimen.mobile_width));
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setId(editText.generateViewId());
        editTextList.add(editText);
        Log.e("editTextId", editText.getId()+"");
        for (int i=0;i<editTextList.size();i++) {
            Log.e("editTextListId", editTextList.get(i).getId()+"");
        }
//        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setMaxLines(10);
//        mLinearLayout.addView(editText);

//        editText.setFocusable(false);
//        editText.setCursorVisible(false);

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
        txt_mobile.invalidate();
        return true;
    }



    public void editTextClick(){
//        txt_mobile.setFocusable(true);
//        txt_mobile.setBackgroundColor(Color.TRANSPARENT);
//        txt_mobile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_white_transparent_50));
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(txt_mobile, InputMethodManager.SHOW_IMPLICIT);
//        txt_mobile.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        txt_mobile.setRawInputType(InputType.TYPE_CLASS_TEXT);
//        txt_mobile.requestFocus();

        if (txt_mobile.requestFocus()) {
            txt_mobile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_white_transparent_50));
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(txt_mobile, InputMethodManager.SHOW_IMPLICIT);
            txt_mobile.setImeOptions(EditorInfo.IME_ACTION_DONE);
            txt_mobile.setRawInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public void doneOfTextClick(){
//            for(int i = 0; i < editTextList.size(); ++i){
//                if (txt_mobile.getId()==editTextList.get(i).getId()) {
                    txt_mobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                                txt_mobile.setFocusable(false);
//                                txt_mobile.setBackgroundColor(Color.TRANSPARENT);
                                ViewHelper.hideKeyboard(ComponentActivity.this);
                            }
                            return false;
                        }
                    });
//                }}
    }

    public void writingOnCover(){
        WritingBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextClick();
            }
        });
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
                AndroidDialogTools.getMokupSamples(ComponentActivity.this, new VerifyDialogInterfaceForText() {
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
