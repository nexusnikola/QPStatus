package com.nikola.qpstatus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MyBaseActivity extends Activity {
    
    public static Context context;
    public static Activity activity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        activity = this;
    }
    
    protected void onResume() {
        super.onResume();
        context = this.getApplicationContext();
        activity = this;
    }
    
    protected void onPause() {
        context = null;
        activity = null;
        super.onPause();
    }
    
    protected void onDestroy() {        
        context = null;
        activity = null;
        super.onDestroy();
    }

}
