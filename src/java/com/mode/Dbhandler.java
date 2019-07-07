/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.PrintStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 */
package com.mode;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Dbhandler
extends SQLiteOpenHelper {
    private static String DB_NAME;
    private static String DB_PATH;
    private static String TABLENAME;
    String[] a;
    Cursor c1;
    Cursor c2;
    private SQLiteDatabase db;
    private final Context myContext;
    private SQLiteDatabase myDataBase;
    int trainid;

    static {
        DB_PATH = "/data/data/com.mode/databases/";
        DB_NAME = "Ringmode.sqlite";
        TABLENAME = "mode";
    }

    public Dbhandler(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean checkDatabase() {
        SQLiteDatabase sQLiteDatabase;
        block2 : {
            try {
                SQLiteDatabase sQLiteDatabase2;
                sQLiteDatabase = sQLiteDatabase2 = SQLiteDatabase.openDatabase((String)(String.valueOf((Object)DB_PATH) + DB_NAME), null, (int)0);
                if (sQLiteDatabase == null) break block2;
            }
            catch (Exception exception) {
                return false;
            }
            sQLiteDatabase.close();
        }
        boolean bl = false;
        if (sQLiteDatabase == null) return bl;
        return true;
    }

    private void copyDataBase() throws IOException {
        InputStream inputStream = this.myContext.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf((Object)DB_PATH) + DB_NAME);
        byte[] arrby = new byte[1024];
        do {
            int n;
            if ((n = inputStream.read(arrby)) <= 0) {
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                return;
            }
            fileOutputStream.write(arrby, 0, n);
        } while (true);
    }

    public void close() {
        Dbhandler dbhandler = this;
        synchronized (dbhandler) {
            if (this.myDataBase != null) {
                this.myDataBase.close();
            }
            super.close();
            return;
        }
    }

    public ArrayList<String> getModes(SQLiteDatabase sQLiteDatabase) {
        System.out.println("$$$$$$$$$$$$########" + DB_NAME);
        System.out.println("$$$$$$$$$$$$########" + (Object)this.myDataBase);
        ArrayList arrayList = new ArrayList();
        try {
            Cursor cursor = sQLiteDatabase.rawQuery(" SELECT * FROM mode ", null);
            if (cursor.moveToFirst()) {
                do {
                    arrayList.add((Object)cursor.getString(cursor.getColumnIndex("text")));
                } while (cursor.moveToNext());
            }
            cursor.close();
            return arrayList;
        }
        catch (SQLiteException sQLiteException) {
            this.myDataBase.close();
            return arrayList;
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public int onCreateDataBase() throws IOException {
        if (this.checkDatabase()) {
            return 0;
        }
        System.out.println("onCreateDataBase method execution starts");
        this.getReadableDatabase();
        this.copyDataBase();
        return 1;
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    public void openDataBase() throws SQLException {
        this.myDataBase = SQLiteDatabase.openDatabase((String)(String.valueOf((Object)DB_PATH) + DB_NAME), null, (int)1);
    }
}

