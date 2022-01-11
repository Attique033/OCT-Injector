package com.slipkprojects.sockshttp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.slipkprojects.sockshttp.activities.ConfigGeralActivity;
import com.slipkprojects.sockshttp.util.IpHunter;
import com.slipkprojects.sockshttp.util.Utils;
import com.slipkprojects.ultrasshservice.util.ToastUtil;

public class DrawerPanelMain
	implements NavigationView.OnNavigationItemSelectedListener
{
	private AppCompatActivity mActivity;
	
	public DrawerPanelMain(AppCompatActivity activity) {
		mActivity = activity;
	}


	
	
	
	private ToastUtil toastutil;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle toggle;

	public void setDrawer(Toolbar toolbar) {
		NavigationView drawerNavigationView = (NavigationView) mActivity.findViewById(R.id.drawerNavigationView);
		drawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawerLayoutMain);

		// set drawer
		toggle = new ActionBarDrawerToggle(mActivity,
			drawerLayout, toolbar, R.string.open, R.string.cancel);

        drawerLayout.setDrawerListener(toggle);

		toggle.syncState();

		toastutil = new ToastUtil(mActivity);
		
		// set app info
		PackageInfo pinfo = Utils.getAppInfo(mActivity);
		if (pinfo != null) {
			String version_nome = pinfo.versionName;
			int version_code = pinfo.versionCode;
			String header_text = String.format("v. %s (%d)", version_nome, version_code);

			View view = drawerNavigationView.getHeaderView(0);

			TextView app_info_text = view.findViewById(R.id.nav_headerAppVersion);
			app_info_text.setText(header_text);
		}

		// set navigation view
		drawerNavigationView.setNavigationItemSelectedListener(this);
	}
	
	public ActionBarDrawerToggle getToogle() {
		return toggle;
	}
	
	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();

		switch(id)
		{
			case R.id.miPhoneConfg:
				PackageInfo app_info = Utils.getAppInfo(mActivity);
				if (app_info != null) {
					String aparelho_marca = Build.BRAND.toUpperCase();

					if (aparelho_marca.equals("HTC") || aparelho_marca.equals("HUAWEY")) {
						toastutil.showErrorToast("Function not supported by your device");
							
						
					}
					else {
						try {
							Intent in = new Intent(Intent.ACTION_MAIN);
							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							in.setClassName("com.android.settings", "com.android.settings.RadioInfo");
							mActivity.startActivity(in);
						} catch(Exception e) {
							Toast.makeText(mActivity, R.string.error_no_supported, Toast.LENGTH_SHORT)
								.show();
						}
					}
				}
				break;
				
				case R.id.iphunter2:
				Intent iphunterintent = new Intent(mActivity, IpHunter.class);
				iphunterintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(iphunterintent);
				break;
				
		    /*case R.id.hostchecker2:
				Intent hostcheckerintent = new Intent(mActivity, HostChecker.class);
				hostcheckerintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(hostcheckerintent);
				break;*/
			

			case R.id.miSettings:
				Intent intent = new Intent(mActivity, ConfigGeralActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(intent);
				break;
				
			case R.id.miSettingsSSH:
				Intent intent2 = new Intent(mActivity, ConfigGeralActivity.class);
				intent2.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
				intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mActivity.startActivity(intent2);
				break;
				
			
		}

		return true;
	}
	
}
