package com.WattanArt.artcomponent;

import android.app.ActionBar;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.WattanArt.R;


public class ComponentActivity extends AppCompatActivity {

    int height,width;
//    private Matrix matrix = new Matrix();
//    private Matrix savedMatrix = new Matrix();
//    private PointF start = new PointF();
    CoverView cover;
    ImageCaseComponent component;
    DimensionData dimensionData;
    AccessoriesView accessoriesView;

    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    public static String fileNAME;
    public static int framePos = 0;

    private float scale = 0;
    private float newDist = 0;

    // Fields
    private String TAG = this.getClass().getSimpleName();

    // We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    // Remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_layout_view);

//        final ImageView iv = new ImageView(this);
//        iv.setImageDrawable(getDrawable(R.drawable.huaweiy9));
//        height=iv.getMeasuredHeight();
//        width=iv.getMeasuredWidth();
//        Log.e("sssssss",height+"");
//        Log.e("wwwwwww",width+"");

         component = findViewById(R.id.componentView);




        dimensionData = new DimensionData(700, 1024, 430,
                1000, 0, 0, 40, 700,
                1024, 0, 0);

        component.initData(dimensionData, new Pair<>(R.drawable.huaweiy9,R.drawable.huaweiy9_2));

         cover = (CoverView) component.getComponentView(R.id.cover);
        cover.setData(R.drawable.test);

         accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
        accessoriesView.setData(R.drawable.huaweiy9_2);

//        cover.setOnTouchListener(this);
//        cover.setZoom(3.5f, 0.65f, 0.098f);
        cover.setMaxZoom(20f);
    }


    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);

        return (float) Math.toDegrees(radians);
    }
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // Handle touch events here...
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                savedMatrix.set(matrix);
//                start.set(event.getX(), event.getY());
//                mode = DRAG;
//                lastEvent = null;
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                oldDist = spacing(event);
//                if (oldDist > 10f) {
//                    savedMatrix.set(matrix);
//                    midPoint(mid, event);
//                    mode = ZOOM;
//                }
//                lastEvent = new float[4];
//                lastEvent[0] = event.getX(0);
//                lastEvent[1] = event.getX(1);
//                lastEvent[2] = event.getY(0);
//                lastEvent[3] = event.getY(1);
//                d = rotation(event);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:
//                mode = NONE;
//                lastEvent = null;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (mode == DRAG) {
//                    // ...
//                    matrix.set(savedMatrix);
//                    matrix.postTranslate(event.getX() - start.x, event.getY()
//                            - start.y);
//                } else if (mode == ZOOM && event.getPointerCount() == 2) {
//                    float newDist = spacing(event);
//                    matrix.set(savedMatrix);
//                    if (newDist > 10f) {
//                        float scale = newDist / oldDist;
//                        matrix.postScale(scale, scale, mid.x, mid.y);
//                    }
//                    if (lastEvent != null) {
//                        newRot = rotation(event);
//                        float r = newRot - d;
//                        matrix.postRotate(r, cover.getMeasuredWidth() / 2f,
//                                cover.getMeasuredHeight() / 2f);
//                    }
//                }
//                break;
//        }
//
//        cover.setImageMatrix(matrix);
//
//        return true;
//    }


    //    void testDate(){
//        dimensionData = new DimensionData(700, 1024,
//
//                295, 624,
//
//                0, 0,
//
//                0,
//
//                240, 240,
//
//                230,230);
//
//
//        component.initData(dimensionData, new Pair<>(R.drawable.tshirt_test,R.drawable.test));
//
//        CoverView cover = (CoverView) component.getComponentView(R.id.cover);
//        cover.setData(R.drawable.test);
//
//        AccessoriesView accessoriesView = (AccessoriesView) component.getComponentView(R.id.accessories);
//        accessoriesView.setData(R.drawable.ic_8_12);
//
//        cover.setZoom(3.5f, 0.65f, 0.098f);
//    }

    public void rotateImage(){
        Matrix matrix = new Matrix();
        cover.setScaleType(ImageView.ScaleType.MATRIX);   //required
//        matrix.postRotate((float) angle, pivotX, pivotY);


        matrix.postRotate( 90f,
                cover.getDrawable().getBounds().width()/2f,
                cover.getDrawable().getBounds().height()/2f);
        cover.setImageMatrix(matrix);
    }

}
