package com.game.bentz.ShapeShift.Utility;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Michael on 6/30/16.
 * The <code>NotificationCenter</code> is the handler for adding and removing a <code>Notification</code>
 */
public class NotificationCenter {
    private static NotificationCenter instance;

    private List<Notification> notifications;
    private boolean powerUpActive;

    private NotificationCenter(){
        this.notifications = new CopyOnWriteArrayList<>();
        this.powerUpActive = false;
    }

    /**
     * Returns and instance of <code>NotificationCenter</code>
     * @return the <code>NotificationCenter</code> singleton
     */
    public static NotificationCenter getInstance(){
        if (instance == null){
            instance = new NotificationCenter();
        }
        return instance;
    }

    /**
     * Removes all existing notifications
     */
    public void reset(){
        if (!notifications.isEmpty()){
            notifications.clear();
        }
    }

    /**
     * Returns the list of notifications
     * @return the <code>List</code> of notifications
     */
    public List<Notification> getNotifications(){
        return notifications;
    }

    /**
     * Returns if the list of notifications is empty
     * @return <monocode>true</monocode> or <monocode>false</monocode>
     */
    public boolean isEmpty(){
        return notifications.isEmpty();
    }

    /**
     * Adds a notification to the list
     * @param notification the <code>Notification</code> added to the list
     */
    public void addNotification(Notification notification){
        notifications.add(notification);
    }

    /**
     * Removes the notification from the list
     * @param notification the <code>Notification removed from the list</code>
     */
    public void removeNotification(Notification notification){
        if (!notifications.isEmpty()) {
            notifications.remove(notification);
        }
    }


    public boolean isPowerUpActive(){
        return powerUpActive;
    }

}
