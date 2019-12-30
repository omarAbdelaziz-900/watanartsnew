package com.WattanArt.ui.EditDesign;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.ui.ShippingT_Shirt.ShippingT_ShirtActivity;
import com.WattanArt.ui.base.BaseActivity;

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


    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;

    T_ShirtAdapter t_shirtAdapter;
    ArrayList<T_ShirtModel> t_shirtModels;

    T_ShirtColorAdapter t_shirtColorAdapter;
    ArrayList<T_ShirtModel> t_shirtColorModels;

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

    UserData userData;
//    String imgFront ="http://23.236.154.106:8063/img/flashf.png";
//    String imgBack ="http://23.236.154.106:8063/img/flashb.png";
    String imgFront ="";
    String imgBack ="";
    String t_ShirtType;
    ArrayList<String> photosFront,photosBack;
    CardView image_front;
    CardView image_back;
    CustomeTextViewBold mToolbarTitleTextView;
    ImageView mToolbarBackImageView;

    ScrollView scrollview;

    Bitmap currentBitmapFront, mobileBitmapFront;
    Bitmap currentBitmapBack, mobileBitmapBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_design);

        ButterKnife.bind(this);
        initRecyclerView();
        initColorRecyclerView();

        pickFromGalleryAction();

        mToolbarBackImageView=findViewById(R.id.toolbar_image_view);
        mToolbarTitleTextView=findViewById(R.id.toolbar_tv_title);
        scrollview = findViewById(R.id.scrollview);
        image_front = findViewById(R.id.image_front);
        image_back = findViewById(R.id.image_back);
        componentView_back = findViewById(R.id.componentView_back);
        accessoriesView_back = (AccessoriesView) componentView_back.getComponentView(R.id.accessories);
        cover_back = (CoverView) componentView_back.getComponentView(R.id.cover);
        objectView_back = (ObjectView) componentView_back.getComponentView(R.id.component);

        componentView_front = findViewById(R.id.componentView_front);
        accessoriesView_front = (AccessoriesView) componentView_front.getComponentView(R.id.accessories);
        cover_front = (CoverView) componentView_front.getComponentView(R.id.cover);
        objectView_front = (ObjectView) componentView_front.getComponentView(R.id.component);

        componentView_front.setVisibility(View.VISIBLE);
        componentView_back.setVisibility(View.GONE);

        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.edit_design);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });

        objectView_back.setBackgroundResource(R.drawable.tshirt_test);
        objectView_front.setBackgroundResource(R.drawable.tshirt_test);
