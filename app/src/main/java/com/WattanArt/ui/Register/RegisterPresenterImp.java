package com.WattanArt.ui.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.social_network.FacebookIntegrationTool;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Request.RegisterRequestModel;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.Response.SocialUserModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenterImp<V extends RegisterMvpView> extends
        BasePresenter<V> implements RegisterMvpPresenter<V> {

    private FacebookIntegrationTool facebookIntegrationTool = new FacebookIntegrationTool();
    private SocialUserModel faceBookDataLModel;
    private UserData userData = new UserData();
    boolean FaceHasMail = false;


    @Inject
    public RegisterPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void UserRegister(AppCompatActivity context, String fullName, String email, String password, String phone,
                             int registerType, int cityID, int countryID, String FB, String fcm_Token
            , String device_id, String deviceTypeID, int language) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .register(new RegisterRequestModel(fullName, email, password, phone, registerType, cityID,
                        countryID, FB, fcm_Token, device_id, deviceTypeID,
                        language))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterResponseModel>() {
                               @Override
                               public void accept(RegisterResponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 &&
                                           responseModel.getResult().getSuccess()) {

                                       //to save user login data
                                       userData.setRemmemberMe(context, true);

                                       userData.savePhone(context, responseModel.getResult().getObj().getPhone());
                                       userData.saveUserID(context, responseModel.getResult().getObj().getUserID());
                                       userData.saveUserType(context, responseModel.getResult().getObj().getRegisterType());
                                       getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.register_success));
                                       getMvpView().openHomeActivity(responseModel.getResult().getObj().getUserID());

                                   } else if (responseModel != null && responseModel.getResult() != null) {
                                       if (responseModel.getResult().getState() == Constants.MAIL_EXIST) {
                                           getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.email_exist));
                                       } else if (responseModel.getResult().getState() == Constants.LOGIN_OR_REGISTER_FAIL) {
                                           getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.register_fail));

                                       } else if (responseModel.getResult().getState() == Constants.MISSING_DATA) {
                                           getMvpView().showMessage(responseModel.getResult().getMessage());
                                       } else if (responseModel.getResult().getState() == Constants.CATCH) {
                                           getMvpView().showMessage(responseModel.getResult().getMessage());
                                       }
                                   }


                                   getMvpView().hideLoading();
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
    public void getResponsOfCountryCityList(int Language) {

        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getCountries_citiesList(Language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SelectCountryCitiyListsResponseModel>() {
                               @Override
                               public void accept(SelectCountryCitiyListsResponseModel selectCountryCitiyListsResponseModel)
                                       throws Exception {
                                   if (selectCountryCitiyListsResponseModel != null &&
                                           selectCountryCitiyListsResponseModel.getISResultHasData() == 1 &&
                                           selectCountryCitiyListsResponseModel.getResult() != null) {

                                       getMvpView().returnList(selectCountryCitiyListsResponseModel.getResult());

                                   } else {
                                       //how to hold context for get string
                                       getMvpView().showMessage(null);
                                   }
                                   getMvpView().hideLoading();
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
    public void getFacebookAccount(AppCompatActivity appCompatActivity) {

        facebookIntegrationTool.facebookIntegration(appCompatActivity, (FacebookIntegrationTool.FacebookResponse) resonceJsonObject -> {
            if (resonceJsonObject != null) {
                Log.d("namesLenght", resonceJsonObject.names().length() + "");
                List<String> paramterNames = new ArrayList<>();

                JSONArray names = resonceJsonObject.names();
                for (int i = 0; i < names.length(); i++) {
                    try {
                        paramterNames.add(names.getString(i));
                        if (paramterNames.get(i).equals("email")) {
                            FaceHasMail = true;
                            Log.d("FaceHasMail", paramterNames.get(i) + "");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    if (FaceHasMail) {
                        Log.d("faceEmailExist and its:", names.getString(0) + "");
                        if (resonceJsonObject.getInt(Constants.FACEBOOK_ID) != 0
                                && resonceJsonObject.getString(Constants.FACEBOOK_EMAIL) != null
                                && resonceJsonObject.getString(Constants.FACEBOOK_FIRST_NAME) != null) {
                            faceBookDataLModel = new SocialUserModel();
                            faceBookDataLModel.setId(resonceJsonObject.getString(Constants.FACEBOOK_ID));
                            faceBookDataLModel.setEmail(resonceJsonObject.getString(Constants.FACEBOOK_EMAIL));
                            faceBookDataLModel.setName(resonceJsonObject.getString(Constants.FACEBOOK_FIRST_NAME));
                            faceBookDataLModel.setLastName(resonceJsonObject.getString(Constants.FACEBOOK_LAST_NAME));

                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(appCompatActivity,
                                    new OnSuccessListener<InstanceIdResult>() {
                                        @Override
                                        public void onSuccess(InstanceIdResult instanceIdResult) {
                                            String newToken = instanceIdResult.getToken();
                                            Log.e("newToken", newToken);

                                            String deviceID = UtilitiesManager.getDeviceID(appCompatActivity);
                                            loginWithFacebook(appCompatActivity, faceBookDataLModel.id,
                                                    faceBookDataLModel.email, newToken, deviceID, "1");
                                        }
                                    });


                        }
                    } else {
                        Log.d("no EmailFromFace", names.getString(0) + "");
                        if (resonceJsonObject.getInt(Constants.FACEBOOK_ID) != 0
                                && resonceJsonObject.getString(Constants.FACEBOOK_FIRST_NAME) != null) {
                            faceBookDataLModel = new SocialUserModel();
                            faceBookDataLModel.setId(resonceJsonObject.getString(Constants.FACEBOOK_ID));
                            faceBookDataLModel.setName(resonceJsonObject.getString(Constants.FACEBOOK_FIRST_NAME));
                            faceBookDataLModel.setLastName(resonceJsonObject.getString(Constants.FACEBOOK_LAST_NAME));


                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(appCompatActivity,
                                    new OnSuccessListener<InstanceIdResult>() {
                                        @Override
                                        public void onSuccess(InstanceIdResult instanceIdResult) {
                                            String newToken = instanceIdResult.getToken();
                                            Log.e("newToken", newToken);

                                            String deviceID = UtilitiesManager.getDeviceID(appCompatActivity);
                                            loginWithFacebook(appCompatActivity, faceBookDataLModel.id, "", newToken,
                                                    deviceID, "1");
                                        }
                                    });
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });
    }

    @Override
    public void loginWithFacebook(AppCompatActivity context, String socialId, String mail,
                                  String fcm_token, String device_id, String deviceTypeID) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().registerOrLoginSocial(new LoginRequestModel(socialId, mail,
                        fcm_token, device_id, deviceTypeID))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<LoginResponseModel>() {
                                       @Override
                                       public void accept(LoginResponseModel responseModel) throws Exception {
                                           if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult().getSuccess()) {

                                               userData.saveUserID(context, responseModel.getResult().getObj().getUserID());
                                               userData.saveUserType(context, responseModel.getResult().getObj().getRegisterType());
//                                       userData.savePhone(context, responseModel.getResult().getObj().getPhone());

                                               userData.setRemmemberMe(context, true);
                                               getMvpView().openHomeActivity(responseModel.getResult().getObj().getUserID());
                                               //
                                           } else if (responseModel != null && responseModel.getResult() != null) {
                                               if (responseModel.getResult().getState() == Constants.LOGIN_OR_REGISTER_FAIL)
                                                   getMvpView().returnFacebookRegistrationData(
                                                           faceBookDataLModel.name, faceBookDataLModel.lastName, faceBookDataLModel.id);
                                           }

                                           getMvpView().hideLoading();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookIntegrationTool.onActivityResult(requestCode, resultCode, data);

    }


}
