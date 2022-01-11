package com.slipkprojects.sockshttp.util;

import android.os.*;
import androidx.appcompat.app.*;
import android.view.*;
import android.widget.*;
import java.net.*;
import java.util.*;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.util.InetAddressUtils;
import com.slipkprojects.sockshttp.R;
import androidx.appcompat.widget.Toolbar;
import android.graphics.Color;

public class IpHunter extends AppCompatActivity 
{

	private TextView xIp;
	private ListView xLv;
	private ArrayList<String> xLItems;
	private ArrayAdapter<String> xAdapter;
	private EditText xEdt;
	private ToggleButton toggle;
	private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iphunter);

		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
		mToolbar.setSubtitle("IP Hunter");
		mToolbar.setSubtitleTextColor(Color.WHITE);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		xIp = (TextView)findViewById(R.id.ip);

		xEdt =(EditText)findViewById(R.id.edt);

		xLv = (ListView) findViewById(R.id.lv);
        xLItems = new ArrayList<String>();
        String a =new String(new byte[]{79,67,84,32,73,110,106,101,99,116,111,114,32,45,32,73,80,32,72,117,110,116,101,114,});
		xLItems.add(a);
        xAdapter = new ArrayAdapter<String>(this,
											R.layout.list_text,xLItems);
        xLv.setAdapter(xAdapter);
		String b = new String(new byte[]{79,67,84,32,73,110,106,101,99,116,111,114,32,45,32,73,80,32,72,117,110,116,101,114,});
		Toast.makeText(getApplicationContext(),b,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),b,Toast.LENGTH_LONG).show();

		toggle = (ToggleButton) findViewById(R.id.toggle);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

					xAdapter.notifyDataSetChanged();
					if (isChecked) {
						// The toggle is enabled
						xEdt.setEnabled(false);	

						if(xIp.getText().toString().startsWith(xEdt.getText().toString())){
							xLItems.add("Starting...");
							xLItems.add("Current IP: "+xIp.getText().toString());
							xLItems.add("Success  Found IP: "+xIp.getText().toString());
							xLItems.add("Stopped");
							xEdt.setEnabled(true);
							Toast.makeText(getApplicationContext(),"Founded IP:"+xIp.getText().toString(),Toast.LENGTH_LONG).show();
							toggle.setChecked(false);

							xEdt.setEnabled(true);
						}else{
							xLItems.add("Starting...");
							xLItems.add("Current IP: "+xIp.getText().toString());
							xLItems.add("Please Turn OFF/ON mobile data network!");

						}

					} else {
						// The toggle is disabled
						xLItems.add("Stopped");
						xEdt.setEnabled(true);
					}
				}
			});

		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						Thread.sleep(1000);
						runOnUiThread(new Runnable() {
								@Override
								public void run() {
									xIp.setText(getIpAddress());
									String	s2 = xEdt.getText().toString();
									if(s2.isEmpty()||s2.isEmpty()){
										toggle.setEnabled(false);
									}else {
										toggle.setEnabled(true);
									}
								}
							});
					}
				} catch (InterruptedException e) {
				}
			}
		};

		t.start();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu, menu);
    	return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.clrlog:
				xAdapter.clear();
    			Toast.makeText(getApplicationContext(),"Log Cleared",Toast.LENGTH_LONG).show();
				return true;   
    	}
    	return false;
    }

	public static String getIpAddress() {
		String xHostIfAvailable = "127.0.0.1";
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (isIPv4) 
							return sAddr;
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return xHostIfAvailable;
    }

	@Override
	public boolean onSupportNavigateUp()
	{
		onBackPressed();
		return true;
	}

}
