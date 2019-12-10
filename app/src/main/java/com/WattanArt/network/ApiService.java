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
import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Request.SendFeedBackRequestModel;
import com.WattanArt.model.Request.UpdateProfileRequestModel;
import com.WattanArt.model.Response.AboutModel;
import com.WattanArt.model.Response.AboutUsResponseModel;
import com.WattanArt.model.Response.AlbumModel;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.model.Response.CanvasPrintResponseModel;
import com.WattanArt.model.Response.GeneralResponse;
import com.WattanArt.model.Response.PasswordResponseModel;
import com.WattanArt.model.Response.ContactUsModel;
import com.WattanArt.model.Response.ContactUsServicesModel;
import com.WattanArt.model.Response.EventDetailsModel;
import com.WattanArt.model.Response.EventModel;
import com.WattanArt.model.Response.FAQresponseModel;
import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.model.Response.ManegerWords;
import com.WattanArt.model.Response.NewsDetailsModel;
import com.WattanArt.model.Response.NewsModel;
import com.WattanArt.model.Response.NewsTicker;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Response.OrderDetailsResponseModel;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.Response;
import com.WattanArt.model.Response.ResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.Response.SendFeedBackResponsetModel;
import com.WattanArt.model.Response.SettingResponseModel;
import com.WattanArt.model.Response.SliderModel;
import com.WattanArt.model.Response.TermsResposeModel;
import com.WattanArt.model.Response.requestModel;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.ShippingForMobile.MobileOrderResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 *  If you like to go in details about the retrofit , please visit this link
 *  https://github.com/codepath/android_guides/wiki/Consuming-APIs-with-Retrofit
 */

public interface ApiService {

    /**
     * This interface contains all the call for the service
     * STEPS
     * 1- create the Model
     * 2- add the call with your url
     * 3- add RXAndroid event Handler
     */

    @POST("api/Content/GetALLSliders")
    Observable<SliderModel> getAllSlides(@Body LanguageRequestModel languageRequest);


    @POST("api/Content/GetAboutUsData")
    Observable<AboutModel> getAboutUs(@Body LanguageRequestModel languageRequest);



    @POST("api/Content/GetManagerWordData")
    Observable<ManegerWords> getManerWords(@Body LanguageRequestModel languageRequest);

    @POST("api/AlbumGalleryAPI/GetAllAlbums")
    Observable<AlbumModel> getAllAlbums(@Body LanguageRequestModel languageRequest);


    @POST("api/AlbumGalleryAPI/GetAllGalleryByAlbumID")
    Observable<AlbumModel> getAllGalleryById(@Body AllAlbumsByCatIdRequestModel allAlbumsByCatIdModel);


    @POST("api/AlbumGalleryAPI/GetAllGalleryImagesByAlbumID")
    Observable<AlbumModel> getAllGalleryImagesByAlbumID(@Body AllGalleryImagesByAlbumIDModel body);


    @POST("api/AlbumGalleryAPI/GetAllGalleryVideosByAlbumID")
    Observable<AlbumModel> getAllGalleryVideosByAlbumID(@Body AllGalleryImagesByAlbumIDModel body);


    @POST("api/EventsAPI/GetAllEvents")
    Observable<EventModel> getAllEvents(@Body LanguageRequestModel languageRequest);


    @POST("api/EventsAPI/GetEventDetails")
    Observable<EventDetailsModel> getEventDetails(@Body EventDetailsRequestModel body);


    @POST("api/EventsAPI/GetEventDetails")
    Observable<EventDetailsModel> getEventDetailsNewRequest(@Body requestModel model);


    @POST("api/NewsAPI/GetAllNews")
    Observable<NewsModel> getNews(@Body LanguageRequestModel languageRequest);


    @POST("api/NewsAPI/NewsDetails")
    Observable<NewsDetailsModel> getNewsDetails(@Body NewsDetailsRequestModel body);


    @POST("api/NewsAPI/GetNewsTicker")
    Observable<NewsTicker> getNewsTicker(@Body LanguageRequestModel languageRequest);


    @POST("api/Users/Register_Login")
    Observable<ResponseModel> login_register(@Body LoginRequestModel loginRequest);

    //watn arts
    @POST("api/Users/Login")
    Observable<LoginResponseModel> login(@Body LoginRequestModel loginRequest);

    @POST("api/Users/Register")
    Observable<RegisterResponseModel> register(@Body RegisterRequestModel registerRequest);

    @FormUrlEncoded
    @POST("api/SettingApi/SellectLists")
    Observable<SelectCountryCitiyListsResponseModel> getCountries_citiesList(@Field("Language") int Language);


