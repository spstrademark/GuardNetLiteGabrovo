package AddTools;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import Common.Settings;

public class Credentials {
    private static String ITEM_URL     = "URL";
    private static String ITEM_NAME    = "Name";
    private static String AUTH         = "Auth";
    private static String Username     = "Username";
    private static String Password     = "Password";
    Settings settings;

    public boolean Add(@NonNull String URL,@NonNull String DisplayName,boolean auth,String Username, String Password, Settings settings) {
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

        NewItem(URL,DisplayName,auth,Username,Password);

        return true;

    }

    public void NewItem(@NonNull String URL,@NonNull String DisplayName,boolean auth,String Username, String Password)
    {

        JSONObject item = new JSONObject();
        try {
            item.put(this.ITEM_URL, URL);
            item.put(this.ITEM_NAME, DisplayName);
            item.put(this.AUTH, auth);
            item.put(this.Username, Username);
            item.put(this.Password, Password);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String test = "";
    //    return (String)item;
       // String playVideo= String.format("", GetCameraURL(camera));
    }

    public boolean ValidField(@NonNull String field)
    {

         return !(field.contains(settings.GetFieldSeparator()) || (field.contains(settings.GetItemSeparator())) );
    }

}
