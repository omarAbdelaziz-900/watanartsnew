//package com.WattanArt.Utils;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//
//import com.tbruyelle.rxpermissions2.RxPermissions;
//
//import java.lang.ref.WeakReference;
//
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Consumer;
//
//
///**
// * Created by Android Team on 1/31/2018.
// */
//
//public class PermissionChecker {
//
//    public static void checkCameraPermission(Context context, final OnPermissionCheckListener listener) {
//        WeakReference<Context> weakReference = new WeakReference<>(context);
//        RxPermissions rxPermissions = new RxPermissions((Activity) weakReference.get());
//        rxPermissions
//                .request(Manifest.permission.CAMERA)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//                        if(listener != null){
//                            if(aBoolean)
//                                listener.onGranted();
//                            else
//                                listener.onDenied();
//                        }
//
//                    }
//                });
//    }
//
//
//    public static void checkReadExternalStoragePermission(Context context, final OnPermissionCheckListener listener) {
//        WeakReference<Context> weakReference = new WeakReference<>(context);
//        RxPermissions rxPermissions = new RxPermissions((Activity) weakReference.get());
//        rxPermissions
//                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//                        if(listener != null){
//                            if(aBoolean)
//                                listener.onGranted();
//                            else
//                                listener.onDenied();
//                        }
//
//                    }
//                });
//    }
//
//
//
//    public static void checkWriteExternalStoragePermission(Context context, final OnPermissionCheckListener listener) {
//        WeakReference<Context> weakReference = new WeakReference<>(context);
//        RxPermissions rxPermissions = new RxPermissions((Activity) weakReference.get());
//        rxPermissions
//                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//                        if(listener != null){
//                            if(aBoolean)
//                                listener.onGranted();
//                            else
//                                listener.onDenied();
//                        }
//
//                    }
//                });
//    }
//
//    public static void checkReadPhoneStatePermission(Context context, final OnPermissionCheckListener listener) {
//        WeakReference<Context> weakReference = new WeakReference<>(context);
//        RxPermissions rxPermissions = new RxPermissions((Activity) weakReference.get());
//        rxPermissions
//                .request(Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//                        if(listener != null){
//                            if(aBoolean)
//                                listener.onGranted();
//                            else
//                                listener.onDenied();
//                        }
//
//                    }
//                });
//    }
//
//    public interface OnPermissionCheckListener {
//        void onGranted();
//
//        void onDenied();
//    }
//}
