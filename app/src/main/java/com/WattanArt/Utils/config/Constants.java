package com.WattanArt.Utils.config;


public class Constants {


    // Retrofit Base url
//    public static final String BASE_URL = "http://23.236.154.106:8087/"; // for develop
//    public static final String BASE_URL = "http://23.236.154.106:8063/"; // for test
//    public static final String BASE_URL = "http://23.236.154.106:8083/"; // for Client

//    public static final String BASE_URL = "http://138.128.65.98/"; // for Client

//    public static final String BASE_URL = "http://23.236.154.106:8063/"; // for Test
    public static final String BASE_URL = "https://www.watanarts.com/"; // for Client

    public static final String HOST = "api.linkedin.com";


    public static final String mTopCardUrl = "https://" + HOST + "/v1/people/~:" +
            "(positions,location,email-address,summary,formatted-name,phone-numbers," +
            "public-profile-url,picture-url,picture-urls::(original))";

    public static final String INSTA_BASE_URL = "https://api.instagram.com/";
    public static final String INSTA_CLIENT_ID = "1ffeedd4c6424b058971fe32980fd73f";

    public static final String AR_LANG = "1";
    public static final String EN_LANG = "2";
    public static final String TAG_ARABIC_String = "ar";
    public static final String TAG_ENGLISH_String = "en";
    public static final String LinkTOShare = "http://watanarts.com/";


    public static final String MOBLIE_TYPE = "1";
    public static final String TAbLET_TYPE = "2";

    public static final int FB_LOGIN = 1;
    public static final int TWITTER_LOGIN = 2;



    //Number of items per page
    public static final int ITEMS_PER_PAGE = 10;
    public static final int ITEMS_PER_PAGE_gridview = 50;

    //Image compression
    public static final int COMPRESSION_RATIO = 70;

    //Image compression
    public static final int NO_OF_IMGS_PER_POST = 10;

    //Image max width and height
    public static final int IMG_MAX_WIDTH = 1800;
    public static final int IMG_MAX_HEIGHT = 3200;

    //Avatar max width and height
    public static final int AVATAR_MAX_WIDTH = 512;
    public static final int AVATAR_MAX_HEIGHT = 512;

    //Camera Intent Constant
    public static final int CAMERA_CODE = 100;
    public static final int FILE_CODE = 200;
    public static final int NOTIFICATION_ID = 1;

    //faceBoookStrings
    public static final String FACEBOOK_ID = "id";
    public static final String FACEBOOK_FIRST_NAME = "first_name";
    public static final String FACEBOOK_LAST_NAME = "last_name";
    public static final String FACEBOOK_EMAIL = "email";
    public static final String FACEBOOK_LOGIN = "faceLogin";

    public static final String YOUTUBE_API_KEY = "AIzaSyCpn9iKiIUJqWD2cNK2f4pMhobHNvBhTLc";

    public static final String UPLOAD = "UploadedImages/";

    public static final String ORDERID = "OrderId";
    public static final String STATEVALUE = "stateValue";
    public static final int MAIL_EXIST = -2;
    public static final int LOGIN_OR_REGISTER_FAIL = -1;
    public static final int MISSING_DATA = -3;
    public static final int CATCH = -4;

    public static final int NORMAL_REGISTER = 1;
    public static final int FACEBOOK_REGISTER = 2;
    public static final int FACEBOOK_REGISTER_WithPassword = 3;
    public static final String ORDERITEMPRICE = "OrderItemPrice";
    public static final String ORDERChARGE = "Ordercharge";

    public static final String DEFAULT_PASSWORD = "123456";


}
