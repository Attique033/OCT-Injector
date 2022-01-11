package com.slipkprojects.sockshttp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ViewAnimation;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.slipkprojects.sockshttp.activities.AboutActivity;
import com.slipkprojects.sockshttp.activities.BaseActivity;
import com.slipkprojects.sockshttp.activities.ConfigExportFileActivity;
import com.slipkprojects.sockshttp.activities.ConfigGeralActivity;
import com.slipkprojects.sockshttp.activities.ConfigImportFileActivity;
import com.slipkprojects.sockshttp.adapter.LogsAdapter;
import com.slipkprojects.sockshttp.fragments.ClearConfigDialogFragment;
import com.slipkprojects.sockshttp.fragments.CustomSniFragment;
import com.slipkprojects.sockshttp.fragments.ProxyRemoteDialogFragment;
import com.slipkprojects.sockshttp.util.HostChecker;
import com.slipkprojects.sockshttp.util.IpHunter;
import com.slipkprojects.sockshttp.util.Utils;
import com.slipkprojects.ultrasshservice.LaunchVpn;
import com.slipkprojects.ultrasshservice.SocksHttpService;
import com.slipkprojects.ultrasshservice.config.ConfigParser;
import com.slipkprojects.ultrasshservice.config.PasswordCache;
import com.slipkprojects.ultrasshservice.config.Settings;
import com.slipkprojects.ultrasshservice.logger.ConnectionStatus;
import com.slipkprojects.ultrasshservice.logger.SkStatus;
import com.slipkprojects.ultrasshservice.tunnel.TunnelManagerHelper;
import com.slipkprojects.ultrasshservice.tunnel.TunnelUtils;
import com.slipkprojects.ultrasshservice.util.SkProtect;
import com.slipkprojects.ultrasshservice.util.ToastUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;
import io.github.tonnyl.light.Light;
import com.google.android.material.snackbar.Snackbar;
import android.widget.ScrollView;
import android.graphics.Color;
import android.content.ClipboardManager;
import android.content.ClipData;

/**
 * Activity Principal
 * @author SlipkHunter
 */

