package com.example.shinelon.qqview;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a = BuildConfig.IBS;
        try{
            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            info.metaData.getString("APP_NAME_THIS");
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(),PackageManager.GET_META_DATA);
            activityInfo.metaData.getString("APP_NAME");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
