package sapir_liran.melzarito.UI;


import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Logic.OrderNotification;
import Logic.SpecialNotification;
import Logic.StockNotification;
import sapir_liran.melzarito.R;

public class NotificationListener {

    FirebaseDatabase database;
    DatabaseReference db_order_notifications;
    DatabaseReference db_stock_notifications;
    DatabaseReference db_specials_notifications;


    int counter = 0;
    private Context context;

    public NotificationListener(final Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        
        db_order_notifications = database.getReference("Notifications");
        //order notifications listener
        db_order_notifications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(ChooseEmployeeRoleActivity.loggedInUserRole.equals("מלצר")) {
                GenericTypeIndicator<ArrayList<OrderNotification>> typeIndicator = new GenericTypeIndicator<ArrayList<OrderNotification>>() {
                };
                try {
                    ArrayList<OrderNotification> notifications = dataSnapshot.getValue(typeIndicator);
                    if (notifications != null) {
                        for (OrderNotification notification : notifications) {
                            if (notification != null) {
                                //if not invoked already
                                if (!notification.isInvoked()) {
                                    //send notification
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setContentTitle(context.getString(R.string.order_ready_txt_ntf))
                                            .setContentText(context.getString(R.string.order_num) + notification.getOrderId() + context.getString(R.string.table_num) + notification.getTableNumber())
                                            .setSmallIcon(R.drawable.servicebell)
                                            .setSound(Uri.parse(("android.resource://" + context.getPackageName() + "/raw/bell")));

                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    counter++;
                                    notificationManager.notify(counter, builder.build());
                                    //set invoked on db
                                    db_order_notifications.child(notification.getOrderId() + "").child("invoked").setValue(true);

                                }
                            }
                        }
                    }
                } catch (DatabaseException ex) {
                }
//                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        db_stock_notifications = database.getReference("StockNotifications");
        //stock notifications listener
        db_stock_notifications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(ChooseEmployeeRoleActivity.loggedInUserRole.equals("מלצר")) {
                GenericTypeIndicator<ArrayList<StockNotification>> typeIndicator = new GenericTypeIndicator<ArrayList<StockNotification>>() {
                };
                try {
                    ArrayList<StockNotification> notifications = dataSnapshot.getValue(typeIndicator);
                    if (notifications != null) {
                        for (StockNotification notification : notifications) {
                            if (notification != null) {
                                //if not invoked already
                                if (!notification.isInvoked()) {
                                    //send notification
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setContentTitle(context.getString(R.string.item_soldout))
                                            .setContentText(notification.getItemName())
                                            .setSmallIcon(R.drawable.outofstock)
                                            .setSound(Uri.parse(("android.resource://" + context.getPackageName() + "/raw/out")));

                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    counter++;
                                    notificationManager.notify(counter, builder.build());
                                    //set invoked on db
                                    db_stock_notifications.child(notification.getItemId() + "").child("invoked").setValue(true);

                                }
                            }
                        }
                    }
                } catch (DatabaseException ex) {
                }
            }
//            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        db_specials_notifications = database.getReference("SpecialNotifications");
        //stock notifications listener
        db_specials_notifications.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(ChooseEmployeeRoleActivity.loggedInUserRole.equals("מלצר")) {
                GenericTypeIndicator<ArrayList<SpecialNotification>> typeIndicator = new GenericTypeIndicator<ArrayList<SpecialNotification>>() {
                };
                try {
                    List<SpecialNotification> notifications = new ArrayList<SpecialNotification>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        notifications.add(child.getValue(SpecialNotification.class));
                    }
                    if (notifications != null) {
                        for (SpecialNotification notification : notifications) {
                            if (notification != null) {
                                //if not invoked already
                                if (!notification.isInvoked()) {
                                    //send
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                            .setContentTitle(context.getString(R.string.new_special_added_ntf))
                                            .setContentText(notification.getName())
                                            .setSmallIcon(R.drawable.special)
                                            .setSound(Uri.parse(("android.resource://" + context.getPackageName() + "/raw/special")));

                                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                    counter++;
                                    notificationManager.notify(counter, builder.build());
                                    //store on db
                                    db_specials_notifications.child(notification.getId() + "").child("invoked").setValue(true);
                                }
                            }
                        }
                    }
                } catch (DatabaseException ex) {
                }
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
