//package com.WattanArt.Services.firebase;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.AudioAttributes;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.TaskStackBuilder;
//import android.util.Log;
//
//import com.WattanArt.MyApplication;
//import com.WattanArt.R;
//import com.WattanArt.Utils.NotificationUtils;
//import com.WattanArt.ui.Home.HomeActivity;
//import com.WattanArt.ui.Splash.SplashActivity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import org.json.JSONObject;
//
//import java.util.Map;
//
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
//
//    private NotificationUtils notificationUtils;
//
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
////        Log.e(TAG, "From: " + remoteMessage.getFrom());
////
//        if (remoteMessage == null)
//            return;
//
//        Log.e("RemoteMessage", "" + remoteMessage.getData());
//        fireNotification(MyApplication.getAppContext() ,
//                remoteMessage.getData().get("message"));
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
//
//            Map<String, String> map = remoteMessage.getData();
//            String jsonOnject = map.get("obj");
//
//            try {
//                JSONObject json = new JSONObject(jsonOnject);
//                handleDataMessage(json);
//            } catch (Exception e) {
//                Log.e(TAG, "Exception11: " + e.getMessage());
//            }
//        } else {
//            Log.e(TAG, "no data");
//        }
//    }
//
//    private void fireNotification(Context context , String message) {
//        // Sets an ID for the notification, so it can be updated.
//        int notifyID = 1;
//        String CHANNEL_ID = "my_channel_01";// The id of the channel.
//        CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//
//// Create a notification and set the notification channel.
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        Intent notificationIntent = new Intent(getApplicationContext(), HomeActivity.class);
//
////**add this line**
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
////**edit this line to put requestID as requestCode**
//        PendingIntent contentIntent = PendingIntent.getActivity(this, notifyID ,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
////        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
//
//
////        Intent intent = new Intent(context, HomeActivity.class);
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
////        stackBuilder.addParentStack(SplashActivity.class);
////        stackBuilder.addNextIntent(intent);
////        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//
//        Notification notification;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            notification = new Notification.Builder(context, CHANNEL_ID)
//                    .setContentTitle("New Message")
//                    .setContentText(message)
//                    .setContentIntent(contentIntent)
//                    .setSmallIcon(R.drawable.logo).build();
//        } else {
//            notification = new Notification.Builder(context)
//                    .setContentTitle("New Message")
//                    .setSound(alarmSound)
//                    .setContentIntent(contentIntent)
//                    .setContentText(message).setSmallIcon(R.drawable.logo).build();
//        }
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//            AudioAttributes attributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
//
//            mChannel.setSound(alarmSound, attributes);
//
//            mNotificationManager.createNotificationChannel(mChannel);
//        }
//
//// Issue the notification.
//        mNotificationManager.notify(notifyID, notification);
//
//    }
//
//    private void handleDataMessage(JSONObject json) {
//        Log.e(TAG, "push json: " + json.toString());
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//
//    /**
//     * Showing notification with text only
//     */
//    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
//        notificationUtils = new NotificationUtils(context);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
//    }
//
//    /**
//     * Showing notification with text and image
//     */
//    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
//        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
//    }
//}