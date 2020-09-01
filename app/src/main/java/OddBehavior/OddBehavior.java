package OddBehavior;

import android.content.Context;

import Common.SettingsUtils;

public class OddBehavior {

    private static OddBehavior sInstance;
    private static final Object LOCK = new Object();
    public synchronized static void createInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new OddBehavior();
                }
            }
        }
    }

    public static OddBehavior getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Preference instance not initialized");
        }
        return sInstance;
    }

}
