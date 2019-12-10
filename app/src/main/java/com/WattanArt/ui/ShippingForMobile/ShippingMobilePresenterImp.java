package com.WattanArt.ui.ShippingForMobile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.ValidationTool;
import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.Response;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.ThrowableModel;
import com.WattanArt.network.ApiService;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.Register.RegisterActivity;
import com.WattanArt.ui.Shipping.ShippingPresenter;
import com.WattanArt.ui.base.BasePresenter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.WattanArt.ui.Register.RegisterActivity.ShippingFlashRegister;
import static com.WattanArt.ui.Register.RegisterActivity.ShippingMobileRegister;
import static com.WattanArt.ui.Register.RegisterActivity.ShippingRegister;
import static com.WattanArt.ui.ShippingForMobile.ShippingMobileActivity.OPEN_REGISTERATION_CODE_FROM_SHOPPING_MOBILE;

public class ShippingMobilePresenterImp <V extends ShippingMobileMvpView> extends BasePresenter<V> implements ShippingMobileMvpPresenter<V>  {

    List<SelectCountryCitiyListsResponseModel.Result.PatternTypeBean> patternTypeEntityList;
    UserData userData = new UserData();
    ValidationTool validationTool = new ValidationTool(MyApplication.getAppContext());
    boolean isCouponCodeApplied = false;
    int serverUploadIndex = 0;
    File filetoUploadForMobile = null;
    File filetoUploadForCover = null;


    @Inject
    public ShippingMobilePresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


        @Override
        public void getShippingPrices() {
            getMvpView().showLoadingInner();
            getCompositeDisposable().add(getDataManager().
                    getCountries_citiesList(userData.getLocalization(MyApplication.getAppContext()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(selectCountryCitiyListsResponseModel -> {
                        if (selectCountryCitiyListsResponseModel != null && selectCountryCitiyListsResponseModel.getISResultHasData() == 1) {
                            this.patternTypeEntityList = selectCountryCitiyListsResponseModel.getResult().getPatternType();
                            ShippingMobilePresenterImp.this.getMvpView().returnPatternList(selectCountryCitiyListsResponseModel.getResult());
                        } else {
                            ShippingMobilePresenterImp.this.getMvpView().showMessage(R.string.some_error);
                        }
                        getMvpView().hideLoadingInner();
                    }, throwable -> {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoadingInner();

                        handleThrowableError(throwable);
                    }));
        }

    @Override
    public void getOrderSubmit(MobileOrderRequest mobileOrderRequest) {
        getMvpView().showLoadingInner();
        getCompositeDisposable().add(getDataManager().getSubmitOrderForMobile(mobileOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MobileOrderResponse>() {
                               @Override
                               public void accept(MobileOrderResponse responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 ) {
                                       getMvpView().returnFromMakeOrder(responseModel);

                                   } else {
//                                       isCouponCodeApplied = false;
//                                       getMvpView().applyCouponCode(false);
                                   }

                                   getMvpView().hideLoadingInner();
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }

                                getMvpView().hideLoadingInner();
                                handleThrowableError(throwable);

                            }
                        }
                )

        );
    }

