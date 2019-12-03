package com.WattanArt.ui.EditProfile;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.model.Request.ChangePasswordRequestModel;
import com.WattanArt.model.Request.UpdateProfileRequestModel;
import com.WattanArt.model.Response.PasswordResponseModel;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EditProfilePresenterImp<V extends EditProfileMvpView> extends BasePresenter<V> implements EditProfileMvpPresenter<V> {
    private UserData userData;

    @Inject
    public EditProfilePresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getProfileData(AppCompatActivity context) {
        getMvpView().showLoading();
        userData = new UserData();
        String userId = userData.getUserID(context);
        Log.d("userId", userId + "");

        getCompositeDisposable().add(getDataManager().getProfileData(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<RegisterResponseModel>() {
                                       @Override
                                       public void accept(RegisterResponseModel responseModel) throws Exception {
                                           if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult() != null) {
                                               getMvpView().returnProfileData(responseModel);
                                               getMvpView().hideLoading();
//                                       getMvpView().showMessage(responseModel.getResult().getObj().getEmail());
                                               //
                                           } else if (responseModel != null && responseModel.getResult() != null) {
                                               if (responseModel.getResult().getState() == Constants.MAIL_EXIST) {
                                                   getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.email_exist));

                                               } else if (responseModel.getResult().getState() == Constants.LOGIN_OR_REGISTER_FAIL) {
                                                   getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.update_profile_fail));

                                               } else if (responseModel.getResult().getState() == Constants.MISSING_DATA) {
                                                   getMvpView().showMessage(responseModel.getResult().getMessage());
                                               } else if (responseModel.getResult().getState() == Constants.CATCH) {
                                                   getMvpView().showMessage(responseModel.getResult().getMessage());
                                               }
                                               getMvpView().hideLoading();
                                           }
                                           getResponsOfCountryCityList(userData.getLocalization(context));
//                                           getMvpView().hideLoading();
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
    public void sendUpdatedProfileData(String fullName, String email, String phone,
                                       String UserID, int cityId, int countryId, int language) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().updateProfile(new UpdateProfileRequestModel(fullName, email, phone, UserID, cityId, countryId, language))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterResponseModel>() {
                               @Override
                               public void accept(RegisterResponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 &&
                                           responseModel.getResult() != null && responseModel.getResult().getSuccess()) {
                                       getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.update_profile_success));
                                       getMvpView().openHomeActivity();
                                       getMvpView().hideLoading();
                                   } else if (responseModel != null && responseModel.getResult() != null) {

                                       getMvpView().showMessage(null);
                                       getMvpView().hideLoading();
                                   }

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

//        getMvpView().showLoading();
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

                                       getMvpView().returnCountryCityList(selectCountryCitiyListsResponseModel.getResult());

                                   } else {
                                       getMvpView().showMessage(null);
                                   }
//                                   getMvpView().hideLoading();
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }
//                                getMvpView().hideLoading();
                                handleThrowableError(throwable);
                            }
                        }
                )
        );
    }

    @Override
    public void changePassword(String oldPass, String newPass, String userId) {


        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().changePassword(new ChangePasswordRequestModel(oldPass, newPass, userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PasswordResponseModel>() {
                               @Override
                               public void accept(PasswordResponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult()) {
                                       getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.pass_changed_succsess));

                                   } else {

                                       getMvpView().showMessage(MyApplication.getAppContext().getString(R.string.fail_changed_succsess));
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


}
