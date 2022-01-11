package com.slipkprojects.sockshttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.slipkprojects.ultrasshservice.util.ToastUtil;

/**
* Ads Manager
* @author dFiR30n
*/

public class AdsManager
{
	private final String TAG = "AdsManager";
	
	private Context mContext;
	private SharedPreferences mPrefs;
	
	private ToastUtil toastutil;
	
	private InterstitialAd mInterstitialAd;
	
	public static AdsManager newInstance(Context context) {
		return new AdsManager(context);
	}
	
	private AdsManager(Context context){
		mContext = context;
		mPrefs = context.getSharedPreferences(SocksHttpApp.PREFS_GERAL, Context.MODE_PRIVATE);
		
		// Ads interstitial
		setupAdsInterstitial();
		
		toastutil = new ToastUtil(mContext);
	}

	private void setupAdsInterstitial() {
		mInterstitialAd = new InterstitialAd(mContext);

		if (!BuildConfig.DEBUG)
			mInterstitialAd.setAdUnitId(SocksHttpApp.ADS_UNITID_INTERSTITIAL_MAIN);
		else
			mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				if (mInterstitialAd != null) {
					mInterstitialAd.show();
				}
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				// Code to be executed when an ad request fails.
			}

			@Override
			public void onAdOpened() {
				// Code to be executed when the ad is displayed.
				if (mPrefs != null) {
					SharedPreferences.Editor pEdit = mPrefs.edit();
					pEdit.putLong("last_ads_time", SystemClock.elapsedRealtime());
					pEdit.apply();
				}
			}

			@Override
			public void onAdClicked() {
				// Code to be executed when the user clicks on an ad.
			}

			@Override
			public void onAdLeftApplication() {
				// Code to be executed when the user has left the app.
			}

			@Override
			public void onAdClosed() {
				// Code to be executed when the interstitial ad is closed.

				toastutil.showSuccessToast("Thank you for supporting the app.❤️");

			}
		});
	}

	public void loadAdsInterstitial() {
		// carrega anúncio a cada 1 hora
		createTimer(60000);
	}


	/**
	 * Ads Timer
	 */
	private CountDownTimer countDownTimer;
	private long timerMilliseconds;

	private void createTimer(final long milliseconds) {
		// Create the game timer, which counts down to the end of the level
		// and shows the "retry" button.
		if (countDownTimer != null) {
			countDownTimer.cancel();
		}

		countDownTimer = new CountDownTimer(milliseconds, 50) {
			@Override
			public void onTick(long millisUnitFinished) {
				timerMilliseconds = millisUnitFinished;
			}

			@Override
			public void onFinish() {
//				long time = 60*1;
				if (mInterstitialAd != null ){
//					mInterstitialAd.loadAd(new AdRequest.Builder().build());
					Log.d(TAG, "Loading interstitial ad..");
				}// carrega novo anúncio
			}
		};
	}

}
