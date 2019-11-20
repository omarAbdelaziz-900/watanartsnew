package com.WattanArt.Dagger.component;


import com.WattanArt.Dagger.PerActivity;
import com.WattanArt.Dagger.module.ActivityModule;
//import com.WattanArt.ui.OldPackages.About.AboutFragment;
//import com.WattanArt.ui.OldPackages.AlbumDetails.AlbumDetailsActivity;
//import com.WattanArt.ui.OldPackages.Albums.AlbumsFragment;
//import com.WattanArt.ui.OldPackages.AllGallery.AllGalleryActivity;
//import com.WattanArt.ui.OldPackages.AllGalleryImages.AllGalleryImagsFragment;
//import com.WattanArt.ui.OldPackages.AllGalleryVideos.AllGalleryVideosFragment;
//import com.WattanArt.ui.OldPackages.ContactUs.ContactUsFragment;
//import com.WattanArt.ui.OldPackages.ContactUsServices.ContactUsServicesFragment;
//import com.WattanArt.ui.OldPackages.EventDetails.EventDetailsActivity;
//import com.WattanArt.ui.OldPackages.Events.EventsFragment;
//import com.WattanArt.ui.OldPackages.GalleryImagesById.GalleryImagesActivity;
//import com.WattanArt.ui.OldPackages.GalleryVideosById.GalleryVideosActivity;
//import com.WattanArt.ui.OldPackages.LoginOld.LoginActivity;
//import com.WattanArt.ui.OldPackages.ManegerWords.ManegerWordsFragment;
//import com.WattanArt.ui.OldPackages.News.NewsFragment;
//import com.WattanArt.ui.OldPackages.NewsDetails.NewsDetailsActivity;
//import com.WattanArt.ui.OldPackages.NewsTicker.NewsTickerFragment;
//import com.WattanArt.ui.OldPackages.Slides.SlidesFragment;
import com.WattanArt.ui.mobileCase.ComponentActivity;
import com.WattanArt.ui.About.AboutActivity;
import com.WattanArt.ui.ContactUs.ContactInnerFragment;
import com.WattanArt.ui.ContactUs.ContactUsActivity;
import com.WattanArt.ui.ContactUs.InfoFragment;
import com.WattanArt.ui.FAQ.FaqActivity;
import com.WattanArt.ui.Order.OrderDetails.OrderDetailsActivity;
import com.WattanArt.ui.Order.OrderHistory.OrderHistoryFragment;
import com.WattanArt.ui.Setting.SettingFragment;
import com.WattanArt.ui.Shipping.ShippingActivity;
import com.WattanArt.ui.Terms.TermsActivity;
import com.WattanArt.ui.getFreeCredit.GetFreeCreditActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(com.WattanArt.ui.Login.LoginActivity loginActivity);


    void inject(com.WattanArt.ui.Register.RegisterActivity registerActivity);

    void inject(com.WattanArt.ui.HomeFragment.HomeFragment homeFragment);
    void inject(com.WattanArt.ui.EditProfile.EditProfileActivity editProfileActivity);
    void inject(OrderHistoryFragment orderHistoryFragment);
    void inject(com.WattanArt.ui.CanvasPrint.CanvasPrintActivity canvasPrintActivity);
    void inject(ShippingActivity shippingActivity);
    void inject(OrderDetailsActivity orderDetailsActivity);
    void inject(AboutActivity aboutActivity);
    void inject(GetFreeCreditActivity getFreeCreditActivity);
    void inject(ContactUsActivity contactUsActivity);
    void inject(InfoFragment infoFragment);
    void inject(ContactInnerFragment contactInnerFragment);
    void inject(SettingFragment settingFragment);

    void inject(FaqActivity faqActivity);
    void inject(TermsActivity termsActivity);

    void inject(ComponentActivity componentActivity);
}
