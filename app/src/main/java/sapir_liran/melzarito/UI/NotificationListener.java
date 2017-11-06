package sapir_liran.melzarito.UI;


import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sapir_liran.melzarito.R;

import static android.R.id.list;

public class NotificationListener {

    FirebaseDatabase database;
    DatabaseReference db;
    DatabaseReference dbstock;
    DatabaseReference dbspecial;


    int counter=0;
    private Context context;
    public NotificationListener(final Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        db = database.getReference("Notifications");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<OrderNotification>> typeIndicator = new GenericTypeIndicator<ArrayList<OrderNotification>>() {
                };
                GenericTypeIndicator<ArrayList<StockNotification>> stocktypeIndicator = new GenericTypeIndicator<ArrayList<StockNotification>>() {
                };
                try {

                    ArrayList<OrderNotification> notifications = dataSnapshot.getValue(typeIndicator);
                    if (notifications != null) {
                        for (OrderNotification notification : notifications) {
                            if (notification != null) {
                                if (!notification.isInvoked()) {

                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setContentTitle("הזמנה מוכנה")
                                            .setContentText("הזמנה מספר:" + notification.getOrderId() + " שולחן מספר: " + notification.getTableNumber())
                                            .setSmallIcon(R.drawable.servicebell)
                                            .setSound( Uri.parse(("android.resource://"+context.getPackageName()+"/raw/bell")));



                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                   counter++;
                                    notificationManager.notify(counter, builder.build());
                                    db.child(notification.getOrderId() + "").child("invoked").setValue(true);

                                }
                            }
                        }
                    }
                }
                catch (DatabaseException ex){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbstock = database.getReference("StockNotifications");

        dbstock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<StockNotification>> typeIndicator = new GenericTypeIndicator<ArrayList<StockNotification>>() {
                };
                try {

                    ArrayList<StockNotification> notifications = dataSnapshot.getValue(typeIndicator);
                    if (notifications != null) {
                        for (StockNotification notification : notifications) {
                            if (notification != null) {
                                if (!notification.isInvoked()) {
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setContentTitle("פריט אזל בתפריט")
                                            .setContentText(notification.getItemName())
                                            .setSmallIcon(R.drawable.outofstock)
                                            .setSound( Uri.parse(("android.resource://"+context.getPackageName()+"/raw/out")));


                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    counter++;
                                    notificationManager.notify(counter, builder.build());
                                    dbstock.child(notification.getItemId() + "").child("invoked").setValue(true);

                                }
                            }
                        }
                    }
                }
                catch (DatabaseException ex){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbspecial = database.getReference("SpecialNotifications");

        dbspecial.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<SpecialNotification>> typeIndicator = new GenericTypeIndicator<ArrayList<SpecialNotification>>() {
                };
                try {

                    List<SpecialNotification> notifications = new ArrayList<SpecialNotification>();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        notifications.add(child.getValue(SpecialNotification.class));
                    }
                    if (notifications != null) {
                        for (SpecialNotification notification : notifications) {
                            if (notification != null) {
                                if (!notification.isInvoked()) {
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setContentTitle("ספיישל חדש נוסף לתפריט")
                                            .setContentText(notification.getName())
                                            .setSmallIcon(R.drawable.special)
                                            .setSound( Uri.parse(("android.resource://"+context.getPackageName()+"/raw/special")));



                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    counter++;
                                    notificationManager.notify(counter, builder.build());
                                    dbspecial.child(notification.getId() + "").child("invoked").setValue(true);

                                }
                            }
                        }
                    }
                }
                catch (DatabaseException ex){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
