/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.Toast
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Error
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 */
package com.mode;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mode.Dbhandler;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class main
extends Activity {
    SQLiteDatabase Mydatabase;
    ArrayList<String> aa;
    Dbhandler myDbHelper;
    EditText ring;
    Button save;
    EditText silent;
    EditText vibrate;

    private void FetchingData() {
        try {
            this.myDbHelper.onCreateDataBase();
        }
        catch (IOException iOException) {
            throw new Error("Unable to create database");
        }
        this.myDbHelper.openDataBase();
        this.Mydatabase = this.myDbHelper.getWritableDatabase();
        System.out.println("executed");
        return;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903041);
        this.silent = (EditText)this.findViewById(2131165190);
        this.ring = (EditText)this.findViewById(2131165189);
        this.vibrate = (EditText)this.findViewById(2131165188);
        this.save = (Button)this.findViewById(2131165193);
        this.myDbHelper = new Dbhandler((Context)this);
        main.super.FetchingData();
        this.Mydatabase = this.myDbHelper.getReadableDatabase();
        this.aa = this.myDbHelper.getModes(this.Mydatabase);
        System.out.println("values of Db      " + this.aa);
        String string2 = this.aa.toString();
        String[] arrstring = string2.substring(1, -1 + string2.length()).split(",");
        this.silent.setText((CharSequence)arrstring[0].trim());
        this.ring.setText((CharSequence)arrstring[1].trim());
        this.vibrate.setText((CharSequence)arrstring[2].trim());
        this.save.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                String string2 = main.this.silent.getText().toString();
                String string3 = main.this.ring.getText().toString();
                String string4 = main.this.vibrate.getText().toString();
                if (string2.equals((Object)"") || string3.equals((Object)"") || string4.equals((Object)"")) {
                    Toast.makeText((Context)main.this.getApplicationContext(), (CharSequence)"Please fill all fields", (int)60).show();
                    return;
                }
                main.this.myDbHelper = new Dbhandler((Context)main.this);
                main.this.FetchingData();
                main.this.Mydatabase = main.this.myDbHelper.getReadableDatabase();
                main.this.Mydatabase.execSQL("update mode set text='" + string2 + "' where Mode='silent'");
                System.out.println("^^^^^^^^^^^^^^^ inserted value is   " + string2);
                main.this.Mydatabase.execSQL("update mode set text='" + string3 + "' where Mode='ring'");
                main.this.Mydatabase.execSQL("update mode set text='" + string4 + "' where Mode='vibrate'");
                Toast.makeText((Context)main.this.getApplicationContext(), (CharSequence)"saved", (int)150).show();
            }
        });
    }

}

