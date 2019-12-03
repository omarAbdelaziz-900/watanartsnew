package com.WattanArt.Utils.social_network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * `
 * Created by android team  on 7/24/2016.
 */

/**
 * Integrate facebook Login
 * <p>
 * 1- compile 'com.facebook.android:facebook-android-sdk:[4,5)'  (Check latest version)
 * <p>
 * 2- add meta-data to the manifest
 * <meta-data
 * android:name="com.facebook.sdk.ApplicationId"
 * android:value="@string/app_fb_id" />
 * (FIND FacebookIntegrationTool in utils.social_network)
 * 3- in your activity implements FacebookIntegrationTool.FacebookResponse
 * 4- in your login activity ->
 * facebookIntegration.facebookIntegration(Activity, this);
 * this refer to FacebookIntegrationTool.FacebookResponse
 * 5- inside your activity on activity result
 *
 * @Override public void onActivityResult(int requestCode, int resultCode, Intent data)
 * facebookIntegration.onActivityResult(requestCode, resultCode, data);

 * 6- inside your activity after implementing FacebookIntegrationTool.FacebookResponse you will
 * override returnResponse(JSONObject resonceJsonObject) method
 * if (resonceJsonObject != null) {
 * faceBookDataLoginedModel.id = (resonceJsonObject.getString("id"));
 * faceBookDataLoginedModel.email = (resonceJsonObject.getString("email"));
 * faceBookDataLoginedModel.name = (resonceJsonObject.getString("first_name"));
 * <p>
 * PRESENTER LOGIN
 * mPresenter.getLoginData(faceBookDataLoginedModel.id, Constants.FB_LOGIN,
 * faceBookDataLoginedModel.name, faceBookDataLoginedModel.email, UtilitiesManager.getFirebaseToken());
 * //your fcm token ->UtilitiesManager.getFirebaseToken()
 */
public class FacebookIntegrationTool {
    private static final String TAG_CANCEL = "oncancel";
    private static final String TAG_ERROR = "onerror";
    private CallbackManager callbackmanager;

    // method to integrate wit  facebook to get data
    public void facebookIntegration(Activity activity, final FacebookResponse facebookResponce) {
        if (!FacebookSdk.isInitialized())
            FacebookSdk.sdkInitialize(activity.getApplicationContext());

        callbackmanager = CallbackManager.Factory.create();
//        LoginManager.getInstance().logOut();

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList( "email","user_photos", "public_profile", "user_birthday"));
        LoginManager.getInstance().registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("Success");
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject responcejsonObject, GraphResponse response) {
                        System.out.print("response" + response.toString());

                        responcejsonObject = response.getJSONObject();
                        facebookResponce.returnResponse(responcejsonObject);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "location,gender,link,email,picture,birthday,first_name,id,last_name");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d(TAG_CANCEL, "On cancel");

            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
                Log.d(TAG_ERROR, error.toString());
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    public interface FacebookResponse {
        void returnResponse(JSONObject resonceJsonObject);
    }
}
