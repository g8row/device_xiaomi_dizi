/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.app.ActivityManager;
import android.content.om.OverlayManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.PreferenceManager;

/**
 * Launcher blur (Display > Launcher effects). The Pixel Launcher blurs the home screen and
 * wallpaper behind the app drawer and behind launching apps; SurfaceFlinger then composes those
 * layers on the GPU for every frame of the animation. Off by default: a runtime overlay sets the
 * launcher's blur radius to 0.
 */
public final class LauncherEffects {

    private static final String TAG = "LauncherEffects";
    public static final String KEY_LAUNCHER_BLUR = "launcher_blur";
    public static final String KEY_SHADE_BLUR = "shade_blur";
    /** SystemUI reads it when it starts (WindowRootViewBlurRepository, BlurUtils). */
    private static final String PROP_DISABLE_SHADE_BLUR = "persist.sysui.disableBlur";
    private static final String NO_BLUR_OVERLAY =
            "com.google.android.apps.nexuslauncher.overlay.dizi.noblur";

    private LauncherEffects() { }

    private static final String PIXEL_LAUNCHER = "com.google.android.apps.nexuslauncher";

    public static boolean hasPixelLauncher(Context context) {
        try {
            context.getPackageManager().getPackageInfo(PIXEL_LAUNCHER, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBlurEnabled(Context context) {
        return prefs(context).getBoolean(KEY_LAUNCHER_BLUR, false);
    }

    /** Applies the saved choice (at boot, and when the switch changes). */
    public static void apply(Context context) {
        if (!hasPixelLauncher(context)) {
            return;
        }
        apply(context, isBlurEnabled(context));
    }

    public static void apply(Context context, boolean blur) {
        try {
            context.getSystemService(OverlayManager.class)
                    .setEnabled(NO_BLUR_OVERLAY, !blur, UserHandle.SYSTEM);
        } catch (Exception e) {
            Log.e(TAG, "Failed to switch " + NO_BLUR_OVERLAY, e);
        }
    }

    public static boolean isShadeBlurEnabled() {
        return !SystemProperties.getBoolean(PROP_DISABLE_SHADE_BLUR, false);
    }

    /** Notification shade and quick settings blur; SystemUI restarts to pick it up. */
    public static void setShadeBlur(Context context, boolean blur) {
        SystemProperties.set(PROP_DISABLE_SHADE_BLUR, blur ? "false" : "true");
        try {
            context.getSystemService(ActivityManager.class)
                    .forceStopPackage("com.android.systemui");
        } catch (Exception e) {
            Log.e(TAG, "Failed to restart SystemUI", e);
        }
    }

    private static SharedPreferences prefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.createDeviceProtectedStorageContext());
    }
}
