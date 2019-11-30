package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.Category.ColorBgMobileAdapter;
import com.WattanArt.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;


public class ComponentActivity extends BaseActivity implements MobileMvpView ,ColorBgMobileAdapter.ItemListenerOfItems{

    CustomeTextViewBold pick_from_gallery_tv;
    CustomeTextViewBold complete_tv;
    ImageView toolbar_image_view;
    final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 102;

    //    CoverView cover;
    CoverView cover;

    ImageCaseComponent component;
    ObjectView objectView;
    ImageView view_image;
    DimensionData dimensionData;
    AccessoriesView accessoriesView;
    // Fields
    private String TAG = this.getClass().getSimpleName();
    ArrayList<String> photos;
    Bitmap currentBitmap , mobileBitmap;

    @Inject
    MobileMvpPresenter<MobileMvpView> mPresenter;

    @BindView(R.id.styleBottomHolder)
    RelativeLayout styleBottomHolder;

    @BindView(R.id.rotateBottomHolder)
    RelativeLayout rotateBottomHolder;

    @BindView(R.id.replaceBottomHolder)
    RelativeLayout replaceBottomHolder;

    @BindView(R.id.colorBottomHolder)
    RelativeLayout colorBottomHolder;

    @BindView(R.id.color_recyclerview)
    RecyclerView color_recyclerview;

    @BindView(R.id.linear_container)
    View linear_container;



    @BindView(R.id.toolbar_tv_title)
    public CustomeTextViewBold mToolbarTitleTextView;

    double centreX ,centreY ,a;

    String mobileImage;
    ArrayList<Object>  General_Style ,General_Color;
    ColorBgMobileAdapter colorBgMobileAdapter;
    int colorOrStyleType;
    File fileGeting;

    int actW,actH;
    float scaleX,scaleY;
    Matrix mat;

    int colorPosition=-1 ,stylePostion=-1;

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
        getData();
        actionColorOrStyle();
        pickFromGalleryAction();
        measureImageDimensions();
    }

    void initView() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTitleTextView.setText(getString(R.string.mobile_cover));
        toolbar_image_view = findViewById(R.id.toolbar_image_view);
        pick_from_gallery_tv = findViewById(R.id.pick_from_gallery_tv);
        complete_tv = findViewById(R.id.order_design_tv);
        component = findViewById(R.id.componentView);
        objectView = findViewById(R.id.component);
        view_image = findViewById(R.id.view_image);


        dimensionData = new DimensionData(600, 1420, 590,
                1410, 0, 600,
                1420, 0, 0);

        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9, R.drawable.huaweiy9_2));
        cover = (CoverView) component.getComponentView(R.id.cover);
        accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
