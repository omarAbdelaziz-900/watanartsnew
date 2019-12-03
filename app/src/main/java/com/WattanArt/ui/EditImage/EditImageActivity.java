
package com.WattanArt.ui.EditImage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.WattanArt.Adapters.ImagesModelAdapter;
import com.WattanArt.Adapters.ThumbnailsAdapter;
import com.WattanArt.R;
import com.WattanArt.Utils.Localization;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.WrapContentLinearLayoutManager;
import com.WattanArt.Utils.widgets.CustomeTextViewBold;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yalantis.ucrop.Constants;
import com.yalantis.ucrop.callback.OnPhotoEditorListener;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.iwf.photopicker.PhotoPicker;

public class EditImageActivity extends AppCompatActivity implements
        CropImageView.LowPixelsInteractions,
        CropImageView.getNewXYpositions,
        ThumbnailsAdapter.ThumbnailsAdapterListener,
        View.OnClickListener,
        ImagesModelAdapter.OnItemClickListener,
        OnPhotoEditorListener {

    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 20;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    Uri uri;
    //  public static List<ImageModel> filteredList = new ArrayList<>();
    ImageModel mainImageModel;
    ViewGroup subControlsHolder, ratio_holder;
    ViewGroup bottomControlsHolder, ratioBottomHolder, rotateBottomHolder, flipBottomHolder, colorBottomHolder, filterBottomHolder, contrastBottomHolder, lightBottomHolder;
    Matrix tempMatrix;
    boolean isSelectOneImage, isSelectMultiImage;
    UCropView uCropView;
    GestureCropImageView mGestureCropImageView;
    OverlayView moOverlayView;
    HorizontalScrollView horizontalScrollView;

    ThumbnailsAdapter mAdapter;
    RecyclerView mFilterRecycler;

    ImageView repalceIv, lowPixelsIv, deleteImage, borderIv;

    SeekBar lightSeekBar;
    SeekBar contrastSeekBar;
    SeekBar hueSeekBar;
    SeekBar saturationSeekbar;

    int currentPosition = 0;

    boolean isSegmented = false;
    ImageView mToolbarBackImageView;

    float brightness, contrast, hue;
    float saturation;
    boolean isInitial = true;
    Filter mFilter;
    float ratio;
    float currentRatioIndex;
    boolean isFlip;
    float rotateDegree;
    float mCurrentScale;
    float positionX, positionY;
    int imageWidth, imageHeight;

    boolean isRotateEstablished = false;
    //    boolean isReplaceIstablished = false;
    boolean isLowResolution;

    public static int typeOmar;
//    float[] ratios = new float[]{1f, 8f / 12f, 12f / 8f, 24f / 8f, 24f / 12f, 36f / 8f, 12f / 12f, 12f / 16f, 12f / 20f};
//
//    Pair<Integer, Integer>[] pairDimens = new Pair[]{new Pair(600, 600), new Pair(1000, 1000), new Pair(1000, 1000),
//            new Pair(1800, 600), new Pair(3000, 1000), new Pair(3000, 1000)};

//    float[] ratios = new float[]{
//            1f
//            ,8f / 12f
//            , 12f / 8f
//            , 24f / 8f
//            , 24f / 12f
//            , 36f / 8f
//
//
//            ,1f
//            ,12f/16f
//            ,12f/20f
//            ,4f/4f,
//            6f/6f ,
//            8f/8f
//    };

    float[] ratios = new float[]{
            20f / 20f,
            20f / 30f,
            30f / 20f,
            60f / 20f,
            60f / 30f,
            90f / 20f,

            30f / 30f,

            40f / 30f,
            50f / 30f,

            40f / 40f,
            60f / 60f,
            80f / 80f};

    Pair<Integer, Integer>[] pairDimens = new Pair[]{
            new Pair(600, 600),
            new Pair(600, 800),
            new Pair(800, 600),
            new Pair(1200, 600),

            new Pair(1200, 800),

            new Pair(1400, 600),

            new Pair(800, 800),

//            new Pair(800, 1000),
            new Pair(1000, 800),

//            new Pair(800, 1200),
            new Pair(1200, 800),

            new Pair(1000, 1000),
            new Pair(1200, 1200),
            new Pair(2000, 2000)};

    Pair<Integer, Integer>[] pairPieces = new Pair[]{
            new Pair(0, 0),
            new Pair(0, 0),
            new Pair(0, 0),
            new Pair(2, 0),
            new Pair(2, 0),
            new Pair(2, 0),

            new Pair(0, 0),
            new Pair(0, 0),
            new Pair(0, 0),

            new Pair(1, 1),
            new Pair(2, 2),
            new Pair(3, 3)
    };

    @Inject
    EditImagePresenterImp mPresenter;
    private ArrayList<ThumbnailItem> thumbnailItemList = new ArrayList<>();
    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
        @Override
        public void onRotate(float currentAngle) {
            mGestureCropImageView.getImageState(false);
        }

        @Override
        public void newRotateListner(float currentAngle) {
            mGestureCropImageView.setImageToWrapCropBounds();


            if (!isInitial)
                if (currentAngle == 0 || currentAngle == 180 || currentAngle == 360)
                    mGestureCropImageView.zoomOutImage(mGestureCropImageView.getMinScale());

            mGestureCropImageView.getImageState(false);
        }


        @Override
        public void onScale(float currentScale) {
            Log.e("Scale", "" + currentScale);
            //added to resolve marwa issue that when release xoom no state for image dimensions is returned
            mGestureCropImageView.getImageState(false);
            mCurrentScale = currentScale;
        }


        @Override
        public void onDrawNewScales() {

        }


        @Override
        public void onLoadComplete() {
        }


        @Override
        public void onLoadFailure(@NonNull Exception e) {
            e.printStackTrace();
            finish();
        }
    };


    TransformImageView.RationChanged onRationChanged = new TransformImageView.RationChanged() {
        @Override
        public void onRationChanged() {
            if (rotateDegree != 0 && isRotateEstablished) {
                rotateByAngle(((int) rotateDegree));
                isRotateEstablished = false;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        if (getIntent() != null)
            uri = getIntent().getParcelableExtra("uri");
        Log.e("uriiii", uri + "");
        currentPosition = getIntent().getIntExtra("index", 0);

        mainImageModel = ShippingActivity.imageModelList.get(currentPosition);
        mToolbarBackImageView = findViewById(R.id.toolbar_image_view);
        mToolbarBackImageView.setVisibility(View.VISIBLE);

        mToolbarBackImageView.setOnClickListener(v -> {
            onBackPressed();
        });

        Log.e("onCReatte","onCReatte");

        CustomeTextViewBold mToolbarTitleTextView = findViewById(R.id.toolbar_tv_title);
        mToolbarTitleTextView.setText(R.string.title_edit_images);
        mToolbarTitleTextView.setVisibility(View.VISIBLE);


        TextView submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);


        tempMatrix = mainImageModel.getMatrix();

//        if (getIntent().hasExtra(Constants.EXTRA_ASPECT_RATIO_OPTIONS)) {
//            mainImageModel.setCurrentRatio(getIntent().getFloatExtra(Constants.EXTRA_ASPECT_RATIO_OPTIONS, 0f));
//        }


        deleteImage = findViewById(R.id.deleteImage);

        ratio_holder = findViewById(R.id.ratio_holder);
        ratioBottomHolder = findViewById(R.id.ratioBottomHolder);
        rotateBottomHolder = findViewById(R.id.rotateBottomHolder);
        flipBottomHolder = findViewById(R.id.flipBottomHolder);
        colorBottomHolder = findViewById(R.id.colorBottomHolder);
        filterBottomHolder = findViewById(R.id.filterBottomHolder);
        contrastBottomHolder = findViewById(R.id.contrastBottomHolder);
        lightBottomHolder = findViewById(R.id.lightBottomHolder);
        hueSeekBar = findViewById(R.id.hueSeekBar);
        saturationSeekbar = findViewById(R.id.saturationSeekbar);


        bottomControlsHolder = findViewById(R.id.bottomControlsHolder);


        horizontalScrollView = findViewById(R.id.horizontalScrollView);

        lowPixelsIv = findViewById(R.id.lowPixelsIv);
        repalceIv = findViewById(R.id.repalceIv);
        borderIv = findViewById(R.id.borderIv);


        if (new UserData().getLocalization(this) == Localization.ARABIC_VALUE) {
            horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
        }

        subControlsHolder = findViewById(R.id.subControlsHolder);

        LinearLayoutManager secondLinearLayoutManager = new LinearLayoutManager(this, WrapContentLinearLayoutManager.HORIZONTAL, false);

        mFilterRecycler = findViewById(R.id.mFilterRecycler);
        mFilterRecycler.setLayoutManager(secondLinearLayoutManager);


        uCropView = findViewById(R.id.ucrop);


        moOverlayView = uCropView.getOverlayView();
        mGestureCropImageView = uCropView.getCropImageView();
        mGestureCropImageView.setLowPixelsInteractions(this);
        mGestureCropImageView.setGetNewXYpositions(this);
        mGestureCropImageView.setOnPhotoEditorListener(this);

        mGestureCropImageView.setTransformImageListener(mImageListener);
        mGestureCropImageView.setOnratioChanged(onRationChanged);


        repalceIv.setOnClickListener(this);
        deleteImage.setOnClickListener(this);
        borderIv.setOnClickListener(this);

        rotateDegree = mainImageModel.getCurrentAngle();
        isLowResolution = mainImageModel.isLowResolution();
        isFlip = mainImageModel.isFlipped();


        for (int index = 0; index < bottomControlsHolder.getChildCount(); index++) {
            int finalIndex = index;
            bottomControlsHolder.getChildAt(finalIndex).setOnClickListener(view -> {
                for (int i = 0; i < subControlsHolder.getChildCount(); i++) {
                    if (i == finalIndex) {
                        subControlsHolder.getChildAt(i).setVisibility(View.VISIBLE);
                    } else {
                        subControlsHolder.getChildAt(i).setVisibility(View.GONE);
                    }
                }

                bottomControlsHolder.getChildAt(finalIndex).setBackground(getDrawable(R.drawable.background_selected));

                for (int i = 0; i < bottomControlsHolder.getChildCount(); i++) {
                    if (i != finalIndex) {
                        bottomControlsHolder.getChildAt(i).setBackground(getDrawable(R.drawable.background_unselected));
                    }
                }


//                rotate action
                if (finalIndex == 1) {

                    rotateDegree += 90F;

                    if (rotateDegree == 360F) {
                        rotateDegree = 0;
                    }
                    rotateByAngle(90);
                }


                //flip action
                if (finalIndex == 2) {
                    Bitmap bitmap = fastFlip(mGestureCropImageView.getViewBitmap());
                    mGestureCropImageView.setImageBitmap(bitmap);
                    mainImageModel.setBitmap(fastFlip(mainImageModel.getBitmap()));
                    mainImageModel.setFilteredBitmap(fastFlip(mainImageModel.getFilteredBitmap()));
                    isFlip = !isFlip;
                }

//                if (finalIndex == 7) {

                //            mGestureCropImageView.postScale(mainImageModel.getCurrentScale(),
//                    (mainImageModel.getImageWidth() / 2) + mainImageModel.getPositionX(),
//                    (mainImageModel.getImageHeight() / 2) + mainImageModel.getPositionY());

//                    mGestureCropImageView.zoomImageToPositionMine(2.3598654f,
//                            313f,
//                            179f);

//                    TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
//                    textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
//                        @Override
//                        public void onDone(String inputText, int colorCode) {
//                            mGestureCropImageView.addText(inputText, colorCode);
//                        }
//                    });
//                }
            });
        }


        ratio = mainImageModel.getCurrentRatio();
        currentRatioIndex = mainImageModel.getCurrentRatioIndex();
        Log.e("currentRatioIndex1", currentRatioIndex + "");


        if (ratio == ratios[0]
                || ratio == ratios[1]
                || ratio == ratios[2]
                || ratio == ratios[6]
                || ratio == ratios[7]
                || ratio == ratios[8]) {
            isSegmented = false;
//            moOverlayView.setCropGridColumnCount(0);
        } else {
            isSegmented = true;
//            moOverlayView.setCropGridColumnCount(2);
        }

        moOverlayView.setCropGridRowCount(pairPieces[(int) currentRatioIndex].second);
        moOverlayView.setCropGridColumnCount(pairPieces[(int) currentRatioIndex].first);
        Log.e("currentRatioIndex2", currentRatioIndex + "");
        moOverlayView.drawCropGrid(new Canvas());


        for (int index = 0; index < ratio_holder.getChildCount(); index++) {


//            if (mainImageModel.getCurrentRatio() == ratios[index]) {
//                ratio_holder.getChildAt(index).setBackground(getDrawable(R.drawable.background_selected_bordered));
//            }


            int finalIndex = index;


            Log.e("finalIndexx", finalIndex + "");
            Log.e("ratio_holderChildCount", ratio_holder.getChildCount() + "");

            int finalIndex1 = index;

            ratio_holder.getChildAt(index).setOnClickListener(view -> {


                mainImageModel.setTypePatternId(finalIndex + 1);

                Log.e("TypeOmarPattern", mainImageModel.getTypePatternId() + "");
                Log.e("finalIndexxInsideClick", finalIndex + "");
                Log.e("currentRatioIndex4", currentRatioIndex + "");


//                if (finalIndex == currentRatioIndex) {
//                    Log.e("currentRatioIndex5",currentRatioIndex+"");
//                    return;
//                }


                currentRatioIndex = finalIndex;
                Log.e("currentRatioIndex20", currentRatioIndex + "");

                //not 8*8 ratio
                Log.e("MainImageWidth", mainImageModel.getMainImageWidth() + "");
                Log.e("MainImageHeight", mainImageModel.getMainImageHeight() + "");
                if (finalIndex != 0) {
//                    if (mainImageModel.getMainImageWidth() >= 1000 && mainImageModel.getMainImageHeight() >= 1000) {
                    if (mainImageModel.getMainImageWidth() >= 600 && mainImageModel.getMainImageHeight() >= 600) {


                        ratio_holder.getChildAt(finalIndex).setBackground(getDrawable(R.drawable.background_selected_bordered));

//                        mGestureCropImageView.setTargetAspectRatio(0);
                        mGestureCropImageView.setTargetAspectRatio(ratios[finalIndex]);


                        ratio = ratios[finalIndex];
                        Log.e("ratioInside", ratio + "");
                        currentRatioIndex = finalIndex;
                        mainImageModel.setCurrentRatioIndex(currentRatioIndex);

                        Log.e("currentRatioIndex6", currentRatioIndex + "");
                        Log.e("currentRatioIndex7", mainImageModel.getCurrentRatioIndex() + "");

                        for (int i = 0; i < ratio_holder.getChildCount(); i++) {
                            if (i != finalIndex)
                                ratio_holder.getChildAt(i).setBackground(getDrawable(R.drawable.background_selected));
                        }


                        if (finalIndex == 0 || finalIndex == 1 || finalIndex == 2
                                || finalIndex == 6 || finalIndex == 7 || finalIndex == 8) {
//                            moOverlayView.setCropGridColumnCount(0);
                            isSegmented = false;
                            mainImageModel.setSegmented(false);
                        } else {
//                            moOverlayView.setCropGridColumnCount(2);
                            isSegmented = true;
                            mainImageModel.setSegmented(true);
                        }


                        moOverlayView.setCropGridRowCount(pairPieces[(int) currentRatioIndex].second);
                        moOverlayView.setCropGridColumnCount(pairPieces[(int) currentRatioIndex].first);
                        Log.e("currentRatioIndex8", currentRatioIndex + "");

                        moOverlayView.drawCropGrid(new Canvas());
                        mGestureCropImageView.setImageToWrapCropBounds(false);
                        new Handler().postDelayed(() ->{
                            mGestureCropImageView.zoomOutImage(mGestureCropImageView.getMinScale());
                            mGestureCropImageView.setImageToWrapCropBounds(true);
                        } ,50);


//                        float currentScale = mGestureCropImageView.getCurrentScale();
//                        mGestureCropImageView.zoomOutImage(mGestureCropImageView.getMinScale());
//                        mGestureCropImageView.zoomOutImage(currentScale);

                    } else {
                        Toast toast = Toast.makeText(this, getString(R.string.can_not_change_ratio), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } else {

                    ratio_holder.getChildAt(finalIndex).setBackground(getDrawable(R.drawable.background_selected_bordered));

                    mGestureCropImageView.setTargetAspectRatio(ratios[finalIndex]);
                    ratio = ratios[finalIndex];
                    currentRatioIndex = finalIndex;
                    Log.e("currentRatioIndex9", currentRatioIndex + "");
                    mainImageModel.setCurrentRatioIndex(currentRatioIndex);

                    for (int i = 0; i < ratio_holder.getChildCount(); i++) {
                        if (i != finalIndex)
                            ratio_holder.getChildAt(i).setBackground(getDrawable(R.drawable.background_selected));
                    }

                    moOverlayView.setCropGridColumnCount(0);
                    moOverlayView.setCropGridRowCount(0);
                    isSegmented = false;
                    moOverlayView.drawCropGrid(new Canvas());
                    mGestureCropImageView.setImageToWrapCropBounds();

                    //zoom out image to fit screen and no resolution issues occured
                    float currentScale = mGestureCropImageView.getCurrentScale();
                    mGestureCropImageView.zoomOutImage(mGestureCropImageView.getMinScale());
                    mGestureCropImageView.zoomOutImage(currentScale);

                }

                isRotateEstablished = true;

            });
        }


        imageHeight = mainImageModel.getImageHeight();
        imageWidth = mainImageModel.getImageWidth();
        positionX = mainImageModel.getPositionX();
        positionY = mainImageModel.getPositionY();


        prepareImageFromShippingActivity(true);

        brightness = mainImageModel.getCurrentBrightness();

        contrast = mainImageModel.getCurrentContrast();
        hue = mainImageModel.getHue();
        if (mainImageModel.getSaturation() == 0) {
            mainImageModel.setSaturation(1);
        }
        saturation = mainImageModel.getSaturation();
        mFilter = mainImageModel.getCurrentFilter();

        adjustBrightness();
        adjustContrast();
        adjustHueMethod();

        lowPixelsIv.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, getString(R.string.img_resoluyion), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
        isInitial = false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_submit, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        //respond to menu item selection
//
//        if (item.getItemId() == R.id.submit) {
//
//        }
////            mGestureCropImageView.cropAndSaveImage(Bitmap.CompressFormat.JPEG, 100, new BitmapCropCallback() {
////                @Override
////                public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
////                    Log.e("ssssssss", "Ssssssssssss");
////                }
////
////                @Override
////                public void onCropFailure(@NonNull Throwable t) {
////                    t.printStackTrace();
////                }
////            });
////        }
//        return true;
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void pickFromGallery() {
        isSelectMultiImage = true;
        isSelectOneImage = false;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {


            ArrayList<String> selected = new ArrayList<>();
            selected.add(mainImageModel.getPath());

            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setRemoval(selected)
                    .setShowCamera(false)
                    .setPreviewEnabled(false)
                    .start(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void replaceImage() {

        isSelectMultiImage = false;
        isSelectOneImage = true;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            ArrayList<String> selected = new ArrayList<>();
            selected.add(mainImageModel.getPath());

            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setPhotoCount(1)
                    .setRemoval(selected)
                    .setShowCamera(false)
                    .setPreviewEnabled(false)
                    .start(this);
        }
    }

    private float calculateSaturationValue(int value) {
        return value / 256f;
    }

    private void adjustHueMethod() {
        this.hueSeekBar.setMax(512);
        this.hueSeekBar.setProgress((int) this.mainImageModel.getHue());
        final Bitmap[] bitmap = new Bitmap[1];
        this.hueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hue = (float) progress;
                mGestureCropImageView.setImageBitmap(mGestureCropImageView.adjustHue(bitmap[0], hue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if (hue == 0f) {
                    bitmap[0] = mGestureCropImageView.getViewBitmap();
                } else {
                    if (mFilter != null && mFilter.getName() != getString(com.yalantis.ucrop.R.string.ucrop_filter_normal)) {
                        bitmap[0] = mainImageModel.getFilteredBitmap();
                    }

                    if (brightness != 0f) {
                        bitmap[0] = ColorMatrixUtils.changeBitmapBrightness(bitmap[0] == null ? mainImageModel.getBitmap() : bitmap[0], brightness);
                    }

                    if (contrast != 0f) {
                        bitmap[0] = ColorMatrixUtils.changeBitmapContrast(bitmap[0] == null ? mainImageModel.getBitmap() : bitmap[0], contrast);
                    }

                    if (saturation != 0f && saturation != 1f) {
                        bitmap[0] = updateSat(bitmap[0] == null ? mainImageModel.getBitmap() : bitmap[0], saturation);
                    }

                    if (bitmap[0] == null) {
//                        bitmap[0] = mGestureCropImageView.getViewBitmap();
                        bitmap[0] = mainImageModel.getBitmap();
                    }
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                bitmap[0] = null;
            }
        });


        if (this.mainImageModel.getSaturation() == 0) {
            this.mainImageModel.setSaturation(1f);
        }

        this.saturationSeekbar.setMax(512);
        this.saturationSeekbar.setProgress((mainImageModel.getSaturation() == 0 || mainImageModel.getSaturation() == 1) ? 256 :
                ((int) (mainImageModel.getSaturation() * 256f)));
        final Bitmap[] satBitmap = new Bitmap[1];

        this.saturationSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress == 0 ? 1 : progress;
                saturation = calculateSaturationValue(progress);
                mGestureCropImageView.setImageBitmap(updateSat(satBitmap[0], saturation));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if (saturation == 0f) {
                    satBitmap[0] = mGestureCropImageView.getViewBitmap();
                } else {
                    if (mFilter != null && mFilter.getName() != getString(com.yalantis.ucrop.R.string.ucrop_filter_normal)) {
                        satBitmap[0] = mainImageModel.getFilteredBitmap();
                    }

                    if (brightness != 0f) {
                        satBitmap[0] = ColorMatrixUtils.changeBitmapBrightness(satBitmap[0] == null ? mainImageModel.getBitmap() :
                                satBitmap[0], brightness);
                    }

                    if (contrast != 0f) {
                        satBitmap[0] = ColorMatrixUtils.changeBitmapContrast(satBitmap[0] == null ? mainImageModel.getBitmap()
                                : satBitmap[0], contrast);
                    }

                    if (hue != 0f && hue != 1f) {
                        satBitmap[0] = mGestureCropImageView.adjustHue(satBitmap[0] == null
                                ? mainImageModel.getBitmap() : satBitmap[0], hue);
                    }

                    if (satBitmap[0] == null) {
//                        bitmap[0] = mGestureCropImageView.getViewBitmap();
                        satBitmap[0] = mainImageModel.getBitmap();
                    }

                }

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                satBitmap[0] = null;
            }
        });
    }

    private Bitmap updateSat(Bitmap src, float settingSat) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap bitmapResult = null;

        try {
            bitmapResult =
                    Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            Canvas canvasResult = new Canvas(bitmapResult);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(settingSat);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(filter);
            canvasResult.drawBitmap(src, 0, 0, paint);
        } catch (OutOfMemoryError error) {
            Log.e("updateSat", "OutOfMemoryError error");
        }


        return bitmapResult;
    }


//    private Bitmap increaseBrightness(Bitmap bitmap, int value) {
//        Mat src = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC1);
//        org.opencv.android.Utils.bitmapToMat(bitmap, src);
//        src.convertTo(src, -1, 1, value);
//        Bitmap result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.RGB_565);
//        org.opencv.android.Utils.matToBitmap(src, result);
//        return result;
//    }


    private int calculateBrightnessProgress(int value) {
        return value - 50;
    }

    private void adjustBrightness() {
        this.lightSeekBar = (SeekBar) this.findViewById(R.id.lightSeekBar);
        this.lightSeekBar.setMax(100);
        this.lightSeekBar.setProgress(mainImageModel.getCurrentBrightness() == 0 ? 50 :
                ((int) (mainImageModel.getCurrentBrightness() + 50f)));
        final Bitmap[] bitmap = new Bitmap[1];
        this.lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightness = (float) calculateBrightnessProgress(progress);
                Log.e("brightness", "" + brightness);
                mGestureCropImageView.setImageBitmap(ColorMatrixUtils.changeBitmapBrightness(bitmap[0], brightness));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if (brightness == 0f) {
                    bitmap[0] = mGestureCropImageView.getViewBitmap();
                } else {
                    if (mFilter != null && mFilter.getName() != getString(com.yalantis.ucrop.R.string.ucrop_filter_normal)) {
                        bitmap[0] = mainImageModel.getFilteredBitmap();
                    }

                    if (contrast != 0f) {
                        bitmap[0] = ColorMatrixUtils.changeBitmapContrast(bitmap[0] == null ? mainImageModel.getBitmap() : bitmap[0], contrast);
                    }

                    if (hue != 0f && hue != 1f) {
                        bitmap[0] = mGestureCropImageView.adjustHue(bitmap[0] == null ? mainImageModel.getBitmap() : bitmap[0], hue);
                    }

                    if (saturation != 0f && saturation != 1f) {
                        bitmap[0] = updateSat(bitmap[0] == null ? mainImageModel.getBitmap() : bitmap[0], saturation);
                    }

                    if (bitmap[0] == null) {
//                        bitmap[0] = mGestureCropImageView.getViewBitmap();
                        bitmap[0] = mainImageModel.getBitmap();
                    }
                }

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                bitmap[0] = null;
            }
        });
    }//    private void adjustBrightness() {
