package com.aga.disabler.pro.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.aga.disabler.pro.R;
import com.aga.disabler.pro.adapters.pageradapter;
import com.aga.disabler.pro.license.ExecutorServiceII;
import com.aga.disabler.pro.tools.appinfo;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.aga.disabler.pro.tools.Helper.getpkgname;

public class AppinfoActivity extends  AppCompatActivity implements ExecutorServiceII.Tasks {
	
	
	private String pkg = "";
	private String appname = "";
	private ViewPager viewPager;
	private LinearLayout linall;
	private LinearLayout linload2;
	private pageradapter adapter;
	private TabLayout tabLayout;
	private ImageView img;
	private TextView te1;
	private TextView te2;
	private Drawable image;
	private ExecutorServiceII exe;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.appinfo);
		initialize();
		//setupWindowAnimations();
	}

	/*
	private void setupWindowAnimations() {
		getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.activity_fade));
		getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.activity_slide));
	}
	 */

	@Override
	public void onConfigurationChanged(@NotNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		initialize();
	}

	
	private void initialize() {
		linall = findViewById(R.id.linear_all);
		linall.setVisibility(View.INVISIBLE);
		prog();
		Context c = AppinfoActivity.this.getApplicationContext();
		exe = new ExecutorServiceII.ExecutorBuilder().setTasks(this).build();
		pkg = getpkgname(c);
		linload2 = findViewById(R.id.load_linear);
		viewPager = findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(10);
		tabLayout = findViewById(R.id.tabs);
		img = findViewById(R.id.imageview1);
		te1 = findViewById(R.id.appname1);
		te2 = findViewById(R.id.pkgname1);
		exe.execute();
	}

	@Override
	public void doinbackground() {
		appinfo appinfoo = new appinfo(this, pkg);
		HashMap<Object, Object> ob = appinfoo.getsomeappinfo();
		appname = (String) ob.get("name");
		image = (Drawable) ob.get("icon");
		adapter = new pageradapter(this, getSupportFragmentManager(), pkg , AppinfoActivity.this);
	}

	@Override
	public void onpreexecute() {
	}

	@Override
	public void onpostexecute() {
		img.setImageDrawable(image);
		te1.setText(appname);
		te2.setText(pkg);
		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);
		Timer tt = new Timer();
		TimerTask t1 = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(() -> {
					linall.setVisibility(View.VISIBLE);
					progress.dismiss();
				});
			}
		};
		tt.schedule(t1, (1000));
	}

	private void prog() {
		progress = new ProgressDialog(this);
		progress.setCancelable(false);
		progress.setCanceledOnTouchOutside(false);
		progress.setIndeterminate(true);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Loading....");
		progress.show();
	}

}
