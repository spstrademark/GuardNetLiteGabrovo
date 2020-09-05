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
  //      this.settings = settings;
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

//    public void notificationsSetMails(String mail1, String mail2, String mail3)
//    {
//        SettingsUtils settings1 = SettingsUtils.getInstance();
//        String addAll = "";
//
//        if(mail1!=null && mail1.isEmpty()==false){
//            addAll += mail1;
//            if( (mail2!=null && mail2.isEmpty()==false) || (mail3 !=null && mail3.isEmpty()==false) ){
//                addAll += ITEM_SEPARATOR;
//            }
//        }
//
//        if(mail2!=null && mail2.isEmpty()==false){
//            addAll += mail2 + ITEM_SEPARATOR;
//        }
//
//        if(mail3!=null && mail3.isEmpty()==false){
//            addAll += mail3 + ITEM_SEPARATOR;
//        }
//
//
//            String val = settings1.GetNotificationEmailsKey();
//
//        settings1.GetPreferencesEditor().putString(val, addAll);
//        settings1.GetPreferencesEditor().apply();
//    }

//    public String[] notificationsGetMailFields()
//    {
//        SettingsUtils settings1 = SettingsUtils.getInstance();
//        String values = settings1.GetPreferences().getString(settings1.GetNotificationEmailsKey(),null);
//        if(values !=null){
//            if(values.indexOf(ITEM_SEPARATOR) !=-1){
//                return values.split(ITEM_SEPARATOR);
//            }else{
//                return values.concat(ITEM_SEPARATOR).split(ITEM_SEPARATOR);
//            }
//
//        }
//        return null;
//    }

    public String notificationsGetMails() {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        String val =  settings1.GetPreferences().getString(settings1.GetNotificationEmailsKey(),null);
        return val;
    }


    public void notificationsSendSet(boolean mailActive)
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        settings1.GetPreferencesEditor().putBoolean(settings1.GetNotificationSendMailKey(), mailActive);
        settings1.GetPreferencesEditor().apply();
    }

    public void notificationsNotifySet(boolean mailActive)
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        settings1.GetPreferencesEditor().putBoolean(settings1.GetNotificationNotifyKey(), mailActive);
        settings1.GetPreferencesEditor().apply();
    }

    public boolean  notificationsNotifyGet()
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        return settings1.GetPreferences().getBoolean(settings1.GetNotificationNotifyKey(),true);
    }

    public boolean  notificationsSendGet()
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        return settings1.GetPreferences().getBoolean(settings1.GetNotificationSendMailKey(),true);
    }

    public void notificationSecondsTriggerSet(String seconds)
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        String[] items = seconds.trim().split(" ");
        int val = Integer.parseInt(items[0]);
        settings1.GetPreferencesEditor().putInt(settings1.GetNotificationActivateKey(), val);
        settings1.GetPreferencesEditor().apply();
    }

    public int notificationSecondsTriggerGet()
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        return settings1.GetPreferences().getInt(settings1.GetNotificationActivateKey(),5);
    }

}
