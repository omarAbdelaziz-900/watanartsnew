package com.WattanArt.Dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

//import com.WattanArt.Dagger.ActivityContext;
import com.WattanArt.Dagger.PerActivity;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.About.AboutMvpView;
import com.WattanArt.ui.About.AboutPresenterImp;
import com.WattanArt.ui.About.AboutPresenterMvp;
import com.WattanArt.ui.CanvasPrint.CanvasPrintMvpPresnter;
import com.WattanArt.ui.CanvasPrint.CanvasPrintMvpView;
import com.WattanArt.ui.CanvasPrint.CanvasPrintPresenterImp;
import com.WattanArt.ui.Category.CategoryMvpPresenter;
import com.WattanArt.ui.Category.CategoryMvpView;
import com.WattanArt.ui.Category.CategoryPresenterImp;
import com.WattanArt.ui.Coaster.CoasterMvpPresenter;
import com.WattanArt.ui.Coaster.CoasterMvpView;
import com.WattanArt.ui.Coaster.CoasterPresenterImp;
import com.WattanArt.ui.ContactUs.ContactUsMvpView;
import com.WattanArt.ui.ContactUs.ContactUsPresenterImp;
import com.WattanArt.ui.ContactUs.ContactUsPresenterMvp;
import com.WattanArt.ui.EditProfile.EditProfileMvpPresenter;
import com.WattanArt.ui.EditProfile.EditProfileMvpView;
import com.WattanArt.ui.EditProfile.EditProfilePresenterImp;
import com.WattanArt.ui.FAQ.FAQMvpPresenterImp;
import com.WattanArt.ui.FAQ.FAQMvpView;
import com.WattanArt.ui.FAQ.FAQMvpViewPresenter;
import com.WattanArt.ui.FlashMemory.FlashMemoryMvpPresenter;
import com.WattanArt.ui.FlashMemory.FlashMemoryMvpView;
import com.WattanArt.ui.FlashMemory.FlashMemoryPresenterImp;
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
import com.WattanArt.ui.PublicShipping.PublicShippingMvpPresenter;
import com.WattanArt.ui.PublicShipping.PublicShippingMvpView;
import com.WattanArt.ui.PublicShipping.PublicShippingPresenterImp;
import com.WattanArt.ui.Register.RegisterMvpPresenter;
import com.WattanArt.ui.Register.RegisterMvpView;
import com.WattanArt.ui.Register.RegisterPresenterImp;
import com.WattanArt.ui.Setting.SettingMvpPresenter;
import com.WattanArt.ui.Setting.SettingMvpView;
import com.WattanArt.ui.Setting.SettingPresenterImp;
import com.WattanArt.ui.Shipping.ShippingMvpView;
import com.WattanArt.ui.Shipping.ShippingPresenter;
import com.WattanArt.ui.Shipping.ShippingPresenterMvp;
import com.WattanArt.ui.ShippingCoaster.ShippingCoasterMvpPresenter;
import com.WattanArt.ui.ShippingCoaster.ShippingCoasterMvpView;
import com.WattanArt.ui.ShippingCoaster.ShippingCoasterPresenterImp;
import com.WattanArt.ui.ShippingForFlashMemory.ShippingFlashMemoryMvpPresenter;
import com.WattanArt.ui.ShippingForFlashMemory.ShippingFlashMemoryMvpView;
import com.WattanArt.ui.ShippingForFlashMemory.ShippingFlashMemoryPresenterImp;
import com.WattanArt.ui.ShippingForMobile.ShippingMobileMvpPresenter;
import com.WattanArt.ui.ShippingForMobile.ShippingMobileMvpView;
import com.WattanArt.ui.ShippingForMobile.ShippingMobilePresenterImp;
import com.WattanArt.ui.ShippingT_Shirt.ShippingT_ShirtMvpPresenter;
import com.WattanArt.ui.ShippingT_Shirt.ShippingT_ShirtMvpView;
import com.WattanArt.ui.ShippingT_Shirt.ShippingT_ShirtPresenterImp;
import com.WattanArt.ui.getFreeCredit.GetFreeCreditPresenter;
import com.WattanArt.ui.getFreeCredit.IGetFreeCredit;
import com.WattanArt.ui.getFreeCredit.IGetFreeCreditPresenter;
import com.WattanArt.ui.mobileCase.MobileMvpView;
import com.WattanArt.ui.mobileCase.MobilePresenterImp;
import com.WattanArt.ui.mobileCase.MobileMvpPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }


    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    AppDataManager provideAppDataManager() {
        return new AppDataManager();
    }


