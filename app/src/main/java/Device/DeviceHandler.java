package Device;

import android.text.TextUtils;

import androidx.annotation.NonNull;


import com.example.guardnet_lite_gabrovo.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import Common.SettingsUtils;

public class DeviceHandler {

    private static String ITEM_SEPARATOR = ";";
    private SettingsUtils settings;

    public DeviceHandler(SettingsUtils settings) {
        this.settings = settings;
    }

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
        Device dev = new Device();
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
        return !(field.contains(ITEM_SEPARATOR));
    }


    public void PushNewDevice(String device) {
        device = device.concat(ITEM_SEPARATOR);
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);
        all += device;
        settings.GetPreferencesEditor().putString(settings.GetDeviceKey(), all);
        settings.GetPreferencesEditor().apply();
    }

    public Device GetDeviceByID(int id) {

        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);

        Gson g = new Gson();
        if (all != null) {

            String[] Devices = all.split(ITEM_SEPARATOR);
            for (int i = 0; i < Devices.length; i++) {
                Device device = g.fromJson(Devices[id], Device.class);
                if (device.GetID() == id)
                    return device;
            }
        }
        return null;
    }


    public int GetDeviceCount() {
        int count = 0;
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);

        if (all != null) {
            count = all.split(ITEM_SEPARATOR).length;
        }

        return count;
    }

    public int InitDeviceID() {
        int id = 0;
        Gson g = new Gson();
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(ITEM_SEPARATOR);
            int count = Integer.valueOf(settings.GetContext().getResources().getString(R.string.MaxDevices));//getString(R.string.hello);

            for (id = 0; id < count; id++) {
                Device device;
                try {
                    device = g.fromJson(devices[id], Device.class);

                    if (device.GetID() != id)
                        return id;

                } catch (Exception e) {
                    return id;
                }



            }
        }

        return id;
    }

    public int DeviceExist(Device dev) {
        int id = -1;
        Gson g = new Gson();
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(ITEM_SEPARATOR);

            for (id = 0; id < devices.length; id++) {
                Device device = g.fromJson(devices[id], Device.class);
                if (device.GetURL().equals(dev.GetURL()))
                    return id;


            }
        }


        return -1;
    }

    public String InitDeviceName(String name) {
        int id = -1;
        Gson g = new Gson();
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);
        int occurence = 0;
        if (all != null) {
            String[] devices = all.split(ITEM_SEPARATOR);

            for (id = 0; id < devices.length; id++) {
                Device device = g.fromJson(devices[id], Device.class);
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

    public List<Device> GetAllDevices() {
        List<Device> myDevices = new ArrayList<Device>();
        Gson g = new Gson();
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(ITEM_SEPARATOR);

            for (int i = 0; i < devices.length; i++) {
                Device device = g.fromJson(devices[i], Device.class);
                myDevices.add(device);
            }
        }


        return myDevices;
    }

    public List<String> GetAllDeviceNames() {
        List<String> Names = new ArrayList<String>();
        Gson g = new Gson();
        String all = settings.GetPreferences().getString(settings.GetDeviceKey(), null);

        if (all != null) {
            String[] devices = all.split(ITEM_SEPARATOR);

            for (int i = 0; i < devices.length; i++) {
                Device device = g.fromJson(devices[i], Device.class);
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


}
