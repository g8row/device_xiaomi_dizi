/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.settings.launcher;

import android.os.Bundle;

import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreferenceCompat;

import org.lineageos.settings.R;

public class LauncherEffectsFragment extends PreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Device-protected: the boot receiver applies the choice before unlock.
        getPreferenceManager().setStorageDeviceProtected();
        setPreferencesFromResource(R.xml.launcher_effects_settings, rootKey);

        // Pixel Launcher only; Trebuchet has its own (Home settings > Visual effects).
        SwitchPreferenceCompat blur = findPreference(LauncherEffects.KEY_LAUNCHER_BLUR);
        if (LauncherEffects.hasPixelLauncher(getContext())) {
            blur.setOnPreferenceChangeListener((pref, newValue) -> {
                LauncherEffects.apply(getContext(), (Boolean) newValue);
                return true;
            });
        } else {
            getPreferenceScreen().removePreference(blur);
        }

        // Not persisted: the property is the state.
        SwitchPreferenceCompat shade = findPreference(LauncherEffects.KEY_SHADE_BLUR);
        shade.setChecked(LauncherEffects.isShadeBlurEnabled());
        shade.setOnPreferenceChangeListener((pref, newValue) -> {
            LauncherEffects.setShadeBlur(getContext(), (Boolean) newValue);
            return true;
        });
    }
}
