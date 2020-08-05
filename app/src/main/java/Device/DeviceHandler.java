package Device;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import Common.Settings;
import Device.Device;

public class DeviceHandler {

//    private static String ID           = "ID";
//    private static String ITEM_URL     = "URL";
//    private static String ITEM_NAME    = "Name";
//    private static String AUTH         = "Auth";
//    private static String Username     = "Username";
//    private static String Password     = "Password";
    Settings settings;

    public boolean Add(@NonNull String URL, @NonNull String DisplayName, boolean auth, String Username, String Password, Settings settings) {
        this.settings = settings;

        if(!ValidField(URL))            return false;
        if(!ValidField(DisplayName))    return false;

        if(!TextUtils.isEmpty(Username)){
            if(!ValidField(Username))
                return false;
        }

        if(!TextUtils.isEmpty(Username)) {
            if (!ValidField(Password))
                return false;
        }

        String item = NewItem(URL,DisplayName,auth,Username,Password);
        if(item != null && item.length()>0){

        }

        return true;

    }

    public String NewItem(@NonNull String URL,@NonNull String DisplayName,boolean auth,String Username, String Password)
    {
        Device dev = new Device();
        dev.SetID(settings.InitDeviceID());
        dev.SetURL(URL);
        dev.SetName(DisplayName);
        dev.SetAuth(auth);
        dev.SetUsername(Username);
        dev.SetPassword(Password);
        Gson gson = new Gson();
        String json = gson.toJson(dev);
        return json;
    }

    public boolean ValidField(@NonNull String field)
    {
        return !(field.contains(settings.GetItemSeparator()));
    }



}
