package com.slipkprojects.ultrasshservice.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Log;
import android.widget.Toast;
import com.slipkprojects.ultrasshservice.BuildConfig;
import com.slipkprojects.ultrasshservice.R;

/**
 * @author Skank3r
 */
public class SkProtect {

	private static final String TAG = SkProtect.class.getSimpleName();
	
	private static final String APP_BASE = new String(new byte[]{99,111,109,46,111,99,116,46,105,110,106,101,99,116,111,114});
	private static final String APP_NAME = new String(new byte[]{111,99,116,32,105,110,106,101,99,116,111,114});
	
	// Assinatura da Google Play
	//private static final String APP_SIGNATURE = "XbhYZ4Bz/9F4cWLIDMg0wl/+jl8=\n";

	private static SkProtect mInstance;

	private Context mContext;
	
	public static void init(Context context) {
		if (mInstance == null) {
			mInstance = new SkProtect(context);

			// This method will print your certificate signature to the logcat.
			//AndroidTamperingProtectionUtils.getCertificateSignature(context);
		}
	}

	private SkProtect(Context context) {
		mContext = context;
	}
	
	/*public void tamperProtect() {
		AndroidTamperingProtection androidTamperingProtection = new AndroidTamperingProtection.Builder(mContext, APP_SIGNATURE)
			.installOnlyFromPlayStore(false) // By default is set to false.
			.build();

		if (!androidTamperingProtection.validate()) {
			throw new RuntimeException();
		}
	}*/
	
	public void simpleProtect() {
		if (!APP_BASE.equals(mContext.getPackageName().toLowerCase()) ||
				!mContext.getString(R.string.app_name).toLowerCase().equals(APP_NAME)) {
			throw new RuntimeException();
		}
	}

	public static void CharlieProtect() {
		if (mInstance == null) return;
			
		mInstance.simpleProtect();
		
		// ative apenas ao enviar pra PlayStore
		//mInstance.tamperProtect();
	}
}
