package com.WattanArt.ui.EditImage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;


import com.yalantis.ucrop.util.BitmapLoadUtils;

/**
 * Created by khaled.badawy on 5/22/2018.
 */


/*

this.seekBar.setSteps(1024);
      this.seekBar.setMin(-1.0F);
      this.seekBar.setMax(1.0F);

case BRIGHTNESS:
         this.colorAdjustmentTool.setBrightness(value);
         break;
      case CONTRAST:
         this.colorAdjustmentTool.setContrast(value > 0.0F ? value * 2.0F : value);
         break;
      case GAMMA:
         this.colorAdjustmentTool.setGamma(1.0F + (value > 0.0F ? value * 2.0F : value * 0.5F));
         break;
      case SATURATION:
         this.colorAdjustmentTool.setSaturation(value);

 */
public class ColorMatrixUtils {

    public static ColorMatrix generateExposureMatrix(float exposure) {
        float scale = (float) Math.pow(2.0D, (double) exposure);
        float[] array = new float[]{scale, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F};
        return new ColorMatrix(array);
    }


    public static ColorMatrix generateSaturationMatrix(float saturation) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(saturation + 1.0F);
        return matrix;
    }


    public static ColorMatrix generateContrastMatrix(float contrast) {
        float scale = contrast + 1.0F;
        float translate = (-0.5F * scale + 0.5F) * 255.0F;
        float[] array = new float[]{
                scale, 0.0F, 0.0F, 0.0F, translate,
                0.0F, scale, 0.0F, 0.0F, translate,
                0.0F, 0.0F, scale, 0.0F, translate,
                0.0F, 0.0F, 0.0F, 1.0F, 0.0F};
        return new ColorMatrix(array);
    }


    public static ColorMatrix generateBrightnessMatrix(float brightness) {
        float translate = brightness * 255.0F;
        float[] array = new float[]{1.0F, 0.0F, 0.0F, 0.0F, translate, 0.0F, 1.0F, 0.0F, 0.0F, translate,
                0.0F, 0.0F, 1.0F, 0.0F, translate, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F};
        return new ColorMatrix(array);
    }


    public static Bitmap changeBitmapBrightness(Bitmap bmp, float brightness) {

        ColorMatrix cm = new ColorMatrix(new float[]{
                1.0F, 0.0F, 0.0F, 0.0F, brightness,
                0.0F, 1.0F, 0.0F, 0.0F, brightness,
                0.0F, 0.0F, 1.0F, 0.0F, brightness,
                0.0F, 0.0F, 0.0F, 1.0F, 0.0F});

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
//        Bitmap ret = BitmapLoadUtils.convertToMutable(bmp);

        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint();

        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    public static Bitmap changeBitmapContrast(Bitmap src, float value) {
        if (value == -1f) {
            value = -0.9f;
        }
        float scale = value + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;

        ColorMatrix cm = new ColorMatrix(new float[]{
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0});

        if (src==null){
            return null;
        }
        Bitmap ret = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
//        Bitmap ret = BitmapLoadUtils.convertToMutable(src);

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(src, 0, 0, paint);

        return ret;
    }
}
