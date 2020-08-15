package Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;

import androidx.preference.PreferenceManager;

import com.example.guardnet_lite_gabrovo.R;

import java.io.File;
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

    final String NotificationTrigger = "SelectedNotificationTrigger";

    final String Devices = "Devices";
    final String DeviceCount = "DevicesCount";

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

    public void InitAppFolder(String AppName) {
        this.folder = AppName;
        final File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), AppName);

        if (!folder.exists()) {
            boolean rv = folder.mkdir();
        } else {

        }
    }

    public Configuration SetLanguage(int languageIDX) {
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

    public int GetLanguage() {
        int lang = sharedPreferences.getInt(Language, LanguagesEnum.EN.ordinal());
        String lan = LanguagesEnum.values()[lang].toString().toLowerCase();
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lan)); // API 17+ only.
        res.updateConfiguration(conf, dm);
        return lang;
    }

    public void SetCamera(int idx) {
        sharedPreferences.edit().putInt(this.SelectedCam, idx).apply();
    }

    public int GetCamera() {
        return sharedPreferences.getInt(this.SelectedCam, PublicCamerasEnum.RADECKA.ordinal());
    }

    public void SetGalleryView(int idx) {
        sharedPreferences.edit().putInt(this.GalleryView, idx).apply();
    }

    public int GetGalleryView() {
        int default_grid = Integer.parseInt(context.getResources().getString(R.string.default_gallery_view));
        return sharedPreferences.getInt(this.GalleryView, default_grid);
    }

    public void SetNotificationsEventTrigger(int idx) {
        sharedPreferences.edit().putInt(this.NotificationTrigger, idx).apply();
    }

    public int GetNotificationsEventTrigger() {
        return sharedPreferences.getInt(this.NotificationTrigger, NotificationsTriggerEnum.SECONDS_5.ordinal());
    }

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

    // TODO: remove
    public Context GetContext() {
        return context;
    }

}