    @Override
    public boolean checkCouponCode(CouponCodeModel couponCodeModel) {

        getMvpView().showLoadingInner();
        getCompositeDisposable().add(getDataManager().checkCouponCode(couponCodeModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                               @Override
                               public void accept(Response responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult() == 1) {
                                       getMvpView().applyCouponCode(true);
                                       isCouponCodeApplied = true;
                                   } else {
                                       isCouponCodeApplied = false;
                                       getMvpView().applyCouponCode(false);
                                   }

                                   getMvpView().hideLoadingInner();
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }

                                getMvpView().hideLoadingInner();
                                handleThrowableError(throwable);

                            }
                        }
                )

        );
        return true;
    }

    @Override
    public void showLoginPopup(Context coontext, boolean code ,EditText copenText) {

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

                if (copenText.getText().toString().isEmpty()){
                    boolean validPassword = validationTool.validatePassword(mPasswordEditText, MyApplication.getAppContext().getString(R.string.enter_password));
                    boolean validMail = validationTool.validateEmail(mEmailEditText, MyApplication.getAppContext().getString(R.string.invalid_email));
                    if (validMail && validPassword) {
                        getMvpView().showLoadingInner();


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
//

                                                                        getMvpView().applyAfterLogin();


                                                                    } else if (responseModel != null && responseModel.getResult() != null) {
                                                                        getMvpView().hideLoadingInner();
                                                                        getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.fail_login));
                                                                    }

                                                                }
                                                                , new Consumer<Throwable>() {
                                                                    @Override
                                                                    public void accept(Throwable throwable) throws Exception {
                                                                        if (!isViewAttached()) {
                                                                            return;
                                                                        }

                                                                        getMvpView().hideLoadingInner();
                                                                        handleThrowableError(throwable);

                                                                    }
                                                                }
                                                        )
                                        );

                                    }
                                });

                    }
                }else {
                    if (code){
                        boolean validPassword = validationTool.validatePassword(mPasswordEditText, MyApplication.getAppContext().getString(R.string.enter_password));
                        boolean validMail = validationTool.validateEmail(mEmailEditText, MyApplication.getAppContext().getString(R.string.invalid_email));
                        if (validMail && validPassword) {
                            getMvpView().showLoadingInner();


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
//

                                                                            getMvpView().applyAfterLogin();


                                                                        } else if (responseModel != null && responseModel.getResult() != null) {
                                                                            getMvpView().hideLoadingInner();
                                                                            getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.fail_login));
                                                                        }

                                                                    }
                                                                    , new Consumer<Throwable>() {
                                                                        @Override
                                                                        public void accept(Throwable throwable) throws Exception {
                                                                            if (!isViewAttached()) {
                                                                                return;
                                                                            }

                                                                            getMvpView().hideLoadingInner();
                                                                            handleThrowableError(throwable);

                                                                        }
                                                                    }
                                                            )
                                            );

                                        }
                                    });

                        }
                    }else {
                        getMvpView().showMessage(coontext.getString(R.string.check_code));
                    }
                }


            }
        });


        mDialog.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.dismiss();
                Intent intent = new Intent(coontext, RegisterActivity.class);
                intent.putExtra(ShippingMobileRegister, true);
                intent.putExtra(ShippingRegister, false);
                intent.putExtra(ShippingFlashRegister, false);
                ((Activity) coontext).startActivityForResult(intent, OPEN_REGISTERATION_CODE_FROM_SHOPPING_MOBILE);

