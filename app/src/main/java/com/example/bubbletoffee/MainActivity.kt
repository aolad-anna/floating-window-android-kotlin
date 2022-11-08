package com.example.bubbletoffee

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.uwuster.sampleapp.hasDefaultOverlayPermission

class MainActivity : AppCompatActivity() {
    private val startForOverlayPermission = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (!hasDefaultOverlayPermission() && !Settings.canDrawOverlays(this)) {
            displayMissingOverlayPermissionDialog()
        } else {
            startService(Intent(this, MyServiceToffee::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showButton = findViewById<Button>(R.id.showButton)
        val removeButton = findViewById<Button>(R.id.removeButton)

        showButton.setOnClickListener {
            if (!hasDefaultOverlayPermission() && !Settings.canDrawOverlays(this)) {
                requestOverlayPermission()
            } else {
                val intent = Intent(this, MyServiceToffee::class.java) // Build the intent for the service
                startService(intent)
            }
        }

        removeButton.setOnClickListener {
            val intent = Intent(this, MyServiceToffee::class.java)
            stopService(intent)
        }
    }

    /**
     * Request to grant overlay permission from settings, if not already granted
     */
    private fun requestOverlayPermission() {
        if (!hasDefaultOverlayPermission() && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startForOverlayPermission.launch(intent)
        }
    }

    private fun displayMissingOverlayPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.missing_overlay_permission_dialog_title)
            .setMessage(R.string.missing_overlay_permission_dialog_message)
            .setPositiveButton(R.string.missing_overlay_permission_dialog_positive_button) { _, _ -> requestOverlayPermission() }
            .setNegativeButton(R.string.missing_overlay_permission_dialog_negative_button) { dialog, _ -> dialog.cancel() }
            .setCancelable(true)
            .show()
    }
}