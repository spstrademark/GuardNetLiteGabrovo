package OddBehavior;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Ai.Coords;
import Ai.TFLiteDetector;
import Common.SettingsUtils;

public class OddBehavior {

    private static OddBehavior sInstance;
    private static List<List<Coords>> positioning;
    private static final Object LOCK = new Object();
    public synchronized static void createInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new OddBehavior();
                    positioning = new ArrayList<List<Coords>>();
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

//    public boolean isBehaviorOdd(List<List<TFLiteDetector.Coords>> coords)
//    {
//        return false;
//    }

//    public boolean isBehaviorOdd(List<List<Coords>> kp) {
//
//        return false;
//    }

    public boolean isBehaviorOdd(@NotNull List<? extends Map<String, Object>> kp) {
        return false;
    }

//    @NotNull
//    public static boolean isBehaviorOdd(@NotNull List<? extends Map<String, Object>> kp) {
//        return boolean;
//    }

}
