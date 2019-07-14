package com.yalantis.ucrop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalantis.ucrop.R;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.callback.MultiTouchListener;
import com.yalantis.ucrop.callback.OnPhotoEditorListener;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;
import com.yalantis.ucrop.util.RotationGestureDetector;

/**
 * Created by Oleksii Shliama (https://github.com/shliama).
 */
public class GestureCropImageView extends CropImageView {

    private static final int DOUBLE_TAP_ZOOM_DURATION = 200;

    private ScaleGestureDetector mScaleDetector;
    private RotationGestureDetector mRotateDetector;
    private GestureDetector mGestureDetector;
    private LayoutInflater mLayoutInflater;
    private Typeface mDefaultTextTypeface;

    private float mMidPntX, mMidPntY;
    private OnPhotoEditorListener mOnPhotoEditorListener;

    private boolean mIsRotateEnabled = false, mIsScaleEnabled = true, mIsGestureEnable = true;
    private int mDoubleTapScaleSteps = 0;

    public GestureCropImageView(Context context) {
        super(context);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface ImageClickListner {
        void onImageClick();
    }

    public GestureCropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GestureCropImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setScaleEnabled(boolean scaleEnabled) {
        mIsScaleEnabled = scaleEnabled;
    }

    public void setOnPhotoEditorListener(@NonNull OnPhotoEditorListener onPhotoEditorListener) {
        this.mOnPhotoEditorListener = onPhotoEditorListener;
    }

    public void setGestureEnabled(boolean gestureEnable) {
        mIsGestureEnable = gestureEnable;
    }

    private View getLayout() {
        View rootView = mLayoutInflater.inflate(R.layout.view_photo_editor_text, null);
        TextView txtText = rootView.findViewById(R.id.tvPhotoEditorText);
//        if (txtText != null && mDefaultTextTypeface != null) {
        txtText.setGravity(Gravity.CENTER);
//            if (mDefaultEmojiTypeface != null) {
        mDefaultTextTypeface = Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/GOTHIC_2.TTF");

        txtText.setTypeface(mDefaultTextTypeface);

        return rootView;
    }


    /**
     * Create a new instance and scalable touchview
     *
     * @return scalable multitouch listener
     */
    @NonNull
    private MultiTouchListener getMultiTouchListener() {
        MultiTouchListener multiTouchListener = new MultiTouchListener(
                mOnPhotoEditorListener);
        //multiTouchListener.setOnMultiTouchListener(this);

        return multiTouchListener;
    }

    public void addText(String text, final int colorCodeTextView) {
        addText(null, text, colorCodeTextView);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void addText(@Nullable Typeface textTypeface, String text, final int colorCodeTextView) {
        final View textRootView = getLayout();
        final TextView textInputTv = textRootView.findViewById(R.id.tvPhotoEditorText);
        final ImageView imgClose = textRootView.findViewById(R.id.imgPhotoEditorClose);
        final FrameLayout frmBorder = textRootView.findViewById(R.id.frmBorder);

        textInputTv.setText(text);
        textInputTv.setTextColor(colorCodeTextView);
        if (textTypeface != null) {
            textInputTv.setTypeface(textTypeface);
        }
        MultiTouchListener multiTouchListener = getMultiTouchListener();

        multiTouchListener.setOnGestureControl(new MultiTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                boolean isBackgroundVisible = frmBorder.getTag() != null && (boolean) frmBorder.getTag();
                frmBorder.setBackgroundResource(isBackgroundVisible ? 0 : R.drawable.rounded_border_tv);
                imgClose.setVisibility(isBackgroundVisible ? View.GONE : View.VISIBLE);
                frmBorder.setTag(!isBackgroundVisible);
            }

            @Override
            public void onLongClick() {
                String textInput = textInputTv.getText().toString();
                int currentTextColor = textInputTv.getCurrentTextColor();
                if (mOnPhotoEditorListener != null) {
                    mOnPhotoEditorListener.onEditTextChangeListener(textRootView, textInput, currentTextColor);
                }
            }
        });

        textRootView.setOnTouchListener(multiTouchListener);
        addViewToParent(textRootView);
    }

    /**
     * Add to root view from image,emoji and text to our parent view
     *
     * @param rootView rootview of image,text and emoji
     */
    private void addViewToParent(View rootView) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        ((ViewGroup) getParent()).addView(rootView, params);

