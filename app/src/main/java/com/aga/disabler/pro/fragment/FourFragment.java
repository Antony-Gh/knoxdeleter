package com.aga.disabler.pro.fragment;

import static com.aga.disabler.pro.tools.Helper.emmtoast;
import static com.aga.disabler.pro.tools.Helper.shareapp;
import static com.aga.disabler.pro.tools.Helper.uninaga;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;

import com.aga.disabler.pro.R;
import com.aga.disabler.pro.activity.AboutActivity;
import com.aga.disabler.pro.activity.GameModeActivity;
import com.aga.disabler.pro.activity.LockActivity;
import com.aga.disabler.pro.activity.LoginActivity;
import com.aga.disabler.pro.activity.PoliciesActivity;
import com.aga.disabler.pro.activity.ScrollingActivity;

import java.util.Arrays;


public class FourFragment extends PreferenceFragmentCompat implements PreferenceGroup.OnPreferenceClickListener{

    private final Activity a;

    public FourFragment(Activity act) {
        this.a = act;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_main, rootKey);
        for (String s : Arrays.asList("info", "about", "game", "signin", "lockaga", "shareaga", "uni", "policy", "whats", "email")) {
            findPreference(s).setOnPreferenceClickListener(this);
        }
    }


    @Override
    public boolean onPreferenceClick(androidx.preference.Preference preference) {
        String key = preference.getKey();
        Context cont = preference.getContext();
        switch (key){
            case "email":
                sendEmail(cont);
            break;
            case "whats":
                callme(cont);
            break;
            case "policy":
                Intent iii= new Intent();
                iii.setClass(cont, PoliciesActivity.class);
                startActivity(iii);
                break;
            case "uni":
                uninaga(a);
                break;
            case "shareaga":
                shareapp("com.aga.disabler.pro", cont);
                break;
            case "info":
                final Intent i = new Intent();
                i.setClass(cont, ScrollingActivity.class);
                startActivity(i);
                break;
            case "about":
                final Intent i2 = new Intent();
                i2.setClass(cont, AboutActivity.class);
                startActivity(i2);
                break;
            case "lockaga":
                final Intent ii2 = new Intent();
                ii2.putExtra("action", "save_pattern");
                ii2.setClass(cont, LockActivity.class);
                startActivity(ii2);
                break;
            case "game":
                final Intent ii5 = new Intent();
                ii5.setClass(cont, GameModeActivity.class);
                startActivity(ii5);
                break;
            case "signin":
                final Intent ii55 = new Intent();
                ii55.setClass(cont, LoginActivity.class);
                ii55.putExtra("st", true);
                startActivity(ii55);
                break;
        }
        return false;
    }

    public static void sendEmail(Context c) {
        try {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"knkmam05@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AGA Disabler Problem");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Help me solve this problem :- \n\n");
        emailIntent.setType("message/rfc822");
        c.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (Exception ex) {
            emmtoast("No email clients installed.",c);
        }
    }

    public static void callme(Context c) {
        Uri whatsurl = Uri.parse("https://wa.me/qr/U7UREOZBBFO5K1");
        Uri con = Uri.parse("https://wa.me/201273481309");
        Uri intg = Uri.parse("tel:01273481309");

        try{
            c.startActivity(new Intent(Intent.ACTION_VIEW, whatsurl));
        }catch(Exception e){
            try {
                c.startActivity(new Intent(Intent.ACTION_VIEW, con));
            }catch (Exception u){
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(intg);
                    c.startActivity(intent);
                }catch (Exception l){
                    emmtoast("Failed",c);
                }
            }
        }
        }

}
