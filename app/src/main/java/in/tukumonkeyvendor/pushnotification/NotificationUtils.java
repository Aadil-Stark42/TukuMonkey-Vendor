package in.tukumonkeyvendor.pushnotification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import in.tukumonkeyvendor.R;
import in.tukumonkeyvendor.dashboard.DashboardActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    MediaPlayer mediaPlayer;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }


    public void showNotificationMessage(final String title, final String message, final String imageUrl, Intent intent, boolean isSound) {


        Log.d(TAG, "showNotificationMessage: class ");
        // Check for empty push messag
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.drawable.notification_icon;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );


        Log.e(TAG, "showNotificationMessage: notification");

        Notification.Builder notification = new Notification.Builder(mContext);
        //   showSmallNotification(notification, icon, title, message,imageUrl , resultPendingIntent);

     /*   final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");*/

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, notification, icon, title, message, imageUrl, resultPendingIntent);
                } else {
                    showSmallNotification(notification, icon, title, message, imageUrl, resultPendingIntent);
                }
            }
        } else {
            showSmallNotification(notification, icon, title, message, imageUrl, resultPendingIntent);
            if (isSound) {
                //playNotificationSound();
                play_ringtone();
            }
        }
    }


    private void showSmallNotification(Notification.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent) {

        Log.e(TAG, "showSmallNotification: small notification ");
        Log.d(TAG, "showSmallNotification: titile " + title);
        Log.d(TAG, "showSmallNotification: message " + message);
        Log.d(TAG, "showSmallNotification: resultPendingIntent " + resultPendingIntent);

        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";

        play_ringtone();
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(5000);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent);

        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
   /*     Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    private void showBigNotification(Bitmap bitmap, Notification.Builder mBuilder, int icon, String title, String message, String imageurl, PendingIntent resultPendingIntent) {


        Log.d(TAG, "showBigNotification: big images");

        NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
        bpStyle.bigPicture(bitmap);

        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";

        play_ringtone();
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(5000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId);

        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(icon)
                .setWhen(System.currentTimeMillis())
                .setStyle(bpStyle);

     /*   Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);*/

        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());

    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            if(!strURL.equals("sample.png")){
                URL url = new URL(strURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }else {
                return  null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
 /*       try {
//            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + mContext.getPackageName() + "/raw/notification");
//            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
//            r.play();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(mContext.getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public void play_ringtone() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = MediaPlayer.create(mContext.getApplicationContext(), R.raw.noti_beep);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

            MyFirebaseMessagingService.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("handler.postDelayed", "handler.postDelayed ");
                    mediaPlayer.setLooping(false);
                    mediaPlayer = null;
                }
            }, 7000);
        } catch (Exception e) {
            Log.d(TAG, "play_ringtone: errororr " + e.getMessage());
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
