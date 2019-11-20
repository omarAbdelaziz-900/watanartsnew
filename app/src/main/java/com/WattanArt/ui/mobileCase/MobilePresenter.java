package com.WattanArt.ui.mobileCase;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.PictUtil;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Request.OrderDetailsItem;
import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.Response;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.ThrowableModel;
import com.WattanArt.network.ApiService;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.task.BitmapCropTask;
import com.yalantis.ucrop.util.RectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import com.WattanArt.ui.Register.RegisterActivity;
import static com.WattanArt.ui.Register.RegisterActivity.ShippingRegister;
import static com.WattanArt.ui.Shipping.ShippingActivity.OPEN_REGISTERATION_CODE;
import static com.WattanArt.ui.Shipping.ShippingActivity.REQUEST_STORAGE_READ_ACCESS_PERMISSION;

/**
 * Created by Android Team on 1/18/2018.
 */

public class
MobilePresenter<V extends MobileMvpView> extends BasePresenter<V>
        implements MobilePresenterMvp<V> {

    int uploadIndex = 0;
    int failIndex = 0;
    int cropIndex = 0;
    int segmentationIndex = 0;
    int orderDetailsItemsIndex = 0;
    int serverUploadIndex = 0;
    boolean isCouponCodeApplied = false;

    List<SelectCountryCitiyListsResponseModel.Result.PatternType> patternTypeEntityList;
    UserData userData = new UserData();
    ValidationTool validationTool = new ValidationTool(MyApplication.getAppContext());

    File filetoUpload = null;
    ArrayList<byte[]> bs = new ArrayList();
    List<OrderDetailsItem> orderDetailsItemsList = new ArrayList();

    List<ImageModel> imageModels;


    EditText address, code, phone;
    int city, country, buyType;


    int allListSize = 0;
    int sucessParts = 0;

    ArrayList<byte[]> newFailList = new ArrayList();


    @Inject
    public MobilePresenter(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void uploadImage() {
    }

    @Override
    public void segmentImage(List<String> listPaths) {

        if (listPaths.size() == segmentationIndex) {
            //TODO : start upload request
            uploadAllRequest();
            return;
        }

        Log.e("path to segment", "--->" + listPaths.get(segmentationIndex));

        final Bitmap[] bm = new Bitmap[1];
        Observable.create((ObservableEmitter<Object> emitter) -> {
            filetoUpload = new File(listPaths.get(segmentationIndex));
            bm[0] = BitmapFactory.decodeFile(listPaths.get(segmentationIndex));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("FileName", filetoUpload.getName());

                Log.e("Path", "->" + listPaths.get(segmentationIndex));
                if (bm[0] == null) {
                    Log.e("Bitmap null", "Bitmap is null so return");
                    return;
                }

                divideImages(bm[0], getOffsetNumber(filetoUpload), listPaths.get(segmentationIndex), () -> {
                    uploadFile(listPaths);
                    Log.e("Segmented", "image segmented in index " + segmentationIndex);
                });
            }
        });
    }

    interface imageDivided {
        void whenImageDivided();
    }

    @Override
    public void uploadAllRequest() {

        ShippingRequest shippingRequest = new ShippingRequest();
        ShippingRequest.Order order = new ShippingRequest.Order();
        order.setAddress1("dddd");
        order.setPhoneNumber("01099999999");
        order.setCityID(city);
        order.setCountryID(country);

        order.setCouponCode("");
        order.setValidCouponCode(false);

        try {
            order.setInEgypt(UtilitiesManager.getUserCountry(MyApplication.getAppContext()).equals("eg"));
            Log.d("isInEgypt", "" + UtilitiesManager.getUserCountry(MyApplication.getAppContext()));

        } catch (Exception e) {
            order.setInEgypt(true);
            Log.d("isInEgypt2", "" + UtilitiesManager.getUserCountry(MyApplication.getAppContext()));

        }


        order.setUserID(userData.getUserID(MyApplication.getAppContext()));
        order.setBuytype(buyType);
        shippingRequest.setOrder(order);
        shippingRequest.setOrderDetils(orderDetailsItemsList);

        getCompositeDisposable().add(getDataManager().makeOrder(shippingRequest)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Response>() {
                                       @Override
                                       public void accept(Response responseModel) throws Exception {
                                           if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult() != 0) {

//                                               getMvpView().logOrderToAnalytics(shippingRequest);
                                               getMvpView().showMessage(R.string.request_done);
//                                       getMvpView().hideLoading();
//                                       getMvpView().hideLoading();
                                               getMvpView().baackPress(responseModel.getResult());

                                           }

                                           getMvpView().hideLoading();
                                           uploadIndex = 0;
                                           failIndex = 0;
                                           cropIndex = 0;
                                           segmentationIndex = 0;
                                           serverUploadIndex = 0;
                                           orderDetailsItemsIndex = 0;
                                       }
                                   }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        if (!isViewAttached()) {
                                            return;
                                        }

                                        getMvpView().hideLoading();
                                        handleThrowableError(throwable);

                                    }
                                }
                        )

        );
    }

    @Override
    public void cropImages(Context context, List<ImageModel> imageModels, int city, int country, EditText code, int buyType) {
        this.imageModels = imageModels;
        this.city = city;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.code = code;
        this.buyType = buyType;

        getMvpView().showLoading();

        try {

            Log.e("CroppingParams", "Positions x is-> " + imageModels.get(cropIndex).getPositionX() + "     " +
                    "Positions Y is-> " + imageModels.get(cropIndex).getPositionY() + "     " +
                    "getImageWidth is-> " + imageModels.get(cropIndex).getImageWidth() + "     " +
                    "getImageHeight is-> " + imageModels.get(cropIndex).getImageHeight() + "     " +
                    "Bitmap width ->  " + imageModels.get(cropIndex).getFilteredBitmap().getWidth() + "     " +
                    "Bitmap Height -> " + imageModels.get(cropIndex).getFilteredBitmap().getHeight()
            );

            PictUtil.saveToCacheFile(imageModels.get(cropIndex).getFilteredBitmap(),
                    imageModels.get(cropIndex).getCurrentRotate(),
                    new File(imageModels.get(cropIndex).getPath()).getName(),
                    imageModels.get(cropIndex).getMainImageWidth(),
                    imageModels.get(cropIndex).getMainImageHeight(),
                    new PictUtil.saveBitmapCallback() {
                        @Override
                        public void onSuccess(String path) {

                            Log.e("PathToNewFile", path + "");


                            imageModels.get(cropIndex).setUri(Uri.fromFile(new File(path)));
                            imageModels.get(cropIndex).setOutputUri(Uri.fromFile(new File(path)));
                            imageModels.get(cropIndex).setmImageOutputPath(new File(path.replace("/.Cache", "")).getParent() + "/" + new File(imageModels.get(cropIndex).getPath())
                                    .getName());

                            cropImage(
                                    imageModels.get(cropIndex).getFilteredBitmap(),
                                    imageModels.get(cropIndex).getMatrix(),
                                    (int) (imageModels.get(cropIndex).getPositionX() *
                                            imageModels.get(cropIndex).getFactorWidth()),
                                    (int) (imageModels.get(cropIndex).getPositionY() *
                                            imageModels.get(cropIndex).getFactorHeight()),
                                    imageModels.get(cropIndex).getOutputUri(),
                                    imageModels.get(cropIndex).getmExifInfo(),
                                    imageModels.get(cropIndex).getImageWidth(),
                                    imageModels.get(cropIndex).getImageHeight(),
                                    path,
                                    new File(path.replace("/.Cache", "")).getParent() + "/" + new File(imageModels.get(cropIndex).getPath())
                                            .getName(),
                                    imageModels.get(cropIndex).getmCropRect(),
                                    imageModels.get(cropIndex).getmCurrentImageCorners(),
                                    imageModels.get(cropIndex).getCurrentScale(),
                                    imageModels.get(cropIndex).getCurrentAngle(),
                                    new BitmapCropCallback() {
                                        @Override
                                        public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {


                                            if (imageModels.size() - 1 > cropIndex) {
                                                Log.e("Cropped", "image cropped  " + cropIndex);
                                                cropIndex++;
                                                cropImages(context, imageModels, city, country, code, buyType);
                                            } else {
                                                Log.e("AllImagesCropped", "AllImagesCropped");
                                                List<String> pathList = new ArrayList<>();
                                                for (ImageModel model : imageModels) {
                                                    pathList.add(model.getmImageOutputPath());
                                                }
                                                segmentImage(pathList);
                                                return;
                                            }
                                        }

                                        @Override
                                        public void onCropFailure(@NonNull Throwable t) {
                                            getMvpView().hideLoading();
                                            t.printStackTrace();
                                        }
                                    });
                        }

                        @Override
                        public void onFail(Exception e) {
                            getMvpView().hideLoading();
                            Toast.makeText(context, context.getString(R.string.some_error), Toast.LENGTH_SHORT).show();
                            Log.e("ErrorInsaveFilterImage", "error saving filtered Bitmap ");
                            e.printStackTrace();
                        }
                    });

        } catch (Exception ex) {
            Toast.makeText(context, context.getString(R.string.some_error), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            getMvpView().hideLoading();
        }
    }

    @Override
    public void getShippingPrices() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().
                getCountries_citiesList(userData.getLocalization(MyApplication.getAppContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(selectCountryCitiyListsResponseModel -> {
                    if (selectCountryCitiyListsResponseModel != null && selectCountryCitiyListsResponseModel.getISResultHasData() == 1) {
                        this.patternTypeEntityList = selectCountryCitiyListsResponseModel.getResult().getPatternType();
                        MobilePresenter.this.getMvpView().returnPatternList(selectCountryCitiyListsResponseModel.getResult());
                    } else {
                        MobilePresenter.this.getMvpView().showMessage(R.string.some_error);
                    }
                    getMvpView().hideLoading();
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    uploadIndex = 0;
                    failIndex = 0;
                    cropIndex = 0;
                    segmentationIndex = 0;
                    serverUploadIndex = 0;
                    orderDetailsItemsIndex = 0;
                    getMvpView().hideLoading();

                    handleThrowableError(throwable);
                }));
    }

    @Override
    public void showLoginPopup(Context coontext, List<ImageModel> imageModels,
                                int city, int country, int buyType) {

        Dialog mDialog = new Dialog(coontext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.login_popup);


        EditText mEmailEditText = mDialog.findViewById(R.id.login_email);
        EditText mPasswordEditText = mDialog.findViewById(R.id.login_password);
//        CheckBox remeberme_checkbox = mDialog.findViewById(R.id.remeberme_checkbox);

        mDialog.findViewById(R.id.close_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validPassword = validationTool.validatePassword(mPasswordEditText, MyApplication.getAppContext().getString(R.string.enter_password));
                boolean validMail = validationTool.validateEmail(mEmailEditText, MyApplication.getAppContext().getString(R.string.invalid_email));
                if (validMail && validPassword) {
                    getMvpView().showLoading();


                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(((Activity) coontext),
                            new OnSuccessListener<InstanceIdResult>() {
                                @Override
                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                    String newToken = instanceIdResult.getToken();
                                    Log.e("newToken", newToken);

                                    String deviceID = UtilitiesManager.getDeviceID(coontext);
                                    getCompositeDisposable().add(
                                            getDataManager().login(
                                                    new LoginRequestModel("",
                                                            mEmailEditText.getText().toString(),
                                                            newToken, deviceID, "1", null,
                                                            mPasswordEditText.getText().toString(), 1))
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(responseModel -> {
                                                                if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult().getSuccess()) {

                                                                    getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.login_success));
                                                                    mDialog.dismiss();
                                                                    userData.saveUserID(MyApplication.getAppContext(), responseModel.getResult().getObj().getUserID());
                                                                    userData.saveUserType(MyApplication.getAppContext(), responseModel.getResult().getObj().getRegisterType());
                                                                    userData.savePhone(MyApplication.getAppContext(), responseModel.getResult().getObj().getPhone());
                                                                    userData.setRemmemberMe(MyApplication.getAppContext(), true);

                                                                        cropImages(coontext, imageModels, city, country, code, buyType);


                                                                } else if (responseModel != null && responseModel.getResult() != null) {
//
                                                                    getMvpView().hideLoading();
                                                                    getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.fail_login));

                                                                }

                                               getMvpView().hideLoading();
                                                            }
                                                            , new Consumer<Throwable>() {
                                                                @Override
                                                                public void accept(Throwable throwable) throws Exception {
                                                                    if (!isViewAttached()) {
                                                                        return;
                                                                    }

                                                                    getMvpView().hideLoading();
                                                                    handleThrowableError(throwable);

                                                                }
                                                            }
                                                    )

                                    );

                                }
                            });

                }
            }
        });