//                getMvpView().showMessage("This feature is under developement");
            }
        });
        mDialog.show();
    }

    @Override
    public void returnUploadedImageForMobile(List<String> listPaths, File filePath) {

//        getMvpView().showLoading();

        getMvpView().showLoadingInner();
        int index = serverUploadIndex + 1;

        filetoUploadForMobile = new File(listPaths.get(0));
        String thisFileName;

        if (getExtension(listPaths.get(0)).toLowerCase().contains("png")) {
            thisFileName = getNameWithOutExtension(filetoUploadForMobile.getName()) + ".png.part_" + index + "." +1;
        } else {
            thisFileName = getNameWithOutExtension(filetoUploadForMobile.getName()) + ".jpg.part_" + index + "." + 1;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);

//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", filePath.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filePath.getName());



        ApiService getResponse = AppDataManager.getApi();
        Call<ImageUploadResponseModel> call = getResponse.uploadFileNew(fileToUpload, filename);


        call.enqueue(new Callback<ImageUploadResponseModel>() {
            @Override
            public void onResponse(Call<ImageUploadResponseModel> call, retrofit2.Response<ImageUploadResponseModel> response) {

//                Log.e("Stateee",response.body().getState()+"");
//
                getMvpView().returnUploadedImageForMobile(response.body());

                getMvpView().hideLoadingInner();

                if (response.code() == 200) {
                    if (response.body().getState() == 1) {
                        //could not find file
                        Toast.makeText(MyApplication.getAppContext(), "Error 1", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 2) {
                        //part file catch from service
                        Toast.makeText(MyApplication.getAppContext(), "Error 2", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 3) {
                        //success in part and complete upload other parts , file name is uploaded parts concatenated with ,
//                        (listPaths);
                    } else if (response.body().getState() == 4) {
                        //all parts uploaded but error to join them by service
                        Toast.makeText(MyApplication.getAppContext(), "Error 4", Toast.LENGTH_SHORT).show();


                    } else if (response.body().getState() == 5) {
                        //image completed successfully
                        try {
//                            OrderDetailsItem orderDetailsItems = new OrderDetailsItem();
//                            orderDetailsItems.setMainImage(response.body().getFileName());
//                            orderDetailsItems.setPatternID(10);
//                            orderDetailsItems.setQuantiy(imageModels.get(orderDetailsItemsIndex).getQuantity());
//                            orderDetailsItemsList.add(orderDetailsItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    Log.e("TEST on success", "-->" + new Gson().toJson(call.request()));

                } else {
                    onFailure(call, new Throwable());
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

                getMvpView().hideLoadingInner();

            }
        });

    }

    @Override
    public void returnUploadedImageForCover(List<String> list, File filePath) {



        getMvpView().showLoadingInner();
        int index = serverUploadIndex + 1;

        filetoUploadForCover = new File(list.get(0));
        String thisFileName;

        if (getExtension(list.get(0)).toLowerCase().contains("png")) {
            thisFileName = getNameWithOutExtension(filetoUploadForCover.getName()) + ".png.part_" + index + "." +1;
        } else {
            thisFileName = getNameWithOutExtension(filetoUploadForCover.getName()) + ".jpg.part_" + index + "." + 1;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", thisFileName, requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), thisFileName);

//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), filePath);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", filePath.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filePath.getName());



        ApiService getResponse = AppDataManager.getApi();
        Call<ImageUploadResponseModel> call = getResponse.uploadFileNew(fileToUpload, filename);


        call.enqueue(new Callback<ImageUploadResponseModel>() {
            @Override
            public void onResponse(Call<ImageUploadResponseModel> call, retrofit2.Response<ImageUploadResponseModel> response) {

//                Log.e("Stateee",response.body().getState()+"");
//
                getMvpView().returnUploadedImageForCover(response.body());

                getMvpView().hideLoadingInner();

//                ViewHelper.hideProgressDialog();
                if (response.code() == 200) {
                    if (response.body().getState() == 1) {
                        //could not find file
                        Toast.makeText(MyApplication.getAppContext(), "Error 1", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 2) {
                        //part file catch from service
                        Toast.makeText(MyApplication.getAppContext(), "Error 2", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getState() == 3) {
                        //success in part and complete upload other parts , file name is uploaded parts concatenated with ,
//                        (listPaths);
                    } else if (response.body().getState() == 4) {
                        //all parts uploaded but error to join them by service
                        Toast.makeText(MyApplication.getAppContext(), "Error 4", Toast.LENGTH_SHORT).show();


                    } else if (response.body().getState() == 5) {
                        //image completed successfully
                        try {
//                            OrderDetailsItem orderDetailsItems = new OrderDetailsItem();
//                            orderDetailsItems.setMainImage(response.body().getFileName());
//                            orderDetailsItems.setPatternID(10);
//                            orderDetailsItems.setQuantiy(imageModels.get(orderDetailsItemsIndex).getQuantity());
//                            orderDetailsItemsList.add(orderDetailsItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    Log.e("TEST on success", "-->" + new Gson().toJson(call.request()));

                } else {
                    onFailure(call, new Throwable());
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

                getMvpView().hideLoadingInner();

            }
        });

    }

    private String getExtension(String someFilepath) {
        String ext = someFilepath.substring(someFilepath.lastIndexOf("."));
        Log.e("EXT", "" + ext);
        return ext;
    }

    private String getNameWithOutExtension(String s) {
        return s != null && s.lastIndexOf(".") > 0 ? s.substring(0, s.lastIndexOf(".")) : s;
    }


}

