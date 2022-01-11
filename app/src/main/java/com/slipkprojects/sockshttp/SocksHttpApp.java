package com.slipkprojects.sockshttp;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;

import com.slipkprojects.ultrasshservice.util.SkProtect;

import android.content.Context;

import com.slipkprojects.ultrasshservice.SocksHttpCore;
import com.slipkprojects.ultrasshservice.config.Settings;
import com.google.android.gms.ads.MobileAds;
import android.content.res.Configuration;

/**
 * App
 */
public class SocksHttpApp extends Application
{
	private static final String TAG = SocksHttpApp.class.getSimpleName();
	public static final String PREFS_GERAL = "SocksHttpGERAL";

	public static final String ADS_UNITID_INTERSTITIAL_MAIN = "ca-app-pub-9912895734802521/8066804110";
    public static final String ADS_UNITID_BANNER_MAIN = "ca-app-pub-9912895734802521/2156001887";
    public static final String ADS_UNITID_BANNER_SOBRE = "ca-app-pub-9912895734802521/2156001887";
    public static final String INTERSTITIAL_AD = new String(new byte[]{99,97,45,97,112,112,45,112,117,98,45,57,57,49,50,56,57,53,55,51,52,56,48,50,53,50,49,47,56,48,54,54,56,48,52,49,49,48});
    public static final String BANNER_AD = new String(new byte[]{99,97,45,97,112,112,45,112,117,98,45,57,57,49,50,56,57,53,55,51,52,56,48,50,53,50,49,47,50,49,53,54,48,48,49,56,56,55});
    public static final String REWARDED_AD = new String(new byte[]{99,97,45,97,112,112,45,112,117,98,45,57,57,49,50,56,57,53,55,51,52,56,48,50,53,50,49,47,55,52,52,49,54,48,56,56,52,49});
    public static final String UNIT_ID_AD = new String(new byte[]{99,97,45,97,112,112,45,112,117,98,45,57,57,49,50,56,57,53,55,51,52,56,48,50,53,50,49,126,56,55,50,49,52,49,48,50,51,52});
    public static final boolean DEBUG = true;
    public static final String ADS_UNITID_BANNER_TEST = "ca-app-pub-9912895734802521/2156001887";
	public static final String APP_FLURRY_KEY = "RQQ8J9Q2N4RH827G32X9";

	private static SocksHttpApp mApp;

	@Override
	public void onCreate()
	{
		super.onCreate();

		mApp = this;

		// captura dados para análise
		/*new FlurryAgent.Builder()
		 .withCaptureUncaughtExceptions(true)
		 .withIncludeBackgroundSessionsInMetrics(true)
		 .withLogLevel(Log.VERBOSE)
		 .withPerformanceMetrics(FlurryPerformance.ALL)
		 .build(this, APP_FLURRY_KEY);*/

		// inicia
		SocksHttpCore.init(this);

		// protege o app
		SkProtect.init(this);

		// Initialize the Mobile Ads SDK.
        MobileAds.initialize(this);

		// modo noturno
		setModoNoturno(this);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//LocaleHelper.setLocale(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//LocaleHelper.setLocale(this);
	}

	private void setModoNoturno(Context context) {
	 boolean is = new Settings(context)
	.getModoNoturno().equals("on");

	 int night_mode = is ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
	 AppCompatDelegate.setDefaultNightMode(night_mode);
	 }

	public static SocksHttpApp getApp() {
		return mApp;
	}
}
