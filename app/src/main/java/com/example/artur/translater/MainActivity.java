package com.example.artur.translater;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<String> languagesList = new ArrayList<String>();
    private String mLanguages = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter langFilter = new IntentFilter( TranslatorIntentService.ALL_LANGUAGES_INTENT );
        LanguageResponseReceiver languagesReceiver = new LanguageResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(languagesReceiver, langFilter);

        /*Intent translatorServiceIntent = new Intent(MainActivity.this, TranslatorIntentService.class);
        translatorServiceIntent.putExtra(TranslatorIntentService.ACTION_GET_ALL, "");
        startService(translatorServiceIntent);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ProgressBar bar = (ProgressBar)findViewById(R.id.progressBar);
        //bar.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        /*Intent intent = new Intent(MainActivity.this, TranslatorActivity.class);
        intent.putExtra("Languages", languagesList);
        startActivity(intent);*/
        Intent translatorServiceIntent = new Intent(MainActivity.this, TranslatorIntentService.class);
        translatorServiceIntent.putExtra(TranslatorIntentService.ACTION_GET_ALL, "");
        startService(translatorServiceIntent);
    }

    private class LanguageResponseReceiver extends BroadcastReceiver
    {
        private LanguageResponseReceiver() {

        }
        public void onReceive(Context context, Intent intent) {
            mLanguages = intent.getStringExtra(TranslatorIntentService.RETURN_LANGUAGES);
            Intent i = new Intent(MainActivity.this, TranslatorActivity.class);
            i.putExtra(TranslatorActivity.EXTR_LANGUAGE_JSON, mLanguages);
            startActivity(i);
        }
    }
}
