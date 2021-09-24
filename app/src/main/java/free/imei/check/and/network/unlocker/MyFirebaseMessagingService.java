package free.imei.check.and.network.unlocker;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import free.imei.check.and.network.unlocker.Activities.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static String NOTIFICATION_CHANNEL_ID = "com.itw.firebasepushnotificationdemo";
    public static final int NOTIFICATION_ID = 0;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationManager mNotificationManager;

    @SuppressLint("LongLogTag")


    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {


        Log.d("msg", "onMessageReceived: " + remoteMessage.toString());

        if (remoteMessage.getData().size()>0){
            Intent intent = new Intent(this, MainActivity.class);

            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.putExtra("video_url", remoteMessage.getData().get("link_url"));
            intent.putExtra("video_title", remoteMessage.getData().get("message"));
            intent.putExtra("video_tag", remoteMessage.getData().get("title"));
            intent.putExtra("pdf_link", remoteMessage.getData().get("pdf_lin"));
            intent.putExtra("video_likes", remoteMessage.getData().get("video_likes"));
            intent.putExtra("video_dislikes", remoteMessage.getData().get("video_dislikes"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message")).setAutoCancel(true).setContentIntent(pendingIntent);
            ;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());
/*
            sendNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("title"));
*/
        }

        /**/
    }


  /*
  * notificationIntent.setAction(Intent.ACTION_MAIN);
    notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
    notificationIntent.putExtra("screen", "debugScreen");
    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
 */




  /*
  *   if (remoteMessage.getData().size()>0){
            Intent intent = new Intent(this, VideoListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("message")).setAutoCancel(true).setContentIntent(pendingIntent);
            ;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());
/*
            sendNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("title"));
*/

}