public class SocksHttpMainActivity extends BaseActivity
implements DrawerLayout.DrawerListener, RewardedVideoAdListener,
View.OnClickListener, RadioGroup.OnCheckedChangeListener,
 SkStatus.StateListener, RenzGenerator.OnDismissListener
{

	private RewardedVideoAd mRewardedVideoAd;

	private CardView connectionCardview;

	private MenuItem settingsSSH;

	private ImageView cArrow1;

	private LinearLayout cLinearLayout;

	private LinearLayout cLinearLayoutHeader;

	private ImageView mArrow1;

	private LinearLayout mLinearLayout;

	private LinearLayout mLinearLayoutHeader;

	private AdView adsBannerView;

	private ImageButton bt_toggle_input;

	private NestedScrollView nested_scroll_view;

	private Button bt_save_input;

	private Button bt_hide_input;

	private View lyt_expand_input;

	private LinearLayout ly_hide_input;

	private ScrollView main_ly;

	private String version_nome;


	private CardView tunnelLayout;

	private ProgressDialog pDialog;


	private static final String TAG = SocksHttpMainActivity.class.getSimpleName();
	private static final String UPDATE_VIEWS = "MainUpdate";
	public static final String OPEN_LOGS = "com.slipkprojects.sockshttp:openLogs";
	private LinearLayout sslLayout;

	private TextView sslText;
	private LinearLayout testinglayout;

	private DrawerPanelMain mDrawerPanel;

	private Settings mConfig;
	private Toolbar toolbar_main;
	private Handler mHandler;

	private LinearLayout mainLayout;
	private LinearLayout loginLayout;
	private LinearLayout proxyLayout;
	private TextView proxyText;
	private RadioGroup metodoConexaoRadio;
	//private RadioGroup tunnelmode_layout;
	private TextView tunnelInfo;
	private LinearLayout payloadLayout;
	private TextInputEditText payloadEdit;
	private AppCompatCheckBox customPayloadSwitch;
	private Button starterButton;
	private NavigationView drawerNavigationView;
	private ImageButton inputPwShowPass;
	private TextInputEditText inputPwUser;
	private TextInputEditText inputPwPass;

	private CardView configMsgLayout;
	private TextView configMsgText;

	private CardView Viewgone;
	private CardView ViewGone;
	private CardView tunneltype;
	private TextView tunnelselected;

	private TextView mTextViewCountDown;
	private Button mButtonSet;
	private CheckBox mButtonStartPause;
	private Button mButtonReset;
	private CountDownTimer mCountDownTimer;
	private boolean mTimerRunning;
	private long mStartTimeInMillis;
	private long mTimeLeftInMillis;
	private long mEndTime;
	private EditText mEditTextInput;

    private ToastUtil toastutil;

	private static final String AD_UNIT_ID = "ca-app-pub-9912895734802521~8721410234";
	private InterstitialAd interstitialAd;
	private InterstitialAd mInterstitialAd;

	private static final String[] tabTitle = {"Home","Log","Help"};
	private LogsAdapter mLogAdapter;
	private RecyclerView logList;
	private TabLayout tabs;
	private ViewPager vp;
	private FloatingActionButton deleteLogs;

	private TextView status;
	private LinearLayout tunnel_dialog_layout;

	View view1;
    View view2;
    View view3;
    View view4;
	View view20;
	View view21;
	View view22;
    View view5;
    View view6;
	View view7;
	View view8;
    private GuideView mGuideView;
	private AlertDialog dialog;

	private TextView tunneltypeselected;
	private TextView onoff;


	String APP = new String(new byte[]{84,104,105,115,32,97,112,112,108,105,99,97,116,105,111,110,32,105,115,32,111,119,110,101,100,32,98,121,32,78,117,116,114,111,32,87,111,114,108,100});

	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		mHandler = new Handler();
		mConfig = new Settings(this);
		toastutil = new ToastUtil(this);
		mDrawerPanel = new DrawerPanelMain(this);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		SharedPreferences prefs = getSharedPreferences(SocksHttpApp.PREFS_GERAL, Context.MODE_PRIVATE);

		boolean showFirstTime = prefs.getBoolean("connect_first_time", true);
		int lastVersion = prefs.getInt("last_version", 0);

		// se primeira vez
		if (showFirstTime)
        {
            SharedPreferences.Editor pEdit = prefs.edit();
            pEdit.putBoolean("connect_first_time", false);
            pEdit.apply();

			Settings.setDefaultConfig(this);

			showVersion();
        }

		try {
			int idAtual = ConfigParser.getBuildId(this);

			if (lastVersion < idAtual) {
				SharedPreferences.Editor pEdit = prefs.edit();
				pEdit.putInt("last_version", idAtual);
				pEdit.apply();

				// se estiver atualizando
				if (!showFirstTime) {
					if (lastVersion <= 12) {
						Settings.setDefaultConfig(this);
						Settings.clearSettings(this);

						Toast.makeText(this, "As configurações foram limpas para evitar bugs",
									   Toast.LENGTH_LONG).show();
					}
				}

			}
		} catch(IOException e) {}


		// set layout
		doLayout();

		// verifica se existe algum problema
		SkProtect.CharlieProtect();

		// recebe local dados
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_VIEWS);
		filter.addAction(OPEN_LOGS);

		LocalBroadcastManager.getInstance(this)
			.registerReceiver(mActivityReceiver, filter);

		doUpdateLayout();
		doTabs();
		alllayout();
		AdsManager.newInstance(getApplicationContext()).loadAdsInterstitial();
	}

	

	public void alllayout()
	{
		CardView iphunter_layout = (CardView)findViewById(R.id.iphunter_layout);
		iphunter_layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					Intent iphunterintent = new Intent(SocksHttpMainActivity.this, IpHunter.class);
					iphunterintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(iphunterintent);

				}});

		CardView hostchecker_layout = (CardView)findViewById(R.id.hostchecker_layout);
		hostchecker_layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					Intent hostintent = new Intent(SocksHttpMainActivity.this, HostChecker.class);
					startActivity(hostintent);
				}});
	}

	private void tunnel_options(){
		LayoutInflater inflater = LayoutInflater.from(this);
		final View v = inflater.inflate(R.layout.dialog_tunnelmode, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(v);
		RadioGroup metodoConexaoRadio = (RadioGroup) v.findViewById(R.id.activity_mainMetodoConexaoRadio);
		metodoConexaoRadio.setOnCheckedChangeListener(this);
		final TextView selected_tunnel_options = (TextView)v.findViewById(R.id.activitymainTextView1);
		final TextView tunneltype_selected_layout = (TextView)v.findViewById(R.id.tunnelmode_selected_layout);
		//final SwitchCompat payload_enable = (SwitchCompat)v.findViewById(R.id.activity_mainCustomPayloadSwitch);
		// customPayloadSwitch = (CheckBox) findViewById(R.id.activity_mainCustomPayloadSwitch);
		final RadioButton btn_direct = (RadioButton) v.findViewById(R.id.activity_mainSSHDirectRadioButton);
		final RadioButton btn_ssh = (RadioButton) v.findViewById(R.id.activity_mainSSHProxyRadioButton);
		final RadioButton btn_ssl = (RadioButton) v.findViewById(R.id.activity_mainSSLProxyRadioButton);
		//savez = (Button) v.findViewById(R.id.savez);
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.color.colorPrimary));
		metodoConexaoRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					// save id into your SharedPreferences

					if(btn_direct.isChecked())
					{
						((AppCompatRadioButton) findViewById(R.id.activity_mainSSHDirectRadioButton)).setChecked(true);
						//tunnel_type_txt.setText("Direct SSH");
						tunneltype_selected_layout.setText("Direct SSH");
						//selected_tunnel_options.setText("Direct SSH");
						SkStatus.logInfo("<font color='green'>Direct SSH Mode</font>");

					}
					if(btn_ssh.isChecked())
					{
						((AppCompatRadioButton) findViewById(R.id.activity_mainSSHProxyRadioButton)).setChecked(true);
						tunneltype_selected_layout.setText("HTTP Proxy ➔ SSH");
						//selected_tunnel_options.setText("HTTP Proxy ➔ SSH");
						SkStatus.logInfo("<font color='green'>SSH + PROXY Mode</font>");
						payloadEdit.setHint(R.string.payload);
					}
					else if(btn_ssl.isChecked())
					{
						((RadioButton) findViewById(R.id.activity_mainSSLProxyRadioButton)).setChecked(true);
						tunneltype_selected_layout.setText("SSL/TLS ➝ SSH");
						//selected_tunnel_options.setText("SSL/TLS ➝ SSH");
						SkStatus.logInfo("<font color='green'>SSH + SSL/TLS Mode</font>");
						payloadLayout.setVisibility(View.VISIBLE);
					}
				}
			});


		builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int i) {

					dialog.dismiss();

				}
			});
		/*btn_saveR.setOnClickListener(new View.OnClickListener() {
		 @Override
		 public void onClick(View v) {


		 }
		 });*/

		builder.show();
	}

	public void tunnel_options_layout(View v)
	{
		tunnel_options();
	}

	public void OpenAppListing (View v)
	{
		mLogAdapter.clearLog();
	}
	public void doTabs() {
        vp = (ViewPager)findViewById(R.id.viewpager);
        tabs = (TabLayout)findViewById(R.id.tablayout);
        vp.setAdapter(new MyAdapter(Arrays.asList(tabTitle)));
        vp.setOffscreenPageLimit(3);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(vp);

		vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
            {
                @Override
                public void onPageSelected(int position)
                {
                    if (position == 0) {
                        toolbar_main.getMenu().clear();
						getMenuInflater().inflate(R.menu.main_menu, toolbar_main.getMenu());
                    } else if (position == 1) {
                        toolbar_main.getMenu().clear();
						getMenuInflater().inflate(R.menu.logs_menu, toolbar_main.getMenu());
					} else if (position == 2) {
                        toolbar_main.getMenu().clear();
						getMenuInflater().inflate(R.menu.vp_menu, toolbar_main.getMenu());
						
						}
                }
			});
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

	}

	public class MyAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            // TODO: Implement this method
            return 3;
        }

        @Override
        public boolean isViewFromObject(View p1, Object p2)
        {
            // TODO: Implement this method
            return p1 == p2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            int[] ids = new int[]{R.id.tab1, R.id.tab2, R.id.tab3};
            int id = 0;
            id = ids[position];
            // TODO: Implement this method
            return findViewById(id);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            // TODO: Implement this method
            return titles.get(position);
        }

        private List<String> titles;
        public MyAdapter(List<String> str)
        {
            titles = str;
        }
	}
	public void guides()
	{
		new GuideView.Builder(SocksHttpMainActivity.this)
			.setTitle(getString(R.string.tun_type))
			.setContentText("Here you can change the tunnel type of your choice")
			.setGravity(Gravity.center)
			.setTargetView(view1)
			.setDismissType(DismissType.outside) //optional - default dismissible by TargetView
			.setGuideListener(new GuideListener() {
				@Override
				public void onDismiss(View view) {
					//TODO ...
					new GuideView.Builder(SocksHttpMainActivity.this)
						.setTitle("Config Login")
						.setContentText("In This Config Login you need to enter password provides by creator if null")
						.setGravity(Gravity.center)
						.setTargetView(view2)
						.setDismissType(DismissType.outside) //optional - default dismissible by TargetView
						.setGuideListener(new GuideListener() {

//ari nimu e solod ang bag o
							@Override
							public void onDismiss(View view) {
								//TODO ...
								new GuideView.Builder(SocksHttpMainActivity.this)
									.setTitle("Import/Export")
									.setContentText("Here is where you import/export config")
									.setGravity(Gravity.center)
									.setTargetView(view3)
									.setDismissType(DismissType.outside) //optional - default dismissible by TargetView
									.setGuideListener(new GuideListener() {
										@Override
										public void onDismiss(View view) {
											//TODO ...
											new GuideView.Builder(SocksHttpMainActivity.this)
												.setTitle("Menu")
												.setContentText("Click here for more options")
												.setGravity(Gravity.center)
												.setTargetView(view4)
												.setDismissType(DismissType.outside) //optional - default dismissible by TargetView
												.setGuideListener(new GuideListener() {

													@Override
													public void onDismiss(View view) {
														//TODO ...
														new GuideView.Builder(SocksHttpMainActivity.this)
															.setTitle("Clear Log")
															.setContentText("Click here to refresh logs")
															.setGravity(Gravity.center)
															.setTargetView(view20)
															.setDismissType(DismissType.outside) //optional - default dismissible by TargetView
															.setGuideListener(new GuideListener() {

																@Override
																public void onDismiss(View view) {
																	//TODO ...
																	new GuideView.Builder(SocksHttpMainActivity.this)
																		.setTitle("Connect/Disconnect")
																		.setContentText("Click This Button To :\n\nConnect = Start connection between the server...\n\nDisconnect = Stop the connection")
																		.setGravity(Gravity.center)
																		.setTargetView(view21)
																		.setDismissType(DismissType.outside) //optional - default dismissible by TargetView
																		.setGuideListener(new GuideListener() {

//ari nimu e solod ang bag o
																			@Override
																			public void onDismiss(View view) {
																				//TODO ...

																			}

																		})
																		.build()
																		.show();
																}
															})
															.build()
															.show();
													}

												})
												.build()
												.show();
										}
									})
									.build()
									.show();
							}
						})
						.build()
						.show();
				}
			})
			.build()
			.show();

		updatingForDynamicLocationViews();
	}
	private void updatingForDynamicLocationViews() {
        view4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean b) {
					mGuideView.updateGuideViewLocation();
				}
			});}

	public class DrawerPanelMain
	implements NavigationView.OnNavigationItemSelectedListener
	{
		private AppCompatActivity mActivity;



		public DrawerPanelMain(AppCompatActivity activity) {
			mActivity = activity;
		}


		private DrawerLayout drawerLayout;
		private ActionBarDrawerToggle toggle;

		public void setDrawer(Toolbar toolbar) {
			drawerNavigationView = (NavigationView) mActivity.findViewById(R.id.drawerNavigationView);
			drawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawerLayoutMain);

			// set drawer
			toggle = new ActionBarDrawerToggle(mActivity,
											   drawerLayout, toolbar, R.string.open, R.string.cancel);

			drawerLayout.setDrawerListener(toggle);

			toggle.syncState();

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

						if (aparelho_marca.equals("") || aparelho_marca.equals("")) {
							Light.error(main_ly, getString(R.string.error_no_supported), Snackbar.LENGTH_LONG).show();
						}
						else {
							try {
								Intent in = new Intent(Intent.ACTION_MAIN);
								in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								in.setClassName("com.android.settings", "com.android.settings.RadioInfo");
								mActivity.startActivity(in);
							} catch(Exception e) {
								Light.error(main_ly, getString(R.string.error_no_supported), Snackbar.LENGTH_LONG).show();
							}
						}
					}
					break;
				case R.id.miGen:
					SharedPreferences prefs = mConfig.getPrefsPrivate();
					boolean protect = prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false);
					if (protect) {
						Light.warning(main_ly, "Config is locked!!!", Snackbar.LENGTH_LONG).show();
						} else {
					
					if(customPayloadSwitch.isChecked())
					{
						showGenerator();

					} else {
						Light.info(main_ly, "Please enable the Custom Payload Switch..", Snackbar.LENGTH_LONG).show();
						
                       }
					}
					break;

				case R.id.iphunter2:
					Intent iphunterintent = new Intent(mActivity, IpHunter.class);
					iphunterintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mActivity.startActivity(iphunterintent);
					if (adsBannerView != null && TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
						adsBannerView.setAdListener(new AdListener() {
								@Override
								public void onAdLoaded() {
									if (adsBannerView != null && !isFinishing()) {
										adsBannerView.setVisibility(View.VISIBLE);
									}
								}
							});
						adsBannerView.postDelayed(new Runnable() {
								@Override
								public void run() {
									// carrega ads interestitial
//									AdsManager.newInstance(getApplicationContext())
//										.loadAdsInterstitial();
									// ads banner
									if (adsBannerView != null && !isFinishing()) {
//										adsBannerView.loadAd(new AdRequest.Builder()
//															 .build());
									}
								}
							}, 5000);
					}

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
					if (adsBannerView != null && TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
						adsBannerView.setAdListener(new AdListener() {
								@Override
								public void onAdLoaded() {
									if (adsBannerView != null && !isFinishing()) {
										adsBannerView.setVisibility(View.VISIBLE);
									}
								}
							});
						adsBannerView.postDelayed(new Runnable() {
								@Override
								public void run() {
									// carrega ads interestitial
//									AdsManager.newInstance(getApplicationContext())
//										.loadAdsInterstitial();
									// ads banner
									if (adsBannerView != null && !isFinishing()) {
//										adsBannerView.loadAd(new AdRequest.Builder()
//															 .build());
									}
								}
							}, 5000);
					}
					break;

				case R.id.miSettingsSSH:
					SharedPreferences mPrefs = mConfig.getPrefsPrivate();
					int tunnelType = mPrefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);

					if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
						Intent intent2 = new Intent(SocksHttpMainActivity.this, ConfigGeralActivity.class);
						intent2.setAction(ConfigGeralActivity.OPEN_SETTINGS_DNS);
						intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent2);
					} else {
						Intent intent2 = new Intent(SocksHttpMainActivity.this, ConfigGeralActivity.class);
						intent2.setAction(ConfigGeralActivity.OPEN_SETTINGS_SSH);
						intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent2);
					}
					if (adsBannerView != null && TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
						adsBannerView.setAdListener(new AdListener() {
								@Override
								public void onAdLoaded() {
									if (adsBannerView != null && !isFinishing()) {
										adsBannerView.setVisibility(View.VISIBLE);
									}
								}
							});
						adsBannerView.postDelayed(new Runnable() {
								@Override
								public void run() {
									// carrega ads interestitial
//									AdsManager.newInstance(getApplicationContext())
//										.loadAdsInterstitial();
									// ads banner
									if (adsBannerView != null && !isFinishing()) {
//										adsBannerView.loadAd(new AdRequest.Builder()
//															 .build());
									}
								}
							}, 5000);
					}
					break;


				case R.id.miAvaliarPlaystore:
					Intent email1 = new Intent(Intent.ACTION_SEND);  
					String url = "https://play.google.com/store/apps/details?id=com.oct.injector";
					Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mActivity.startActivity(Intent.createChooser(intent3, mActivity.getText(R.string.open_with)));
					Snackbar snackbar;
					snackbar = Snackbar.make(main_ly, "Please make sure you join", Snackbar.LENGTH_SHORT);
					View snackBarView = snackbar.getView();
					TextView textView = (TextView) snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
					textView.setTextColor(Color.GREEN);
					snackbar.show();

					break;


				case R.id.more:
					Intent aboutIntent = new Intent(mActivity, AboutActivity.class);
					startActivity(aboutIntent);
					break;

			   case R.id.mloadAds:
					mLoadAdsa();
					break;

				case R.id.miAdmin:
					String url1 = "https://t.me/OCTinjector";
					Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
					intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mActivity.startActivity(Intent.createChooser(intent4, mActivity.getText(R.string.open_with)));
					break;

				/*case R.id.miSendFeedback:
					if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
						try {
							GoogleFeedbackUtils.bindFeedback(mActivity);
						} catch (Exception e) {
							Light.error(main_ly, getString(R.string.error_no_supported), Snackbar.LENGTH_LONG).show();
						
							SkStatus.logDebug("Error: " + e.getMessage());
						}
					}
					else {
						Intent email = new Intent(Intent.ACTION_SEND);  
						email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						email.putExtra(Intent.EXTRA_EMAIL, new String[]{"octproject@outlook.com"});  
						email.putExtra(Intent.EXTRA_SUBJECT, "OCT Injector - " + mActivity.getString(R.string.feedback));  
						email.setType("message/rfc822"); 

						Light.normal(main_ly, "Choose an Email client", Snackbar.LENGTH_LONG).show();
						mActivity.startActivity(Intent.createChooser(email, "Choose an Email client:"));

					}
					break;*/
			}

			return true;
		}





	}


	private void mLoadAdsa() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.load_ads,null);

		builder.setView(dialogView);
		Button one = dialogView.findViewById(R.id.loadadsButton);
		final AlertDialog dialog = builder.create();


		one.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					loadingAdsa();
					loadRewardedVideoAd();

				}

			});

		dialog.show();

	}


	private void loadingAdsa() {

		pDialog = new ProgressDialog(this);
		pDialog.setTitle("Loading Video Ad");
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		pDialog.show();

	}

	private void cLoadAdsa()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater inflater = getLayoutInflater();
		View dialogView1 = inflater.inflate(R.layout.loadfailed,null);

		builder.setView(dialogView1);
		Button one = dialogView1.findViewById(R.id.loadfailedButton);
		final AlertDialog dialog = builder.create();


		one.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					loadingAdsa();
					loadRewardedVideoAd();

				}

			});

		dialog.show();

	}


	/**
	 * Layout
	 */
	private void showGenerator() {

		RenzGenerator gen = new RenzGenerator(this);
		gen.setDismissListener(this);
		dialog = new AlertDialog.Builder(this).create();
		dialog.setView(gen);
		dialog.show();

	}

	@Override
	public void onDismiss(String payload)
	{
		payloadEdit.setText(payload);
		Light.success(main_ly, "Payload successfully generated.", Snackbar.LENGTH_LONG).show();
		dialog.dismiss();
		// TODO: Implement this method
	}

	//for example ito code ng connect



	private void doLayout() {
		setContentView(R.layout.activity_main_drawer);


		toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
		mDrawerPanel.setDrawer(toolbar_main);
		setSupportActionBar(toolbar_main);
		mEditTextInput=(EditText)findViewById(R.id.time);

		mTextViewCountDown = (TextView) findViewById(R.id.duration);

		mButtonSet = (Button) findViewById(R.id.set);
		mButtonSet.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String input = mEditTextInput.getText().toString();
					if (input.length() == 0) {
						Light.error(main_ly, "Field can't be empty", Snackbar.LENGTH_LONG).show();
						return;
					}
					long millisInput = Long.parseLong(input) * 1000;
					if (millisInput == 0) {
						Light.error(main_ly, "Please a positive number", Snackbar.LENGTH_LONG).show();
						return;
					}
					setTime(millisInput);
					mEditTextInput.setText("55");
				}
			});

		MobileAds.initialize(this, SocksHttpApp.UNIT_ID_AD);
		mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		mRewardedVideoAd.setRewardedVideoAdListener(this);

		initComponent();
		setupInterstitial();
		showBoasVindas();
	}

	private void loadRewardedVideoAd() {
//		mRewardedVideoAd.loadAd(SocksHttpApp.REWARDED_AD,
//						  new AdRequest.Builder().build());
	}

	private void setupInterstitial(){

        mInterstitialAd = new InterstitialAd(this);

		if (!BuildConfig.DEBUG)
			mInterstitialAd.setAdUnitId(SocksHttpApp.ADS_UNITID_INTERSTITIAL_MAIN);
		else
			mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");; // inter ads

		mInterstitialAd.setAdListener(new AdListener() {

				@Override
				public void onAdClosed() {
					// Code to be executed when the interstitial ad is closed.
					Light.success(main_ly, getString(R.string.ad_done), Snackbar.LENGTH_LONG).show();

					loadInterstitial();

				}
			});



		mButtonStartPause = (CheckBox) findViewById(R.id.start);
		mButtonStartPause.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mTimerRunning) {
						//pauseTimer();
						mEditTextInput.setText("55");
					    pauseTimer();
						onoff.setText("OFF");
						SkStatus.logInfo("<font color='red'>Auto Reconnect is Stopping....</font>");
					} else {
						String input = mEditTextInput.getText().toString();

						if (input.length() == 0) {
							Light.error(main_ly, "Field can't be empty", Snackbar.LENGTH_LONG).show();
							return;
						}
						long millisInput = Long.parseLong(input) * 1000;
						if (millisInput == 0) {
							Light.error(main_ly, "Please a positive number", Snackbar.LENGTH_LONG).show();
							return;
						}
						setTime(millisInput);
						mEditTextInput.setText("");
						startTimer();
						onoff.setText("ON");
						SkStatus.logInfo("<font color='green'>Auto Reconnect is Starting....</font>");
					}
				}
			});

		mButtonReset = (Button) findViewById(R.id.reset);
		mButtonReset.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					resetTimer();
				}
			});



		// set ADS
		View adContainer = findViewById(R.id.load_main);
		adsBannerView = new AdView(this);
		adsBannerView.setAdSize(AdSize.BANNER);
		adsBannerView.setAdUnitId(SocksHttpApp.BANNER_AD);
		((LinearLayout)adContainer).addView(adsBannerView);
		AdRequest adRequest = new AdRequest.Builder().build();
