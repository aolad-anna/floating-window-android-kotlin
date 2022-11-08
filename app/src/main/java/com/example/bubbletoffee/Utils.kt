package com.uwuster.sampleapp

import android.os.Build

/**
 * Check if the application has overlay permission by default. Permission is default available for
 * SDK < 23 and must ask for SDK > 23 during runtime.
 */
fun hasDefaultOverlayPermission():Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
}