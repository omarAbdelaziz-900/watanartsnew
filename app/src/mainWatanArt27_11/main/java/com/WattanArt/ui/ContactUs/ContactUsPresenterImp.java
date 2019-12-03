package com.WattanArt.ui.ContactUs;

import com.WattanArt.R;
import com.WattanArt.model.Request.LanguageRequestModel;
import com.WattanArt.model.Request.SendFeedBackRequestModel;
import com.WattanArt.model.Response.ContactUsModel;
import com.WattanArt.model.Response.SendFeedBackResponsetModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Team on 1/18/2018.
 */

public class ContactUsPresenterImp<V extends ContactUsMvpView> extends BasePresenter<V>
        implements ContactUsPresenterMvp<V> {

    @Inject
    public ContactUsPresenterImp(AppDataManager dataManager , CompositeDisposable compositeDisposable) {
        super(dataManager , compositeDisposable);
    }

    public void getContactUsData(int lang) {
        getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager().getContactUs(lang)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ContactUsModel>() {
                        @Override
                        public void accept(@NonNull ContactUsModel contactUsModel)
                                throws Exception {
                            if (contactUsModel != null && contactUsModel.getISResultHasData()==1) {
                                getMvpView().UpdateInfoUi(contactUsModel);
                            }else {
                                getMvpView().showMessage(R.string.some_error);
                            }
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable)
                                throws Exception {
                            if (!isViewAttached()) {
                                return;
                            }
    
                            getMvpView().hideLoading();

                            handleThrowableError(throwable);
                     }
                    }));

    }

    @Override
    public void sendFeedBack(String email, String subject, String message) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().sendFeedback(new SendFeedBackRequestModel(email,subject,message))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SendFeedBackResponsetModel>() {
                    @Override
                    public void accept(@NonNull SendFeedBackResponsetModel contactUsModel)
                            throws Exception {
                        if (contactUsModel != null && contactUsModel.getISResultHasData()==1) {
                            getMvpView().showMessage(R.string.send_messege_succsess);
                            getMvpView().flagSuccessfullySend();

                        }else {
                            getMvpView().showMessage(R.string.some_error);
                        }
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        handleThrowableError(throwable);
                    }
                }));


    }


}
