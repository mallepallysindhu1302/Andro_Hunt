/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.System
 *  java.lang.Thread
 */
package com.mode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.mode.main;

public class SplashUI
extends Activity {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (!(view instanceof ViewGroup)) return;
        int n = 0;
        do {
            if (n >= ((ViewGroup)view).getChildCount()) {
                ((ViewGroup)view).removeAllViews();
                return;
            }
            SplashUI.super.unbindDrawables(((ViewGroup)view).getChildAt(n));
            ++n;
        } while (true);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903040);
        new Thread(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public void run() {
                n = 0;
                do {
                    if (n < 2000) ** GOTO lbl8
                    intent = new Intent((Context)SplashUI.this, main.class);
                    SplashUI.this.startActivity(intent);
                    return;
lbl8: // 1 sources:
                    1.sleep((long)100L);
                    n += 100;
                    continue;
                    break;
                } while (true);
                catch (Exception exception) {
                    exception.printStackTrace();
                    return;
                }
                finally {
                    SplashUI.this.finish();
                }
            }
        }.start();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.unbindDrawables(this.findViewById(2131165184));
        System.gc();
    }

}