//
//        lightSeekBar = findViewById(R.id.lightSeekBar);
//        lightSeekBar.setMaxValue(100f);
//        lightSeekBar.setMinValue(-100f);
//        lightSeekBar.setMinStartValue(mainImageModel.getCurrentBrightness());
//        lightSeekBar.apply();
//
//        final Bitmap[] bitmap = new Bitmap[1];
//
//        lightSeekBar.setOnSeekbarChangeListener(value -> {
//            Log.e("LightValue", "" + value.floatValue());
//            brightness = value.floatValue();
//            if (isInitial) {
//                bitmap[0] = ColorMatrixUtils.changeBitmapBrightness(mainImageModel.getBitmap(), value.floatValue());
//            } else {
//                bitmap[0] = ColorMatrixUtils.changeBitmapBrightness(mainImageModel.getBitmap(), value.floatValue());
//            }
//
//            if (mFilter != null) {
//                onFilterSelected(mFilter);
//            } else {
//                if (!isInitial) {
//                    //apply contrast
//                    if (contrast != 0)
//                        bitmap[0] = (ColorMatrixUtils.changeBitmapContrast(bitmap[0], contrast));
//
//                    //apply hue
//                    if (hue != 0)
//                        bitmap[0] = (mGestureCropImageView.adjustHue(bitmap[0], hue));
//
//                    //apply saturation
//                    if (saturation != 0)
//                        bitmap[0] = (updateSat(bitmap[0], saturation));
//
//                }
//                mGestureCropImageView.setImageBitmap(bitmap[0]);
//            }
//        });
//
//    }


    private void triggerSwipeEvent() {
//        // Obtain MotionEvent object
//        long downTime = SystemClock.uptimeMillis();
//        long eventTime = SystemClock.uptimeMillis() + 10;
//        float x = 0f;
//        float y = 0f;
//// List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
//        int metaState = 0;
//        MotionEvent motionEvent = MotionEvent.obtain(
//                downTime,
//                eventTime,
//                MotionEvent.ACTION_MOVE,
//                positionX,
//                positionX,
//                metaState
//        );
//
//// Dispatch touch event to view
//        uCropView.getCropImageView().dispatchTouchEvent(motionEvent);


        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) uCropView.getCropImageView().getLayoutParams();
        p.leftMargin = 10; // in PX
        p.topMargin = 10; // in PX
        uCropView.getCropImageView().setLayoutParams(p);

    }


    public Bitmap takeScreenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    protected void setResultUri(Uri uri) {


        if (uri == null) {
            mainImageModel = null;
        } else {
            mainImageModel.setmExifInfo(mGestureCropImageView.getExifInfo());
            mainImageModel.setmCropRect(mGestureCropImageView.getMCropRect());
            mainImageModel.setmCurrentImageCorners(mGestureCropImageView.getMCurrentImageCorners());
            mainImageModel.setmImageInputPath(mGestureCropImageView.getImageInputPath());
            mainImageModel.setmImageOutputPath(mGestureCropImageView.getImageOutputPath());
            mainImageModel.setScreenShotImage(takeScreenShot(uCropView.getCropImageView()));
            mainImageModel.setFilteredBitmap(mGestureCropImageView.getViewBitmap());
            mainImageModel.setmInitialImageCenter(mGestureCropImageView.getmInitialImageCenter());
            mainImageModel.setmInitialImageCorners(mGestureCropImageView.getmInitialImageCorners());
            mainImageModel.setmCurrentImageCenter(mGestureCropImageView.getmCurrentImageCenter());
            mainImageModel.setMatrix(mGestureCropImageView.getmCurrentImageMatrix());
            mainImageModel.setCurrentFilter(mFilter);
            mainImageModel.setFlipped(isFlip);
            mainImageModel.setCurrentRotate(rotateDegree);
            mainImageModel.setCurrentAngle(rotateDegree);
            mainImageModel.setLowResolution(isLowResolution);
            mainImageModel.setCurrentScale(mCurrentScale);
            mainImageModel.setPositionX((int) positionX);
            mainImageModel.setPositionY((int) positionY);
            mainImageModel.setImageWidth(this.imageWidth);
            mainImageModel.setImageHeight(this.imageHeight);
            mainImageModel.setViewHeight(mGestureCropImageView.getHeight());
            mainImageModel.setViewWidth(mGestureCropImageView.getWidth());

            if (mainImageModel.getQuantity() == 0) {
                mainImageModel.setQuantity(1);
            }
        }


//        Log.e("XANDYFromActivity",
//                " scale   " + mainImageModel.getCurrentScale()
//                        + "    positionX "
//                        + mainImageModel.getPositionX() + "   positionY  "
//                        + mainImageModel.getPositionY() + "    image width "
//                        + mainImageModel.getImageWidth() + "    image height "
//                        + mainImageModel.getImageHeight());

        ShippingActivity.imageModelList.set(currentPosition, mainImageModel);
        setResult(RESULT_OK, new Intent().putExtra(Constants.EXTRA_INDEX, currentPosition));
        finish();
    }

    @Override
    public void onBackPressed() {

        finish();
//        setResultUri(getImageUri(this, mainImageModel.getBitmap()),
//                mGestureCropImageView.getTargetAspectRatio(), 0, 0, 0, 0);
//        mGestureCropImageView.cropAndSaveImage(Bitmap.CompressFormat.JPEG, 100, new BitmapCropCallback() {
//
//            @Override
//            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
//                setResultUri(resultUri, mGestureCropImageView.getTargetAspectRatio(), offsetX, offsetY, imageWidth, imageHeight);
//            }
//
//            @Override
//            public void onCropFailure(@NonNull Throwable t) {
//                t.printStackTrace();
//                finish();
//            }
//        });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        if (inImage == null) {
            return null;
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private float calculateContrastProgress(int value) {
        return (value * 0.1f) - 1;
    }

    private void adjustContrast() {
        this.contrastSeekBar = (SeekBar) this.findViewById(R.id.contrastSeekBar);
        this.contrastSeekBar.setMax(20);
        int initialContrast = (int) ((mainImageModel.getCurrentContrast() + 1f) * 10);
        this.contrastSeekBar.setProgress(mainImageModel.getCurrentContrast() == 0 ? 10 : initialContrast);
        final Bitmap[] bitmap = new Bitmap[1];
        this.contrastSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                contrast = calculateContrastProgress(progress);
                mGestureCropImageView.setImageBitmap(ColorMatrixUtils.changeBitmapContrast(bitmap[0], contrast));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                if (contrast == 0f) {
                    bitmap[0] = mGestureCropImageView.getViewBitmap();
                } else {
                    if (mFilter != null && mFilter.getName() != getString(com.yalantis.ucrop.R.string.ucrop_filter_normal)) {
                        bitmap[0] = mainImageModel.getFilteredBitmap();
                    }

                    if (brightness != 0f) {
                        bitmap[0] = ColorMatrixUtils.changeBitmapBrightness(bitmap[0] == null
                                ? mainImageModel.getBitmap() : bitmap[0], brightness);
                    }

                    if (hue != 0f && hue != 1f) {
                        bitmap[0] = mGestureCropImageView.adjustHue(bitmap[0] == null ?
                                mainImageModel.getBitmap() : bitmap[0], hue);
                    }

                    if (saturation != 0f && saturation != 1f) {
                        bitmap[0] = updateSat(bitmap[0] == null ? mainImageModel.getBitmap()
                                : bitmap[0], saturation);
                    }

                    if (bitmap[0] == null) {
//                        bitmap[0] = mGestureCropImageView.getViewBitmap();
                        bitmap[0] = mainImageModel.getBitmap();

                    }
                }
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                bitmap[0] = null;
            }
        });
    }

    public void prepareThumbnail(final Bitmap bitmap) {

        mAdapter = new ThumbnailsAdapter(EditImageActivity.this,
                thumbnailItemList, this);

        mFilterRecycler.setAdapter(mAdapter);


        Runnable r = () -> {
            Bitmap thumbImage;

            if (bitmap == null) {
                thumbImage = null;
            } else {
                thumbImage = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
            }

            if (thumbImage == null)
                return;

            ThumbnailsManager.clearThumbs();
            thumbnailItemList.clear();

            mFilterRecycler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });

            // add normal bitmap first
            ThumbnailItem thumbnailItem = new ThumbnailItem();
            thumbnailItem.image = thumbImage;
            thumbnailItem.filterName = getString(com.yalantis.ucrop.R.string.ucrop_filter_normal);
            ThumbnailsManager.addThumb(thumbnailItem);

            List<Filter> filters = FilterPack.getFilterPack(getBaseContext());

            for (Filter filter : filters) {
                ThumbnailItem tI = new ThumbnailItem();
                tI.image = thumbImage;
                tI.filter = filter;
                tI.filterName = filter.getName();
                ThumbnailsManager.addThumb(tI);
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(getBaseContext()));

            runOnUiThread(() -> {
                mAdapter.notifyDataSetChanged();
                mAdapter.setSelectedFilter(mainImageModel.getCurrentFilter());
            });
        };

        new Thread(r).start();
    }

    private void rotateByAngle(int angle) {
//        mGestureCropImageView.setImageBitmap(fastRotate(mGestureCropImageView.getViewBitmap() , angle));
//        mGestureCropImageView.setTargetAspectRatio(ratio);
        mGestureCropImageView.postRotate(angle);
        mGestureCropImageView.setImageToWrapCropBounds();
    }


    private boolean checkLowResolutionView() {

        boolean show = true;
        Log.e("mnmmmaaa", imageWidth + "<<" + imageHeight);
        Log.e("MainImageWidthhh", mainImageModel.getMainImageWidth() + "");
        Log.e("MainImageHeightttt", mainImageModel.getMainImageHeight() + "");

        Log.e("currentRatioIndex10", currentRatioIndex + "");
        show = imageHeight < pairDimens[(int) currentRatioIndex].second ||
                imageWidth < pairDimens[(int) currentRatioIndex].first;

        Log.e("currentRatioIndex11", currentRatioIndex + "");
//        imageWidth=mainImageModel.getMainImageWidth();
//        imageHeight=mainImageModel.getMainImageHeight();

//        if (imageHeight == pairDimens[(int) currentRatioIndex].second
//        && imageWidth == pairDimens[(int) currentRatioIndex].first){
//            show=false;
//        }else if (imageHeight == pairDimens[(int) currentRatioIndex].second
//                && imageWidth >= pairDimens[(int) currentRatioIndex].first){
//            show=false;
//        }else if (imageHeight >= pairDimens[(int) currentRatioIndex].second
//                && imageWidth == pairDimens[(int) currentRatioIndex].first){
//            show=false;
//        } else if (imageHeight >= pairDimens[(int) currentRatioIndex].second
//                && imageWidth >= pairDimens[(int) currentRatioIndex].first){
//            show=false;
//        }
//        if (ratio == 1f) {
//            if (imageHeight < 600 || imageWidth < 600)
//                show = true;
//            else
//                show = false;
//        } else if (ratio == ratios[1] || ratio == ratios[2]) {
//            if (imageHeight < 1000 || imageWidth < 1000)
//                show = true;
//            else
//                show = false;
//        } else if (ratio == ratios[3]) {
//            if (imageHeight < 600 || imageWidth < 1800)
//                show = true;
//            else
//                show = false;
//        } else {
//            if (imageHeight < 1000 || imageWidth < 3000)
//                show = true;
//            else
//                show = false;
//        }
//        /*else if(ratio == ratios[4]){
//            if (imageHeight < 1000 || imageWidth < 2400)
//                show = true;
//            else
//                show = false;
//        } else if(ratio == ratios[5]){
//            if (imageHeight < 1000 || imageWidth < 2400)
//                show = true;
//            else
//                show = false;
//        }


        isLowResolution = show;

        return show;
    }

