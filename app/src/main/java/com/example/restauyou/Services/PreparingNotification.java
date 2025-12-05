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

public class PreparingNotification extends Service {
    private static final String TAG = "PreparingNotification";
    private static final String CHANNEL_ID = "Order Notification";
    private ExecutorService executor;

    @Override
    public void onCreate() {
        Log.d(TAG, "The notification is created.");

        super.onCreate();
        createNotificationChannel();
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Get a notification id
        final int notificationId =  (int) (Math.random() * 10000);

        // Intent & Pending Intent
        Intent iGo = new Intent(this, AdminHomePageActivity.class);
        iGo.putExtra("NOTIFICATION_ID", notificationId);
        iGo.setAction("ACTION_REDIRECT_ORDER_MANAGEMENT");

        PendingIntent pI = PendingIntent.getActivity(
                this,
                notificationId,
                iGo,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Build notification
        Notification noti = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Preparing Order")
                .setContentText("The order is being prepared.")
                .setSmallIcon(R.drawable.baseline_fastfood_24)
                .setContentIntent(pI).build();

        // Start foreground
        startForeground(notificationId, noti);

        // Check if sound is enabled
        boolean sound = intent.getBooleanExtra("soundStatus", true);
        if (sound)
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    // Start media
                    MediaPlayer mediaPlayer = MediaPlayer.create(PreparingNotification.this, R.raw.preparing_notification);
                    mediaPlayer.start();

                    // Wait 10 seconds
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Destroy media
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    mediaPlayer.release();
                    stopSelf();
                }
            });
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "The notification is destroyed.");
        stopForeground(true);

        // Stop thread(s)
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
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Order Status", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Admin: Order Status Notification Channel");
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        else
            Log.d(TAG, "The notification channel is not created due to an old version of Android.");
    }
}
