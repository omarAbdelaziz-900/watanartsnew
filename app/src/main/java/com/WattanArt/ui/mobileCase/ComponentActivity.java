package com.WattanArt.ui.mobileCase;

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
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WattanArt.Dagger.component.ActivityComponent;
import com.WattanArt.R;
import com.WattanArt.Utils.PictUtil;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.widgets.CustomeTextView;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.artcomponent.AccessoriesView;
import com.WattanArt.artcomponent.CoverView;
import com.WattanArt.artcomponent.DimensionData;
import com.WattanArt.artcomponent.ImageCaseComponent;
import com.WattanArt.artcomponent.ObjectView;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.Category.ColorBgMobileAdapter;
import com.WattanArt.ui.ShippingForMobile.ShippingMobileActivity;
import com.WattanArt.ui.base.BaseActivity;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.task.BitmapCropTask;
import com.yalantis.ucrop.util.RectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    Uri imageUri;
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


    double centreX, centreY;
            float a;

    String mobileImage, mobileType,mobileId ,priceIn ,priceOut ;
    ArrayList<Object> General_Style, General_Color;
    ColorBgMobileAdapter colorBgMobileAdapter;
    int colorOrStyleType;

    File fileForMobile, fileForCover;

    int actW, actH;
    float scaleX, scaleY;
    Matrix mat;
    float[] pts;

    int colorPosition = -1, stylePostion = -1;



    String mobileImageUrl;

    Intent intent;

    String mobileFilename, coverFileilename;

    String mobileName, coverName;

    String colorSend="";
    String styleSend="";

    String filenameMobile, filenameCover;
    ImageView lowPixelsIv;

    boolean show=false;

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
        lowPixelsIv.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, getString(R.string.img_resoluyion), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
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
        lowPixelsIv = findViewById(R.id.lowPixelsIv);

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

//        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9, R.drawable.huaweiy9_2));

        cover = (CoverView) component.getComponentView(R.id.cover);
        accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);

        objectView = (ObjectView) component.getComponentView(R.id.component);

//        accessoriesView.setData(R.drawable.huaweiy9_2);
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

    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
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


                File file = new File(photos.get(0));
                imageUri = Uri.fromFile(file);
                     Log.e("imageUri",imageUri+"");
                     Log.e("imageUri",imageUri.getPath()+"");
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
//        Log.e("imageNameFromMobile", imageUploadResponseModel.getFileName());
//        if (imageUploadResponseModel.getState() == 5) {
//            mobileName = imageUploadResponseModel.getFileName();
//            mPresenter.returnUploadedImageForCover(photos, fileForCover);
//        }
    }

    @Override
    public void returnUploadedImageForCover(ImageUploadResponseModel imageUploadResponseModel) {
//        Log.e("imageNameFromCover", imageUploadResponseModel.getFileName());
//        if (imageUploadResponseModel.getState() == 5) {
//            coverName = imageUploadResponseModel.getFileName();
//            sendBitmapImage();
//        }
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
        if (General_Style.get(position)!=null)
            styleSend=General_Style.get(position)+"";
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

                if (actH<0){
                    actH=(actH*-1)+400;
                }
                if (actW<0){
                    actW=(actW*-1)+400;
                }

                zoomEffect();

                Log.e("calculateScaleImage", "" + scale);


                float aspect_ratio = (float) coverWidth / (float) coverHeight;
                float aspect_ratio2 = (float) actW / (float) actH;

                Log.e("calculateAspectRatio", "" + aspect_ratio + " && " + aspect_ratio2);


                centreX = cover.getX() + cover.getWidth() / 2;
                centreY = cover.getY() + cover.getHeight() / 2;

                double tx = scaleX - centreX;
                double ty = scaleY - centreY;

                double t_length = Math.sqrt(tx * tx + ty * ty);
                 a = (float) Math.acos(ty / t_length);

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

    if (!show){
        if (cover.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) cover.getDrawable()).getBitmap();

            currentBitmap = Bitmap.createBitmap(bitmap,
                    0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), cover.getImageMatrix(), true);

            mobileBitmap = loadBitmapFromView(component);
