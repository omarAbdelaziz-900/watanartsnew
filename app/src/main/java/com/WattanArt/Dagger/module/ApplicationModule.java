package com.WattanArt.Dagger.module;

import android.app.Application;
import android.content.Context;

//import com.WattanArt.Dagger.ApplicationContext;

import com.WattanArt.Dagger.PerActivity;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.About.AboutMvpView;
import com.WattanArt.ui.About.AboutPresenterImp;
import com.WattanArt.ui.About.AboutPresenterMvp;
import com.WattanArt.ui.CanvasPrint.CanvasPrintMvpPresnter;
import com.WattanArt.ui.CanvasPrint.CanvasPrintMvpView;
import com.WattanArt.ui.CanvasPrint.CanvasPrintPresenterImp;
import com.WattanArt.ui.ContactUs.ContactUsMvpView;
import com.WattanArt.ui.ContactUs.ContactUsPresenterImp;
import com.WattanArt.ui.ContactUs.ContactUsPresenterMvp;
import com.WattanArt.ui.EditImage.EditImageMvpView;
import com.WattanArt.ui.EditImage.EditImagePresenterImp;
import com.WattanArt.ui.EditImage.EditImagePresenterMvp;
import com.WattanArt.ui.EditProfile.EditProfileMvpPresenter;
import com.WattanArt.ui.EditProfile.EditProfileMvpView;
import com.WattanArt.ui.EditProfile.EditProfilePresenterImp;
import com.WattanArt.ui.FAQ.FAQMvpPresenterImp;
import com.WattanArt.ui.FAQ.FAQMvpView;
import com.WattanArt.ui.FAQ.FAQMvpViewPresenter;
import com.WattanArt.ui.HomeFragment.HomeMvpPresenter;
import com.WattanArt.ui.HomeFragment.HomeMvpView;
import com.WattanArt.ui.HomeFragment.HomePresenterImp;
import com.WattanArt.ui.Login.LoginMvpPresenter;
import com.WattanArt.ui.Login.LoginPresenterImp;
import com.WattanArt.ui.Order.OrderDetails.OrderDetailsMvpPresenter;
import com.WattanArt.ui.Order.OrderDetails.OrderDetailsMvpView;
import com.WattanArt.ui.Order.OrderDetails.OrderDetailsPresenterImp;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryMvpPresenter;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryMvpView;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryPresenterImp;
import com.WattanArt.ui.Register.RegisterMvpPresenter;
import com.WattanArt.ui.Register.RegisterMvpView;
import com.WattanArt.ui.Register.RegisterPresenterImp;
import com.WattanArt.ui.Setting.SettingMvpPresenter;
import com.WattanArt.ui.Setting.SettingMvpView;
import com.WattanArt.ui.Setting.SettingPresenterImp;
import com.WattanArt.ui.Shipping.ShippingMvpView;
import com.WattanArt.ui.Shipping.ShippingPresenter;
import com.WattanArt.ui.Shipping.ShippingPresenterMvp;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
//    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    AppDataManager provideAppDataManager() {
        return new AppDataManager();
    }

// WATN

    @Provides
    @PerActivity
    LoginMvpPresenter<com.WattanArt.ui.Login.LoginMvpView> provideLoginPresenter2
            (LoginPresenterImp<com.WattanArt.ui.Login.LoginMvpView> presenterImp) {
        return presenterImp;
    }

    @Provides
    @PerActivity
    RegisterMvpPresenter<RegisterMvpView> provideRegisterPresenter(
            RegisterPresenterImp<RegisterMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomePresenter
            (HomePresenterImp<HomeMvpView> presenterImp){

        return presenterImp;
    }

    @Provides
    @PerActivity
    EditProfileMvpPresenter<EditProfileMvpView> provideEditProfilePresenter
            (EditProfilePresenterImp<EditProfileMvpView> presenterImp){

        return presenterImp;
    }

    @Provides
    @PerActivity
    OrderHistoryMvpPresenter<OrderHistoryMvpView> provideOrderHistoryPresenter
            (OrderHistoryPresenterImp<OrderHistoryMvpView> presenterImp){

        return presenterImp;
    }

    @Provides
    @PerActivity
    CanvasPrintMvpPresnter<CanvasPrintMvpView> provideCanvasPrintPresenter
            (CanvasPrintPresenterImp<CanvasPrintMvpView> presenterImp){

        return presenterImp;
    }
    @Provides
    @PerActivity
    OrderDetailsMvpPresenter<OrderDetailsMvpView> provideOrderDetailsPresenter
            (OrderDetailsPresenterImp<OrderDetailsMvpView> presenterImp){

        return presenterImp;
    }
    @Provides
    @PerActivity
    AboutPresenterMvp<AboutMvpView> provideAboutPresenter(
            AboutPresenterImp<AboutMvpView> presenter) {
        return presenter;
    }
    @Provides
    @PerActivity
    ContactUsPresenterMvp<ContactUsMvpView> provideContactUsPresenter(
            ContactUsPresenterImp<ContactUsMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    ShippingPresenterMvp<ShippingMvpView> ShippingPresenter(
            ShippingPresenter<ShippingMvpView> presenter) {
        return presenter;
    }
    @Provides
    @PerActivity
    FAQMvpViewPresenter<FAQMvpView> provideFAQPresenter(
            FAQMvpPresenterImp<FAQMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SettingMvpPresenter<SettingMvpView> provideSettingPresenter(
            SettingPresenterImp<SettingMvpView> presenter) {
        return presenter;
    }



//    @Provides
//    @PerActivity
//    EditImagePresenterMvp<EditImageMvpView> EditImagePresenterImp(
//            EditImagePresenterImp<EditImageMvpView> presenter) {
//        return presenter;
//    }

}
