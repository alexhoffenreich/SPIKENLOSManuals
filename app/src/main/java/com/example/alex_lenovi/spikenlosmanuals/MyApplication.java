package com.example.alex_lenovi.spikenlosmanuals;

/**
 * Created by alex-lenovi on 7/26/2016.
 */

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

//import android.os.PowerManager;

/**
 * This custom class is used to Application level things.
 *
 * @author Chintan Rathod (http://www.chintanrathod.com)
 */
public class MyApplication extends Application {

    public static MyApplication instance;
    //private PowerManager.WakeLock wakeLock;
    //private OnScreenOffReceiver onScreenOffReceiver;
    private static Context mContext;

/*    private boolean enable_wifi;

    public boolean isEnable_wifi() {
        return enable_wifi;
    }

    public void setEnable_wifi(boolean enable_wifi) {
        this.enable_wifi = enable_wifi;
    }*/

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //enable_wifi = false;

        mContext = getApplicationContext();
        instance = this;
        /*final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                ha.postDelayed(this, 10000);
            }
        }, 10000);*/
        //registerKioskModeScreenOffReceiver();
        //startKioskService();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

/*
    public  void disablePermissions(Activity cur_act) {

        // Hide the status bar.
        final Activity cur_activity = cur_act;
        cur_act.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cur_act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View decorView = cur_act.getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;// | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        Timer timer = new Timer();
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                cur_activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        decorView.setSystemUiVisibility(uiOptions);
                                        // Disable Wifi
                                        if (!enable_wifi){
                                            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                                            wifiManager.setWifiEnabled(false);

                                        }


                                        // Disable bluetooth

                                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                        if (mBluetoothAdapter.isEnabled()) mBluetoothAdapter.disable();

                                        // disable nfc

                                        //disableNFC();

                                    }
                                });
                            }
                        };

                        timer.scheduleAtFixedRate(task, 0, 1);
                    }
                });



        // Restart app if crushes
        //Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(cur_activity));



    }
*/




 /*   public static boolean disableNFC() {
        boolean success = false;
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(mContext);
        String TAG = "NFC";
        if (nfcAdapter != null) {
            Class<?> NfcManagerClass;
            Method setNfcEnabled;

            try {
                NfcManagerClass = Class.forName(nfcAdapter.getClass().getName());
                setNfcEnabled = NfcManagerClass.getDeclaredMethod("disable");
                setNfcEnabled.setAccessible(true);
                success = (Boolean) setNfcEnabled.invoke(nfcAdapter);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }

        return success;
    }*/
/*
    private void registerKioskModeScreenOffReceiver() {
        // register screen off receiver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        onScreenOffReceiver = new OnScreenOffReceiver();
        registerReceiver(onScreenOffReceiver, filter);
    }

    public PowerManager.WakeLock getWakeLock() {
        if(wakeLock == null) {
            // lazy loading: first call, create wakeLock via PowerManager.
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
        }
        return wakeLock;
    }


    private void startKioskService() { // ... and this method
        startService(new Intent(this, KioskService.class));
    }*/

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
