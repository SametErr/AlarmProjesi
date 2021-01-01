package com.example.alarmprojesi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.banner.BannerView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    InterstitialAd interstitialAd; //GLOBAL TANIMLADIK

    TimePicker alarmTime;
    TextClock currentTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //----------------------------REKLAM----------------------

        HwAds.init(this); //HMS ads servisini tetikledik.
        loadBannerAdd(); //yüklenirken banneri yükledik
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdId("testb4znbuh3n2");//video test id ekledik.
        interstitialAd.setAdListener(adListener);
        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);

        alarmTime=findViewById(R.id.timePicker); //time picker ve text clock nesneleri çağırdık.
        currentTime=findViewById(R.id.textClock);

        final Ringtone r= RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        Timer t=new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (currentTime.getText().toString().equals(AlarmTime())){
                    r.play();
                }
                else{
                    r.stop();
                }
            }
        },0, 1000);


    }

    public String AlarmTime(){

        Integer alarmHours=alarmTime.getCurrentHour();
        Integer alarmMinute=alarmTime.getCurrentMinute();

        String stringAlarmTime;

        if (alarmHours>12){
            alarmHours=alarmHours-12;
            stringAlarmTime=alarmHours.toString().concat(":").concat(alarmMinute.toString().concat(" PM"));
        }
        else{
            stringAlarmTime=alarmHours.toString().concat(":").concat(alarmMinute.toString().concat(" AM"));
        }

        return  stringAlarmTime;
    }

    public void loadBannerAdd() {
        //ad parmeter objesini oluşturduk
        AdParam adParam = new AdParam.Builder().build();
        //banner view oluşturduk.
        BannerView bannerView = new BannerView(this);
        // Reklam ıd miz
        bannerView.setAdId("testw6vs28auh3");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        // Add BannerView to the layout.
        //Reklamın gözükeceği layout eriştik.
        RelativeLayout rootView = findViewById(R.id.Bannerüst);
        rootView.addView(bannerView);

        bannerView.loadAd(adParam);
    } //Reklam kodları

    private final AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            Toast.makeText(MainActivity.this, "Reklam Yüklendi", Toast.LENGTH_SHORT).show();
            // Display an interstitial ad.
            showInterstitial();
        }

        @Override
        public void onAdFailed(int errorCode) {
            Toast.makeText(MainActivity.this, "Reklam yüklemesi hata koduyla başarısız oldu: " + errorCode,
                    Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Reklam yüklemesi hata koduyla başarısız oldu: " + errorCode);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            Log.d("TAG", "Kapanan Reklam");
        }

        @Override
        public void onAdClicked() {
            Log.d("TAG", "Tıklanan Reklam");
            super.onAdClicked();
        }

        @Override
        public void onAdOpened() {
            Log.d("TAG", "Açılan Reklam");
            super.onAdOpened();
        }
    };//Reklam kodları

    private void showInterstitial() {

        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(this, "Reklam Yüklenmedi", Toast.LENGTH_SHORT).show();
        }
    }//Reklam kodları

    //---------------PUSH RECİEVER-----



}