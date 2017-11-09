package Logic;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import sapir_liran.melzarito.UI.NotificationListener;

//import static sapir_liran.melzarito.UI.NotificationListener.setSendToWaiters;

//Handles all restaurant actions and DB read/write
public class RestaurantManager {
    private static RestaurantManager singletonRestaurantManager = null;
    private FirebaseDatabase database;
    private DatabaseReference db;
    private Menu menu;
    private int orderIdCounter = 0;
    private int orderItemIdCounter = 0;
    private ArrayList<Table> tables;
    private HashMap<Integer, Order> orders = new LinkedHashMap<>();
    private String loggedInUserName;
    private String loggedInUserRole;

    //init db connection
    private RestaurantManager() {
        database = FirebaseDatabase.getInstance();
        db = database.getReference();
        db.keepSynced(true);
        loadMenu();
        loadTables();
        loadManagerCounters();
        loadAllOpenOrders();


    }

    public static RestaurantManager getInstance() {
        if (singletonRestaurantManager == null) {
            singletonRestaurantManager = new RestaurantManager();
        }
        return singletonRestaurantManager;
    }

    public void loadMenu() {
        database.getReference("Menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //loading Menu
                try {
                    long idl = (long) dataSnapshot.child("id").getValue();
                    int id = (int) idl;
                    GenericTypeIndicator<ArrayList<MenuItem>> items_type = new GenericTypeIndicator<ArrayList<MenuItem>>() {
                    };
                    ArrayList<MenuItem> items = dataSnapshot.child("Items").getValue(items_type);
                    menu = new Menu(id, items);
                } catch (DatabaseException ex) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadManagerCounters() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("counterOrderID").getValue() == null)
                    db.child("counterOrderID").setValue(0);
                long longOrderIdCounter = (long) dataSnapshot.child("counterOrderID").getValue();

                if (dataSnapshot.child("counterOrderItemsID").getValue() == null)
                    db.child("counterOrderItemsID").setValue(0);
                long longOrderItemIdCounter = (long) dataSnapshot.child("counterOrderItemsID").getValue();

                orderIdCounter = (int) longOrderIdCounter;
                orderItemIdCounter = (int) longOrderItemIdCounter;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public Menu getMenu() {
        return menu;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void createOrderItemAndWriteToDB(MenuItem item, int category, int tableNum) {

        OrderItem new_item = new OrderItem(item.getId(), item.getName(), category, orderItemIdCounter, new Date(), item.getPrice());

        db.child("Orders").child(orderIdCounter + "").child("Order items").child(orderItemIdCounter + "").child("category").setValue(new_item.getCategory());
        db.child("Orders").child(orderIdCounter + "").child("Order items").child(orderItemIdCounter + "").child("MenuItemId").setValue(new_item.getId());
        db.child("Orders").child(orderIdCounter + "").child("Order items").child(orderItemIdCounter + "").child("lastModifiedTime").setValue(new_item.getLastModifiedTime());
        db.child("Orders").child(orderIdCounter + "").child("Order items").child(orderItemIdCounter + "").child("name").setValue(new_item.getName());
        db.child("Orders").child(orderIdCounter + "").child("Order items").child(orderItemIdCounter + "").child("price").setValue(new_item.getPrice());

        orderItemIdCounter++;

        db.child("counterOrderItemsID").setValue(orderItemIdCounter);

        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getNumber() == tableNum) {
                tables.get(i).setIsEmpty(false);
                db.child("Tables").child(i + "").child("empty").setValue(false);
                break;
            }
        }


    }

    public void CreateNewOrderAndWriteToDB(int tableNumber) {
        orderIdCounter++;
        db.child("counterOrderID").setValue(orderIdCounter);
        Order new_order = new Order(orderIdCounter, tableNumber, loggedInUserName, new Date(), 1, true, 1);
        db.child("Orders").child(new_order.getId() + "").setValue(new_order);
        kitchenNotification(orderIdCounter,tableNumber);
    }

    public Collection<Order> getOrders() {
        return orders.values();
    }

    public void OutOfStock(MenuItem item) {
        db.child("StockNotifications").child(item.getId() + "").child("itemId").setValue(item.getId());
        db.child("StockNotifications").child(item.getId() + "").child("itemName").setValue(item.getName());
        db.child("StockNotifications").child(item.getId() + "").child("invoked").setValue(false);
    }

    public void addNewSpecial(String name, double price) {
        db.child("Menu").child("Items").child(menu.getItems().size() + 1 + "").child("category").setValue(4);
        db.child("Menu").child("Items").child(menu.getItems().size() + 1 + "").child("id").setValue(menu.getItems().size() + 1);
        db.child("Menu").child("Items").child(menu.getItems().size() + 1 + "").child("name").setValue(name);
        db.child("Menu").child("Items").child(menu.getItems().size() + 1 + "").child("price").setValue(price);

        int id = menu.getItems().size() + 1;
        db.child("SpecialNotifications").child(id + "").child("id").setValue(id);
        db.child("SpecialNotifications").child(id + "").child("name").setValue(name);
        db.child("SpecialNotifications").child(id + "").child("invoked").setValue(false);
    }


