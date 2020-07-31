package Settings;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.util.Locale;

import Camera.PublicCamerasEnum;

public class Settings {
    String folder = "";
    final String Settings       = "Settings";
    final String FirstTime      = "FirstTime";
    final String SelectedCam    = "SelectedCamera";
    final String Language       = "SelectedLanguage";
    SharedPreferences.Editor SetPrefs;
    SharedPreferences GetPrefs;
    Context context;
    public Settings(Context context) {


       // final String APPLICATION_NAME =  context.getApplicationInfo().packageName;//context.getResources().getString(.app_name);
        this.context = context;
        this.GetPrefs = context.getSharedPreferences(Settings, context.MODE_PRIVATE);//.edit();
        this.SetPrefs = GetPrefs.edit();

        boolean firstTime = GetPrefs.getBoolean(FirstTime,true);

        if(firstTime){
            SetPrefs.putBoolean(FirstTime,false);
        }else{

        }

    }
    public void Init(Context context)
    {

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

    public void SaveLanguageValue(int languageIDX)
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
    }

    public int RestoreLanguage()
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

    public void SaveCameraValue(int idx)
    {
        SetPrefs.putInt(SelectedCam,idx);
        SetPrefs.apply();
    }

    public int RestoreCameraValue()
    {
        return GetPrefs.getInt(SelectedCam, PublicCamerasEnum.RADECKA.ordinal());
    }

}