//        mDialog.findViewById(R.id.remeberme_checkbox).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (remeberme_checkbox.isChecked()) {
//                    isRemember = true;
////                }
//            }
//        });
        mDialog.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.dismiss();
                Intent intent = new Intent(coontext, RegisterActivity.class);
                intent.putExtra(ShippingRegister, true);
                ((Activity) coontext).startActivityForResult(intent, OPEN_REGISTERATION_CODE);

//                getMvpView().showMessage("This feature is under developement");
            }
        });
        mDialog.show();
    }

    @Override
    public void saveUserData() {

    }

    @Override
    public void checkImageHasLowResolution(Context context, List<ImageModel> imageModels, int city, int country, int buyType) {
        final boolean[] hasLowResolutionImages = {false};

        for (ImageModel imageModel : imageModels) {
            if (imageModel.isLowResolution()) {
                hasLowResolutionImages[0] = true;
            }
        }

        if (hasLowResolutionImages[0]) {
            Dialog mDialog = new Dialog(context);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mDialog.getWindow().setGravity(Gravity.CENTER);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.setContentView(R.layout.lowreselution_confirm);

            mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
                mDialog.dismiss();
                cropImages(context, imageModels,  city, country, code, buyType);
            });

            mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
                mDialog.dismiss();
            });

            mDialog.findViewById(R.id.close_popup).setOnClickListener(v -> {
                mDialog.dismiss();
            });
            mDialog.show();
        } else {
            uploadIndex = 0;
            failIndex = 0;
            cropIndex = 0;
            segmentationIndex = 0;
            orderDetailsItemsIndex = 0;
            serverUploadIndex = 0;
            bs.clear();

            cropImages(context, imageModels, city, country, code, buyType);
        }
    }

    @Override
    public void showDeleteAllDialog(Context context) {
        Dialog mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.delete_all_popup);

        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
            mDialog.dismiss();
            getMvpView().onDeleteAllImages();
        });

        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
            mDialog.dismiss();
        });

        mDialog.findViewById(R.id.close_popup).setOnClickListener(v -> {
            mDialog.dismiss();
        });
        mDialog.show();
    }

    @Override
    public void addNewImagesToOrder(Context context, ArrayList<String> photosList) {
        pickFromGallery((Activity) context, photosList);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void pickFromGallery(Activity context, ArrayList<String> photosList) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);

        } else {
            PhotoPicker.builder()
                    .setGridColumnCount(3)
                    .setSelected(photosList)
                    .setPreviewEnabled(false)
                    .setShowCamera(false)
                    .start(context);
        }
    }

