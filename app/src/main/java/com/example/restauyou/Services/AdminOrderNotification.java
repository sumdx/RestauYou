package com.example.restauyou.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.restauyou.AdminHomePageActivity;
import com.example.restauyou.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdminOrderNotification extends Service {
    private static final String TAG = "AdminOrderNotification";
    private static final String CHANNEL_ID = "Order ALERT";
    private static final int NOTIFICATION_ID = 1001;
    private ExecutorService executor;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        Log.d(TAG, "The notification is created.");

        super.onCreate();
        createNotificationChannel();
        mediaPlayer = MediaPlayer.create(this, R.raw.order_notifcation_alert);
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Intent & Pending Intent
        Intent iGo = new Intent(this, AdminHomePageActivity.class);
        iGo.setAction("ACTION_REDIRECT_ORDER_MANAGEMENT");
        iGo.putExtra("NOTIFICATION_ID", NOTIFICATION_ID);

        PendingIntent pI = PendingIntent.getActivity(
                this,
                NOTIFICATION_ID,
                iGo,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Build notification
        Notification noti = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Order Alert")
                .setContentText("You have a new order! Click here to check!")
                .setSmallIcon(R.drawable.baseline_fastfood_24)
                .setContentIntent(pI).build();

        // Start foreground
        startForeground(NOTIFICATION_ID, noti);

        // Check if sound is enabled
        boolean sound = intent.getBooleanExtra("soundStatus", true);
        if (sound) {
            // Start media
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.start();
                }
            });
        }
        else {
            getSystemService(NotificationManager.class).cancel(NOTIFICATION_ID);
            stopForeground(false);
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "The notification is destroyed.");
        stopForeground(true);

        // Stop media
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
        }

        // Stop thread
        if (executor != null && !executor.isShutdown())
            executor.shutdown();

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Order Alert", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Admin: Order Alert Channel");
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        else
            Log.d(TAG, "The notification channel is not created due to an old version of Android.");
    }
}
