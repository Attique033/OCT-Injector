package com.slipkprojects.sockshttp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.slipkprojects.sockshttp.R;
import com.slipkprojects.sockshttp.preference.SettingsPreference;
import com.slipkprojects.ultrasshservice.util.SkProtect;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.Preference;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import com.slipkprojects.sockshttp.preference.SettingsSSHPreference;
import com.slipkprojects.sockshttp.preference.SlowDNSPreference;

public class ConfigGeralActivity extends BaseActivity
	implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback
{
	public static String OPEN_SETTINGS_SSH = "openSSHScreen";
	public static String OPEN_SETTINGS_DNS = "openDNSScreen";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);

		PreferenceFragmentCompat preference = new SettingsPreference();
		Intent intent = getIntent();
		
		String action = intent.getAction();
		if (action != null && action.equals(OPEN_SETTINGS_SSH)) {
			setTitle(R.string.settings_ssh);
			preference = new SettingsSSHPreference();
		} else if (action != null && action.equals(OPEN_SETTINGS_DNS)) {
			setTitle(R.string.slowdns_configuration);
			preference = new SlowDNSPreference();
		}
		
		// add preference settings
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_configLinearLayout, preference)
			.commit();

		// toolbar
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		SkProtect.CharlieProtect();
	}
	
	@Override
	public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
		// Instantiate the new Fragment
		final Bundle bundle = pref.getExtras();
		final Fragment fragment = Fragment.instantiate(this, pref.getFragment(), bundle);
        
		fragment.setTargetFragment(caller, 0);
       
		// Replace the existing Fragment with the new Fragment
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_configLinearLayout, fragment)
			.addToBackStack(null)
			.commit();
		
		return true;
	}

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();
		return true;
	}
}

