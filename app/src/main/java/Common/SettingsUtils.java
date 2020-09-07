package Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.example.guardnet_lite_gabrovo.R;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Camera.PublicCamerasEnum;
import Notifications.NotificationsTriggerEnum;

public class SettingsUtils {

    String folder = "";
    final String LastView = "LastView";
    final String Settings = "Common";
    final String FirstTime = "FirstTime";
    final String SelectedCam = "SelectedCamera";
    final String Language = "SelectedLanguage";
    final String GalleryView = "SelectedView";
    private static String ITEM_SEPARATOR = ",";
    private static String DEVICE_ITEM_SEPARATOR = ";";
    final String NotificationTrigger = "SelectedNotificationTrigger";

    final String Devices = "Devices";
    final String DeviceCount = "DevicesCount";

    final String NotificationEmails = "NotificationEmails";

    final String NotificationNotify = "NotificationNotify";
    final String NotificationSendMail = "NotificationSendMail";
    final String NotificationActivate = "NotificationActivate";

    private Context context;

    private static SettingsUtils sInstance;
    private static final Object LOCK = new Object();
    private SharedPreferences sharedPreferences;

    private SettingsUtils(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public synchronized static void createInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new SettingsUtils(context);
                }
            }
        }
    }

    public static SettingsUtils getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Preference instance not initialized");
        }
        return sInstance;
    }


   /* public SettingsUtils(Context context, int activeView) {


        // final String APPLICATION_NAME =  context.getApplicationInfo().packageName;//context.getResources().getString(.app_name);
        this.context = context;
        this.activeView = activeView;
        this.GetPrefs = context.getSharedPreferences(Settings, context.MODE_PRIVATE);//.edit();
        this.SetPrefs = GetPrefs.edit();

        boolean firstTime = GetPrefs.getBoolean(FirstTime, true);

        if (firstTime) {
            SetPrefs.putBoolean(FirstTime, false);
        } else {

        }

        SetPrefs.putInt(this.LastView, activeView);
        SetPrefs.apply();

    }*/

    public int GetView() {
        return sharedPreferences.getInt(this.LastView, FragmentsEnum.MAIN_ACTIVITY.ordinal());
    }

    public void initAppFolder(String appName) {
        this.folder = appName;
        final File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), appName);

        if (!folder.exists()) {
            boolean rv = folder.mkdir();
        } else {

        }
    }

    public Configuration setLanguage(int languageIDX) {
        String lan = LanguagesEnum.values()[languageIDX].toString().toLowerCase();
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lan)); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);

        sharedPreferences.edit().putInt(this.Language, languageIDX).apply();
        return conf;
    }

    public int getLanguage() {
        int lang = sharedPreferences.getInt(Language, LanguagesEnum.EN.ordinal());
        String lan = LanguagesEnum.values()[lang].toString().toLowerCase();
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lan)); // API 17+ only.
        res.updateConfiguration(conf, dm);
        return lang;
    }

    public void saveSelectedCamera(int idx) {
        sharedPreferences.edit().putInt(this.SelectedCam, idx).apply();
    }

    public int getCamera() {
        //    return sharedPreferences.getInt(this.SelectedCam, PublicCamerasEnum.RADECKA.ordinal());
        int val = sharedPreferences.getInt(this.SelectedCam, 0);
        return val;
    }

    public void setGalleryView(int idx) {
        sharedPreferences.edit().putInt(this.GalleryView, idx).apply();
    }

    public int getGalleryView() {
        int default_grid = Integer.parseInt(context.getResources().getString(R.string.default_gallery_view));
        return sharedPreferences.getInt(this.GalleryView, default_grid);
    }

    public void setNotificationsEventTrigger(int idx) {
        sharedPreferences.edit().putInt(this.NotificationTrigger, idx).apply();
    }

    public int getNotificationsEventTrigger() {
        return sharedPreferences.getInt(this.NotificationTrigger, NotificationsTriggerEnum.SECONDS_5.ordinal());
    }

    public void notificationsSetMails(String mail1, String mail2, String mail3) {
        String addAll = "";

        if (mail1 != null && !mail1.isEmpty()) {
            addAll += mail1;
            if ((mail2 != null && !mail2.isEmpty()) || (mail3 != null && !mail3.isEmpty())) {
                addAll += ITEM_SEPARATOR;
            }
        }

        if (mail2 != null && !mail2.isEmpty()) {
            addAll += mail2 + ITEM_SEPARATOR;
        }

        if (mail3 != null && !mail3.isEmpty()) {
            addAll += mail3 + ITEM_SEPARATOR;
        }

        sharedPreferences.edit().putString(GetNotificationEmailsKey(), addAll).apply();
    }

    public String notificationsGetMails() {
        String val =  sharedPreferences.getString(GetNotificationEmailsKey(),context.getResources().getString(R.string.default_notification_email));
        return val;
    }


    public void notificationsSendSet(boolean mailActive)
    {
        sharedPreferences.edit().putBoolean(GetNotificationSendMailKey(), mailActive).apply();
    }

    public void notificationsNotifySet(boolean mailActive)
    {
        sharedPreferences.edit().putBoolean(GetNotificationNotifyKey(), mailActive).apply();
    }

    public boolean  notificationsNotifyGet()
    {
        return sharedPreferences.getBoolean(GetNotificationNotifyKey(),true);
    }

    public boolean  notificationsSendGet()
    {
        return sharedPreferences.getBoolean(GetNotificationSendMailKey(),true);
    }

    public void notificationSecondsTriggerSet(String seconds)
    {
        SettingsUtils settings1 = SettingsUtils.getInstance();
        String[] items = seconds.trim().split(" ");
        int val = Integer.parseInt(items[0]);
        sharedPreferences.edit().putInt(settings1.GetNotificationActivateKey(), val).apply();
    }

    public int notificationSecondsTriggerGet()
    {
        return sharedPreferences.getInt(GetNotificationActivateKey(),5);
    }

    public String[] notificationsGetMailFields() {
        String values = sharedPreferences.getString(GetNotificationEmailsKey(), null);
        if (values != null) {
            if (values.contains(ITEM_SEPARATOR)) {
                return values.split(ITEM_SEPARATOR);
            } else {
                return values.concat(ITEM_SEPARATOR).split(ITEM_SEPARATOR);
            }

        }
        return null;
    }

    //================================ DEVICE UTILS ================================

    public DevicePushResultTypes Add(@NonNull String URL, @NonNull String DisplayName, boolean auth, String Username, String Password) {

        if (TextUtils.isEmpty(URL)) {
            return DevicePushResultTypes.FIELD_IS_EMPTY;
        }

        if (!ValidField(URL)) return DevicePushResultTypes.INVALID_CHARACTER;
        if (!ValidField(DisplayName)) return DevicePushResultTypes.INVALID_CHARACTER;

        if (!TextUtils.isEmpty(Username)) {
            if (!ValidField(Username))
                return DevicePushResultTypes.FIELD_IS_EMPTY;
        }

        if (!TextUtils.isEmpty(Username)) {
            if (!ValidField(Password))
                return DevicePushResultTypes.FIELD_IS_EMPTY;
        }

        int id = InitDeviceID();
        if (id == -1) {
            return DevicePushResultTypes.MAX_LIMIT;
        }
        String item = NewItem(id, URL, DisplayName, auth, Username, Password);
        if (item != null && item.length() > 0) {
            PushNewDevice(item);
        }

        return DevicePushResultTypes.OK;

    }

    public String NewItem(int id, @NonNull String URL, @NonNull String DisplayName, boolean auth, String Username, String Password) {
        String json = "";
        UserDevice dev = new UserDevice();
        dev.SetID(id);
        dev.SetURL(URL);
        dev.SetName(InitDeviceName(DisplayName));
        dev.SetAuth(auth);
        dev.SetUsername(Username);
        dev.SetPassword(Password);
        Gson gson = new Gson();
        json = gson.toJson(dev);
        return json;
    }

    public boolean ValidField(@NonNull String field) {
        return !(field.contains(DEVICE_ITEM_SEPARATOR));
    }

    public void PushNewDevice(String device) {
        device = device.concat(DEVICE_ITEM_SEPARATOR);
        String all = sharedPreferences.getString(GetDeviceKey(), "");
        all += device;
        sharedPreferences.edit().putString(GetDeviceKey(), all).apply();
    }

    public UserDevice GetDeviceByID(int id) {

        String all = sharedPreferences.getString(GetDeviceKey(), null);

        Gson g = new Gson();
        if (all != null) {

            String[] Devices = all.split(DEVICE_ITEM_SEPARATOR);
            for (int i = 0; i < Devices.length; i++) {
                UserDevice device = g.fromJson(Devices[id], UserDevice.class);
                if (device.GetID() == id)
                    return device;
            }
        }
        return null;
    }

    public String InitDeviceName(String name) {
        int id = -1;
        Gson g = new Gson();
        String all = sharedPreferences.getString(GetDeviceKey(), null);
        int occurence = 0;
        if (all != null) {
            String[] devices = all.split(DEVICE_ITEM_SEPARATOR);

            for (id = 0; id < devices.length; id++) {
                UserDevice device = g.fromJson(devices[id], UserDevice.class);
                if(device!=null){
                    if (device.GetName().equals(name))
                        occurence++;
                }

            }
        }

        if (occurence > 0) {
            String subfix = String.format("(%d)", occurence);
            name.concat(subfix);
        }

        return name;
    }

    public List<String> GetAllDeviceNames() {
        List<String> Names = new ArrayList<String>();
        Gson g = new Gson();
        String all = sharedPreferences.getString(GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(DEVICE_ITEM_SEPARATOR);

            for (int i = 0; i < devices.length; i++) {
                UserDevice device = g.fromJson(devices[i], UserDevice.class);
                String url = device.GetURL();
                String name = device.GetName();
                if (name != null && name.length() > 0)
                    Names.add(name);
                else {
                    Names.add(url);
                }
            }
        }


        return Names;
    }

    public List<UserDevice> getAllDevices() {
        List<UserDevice> myDevices = new ArrayList<>();
        Gson g = new Gson();
        String all = sharedPreferences.getString(GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(DEVICE_ITEM_SEPARATOR);

            for (int i = 0; i < devices.length; i++) {
                UserDevice device = g.fromJson(devices[i], UserDevice.class);
                myDevices.add(device);
            }
        }


        return myDevices;
    }

    public int DeviceExist(UserDevice dev) {
        int id = -1;
        Gson g = new Gson();
        String all = sharedPreferences.getString(GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(DEVICE_ITEM_SEPARATOR);

            for (id = 0; id < devices.length; id++) {
                UserDevice device = g.fromJson(devices[id], UserDevice.class);
                if (device.GetURL().equals(dev.GetURL()))
                    return id;


            }
        }


        return -1;
    }

    public int InitDeviceID() {
        int id = 0;
        Gson g = new Gson();
        String all = sharedPreferences.getString(GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(DEVICE_ITEM_SEPARATOR);
            int count = Integer.valueOf(context.getResources().getString(R.string.MaxDevices));//getString(R.string.hello);

            for (id = 0; id < count; id++) {
                UserDevice device;
                try {
                    device = g.fromJson(devices[id], UserDevice.class);

                    if (device.GetID() != id)
                        return id;

                } catch (Exception e) {
                    return id;
                }



            }
        }

        return id;
    }

    public int GetDeviceCount() {
        int count = 0;
        String all = sharedPreferences.getString(GetDeviceKey(), null);

        if (all != null) {
            count = all.split(DEVICE_ITEM_SEPARATOR).length;
        }

        return count;
    }

    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ DEVICE UTILS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    // TODO: remove
    public SharedPreferences GetPreferences() {
        return sharedPreferences;
    }

    // TODO: remove
    public SharedPreferences.Editor GetPreferencesEditor() {
        return sharedPreferences.edit();
    }

    public String GetDeviceKey() {
        return Devices;
    }

    public String GetNotificationEmailsKey() {
        return NotificationEmails;
    }

    public String GetNotificationNotifyKey() {
        return NotificationNotify;
    }

    public String GetNotificationSendMailKey() {
        return NotificationSendMail;
    }

    public String GetNotificationActivateKey() {
        return NotificationActivate;
    }

    // TODO: remove
    public Context GetContext() {
        return context;
    }

}
