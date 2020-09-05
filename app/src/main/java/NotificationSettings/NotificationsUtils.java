package NotificationSettings;

import android.content.Context;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import Ai.Coords;
import Common.SettingsUtils;
import OddBehavior.OddBehavior;

public class NotificationsUtils {

    private static NotificationsUtils sInstance;
    private static String ITEM_SEPARATOR = ",";
    private SettingsUtils settings;
    private static final Object LOCK = new Object();

    public synchronized static void createInstance(SettingsUtils settings) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new NotificationsUtils(settings);
                }
            }
        }
    }
    private NotificationsUtils(SettingsUtils settings) {
        this.settings = settings;
    }

    public static NotificationsUtils getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Preference instance not initialized");
        }
        return sInstance;
    }

//    public NotificationsUtils(SettingsUtils settings) {
//        this.settings = settings;
//    }

    public void notificationsSetMails(String mail1, String mail2, String mail3)
    {
        String addAll = "";

        if(mail1!=null && mail1.isEmpty()==false){
            addAll += mail1;
            if( (mail2!=null && mail2.isEmpty()==false) || (mail3 !=null && mail3.isEmpty()==false) ){
                addAll += ITEM_SEPARATOR;
            }
        }

        if(mail2!=null && mail2.isEmpty()==false){
            addAll += mail2 + ITEM_SEPARATOR;
        }

        if(mail3!=null && mail3.isEmpty()==false){
            addAll += mail3 + ITEM_SEPARATOR;
        }

        settings.GetPreferencesEditor().putString(settings.GetNotificationEmailsKey(), addAll);
        settings.GetPreferencesEditor().apply();
    }

    public String[] notificationsGetMailFields()
    {
        String values = settings.GetPreferences().getString(settings.GetNotificationEmailsKey(),null);
        if(values !=null){
            if(values.indexOf(ITEM_SEPARATOR) !=-1){
                return values.split(ITEM_SEPARATOR);
            }else{
                return values.concat(ITEM_SEPARATOR).split(ITEM_SEPARATOR);
            }

        }
        return null;
    }

    public String notificationsGetMails() {
        String val =  settings.GetPreferences().getString(settings.GetNotificationEmailsKey(),null);
        return val;
    }


    public void notificationsSendSet(boolean mailActive)
    {
        settings.GetPreferencesEditor().putBoolean(settings.GetNotificationSendMailKey(), mailActive);
        settings.GetPreferencesEditor().apply();
    }

    public void notificationsNotifySet(boolean mailActive)
    {
        settings.GetPreferencesEditor().putBoolean(settings.GetNotificationNotifyKey(), mailActive);
        settings.GetPreferencesEditor().apply();
    }

    public boolean  notificationsNotifyGet()
    {
        return settings.GetPreferences().getBoolean(settings.GetNotificationNotifyKey(),true);
    }

    public boolean  notificationsSendGet()
    {
        return settings.GetPreferences().getBoolean(settings.GetNotificationSendMailKey(),true);
    }

    public void notificationSecondsTriggerSet(String seconds)
    {
        String[] items = seconds.trim().split(" ");
        int val = Integer.parseInt(items[0]);
        settings.GetPreferencesEditor().putInt(settings.GetNotificationActivateKey(), val);
        settings.GetPreferencesEditor().apply();
    }

    public int notificationSecondsTriggerGet()
    {
        return settings.GetPreferences().getInt(settings.GetNotificationActivateKey(),5);
    }

}