    @FormUrlEncoded
    @POST("api/SettingApi/GetIntro")
    Observable<HomeIntroResponseModel> getHomeIntro(@Field("Language") int Language);


    @FormUrlEncoded
    @POST("api/SettingApi/GetSubs")
    Observable<CategoryMobileRsponseModel> getSubCategory(@Field("Language") int Language
            , @Query("CatID") Integer CatID);

    @FormUrlEncoded
    @POST("/api/Users/UserData")
    Observable<RegisterResponseModel> getProfileData(@Field("UserIdValue") String UserIdValue);

    @POST("/api/Users/UpdateProfile")
    Observable<RegisterResponseModel> updateProfile(@Body UpdateProfileRequestModel updateProfileRequestModel);

    @POST("/api/OrderAPI/GetALLOrderlist")
    Observable<AllOrdersHistoryResponseModel> getAllOrderHistory(@Body AllOrdersHistoryRequestModel allOrdersHistoryRequestModel);

    @FormUrlEncoded
    @POST("/api/SettingApi/GetGetIntroList")
    Observable<CanvasPrintResponseModel> getConvasPrintData(@Field("Language") int Language);

    @FormUrlEncoded
    @POST("/api/OrderAPI/GetOrderById")
    Observable<OrderDetailsResponseModel> getOrderDetailsData(@Field("Language") int Language ,@Field("ID") String ID);

    @FormUrlEncoded
    @POST("/api/Content/GetAboutUsData")
    Observable<AboutUsResponseModel> getAboutUsData(@Field("Language") int Language);

    @FormUrlEncoded
    @POST("/api/SettingApi/GetContactUsData")
    Observable<ContactUsModel> getContactUs(@Field ("Language")  int Language);

    @POST("/api/UserRequests/ContactUs")
    Observable<SendFeedBackResponsetModel> sendFeedback(@Body SendFeedBackRequestModel sendFeedBackRequestModel);

    @POST("/api/SettingApi/GetALLFQA")
    Observable<FAQresponseModel> getFaQ(@Body FAQrequestModel faQrequestModel);

    @POST("/api/Users/changePassWord")
    Observable<PasswordResponseModel> changePassword(@Body ChangePasswordRequestModel changePasswordRequestModel);

    @FormUrlEncoded
    @POST("/api/Users/ForgetPassword")
    Observable<PasswordResponseModel> forgetPassword(@Field("Email") String Email);

    @POST("/api/Users/RegisterLoginSocial")
    Observable<LoginResponseModel> registerOrLoginSocial(@Body LoginRequestModel loginRequest);


    ///end
    @POST("api/AlbumGalleryAPI/GetAllGallery")
    Observable<AlbumModel> getAllGallery(@Body LanguageRequestModel languageRequest);


    @POST("api/AlbumGalleryAPI/GetAllGalleryVideos")
    Observable<AlbumModel> getAllGalleryVideos(@Body LanguageRequestModel languageRequest);


    @POST("api/AlbumGalleryAPI/GetAllGalleryImages")
    Observable<AlbumModel> getAllGalleryImages(@Body LanguageRequestModel languageRequest);


    @POST("api/UserRequests/GetContactUsServices")
    Observable<ContactUsServicesModel> getContactUsServices(@Body LanguageRequestModel languageRequest);

    @POST("api/Users/Logout")
    Observable<GeneralResponse> Logout(@Body JsonObject userID);


    @POST("api/OrderAPI/MakeOrder")
    Observable<Response> makeOrder(@Body ShippingRequest shippingRequest);


    @POST("api/OrderAPI/CheckCouponCode")
    Observable<Response> checkCouponCode(@Body CouponCodeModel couponCodeModel);


    @POST("api/SettingApi/SellectLists")
    Observable<SettingResponseModel> getSettingList(@Body LanguageRequestModel languageRequest);


    @Multipart
    @POST("api/FileUploader/UploadFile/")
    Observable<ImageUploadResponseModel> uploadFile(@Part MultipartBody.Part file , @Part("file") RequestBody name);


    @Multipart
    @POST("api/FileUploader/UploadFile/")
    Call<ImageUploadResponseModel> uploadFileNew(@Part MultipartBody.Part file , @Part("file") RequestBody name);

    @POST("api/SettingApi/TermsAndConditions")
    Observable<TermsResposeModel> getTerms(@Body LanguageRequestModel languageRequest);

    @POST("api/OrderAPI/MakeNewOrder")
    Observable<MobileOrderResponse> getSubmitOrderForMobile(@Body MobileOrderRequest mobileOrderRequest);
}
