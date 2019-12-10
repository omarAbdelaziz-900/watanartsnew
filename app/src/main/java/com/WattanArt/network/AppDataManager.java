package com.WattanArt.network;

import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Request.AllAlbumsByCatIdRequestModel;
import com.WattanArt.model.Request.AllGalleryImagesByAlbumIDModel;
import com.WattanArt.model.Request.AllOrdersHistoryRequestModel;
import com.WattanArt.model.Request.ChangePasswordRequestModel;
import com.WattanArt.model.Request.EventDetailsRequestModel;
import com.WattanArt.model.Request.FAQrequestModel;
import com.WattanArt.model.Request.LanguageRequestModel;
import com.WattanArt.model.Request.NewsDetailsRequestModel;
import com.WattanArt.model.Request.RegisterRequestModel;
import com.WattanArt.model.Request.SendFeedBackRequestModel;
import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Request.UpdateProfileRequestModel;
import com.WattanArt.model.Response.AboutModel;
import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.model.Response.AlbumModel;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.model.Response.CanvasPrintResponseModel;
import com.WattanArt.model.Response.ContactUsModel;
import com.WattanArt.model.Response.ContactUsServicesModel;
import com.WattanArt.model.Response.EventDetailsModel;
import com.WattanArt.model.Response.EventModel;
import com.WattanArt.model.Response.FAQresponseModel;
import com.WattanArt.model.Response.GeneralResponse;
import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.model.Response.ManegerWords;
import com.WattanArt.model.Response.NewsDetailsModel;
import com.WattanArt.model.Response.NewsModel;
import com.WattanArt.model.Response.NewsTicker;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Response.OrderDetailsResponseModel;
import com.WattanArt.model.Response.PasswordResponseModel;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.Response;
import com.WattanArt.model.Response.ResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.Response.SendFeedBackResponsetModel;
import com.WattanArt.model.Response.SettingResponseModel;
import com.WattanArt.model.Response.SliderModel;
import com.WattanArt.model.Response.TermsResposeModel;
import com.WattanArt.model.Response.requestModel;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.ShippingForMobile.MobileOrderResponse;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Singleton
public class AppDataManager implements ApiService {

    private static final String TAG = "AppDataManager";

//    private final Context mContext;

    public static ApiService getApi() {
        //Retrofit interceptor.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).addInterceptor(interceptor).build();

//        client.certificatePinner()
        // Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

    public static ApiService getApiNoInterpretor() {
        //Retrofit interceptor.
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES)
//                .readTimeout(2, TimeUnit.MINUTES)/*.addInterceptor(interceptor)*/.build();
        OkHttpClient client = new UnsafeOkHttpClient2().getUnsafeOkHttpClient();

        // Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

//    public static ApiService getApiTest() {
//        //Retrofit interceptor.
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS).addInterceptor(interceptor).build();
////        OkHttpClient client = new UnsafeOkHttpClient2().getUnsafeOkHttpClient();
//
//        // Retrofit.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .client(client)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit.create(ApiService.class);
//    }

    //    @Inject //@ApplicationContext Context context

    public AppDataManager() {
        //    @Inject //@ApplicationContext Context context

    }

    @Override
    public Observable<SliderModel> getAllSlides(LanguageRequestModel languageRequest) {
        return getApi().getAllSlides(languageRequest);
    }

    @Override
    public Observable<AboutModel> getAboutUs(LanguageRequestModel languageRequest) {
        return getApi().getAboutUs(languageRequest);
    }


    @Override
    public Observable<ManegerWords> getManerWords(LanguageRequestModel languageRequest) {
        return getApi().getManerWords(languageRequest);
    }

    @Override
    public Observable<AlbumModel> getAllAlbums(LanguageRequestModel languageRequest) {
        return getApi().getAllAlbums(languageRequest);
    }

    @Override
    public Observable<AlbumModel> getAllGalleryById(AllAlbumsByCatIdRequestModel body) {
        return getApi().getAllGalleryById(body);
    }

    @Override
    public Observable<AlbumModel> getAllGalleryImagesByAlbumID(AllGalleryImagesByAlbumIDModel
                                                                       body) {
        return getApi().getAllGalleryImagesByAlbumID(body);
    }

    @Override
    public Observable<AlbumModel> getAllGalleryVideosByAlbumID(AllGalleryImagesByAlbumIDModel
                                                                       body) {
        return getApi().getAllGalleryVideosByAlbumID(body);
    }

    @Override
    public Observable<EventModel> getAllEvents(LanguageRequestModel languageRequest) {
        return getApi().getAllEvents(languageRequest);
    }

    @Override
    public Observable<EventDetailsModel> getEventDetails(EventDetailsRequestModel body) {
        return getApi().getEventDetails(body);
    }

    @Override
    public Observable<EventDetailsModel> getEventDetailsNewRequest(requestModel model) {
        return getApi().getEventDetailsNewRequest(model);
    }


    @Override
    public Observable<NewsModel> getNews(LanguageRequestModel languageRequest) {
        return getApi().getNews(languageRequest);
    }

    @Override
    public Observable<NewsDetailsModel> getNewsDetails(NewsDetailsRequestModel body) {
        return getApi().getNewsDetails(body);
    }

    @Override
    public Observable<NewsTicker> getNewsTicker(LanguageRequestModel languageRequest) {
        return getApi().getNewsTicker(languageRequest);
    }

