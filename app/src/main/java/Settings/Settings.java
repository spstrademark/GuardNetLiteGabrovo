package Settings;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
        } else {

        }
    }

    public void SaveLanguage()
    {
        // use activity
//                Configuration conf = this.context.getResources().getConfiguration();
//        conf.locale = new Locale("fr"); //french language locale
//        DisplayMetrics metrics = new DisplayMetrics();
//        ((Activity)this.context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        Resources resources = new Resources(getAssets(), metrics, conf);
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