//        accessoriesView.setData(R.drawable.huaweiy9_2);



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
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
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

        if (cover.getDrawable()!=null) {
            Bitmap bitmap = ((BitmapDrawable) cover.getDrawable()).getBitmap();

            currentBitmap = Bitmap.createBitmap(bitmap,
                    0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), cover.getImageMatrix(), true);

            mobileBitmap = loadBitmapFromView(component);


            float rorationAngle = cover.getCurrentAngle();
            float zoom = cover.getCurrentScale();

            saveTempBitmap(mobileBitmap);

            Log.i(TAG, String.format("Size (%d , %d) allMobile Size (%d , %d) current Rottion Angle %f and Zoom %f", currentBitmap.getWidth(), currentBitmap.getHeight()
                    , mobileBitmap.getWidth(), mobileBitmap.getHeight(), rorationAngle, zoom));

            getBitmab();
        }
    }

    void getBitmab() {
        Log.e("photo",photos.get(0));
            File imgFile = new File(photos.get(0));
        if (imgFile.exists()) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imgFile), options);
                bitmap=currentBitmap;
                if (bitmap != null) {
//                    mPresenter.returnUploadedImage(photos,imgFile);
                    mPresenter.returnUploadedImage(photos,fileGeting);
                }
            }
    }

    @Override
    public void returnUploadedImage(ImageUploadResponseModel imageUploadResponseModel) {
        Log.e("imageName",imageUploadResponseModel.getFileName());
    }

    private void initColorRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        color_recyclerview.setLayoutManager(manager);
        color_recyclerview.setItemAnimator(new DefaultItemAnimator());
        color_recyclerview.setNestedScrollingEnabled(false);
        color_recyclerview.setHasFixedSize(true);
        color_recyclerview.scrollToPosition(0);
        if (colorOrStyleType==1) {
            colorBgMobileAdapter = new ColorBgMobileAdapter(colorOrStyleType, this, General_Color, this);
        }else {
            colorBgMobileAdapter = new ColorBgMobileAdapter(colorOrStyleType, this, General_Style, this);
        }
        color_recyclerview.setAdapter(colorBgMobileAdapter);
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

    @Override
    public void onColorItemsClickFromAdapter( int position) {
        cover.layout(0, 0, linear_container.getLayoutParams().width, linear_container.getLayoutParams().height);
        accessoriesView.setData(null);
        Log.e("position->>",position+"");
        if (position!=colorPosition) {
            Log.e("colorPosotion",colorPosition+"");
            accessoriesView.setBackgroundColor(Color.parseColor("#"+General_Color.get(position)));
            colorPosition=position;
        }
    }

    @Override
    public void onStyleItemsClickFromAdapter(int position) {
        cover.setBackgroundColor(0x00000000);
        cover.layout(0, 0, linear_container.getLayoutParams().width, linear_container.getLayoutParams().height);
        String imgName="http://23.236.154.106:8063/UploadedImages/"+General_Style.get(position);
        Log.e("colorPosotion",stylePostion+"");
        Log.e("position",position+"");
        if (position!=stylePostion) {
            accessoriesView.setData(imgName);
            stylePostion=position;
        }
    }



    public void getData() {
        Intent intent=getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        if (intent.hasExtra("mobileImage")){
            mobileImage = intent.getStringExtra("mobileImage");
            Log.e("mobileImage",mobileImage+"");
        }

        if (args!=null) {
            General_Style = (ArrayList<Object>) args.getSerializable("General_Style");
            Log.e("General_Style", General_Style + "");
            General_Color = (ArrayList<Object>) args.getSerializable("General_Color");
            Log.e("General_Color", General_Color + "");
        }

        color_recyclerview.setVisibility(View.GONE);
    }

    public void actionColorOrStyle(){
        colorBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorBgMobileAdapter!=null){
                    colorBgMobileAdapter.notifyDataSetChanged();
                }
                color_recyclerview.setVisibility(View.VISIBLE);
                colorOrStyleType=1;
                if (General_Color!=null){
                    initColorRecyclerView();
                }
            }
        });

        styleBottomHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorBgMobileAdapter!=null){
                    colorBgMobileAdapter.notifyDataSetChanged();
                }
                color_recyclerview.setVisibility(View.VISIBLE);
                colorOrStyleType=2;
                if (General_Style!=null){
                    initColorRecyclerView();
                }
            }
        });
    }


    public void saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap);
        }else{
            //prompt the user or do something
        }
    }

    private File saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Shutta_"+ timeStamp +".jpg";

         fileGeting = new File(myDir, fname);
        if (fileGeting.exists()) fileGeting.delete ();
        try {
            FileOutputStream out = new FileOutputStream(fileGeting);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.e("kljkjkjk",fileGeting+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileGeting;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
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
                actW = Math.round(coverWidth * scaleX);
                actH = Math.round(coverHeight * scaleY);

                Log.e("DBG", "["+coverWidth+","+coverHeight+"] -> ["+actW+","+actH+"] & scales: x="+scaleX+" y="+scaleY);

                float wScale = (float)actW/(float)coverWidth;
                float hScale = (float)actH/(float)coverHeight;
                float scale = Math.min(wScale, hScale);

                Log.e("calculateScaleImage",""+scale);


                float aspect_ratio = (float)coverWidth / (float)coverHeight ;
                float aspect_ratio2 = (float)actW / (float)actH ;

                Log.e("calculateAspectRatio",""+aspect_ratio+" && "+aspect_ratio2);


                centreX=cover.getX() + cover.getWidth()  / 2;
                centreY=cover.getY() + cover.getHeight() / 2;

                double tx = scaleX - centreX ;
                double ty = scaleY - centreY;

                double t_length = Math.sqrt(tx*tx + ty*ty);
                a = Math.acos(ty / t_length);

                Log.e("calculateRotate",""+a);


//                Bitmap bMap = BitmapFactory.decodeFile(selectedImagePath);
//                cover.setImageBitmap(bMap);
                 mat = cover.getImageMatrix();

//                RectF drawableRect = new RectF(0, 0, actW, actH);
//                RectF viewRect = new RectF(0, 0, cover.getWidth(), cover.getHeight());
//                mat.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL);
                Log.e("matrixCalc",""+mat);
//                cover.setImageMatrix(mat);

                return false;
            }
        });
    }

    public Bitmap convertUrlToBitmap(String imgName){
        Bitmap bitmap = null;
        String drawableRes="http://23.236.154.106:8063/UploadedImages/"+imgName;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(drawableRes);
            bitmap=BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch (IOException e) {
            //Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }
}