//    private int getPatternId(float ratio, List<SelectCountryCitiyListsResponseModel.Result.PatternType> pattenList) {
//
//        int id = 0;
//
//        if (ratio == ratios[0]) {
//            id = 1;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x8")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        } else if (ratio == ratios[1]) {
//            id = 5;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x12")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        } else if (ratio == ratios[2]) {
//            id = 2;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("12x8")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        } else if (ratio == ratios[3]) {
//            id = 3;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x24")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        } else if (ratio == ratios[4]) {
//            id = 6;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("12x24")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        } else if (ratio == ratios[5]) {
//            id = 4;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
////            ssaa
//        }else if (ratio == ratios[6]) {
//            id = 7;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        }else if (ratio == ratios[7]) {
//            id = 8;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        }else if (ratio == ratios[8]) {
//            id = 9;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        }else if (ratio == ratios[9]) {
//            id = 10;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        }else if (ratio == ratios[10]) {
//            id = 11;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        }else if (ratio == ratios[11]) {
//            id = 12;
////            for (SelectCountryCitiyListsResponseModel.Result.PatternType patternTypeEntity : pattenList) {
////                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
////                    id = patternTypeEntity.getID();
////                    break;
////                }
////            }
//        }
//
//        return id;
//    }

    /**
     * Cancels all current animations and sets image to fill crop area (without animation).
     * Then creates and executes {@link BitmapCropTask} with proper parameters.
     */
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

    private int getOffsetNumber(File file) {
        int sizeInMB = (int) Math.ceil(file.length() / 512d / 1024d);
        Log.e("getOffsetNumber", "" + sizeInMB);
        return sizeInMB;
    }

    private void divideImages(Bitmap bit, int offsetNumbers, String path, imageDivided imageDivided) {

        Observable.create((ObservableEmitter<Object> emitter) -> {

            Log.e("divideImages", "Path is -> " + path);


            ByteArrayOutputStream bos = new ByteArrayOutputStream();


            bit.compress(Bitmap.CompressFormat.JPEG, 90 /*ignored for PNG*/, bos);

            byte[] allBytesArray = bos.toByteArray();

            int step = 1 * 512 * 1024; //512 bytes

            byte[] singleArray;
            for (int k = 0; k < offsetNumbers; k++) {
                singleArray = new byte[step];
                int index = 0;

                Log.e("allBytesArray", "-->" + allBytesArray.length);
                Log.e("singleArray", "-->" + singleArray.length);

                for (int i = k * step; i < (k * step) + step; i++) {// 0.5 MB
//                    Log.e("ByteIS0 " , "->"+allBytesArray[i]);
                    if (i < allBytesArray.length) {
                        singleArray[index] = allBytesArray[i];
                        index++;
                    }
                }
                Log.e("index", "-->" + index);

                bs.add(singleArray);
            }

            allListSize = bs.size();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                imageDivided.whenImageDivided();
            }
        });

    }

    private String getNameWithOutExtension(String s) {
        return s != null && s.lastIndexOf(".") > 0 ? s.substring(0, s.lastIndexOf(".")) : s;
    }

    private String getExtension(String someFilepath) {
        String ext = someFilepath.substring(someFilepath.lastIndexOf("."));
        Log.e("EXT", "" + ext);
        return ext;
    }

    // Uploading Image slices
    private void uploadFile(List<String> listPaths) {
        int index = serverUploadIndex + 1;


        Log.e("DATA", serverUploadIndex + "          " + bs.size());
        Log.e("PATH", listPaths.get(segmentationIndex));

        if (serverUploadIndex == bs.size()) {
            bs.clear();
            uploadIndex = 0;
            return;
        }


        String thisFileName;

        if (getExtension(listPaths.get(segmentationIndex)).toLowerCase().contains("png")) {
            thisFileName = getNameWithOutExtension(filetoUpload.getName()) + ".png.part_" + index + "." + bs.size();
        } else {
            thisFileName = getNameWithOutExtension(filetoUpload.getName()) + ".jpg.part_" + index + "." + bs.size();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), bs.get(serverUploadIndex));
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);
//
//
        ApiService getResponse = AppDataManager.getApi();
        Call<ImageUploadResponseModel> call = getResponse.uploadFileNew(fileToUpload, filename);


        call.enqueue(new Callback<ImageUploadResponseModel>() {
            @Override
            public void onResponse(Call<ImageUploadResponseModel> call, retrofit2.Response<ImageUploadResponseModel> response) {
                sucessParts++;
                serverUploadIndex++;

                Log.e("Stateee",response.body().getState()+"");
                if (response.code() == 200) {
                    if (response.body().getState() == 1) {
                        //could not find file
                        Toast.makeText(MyApplication.getAppContext(), "Error 1", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 2) {
                        //part file catch from service
                        Toast.makeText(MyApplication.getAppContext(), "Error 2", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 3) {
                        //success in part and complete upload other parts , file name is uploaded parts concatenated with ,
                        uploadFile(listPaths);
                    } else if (response.body().getState() == 4) {
                        //all parts uploaded but error to join them by service
                        Toast.makeText(MyApplication.getAppContext(), "Error 4", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 5) {
                        //image completed successfully
                        try {
                            OrderDetailsItem orderDetailsItems = new OrderDetailsItem();
                            orderDetailsItems.setMainImage(response.body().getFileName());
                            orderDetailsItems.setPatternID(2);
                            orderDetailsItems.setQuantiy(imageModels.get(orderDetailsItemsIndex).getQuantity());
                            orderDetailsItemsList.add(orderDetailsItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        bs.clear();

                        segmentationIndex++;
                        orderDetailsItemsIndex++;
                        serverUploadIndex = 0;
                        segmentImage(listPaths);

                    }
                    Log.e("TEST on success", "-->" + new Gson().toJson(call.request()));

                } else {
                    onFailure(call, new Throwable());
                }

                if (sucessParts + newFailList.size() == allListSize) {
                    // TODO  : image upload finished , start the next image
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponseModel> call, Throwable t) {
                //TODO  : image upload failed , add to faillistNew list to upload later
                Log.e("TEST", "-->" + new Gson().toJson(call.request().body()));
                Log.e("TEST", "-->" + new Gson().toJson(call.request()));

                ThrowableModel model = new Gson().fromJson(new Gson().toJson(call.request().body()), ThrowableModel.class);
                t.printStackTrace();
                Toast.makeText(MyApplication.getAppContext(),
                        MyApplication.getAppContext().getString(R.string.slow_connection)
                        , Toast.LENGTH_SHORT).show();

                serverUploadIndex++;
                if (bs.size() > serverUploadIndex) {
                    getMvpView().hideLoading();
                    uploadFile(listPaths);
                } else if (!orderDetailsItemsList.isEmpty()) {
                    //some images failed to upload , just make order with the completed parts
                    bs.clear();
                    segmentationIndex++;
                    orderDetailsItemsIndex++;
                    serverUploadIndex = 0;
                    segmentImage(listPaths);
                } else {
                    getMvpView().hideLoading();
                }
            }
        });
    }

}
