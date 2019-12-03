//package com.WattanArt.Utils.social_network;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.twitter.sdk.android.core.TwitterException;
//import com.twitter.sdk.android.core.TwitterSession;
//import com.twitter.sdk.android.core.identity.TwitterLoginButton;
//
///**
// * Created by Android Team on 1/22/2018.
// */
//
//public class TwitterIntegerationTool {
//
//    Activity context;
//    TwitterLoginButton twitterLoginButton;
//
//    public void TwitterIntegerationTool(Activity context , TwitterLoginButton twitterLoginButton){
//        this.context = context;
//        this.twitterLoginButton = twitterLoginButton;
//    }
//
//
//    public void loginUsingTwitter(TwitterLoginButton twitterLoginButton , final TwitterResponse responce){
//        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                // Do something with result, which provides a TwitterSession for making API calls
//                TwitterSession session = result.data;
//
//                responce.returnResponse(session);
//                //Getting the username from session
//                final String userName = session.getUserName();
//
//                Log.e("User" , "=-->"+userName);
//                Log.e("User" , "=-->"+session.getUserId());
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                // Do something on failure
//                exception.printStackTrace();
////                Log.e("TwitterExc" , ""+);
//            }
//        });
//    }
//    public interface TwitterResponse {
//        void returnResponse(TwitterSession session);
//    }
//
//}
