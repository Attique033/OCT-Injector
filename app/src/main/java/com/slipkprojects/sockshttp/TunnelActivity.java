package com.slipkprojects.sockshttp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.slipkprojects.ultrasshservice.config.Settings;
//import org.apache.commons.io.filefilter.FalseFileFilter;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdSize;
import com.slipkprojects.sockshttp.view.MaterialButton;

public class TunnelActivity extends AppCompatActivity implements View.OnClickListener {

	private Toolbar toolbar_main;
	private RadioButton btnDirect;
    private RadioButton btnHTTP;
	private RadioButton btnSSL;
	private RadioButton btnSlowDNS;
	private CheckBox customPayload;
	private Settings mConfig;
	private SharedPreferences prefs;
	private MaterialButton save;

	private TextView mTextView;

	private RadioButton btnTLS;

	private AdView adsBannerView;

	private RadioButton btnTYPE;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.activity_mainSSHDirectRadioButton:
				btnTLS.setChecked(false);
				btnTYPE.setChecked(true);
				btnHTTP.setChecked(false);
				btnSSL.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(true);
				if (customPayload.isChecked()) {
					mTextView.setText(getString(R.string.direct) + getString(R.string.custom_payload1));
				} else {
					mTextView.setText(getString(R.string.direct));			
				}
				break;

			case R.id.activity_mainSSHProxyRadioButton:
				btnTLS.setChecked(false);
				btnDirect.setChecked(false);
				btnTYPE.setChecked(true);
				btnSSL.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(true);
				if (customPayload.isChecked()) {
					mTextView.setText(getString(R.string.http) + getString(R.string.custom_payload1));
				} else {
					mTextView.setText(getString(R.string.http));			
				}
				break;

			case R.id.activity_mainSSLProxyRadioButton:
				btnTLS.setChecked(false);
				btnTYPE.setChecked(true);
				btnHTTP.setChecked(false);
				btnDirect.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(false);
				customPayload.setChecked(false);
				mTextView.setText(getString(R.string.ssl));		
				break;

			case R.id.activity_mainSSLPayloadRadioButton:
				btnSSL.setChecked(false);
				btnTYPE.setChecked(true);
				btnHTTP.setChecked(false);
				btnDirect.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(false);
				customPayload.setChecked(true);
				if (customPayload.isChecked()) {
					mTextView.setText(getString(R.string.tls) + getString(R.string.custom_payload1));
				} else {
					mTextView.setText(getString(R.string.tls));	
				}
				break;


			case R.id.btnSlowDNS:
				btnTLS.setEnabled(false);
				btnHTTP.setEnabled(false);
				btnSSL.setEnabled(false);
				btnTYPE.setChecked(false);
				btnSSL.setChecked(false);
				btnHTTP.setChecked(false);
				btnTLS.setChecked(false);
				customPayload.setEnabled(false);
				customPayload.setChecked(false);
				btnDirect.setChecked(false);
				btnDirect.setEnabled(false);
				mTextView.setText(getString(R.string.slowdns));		
				break;

			case R.id.btnTYPE:
				btnSSL.setChecked(false);
				btnHTTP.setChecked(false);
				btnDirect.setChecked(true);
				btnTLS.setChecked(false);
				btnSlowDNS.setChecked(false);
				btnSSL.setEnabled(true);
				btnTLS.setEnabled(true);
				btnHTTP.setEnabled(true);
				btnDirect.setEnabled(true);
				btnSlowDNS.setEnabled(true);
				customPayload.setEnabled(true);
				customPayload.setChecked(false);		
				break;


			case R.id.activity_mainCustomPayloadSwitch:
				if (customPayload.isChecked()) {
					if (btnDirect.isChecked()) {
						mTextView.setText(getString(R.string.direct) + getString(R.string.custom_payload1));
					} else if (btnHTTP.isChecked()) {
						mTextView.setText(getString(R.string.http) + getString(R.string.custom_payload1));
					} else if (btnTLS.isChecked()) {
						mTextView.setText(getString(R.string.tls) + getString(R.string.custom_payload1));
					}
				} else {
					if (btnDirect.isChecked()) {
						mTextView.setText(getString(R.string.direct));
					} else if (btnHTTP.isChecked()) {
						mTextView.setText(getString(R.string.http));
					} else if (btnTLS.isChecked()) {
						mTextView.setText(getString(R.string.tls));
					}
				}
				break;