//            Log.i(TAG, String.format("Size (%d , %d) allMobile Size (%d , %d) current Rottion Angle %f and Zoom %f", currentBitmap.getWidth(), currentBitmap.getHeight()
//                    , mobileBitmap.getWidth(), mobileBitmap.getHeight()));


//            cropImage();

            if (mobileBitmap != null && currentBitmap != null) {
                BitmapMobileHelper.getInstance().setBitmapMobile(mobileBitmap);
                BitmapMobileHelper.getInstance().setBitmapCover(currentBitmap);
            }

            sendBitmapImage();
//            cropImages(this);
        } else {
            hideLoaderDialog();
            Toast.makeText(this, getString(R.string.choose_bg), Toast.LENGTH_SHORT).show();
        }
    }
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

    @Override
    public void showLoading() {
//        super.showLoading();
    }

    @Override
    public void hideLoading() {
//        super.hideLoading();
    }




    public void sendBitmapImage() {
        initColorRecyclerView();
        if (colorBgMobileAdapter != null) {
            color_recyclerview.setAdapter(colorBgMobileAdapter);
        }
        Intent intent = new Intent(this, ShippingMobileActivity.class);
        intent.putExtra("color", colorSend+"");
        intent.putExtra("style", styleSend+"");
        intent.putExtra("photos", photos.get(0)+"");
        intent.putExtra("mobileId", mobileId+"");
        intent.putExtra("priceIn", priceIn+"");
        intent.putExtra("priceOut", priceOut+"");
        Log.e("photos",photos.get(0)+"");
        startActivity(intent);
    }


    public Bitmap cropImage(){
//        HighlightView highlightView=new HighlightView(cover);
//        highlightView.getCropRect();
        Bitmap b = Bitmap.createBitmap(cover.getWidth(), cover.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        canvas.drawColor(Color.TRANSPARENT);
        cover.layout(0, 0, cover.getLayoutParams().width, cover.getLayoutParams().height);
        cover.draw(canvas);
        view_image.setVisibility(View.VISIBLE);
        view_image.setImageBitmap(b);
        return b;
    }


    public void cropImages(Context context) {
        ImageModel imageModel=new ImageModel();
        imageModel.setCurrentRotate(a);
        imageModel.setUri(imageUri);


        imageModel.setPath(imageUri.getPath());
        imageModel.setMainImageWidth(actW);
        imageModel.setMainImageHeight(actH);
        imageModel.setFilteredBitmap(currentBitmap);
        imageModel.setMatrix(mat);
        int[] posXY = new int[2];
        cover.getLocationOnScreen(posXY);
        int x = posXY[0];
        int y = posXY[1];
        imageModel.setPositionX(x);
        imageModel.setPositionY(y);
        imageModel.setCurrentAngle(a);
        imageModel.setCurrentScale(1f);
        imageModel.setmCurrentImageCorners(cover.getMCurrentImageCorners());
        imageModel.setmExifInfo(cover.getExifInfo());

         double factorHeight = ((double) actH) / ((double) currentBitmap.getHeight());
         double factorWidth = ((double) actW) / ((double) currentBitmap.getWidth());

         imageModel.setFactorHeight(factorHeight);
         imageModel.setFactorWidth(factorWidth);
        RectF rect = new RectF();
        rect.left=cover.getLeft();
            imageModel.setmCropRect(rect);

        List<ImageModel> imageModels =new ArrayList<>();
        imageModels.add(imageModel);


        PictUtil.saveToCacheFile(currentBitmap,
                imageModel.getCurrentRotate(),
                new File(imageModels.get(0).getPath()).getName(),
                imageModels.get(0).getMainImageWidth(),
                imageModels.get(0).getMainImageHeight(),
                new PictUtil.saveBitmapCallback() {
                    @Override
                    public void onSuccess(String path) {

                        Log.e("PathToNewFile", path + "");
                        imageModels.get(0).setUri(Uri.fromFile(new File(path)));
                        imageModels.get(0).setOutputUri(Uri.fromFile(new File(path)));
                        imageModels.get(0).setmImageOutputPath(new File(path.replace("/.Cache", "")).getParent() + "/" + new File(imageModels.get(0).getPath())
                                .getName());

                        cropImage(
                                imageModels.get(0).getFilteredBitmap(),
                                imageModels.get(0).getMatrix(),
                                (int) (imageModels.get(0).getPositionX() *
                                        imageModels.get(0).getFactorWidth()),
                                (int) (imageModels.get(0).getPositionY() *
                                        imageModels.get(0).getFactorHeight()),
                                imageModels.get(0).getOutputUri(),
                                imageModels.get(0).getmExifInfo(),
                                imageModels.get(0).getImageWidth(),
                                imageModels.get(0).getImageHeight(),
                                path,
                                new File(path.replace("/.Cache", "")).getParent() + "/" + new File(imageModels.get(0).getPath())
                                        .getName(),
                                imageModels.get(0).getmCropRect(),
                                imageModels.get(0).getmCurrentImageCorners(),
                                imageModels.get(0).getCurrentScale(),
                                imageModels.get(0).getCurrentAngle(),
                                new BitmapCropCallback() {
                                    @Override
                                    public void onBitmapCropped(@io.reactivex.annotations.NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {

//                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                                        try {
                                            Bitmap bitmap = getThumbnail(imageUri);
                                            view_image.setVisibility(View.VISIBLE);
                                            view_image.setImageBitmap(bitmap);
                                        } catch (IOException e) {
                                            Log.e("failINnnnl","failINnnnl");
                                            e.printStackTrace();
                                        }

                                        Log.e("goooooogd","goooooooogd");
                                    }

                                    @Override
                                    public void onCropFailure(@io.reactivex.annotations.NonNull Throwable t) {
                                        Log.e("failllll","failllll");
                                        t.printStackTrace();
                                    }
                                });

                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(context, context.getString(R.string.some_error), Toast.LENGTH_SHORT).show();
                        Log.e("ErrorInsaveFilterImage", "error saving filtered Bitmap ");
                        e.printStackTrace();
                    }
                });
    }

    private void cropImage(Bitmap bitmap, Matrix matrix, int x, int y, Uri uri, ExifInfo mExifInfo, int mMaxResultImageSizeX,
                           int mMaxResultImageSizeY, String mImageInputPath, String mImageOutputPath
            , RectF mCropRect, float[] mCurrentImageCorners, float currentScale,
                           float currentAngle, BitmapCropCallback cropCallback) {

        Log.e("currentScale", currentScale + " - ");


        //TODO: check this code later
        //check this code later
        if (mCurrentImageCorners == null) {
            Log.e("mCurrentImageCorners", "mCurrentImageCorners == null");
            //check tranformImageView
            // private static final int RECT_CORNER_POINTS_COORDS = 8;
            //protected final float[] mCurrentImageCorners = new float[RECT_CORNER_POINTS_COORDS];
            //which has this code
            /* 0------->1
             * ^        |
             * |        |
             * |        v
             * 3<-------2
             */
            mCurrentImageCorners = new float[8];
        }
        final ImageState imageState;

        imageState = new ImageState(
                mCropRect, RectUtils.trapToRect(mCurrentImageCorners),
                currentScale, currentAngle);


        final CropParameters cropParameters = new CropParameters(
                mMaxResultImageSizeX, mMaxResultImageSizeY, x, y,
                Bitmap.CompressFormat.JPEG, 90,
                mImageInputPath, mImageOutputPath, mExifInfo);

        new BitmapCropTask(bitmap, matrix, uri, imageState, cropParameters, cropCallback).execute();
    }


    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 1000) ? (originalSize / 1000) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    public boolean zoomEffect(){
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
