package Common;
import com.example.guardnet_lite_gabrovo.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.util.Locale;

import Camera.PublicCamerasEnum;
import Notifications.NotificationsTriggerEnum;

public class Settings {
    String folder = "";
    final String LastView       = "LastView";
    final String SecondLastView = "SecondLastView";
    final String Settings       = "Common";
    final String FirstTime      = "FirstTime";
    final String SelectedCam    = "SelectedCamera";
    final String Language       = "SelectedLanguage";
    final String GalleryView    = "SelectedView";

    final String NotificationTrigger    = "SelectedNotificationTrigger";
    SharedPreferences.Editor SetPrefs;
    SharedPreferences GetPrefs;
    Context context;
    int activeView;


    public Settings(Context context,int activeView) {


       // final String APPLICATION_NAME =  context.getApplicationInfo().packageName;//context.getResources().getString(.app_name);
        this.context = context;
        this.activeView = activeView;
        this.GetPrefs = context.getSharedPreferences(Settings, context.MODE_PRIVATE);//.edit();
        this.SetPrefs = GetPrefs.edit();

        boolean firstTime = GetPrefs.getBoolean(FirstTime,true);

        if(firstTime){
            SetPrefs.putBoolean(FirstTime,false);
        }else{

        }


        SetPrefs.putInt(LastView,activeView);



        SetPrefs.apply();

    }
    public int GetView()
    {
        return GetPrefs.getInt(LastView,  FragmentsEnum.MAIN_ACTIVITY.ordinal());
    }

    public void InitAppFolder(String AppName)
    {
        this.folder = AppName;
        final File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), AppName);

        if (!folder.exists()) {
            boolean rv =         folder.mkdir();    //f.mkdir();
            Log.d("tst","tst");
        } else {

        }
    }

    public android.content.res.Configuration SetLanguage(int languageIDX)
    {
        String lan =  LanguagesEnum.values()[languageIDX].toString().toLowerCase();
        Resources res = context.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lan)); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);

        SetPrefs.putInt(Language,languageIDX);
        SetPrefs.apply();
        return conf;
    }

    public int GetLanguage()
    {
        int lang = GetPrefs.getInt(Language, LanguagesEnum.EN.ordinal());
        String lan =  LanguagesEnum.values()[lang].toString().toLowerCase();
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lan)); // API 17+ only.
        res.updateConfiguration(conf, dm);
        return lang;
    }

    public void SetCamera(int idx)
    {
        SetPrefs.putInt(SelectedCam,idx);
        SetPrefs.apply();
    }

    public int GetCamera()
    {
        return GetPrefs.getInt(SelectedCam, PublicCamerasEnum.RADECKA.ordinal());
    }

    public void SetGalleryView(int idx)
    {
        SetPrefs.putInt(GalleryView,idx);
        SetPrefs.apply();
    }

    public int GetGalleryView()
    {
        int default_grid = Integer.parseInt(context.getResources().getString(R.string.default_gallery_view));
        return GetPrefs.getInt(GalleryView, default_grid);
    }

    public void SetNotificationsEventTrigger(int idx)
    {
        SetPrefs.putInt(NotificationTrigger,idx);
        SetPrefs.apply();
    }

    public int GetNotificationsEventTrigger()
    {
        return GetPrefs.getInt(NotificationTrigger, NotificationsTriggerEnum.SECONDS_5.ordinal());
    }

}