//    private boolean checkLowResolutionView() {
//
//        if (ratio==1f){ //1000*1000
//
//            if (imageHeight<600 || imageWidth<600){  //20*20
//                isLowResolution=true;
//            }else if (imageHeight<800 || imageWidth<800){ //30*30
//                isLowResolution=true;
//            }else if (imageHeight<1000 || imageWidth<1000){//40*40
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",1f+" "+isLowResolution);
//        } else if (ratio == ratios[1] || ratio == ratios[2]){//20*30   //30*20
//
//            if (imageHeight < 600 || imageWidth < 800){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",ratio+" "+isLowResolution);
//    }else if (ratio==ratios[3]){
//
//            if (imageHeight < 1200 || imageWidth < 600){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",3+" "+isLowResolution);
//        }else if (ratio==ratios[4]){
//
//            if (imageHeight < 1200 || imageWidth < 800){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",4+" "+isLowResolution);
//        }else if (ratio==ratios[5]){
//
//            if (imageHeight < 1400 || imageWidth < 600){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",5+" "+isLowResolution);
//        }else if (ratio==ratios[7]){
//
//            if (imageHeight < 1000 || imageWidth < 800){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",7+" "+isLowResolution);
//        }else if (ratio==ratios[8]){
//
//            if (imageHeight < 1200 || imageWidth < 800){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",8+" "+isLowResolution);
//        }else if (ratio==ratios[9]){
//
//            if (imageHeight < 1000 || imageWidth < 1000){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",9+" "+isLowResolution);
//        }else if (ratio==ratios[10]){
//
//            if (imageHeight < 1200 || imageWidth < 1200){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",10+" "+isLowResolution);
//        }else if (ratio==ratios[11]){
//
//            if (imageHeight < 2000 || imageWidth < 2000){
//                isLowResolution=true;
//            }else {
//                isLowResolution=false;
//            }
//            Log.e("REsolution",11+" "+isLowResolution);
//        }
//        return isLowResolution;
//    }


    @Override
    public void showHidePixelsImage(boolean show) {
        show = checkLowResolutionView();
        lowPixelsIv.setVisibility(isLowResolution ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void TransmitNewPositions(float x, float y, int width, int height) {
        positionX = x;
        positionY = y;
        Log.e("getFactorHeight", mainImageModel.getFactorHeight() + "");
        Log.e("getFactorWidth", mainImageModel.getFactorWidth() + "");

        imageHeight = (int) (height * mainImageModel.getFactorHeight());
        imageWidth = (int) (width * mainImageModel.getFactorWidth());

        imageHeight = (int) (height * mainImageModel.getFactorHeight()) + 50;
        imageWidth = (int) (width * mainImageModel.getFactorWidth()) + 50;

        Log.e("FromActivityX_Y", "x is " + x + "     and Y= " + y + "    width is " + imageWidth + "    height is " + imageHeight);
        Log.e("FromActivityX_YBefore", "x is " + x + "     and Y= " + y + "    width is " + width + "    height is " + height);
        Log.e("==========", "=============================================================================================");

        showHidePixelsImage(true);
    }

    Observable customFilterImage(Filter filter, Bitmap mBitmap, boolean setImage) {

        return Observable.create(emitter -> {
            mFilter = filter;
            Bitmap bitmap = filter.processFilter(mBitmap.copy(Bitmap.Config.RGB_565, true));
            mainImageModel.setFilteredBitmap(bitmap);
            if (setImage)
                mGestureCropImageView.setImageBitmap(bitmap);

//            if (isFlip)
//                if (mGestureCropImageView.getRotation() == 0) {
//                    mainImageModel.setFilteredBitmap(mGestureCropImageView.flip(mGestureCropImageView.getViewBitmap(), false));
//                } else {
//                    mainImageModel.setFilteredBitmap(mGestureCropImageView.flip(mGestureCropImageView.getViewBitmap(), true));
//                }
//            mFilterRecycler.smoothScrollBy(0, 40);
            emitter.onComplete();
        })/*.subscribeOn(Schedulers.io())*/
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable filterImage(Filter filter) {

        return Observable.create(emitter -> {
            mFilter = filter;
            emitter.onComplete();
//            Bitmap bitmap = filter.processFilter(mainImageModel.getBitmap().copy(Bitmap.Config.RGB_565, true));
//            mainImageModel.setFilteredBitmap(bitmap);
//            mGestureCropImageView.setImageBitmap(bitmap);
//            if (isFlip)
//                if (mGestureCropImageView.getRotation() == 0) {
//                    mainImageModel.setFilteredBitmap(mGestureCropImageView.flip(mGestureCropImageView.getViewBitmap(), false));
//                } else {
//                    mainImageModel.setFilteredBitmap(mGestureCropImageView.flip(mGestureCropImageView.getViewBitmap(), true));
//                }
//            mFilterRecycler.smoothScrollBy(0, 40);

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onFilterSelected(Filter filter) {
        mFilter = filter;

        Bitmap bitmap = filter.processFilter
                (mainImageModel.getBitmap().
                        copy(Bitmap.Config.RGB_565, true));

        mainImageModel.setFilteredBitmap(bitmap);


        if (brightness != 0)
            bitmap = (ColorMatrixUtils.changeBitmapBrightness(bitmap,
                    brightness));

        //apply contrast
        if (contrast != 0)
            bitmap = (ColorMatrixUtils.changeBitmapContrast(bitmap, contrast));

        //apply hue
        if (hue != 0)
            bitmap = (mGestureCropImageView.adjustHue(bitmap, hue));

        //apply saturation
        if (saturation != 0)
            bitmap = (updateSat(bitmap, saturation));


        mGestureCropImageView.setImageBitmap(bitmap);

        mAdapter.setSelectedFilter(mFilter);
//        isRotateEstablished = true;
    }


//        customFilterImage(mFilter , mGestureCropImageView.getViewBitmap() ,true);


//        if (mainImageModel != null) {
//            try {
//
//                customFilterImage(filter, mainImageModel.getBitmap(),false)
//                        .subscribe(new Observer() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//                            }
//
//                            @Override
//                            public void onNext(Object o) {
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                //apply brightness
//                                if (brightness != 0)
//                                    mGestureCropImageView.setImageBitmap(ColorMatrixUtils.changeBitmapBrightness(mGestureCropImageView.getViewBitmap(),
//                                            brightness));
//
//                                //apply contrast
//                                if (contrast != 0)
//                                    mGestureCropImageView.setImageBitmap(ColorMatrixUtils.changeBitmapContrast(mGestureCropImageView.getViewBitmap(), contrast));
//
//                                //apply hue
//                                if (hue != 0)
//                                    mGestureCropImageView.setImageBitmap(mGestureCropImageView.adjustHue(mGestureCropImageView.getViewBitmap(), hue));
//
//                                //apply saturation
//                                if (saturation != 0)
//                                    mGestureCropImageView.setImageBitmap(updateSat(mGestureCropImageView.getViewBitmap(), saturation));
//
//                                customFilterImage(mFilter , mGestureCropImageView.getViewBitmap() ,true);
//
//                            }
//                        });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;


                    if (isSelectOneImage) {


                        Glide.with(EditImageActivity.this)
                                .asBitmap()
                                .load(photos.get(0))
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap,
                                                                Transition<? super Bitmap> transition) {
                                        int imageWidth = bitmap.getWidth();
                                        int imageHeight = bitmap.getHeight();

                                        mGestureCropImageView.invalidate();
                                        moOverlayView.invalidate();
                                        uCropView.invalidate();

//                        if (mainImageModel.getCurrentRatio() ==1f) {
                                        mGestureCropImageView.setTargetAspectRatio(2.5f);
//                        } else {
//                            mGestureCropImageView.setTargetAspectRatio(1f);
//                        }

                                        File file = new File(photos.get(0));
                                        uri = Uri.fromFile(file);
                                        Log.e("uriiii2", uri + "");
                                        mainImageModel = new ImageModel(uri, 1f);
                                        mainImageModel.setCurrentScale(1f);
                                        mainImageModel.setCurrentAngle(0f);
//                                        BitmapFactory.decodeFile(photos.get(0), options);
//                                        int imageHeight = options.outHeight;
//                                        int imageWidth = options.outWidth;
//                        mainImageModel.setmCropRect(new RectF(0, 0, imageWidth, imageHeight));
                                        mainImageModel.setMainImageHeight(imageHeight);
                                        mainImageModel.setMainImageWidth(imageWidth);

                                        mainImageModel.setImageHeight(imageHeight);
                                        mainImageModel.setImageWidth(imageWidth);

                                        mainImageModel.setQuantity(1);
                                        mainImageModel.setPath(photos.get(0));

                                        brightness = 0;
                                        hue = 0;
                                        saturation = 0;
                                        contrast = 0;
                                        rotateDegree = 0;

                                        isInitial = false;
                                        tempMatrix = null;
                                        mFilter = new Filter();

                                        lightSeekBar.setOnSeekBarChangeListener(null);
                                        contrastSeekBar.setOnSeekBarChangeListener(null);
                                        hueSeekBar.setOnSeekBarChangeListener(null);
                                        saturationSeekbar.setOnSeekBarChangeListener(null);

                                        lightSeekBar.setProgress(50);
                                        contrastSeekBar.setProgress(10);
                                        saturationSeekbar.setProgress(256);
                                        hueSeekBar.setProgress(0);

                                        adjustBrightness();
                                        adjustContrast();
                                        adjustHueMethod();


                                        positionY = 0;
                                        positionX = 0;
                                        mCurrentScale = 1;
                                        imageWidth = imageWidth;
                                        imageHeight = imageHeight;

                                        isFlip = false;

                                        setRatioFromWidthAndHeight();

                                        ratio = mainImageModel.getCurrentRatio();

                                        Log.e("currentRatioIndex12", mainImageModel.getCurrentRatioIndex() + "");
                                        currentRatioIndex = mainImageModel.getCurrentRatioIndex();


                                        moOverlayView.setCropGridColumnCount(0);
                                        isSegmented = false;
                                        mainImageModel.setSegmented(isSegmented);
                                        moOverlayView.drawCropGrid(new Canvas());

                                        moOverlayView.setCropGridRowCount(pairPieces[(int) currentRatioIndex].second);
                                        moOverlayView.setCropGridColumnCount(pairPieces[(int) currentRatioIndex].first);
                                        chooseCurrentSelectedRationBackground(currentRatioIndex);
//                                        moOverlayView.drawCropGrid(new Canvas());

                                        prepareImageFromShippingActivity(false);
                                    }
                                });
                    }
                }
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isSelectOneImage) {
                        replaceImage();
                    } else {
                        pickFromGallery();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view.getId() == repalceIv.getId()) {
            showReplaceDialog(this);
//            drawSecondaryGrid(true);
        } else if (view.getId() == borderIv.getId()) {
            uCropView.changeBorderVisibilty();
        } else if (view.getId() == deleteImage.getId()) {
            showDeleteDialog(this);
        } else if (view.getId() == R.id.submit) {
            if (isLowResolution) {
                Toast toast = Toast.makeText(this, getString(R.string.can_not_submit), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                mainImageModel.setmExifInfo(mGestureCropImageView.getExifInfo());
                mainImageModel.setmImageInputPath(mGestureCropImageView.getImageInputPath());
                mainImageModel.setmImageOutputPath(mGestureCropImageView.getImageOutputPath());

                mainImageModel.setCurrentBrightness(brightness);
                mainImageModel.setCurrentContrast(contrast);
                mainImageModel.setHue(hue);
                mainImageModel.setSaturation(saturation);
                mainImageModel.setCurrentRatio(ratio);
                mainImageModel.setCurrentRatioIndex(currentRatioIndex);
                setResultUri(Uri.fromFile(new File(mainImageModel.getPath())));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showReplaceDialog(Context context) {

        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.replace_layout);
        mDialog.setCancelable(false);

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
//            onReplaceImage();
            replaceImage();
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.close_popup).setOnClickListener(v -> {
            mDialog.dismiss();
        });

        mDialog.show();

    }

    private void showDeleteDialog(Context context) {
        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.delete_popup);
        mDialog.setCancelable(false);

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            onDeleteImage();
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });
        mDialog.show();
    }


    private Bitmap fastFlip(Bitmap bInput) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1f, 1f);
        return Bitmap.createBitmap(bInput, 0, 0, bInput.getWidth(), bInput.getHeight(), matrix, true);
    }


    private void prepareImageFromShippingActivity(boolean addAdjsutMethods) {

        Log.e("HeightandWidthaaaaa", mainImageModel.getMainImageWidth() + ">>" + mainImageModel.getMainImageHeight());
        try {
            String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            mGestureCropImageView.setImageUriSecond(uri, Uri.fromFile(new File(downloadsDirectoryPath, uri.getLastPathSegment())),
                    mainImageModel.getMainImageWidth(),
                    mainImageModel.getMainImageHeight(),
                    (bitmap, factorW, factorH) -> {

                        mainImageModel.setFactorWidth(factorW);
                        mainImageModel.setFactorHeight(factorH);

                        prepareThumbnail(bitmap);

                        if (addAdjsutMethods) {
                            if (this.mainImageModel.isFlipped()) {
                                bitmap = fastFlip(bitmap);
                            }
                        } else {

                            mGestureCropImageView.destroyDrawingCache();
                            mGestureCropImageView.setImageResource(android.R.color.transparent);
                            mGestureCropImageView.setImageMatrix(new Matrix(), false);
                            mGestureCropImageView.setmCropRect(moOverlayView.getCropViewRect());

                            mGestureCropImageView.setImageBitmap(bitmap);
                        }


                        Log.e("factorW, ", factorW + "");
                        Log.e("factorH, ", factorH + "");

                        if (mainImageModel.getBitmap() == null) {
                            mainImageModel.setBitmap(bitmap);
                            mainImageModel.setFilteredBitmap(bitmap);
                            mainImageModel.setScreenShotImage(bitmap);
                        } else {

//                            if (this.mainImageModel.isFlipped()) {
//                                mainImageModel.setBitmap(fastFlip(mainImageModel.getBitmap()));
//                                mainImageModel.setFilteredBitmap(fastFlip(mainImageModel.getFilteredBitmap()));
//                            }
                        }


                        if (addAdjsutMethods) {
                            if (mainImageModel.getCurrentFilter() != null) {
                                bitmap = mainImageModel.getCurrentFilter().processFilter
                                        (bitmap.copy(Bitmap.Config.RGB_565, true));
                                mainImageModel.setFilteredBitmap(bitmap);

                                if (brightness != 0)
                                    bitmap = (ColorMatrixUtils.changeBitmapBrightness(bitmap,
                                            brightness));

                                if (contrast != 0)
                                    bitmap = (ColorMatrixUtils.changeBitmapContrast(bitmap, contrast));

                                if (hue != 0 && hue != 1)
                                    bitmap = (mGestureCropImageView.adjustHue(bitmap, hue));


                                if (saturation != 0)
                                    bitmap = (updateSat(bitmap, saturation));

                            } else {
                                if (this.brightness != 0f) {
                                    bitmap = (ColorMatrixUtils.changeBitmapBrightness
                                            (bitmap, this.brightness));
                                }

                                if (this.contrast != 0f) {
                                    bitmap = (ColorMatrixUtils.changeBitmapContrast
                                            (bitmap, this.contrast));
                                }

                                if (this.hue != 0f && hue != 1f) {
                                    bitmap = (this.mGestureCropImageView.adjustHue
                                            (bitmap, this.hue));
                                }

                                if (this.saturation != 0f && this.saturation != 1f) {
                                    bitmap = (this.updateSat
                                            (bitmap, this.saturation));
                                }
                            }
                            mGestureCropImageView.setImageBitmap(bitmap);
                        }

                        mGestureCropImageView.setTargetAspectRatio(mainImageModel.getCurrentRatio());
                        isRotateEstablished = true;

                        chooseCurrentSelectedRationBackground(mainImageModel.getCurrentRatio());

//                        if (mainImageModel.getPositionX() != 0 && mainImageModel.getPositionY() != 0) {
                        if (mainImageModel.getMatrix() != null) {

                            final float oldScale = mGestureCropImageView.getCurrentScale();
                            final float deltaScale = mainImageModel.getCurrentScale() - oldScale;

                            //zoom corrct with no position correct
                            mGestureCropImageView.zoomImageToPosition(mainImageModel.getCurrentScale(),
                                    mainImageModel.getmCropRect().centerX(),
                                    mainImageModel.getmCropRect().centerY(),
                                    500);

                            //zoom false with position correct
                            mGestureCropImageView.post(new ZoomImageToPosition(700, oldScale, deltaScale, 0, 0));


                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void prepareImage(Uri uri, int requiredWidth, int requiredHeight) {
//
//        try {
//
//            uCropView.resetCropImageView();
////            String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
////            Uri.fromFile(new File(downloadsDirectoryPath, uri.getLastPathSegment()))
//            mGestureCropImageView.setImageUriSecond(uri, uri,
//                    requiredWidth,
//                    requiredHeight,
//                    (bitmap, factorW, factorH) -> {
//
//                        mainImageModel.setFactorWidth(factorW);
//                        mainImageModel.setFactorHeight(factorH);
//
////                        if (mainImageModel.getBitmap() == null) {
//                        mainImageModel.setBitmap(bitmap);
//                        mainImageModel.setFilteredBitmap(bitmap);
//                        mainImageModel.setScreenShotImage(bitmap);
////                        }
//                        mGestureCropImageView.setImageBitmap(bitmap);
//
////                        adjustBrightness();
////                        adjustContrast();
////                        adjustHueMethod();
//
//
//                        prepareThumbnail(bitmap);
//
////                        if (mainImageModel.getCurrentRatio() == 0f) {
//
//                        if (mainImageModel.getMainImageHeight() == mainImageModel.getMainImageWidth()) {
//                            mainImageModel.setCurrentRatio(1f);
//                            chooseCurrentSelectedRationBackground(1f);
//                        } else if (mainImageModel.getMainImageHeight() > mainImageModel.getMainImageWidth()) {
//                            if (mainImageModel.getMainImageHeight() >= 1000 && mainImageModel.getMainImageWidth() >= 1000 &&
//                                    (((float) mainImageModel.getMainImageHeight()) / 1.5f) >= 1000) {
//                                mainImageModel.setCurrentRatio(8f / 12);
//                                chooseCurrentSelectedRationBackground(8f / 12);
//                            } else {
//                                mainImageModel.setCurrentRatio(1f);
//                                chooseCurrentSelectedRationBackground(1f);
//                            }
//                        } else if (mainImageModel.getMainImageHeight() < mainImageModel.getMainImageWidth()) {
//                            if (mainImageModel.getMainImageHeight() >= 1000 && mainImageModel.getMainImageWidth() >= 1000 &&
//                                    (((float) mainImageModel.getMainImageWidth()) / 1.5f) >= 1000) {
//                                mainImageModel.setCurrentRatio(12f / 8f);
//                                chooseCurrentSelectedRationBackground(12f / 8f);
//                            } else {
//                                mainImageModel.setCurrentRatio(1f);
//                                chooseCurrentSelectedRationBackground(1f);
//                            }
//                        } else {
//                            mainImageModel.setCurrentRatio(1f);
//                            chooseCurrentSelectedRationBackground(1f);
//                        }
//
//                        moOverlayView.setCropGridColumnCount(0);
//                        isSegmented = false;
//                        mainImageModel.setSegmented(isSegmented);
//                        moOverlayView.drawCropGrid(new Canvas());
//
//                        mGestureCropImageView.setTargetAspectRatio(mainImageModel.getCurrentRatio());
//
//                        mGestureCropImageView.setImageToWrapCropBounds();
//
//                    });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void chooseCurrentSelectedRationBackground(float ratio) {
        for (int i = 0; i < ratio_holder.getChildCount(); i++) {
            if (currentRatioIndex == i)
                ratio_holder.getChildAt(i).setBackground(getDrawable(R.drawable.background_selected_bordered));
            else
                ratio_holder.getChildAt(i).setBackground(getDrawable(R.drawable.background_selected));
        }
    }

    @Override
    public void onItemClick(int position) {


    }

    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {

    }

    @Override
    public void onAddViewListener() {

    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener() {

    }

    @Override
    public void onStopViewChangeListener() {

    }


    public void onDeleteImage() {
        setResultUri(null);
    }


    private class ZoomImageToPosition implements Runnable {

        private final long mDurationMs, mStartTime;
        private final float mOldScale;
        private final float mDeltaScale;
        private final float mDestX;
        private final float mDestY;


        public ZoomImageToPosition(long durationMs,
                                   float oldScale, float deltaScale,
                                   float destX, float destY) {


            mStartTime = System.currentTimeMillis();
            mDurationMs = durationMs;
            mOldScale = oldScale;
            mDeltaScale = deltaScale;
            mDestX = destX;
            mDestY = destY;

        }

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            float currentMs = Math.min(mDurationMs, now - mStartTime);

            if (currentMs < mDurationMs) {
                Matrix msupprtMatrix = mainImageModel.getMatrix();


                Matrix mDrawMatrix = new Matrix();
                mDrawMatrix.set(mGestureCropImageView.getMatrix());
                mDrawMatrix.postConcat(msupprtMatrix);
//                mDrawMatrix.postScale(
//                        mainImageModel.getCurrentScale(),
//                        mainImageModel.getCurrentScale()
//                );
                mGestureCropImageView.setImageMatrix(mDrawMatrix, false);
            } else {
                mGestureCropImageView.setImageToWrapCropBounds();
            }
        }
    }

    public void setRatioFromWidthAndHeight(){

        if (mainImageModel.getMainImageHeight() == mainImageModel.getMainImageWidth()) {
            mainImageModel.setCurrentRatio(20f/20f);
            chooseCurrentSelectedRationBackground(20f/20f);

        } else if (mainImageModel.getMainImageHeight() > mainImageModel.getMainImageWidth()) {

            if (mainImageModel.getMainImageHeight() >= 600 && mainImageModel.getMainImageWidth() >= 600 &&
                    (((float) mainImageModel.getMainImageHeight()) / 1.5f) >= 600) {
                mainImageModel.setCurrentRatio(20f / 30f);
                chooseCurrentSelectedRationBackground(20f / 30f);
            } else {
                mainImageModel.setCurrentRatio(20f/20f);
                chooseCurrentSelectedRationBackground(20f/20f);
            }

        } else if (mainImageModel.getMainImageHeight() < mainImageModel.getMainImageWidth()) {

            if (mainImageModel.getMainImageHeight() >= 600 && mainImageModel.getMainImageWidth() >= 600 &&
                    (((float) mainImageModel.getMainImageWidth()) / 1.5f) >= 600) {
                mainImageModel.setCurrentRatio(30f / 20f);
                chooseCurrentSelectedRationBackground(30f / 20f);
            } else {
                mainImageModel.setCurrentRatio(20f/20f);
                chooseCurrentSelectedRationBackground(20f/20f);
            }

        } else {
            mainImageModel.setCurrentRatio(20f/20f);
            chooseCurrentSelectedRationBackground(20f/20f);
        }
    }
}
