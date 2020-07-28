package Settings;
import android.content.Context;
import android.content.SharedPreferences;
import Camera.PublicCamerasEnum;

public class Settings {

    final String Settings       = "Settings";
    final String FirstTime      = "FirstTime";
    final String SelectedCam    = "LastSelectedCamera";
    SharedPreferences.Editor SetPrefs;
    SharedPreferences GetPrefs;

    public Settings(Context context) {

        GetPrefs = context.getSharedPreferences(Settings, context.MODE_PRIVATE);//.edit();
        SetPrefs = GetPrefs.edit();

        boolean firstTime = GetPrefs.getBoolean(FirstTime,true);

        if(firstTime){
            SetPrefs.putBoolean(FirstTime,false);
        }else{

        }

    }
    public void Init(Context context)
    {

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
