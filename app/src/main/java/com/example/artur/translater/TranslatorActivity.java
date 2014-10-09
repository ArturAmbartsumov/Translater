package com.example.artur.translater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class TranslatorActivity extends FragmentActivity implements SecondFragment.OnFragmentInteractionListener,
                                                                    FirstFragment.OnFragmentInteractionListener,
                                                                    LanguageFragment.OnFragmentInteractionListener{

    private ArrayList<String> mLanguageList = new ArrayList<String>();
    private Translator mTranslator;
    private String mLanguageJSON;

    private static final String LOG_TAG = TranslatorActivity.class.getName();
    public static final String EXTR_LANGUAGE_JSON = "Languages";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        Intent I = getIntent();
        mLanguageJSON = I.getStringExtra(EXTR_LANGUAGE_JSON);
        mTranslator = new Translator(this, mLanguageJSON);


        IntentFilter trFilter = new IntentFilter( TranslatorIntentService.TRANSLATE_INTENT );
        TranslateResponseReceiver translateReceiver = new TranslateResponseReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(translateReceiver, trFilter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FirstFragment firstFragment = (FirstFragment)fragmentManager.findFragmentById(R.id.firstFragment);
        SecondFragment secondFragment = (SecondFragment)fragmentManager.findFragmentById(R.id.secondFragment);
        firstFragment.setRetainInstance(true);
        secondFragment.setRetainInstance(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.translator, menu);
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
    public void onTranslateButtonClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FirstFragment firstFragment = (FirstFragment)fragmentManager.findFragmentById(R.id.firstFragment);
        String originText = firstFragment.getOriginText();
        String language1 = firstFragment.getLanguage1();
        String language2 = firstFragment.getLanguage2();
        //Toast.makeText(this, language1+language2, Toast.LENGTH_SHORT).show();
        mTranslator.translate(originText, language1, language2);


    }

    @Override
    public void OnShowLanguageList(int buttonId) {
        //Toast.makeText(this, "Ура", Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();

        LanguageFragment buff = (LanguageFragment)fragmentManager.findFragmentByTag(LanguageFragment.LANGUAGE_LIST_FRAGMENT_TAG);
        if (buff == null || !buff.isAdded()) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            SecondFragment secondFragment = (SecondFragment)fragmentManager.findFragmentById(R.id.secondFragment);
            fragmentTransaction.hide(secondFragment);

            LanguageFragment languageFragment = new LanguageFragment();
            //languageFragment.setRetainInstance(true);

            Bundle args = new Bundle();
            args.putStringArrayList(LanguageFragment.ARG_LANGUAGE_LIST, mTranslator.getAllLanguages());
            args.putInt(LanguageFragment.ARG_BUTTON_INDEX, buttonId);
            languageFragment.setArguments(args);

            fragmentTransaction.add(R.id.translatorLayout, languageFragment, LanguageFragment.LANGUAGE_LIST_FRAGMENT_TAG);
            fragmentTransaction.addToBackStack("List");
            fragmentTransaction.setCustomAnimations(
                    android.R.animator.fade_in, android.R.animator.fade_out);
            //LinearLayout linearLayout = languageFragment.getView().findViewById(R.id.)

            fragmentTransaction.commit();
        }
    }

    @Override
    public void onLanguageSelect(String language, int buttonId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FirstFragment firstFragment = (FirstFragment)fragmentManager.findFragmentById(R.id.firstFragment);
        firstFragment.setLanguage(language, buttonId);

        LanguageFragment languageFragment = (LanguageFragment)fragmentManager.findFragmentByTag(LanguageFragment.LANGUAGE_LIST_FRAGMENT_TAG);
        if (languageFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(languageFragment);


            SecondFragment secondFragment = (SecondFragment)fragmentManager.findFragmentById(R.id.secondFragment);
            fragmentTransaction.show(secondFragment);

            fragmentTransaction.setCustomAnimations(
                    android.R.animator.fade_in, android.R.animator.fade_out);
            fragmentTransaction.commit();
            fragmentManager.popBackStack();
        }
    }

    private class TranslateResponseReceiver extends BroadcastReceiver
    {
        private TranslateResponseReceiver() {

        }
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra(TranslatorIntentService.RETURN_TRANSLATE);
            //Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FirstFragment firstFragment = (FirstFragment)fragmentManager.findFragmentById(R.id.firstFragment);
            SecondFragment secondFragment = (SecondFragment)fragmentManager.findFragmentById(R.id.secondFragment);
            secondFragment.setTranslatedText(s);
        }
    }
}
