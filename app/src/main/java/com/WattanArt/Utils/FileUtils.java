package com.WattanArt.Utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;


import com.WattanArt.logging.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Android Team on 1/31/2018.
 */

public class FileUtils {
    //    TODO remove TEMP URL
    public static final String TEMP_URL = "http://i.imgur.com/QkGY8Pt.jpg";
    private static final String APP_NAME = "/EventsApp";
    private static final String MAIN_DIRECTORY = Environment.getExternalStorageDirectory() + APP_NAME;
    //Copied From Common class from Version 1
    public static final String EVENT_PHOTOS = MAIN_DIRECTORY + "/.event_photos";
    public static final String EVENT_SPONSORS = MAIN_DIRECTORY + "/.event_photos";
    public static final String ROOM_PHOTOS = MAIN_DIRECTORY + "/.room_photos";
    public static final String SPEAKER_PHOTOS = MAIN_DIRECTORY + "/.speaker_photos";
    public static final String PLACE_PHOTOS = MAIN_DIRECTORY + "/.place_photos";
    public static final String AVATAR = MAIN_DIRECTORY + "/avatar";
    public static final String DOWNLOADS = MAIN_DIRECTORY + "/Downloads";
    public static final String TEMP_FILES = MAIN_DIRECTORY + "/temp_files";

    public void readFromFile() {
    }

    //Copied From Common class from Version 1

    /**
     * @param directory          Where to save your file
     * @param fileName           Image Name to be saved
     * @param bitmap             Image to be saved with
     * @param compressionQuality Compression Ratio to be used when compressing bitmap
     * @return Absolute File Path if the Bitmap was compressed and written
     * Or Empty String in case of failure
     * @throws IOException
     */
    private String writeBitmapToFile(File directory, String fileName, Bitmap bitmap, int compressionQuality) throws IOException {
        File file = new File(directory, fileName);
        if (!UtilitiesManager.isSdCardMounted()) return "";

//        if (!file.isDirectory()) file.mkdirs();
        if (file.exists())
            file.delete();
        if (directory.mkdirs() || directory.isDirectory()) {
            FileOutputStream outputStream = new FileOutputStream(file);
            boolean b = bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream);
            Logger.info(getClass().getSimpleName(), file.getAbsolutePath() + " was compressed: " + b);

            outputStream.flush();
            outputStream.close();
            return file.getAbsolutePath();
        }
        return "";
    }

    public String writeImageToFile(String directory, String fileName, Bitmap bitmap) throws IOException {
        File directoryFolder = new File(directory);
        String filePath = writeBitmapToFile(directoryFolder, fileName, bitmap, 90);
        Logger.info(getClass().getSimpleName(), "File Path: " + filePath);
        return filePath;
    }

    //Copied From Common class from Version 1
    public void addSavedImageToGallery(String fileName, Context context) {
        File file = new File("file:" + fileName);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);

        }
    }

    //SRC: http://developer.android.com/training/camera/photobasics.html
    public void setBitmapToImage(String mCurrentPhotoPath, ImageView mImageView) {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 0;
        // Determine how much to scale down the image
        if (targetW != 0 && targetH != 0)
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        else scaleFactor = 0;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        mImageView.setImageBitmap(bitmap);
    }

    public String getPathFromUri(Uri uri, Context context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    public static void deleteFilesInDirectory(File file) {
        if (file.isDirectory())
            for (File child : file.listFiles())
                deleteFilesInDirectory(child);

        file.delete();
    }

    public static void deleteFilesWithDirectory(File file) {
        if (file.isDirectory())
            for (File child : file.listFiles())
                deleteFilesInDirectory(child);

        if(!file.isDirectory())
            file.delete();
    }


}
