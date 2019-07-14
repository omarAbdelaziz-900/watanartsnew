package com.WattanArt;

/**
 * Created by Android Team on 2/5/2018.
 */

public class  Documentation {


    /**
     * 1- Hierarchy
     *      *-Adapters-> all the adapter that we will use thought the application
     *          -Adapters.Holders-> all the Custom GENERAL holders are used thought multiple
     *                          adapters and we don't want to repeat their Code in the adapters
     *
     *      *-Dagger-> Dependency Injection
     *          -Dagger.Component->@Component interface. This interface is used by Dagger2 to generate
     *                           code which uses the modules to fill the requested dependencies.
     *          -Dagger.Modules->@Module Classes tha tprovide the dependencies annotated with @Provides
     *
     *      *-Logger-> Logging process . used to log in the case of the debug state ONLY not the release
     *
     *      *-Model-> Models of the request and response from the web service
     *
     *      *-Network-> Retrofit Network Calls
     *                 -ApiService ->This interface contains all methods for the call of the service
     *                 -AppDataManager ->This Class responsible for every call for the service
     *
     *      *-Services-> Services
     *
     *      *-UI->  contains the fragments and activities in our application that
     *              every view is seperated in its package with
     *              the view , presenter and interactor
     */

    /**
     * -------------------------------------------------------------------------------------------------
     */

    /**
     * 2-BASE (UI->Base package)
     *  1- MvpView ->Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
     *              pattern must implement. NOTE this interface will be extended by a more specific interface
     *              that then usually will be implemented by an Activity or Fragment.
     *
     *  2- MvpPresenter ->Every presenter in the app must implement this interface
     *                   indicating the MvpView type that wants to be attached with
     *  3- BaseViewHolder -> View holder for the recycler view
     *
     *  4- BasePresenter ->  Base class that implements the Presenter interface and provides a base implementation for
     *                      onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
     *                      can be accessed from the children classes by calling getMvpView().
     *
     *  5-BaseAdapter -> Base RecyclerView Adapter Contains used For pagination
     *
     *  6-BaseFragment -> Base Fragment that every fragment in the app MUST extend
     *
     *  7-BaseActivity -> Base Activity that every Activity in the app MUST extend
     *
     */

    /**
     * -------------------------------------------------------------------------------------------------
     */

    /**
     * Create new Fragment
     *  1-Create package inside UI package with the name of the fragment EX:Notification
     *
     *  2-create NotificationView - Interface VIEW that NotificationFraqment will implements
     *      and this interface MUST extends MvpView and create your view specific
     *      methods ex: updateListOfNotification .. etc
     *
     *  3- Create your Interctor  ex: interface NotificationPresenterMvp<V extends NotificationView> extends MvpPresenter<V>
     *                      NOTE that u must specify the type of the view that is hocked up eith this interactor
     *                      (NotificationView in this example)
     *
     *  4-Create your presenter Ex:NotificationPresenter<V extends NotificationView> extends BasePresenter<V>
     *                  implements NotificationPresenterMvp<V> And Make it implement your interactor
     *                  in the constructor of this Class Comes The Dagger injection role to inject app data manager
     *                  to get access to retrofit interface methods and CompositeDisposable to manage retrofit with RXJAVA
     *
     *                 @Inject
     *                   public NotificationPresenter(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
     *                   super(dataManager, compositeDisposable); }
     *
     *   5-in Dagger->Module -> provide the presenter to use it in the fragment that we will create
     *   EX:
     *      @Provides
     *      @PerActivity NotificationPresenterMvp<NotificationMvpView> NotificationPresenter(
     *           NotificationPresenter<NotificationMvpView> presenter) {
     *           return presenter;
     *      }
     *   5-Create your Fragment ex:NotificationFragment extends BaseFragment implements NotificationMvpView
     *
     *   6- in your activityComponent create method inject(YourFragment )
     *   6-ActivityComponent component = getActivityComponent();
     *      if (component != null) {
     *      component.inject(this);
     *      mPresenter.onAttach(this);
     *    }
     *
     *    in this step you inject the fragment mvp view to provide the presenter back
     */