//        objectView_front.setBackgroundColor(Color.parseColor("#cf878787"));

        t_ShirtType="front";
        initFrontView();
        initBackView();
        clickImageFront();
        clickImageback();

        scrollview.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollview.setFocusable(true);
        scrollview.setFocusableInTouchMode(true);
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    void initFrontView() {
        Log.e("hhhshhs",getResources().getDimension(R.dimen.flash_width)+"");
        dimensionData_front = new DimensionData(
                (int) getResources().getDimension(R.dimen.flash_width),
                (int)getResources().getDimension(R.dimen.flash_height),
                (int) getResources().getDimension(R.dimen.t_shirt_width)-15,
                (int)getResources().getDimension(R.dimen.t_shirt_height),
                (int) getResources().getDimension(R.dimen.radius_flash),
                (int) getResources().getDimension(R.dimen.flash_width),
                (int)getResources().getDimension(R.dimen.flash_height), 0, 0);
        componentView_front.initUrlData(dimensionData_front, new Pair<>( imgFront,imgFront));

    }

    void initBackView() {
        dimensionData_back  = new DimensionData((int) getResources().getDimension(R.dimen.flash_width),
                (int)getResources().getDimension(R.dimen.flash_height),
                (int) getResources().getDimension(R.dimen.t_shirt_width)-15,
                (int)getResources().getDimension(R.dimen.t_shirt_height),
                (int) getResources().getDimension(R.dimen.radius_flash),
                (int) getResources().getDimension(R.dimen.flash_width),
                (int)getResources().getDimension(R.dimen.flash_height), 0, 0);
        componentView_back.initUrlData(dimensionData_back, new Pair<>( imgBack,imgBack));

    }

    public void clickImageFront(){
        image_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColorRecyclerView();
                t_ShirtType="front";
                componentView_front.setVisibility(View.VISIBLE);
                componentView_back.setVisibility(View.GONE);
            }
        });
    }

    public void clickImageback(){

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initColorRecyclerView();
                t_ShirtType="back";
                componentView_front.setVisibility(View.GONE);
                componentView_back.setVisibility(View.VISIBLE);
            }
        });
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
                if (t_ShirtType.equals("front")) {
                    photosFront = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photosFront == null || photosFront.isEmpty()) return;
                    cover_front.setData(photosFront.get(0));
                }else if (t_ShirtType.equals("back")){
                    photosBack = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photosBack == null || photosBack.isEmpty()) return;
                    cover_back.setData(photosBack.get(0));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
    public void submit(View view) {
        if (cover_front.getDrawable() != null ) {
            if (cover_back.getDrawable() != null){
                componentView_front.setVisibility(View.VISIBLE);
                componentView_back.setVisibility(View.VISIBLE);

                createFrontBitmaps();
                createBackBitmaps();
                Log.e("T_ShirtBitmapHelper", T_ShirtBitmapHelper.getInstance().getBitmapFront() + "");
                sendBitmapImage();
            }else {
                hideLoaderDialog();
                Toast.makeText(this, getString(R.string.choose_bg_back), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideLoaderDialog();
            Toast.makeText(this, getString(R.string.choose_bg_back), Toast.LENGTH_SHORT).show();
        }
    }

    public void createFrontBitmaps(){
        if (cover_front.getDrawable() != null) {
            mobileBitmapFront = loadBitmapFromView(componentView_front);
            currentBitmapFront=loadBitmapFromCover(cover_front);
            Log.e("FrontBitmaps",currentBitmapFront+"    "+mobileBitmapFront);
            T_ShirtBitmapHelper.getInstance().setBitmapFront(mobileBitmapFront);
            T_ShirtBitmapHelper.getInstance().setBitmapFrontCover(currentBitmapFront);
        }
    }
    public void createBackBitmaps(){
        if (cover_back.getDrawable() != null) {
            mobileBitmapBack = loadBitmapFromView(componentView_back);
            currentBitmapBack=loadBitmapFromCover(cover_back);
            Log.e("BackBitmaps",currentBitmapBack+"    "+mobileBitmapBack);
            T_ShirtBitmapHelper.getInstance().setBitmapBack(mobileBitmapBack);
            T_ShirtBitmapHelper.getInstance().setBitmapBackCover(currentBitmapBack);
        }
    }


    public static Bitmap loadBitmapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
//        Canvas canvas = new Canvas(b);
////        canvas.drawColor(Color.WHITE);
//        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
//        v.draw(canvas);
        return b;
    }

    public static Bitmap loadBitmapFromCover(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        return b;
    }

    public void sendBitmapImage() {
//        if (flashType.equals("front")){
//            componentView_back.setVisibility(View.INVISIBLE);
//            componentView_front.setVisibility(View.VISIBLE);
//            image_front.setImageResource(R.drawable.ic_front_flash);
//            loadImage(imgBack,image_back);
//        }else {
//            componentView_back.setVisibility(View.VISIBLE);
//            componentView_front.setVisibility(View.INVISIBLE);
//            image_back.setImageResource(R.drawable.ic_back_flash);
//            loadImage(imgFront,image_front);
//        }
        Intent intent = new Intent(this, ShippingT_ShirtActivity.class);
        startActivity(intent);
    }
}
