package sapir_liran.melzarito.UI;


import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class MelzaritoFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification()!=null){
            sendNotification(remoteMessage.getNotification().getBody());

        }
    }

    private void sendNotification(String body) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("הזמנה מוכנה")
                .setContentText(body);


        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