    public void getLoggedInUserFromDB(final String uid) {

        database.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loggedInUserName = (String) dataSnapshot.child(uid).child("name").getValue();
                loggedInUserRole = (String) dataSnapshot.child(uid).child("role").getValue();
                if (loggedInUserRole.equals("מלצר")) {
                    NotificationListener.setSendToWaiters(true);
                }
                if (loggedInUserRole.equals("טבח")) {
                    NotificationListener.setSendToKitchen(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void loadAllOpenOrders() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orders.clear();
                if (dataSnapshot.child("Orders").getValue() != null) {
                    Integer[] ids = new Integer[(int) ((long) dataSnapshot.child("Orders").getChildrenCount())];
                    int i = 0;
                    for (DataSnapshot d : dataSnapshot.child("Orders").getChildren()) {
                        ids[i] = Integer.parseInt(d.getKey());
                        i++;
                    }
                    for (final Integer order_id : ids) {

                        GenericTypeIndicator<Order> order_type = new GenericTypeIndicator<Order>() {
                        };
                        final Order curr_order = dataSnapshot.child("Orders").child(order_id.toString()).getValue(order_type);
                        final Integer[] orderItemsIds = new Integer[(int) ((long) dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildrenCount())];
                        i = 0;
                        for (DataSnapshot d : dataSnapshot.child("Orders").child(order_id + "").child("Order items").getChildren()) {
                            orderItemsIds[i] = Integer.parseInt(d.getKey());
                            i++;
                        }


                        final Query query = db.child("Orders").child(order_id + "").child("Order items");

                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.getValue() != null) {
                                    try {
                                        GenericTypeIndicator<Date> date_type = new GenericTypeIndicator<Date>() {
                                        };
                                        Date tempLastModified = dataSnapshot.child("lastModifiedTime").getValue(date_type);
                                        String tempName = (String) dataSnapshot.child("name").getValue();
                                        int tempCategory = (int) ((long) dataSnapshot.child("category").getValue());
                                        int tempMenuId = (int) ((long) dataSnapshot.child("MenuItemId").getValue());
                                        int orderItem_id = Integer.parseInt(dataSnapshot.getKey());
                                        double price = Double.parseDouble(dataSnapshot.child("price").getValue().toString());
                                        curr_order.addOrderItem(new OrderItem(tempMenuId, tempName, tempCategory, orderItem_id, tempLastModified, price));
                                    } catch (NullPointerException ex) {
                                    }

                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        if (curr_order.isOpen()) {

                            orders.put(order_id, curr_order);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changeOrderStatus(int orderId, int status) {
        db.child("Orders").child(orderId + "").child("status").setValue(status);
    }

    public void sendServiceNotification(int orderId, int tableNumber) {
        db.child("Notifications").child(orderId + "").child("orderId").setValue(orderId);
        db.child("Notifications").child(orderId + "").child("tableNumber").setValue(tableNumber);
        db.child("Notifications").child(orderId + "").child("invoked").setValue(false);
    }

    public void writeUser(String role, String userName, String uid) {
        db.child("Users").child(uid).child("role").setValue(role);
        db.child("Users").child(uid).child("name").setValue(userName);
    }

    public void loadTables() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Table>> table_type = new GenericTypeIndicator<ArrayList<Table>>() {
                };
                tables = dataSnapshot.child("Tables").getValue(table_type);
                for (int i = 0; i < tables.size(); i++) {
                    tables.get(i).setClubMember(dataSnapshot.child("Tables").child(i + "").child("isClubMember").getValue(boolean.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void writeClubMember(ClubMember clubMember) {
        db.child("ClubMembers").child(clubMember.getId() + "").setValue(clubMember);
    }

    public void setClubMemberToTable(final int id, final int tableNum, final Context context) {

        database.getReference("ClubMembers").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ClubMember> customer_type = new GenericTypeIndicator<ClubMember>() {
                };
                try {
                    ClubMember clubMember = dataSnapshot.child(id + "").getValue(customer_type);
                    if (clubMember != null) {
                        for (int i = 0; i < tables.size(); i++) {
                            if (tables.get(i).getNumber() == tableNum) {
                                tables.get(i).setClubMember(true);
                                db.child("Tables").child(i + "").child("isClubMember").setValue(true);
                                Toast.makeText(context, "חבר מועדון נמצא!", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else
                        Toast.makeText(context, "חבר מועדון לא קיים!", Toast.LENGTH_LONG).show();
                } catch (DatabaseException e) {
                    Toast.makeText(context, "שגיאת נתונים", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void closeOrder(int orderID) {
        db.child("Orders").child(orderID + "").child("open").setValue(false);
    }

    public void emptyTable(int indexTable) {
        db.child("Tables").child(indexTable + "").child("empty").setValue(true);
        db.child("Tables").child(indexTable + "").child("isClubMember").setValue(false);
    }

    public void kitchenNotification(int orderid, int tableNum) {
        db.child("KitchenNotification").child(orderid + "").child("orderId").setValue(orderid);
        db.child("KitchenNotification").child(orderid + "").child("tableNum").setValue(tableNum);
        db.child("KitchenNotification").child(orderid + "").child("invoked").setValue(false);
    }
}