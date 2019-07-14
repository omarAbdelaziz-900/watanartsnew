package com.WattanArt.ui.About;

import com.WattanArt.R;
import com.WattanArt.model.Request.LanguageRequestModel;
import com.WattanArt.model.Response.AboutModel;
import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.model.Response.TermsResposeModel;
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

public class AboutPresenterImp<V extends AboutMvpView> extends BasePresenter<V>
        implements AboutPresenterMvp<V> {

    @Inject
    public AboutPresenterImp(AppDataManager dataManager , CompositeDisposable compositeDisposable) {
        super(dataManager , compositeDisposable );
    }

    @Override
    public void getAboutUsData(int lang) {
        getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager().
                    getAboutUsData(lang)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AboutUsResponseModel>() {
                        @Override
                        public void accept(@NonNull AboutUsResponseModel aboutModel)
                                throws Exception {
                            if (aboutModel != null && aboutModel.getISResultHasData()==1) {
                                getMvpView().UpdateUi(aboutModel);
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
    public void getTermsAndConditions(int lang) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().
                getTerms(new LanguageRequestModel(String.valueOf(lang)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TermsResposeModel>() {
                    @Override
                    public void accept(@NonNull TermsResposeModel aboutModel)
                            throws Exception {
                        if (aboutModel != null && aboutModel.getISResultHasData()==1) {
                            getMvpView().UpdateUi(aboutModel);
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
