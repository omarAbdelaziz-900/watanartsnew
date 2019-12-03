//package com.WattanArt.ui.Shipping;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.RectF;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.CheckBox;
//import android.widget.EditText;
//
//import com.WattanArt.MyApplication;
//import com.WattanArt.R;
//import com.WattanArt.Utils.SharedPrefTool.UserData;
//import com.WattanArt.Utils.config.ValidationTool;
//import com.WattanArt.model.AppModels.ImageModel;
//import com.WattanArt.model.Request.LoginRequestModel;
//import com.WattanArt.model.Request.OrderDetailsItem;
//import com.WattanArt.model.Request.ShippingRequest;
//import com.WattanArt.model.Response.LoginResponseModel;
//import com.WattanArt.model.Response.Response;
//import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
//import com.WattanArt.network.AppDataManager;
//import com.WattanArt.ui.Register.RegisterActivity;
//import com.WattanArt.ui.base.BasePresenter;
//import com.yalantis.ucrop.callback.BitmapCropCallback;
//import com.yalantis.ucrop.model.CropParameters;
//import com.yalantis.ucrop.model.ExifInfo;
//import com.yalantis.ucrop.model.ImageState;
//import com.yalantis.ucrop.task.BitmapCropTask;
//import com.yalantis.ucrop.util.RectUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//import me.iwf.photopicker.PhotoPicker;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//
//import static com.WattanArt.ui.Register.RegisterActivity.ShippingRegister;
//import static com.WattanArt.ui.Shipping.ShippingActivity.OPEN_REGISTERATION_CODE;
//import static com.WattanArt.ui.Shipping.ShippingActivity.REQUEST_STORAGE_READ_ACCESS_PERMISSION;
//
///**
// * Created by Android Team on 1/18/2018.
// */
//
//public class ShippingPresenterBeforeUploadEdit<V extends ShippingMvpView> extends BasePresenter<V>
//        implements ShippingPresenterMvp<V> {
//
//    int uploadIndex = 0;
//    int failIndex = 0;
//    int cropIndex = 0;
//    int segmentationIndex = 0;
//    int serverUploadIndex = 0;
//    boolean isRemember = false;
//    List<SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity> patternTypeEntityList;
//    UserData userData = new UserData();
//    ValidationTool validationTool = new ValidationTool(MyApplication.getAppContext());
//
//    File filetoUpload = null;
//    ArrayList<byte[]> bs = new ArrayList();
//    ArrayList<byte[]> failList = new ArrayList();
//    List<OrderDetailsItem> orderDetailsItemsList = new ArrayList();
//    int orderDetailsItemsIndex = 0;
//    List<ImageModel> imageModels;
//    float[] ratios = new float[]{1f, 8f / 12f, 12f / 8f, 24f / 8f, 24f / 12f, 36f / 8f};
//
//
//    EditText address, code;
//    int city, country;
//
//
//    int allListSize = 0;
//    int sucessParts = 0;
//
//    ArrayList<byte[]> newFailList = new ArrayList();
//
//
//    @Inject
//    public ShippingPresenterBeforeUploadEdit(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
//        super(dataManager, compositeDisposable);
//    }
//
//    @Override
//    public void uploadImage() {
//    }
//
//    @Override
//    public void segmentImage(List<String> listPaths) {
//
//        if (listPaths.size() == segmentationIndex) {
//            //TODO : start upload request
//            uploadAllRequest();
//            return;
//        }
//
//        filetoUpload = new File(listPaths.get(segmentationIndex));
//
////        BitmapFactory.Options options = new BitmapFactory.Options();
////        options.inJustDecodeBounds = false;
////        options.inPreferredConfig = Bitmap.Config.RGB_565;
////        options.inDither = true;
//
//        Bitmap bm = BitmapFactory.decodeFile(listPaths.get(segmentationIndex));
//
//        Log.e("FileName", filetoUpload.getName());
//
//        Log.e("Path", "->" + listPaths.get(segmentationIndex));
//        if (bm == null) {
//            Log.e("Bitmap null", "Bitmap is null so return");
//            return;
//        }
//        divideImages(bm, getOffsetNumber(filetoUpload), listPaths.get(segmentationIndex), () -> {
//            uploadFile(listPaths);
//            Log.e("Segmented", "image segmented in index " + segmentationIndex);
//        });
//    }
//
//    interface imageDivided {
//        void whenImageDivided();
//    }
//
//    @Override
//    public void uploadAllRequest() {
//
//        ShippingRequest shippingRequest = new ShippingRequest();
//        ShippingRequest.Order order = new ShippingRequest.Order();
//        order.setAddress1(address.getText().toString());
//        order.setCityID(city);
//        order.setCountryID(country);
//        order.setCouponCode(code.getText().toString());
//
//
//        order.setUserID(userData.getUserID(MyApplication.getAppContext()));
//        order.setBuytype(1);
//        shippingRequest.setOrder(order);
//        shippingRequest.setOrderDetils(orderDetailsItemsList);
//
//        getCompositeDisposable().add(getDataManager().makeOrder(shippingRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Response>() {
//                               @Override
//                               public void accept(Response responseModel) throws Exception {
//                                   if (responseModel != null && responseModel.getISResultHasData() == 1) {
//
//
//                                       getMvpView().showMessage("Your request is done");
//                                       getMvpView().baackPress();
//
//                                   }
//
//                                   getMvpView().hideLoading();
//                                   uploadIndex = 0;
//                                   failIndex = 0;
//                                   cropIndex = 0;
//                                   segmentationIndex = 0;
//                                   serverUploadIndex = 0;
//                               }
//                           }
//                        , new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                if (!isViewAttached()) {
//                                    return;
//                                }
//
//                                getMvpView().hideLoading();
//                                handleThrowableError(throwable);
//
//                            }
//                        }
//                )
//
//        );
//    }
//
//    private void printFloatArray(float[] array) {
//        if (array == null) {
//            Log.e("NullableArray", "Array is null");
//        } else {
//            String outp = "";
//            for (int i = 0; i < array.length; i++) {
//                outp += "    " + array[i];
//            }
//
//            Log.e("getmCurrentImageCorners", outp);
//        }
//    }
//
//
//    @Override
//    public void cropImages(Context cotne , List<ImageModel> imageModels, EditText address, int city, int country, EditText code, int buyType) {
//        this.imageModels = imageModels;
//        this.city = city;
//        this.country = country;
//        this.address = address;
//        this.code = code;
//
////        if (checkImageHasLowResolution(imageModels)){
////
////        }
//        getMvpView().showLoading();
//
//        try {
//            Log.e("SSize", "imageModels.size() = " + imageModels.size());
//            Log.e("TestCropInPres", "Image " + cropIndex + " cropped");
//            Log.e("getmImageOutputPath", "->" + imageModels.get(cropIndex).getmImageOutputPath());
//            Log.e("getmImageInputPath", "->" + imageModels.get(cropIndex).getmImageInputPath());
//            Log.e("getmMaxResultImageSizeX", "->" + imageModels.get(cropIndex).getmMaxResultImageSizeX());
//            Log.e("getmMaxResultImageSizeY", "->" + imageModels.get(cropIndex).getmMaxResultImageSizeY());
//            Log.e("getCurrentAngle", "->" + imageModels.get(cropIndex).getCurrentAngle());
//            Log.e("getCurrentScale", "->" + imageModels.get(cropIndex).getCurrentScale());
//            printFloatArray(imageModels.get(cropIndex).getmCurrentImageCorners());
//            Log.e("getmCropRect", "->" + imageModels.get(cropIndex).getmCropRect());
//
//
//            cropImage(imageModels.get(cropIndex).getFilteredBitmap(), imageModels.get(cropIndex).getUri(), imageModels.get(cropIndex).getmExifInfo(),
//                    imageModels.get(cropIndex).getmMaxResultImageSizeX(),
//                    imageModels.get(cropIndex).getmMaxResultImageSizeY(), imageModels.get(cropIndex).getmImageInputPath(),
//                    imageModels.get(cropIndex).getmImageOutputPath(), imageModels.get(cropIndex).getmCropRect(),
//                    imageModels.get(cropIndex).getmCurrentImageCorners(),
//                    imageModels.get(cropIndex).getCurrentScale(), imageModels.get(cropIndex).getCurrentAngle(), new BitmapCropCallback() {
//                        @Override
//                        public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
//
//                            if (imageModels.size() - 1 > cropIndex) {
//                                Log.e("Cropped", "image cropped  " + cropIndex);
//                                cropIndex++;
//                                cropImages(cotne , imageModels, address, city, country, code, buyType);
//                            } else {
//                                Log.e("AllImagesCropped", "AllImagesCropped");
//                                List<String> pathList = new ArrayList<>();
//                                for (ImageModel model : imageModels) {
//                                    pathList.add(model.getmImageOutputPath());
//                                }
//                                segmentImage(pathList);
//                                return;
//                            }
//
//                        }
//
//                        @Override
//                        public void onCropFailure(@NonNull Throwable t) {
//                            t.printStackTrace();
//                        }
//                    });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Override
//    public void getShippingPrices() {
//        getMvpView().showLoading();
//        getCompositeDisposable().add(getDataManager().
//                getCountries_citiesList(userData.getLocalization(MyApplication.getAppContext()))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(selectCountryCitiyListsResponseModel -> {
//                    if (selectCountryCitiyListsResponseModel != null && selectCountryCitiyListsResponseModel.getISResultHasData() == 1) {
//                        this.patternTypeEntityList = selectCountryCitiyListsResponseModel.getResult().getPatternType();
//                        ShippingPresenterBeforeUploadEdit.this.getMvpView().returnPatternList(selectCountryCitiyListsResponseModel.getResult());
//                    } else {
//                        ShippingPresenterBeforeUploadEdit.this.getMvpView().showMessage(R.string.some_error);
//                    }
//                    ShippingPresenterBeforeUploadEdit.this.getMvpView().hideLoading();
//                }, throwable -> {
//                    if (!isViewAttached()) {
//                        return;
//                    }
//                    uploadIndex = 0;
//                    failIndex = 0;
//                    cropIndex = 0;
//                    segmentationIndex = 0;
//                    serverUploadIndex = 0;
//                    getMvpView().hideLoading();
//
//                    handleThrowableError(throwable);
//                }));
//    }
//
//    @Override
//    public void showLoginPopup(Context coontext) {
//
//        Dialog mDialog = new Dialog(coontext);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mDialog.getWindow().setGravity(Gravity.CENTER);
//        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        mDialog.setContentView(R.layout.login_popup);
//
//
//        EditText mEmailEditText = mDialog.findViewById(R.id.login_email);
//        EditText mPasswordEditText = mDialog.findViewById(R.id.login_password);
//        CheckBox remeberme_checkbox = mDialog.findViewById(R.id.remeberme_checkbox);
//
//        mDialog.findViewById(R.id.close_popup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//        mDialog.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean validPassword = validationTool.validatePassword(mPasswordEditText, MyApplication.getAppContext().getString(R.string.enter_password));
//                boolean validMail = validationTool.validateEmail(mEmailEditText, MyApplication.getAppContext().getString(R.string.invalid_email));
//                if (validMail && validPassword) {
//                    getMvpView().showLoading();
//
//                    getCompositeDisposable().add(getDataManager().login(new LoginRequestModel(mEmailEditText.getText().toString(),
//                            mPasswordEditText.getText().toString(), 1))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Consumer<LoginResponseModel>() {
//                                           @Override
//                                           public void accept(LoginResponseModel responseModel) throws Exception {
//                                               if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult().getSuccess()) {
//
//
//                                                   userData.saveUserID(MyApplication.getAppContext(), responseModel.getResult().getObj().getUserID());
//                                                   userData.saveUserType(MyApplication.getAppContext(), responseModel.getResult().getObj().getRegisterType());
//                                                   userData.setRemmemberMe(MyApplication.getAppContext(), true);
//                                                   getMvpView().hideLoading();
//
//                                                   getMvpView().afterLogin();
//
//                                               } else if (responseModel != null && responseModel.getResult() != null) {
//
//                                                   getMvpView().showMessage(responseModel.getResult().getMessage());
//                                               }
//                                               mDialog.dismiss();
//                                               getMvpView().hideLoading();
//                                           }
//                                       }
//                                    , new Consumer<Throwable>() {
//                                        @Override
//                                        public void accept(Throwable throwable) throws Exception {
//                                            if (!isViewAttached()) {
//                                                return;
//                                            }
//
//                                            getMvpView().hideLoading();
//                                            handleThrowableError(throwable);
//
//                                        }
//                                    }
//                            )
//
//                    );
//
//                }
//            }
//        });
//
//        mDialog.findViewById(R.id.remeberme_checkbox).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (remeberme_checkbox.isChecked()) {
//                    isRemember = true;
//                }
//            }
//        });
//        mDialog.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mDialog.dismiss();
//                Intent intent = new Intent(coontext, RegisterActivity.class);
//                intent.putExtra(ShippingRegister, true);
//                ((Activity) coontext).startActivityForResult(intent, OPEN_REGISTERATION_CODE);
//
////                getMvpView().showMessage("This feature is under developement");
//            }
//        });
//        mDialog.show();
//    }
//
//    @Override
//    public void saveUserData() {
//
//    }
//
//    @Override
//    public void checkImageHasLowResolution(Context context, List<ImageModel> imageModels, EditText address, int city, int country, EditText code, int buyType) {
//        final boolean[] hasLowResolutionImages = {false};
//
//        for (ImageModel imageModel : imageModels) {
//            if (imageModel.isLowResolution()) {
//                hasLowResolutionImages[0] = true;
//            }
//        }
//
//        if (hasLowResolutionImages[0]) {
//            Dialog mDialog = new Dialog(context);
//            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            mDialog.getWindow().setGravity(Gravity.CENTER);
//            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            mDialog.setContentView(R.layout.lowreselution_confirm);
//
//            mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
//                mDialog.dismiss();
//                cropImages(context,imageModels, address, city, country, code, buyType);
//            });
//
//            mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
//                mDialog.dismiss();
//            });
//
//            mDialog.findViewById(R.id.close_popup).setOnClickListener(v -> {
//                mDialog.dismiss();
//            });
//            mDialog.show();
//        } else {
//            cropImages(context ,imageModels, address, city, country, code, buyType);
//        }
//    }
//
//    @Override
//    public void showDeleteAllDialog(Context context) {
//        Dialog mDialog = new Dialog(context);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mDialog.getWindow().setGravity(Gravity.CENTER);
//        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        mDialog.setContentView(R.layout.delete_all_popup);
//
//        mDialog.findViewById(R.id.ok_btn).setOnClickListener(v -> {
//            mDialog.dismiss();
//            getMvpView().onDeleteAllImages();
//        });
//
//        mDialog.findViewById(R.id.cancel_btn).setOnClickListener(v -> {
//            mDialog.dismiss();
//        });
//
//        mDialog.findViewById(R.id.close_popup).setOnClickListener(v -> {
//            mDialog.dismiss();
//        });
//        mDialog.show();
//    }
//
//    @Override
//    public void addNewImagesToOrder(Context context, ArrayList<String> photosList) {
//        pickFromGallery((Activity) context, photosList);
//    }
//
//    @Override
//    public boolean checkOrderQuantity(List<ImageModel> imageModels) {
//        return false;
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    private void pickFromGallery(Activity context, ArrayList<String> photosList) {
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
//
//        } else {
//            PhotoPicker.builder()
//                    .setGridColumnCount(3)
//                    .setSelected(photosList)
//                    .setPreviewEnabled(false).setShowCamera(false)
//                    .start(context);
//        }
//    }
//
//    private int getPatternId(float ratio, List<SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity> pattenList) {
//
//        int id = 0;
//
//        if (ratio == ratios[0]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x8")) {
//                    id = patternTypeEntity.getID();
//                    break;
//                }
//            }
//        } else if (ratio == ratios[1]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x12")) {
//                    id = patternTypeEntity.getID();
//                    break;
//                }
//            }
//        } else if (ratio == ratios[2]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("12x8")) {
//                    id = patternTypeEntity.getID();
//                    break;
//                }
//            }
//        } else if (ratio == ratios[3]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x24")) {
//                    id = patternTypeEntity.getID();
//                    break;
//                }
//            }
//        } else if (ratio == ratios[4]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("12x24")) {
//                    id = patternTypeEntity.getID();
//                    break;
//                }
//            }
//        } else if (ratio == ratios[5]) {
//            for (SelectCountryCitiyListsResponseModel.ResultEntity.PatternTypeEntity patternTypeEntity : pattenList) {
//                if (patternTypeEntity.getPatternText().toLowerCase().equals("8x36")) {
//                    id = patternTypeEntity.getID();
//                    break;
//                }
//            }
//        }
//
//        return id;
//    }
//
//
//    /**
//     * Cancels all current animations and sets image to fill crop area (without animation).
//     * Then creates and executes {@link BitmapCropTask} with proper parameters.
//     */
//    private void cropImage(Bitmap bitmap, Uri uri, ExifInfo mExifInfo, int mMaxResultImageSizeX,
//                           int mMaxResultImageSizeY, String mImageInputPath, String mImageOutputPath
//            , RectF mCropRect, float[] mCurrentImageCorners, float currentScale,
//                           float currentAngle, BitmapCropCallback cropCallback) {
//
//        Log.e("currentScale", currentScale + " - ");
//
//
//        //TODO: check this code later
//        //check this code later
//        if (mCurrentImageCorners == null) {
//            //check tranformImageView
//            // private static final int RECT_CORNER_POINTS_COORDS = 8;
//            //protected final float[] mCurrentImageCorners = new float[RECT_CORNER_POINTS_COORDS];
//            //which has this code
//            mCurrentImageCorners = new float[8];
//        }
//
//        final ImageState imageState = new ImageState(
//                mCropRect, RectUtils.trapToRect(mCurrentImageCorners),
//                currentScale, currentAngle);
//
//
//        final CropParameters cropParameters = new CropParameters(
//                mMaxResultImageSizeX, mMaxResultImageSizeY,0,0,
//                Bitmap.CompressFormat.JPEG, 100,
//                mImageInputPath, mImageOutputPath, mExifInfo);
//
//        new BitmapCropTask(bitmap, uri, imageState, cropParameters, cropCallback).execute();
//    }
//
//
//    private int getOffsetNumber(File file) {
//        int sizeInMB = (int) Math.ceil(file.length() / 512d / 1024d);
//        Log.e("sizeInMB", "" + sizeInMB);
//        return sizeInMB;
//    }
//
//    private void divideImages(Bitmap bit, int offsetNumbers, String path, imageDivided imageDivided) {
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        if (getExtension(path).toLowerCase().contains("png")) {
//            bit.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//        } else {
//            bit.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
//        }
//        byte[] allBytesArray = bos.toByteArray();
//
//        int step = 1 * 512 * 1024; //512 bytes
//        byte[] singleArray;
//        for (int k = 0; k < offsetNumbers; k++) {
//            singleArray = new byte[step];
//            int index = 0;
//
//            for (int i = k * step; i < (k * step) + step; i++) {// 0.5 MB
//                if (i < allBytesArray.length) {
//                    singleArray[index] = allBytesArray[i];
//                    index++;
//                }
//            }
//            bs.add(singleArray);
//        }
//
//        allListSize = bs.size();
//
//        imageDivided.whenImageDivided();
//        Log.e("SIZE", bs.size() + " for index " + segmentationIndex);
//        Log.e("offsetNumbers", offsetNumbers + " for index " + segmentationIndex);
//    }
//
//    private String getNameWithOutExtension(String s) {
//        return s != null && s.lastIndexOf(".") > 0 ? s.substring(0, s.lastIndexOf(".")) : s;
//    }
//
//    private String getExtension(String someFilepath) {
//        String ext = someFilepath.substring(someFilepath.lastIndexOf("."));
//        Log.e("EXT", "" + ext);
//        return ext;
//    }
//
//    private void uploadFileFailed(String path) {
//
//        Log.e("FailMethod", "Entered Fail Method");
//
//        Calendar calendar = Calendar.getInstance();
//        Log.e("TimeIs", "->" + calendar.get(Calendar.HOUR) + " " + calendar.get(Calendar.MINUTE) + " " +
//                calendar.get(Calendar.SECOND) + " " + calendar.get(Calendar.HOUR) + " ");
//
//        int index = failIndex + 1;
//        String thisFileName;
//        if (getExtension(path).toLowerCase().contains("png")) {
//            thisFileName = getNameWithOutExtension(filetoUpload.getName()) + ".png.part_" + index + "." + bs.size();
//        } else {
//            thisFileName = getNameWithOutExtension(filetoUpload.getName()) + ".jpg.part_" + index + "." + bs.size();
//        }
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), failList.get(failIndex));
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);
//
//        getCompositeDisposable().add(getDataManager().uploadFile(fileToUpload, filename)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(settingResponseModel -> {
//                    if (settingResponseModel != null && settingResponseModel.getResult() != null
//                            && settingResponseModel.getISResultHasData() == 1) {
//                        failIndex++;
//                        uploadFileFailed(path);
//                        failList.remove(failIndex);
//                    }
//                    getMvpView().hideLoading();
//
//                }, throwable -> {
//                    if (!isViewAttached()) {
//                        return;
//                    }
//
//                    failList.add(bs.get(uploadIndex));
//
////                    handleThrowableError(throwable);
//                }));
//    }
//
//
//    // Uploading Image slices
//    private void uploadFile(List<String> listPaths) {
//        int index = serverUploadIndex + 1;
//
//
//        Log.e("DATA", serverUploadIndex + "          " + bs.size());
//        Log.e("PATH", listPaths.get(segmentationIndex));
//
//        if (serverUploadIndex == bs.size()) {
//
//
//            bs.clear();
//            uploadIndex = 0;
//            return;
//        }
//
//
//        String thisFileName;
//
//        if (getExtension(listPaths.get(segmentationIndex)).toLowerCase().contains("png")) {
//            thisFileName = getNameWithOutExtension(filetoUpload.getName()) + ".png.part_" + index + "." + bs.size();
//        } else {
//            thisFileName = getNameWithOutExtension(filetoUpload.getName()) + ".jpg.part_" + index + "." + bs.size();
//        }
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), bs.get(serverUploadIndex));
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);
////
////
////        ApiService getResponse = AppDataManager.getApiTest();
////        Call<ImageUploadResponseModel> call = getResponse.uploadFileNew(fileToUpload, filename);
////
////        call.enqueue(new Callback<ImageUploadResponseModel>() {
////            @Override
////            public void onResponse(Call<ImageUploadResponseModel> call, retrofit2.Response<ImageUploadResponseModel> response) {
////                sucessParts++;
////
////                Log.e("TEST", "-->" + new Gson().toJson(call.request()));
////
////                if (sucessParts + newFailList.size() == allListSize) {
////                    //TODO  : image upload finished , start the next image
////                }
////            }
////
////            @Override
////            public void onFailure(Call<ImageUploadResponseModel> call, Throwable t) {
////                //TODO  : image upload failed , add to faillistNew list to upload later
////                Log.e("TEST", "-->" + new Gson().toJson(call.request().body()));
////                Log.e("TEST", "-->" + new Gson().toJson(call.request()));
////
////                t.printStackTrace();
////                Toast.makeText(MyApplication.getAppContext(), "Error Upload -> " + t.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        });
//
//        getCompositeDisposable().add(getDataManager().uploadFile(fileToUpload, filename)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(settingResponseModel -> {
//                    if (settingResponseModel != null && settingResponseModel.getResult() != null
//                            && settingResponseModel.getISResultHasData() == 1) {
//                        serverUploadIndex++;
//                        if (bs.size() > serverUploadIndex) {
//                            uploadFile(listPaths);
//                        } else {
//                            //TODO : get image paths from server to upload with order detils
//                            OrderDetailsItem orderDetailsItems = new OrderDetailsItem();
//                            orderDetailsItems.setMainImage(settingResponseModel.getFileName());
//                            orderDetailsItems.setPatternID(getPatternId(imageModels.get(orderDetailsItemsIndex).getCurrentRatio(), patternTypeEntityList));
//                            orderDetailsItems.setQuantiy(imageModels.get(orderDetailsItemsIndex).getQuantity());
//                            orderDetailsItemsList.add(orderDetailsItems);
//
//                            bs.clear();
//                            segmentationIndex++;
//                            orderDetailsItemsIndex++;
//                            serverUploadIndex = 0;
//                            segmentImage(listPaths);
//                        }
//                    }
//
//                }, throwable -> {
//                    if (!isViewAttached()) {
//                        return;
//                    }
//                }));
//    }
//}
