package Settings;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;

import Camera.PublicCamerasEnum;

public class Settings {

    final String Settings       = "Settings";
    final String FirstTime      = "FirstTime";
    final String SelectedCam    = "SelectedCamera";
    final String Language       = "SelectedLanguage";
    SharedPreferences.Editor SetPrefs;
    SharedPreferences GetPrefs;
    Context context;
    public Settings(Context context) {
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
