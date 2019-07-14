//package com.WattanArt.Utils;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Point;
//import android.graphics.drawable.Drawable;
//import android.media.ExifInterface;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
//import android.util.Base64;
//import android.view.View;
//import android.widget.ImageView;
//
//
//import com.WattanArt.R;
//import com.WattanArt.Utils.config.Constants;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.SimpleTarget;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.lang.ref.WeakReference;
//
///**
// * Created by Android Team on 1/31/2018.
// */
//public class ImageUtils {
//    private static final float CORNER_RADIUS = 2.0f;
//    private WeakReference<ImageView> mImageViewWeakReference;
//    private String mImgPath;
//    public String imgBase64, imgName;
//
//    public ImageUtils() {
//    }
//
//    public ImageUtils(String imgPath) {
//        mImgPath = imgPath;
//    }
//
//    public ImageUtils(ImageView imageView) {
//        mImageViewWeakReference = new WeakReference<>(imageView);
//    }
//
//    public void setRoundedImageView(Resources resources, Bitmap src) {
//        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, src);
//        roundedBitmapDrawable.setCornerRadius(Math.min(src.getWidth(), src.getHeight()) / CORNER_RADIUS);
//        mImageViewWeakReference.get().setImageDrawable(roundedBitmapDrawable);
//    }
//
//    // http://developer.android.com/training/camera/photobasics.html
//    public void setBitmapToImage(String mCurrentPhotoPath, Point screenSize) {
//        if (mImageViewWeakReference == null) return;
//        // Get the dimensions of the View
//        int targetW;
//        targetW = mImageViewWeakReference.get().getWidth();
//        int targetH;
//        targetH = mImageViewWeakReference.get().getHeight();
//
//        if (targetW <= 0) targetW = screenSize.x;
//        if (targetH <= 0) targetH = screenSize.y / 2;
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
////        Logger.info("File Utils", "Image " + photoH + " : " + photoW);
////        Logger.info("File Utils", "Screen " + targetW + " : " + targetH);
//
//        int scaleFactor = getImageScaleFactor(photoW, photoH, targetW, targetH);
//
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
////        Logger.info("FileUtils", "Scale Factor " + scaleFactor);
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        mImageViewWeakReference.get().setImageBitmap(bitmap);
//    }
//
//    public void setBitmapToImageAsIs(String mCurrentPhotoPath) {
//        if (mImageViewWeakReference == null) return;
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
//        mImageViewWeakReference.get().setImageBitmap(bitmap);
//    }
//
//    private int getImageScaleFactor(int photoW, int photoH, int targetW, int targetH) {
//        if (targetW != 0 && targetH != 0)
//            return Math.min(photoW / targetW, photoH / targetH);
//        return 0;
//    }
//
//
//    public void convertImgToBase64(final Context context, final Handler handler, final int width, final int height) {
//
//        //Glide can't resize for width or height equal to 700 (Hamdy)
//        Glide.with(context)
//                .load(mImgPath)
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        getScaledBitmap(resource, width, height)
//                                .compress(Bitmap.CompressFormat.JPEG, Constants.COMPRESSION_RATIO, byteArrayOutputStream);
//                        byte[] byteArray = byteArrayOutputStream.toByteArray();
//
//                        Message msg = handler.obtainMessage();
//                        ImageUtils imageUtils = new ImageUtils(mImgPath);
//                        imageUtils.imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                        imageUtils.imgName = getImgName(mImgPath);
//                        msg.obj = imageUtils;
//                        handler.sendMessage(msg);
//
//                    }
//                });
//
//    }
//
//    public Bitmap getScaledBitmap(Bitmap btimap, int maxWidth, int maxHeight) {
//        int width = btimap.getWidth(), height = btimap.getHeight();
//
//        if (width >= height && width > maxWidth) {
//            height = Math.round(height * maxWidth / width);
//            width = maxWidth;
//        } else if (height > maxHeight) {
//            width = Math.round(width * maxHeight / height);
//            height = maxHeight;
//        }
//        return Bitmap.createScaledBitmap(btimap, width, height, true);
//    }
//
//    public static String getImgName(String imgPath) {
//        return imgPath.substring(imgPath.lastIndexOf("/") + 1);
//    }
//
//
//    public void convertImgToBase64WithoutGlide(final int width, final int height) {
//        Bitmap bitmap = rotateImage(mImgPath, BitmapFactory.decodeFile(mImgPath));
//        //Bitmap bitmap = BitmapFactory.decodeFile(mImgPath);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        getScaledBitmap(bitmap, width, height)
//                .compress(Bitmap.CompressFormat.JPEG, Constants.COMPRESSION_RATIO, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        this.imgBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        this.imgName = getImgName(mImgPath);
//
//    }
//
//    public Bitmap rotateImage(String filePath, Bitmap bitmap) {
//        Bitmap resultBitmap = bitmap;
//
//        try {
//            ExifInterface exifInterface = new ExifInterface(filePath);
//            int orientation = exifInterface.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION, 1);
//
//            Matrix matrix = new Matrix();
//
//            if (orientation == 6) {
//                matrix.postRotate(90);
//            } else if (orientation == 3) {
//                matrix.postRotate(180);
//            } else if (orientation == 8) {
//                matrix.postRotate(270);
//            }
//            // Rotate the bitmap
//            resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                    bitmap.getHeight(), matrix, true);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return resultBitmap;
//    }
//
//    public static boolean isImageExist(String imagePath) {
//        File file = new File(imagePath);
//        if (file.exists())
//            return true;
//
//        return false;
//    }
//
//    public static Bitmap getBitmapFromView(View view) {
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view
//                .getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(returnedBitmap);
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null)
//            bgDrawable.draw(canvas);
//        else
//            // if we unable to get background drawable then we will set white color as wallpaper
//            canvas.drawColor(Color.WHITE);
//        view.draw(canvas);
//        return returnedBitmap;
//    }
//
//    public static String getSelectedImagePath(Activity activity, int requestCode, int resultCode, Intent data, File cameraImage, int randomNumber) {
//
//        if (resultCode == activity.RESULT_OK) {
//            if (requestCode == Constants.FILE_CODE) {
//                Uri selectedImageUri = data.getData();
//                //mImgPath = new FileUtils().getPathFromUri(selectedImageUri, context);
//                return new FileUtils().getPathFromUri(selectedImageUri, activity);
//            } else if (requestCode == Constants.CAMERA_CODE) {
//                //mImgPath = cameraImage.getPath();
//                cameraImage = new File(FileUtils.TEMP_FILES, randomNumber + ".jpg");
//                return cameraImage.getPath();
//
//            }
//        }
//
//        return "";
//    }
//
//    public static AlertDialog.Builder getPhotoChooserDialog(final Activity activity, final int randomNumber) {
//
//        CharSequence photoChooserOptions[] = new CharSequence[]{activity.getResources().getString(R.string.photo_chooser_camera), activity.getResources().getString(R.string.photo_chooser_gallery)};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setTitle(activity.getResources().getString(R.string.photo_chooser_title));
//        builder.setItems(photoChooserOptions, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int option) {
//                if (option == 0) {
//                    if (!new File(FileUtils.TEMP_FILES).isDirectory())
//                        new File(FileUtils.TEMP_FILES).mkdir();
//                    //mCcameraImage = new File(FileUtils.TEMP_FILES, randomNumber + ".jpg");
//                    File cameraImage = new File(FileUtils.TEMP_FILES, randomNumber + ".jpg");
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraImage));
//                    activity.startActivityForResult(intent, Constants.CAMERA_CODE);
//                } else if (option == 1) {
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_PICK);
//                    activity.startActivityForResult(Intent.createChooser(intent, activity.getResources().getString(R.string.gallery_chooser_title)), Constants.FILE_CODE);
//                }
//            }
//        });
//        return builder;
//    }
//
//
//}