//    @Provides
//    @PerActivity
//    SlidesPresenterMvp<SlidesMvpView> provideSlidesPresenter(
//            SlidesPresenter<SlidesMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    EditImagePresenterMvp<EditImageMvpView> provideAboutPresenter(
//            EditImagePresenterImp<EditImageMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    ContactUsPresenterMvp<ContactUsMvpView> provideContactUsPresenter(
//            ContactUsPresenterImp<ContactUsMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    ManegerPresenterMvp<MangerMvpView> provideManegerPresenter(
//            ManegerPresenter<MangerMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    AlbumPresenterMvp<AlbumMvpView> provideAlbumPresenter(
//            AlbumPresenter<AlbumMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    AlbumDetailsPresenterMvp<AlbumDetailsMvpView> provideAlbumDetailsPresenter(
//            AlbumDetailsPresenter<AlbumDetailsMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    GalleryImagesPresenterMvp<GalleryImagesMvpView> provideGalleryImagesPresenter(
//            GalleryImagesPresenter<GalleryImagesMvpView> presenter) {
//        return presenter;
//    }
//
//
//    @Provides
//    @PerActivity
//    GalleryVideosPresenterMvp<GalleryVideosMvpView> provideGalleryVideosPresenter(
//            GalleryVideosPresenter<GalleryVideosMvpView> presenter) {
//        return presenter;
//    }
//
//
//    @Provides
//    @PerActivity
//    EventsPresenterMvp<EventsMvpView> provideEventsPresenter(
//            EventsPresenter<EventsMvpView> presenter) {
//        return presenter;
//    }
//
//
//    @Provides
//    @PerActivity
//    EventDetailsPresenterMvp<EventDetailsMvpView> provideEventDetailsPresenter(
//            EventDetailsPresenter<EventDetailsMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    NewsPresenterMvp<NewsMvpView> provideNewsPresenter(
//            NewsPresenter<NewsMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    NewsDetailsPresenterMvp<NewsDetailsMvpView> provideNewsDetailsPresenter(
//            NewsDetailsPresenter<NewsDetailsMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    NewsTickerPresenterMvp<NewsTickerMvpView> provideNewsTickerPresenter(
//            NewsTickerPresenter<NewsTickerMvpView> presenter) {
//        return presenter;
//    }
//
//
//    @Provides
//    @PerActivity
//    LoginPresenterMvp<LoginMvpView> provideLoginPresenter(
//            LoginPresenter<LoginMvpView> presenter) {
//        return presenter;
//    }

    //watn
    @Provides
    @PerActivity
    LoginMvpPresenter<com.WattanArt.ui.Login.LoginMvpView> provideLoginPresenter2
    (LoginPresenterImp<com.WattanArt.ui.Login.LoginMvpView> presenterImp) {
        return presenterImp;
    }

    @Provides
    @PerActivity
    RegisterMvpPresenter<RegisterMvpView> provideRegisterPresenter
            (RegisterPresenterImp<RegisterMvpView> presenterImp) {

        return presenterImp;
    }

    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomePresenter
            (HomePresenterImp<HomeMvpView> presenterImp) {

        return presenterImp;
    }

    @Provides
    @PerActivity
    CategoryMvpPresenter<CategoryMvpView> provideCategooryPresenter
            (CategoryPresenterImp<CategoryMvpView> presenterImp){

        return presenterImp;
    }

    @Provides
    @PerActivity
    EditProfileMvpPresenter<EditProfileMvpView> provideEditProfilePresenter
            (EditProfilePresenterImp<EditProfileMvpView> presenterImp) {

        return presenterImp;
    }

    @Provides
    @PerActivity
    OrderHistoryMvpPresenter<OrderHistoryMvpView> provideOrderHistoryPresenter
            (OrderHistoryPresenterImp<OrderHistoryMvpView> presenterImp) {

        return presenterImp;
    }

    @Provides
    @PerActivity
    CanvasPrintMvpPresnter<CanvasPrintMvpView> provideCanvasPrintPresenter
            (CanvasPrintPresenterImp<CanvasPrintMvpView> presenterImp) {

        return presenterImp;
    }

    @Provides
    @PerActivity
    OrderDetailsMvpPresenter<OrderDetailsMvpView> provideOrderDetailsPresenter
            (OrderDetailsPresenterImp<OrderDetailsMvpView> presenterImp) {

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
    IGetFreeCreditPresenter<IGetFreeCredit> providesGetFreeCredit(
            GetFreeCreditPresenter<IGetFreeCredit> presenter) {
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
    FAQMvpViewPresenter<FAQMvpView> provideFAQPresenter(
            FAQMvpPresenterImp<FAQMvpView> presenter) {
        return presenter;
    }

    //
    //end
//    @Provides
//    @PerActivity
//    AllGalleryPresenterMvp<AllGalleryMvpView> provideAllGalleryPresenter(
//            AllGalleryPresenter<AllGalleryMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    AllGalleryVideosPresenterMvp<AllGalleryVideosMvpView> provideAllGalleryVideosPresenter(
//            AllGalleryVideosPresenter<AllGalleryVideosMvpView> presenter) {
//        return presenter;
//    }
//
//    @Provides
//    @PerActivity
//    AllGalleryImagesPresenterMvp<AllGalleryImagsMvpView> provideAllGalleryImagsPresenter(
//            AllGalleryImagsPresenter<AllGalleryImagsMvpView> presenter) {
//        return presenter;
//    }
//
//
    @Provides
    @PerActivity
    ContactUsPresenterMvp<ContactUsMvpView> ContactUsServicesPresenter(
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
    SettingMvpPresenter<SettingMvpView> provideSettingPresenter(
            SettingPresenterImp<SettingMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MobileMvpPresenter<MobileMvpView> mobileMvpViewMobilePresenterMvp(
            MobilePresenterImp<MobileMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FlashMemoryMvpPresenter<FlashMemoryMvpView> flashMemoryMvpViewMobilePresenterMvp(
            FlashMemoryPresenterImp<FlashMemoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShippingMobileMvpPresenter<ShippingMobileMvpView> shippingMobileMvpViewMobilePresenterMvp(
            ShippingMobilePresenterImp<ShippingMobileMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShippingFlashMemoryMvpPresenter<ShippingFlashMemoryMvpView> shippingFlashMemoryMvpViewMobilePresenterMvp(
            ShippingFlashMemoryPresenterImp<ShippingFlashMemoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CoasterMvpPresenter<CoasterMvpView> coasterMvpViewCoasterMvpPresenter(
            CoasterPresenterImp<CoasterMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShippingT_ShirtMvpPresenter<ShippingT_ShirtMvpView> shippingT_shirtMvpViewShippingT_shirtMvpPresenter(
            ShippingT_ShirtPresenterImp<ShippingT_ShirtMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShippingCoasterMvpPresenter<ShippingCoasterMvpView> shippingCoasterMvpViewShippingCoasterMvpPresenter(
            ShippingCoasterPresenterImp<ShippingCoasterMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PublicShippingMvpPresenter<PublicShippingMvpView> shippingMvpViewPublicShippingMvpPresenter(
            PublicShippingPresenterImp<PublicShippingMvpView> presenter) {
        return presenter;
    }

}