//		adsBannerView.loadAd(adRequest);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mLogAdapter = new LogsAdapter(layoutManager,this);
		logList = (RecyclerView) findViewById(R.id.recyclerLog);
		logList.setAdapter(mLogAdapter);
		logList.setLayoutManager(layoutManager);
		mLogAdapter.scrollToLastPosition();

		deleteLogs = (FloatingActionButton) findViewById(R.id.clearLog);
		deleteLogs.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View p1)
				{
					mLogAdapter.clearLog();
					//SkStatus.logInfo("<font color='red'>Log Cleared!</font>");
					// TODO: Implement this method
				}


			});

		view1 = findViewById(R.id.tunnelCardView);
        view2 = findViewById(R.id.configMSGText);
        view3 = findViewById(R.id.configmsgheader);
		view4 = findViewById(R.id.configMSGCardView);
		view20 = findViewById(R.id.clearLog);
		view21 = findViewById(R.id.activity_starterButtonMain);

		status = (TextView)findViewById(R.id.status);
		tunneltypeselected = (TextView)findViewById(R.id.tunneltypeselected);
		onoff = (TextView)findViewById(R.id.onoff);

		main_ly = (ScrollView)findViewById(R.id.mainlayoutScrollView);
		tunnel_dialog_layout = (LinearLayout)findViewById(R.id.tunnel_dialog_layout);
		tunnelInfo = (TextView)findViewById(R.id.activitymainTextView1);
		//payloadlock = (LinearLayout)findViewById(R.id.payloadlock);
		ViewGone = (CardView) findViewById(R.id.ViewGone);
		Viewgone = (CardView) findViewById(R.id.Viewgone);
		mainLayout = (LinearLayout) findViewById(R.id.activity_mainLinearLayout);
		loginLayout = (LinearLayout) findViewById(R.id.activity_mainInputPasswordLayout);
		starterButton = (Button) findViewById(R.id.activity_starterButtonMain);
		tunnelLayout = (CardView) findViewById(R.id.tunnelCardView);
		tunnelLayout.setOnClickListener(this);
		inputPwUser = (TextInputEditText) findViewById(R.id.activity_mainInputPasswordUserEdit);
		inputPwPass = (TextInputEditText) findViewById(R.id.activity_mainInputPasswordPassEdit);

		inputPwShowPass = (ImageButton) findViewById(R.id.activity_mainInputShowPassImageButton);

		((TextView) findViewById(R.id.activity_mainAutorText))
			.setOnClickListener(this);

		proxyLayout = (LinearLayout) findViewById(R.id.activity_mainInputProxyLayout);
		proxyText = (TextView) findViewById(R.id.activity_mainProxyText);

		/*Spinner spinnerTunnelType = (Spinner) findViewById(R.id.activity_mainTunnelTypeSpinner);
		 String[] items = new String[]{"SSH DIRECT", "SSH + PROXY", "SSH + SSL (beta)"};
		 //create an adapter to describe how the items are displayed, adapters are used in several places in android.
		 //There are multiple variations of this, but this is the basic variant.
		 ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
		 //set the spinners adapter to the previously created one.
		 spinnerTunnelType.setAdapter(adapter);*/
		sslLayout = (LinearLayout) findViewById(R.id.cmods_snilayout);
		sslText = (TextView) findViewById(R.id.cmods_snitext);
		sslText.setText(mConfig.getPrivString(Settings.CUSTOM_SNI));
		//tunnelmode_layout = (RadioGroup)findViewById(R.id.tunnelmode_layout);
		metodoConexaoRadio = (RadioGroup) findViewById(R.id.activity_mainMetodoConexaoRadio);
		customPayloadSwitch = findViewById(R.id.activity_mainCustomPayloadSwitch);

		starterButton.setOnClickListener(this);
		proxyLayout.setOnClickListener(this);

		payloadLayout = (LinearLayout) findViewById(R.id.activity_mainInputPayloadLinearLayout);
		payloadEdit = (TextInputEditText) findViewById(R.id.activity_mainInputPayloadEditText);
		connectionCardview = (CardView) findViewById(R.id.connection_cardView);
		configMsgLayout = (CardView) findViewById(R.id.configMSGCardView);
		configMsgText = (TextView) findViewById(R.id.configMSGText);


		sslLayout.setOnClickListener(new OnClickListener(){
				SharedPreferences prefs = mConfig.getPrefsPrivate();
				@Override
				public void onClick(View p1) {
					if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
						doSaveData();

						DialogFragment fragSni = new CustomSniFragment();
						fragSni.show(getSupportFragmentManager(), "customSni");
					}
				}
			});
		// fix bugs
		if (mConfig.getPrefsPrivate().getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			if (mConfig.getPrefsPrivate().getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				inputPwUser.setText(mConfig.getPrivString(Settings.USUARIO_KEY));
				inputPwPass.setText(mConfig.getPrivString(Settings.SENHA_KEY));
			}
		}
		else {
			payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
		}

		//tunnelmode_layout.setOnCheckedChangeListener(this);
		metodoConexaoRadio.setOnCheckedChangeListener(this);
		customPayloadSwitch.setOnCheckedChangeListener(this::onCheckedChanged);
		inputPwShowPass.setOnClickListener(this);
	}




	@Override
	public void onRewardedVideoAdLoaded() {
		if (mRewardedVideoAd.isLoaded()){
			mRewardedVideoAd.show();
		}
		pDialog.dismiss();
	}

	@Override
	public void onRewardedVideoAdOpened() {

	}

	@Override
	public void onRewardedVideoStarted() {

	}

	@Override
	public void onRewardedVideoAdClosed() {
		Light.success(main_ly, getString(R.string.ad_done), Snackbar.LENGTH_LONG).show();

	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
		Toast.makeText(SocksHttpMainActivity.this, "Thanks for clicking💙💙💙", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {

		cLoadAdsa();
		pDialog.dismiss();

	}

	private void initComponent() {

        // input section
		nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        bt_toggle_input = (ImageButton) findViewById(R.id.bt_toggle_input);
        ly_hide_input = (LinearLayout) findViewById(R.id.configmsgheader);
        lyt_expand_input = (View) findViewById(R.id.lyt_expand_input);
        lyt_expand_input.setVisibility(View.GONE);

        bt_toggle_input.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					toggleSectionInput(bt_toggle_input);
				}
			});

        ly_hide_input.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					toggleSectionInput(bt_toggle_input);
				}
			});

    }



    private void toggleSectionInput(View view) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt_expand_input, new ViewAnimation.AnimListener() {
					@Override
					public void onFinish() {
						ViewAnimation.nestedScrollTo(nested_scroll_view, lyt_expand_input);
					}
				});
        } else {
            ViewAnimation.collapse(lyt_expand_input);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

	private void doUpdateLayout() {
		SharedPreferences prefs = mConfig.getPrefsPrivate();
		SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();
		boolean isRunning = SkStatus.isTunnelActive();
		int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);

		setStarterButton(starterButton, this);
		setPayloadSwitch(tunnelType, !prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true));

		String proxyStr = getText(R.string.no_value).toString();

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			proxyStr = "*******";
			proxyLayout.setEnabled(false);
			customPayloadSwitch.setEnabled(false);
			payloadLayout.setVisibility(View.VISIBLE);
			payloadLayout.setEnabled(false);
			tunnel_dialog_layout.setEnabled(false);
		}
		else {
			String proxy = mConfig.getPrivString(Settings.PROXY_IP_KEY);

			if (proxy != null && !proxy.isEmpty())
				proxyStr = String.format("%s:%s", proxy, mConfig.getPrivString(Settings.PROXY_PORTA_KEY));
			proxyLayout.setEnabled(!isRunning);
		} 

		proxyText.setText(proxyStr);
		String sniStr = getText(R.string.no_value).toString();


		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			sniStr = "*******";
			sslLayout.setEnabled(false);
			payloadLayout.setVisibility(View.VISIBLE);
			payloadLayout.setEnabled(false);
			customPayloadSwitch.setEnabled(false);
			tunnel_dialog_layout.setEnabled(false);
		}
		else {
			String sni = mConfig.getPrivString(Settings.CUSTOM_SNI);

			if (sni != null && !sni.isEmpty())
				sniStr = String.format(mConfig.getPrivString(Settings.CUSTOM_SNI));
			sslLayout.setEnabled(!isRunning);
		} 

		sslText.setText(sniStr);
		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			sniStr = "*******";
			proxyStr = "*******";
			sslLayout.setEnabled(false);
			payloadLayout.setVisibility(View.VISIBLE);
			payloadLayout.setEnabled(false);
			customPayloadSwitch.setEnabled(false);
			tunnel_dialog_layout.setEnabled(false);
		}
		else {
			String sni = mConfig.getPrivString(Settings.CUSTOM_SNI);

			if (sni != null && !sni.isEmpty())
				sniStr = String.format(mConfig.getPrivString(Settings.CUSTOM_SNI));
			sslLayout.setEnabled(!isRunning);
			String proxy = mConfig.getPrivString(Settings.PROXY_IP_KEY);

			if (proxy != null && !proxy.isEmpty())
				proxyStr = String.format("%s:%s", proxy, mConfig.getPrivString(Settings.PROXY_PORTA_KEY));
			proxyLayout.setEnabled(!isRunning);
		}

		if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
			Menu menuNav = drawerNavigationView.getMenu();
			settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
			settingsSSH.setTitle(R.string.slowdns_configuration);
		} else {
			Menu menuNav = drawerNavigationView.getMenu();
			settingsSSH = menuNav.findItem(R.id.miSettingsSSH);
			settingsSSH.setTitle(R.string.settings_ssh);
		}

		switch (tunnelType) {
			case Settings.bTUNNEL_TYPE_SSH_DIRECT:
				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					connectionCardview.setVisibility(View.VISIBLE);
					tunnelInfo.setText(getString(R.string.direct) + getString(R.string.custom_payload1));

				} else {
					connectionCardview.setVisibility(View.GONE);
					tunnelInfo.setText(getString(R.string.direct));
				} 	
				break;

			case Settings.bTUNNEL_TYPE_SSH_PROXY:
				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					connectionCardview.setVisibility(View.VISIBLE);
					tunnelInfo.setText(getString(R.string.http) + getString(R.string.custom_payload1));
					proxyLayout.setVisibility(View.VISIBLE);

				} else {
					connectionCardview.setVisibility(View.VISIBLE);
					proxyLayout.setVisibility(View.VISIBLE);
					payloadLayout.setVisibility(View.GONE);
					tunnelInfo.setText(getString(R.string.http));
				} 	
				break;

			case Settings.bTUNNEL_TYPE_SSL_TLS:
				connectionCardview.setVisibility(View.VISIBLE);
				payloadLayout.setVisibility(View.GONE);
				proxyLayout.setVisibility(View.GONE);
				sslLayout.setVisibility(View.VISIBLE);
			    tunnelInfo.setText(getString(R.string.ssl));
				break;

			case Settings.bTUNNEL_TYPE_PAY_SSL:
				if (!prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
					connectionCardview.setVisibility(View.VISIBLE);
					tunnelInfo.setText(getString(R.string.tls) + getString(R.string.custom_payload1));
					sslLayout.setVisibility(View.VISIBLE);

				} else {

					connectionCardview.setVisibility(View.VISIBLE);
					payloadLayout.setVisibility(View.GONE);
					proxyLayout.setVisibility(View.GONE);
					tunnelInfo.setText(getString(R.string.ssl));
				}
				break;

			case Settings.bTUNNEL_TYPE_SLOWDNS:
				connectionCardview.setVisibility(View.GONE);		
				tunnelInfo.setText(getString(R.string.slowdns));

				break;

		}

		int msgVisibility = View.GONE;
		int loginVisibility = View.GONE;
		String msgText = "";
		boolean enabled_radio = !isRunning;

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {

			if (prefs.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				loginVisibility = View.VISIBLE;

				inputPwUser.setText(mConfig.getPrivString(Settings.USUARIO_KEY));
				inputPwPass.setText(mConfig.getPrivString(Settings.SENHA_KEY));

				inputPwUser.setEnabled(!isRunning);
				inputPwPass.setEnabled(!isRunning);
				inputPwShowPass.setEnabled(!isRunning);

				//inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}

			String msg = mConfig.getPrivString(Settings.CONFIG_MENSAGEM_KEY);
			if (!msg.isEmpty()) {
				msgText = msg.replace("\n", "<br/>");
				msgVisibility = View.VISIBLE;
			}

			if (mConfig.getPrivString(Settings.PROXY_IP_KEY).isEmpty() ||
				mConfig.getPrivString(Settings.PROXY_PORTA_KEY).isEmpty()) {
				enabled_radio = false;
			}
		}

		loginLayout.setVisibility(loginVisibility);
		configMsgText.setText(msgText.isEmpty() ? "" : Html.fromHtml(msgText));
		configMsgLayout.setVisibility(msgVisibility);

		// desativa/ativa radio group
		for (int i = 0; i < metodoConexaoRadio.getChildCount(); i++) {
			metodoConexaoRadio.getChildAt(i).setEnabled(enabled_radio);
		}
		/*for (int i = 0; i < tunnelmode_layout.getChildCount(); i++) {
		 tunnelmode_layout.getChildAt(i).setEnabled(enabled_radio);
		 }*/

	}


	private synchronized void doSaveData() {
		SharedPreferences prefs = mConfig.getPrefsPrivate();
		SharedPreferences.Editor edit = prefs.edit();

		if (mainLayout != null && !isFinishing())
			mainLayout.requestFocus();

		if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			if (payloadEdit != null && !prefs.getBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, true)) {
				edit.putString(Settings.CUSTOM_PAYLOAD_KEY, payloadEdit.getText().toString());
			}
		}
		else {
			if (prefs.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				edit.putString(Settings.USUARIO_KEY, inputPwUser.getEditableText().toString());
				edit.putString(Settings.SENHA_KEY, inputPwPass.getEditableText().toString());
			}
		}

		int tunnelType = prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
		if (tunnelType == Settings.bTUNNEL_TYPE_SLOWDNS) {
			edit.putString(Settings.SERVIDOR_KEY, "127.0.0.1");
			edit.putString(Settings.SERVIDOR_PORTA_KEY, "8989");

		}

		edit.apply();
	}

	private void pauseTimer() {
		mCountDownTimer.cancel();
		mTimerRunning = false;
		updateWatchInterface();
	}
	private void resetTimer() {
		mTimeLeftInMillis = mStartTimeInMillis;
		updateCountDownText();
		updateWatchInterface();
	}

	private void updateWatchInterface() {
		if (mTimerRunning) {
			mEditTextInput.setVisibility(View.GONE);
			mButtonSet.setVisibility(View.GONE);
			mButtonReset.setVisibility(View.GONE);
			mButtonStartPause.setText("Auto Reconnect");
		} else {
			mEditTextInput.setVisibility(View.GONE);
			mButtonSet.setVisibility(View.GONE);
			mButtonStartPause.setText("Auto Reconnect");
			if (mTimeLeftInMillis < 1000) {
				mButtonStartPause.setVisibility(View.INVISIBLE);
			} else {
				mButtonStartPause.setVisibility(View.VISIBLE);
			}
			if (mTimeLeftInMillis < mStartTimeInMillis) {
				mButtonReset.setVisibility(View.GONE);
			} else {
				mButtonReset.setVisibility(View.GONE);
			}
		}
	}


	private void updateCountDownText() {
		int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
		int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
		int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
		String timeLeftFormatted;
		if (hours > 0) {
			timeLeftFormatted = String.format(Locale.getDefault(),
											  "%d:%02d:%02d", hours, minutes, seconds);
		} else {
			timeLeftFormatted = String.format(Locale.getDefault(),
											  "%02d:%02d", minutes, seconds);
		}
		mTextViewCountDown.setText(timeLeftFormatted);
	}

	private void setTime(long milliseconds) {
		mStartTimeInMillis = milliseconds;
		resetTimer();

	}

	private void startTimer() {
		mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
		mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {


			@Override
			public void onTick(long millisUntilFinished) {
				mTimeLeftInMillis = millisUntilFinished;
				updateCountDownText();
			}
			@Override
			public void onFinish() {
				mTimerRunning = false;
				updateWatchInterface();
				resetTimer();
				startTimer();
				Toast.makeText(SocksHttpMainActivity.this, "Reconnecting please wait...", Toast.LENGTH_SHORT).show();
				mLogAdapter.clearLog();
				Intent reconTunnel = new Intent(SocksHttpService.TUNNEL_SSH_RESTART_SERVICE);
				LocalBroadcastManager.getInstance(SocksHttpMainActivity.this)
					.sendBroadcast(reconTunnel);

				//just a test👇👇
				//starterButton.performClick();
				// i dont know what to do so i test that 0ne
				// build by renz mortel your bf
				//try this 2 👇👇👇

				///startOrStopTunnel(this);

				//context.startService(startVPN);
			}
		}.start();
		mTimerRunning = true;
		updateWatchInterface();
	}


	/**
	 * Tunnel SSH
	 */

	public void startOrStopTunnel(Activity activity) {
		if (SkStatus.isTunnelActive()) {
			TunnelManagerHelper.stopSocksHttp(activity);
			//mLogAdapter.clearLog();
		}
		else {
			// oculta teclado se vísivel, tá com bug, tela verde
			//Utils.hideKeyboard(activity);

			Settings config = new Settings(activity);

			if (config.getPrefsPrivate()
				.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
				if (inputPwUser.getText().toString().isEmpty() || 
					inputPwPass.getText().toString().isEmpty()) {

					Light.error(main_ly, getString(R.string.error_userpass_empty), Snackbar.LENGTH_LONG).show();
					return;
				}
			}

			Intent intent = new Intent(activity, LaunchVpn.class);
			intent.setAction(Intent.ACTION_MAIN);
			vp.setCurrentItem(1);
			if (config.getHideLog()) {
				intent.putExtra(LaunchVpn.EXTRA_HIDELOG, true);
			}

			activity.startActivity(intent);
		}
	}

	private void setPayloadSwitch(int tunnelType, boolean isCustomPayload) {
		SharedPreferences prefs = mConfig.getPrefsPrivate();

		boolean isRunning = SkStatus.isTunnelActive();

		customPayloadSwitch.setChecked(isCustomPayload);

		if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
			payloadEdit.setEnabled(false);

			if (mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY).isEmpty()) {
				customPayloadSwitch.setEnabled(false);
			}
			else {
				customPayloadSwitch.setEnabled(!isRunning);
				tunnel_dialog_layout.setEnabled(!isRunning);
			}

			if (!isCustomPayload && tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY)
				payloadEdit.setText(Settings.PAYLOAD_DEFAULT);
			else
				payloadEdit.setText("*******");
			//payloadlock.setVisibility(8);
			Viewgone.setVisibility(View.GONE);
		}
		else {
			Viewgone.setVisibility(View.VISIBLE);
			//payloadlock.setVisibility(0);
			customPayloadSwitch.setEnabled(!isRunning);
			tunnel_dialog_layout.setEnabled(!isRunning);
			if (isCustomPayload) {
				payloadEdit.setText(mConfig.getPrivString(Settings.CUSTOM_PAYLOAD_KEY));
				payloadEdit.setEnabled(!isRunning);
			}
			else if (tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
				payloadEdit.setText(Settings.PAYLOAD_DEFAULT);
				payloadEdit.setEnabled(false);
			}
		}

		if (isCustomPayload || tunnelType == Settings.bTUNNEL_TYPE_SSH_PROXY) {
			payloadLayout.setVisibility(View.VISIBLE);
			Viewgone.setVisibility(View.VISIBLE);
		}
		else {
			payloadLayout.setVisibility(View.GONE);
			Viewgone.setVisibility(View.GONE);
		}
	}

	public void setStarterButton(Button starterButton, Activity activity) {
		String state = SkStatus.getLastState();
		boolean isRunning = SkStatus.isTunnelActive();

		if (starterButton != null) {
			int resId;

			SharedPreferences prefsPrivate = new Settings(activity).getPrefsPrivate();

			if (ConfigParser.isValidadeExpirou(prefsPrivate
											   .getLong(Settings.CONFIG_VALIDADE_KEY, 0))) {
				resId = R.string.expired;
				starterButton.setEnabled(false);

				if (isRunning) {
					startOrStopTunnel(activity);
				}
			}
			else if (prefsPrivate.getBoolean(Settings.BLOQUEAR_ROOT_KEY, false) &&
					 ConfigParser.isDeviceRooted(activity)) {
				resId = R.string.blocked;
				starterButton.setEnabled(false);


				Light.error(main_ly, getString(R.string.error_root_detected), Snackbar.LENGTH_LONG).show();

				if (isRunning) {
					startOrStopTunnel(activity);
				}
			}
			else if (SkStatus.SSH_INICIANDO.equals(state)) {
				resId = R.string.stop;
				starterButton.setEnabled(false);
			}
			else if (SkStatus.SSH_PARANDO.equals(state)) {
				resId = R.string.state_stopping;
				starterButton.setEnabled(false);
			}
			else {
				resId = isRunning ? R.string.stop : R.string.start;
				starterButton.setEnabled(true);
			}

			starterButton.setText(resId);
		}
	}



	@Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (mDrawerPanel.getToogle() != null)
			mDrawerPanel.getToogle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerPanel.getToogle() != null)
			mDrawerPanel.getToogle().onConfigurationChanged(newConfig);
    }

	private boolean isMostrarSenha = false;

	@Override
	public void onClick(View p1)
	{
		SharedPreferences prefs = mConfig.getPrefsPrivate();
        boolean isRunning = SkStatus.isTunnelActive();
		switch (p1.getId()) {
			case R.id.activity_starterButtonMain:
				doSaveData();
				startOrStopTunnel(this);
				showInterstitial();
				break;


			case R.id.tunnelCardView:
				if (!isRunning) {
					if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
						startActivity(new Intent(this, TunnelActivity.class));
						if (adsBannerView != null && TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
							adsBannerView.setAdListener(new AdListener() {
									@Override
									public void onAdLoaded() {
										if (adsBannerView != null && !isFinishing()) {
											adsBannerView.setVisibility(View.VISIBLE);
										}
									}
								});
							adsBannerView.postDelayed(new Runnable() {
									@Override
									public void run() {
										// carrega ads interestitial
//										AdsManager.newInstance(getApplicationContext())
//											.loadAdsInterstitial();
										// ads banner
										if (adsBannerView != null && !isFinishing()) {
//											adsBannerView.loadAd(new AdRequest.Builder()
//																 .build());
										}
									}
								}, 5000);
						}
					}
				}
				break;

			case R.id.activity_mainInputProxyLayout:
				if (!prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
					doSaveData();

					DialogFragment fragProxy = new ProxyRemoteDialogFragment();
					fragProxy.show(getSupportFragmentManager(), "proxyDialog");
				}
				break;

			case R.id.activity_mainAutorText:
				String url = "http://t.me/SlipkProjects";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getText(R.string.open_with)));
				break;

			case R.id.activity_mainInputShowPassImageButton:
				isMostrarSenha = !isMostrarSenha;
				if (isMostrarSenha) {
					inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					inputPwShowPass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visibility_black_24dp));
				}
				else {
					inputPwPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					inputPwShowPass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_visibility_off_black_24dp));
				}
				break;
		}
	}



	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		SharedPreferences prefs = mConfig.getPrefsPrivate();
		SharedPreferences.Editor edit = prefs.edit();

		switch (p1.getId()) {
			case R.id.activity_mainCustomPayloadSwitch:
				edit.putBoolean(Settings.PROXY_USAR_DEFAULT_PAYLOAD, !p2);
				setPayloadSwitch(prefs.getInt(Settings.TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT), p2);
				break;
		}

		edit.apply();

		doSaveData();
	}
	
	protected void showBoasVindas() {
		AlertDialog dialog = new AlertDialog.Builder(this).
			create();
		dialog.setIcon(R.drawable.app_launch);
		dialog.setTitle("Join Us On Telegram?");
		dialog.setMessage("We have a telegram support channel where we post and discuss about settings, new features and also assist our users. \n\nWould you like to join us there?");
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Join",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{

					String url = "https://t.me/OCTinjector";
					Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(Intent.createChooser(intent3, getText(R.string.open_with)));
					Light.normal(main_ly, "Please make you join", Snackbar.LENGTH_LONG).show();

					// ok
				}
			});

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Later",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// minimiza app

					dialog.dismiss();

				}
			}
		);


		dialog.show();
	}
	
	
	
	
	private void showVersion() {
		PackageInfo pinfo = Utils.getAppInfo(this);
		if (pinfo != null) {
			String version_nomes = pinfo.versionName;
			int version_codes = pinfo.versionCode;
		AlertDialog dialog = new AlertDialog.Builder(this).
			create();
			dialog.setIcon(R.drawable.app_launch);
			dialog.setTitle("What's new inㅤ"+"(v." + version_nomes + ") " + "Build " + "(" + version_codes + ")");
			dialog.setMessage("- BETA Version \n\nThank you for downloading our app.❤");
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

	@Override
	public void updateState(final String state, String msg, int localizedResId, final ConnectionStatus level, Intent intent)
	{
		mHandler.post(new Runnable() {
				@Override
				public void run() {
					doUpdateLayout();
					if (SkStatus.isTunnelActive()){

						if (level.equals(ConnectionStatus.LEVEL_CONNECTED)){
							status.setText(R.string.connected);
							toastutil.showSuccessToast(getString(R.string.state_connected));

							showInterstitial();
						}

						if (level.equals(ConnectionStatus.LEVEL_NOTCONNECTED)){
							status.setText(R.string.servicestop);

							showInterstitial();
						}	

						if (level.equals(ConnectionStatus.LEVEL_CONNECTING_SERVER_REPLIED)){
							status.setText(R.string.authenticating);
						}		

						if (level.equals(ConnectionStatus.LEVEL_CONNECTING_NO_SERVER_REPLY_YET)){
							status.setText(R.string.connecting);
						}			
						if (level.equals(ConnectionStatus.LEVEL_AUTH_FAILED)){
							status.setText(R.string.authfailed);
						}					
						if (level.equals(ConnectionStatus.UNKNOWN_LEVEL)){
							status.setText(R.string.disconnected);
							toastutil.showConfusingToast(getString(R.string.state_disconnected));
						}				
						//if (level.equals(ConnectionStatus.LEVEL_RECONNECTING)){
						//		status.setText(R.string.reconnecting);
					}				
					if (level.equals(ConnectionStatus.LEVEL_NONETWORK)){
						status.setText(R.string.nonetwork);
						toastutil.showDefaultToast(getString(R.string.nonetwork));
					}			
				}});

		switch (state) {
			case SkStatus.SSH_CONECTADO:
				// carrega ads banner
				if (adsBannerView != null && TunnelUtils.isNetworkOnline(SocksHttpMainActivity.this)) {
					adsBannerView.setAdListener(new AdListener() {
							@Override
							public void onAdLoaded() {
								if (adsBannerView != null && !isFinishing()) {
									adsBannerView.setVisibility(View.VISIBLE);
								}
							}
						});
					adsBannerView.postDelayed(new Runnable() {
							@Override
							public void run() {
								// carrega ads interestitial
//								AdsManager.newInstance(getApplicationContext())
//									.loadAdsInterstitial();
								// ads banner
								if (adsBannerView != null && !isFinishing()) {
//									adsBannerView.loadAd(new AdRequest.Builder()
//														 .build());
								}
							}
						}, 5000);
				}
				break;
		}
	}


	/**
	 * Recebe locais Broadcast
	 */

	private BroadcastReceiver mActivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
                return;

            if (action.equals(UPDATE_VIEWS) && !isFinishing()) {
				doUpdateLayout();
			}
			else if (action.equals(OPEN_LOGS)) {

			}
        }
    };


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerPanel.getToogle() != null && mDrawerPanel.getToogle().onOptionsItemSelected(item)) {
            return true;
        }

		// Menu Itens
		switch (item.getItemId()) {

			case R.id.settingsmain:
				Intent intentSettings = new Intent(this, ConfigGeralActivity.class);
				//intentSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentSettings);
				break;
			
			case R.id.miLimparConfig:
				if (!SkStatus.isTunnelActive()) {
					DialogFragment dialog = new ClearConfigDialogFragment();
					dialog.show(getSupportFragmentManager(), "alertClearConf");
					Light.info(main_ly, getString(R.string.error_tunnel_service_execution), Snackbar.LENGTH_LONG).show();
				} else {

					Light.info(main_ly, getString(R.string.error_tunnel_service_execution), Snackbar.LENGTH_LONG).show();
				}
				break;

			/*case R.id.guide:
				if (mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				} else {
					guides();
				}
				break;*/

				/*case R.id.tunneloptions:
				 tunnel_options();
				 break;*/

			case R.id.login_menu:
				askForPW(R.string.password);
				break;

			case R.id.miSettingImportar:
				if (mRewardedVideoAd.isLoaded()) {
					mRewardedVideoAd.show();
				}
				Intent intentImport = new Intent(this, ConfigImportFileActivity.class);
				startActivity(intentImport);
				break;

			case R.id.miSettingExportar:
				if (mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				}
				SharedPreferences prefs = mConfig.getPrefsPrivate();
				if (prefs.getBoolean(Settings.CONFIG_PROTEGER_KEY, false)) {
					Light.error(main_ly, getString(R.string.error_settings_blocked), Snackbar.LENGTH_LONG).show();
				}
				else {
					Intent intentExport = new Intent(this, ConfigExportFileActivity.class);
					startActivity(intentExport);
				}
				break;

				// logs opções
			case R.id.Share_Logs:
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,SkStatus.CopyLogs());
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, SkStatus.CopyLogs());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
				break;

			case R.id.CopyLogs:
				ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("Log Entry", SkStatus.CopyLogs());
				clipboard.setPrimaryClip(clip);
				Toast.makeText(this, "Logs Copied", Toast.LENGTH_SHORT).show();

				break;

				// logs opções
			case R.id.miLimparLogs:
				mLogAdapter.clearLog();
				break;

			case R.id.miExit:
				if (Build.VERSION.SDK_INT >= 16) {
					finishAffinity();
				}

				System.exit(0);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void passlog() {
		Settings config = new Settings(this);

		if (config.getPrefsPrivate()
			.getBoolean(Settings.CONFIG_INPUT_PASSWORD_KEY, false)) {
			if (inputPwUser.getText().toString().isEmpty() || 
				inputPwPass.getText().toString().isEmpty()) {

				toastutil.showErrorToast(getString(R.string.error_userpass_empty));
				return;
			}
		}

		Intent intent = new Intent(this, LaunchVpn.class);
		intent.setAction(Intent.ACTION_MAIN);
		vp.setCurrentItem(1);
		if (config.getHideLog()) {
			intent.putExtra(LaunchVpn.EXTRA_HIDELOG, false);
		}

		startActivity(intent);

	}

	private static final int START_VPN_PROFILE = 70;
	private void askForPW(final int type) {
		AlertDialog.Builder passdialog = new AlertDialog.Builder(this);
		//   passdialog.setTitle(R.string.title_auth);
		//     passdialog.setMessage(R.string.auth_message);
		//passdialog.setOnCancelListener(this);

        @SuppressLint("InflateParams")
			final View userpwlayout = getLayoutInflater()
			.inflate(R.layout.userpass, null, false);

        ((EditText) userpwlayout.findViewById(R.id.username)).setText(mConfig.getPrivString(Settings.USUARIO_KEY));
        ((EditText) userpwlayout.findViewById(R.id.password)).setText(mConfig.getPrivString(Settings.SENHA_KEY));
        ((CheckBox) userpwlayout.findViewById(R.id.save_password)).setChecked(true);
        ((ImageButton) userpwlayout.findViewById(R.id.show_password)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					isMostrarSenha = !isMostrarSenha;
					if (isMostrarSenha) {
						((EditText) userpwlayout.findViewById(R.id.password)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
						((ImageButton) userpwlayout.findViewById(R.id.show_password)).setImageDrawable(ContextCompat.getDrawable(SocksHttpMainActivity.this, R.drawable.ic_visibility_off_black_24dp));
					}
					else {
						((EditText) userpwlayout.findViewById(R.id.password)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
						((ImageButton) userpwlayout.findViewById(R.id.show_password)).setImageDrawable(ContextCompat.getDrawable(SocksHttpMainActivity.this, R.drawable.ic_visibility_black_24dp));
					}
				}
			});

        passdialog.setView(userpwlayout);

        passdialog.setPositiveButton(android.R.string.ok,
			new DialogInterface.OnClickListener() {

				private String mTransientAuthPW;
				@Override
				public void onClick(DialogInterface passdialog, int which) {

					if (type == R.string.password) {
						SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();

						String mUsername = ((EditText) userpwlayout.findViewById(R.id.username)).getText().toString();

						edit.putString(Settings.USUARIO_KEY, mUsername);

						String pw = ((EditText) userpwlayout.findViewById(R.id.password)).getText().toString();
						if (((CheckBox) userpwlayout.findViewById(R.id.save_password)).isChecked()) {
							edit.putString(Settings.SENHA_KEY, pw);
						} else {
							edit.remove(Settings.SENHA_KEY);
							mTransientAuthPW = pw;
						}

						edit.apply();
					}

					if (mTransientAuthPW != null)
						PasswordCache.setCachedPassword(null, PasswordCache.AUTHPASSWORD, mTransientAuthPW);
					onActivityResult(START_VPN_PROFILE, Activity.RESULT_OK, null);
				}

			});
        passdialog.setNegativeButton(android.R.string.cancel,
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface passdialog, int which) {
					SkStatus.updateStateString("USER_VPN_PASSWORD_CANCELLED", "", R.string.state_user_vpn_password_cancelled,
											   ConnectionStatus.LEVEL_NOTCONNECTED);
					passdialog.dismiss();
				}
			});

        passdialog.create().show();
	}

	@Override
	public void onBackPressed() {

		showExitDialog();
	}

	@Override
    public void onResume() {
        super.onResume();

		showInterstitial();

		//doSaveData();
		//doUpdateLayout();

		SkStatus.addStateListener(this);

		if (adsBannerView != null) {
			adsBannerView.resume();
		}
    }

	private void showInterstitial(){
		if (mInterstitialAd.isLoaded()){
			if(showAd) {
				mInterstitialAd.show();
				createTimer(60000);
				showAd = false;
			}
		} else {
			loadInterstitial();
		}
	}


	boolean showAd=true;
	private void loadInterstitial(){
//		mInterstitialAd.loadAd(new AdRequest.Builder().build());
		Log.d(TAG, "///Loading interstitial ad..");
	}

	private CountDownTimer countDownTimer;
	private long timerMilliseconds;

	private void createTimer(final long milliseconds) {
		// Create the game timer, which counts down to the end of the level
		// and shows the "retry" button.
		if (countDownTimer != null) {
			countDownTimer.cancel();
		}

		countDownTimer = new CountDownTimer(milliseconds, 1000) {
			@Override
			public void onTick(long millisUnitFinished) {
				Log.e(TAG,"running timer "+millisUnitFinished);
				timerMilliseconds = millisUnitFinished;
			}

			@Override
			public void onFinish() {
//				long time = 60*1;
				showAd = true;
				Log.e(TAG,"running timer stopped "+showAd);

			}
		};

		countDownTimer.start();
	}

	public void mInterstitialAdLeftApplication() {
		Toast.makeText(SocksHttpMainActivity.this, "Thanks for clicking💙💙💙", Toast.LENGTH_LONG).show();

    }

	@Override
	protected void onPause()
	{
		super.onPause();

		doSaveData();

		SkStatus.removeStateListener(this);

		if (adsBannerView != null) {
			adsBannerView.pause();
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();



		LocalBroadcastManager.getInstance(this)
			.unregisterReceiver(mActivityReceiver);

		if (adsBannerView != null) {
			adsBannerView.destroy();
		}
	}


	/**
	 * DrawerLayout Listener
	 */

	@Override
	public void onDrawerOpened(View view) {

	}

	@Override
	public void onDrawerClosed(View view) {

	}

	@Override
	public void onDrawerStateChanged(int stateId) {}
	@Override
	public void onDrawerSlide(View view, float p2) {}


	/**
	 * Utils
	 */

	public static void updateMainViews(Context context) {
		Intent updateView = new Intent(UPDATE_VIEWS);
		LocalBroadcastManager.getInstance(context)
			.sendBroadcast(updateView);
	}

	public void showExitDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this).
			create();
		dialog.setTitle(getString(R.string.attention));
		dialog.setMessage(getString(R.string.alert_exit));

		dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.
																	string.exit),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Utils.exitAll(SocksHttpMainActivity.this);
				}
			}
		);

		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.
																	string.minimize),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// minimiza app
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(startMain);
				}
			}
		);

		dialog.show();
	}
}

