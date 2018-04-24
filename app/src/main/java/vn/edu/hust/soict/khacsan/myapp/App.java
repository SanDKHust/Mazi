package vn.edu.hust.soict.khacsan.myapp;


import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        Hawk.init(this).build();
    }
}