        if (mOnPhotoEditorListener != null)
            mOnPhotoEditorListener.onAddViewListener();
    }

    public boolean isScaleEnabled() {
        return mIsScaleEnabled;
    }

    public void setRotateEnabled(boolean rotateEnabled) {
        mIsRotateEnabled = rotateEnabled;
    }

    public boolean isRotateEnabled() {
        return mIsRotateEnabled;
    }

    public void setDoubleTapScaleSteps(int doubleTapScaleSteps) {
        mDoubleTapScaleSteps = doubleTapScaleSteps;
    }

    public int getDoubleTapScaleSteps() {
        return mDoubleTapScaleSteps;
    }

    /*
     * If it's ACTION_DOWN event - user touches the screen and all current animation must be canceled.
     * If it's ACTION_UP event - user removed all fingers from the screen and current image position must be corrected.
     * If there are more than 2 fingers - update focal point coordinates.
     * Pass the event to the gesture detectors if those are enabled.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
            cancelAllAnimations();
        }


        if (event.getPointerCount() > 1) {
            mMidPntX = (event.getX(0) + event.getX(1)) / 2;
            mMidPntY = (event.getY(0) + event.getY(1)) / 2;
        }

        if (mIsGestureEnable)
            mGestureDetector.onTouchEvent(event);


        if (mIsScaleEnabled) {
            mScaleDetector.onTouchEvent(event);
        }

        if (mIsRotateEnabled) {
            mRotateDetector.onTouchEvent(event);
        }

        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
            setImageToWrapCropBounds();
        }

        return true;
    }

    @Override
    protected void init() {
        super.init();
        setupGestureListeners();
    }

    /**
     * This method calculates target scale value for double tap gesture.
     * User is able to zoom the image from min scale value
     * to the max scale value with  {@link #mDoubleTapScaleSteps} double taps.
     */
    protected float getDoubleTapTargetScale() {
//        return getCurrentScale() * (float) Math.pow(getMaxScale() / getMinScale(), 1.0f / mDoubleTapScaleSteps);
        return 0;
    }

    private void setupGestureListeners() {
        mGestureDetector = new GestureDetector(getContext(), new GestureListener(), null, true);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mRotateDetector = new RotationGestureDetector(new RotateListener());
    }


    public Bitmap adjustHue(Bitmap bmp, float value) {
        Bitmap ret = null;
        try {
            value = cleanValue(value, 180f) / 180f * (float) Math.PI;

//        if (value == 0) {
//            return null;
//        }

            float cosVal = (float) Math.cos(value);
            float sinVal = (float) Math.sin(value);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            ColorMatrix cm = new ColorMatrix(new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f
                    });

            ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
//            ret = BitmapLoadUtils.convertToMutable(bmp);

            Canvas canvas = new Canvas(ret);

            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            canvas.drawBitmap(bmp, 0, 0, paint);


        } catch (OutOfMemoryError outOfMemoryError) {
            Log.e("adjustHueOutOfMemory", "OutOfMemoryError in this method  tag");
        }
        return ret;
    }

    private float cleanValue(float p_val, float p_limit) {
        return Math.min(p_limit, Math.max(-p_limit, p_val));
    }


//    public Bitmap changeBitmapContrast(Bitmap bmp, int contrast) {
//        ColorMatrix cm = new ColorMatrix(new float[]{
//                contrast, 0, 0, 0, 0,
//                0, contrast, 0, 0, 0,
//                0, 0, contrast, 0, 0,
//                0, 0, 0, 1, 0});
//
////        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
//        Bitmap ret = BitmapLoadUtils.convertToMutable(bmp);
//        ;
//
//        Canvas canvas = new Canvas(ret);
//        Paint paint = new Paint();
//
//        paint.setColorFilter(new ColorMatrixColorFilter(cm));
//        canvas.drawBitmap(bmp, 0, 0, paint);
//
//        return ret;
//    }


//    public Bitmap changeBitmapContrast(Bitmap src, float value) {
//        // image size
//        int width = src.getWidth();
//        int height = src.getHeight();
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//        // color information
//        int A, R, G, B;
//        int pixel;
//        // get contrast value
//        double contrast = Math.pow((100 + value) / 100, 2);
//
//        // scan through all pixels
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                A = Color.alpha(pixel);
//                // apply filter contrast for every channel R, G, B
//                R = Color.red(pixel);
//                R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                if (R < 0) {
//                    R = 0;
//                } else if (R > 255) {
//                    R = 255;
//                }
//
//                G = Color.red(pixel);
//                G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                if (G < 0) {
//                    G = 0;
//                } else if (G > 255) {
//                    G = 255;
//                }
//
//                B = Color.red(pixel);
//                B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                if (B < 0) {
//                    B = 0;
//                } else if (B > 255) {
//                    B = 255;
//                }
//
//                // set new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
//
//        // return final image
//        return bmOut;
//
//    }

