package com.WattanArt.ui.Category;

import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryPresenterImp <V extends CategoryMvpView > extends BasePresenter<V> implements CategoryMvpPresenter<V>{

    @Inject
    public CategoryPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void getSubCategory(int language ,int catId) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getSubCategory(language,catId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryMobileRsponseModel>() {
                               @Override
                               public void accept(CategoryMobileRsponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult()!=null ) {
                                       getMvpView().returnSubCategory(responseModel);
                                       //
                                   } else if (responseModel != null && responseModel.getResult() != null) {

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
//                                handleThrowableError(throwable);

                            }
                        }
                )

        );

    }
}
