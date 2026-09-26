/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.evolution.dizipen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

    private static PenMonitor sMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(intent.getAction()) || sMonitor != null) {
            return;
        }
        sMonitor = new PenMonitor(context.getApplicationContext());
        sMonitor.start();
    }
}