//    public Bitmap adjustedContrast(Bitmap src, double value) {
//        // image size
//        int width = src.getWidth();
//        int height = src.getHeight();
//        // create output bitmap
//        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//        // color information
//        int A, R, G, B;
//        int pixel;
//        // get contrast value
//        double contrast = Math.pow((100 + value) / 100, 2);
//
//        // scan through all pixels
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                A = Color.alpha(pixel);
//                // apply filter contrast for every channel R, G, B
//                R = Color.red(pixel);
//                R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                if (R < 0) {
//                    R = 0;
//                } else if (R > 255) {
//                    R = 255;
//                }
//
//                G = Color.red(pixel);
//                G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                if (G < 0) {
//                    G = 0;
//                } else if (G > 255) {
//                    G = 255;
//                }
//
//                B = Color.red(pixel);
//                B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//                if (B < 0) {
//                    B = 0;
//                } else if (B > 255) {
//                    B = 255;
//                }
//
//                // set new pixel color to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
//
//        // return final image
//        return bmOut;
//    }


    public Bitmap flip(Bitmap src, boolean rotated) {
        Matrix matrix = new Matrix();
        if (rotated) {
            matrix.postScale(1.0f, -1.0f, src.getWidth(), src.getHeight());
        } else {
            matrix.postScale(-1.0f, 1.0f, src.getWidth(), src.getHeight());
        }
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public void flip() {
        Matrix matrix = getmCurrentImageMatrix();

//        if(type == Direction.VERTICAL) {
//            matrix.preScale(1.0f, -1.0f);
//        }
//        else if(type == Direction.HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
//        } else {
//            return src;
//        }

        mCurrentImageMatrix.set(matrix);
        setImageMatrix(mCurrentImageMatrix , true);
        setImageToWrapCropBounds(true);
//        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            postScale(detector.getScaleFactor(), mMidPntX, mMidPntY);
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            Log.e("DoubleTapX" , ""+e.getX());
            Log.e("DoubleTapX_Y" , ""+e.getY());
//            zoomImageToPosition(getDoubleTapTargetScale(), e.getX(), e.getY(), DOUBLE_TAP_ZOOM_DURATION);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            postTranslate(-distanceX, -distanceY);
            getImageState(false);
            return true;
        }
    }

    public void setImageUriSecond(@NonNull Uri imageUri, @Nullable Uri outputUri, final int requredWidth, final int requiredHeight,
                                  final returnBitmap returnBitmap) throws Exception {
        final int maxBitmapSize = getMaxBitmapSize();

        mInputUri = imageUri;
        mOutputUri = outputUri;


        BitmapLoadUtils.decodeBitmapInBackground(getContext(), imageUri, outputUri, maxBitmapSize, maxBitmapSize,
                new BitmapLoadCallback() {

                    @Override
                    public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String imageInputPath, @Nullable String imageOutputPath) {
                        final double factorHeight = ((double) requiredHeight) / ((double) bitmap.getHeight());
                        final double factorWidth = ((double) requredWidth) / ((double) bitmap.getWidth());

                        Log.e("Width(setImageUriSecod)", bitmap.getWidth() + "   =W=   " + requredWidth);
                        Log.e("Width(setImageUriSecod)", bitmap.getHeight() + "   =H=   " + requiredHeight);
                        Log.e("Width(setImageUriSecod)", "requredWidth = " + requredWidth + "   =F_W=   " + factorWidth);
                        Log.e("Width(setImageUriSecod)", "requiredHeight = " + requiredHeight + "   =F_H=   " + factorHeight);

                        mImageInputPath = imageInputPath;
                        mImageOutputPath = imageOutputPath;
                        mExifInfo = exifInfo;

                        mBitmapDecoded = true;
//                        setImageBitmap(bitmap);
                        returnBitmap.returnBitmap(bitmap, factorWidth, factorHeight);
//                        setImageToWrapCropBounds();
                    }

                    @Override
                    public void onFailure(@NonNull Exception bitmapWorkerException) {
                        Log.e("setImageUriSecond", "onFailure: setImageUri", bitmapWorkerException);
                        if (mTransformImageListener != null) {
                            mTransformImageListener.onLoadFailure(bitmapWorkerException);
                        }
                    }

                    @Override
                    public void afterLoadComplete() {

                    }
                });
    }


    private class RotateListener extends RotationGestureDetector.SimpleOnRotationGestureListener {

        @Override
        public boolean onRotation(RotationGestureDetector rotationDetector) {
            postRotate(rotationDetector.getAngle(), mMidPntX, mMidPntY);
            return true;
        }

    }
}
