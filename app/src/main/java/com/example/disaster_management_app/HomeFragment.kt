package com.example.disaster_management_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.maps.MapView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {
    private lateinit var mapView: MapView
    private var ringtone: Ringtone? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = true
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab_reportIncident1)

        fab.setOnClickListener {
            Toast.makeText(requireContext(), "FAB clicked", Toast.LENGTH_SHORT).show()
            triggerEmergencyAlarm()
            showEmergencyNotification()
            showIncidentDialog()
        }

        return view
    }

    override fun onResume() { super.onResume(); mapView.onResume() }
    override fun onPause() { super.onPause(); mapView.onPause() }
    override fun onDestroy() { super.onDestroy(); mapView.onDestroy() }

    private fun triggerEmergencyAlarm() {
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(requireContext(), alarmUri)
        ringtone?.play()
    }


    private fun stopAlarm() {
        ringtone?.stop()
    }

    private fun showIncidentDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Incident Reported")
            .setMessage("An emergency incident has been reported.")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                stopAlarm()
                dialog.dismiss()
            }
            .show()
    }

    private fun showEmergencyNotification() {
        val channelId = "emergency_channel"
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Emergency Alerts", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Emergency Alert Notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Emergency Alert")
            .setContentText("An emergency has been reported!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }

}