    /**
     * -------------------------------------------------------------------------------------------------
     */
    /**
     * Create new Activity
     *  1-Create package inside UI package with the name of the Activity EX:Notification
     *
     *  2-create NotificationView - Interface VIEW that NotificationActivity will implements
     *      and this interface MUST extends MvpView and create your view specific
     *      methods ex: updateListOfNotification .. etc
     *
     *  3- Create your Interctor  ex: interface NotificationPresenterMvp<V extends NotificationView> extends MvpPresenter<V>
     *                      NOTE that u must specify the type of the view that is hocked up eith this interactor
     *                      (NotificationView in this example)
     *
     *  4-Create your presenter Ex:NotificationPresenter<V extends NotificationView> extends BasePresenter<V>
     *                  implements NotificationPresenterMvp<V> And Make it implement your interactor
     *                  in the constructor of this Class Comes The Dagger injection role to inject app data manager
     *                  to get access to retrofit interface methods and CompositeDisposable to manage retrofit with RXJAVA
     *
     *                 @Inject
     *                   public NotificationPresenter(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
     *                   super(dataManager, compositeDisposable); }
     *
     *   5- in your activityComponent create method inject(YourActivity )
     *   5-in Dagger->Module -> provide the presenter to use it in the Activity that we will create
     *   EX:
     *      @Provides
     *      @PerActivity NotificationPresenterMvp<NotificationMvpView> NotificationPresenter(
     *           NotificationPresenter<NotificationMvpView> presenter) {
     *           return presenter;
     *      }
     *   5-Create your Activity ex:NotificationActivity extends BaseActivity implements NotificationMvpView
     *
     *   6-ActivityComponent component = getActivityComponent();
     *      if (component != null) {
     *      component.inject(this);
     *      mPresenter.onAttach(this);
     *    }
     *
     *    in this step you inject the Activity mvp view to provide the presenter back
     */

    /**
     * -------------------------------------------------------------------------------------------------
     */

    /**
     * Integrate facebook Login
     *
     * 1- compile 'com.facebook.android:facebook-android-sdk:[4,5)'  (Check latest version)
     *
     * 2- add meta-data to the manifest
     *         <meta-data
     *          android:name="com.facebook.sdk.ApplicationId"
     *          android:value="@string/app_fb_id" />
     *
     *
     * (FIND FacebookIntegrationTool in utils.social_network)
     * 3- in your activity implements FacebookIntegrationTool.FacebookResponse
     * 4- in your login activity ->
     *          facebookIntegration.facebookIntegration(Activity, this);
     *          this refer to FacebookIntegrationTool.FacebookResponse
     *
     * 5- inside your activity on activity result
     *     @Override
     *      public void onActivityResult(int requestCode, int resultCode, Intent data)
     *     facebookIntegration.onActivityResult(requestCode, resultCode, data);
     *
     *
     * 6- inside your activity after implementing FacebookIntegrationTool.FacebookResponse you will
     *      override returnResponse(JSONObject resonceJsonObject) method
     *      if (resonceJsonObject != null) {
     *          faceBookDataLoginedModel.id = (resonceJsonObject.getString("id"));
     *          faceBookDataLoginedModel.email = (resonceJsonObject.getString("email"));
     *          faceBookDataLoginedModel.name = (resonceJsonObject.getString("first_name"));
     *
     *             PRESENTER LOGIN
     *             mPresenter.getLoginData(faceBookDataLoginedModel.id, Constants.FB_LOGIN,
     *             faceBookDataLoginedModel.name, faceBookDataLoginedModel.email, UtilitiesManager.getFirebaseToken());
     *                  //your fcm token ->UtilitiesManager.getFirebaseToken()
     *
     */

