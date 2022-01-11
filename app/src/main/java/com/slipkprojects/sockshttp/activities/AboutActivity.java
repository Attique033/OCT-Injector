package com.slipkprojects.sockshttp.activities;

import androidx.appcompat.widget.Toolbar;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.content.pm.PackageInfo;

import com.slipkprojects.sockshttp.R;
import com.slipkprojects.sockshttp.util.Utils;
import android.view.LayoutInflater;
import com.google.android.gms.ads.AdView;
import com.slipkprojects.sockshttp.SocksHttpApp;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import android.widget.LinearLayout;
import android.content.DialogInterface;
import android.widget.Button;

public class AboutActivity extends BaseActivity implements OnClickListener {

	private Toolbar tb;
	private View changelog, license, dev;
	private AlertDialog.Builder ab;
    private TextView app_info_text;

    private AdView adsBannerView;

	private TextView new1;

	private TextView new2;

	private TextView new3;

	private TextView new4;

	private TextView new5;

	private TextView new6;

	private TextView new7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		tb = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(tb);
		changelog = findViewById(R.id.changelog);
		license = findViewById(R.id.license);
		dev = findViewById(R.id.developer);
		changelog.setOnClickListener(this);
		license.setOnClickListener(this);
		dev.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PackageInfo pinfo = Utils.getAppInfo(this);
        if (pinfo != null) {
            String version_nome = pinfo.versionName;
            int version_code = pinfo.versionCode;
            String header_text = String.format("%s (%d)", version_nome, version_code);
            app_info_text = (TextView) findViewById(R.id.appVersion);
			app_info_text.setText(header_text);
		}
        View adContainer = findViewById(R.id.load_about);
		adsBannerView = new AdView(this);
		adsBannerView.setAdSize(AdSize.BANNER);
		adsBannerView.setAdUnitId(SocksHttpApp.BANNER_AD);
		((LinearLayout)adContainer).addView(adsBannerView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adsBannerView.loadAd(adRequest);
	}

	@Override
	public void onClick(View view) {
		// TODO: Implement this method
		int id = view.getId();
		if (id == R.id.changelog) {
			changelog();
		} else if (id == R.id.license) {
			license();
		} else if (id == R.id.developer) {
			startActivity(new Intent("android.intent.action.VIEW", 
									 Uri.parse("https://t.me/OCTinjector")));
		}
	}
	
	private void changelogs() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.whats_new,null);

		builder.setView(dialogView);
		
		
		
		Button one = dialogView.findViewById(R.id.whatsnewButton);
		TextView appinfo = dialogView.findViewById(R.id.newappinfo);
		/*TextView new1 = dialogView.findViewById(R.id.new1);
		TextView new2 = dialogView.findViewById(R.id.new2);
		TextView new3 = dialogView.findViewById(R.id.new3);
		TextView new4 = dialogView.findViewById(R.id.new4);
		TextView new5 = dialogView.findViewById(R.id.new5);*/
		TextView new6 = dialogView.findViewById(R.id.new6);
		TextView new7 = dialogView.findViewById(R.id.new7);
		
		PackageInfo pinfo = Utils.getAppInfo(this);
		if (pinfo != null) {
			String version_nome = pinfo.versionName;
			int version_code = pinfo.versionCode;
		
		String newheader_text = String.format("v. %s (%d)", version_nome, version_code);
			appinfo.setText(newheader_text);
		/*new1.setText("✓New UI Designer");
		new2.setText("✓Rebuilt Tunnel Type layout.");
		new3.setText("✓Security is still secured.");
		new4.setText("✓Improved connection transportation.");
		new5.setText("NOTE : Only one unknown bug that crashes");*/
		new6.setText("- BETA Version");
		new7.setText("Thank you for downloading our app");
		
		
		final AlertDialog dialog = builder.create();


		one.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				dialog.dismiss();	
					
				}

			});

		dialog.show();

	}}
		

	private void changelog() {
		// TODO: Implement this method
		PackageInfo pinfo = Utils.getAppInfo(this);
		if (pinfo != null) {
			String version_nomes = pinfo.versionName;
			int version_codes = pinfo.versionCode;
			AlertDialog dialog = new AlertDialog.Builder(this).
				create();
			dialog.setIcon(R.drawable.app_launch);
			dialog.setTitle("Changelog");
			dialog.setMessage("v1 - BETA Version");
			dialog.setCancelable(false);
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.
																		string.ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});

			dialog.show();
		}}

	private void license() {
		// TODO: Implement this method
        startActivity(new Intent(this, LicenseActivity.class));
	}
}