			case R.id.saveButton:
				doSave();	
				break;
		}
	}

	private void doSave() {
		SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();

		if (btnDirect.isChecked()) {
			edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);	

		} else if (btnHTTP.isChecked()) {
			edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_PROXY);	

		} else if (btnSSL.isChecked()) {
			edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSL_TLS);	

		} else if (btnTLS.isChecked()) {
			edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_PAY_SSL);	

		} else if (btnSlowDNS.isChecked()) {
			edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SLOWDNS);	

		} else if (btnTYPE.isChecked()) {
			edit.putInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH);	


	    }	

		if (customPayload.isChecked()) {
			edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, false);

		} else {
			edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true);
		}
		edit.apply();
		startActivity(new Intent(this, SocksHttpMainActivity.class));
		SocksHttpMainActivity.updateMainViews(getApplicationContext());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tunnel_types);
		mConfig = new Settings(this);
		prefs = mConfig.getPrefsPrivate();
		toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setupButton();
	}

	private void setupButton() {
		mTextView = (TextView) findViewById(R.id.tunneltypeTextView1);
		btnDirect = (RadioButton) findViewById(R.id.activity_mainSSHDirectRadioButton);
		btnDirect.setOnClickListener(this);
		btnHTTP = (RadioButton) findViewById(R.id.activity_mainSSHProxyRadioButton);
		btnHTTP.setOnClickListener(this);
		btnSSL = (RadioButton) findViewById(R.id.activity_mainSSLProxyRadioButton);
		btnSSL.setOnClickListener(this);
		btnTLS = (RadioButton) findViewById(R.id.activity_mainSSLPayloadRadioButton);
		btnTLS.setOnClickListener(this);
		btnTYPE = (RadioButton) findViewById(R.id.btnTYPE);
		btnTYPE.setOnClickListener(this);
		btnSlowDNS = (RadioButton) findViewById(R.id.btnSlowDNS);
		btnSlowDNS.setOnClickListener(this);

		customPayload = (CheckBox) findViewById(R.id.activity_mainCustomPayloadSwitch);
		customPayload.setOnClickListener(this);

		save = (MaterialButton) findViewById(R.id.saveButton);
		save.setOnClickListener(this);


		View adContainer = findViewById(R.id.load_tunnel);
		adsBannerView = new AdView(this);
		adsBannerView.setAdSize(AdSize.LARGE_BANNER);
		adsBannerView.setAdUnitId(SocksHttpApp.BANNER_AD);
		((LinearLayout)adContainer).addView(adsBannerView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adsBannerView.loadAd(adRequest);

		int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);

	    customPayload.setChecked(!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true));

		switch (tunnelType) {
			case Settings.bTUNNEL_TYPE_SSH_DIRECT:
				btnTYPE.setChecked(true);
				btnDirect.setChecked(true);
				btnHTTP.setChecked(false);
				btnTLS.setChecked(false);
				btnSSL.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(true);

				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					mTextView.setText(getString(R.string.direct) + getString(R.string.custom_payload1));
				} else {
					mTextView.setText(getString(R.string.direct));			
				}
				break;

			case Settings.bTUNNEL_TYPE_SSH_PROXY:
				btnTYPE.setChecked(true);
				btnHTTP.setChecked(true);
				btnTLS.setChecked(false);
				btnDirect.setChecked(false);
				btnSSL.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(true);
				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					mTextView.setText(getString(R.string.http) + getString(R.string.custom_payload1));
				} else {
					mTextView.setText(getString(R.string.http));			
				}
				break;

			case Settings.bTUNNEL_TYPE_PAY_SSL:
				btnTYPE.setChecked(true);
				btnTLS.setChecked(true);
				btnHTTP.setChecked(false);
				btnDirect.setChecked(false);
				btnSSL.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(false);
				customPayload.setChecked(true);
				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					mTextView.setText(getString(R.string.tls) + getString(R.string.custom_payload1));
				} else {
					mTextView.setText(getString(R.string.tls));			
				}
				break;

			case Settings.bTUNNEL_TYPE_SSL_TLS:
				btnTYPE.setChecked(true);
				btnSSL.setChecked(true);
				btnHTTP.setChecked(false);
				btnDirect.setChecked(false);
				btnTLS.setChecked(false);
				btnSlowDNS.setChecked(false);
				customPayload.setEnabled(false);
				customPayload.setChecked(false);
				mTextView.setText(getString(R.string.ssl));			
				break;

			case Settings.bTUNNEL_TYPE_SLOWDNS:
				btnSlowDNS.setChecked(true);
				btnHTTP.setEnabled(false);
				btnTYPE.setChecked(false);
				btnSSL.setChecked(false);
				btnHTTP.setChecked(false);
				btnTLS.setChecked(false);
				btnSSL.setEnabled(false);
				btnTLS.setEnabled(false);
				btnDirect.setChecked(false);
				btnDirect.setEnabled(false);
				customPayload.setEnabled(false);
			    customPayload.setChecked(false);
			    mTextView.setText(getString(R.string.slowdns));		
				break;

			case Settings.bTUNNEL_TYPE_SSH:
				btnTYPE.setChecked(true);
				btnSSL.setEnabled(true);
				btnHTTP.setEnabled(true);
				btnDirect.setEnabled(true);
				btnTLS.setEnabled(true);
				btnSSL.setChecked(false);
				btnHTTP.setChecked(false);
				btnDirect.setChecked(true);
				btnTLS.setChecked(false);
				btnSlowDNS.setChecked(false);
				btnSlowDNS.setEnabled(true);
				customPayload.setEnabled(true);
				customPayload.setChecked(false);			
				break;
		}
	}


}