    /**
     * -------------------------------------------------------------------------------------------------
     */
    /**
     * Integrate Twitter Login
     *
     * 1- compile 'com.twitter.sdk.android:twitter:3.1.1'  (Check latest version)
     **
     *
     * (FIND TwitterIntegerationTool in utils.social_network)
     * 3- in your activity implements TwitterIntegerationTool.TwitterResponse
     * 4- in your login activity ->
     *          twitterIntegerationTool.loginUsingTwitter(loginButton, this);
     *          loginButton refer to TwitterLoginButton object that is used for callback
     *          this refer to TwitterIntegerationTool.TwitterResponse
     *
     * 5- inside your activity on activity result
     *     @Override
     *      public void onActivityResult(int requestCode, int resultCode, Intent data)
     *     loginButton.onActivityResult(requestCode, resultCode, data);
     *
     *
     * 6- inside your activity after implementing TwitterIntegerationTool.TwitterResponse you will
     *      override returnResponse(TwitterSession session) method
     *      TwitterSession is used to get the logged user data
     *
     *      Log.e("UserId","-->"+session.getUserId());
     *      Log.e("UserName","-->"+session.getUserName());
     *
     *             PRESENTER LOGIN
     *             mPresenter.getLoginData(session.getUserId(), Constants.TWITTER_LOGIN,
     *             session.getUserName(), "", UtilitiesManager.getFirebaseToken());
     *                  //your fcm token ->UtilitiesManager.getFirebaseToken()
     *                  // note that email is not needed when login using twitter
     *
     */
    /**
     * -------------------------------------------------------------------------------------------------
     */

    /**
     * Integrate Instagram Login
     *
     * 1- add InstagramHelper Module to your app
     **
     *
     *         String scope = "basic+public_content+follower_list+comments+relationships+likes";
     *         instagramHelper = new InstagramHelper.Builder()
     *              .withClientId(Constants.INSTA_CLIENT_ID)
     *              .withClientId("Your Client Id")
     *              .withRedirectUrl(" Your redirect id ")
     *              .withScope(scope)
     *              .build();
     *              instagramHelper.loginFromActivity(this);
     *
     * 5- inside your activity on activity result
     *     @Override
     *      public void onActivityResult(int requestCode, int resultCode, Intent data)
     *
     *      if (requestCode == InstagramHelperConstants.INSTA_LOGIN && resultCode == RESULT_OK) {
     *          InstagramUser user = instagramHelper.getInstagramUser(this);
     *          Log.e("ID" , ""+user.getData().getId());
     *          Log.e("user name" , ""+user.getData().getUsername());
     *          Log.e("full name" , ""+user.getData().getFullName());
     *          Log.e("bioooo" , ""+user.getData().getBio());
     *      }
     */
    /**
     * -------------------------------------------------------------------------------------------------
     */
    /**
     * Integrate Linkedin Login
     *
     * 1- add Linkedin-sdk Module to your app
     **
     *  LinkedinLoginTool.getInstance().loginUsingLinkedIn(LoginActivity.this);
     *   LinkedinLoginTool.getInstance().setlistenr(new LinkedinLoginTool.LinkedInLoginListener() {
     *
     *  @Override public void success(ApiResponse result) {
     *          if (mLinkedInModel == null)
     *          mLinkedInModel = new Social();
     *          mLinkedInModel = LinkedinLoginTool.mLinkedInModel;
     *          Log.e("email", mLinkedInModel.email);
     *          Log.e("name", mLinkedInModel.name);
     *          Log.e("photo", mLinkedInModel.photo);
     *          Log.e("id", mLinkedInModel.id);
     *          Log.e("phone", mLinkedInModel.phone);
     *
     *      }
     *
     *  @Override public void failure(LIApiError error) {
     *          Log.e("Error", error.getMessage());
     **  }
     *  });
     */


}
