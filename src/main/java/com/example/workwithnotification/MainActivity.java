package com.example.workwithnotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button notification_button = findViewById(R.id.notification_button);

        notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = MainActivity.this; NotificationChannel newnotchan= null; if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    newnotchan= new NotificationChannel("mychannel1","mychannel",NotificationManager
                            .IMPORTANCE_HIGH);
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build();

                    newnotchan.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+"/"+R.raw.al_heylisten), audioAttributes);
                }
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(newnotchan);
                }
                Notification notification = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    notification = new Notification.Builder(context,"mychannel1")
                            .setContentTitle("Hi from Alex")
                            .setContentText("Hey, listen!")
                            .setTicker("new notification!")
                            .setChannelId("mychannel1")
                            .setSmallIcon(android.R.drawable.ic_dialog_alert)
                            .setOngoing(true)
                            .build();
                }



                notificationManager.notify(0, notification);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this); try {
            dialog.setMessage(getTitle().toString()+ " версия "+ getPackageManager().getPackageInfo(getPackageName(),0).versionName +
                    "\r\n\nПрограмма с примером выполнения диалогового окна\r\n\n Автор - Гержа Александр Геннадьевич, гр. 20ИТ-1");
        } catch (PackageManager.NameNotFoundException e) { e.printStackTrace();

        }
        dialog.setTitle("О программе"); dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.dismiss();
            }
        });
        dialog.setIcon(R.mipmap.ic_launcher_round); AlertDialog alertDialog = dialog.create(); alertDialog.show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}