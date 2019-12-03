package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.ViewHelper;
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
import com.WattanArt.ui.Category.ColorBgMobileAdapter;
import com.WattanArt.ui.ShippingForMobile.ShippingMobileActivity;
import com.WattanArt.ui.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;


public class ComponentActivity extends BaseActivity implements MobileMvpView, ColorBgMobileAdapter.ItemListenerOfItems {

    CustomeTextViewBold pick_from_gallery_tv;
    CustomeTextViewBold complete_tv;
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
    Bitmap currentBitmap, mobileBitmap;

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

    @BindView(R.id.toolbar_image_view)
    ImageView mToolbarBackImageView;

    @BindView(R.id.submit)
    CustomeTextView submit;

    @BindView(R.id.toolbar_tv_title)
    CustomeTextViewBold mToolbarTitleTextView;


    double centreX, centreY, a;

    String mobileImage, mobileType;
    ArrayList<Object> General_Style, General_Color;
    ColorBgMobileAdapter colorBgMobileAdapter;
    int colorOrStyleType;

    File fileForMobile, fileForCover;

    int actW, actH;
    float scaleX, scaleY;
    Matrix mat;

    int colorPosition = -1, stylePostion = -1;

    float[] pts;

    String mobileImageUrl;

    Intent intent;

    String filename, filename2;

    String mobileName, coverName;


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
        mToolbarBackImageView.setVisibility(View.VISIBLE);
        mToolbarTitleTextView.setText(R.string.mobile_cover);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    void initView() {
//        setSupportActionBar(findViewById(R.id.toolbar));
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbarTitleTextView.setText(getString(R.string.mobile_cover));
        pick_from_gallery_tv = findViewById(R.id.pick_from_gallery_tv);
        complete_tv = findViewById(R.id.order_design_tv);
        component = findViewById(R.id.componentView);
        view_image = findViewById(R.id.view_image);

        intent = getIntent();
        if (intent.hasExtra("mobileType")) {
            mobileType = intent.getStringExtra("mobileType");
            Log.e("mobileType", mobileType + "");
        }
        if (mobileType != null) {
            checkisMobileOrTablet();
        }

//        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9, R.drawable.huaweiy9_2));

        cover = (CoverView) component.getComponentView(R.id.cover);
        accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);

