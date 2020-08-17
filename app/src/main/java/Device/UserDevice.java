package Device;

public class UserDevice {
    private int ID;
    private String URL;
    private String NAME;
    private boolean AUTH;
    private String USERNAME;
    private String PASSWORD;

    public void SetID(int id)
    {
        this.ID = id;
    }

    public void SetURL(String url)
    {
        this.URL = url;
    }

    public void SetName(String name)
    {
        this.NAME = name;
    }

    public void SetAuth(boolean auth)
    {
        this.AUTH = auth;
    }

    public void SetUsername(String username)
    {
        this.USERNAME = username;
    }

    public void SetPassword(String password)
    {
        this.PASSWORD = password;
    }





    public int GetID() { return this.ID; }

    public String GetURL()
    {
        return this.URL;
    }

    public String GetName()
    {
        return this.NAME;
    }

    public boolean GetAuth() { return this.AUTH; }

    public String GetUsername() { return this.USERNAME; }

    public String GetPassword()
    {
        return this.PASSWORD;
    }


//    private static String ID           = "ID";
//    private static String ITEM_URL     = "URL";
//    private static String ITEM_NAME    = "Name";
//    private static String AUTH         = "Auth";
//    private static String Username     = "Username";
//    private static String Password     = "Password";
//    Settings settings;
//
//    public boolean Add(@NonNull String URL,@NonNull String DisplayName,boolean auth,String Username, String Password, Settings settings) {
//        this.settings = settings;
//
//        if(!ValidField(URL))            return false;
//        if(!ValidField(DisplayName))    return false;
//
//        if(!TextUtils.isEmpty(Username)){
//            if(!ValidField(Username))
//                return false;
//        }
//
//        if(!TextUtils.isEmpty(Username)) {
//            if (!ValidField(Password))
//                return false;
//        }
//
//        String item = NewItem(URL,DisplayName,auth,Username,Password);
//        if(item != null && item.length()>0){
//
//        }
//
//        return true;
//
//    }
//
//    public String NewItem(@NonNull String URL,@NonNull String DisplayName,boolean auth,String Username, String Password)
//    {
//
//        JSONObject item = new JSONObject();
//        try {
//            item.put(this.ID, settings.GetDeviceID());
//            item.put(this.ITEM_URL, URL);
//            item.put(this.ITEM_NAME, DisplayName);
//            item.put(this.AUTH, auth);
//            item.put(this.Username, Username);
//            item.put(this.Password, Password);
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return item.toString();
////        JSONObject obj;
////        try {
////             obj = new JSONObject(tmp);
////        } catch (JSONException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
//
//    //    String test = "";
//    //    return (String)item;
//       // String playVideo= String.format("", GetCameraURL(camera));
//    }
//
//    public boolean ValidField(@NonNull String field)
//    {
//         return !(field.contains(settings.GetItemSeparator()));
//    }

}
