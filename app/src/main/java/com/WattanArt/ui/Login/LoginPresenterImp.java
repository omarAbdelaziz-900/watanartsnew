package com.WattanArt.ui.Login;

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
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.model.Response.PasswordResponseModel;
import com.WattanArt.model.Response.SocialUserModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.Register.RegisterActivity;
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

public class LoginPresenterImp<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {
    private FacebookIntegrationTool facebookIntegrationTool = new FacebookIntegrationTool();
    private SocialUserModel faceBookDataLModel;
    private UserData userData = new UserData();
    boolean FaceHasMail = false;

    @Inject
    public LoginPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getLoginData(AppCompatActivity context, String name, String email, String fcm_token,
                             String password, String deviceID, String deviceTypeID, boolean isRemmember) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().login(new LoginRequestModel(name, email, fcm_token, deviceID, deviceTypeID
                , null, password,
                userData.getLocalization(context)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseModel -> {
                            if (responseModel != null && responseModel.getISResultHasData() == 1 &&
                                    responseModel.getResult().getSuccess()) {
                                userData.saveUserID(context, responseModel.getResult().getObj().getUserID());
                                userData.saveUserType(context, responseModel.getResult().getObj().getRegisterType());
                                userData.savePhone(context, responseModel.getResult().getObj().getPhone());

                                if (isRemmember) {
                                    userData.setRemmemberMe(context, isRemmember);
                                }

                                getMvpView().hideLoading();
                                getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.login_success));
                                getMvpView().openMainActivity(responseModel.getResult().getObj().getUserID());
                                //
                            } else if (responseModel != null && responseModel.getResult() != null) {
                                getMvpView().hideLoading();
                                if (responseModel.getResult().getMessage().equals("Fail")) {
                                    getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.fail_login));

                                } else if (responseModel.getResult().getMessage().equals("Locked")) {
                                    getMvpView().showMessage("Locked");
                                }

                            }


                        }
                        , throwable -> {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();
                            handleThrowableError(throwable);

                        }
                )

        );


    }

    @Override
    public void getLoginDataFromSocial(AppCompatActivity appCompatActivity) {
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
                        Log.d("face email", names.getString(0) + "");
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
                                            loginWithFacebook(appCompatActivity, faceBookDataLModel.id, faceBookDataLModel.email,
                                                    newToken, deviceID, "1");
                                        }
                                    });


                        }
                    } else {
                        Log.d("no mail from face", names.getString(0) + "");
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            if (resonceJsonObject != null) {
                Log.d("FBresponse", resonceJsonObject.toString());
            }
        });


    }

    @Override
    public void loginWithFacebook(AppCompatActivity context, String socialId, String mail, String fcm_token, String device_id,
                                  String deviceTypeId) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().registerOrLoginSocial(new LoginRequestModel(socialId, mail, fcm_token, device_id, deviceTypeId))
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
                                               if (responseModel.getResult().getObj() != null) {
                                                   getMvpView().openMainActivity(responseModel.getResult().getObj().getUserID());

                                               } else {
                                                   getMvpView().openMainActivity("");

                                               }
                                               //
                                           } else if (responseModel != null && responseModel.getResult() != null) {
                                               if (responseModel.getResult().getState() == Constants.LOGIN_OR_REGISTER_FAIL) {
                                                   Intent intent = new Intent(MyApplication.getAppContext(), RegisterActivity.class);
                                                   intent.putExtra(Constants.FACEBOOK_FIRST_NAME,
                                                           faceBookDataLModel.name + faceBookDataLModel.lastName);
                                                   intent.putExtra(Constants.FACEBOOK_LOGIN, Constants.FACEBOOK_REGISTER_WithPassword);
                                                   intent.putExtra(Constants.FACEBOOK_ID, faceBookDataLModel.id);
                                                   context.startActivity(intent);
                                                   context.finish();
                                               } else {
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
    public void sendforgetPasswordToEmail(String email) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().forgetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PasswordResponseModel>() {
                               @Override
                               public void accept(PasswordResponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult()) {


                                       getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.forget_pass_succsess));

                                   } else {

                                       getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.invalid_email));
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
