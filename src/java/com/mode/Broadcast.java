/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.location.Location
 *  android.media.AudioManager
 *  android.os.Bundle
 *  android.telephony.SmsManager
 *  android.telephony.SmsMessage
 *  android.util.Log
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.Error
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package com.mode;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import com.mode.AppLocationService;
import com.mode.Dbhandler;
import java.io.IOException;
import java.io.PrintStream;

public class Broadcast
extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    SQLiteDatabase Mydatabase;
    AppLocationService appLocationService;
    Context context = null;
    SQLiteDatabase db;
    private AudioManager maudio;
    Dbhandler myDbHelper;

    private void FetchingData() {
        try {
            this.myDbHelper.onCreateDataBase();
        }
        catch (IOException iOException) {
            throw new Error("Unable to create database");
        }
        this.myDbHelper.openDataBase();
        this.db = this.myDbHelper.getReadableDatabase();
        System.out.println("executed");
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void changemode(String string2, String string3) {
        if (string2 == null) {
            System.out.println("^&%&^  I LOVE YOU");
            return;
        } else {
            if (string2.equals((Object)"silent")) {
                System.out.println("The phone state is changing to silent mode");
                int n = this.maudio.getRingerMode();
                System.out.println("The phone state is changing to silent mode " + n);
                this.maudio.setRingerMode(0);
                Location location = this.appLocationService.getLocation("network");
                if (location != null) {
                    double d = location.getLatitude();
                    double d2 = location.getLongitude();
                    SmsManager.getDefault().sendTextMessage(string3, null, "latitude :" + d + "longitude :" + d2, null, null);
                }
                Log.i((String)TAG, (String)"Change to Silent");
                return;
            }
            if (string2.equals((Object)"ring")) {
                System.out.println("The phone state is changing to ring mode");
                this.maudio.setRingerMode(2);
                this.maudio.adjustVolume(1, 4);
                Location location = this.appLocationService.getLocation("network");
                if (location != null) {
                    double d = location.getLatitude();
                    double d3 = location.getLongitude();
                    SmsManager.getDefault().sendTextMessage(string3, null, "latitude :" + d + "longitude :" + d3, null, null);
                }
                System.out.println("volume increased..");
                return;
            }
            if (!string2.equals((Object)"vibrate")) return;
            {
                System.out.println("The phone state is changing to vibrate mode");
                this.maudio.setRingerMode(1);
                Location location = this.appLocationService.getLocation("network");
                if (location != null) {
                    double d = location.getLatitude();
                    double d4 = location.getLongitude();
                    SmsManager.getDefault().sendTextMessage(string3, null, "latitude :" + d + "longitude :" + d4, null, null);
                }
                Log.i((String)TAG, (String)"Changed to Vibrate");
                return;
            }
        }
    }

    private String getMode(String string2, Context context) {
        this.myDbHelper = new Dbhandler(context);
        Broadcast.super.FetchingData();
        System.out.println("inside getmodde");
        this.Mydatabase = this.myDbHelper.getReadableDatabase();
        Cursor cursor = this.Mydatabase.rawQuery("SELECT Mode FROM mode where text='" + string2 + "' ", null);
        cursor.moveToFirst();
        String string3 = null;
        if (cursor != null) {
            System.out.println("column index is..");
            int n = cursor.getColumnIndex("Mode");
            int n2 = cursor.getCount();
            string3 = null;
            if (n2 > 0) {
                string3 = cursor.getString(n).toString();
            }
            System.out.println("mode in getmode.." + string3);
        }
        return string3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onReceive(Context context, Intent intent) {
        this.appLocationService = new AppLocationService(context);
        Log.i((String)TAG, (String)("Intent recieved: " + intent.getAction()));
        this.maudio = (AudioManager)context.getSystemService("audio");
        if (intent.getAction().equals((Object)SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            Log.i((String)TAG, (String)"Message recieved: ");
            Object[] arrobject = (Object[])bundle.get("pdus");
            SmsMessage[] arrsmsMessage = new SmsMessage[arrobject.length];
            int n = 0;
            do {
                if (n >= arrobject.length) {
                    if (arrsmsMessage.length <= -1) return;
                    Log.i((String)TAG, (String)("Message recieved: " + arrsmsMessage[0].getMessageBody()));
                    return;
                }
                arrsmsMessage[n] = SmsMessage.createFromPdu((byte[])((byte[])arrobject[n]));
                System.out.println("message is.." + (Object)arrsmsMessage[n]);
                Log.i((String)TAG, (String)("Message recieved: " + arrsmsMessage[0].getMessageBody()));
                String string2 = arrsmsMessage[0].getMessageBody();
                String string3 = arrsmsMessage[0].getOriginatingAddress();
                System.out.println("message body is.." + string2);
                String string4 = Broadcast.super.getMode(string2, context);
                System.out.println("mode is...." + string4);
                Broadcast.super.changemode(string4, string3);
                ++n;
            } while (true);
        }
        Log.i((String)TAG, (String)"in else... ");
    }
}