    @Override
    public Observable<ResponseModel> login_register(LoginRequestModel loginRequest) {
        return getApi().login_register(loginRequest);
    }

    @Override
    public Observable<LoginResponseModel> login(LoginRequestModel loginRequest) {
        return getApi().login(loginRequest);
    }

    @Override
    public Observable<RegisterResponseModel> register(RegisterRequestModel registerRequest) {
        return getApi().register(registerRequest);
    }

    @Override
    public Observable<SelectCountryCitiyListsResponseModel> getCountries_citiesList(int
                                                                                            Language) {
        return getApi().getCountries_citiesList(Language);
    }

    @Override
    public Observable<HomeIntroResponseModel> getHomeIntro(int Language) {
        return getApi().getHomeIntro(Language);
    }

    @Override
    public Observable<CategoryMobileRsponseModel> getSubCategory(int Language, Integer CatID) {
        return getApi().getSubCategory(Language,CatID);
    }

    @Override
    public Observable<RegisterResponseModel> getProfileData(String UserIdValue) {
        return getApi().getProfileData(UserIdValue);
    }

    @Override
    public Observable<RegisterResponseModel> updateProfile(UpdateProfileRequestModel updateProfileRequestModel) {
        return getApi().updateProfile(updateProfileRequestModel);
    }

    @Override
    public Observable<AllOrdersHistoryResponseModel> getAllOrderHistory
            (AllOrdersHistoryRequestModel allOrdersHistoryRequestModel) {
        return getApi().getAllOrderHistory(allOrdersHistoryRequestModel);
    }

    @Override
    public Observable<CanvasPrintResponseModel> getConvasPrintData(int Language) {
        return getApi().getConvasPrintData(Language);
    }

    @Override
    public Observable<OrderDetailsResponseModel> getOrderDetailsData(int Language, String ID) {
        return getApi().getOrderDetailsData(Language, ID);
    }

    @Override
    public Observable<AboutUsResponseModel> getAboutUsData(int Language) {
        return getApi().getAboutUsData(Language);
    }

    @Override
    public Observable<ContactUsModel> getContactUs(int Language) {
        return getApi().getContactUs(Language);
    }

    @Override
    public Observable<SendFeedBackResponsetModel> sendFeedback(SendFeedBackRequestModel
                                                                       sendFeedBackRequestModel) {
        return getApi().sendFeedback(sendFeedBackRequestModel);
    }

    @Override
    public Observable<FAQresponseModel> getFaQ(FAQrequestModel faQrequestModel) {
        return getApi().getFaQ(faQrequestModel);
    }

    @Override
    public Observable<PasswordResponseModel> changePassword(ChangePasswordRequestModel changePasswordRequestModel) {
        return getApi().changePassword(changePasswordRequestModel);
    }

    @Override
    public Observable<PasswordResponseModel> forgetPassword(String Email) {
        return getApi().forgetPassword(Email);
    }

    @Override
    public Observable<LoginResponseModel> registerOrLoginSocial(LoginRequestModel loginRequest) {
        return getApi().registerOrLoginSocial(loginRequest);
    }

//    @Override
//    public Observable<HomeIntroResponseModel> getHomeIntro() {
//        return getApi().getHomeIntro("1");
//    }


    @Override
    public Observable<AlbumModel> getAllGallery(LanguageRequestModel languageRequest) {
        return getApi().getAllGallery(languageRequest);
    }

    @Override
    public Observable<AlbumModel> getAllGalleryVideos(LanguageRequestModel languageRequest) {
        return getApi().getAllGalleryVideos(languageRequest);
    }

    @Override
    public Observable<AlbumModel> getAllGalleryImages(LanguageRequestModel languageRequest) {
        return getApi().getAllGalleryImages(languageRequest);
    }

    @Override
    public Observable<ContactUsServicesModel> getContactUsServices(LanguageRequestModel languageRequest) {
        return getApi().getContactUsServices(languageRequest);
    }

    @Override
    public Observable<GeneralResponse> Logout(JsonObject userID) {
        return getApi().Logout(userID);
    }

    @Override
    public Observable<Response> makeOrder(ShippingRequest shippingRequest) {
        return getApi().makeOrder(shippingRequest);
    }

    @Override
    public Observable<Response> checkCouponCode(CouponCodeModel couponCodeModel) {
        return getApi().checkCouponCode(couponCodeModel);
    }

    @Override
    public Observable<SettingResponseModel> getSettingList(LanguageRequestModel languageRequest) {
        return getApi().getSettingList(languageRequest);
    }

    //        @Override
//        public Observable<ContactUsServicesModel> uploadFile (MultipartBody.Part file, RequestBody
//        name){
//            return getApi().uploadFile(file, name);
//        }
    public Observable<ImageUploadResponseModel> uploadFile(MultipartBody.Part file, RequestBody
            name) {
        return getApiNoInterpretor().uploadFile(file, name);
    }

    @Override
    public Call<ImageUploadResponseModel> uploadFileNew(MultipartBody.Part file, RequestBody name) {
        return null;
    }
    @Override
    public Observable<TermsResposeModel> getTerms(LanguageRequestModel languageRequest) {
        return getApi().getTerms(languageRequest);
    }

    @Override
    public Observable<MobileOrderResponse> getSubmitOrderForMobile(MobileOrderRequest mobileOrderRequest) {
        return getApi().getSubmitOrderForMobile(mobileOrderRequest);
    }
}