        objectView = (ObjectView) component.getComponentView(R.id.component);
//        accessoriesView.setData(R.drawable.huaweiy9_2);
    }

    public void checkisMobileOrTablet() {

        if (mobileType.equals(Constants.MOBLIE_TYPE)) {
            dimensionData = new DimensionData(700, 1150, 690,
                    1150, 60, 700,
                    1150, 0, 0);

        } else if (mobileType.equals(Constants.TAbLET_TYPE)) {
            dimensionData = new DimensionData(1000, 1000, 1000 - 10,
                    1000, 60, 1000,
                    1000, 0, 0);
        }
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
//            ViewHelper.showProgressDialog(this);
            mobileName = imageUploadResponseModel.getFileName();
            mPresenter.returnUploadedImageForCover(photos, fileForCover);
        }
    }

    @Override
    public void returnUploadedImageForCover(ImageUploadResponseModel imageUploadResponseModel) {
//        ViewHelper.hideProgressDialog();
//        hideLoading();
        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
        if (imageUploadResponseModel.getState() == 5) {
            coverName = imageUploadResponseModel.getFileName();
            sendBitmapImage();
        }
    }

    @Override
    public void onColorItemsClickFromAdapter(int position) {
//        cover.layout(0, 0, linear_container.getLayoutParams().width, linear_container.getLayoutParams().height);
        objectView.setData(null);
        Log.e("position->>", position + "");
        if (position != colorPosition) {
            Log.e("colorPosotion", colorPosition + "");
            objectView.setBackgroundColor(Color.parseColor("#" + General_Color.get(position)));
            colorPosition = position;
        }
    }

    @Override
    public void onStyleItemsClickFromAdapter(int position) {
        objectView.setBackgroundColor(0x00000000);
//        cover.layout(0, 0, linear_container.getLayoutParams().width, linear_container.getLayoutParams().height);
        String imgName = "http://23.236.154.106:8063/UploadedImages/" + General_Style.get(position);
        Log.e("colorPosotion", stylePostion + "");
        Log.e("position", position + "");
        if (position != stylePostion) {
//            accessoriesView.setData(imgName);
            objectView.setData(imgName);
            stylePostion = position;
        }
    }


    public void getData() {

        Bundle args = intent.getBundleExtra("BUNDLE");
        if (intent.hasExtra("mobileImage")) {
            mobileImage = intent.getStringExtra("mobileImage");
            mobileImageUrl = "http://23.236.154.106:8063/UploadedImages/" + mobileImage;
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


    public void measureImageDimensions() {
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

                Log.e("DBG", "[" + coverWidth + "," + coverHeight + "] -> [" + actW + "," + actH + "] & scales: x=" + scaleX + " y=" + scaleY);

                float wScale = (float) actW / (float) coverWidth;
                float hScale = (float) actH / (float) coverHeight;
                float scale = Math.min(wScale, hScale);

                Log.e("calculateScaleImage", "" + scale);


                float aspect_ratio = (float) coverWidth / (float) coverHeight;
                float aspect_ratio2 = (float) actW / (float) actH;

                Log.e("calculateAspectRatio", "" + aspect_ratio + " && " + aspect_ratio2);


                centreX = cover.getX() + cover.getWidth() / 2;
                centreY = cover.getY() + cover.getHeight() / 2;

                double tx = scaleX - centreX;
                double ty = scaleY - centreY;

                double t_length = Math.sqrt(tx * tx + ty * ty);
                a = Math.acos(ty / t_length);

                Log.e("calculateRotate", "" + a);


//                Bitmap bMap = BitmapFactory.decodeFile(selectedImagePath);
//                cover.setImageBitmap(bMap);
                mat = cover.getImageMatrix();

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
        styleBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        colorBottomHolder.setBackground(getDrawable(R.drawable.background_unselected));
        replaceBottomHolder.setBackground(getDrawable(R.drawable.background_selected));
        pickFromGallery();
    }

    public void colorBottomClick() {
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

    public void submit(View view) {
        if (cover.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) cover.getDrawable()).getBitmap();
            currentBitmap = Bitmap.createBitmap(bitmap,
                    0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), cover.getImageMatrix(), true);

            mobileBitmap = loadBitmapFromView(component);
            // this the cropped image from cover
            Bitmap coverImageCroped = loadBitmapFromView(cover);

            float rorationAngle = cover.getCurrentAngle();
            float zoom = cover.getCurrentScale();


//            saveTempBitmapForMobile(BitmapUtils.scaleDown(mobileBitmap, 1000, true));
            saveTempBitmapForMobile(mobileBitmap);
            saveTempBitmapForCover(currentBitmap);

            Log.i(TAG, String.format("Size (%d , %d) allMobile Size (%d , %d) current Rottion Angle %f and Zoom %f", currentBitmap.getWidth(), currentBitmap.getHeight()
                    , mobileBitmap.getWidth(), mobileBitmap.getHeight(), rorationAngle, zoom));

//            sendBitmapImage();

            if (photos != null && fileForMobile != null) {
                mPresenter.returnUploadedImageForMobile(photos, fileForMobile);
            } else {
                Toast.makeText(this, "قم باختيار الخلفيه", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "قم باختيار الخلفيه", Toast.LENGTH_SHORT).show();
        }
    }


    public void saveTempBitmapForMobile(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            saveImageForMobile(bitmap);
        } else {
            //prompt the user or do something
        }
    }

//    @Override
//    public void showLoadingFragment() {
//        super.showLoadingFragment();
//    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
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
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename = timeStamp + ".jpg";

        fileForMobile = new File(myDir, filename);

        if (fileForMobile.exists()) fileForMobile.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForMobile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.e("fileGetting", fileForMobile + "");
//            saveImageForMobileForSending(finalBitmap);
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
//         filename = "Shutta_"+ timeStamp +".jpg";
        filename = timeStamp + ".jpg";

        fileForCover = new File(myDir, filename);

        if (fileForCover.exists()) fileForCover.delete();
        try {
            FileOutputStream out = new FileOutputStream(fileForCover);
//            FileOutputStream out = this.openFileOutput(filename , Context.MODE_PRIVATE);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
//            mobileBitmap.recycle();
            Log.e("fileGetting", fileForCover + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileForCover;
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
//        Paint q = new Paint(Paint.ANTI_ALIAS_FLAG);

        canvas.drawColor(Color.TRANSPARENT);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
//        v.setLayerType(LAYER_TYPE_HARDWARE, q);
        v.draw(canvas);
        return b;
    }

    public void imageToSend() {
        try {
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            mobileBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            mobileBitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBitmapImage() {
        initColorRecyclerView();
        if (colorBgMobileAdapter != null) {
            color_recyclerview.setAdapter(colorBgMobileAdapter);
        }
        imageToSend();
        Intent intent = new Intent(this, ShippingMobileActivity.class);
        intent.putExtra("image", filename);
        intent.putExtra("mobileName", mobileName);
        intent.putExtra("coverName", coverName);
        startActivity(intent);
    }
}
