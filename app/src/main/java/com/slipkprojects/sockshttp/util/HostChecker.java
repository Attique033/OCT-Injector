package com.slipkprojects.sockshttp.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import android.app.*;
import android.preference.*;
import java.io.*;
import android.text.*;

import com.slipkprojects.sockshttp.R;

public class HostChecker extends Activity  {
    private static final String TAG = "HostChecker";
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private Button btnSubmit;
    private EditText bugHost;
    private String c;
    private CheckBox direct;
    private String domain;
    private String ipProxy;
    private ListView list;
    private LinearLayout main;
    private String method;
    private String portProxy;
    private EditText proxy;
    private SharedPreferences sp;
    private Spinner spinner;


    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
        savePreferences("hostChecker", this.bugHost.getText().toString().trim());
        savePreferences("proxyChecker", this.proxy.getText().toString().trim());
    }

	@Override
	protected void onStart()
	{
		String str =(new char[]{72,111,115,116,32,67,104,101,99,107,101,114,}.toString());
		super.onStart();
	}

    public void onResume() {
        super.onResume();
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(this);
        }
        bugHost.setText(this.sp.getString("hostChecker", ""));
        proxy.setText(this.sp.getString("proxyChecker", ""));
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.main);
        this.main = (LinearLayout) findViewById(R.id.mainV);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.c = sharedPreferences.getString("Rise", "");
        this.list = (ListView) findViewById(R.id.listLogs);
        this.arrayList = new ArrayList();
        this.adapter = new ArrayAdapter(getApplicationContext(), R.layout.logtext, this.arrayList);
        this.list.setAdapter(this.adapter);
        this.bugHost = (EditText) findViewById(R.id.editTextUrl);
        this.proxy = (EditText) findViewById(R.id.editTextProxy);
        this.proxy.setVisibility(0);
        this.spinner = (Spinner) findViewById(R.id.spinnerRequestMethod);
        this.spinner.getSelectedItem().toString();
        this.direct = (CheckBox) findViewById(R.id.checkBoxDirectRequest);
        this.direct.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					if (direct.isChecked()) {
						SharedPreferences.Editor	edit = sharedPreferences.edit();
						edit.putBoolean("Xen", true);
						edit.commit();
						proxy.setEnabled(false);
						return;
					}
					SharedPreferences.Editor edit = sharedPreferences.edit();
					edit.putBoolean("Xen", false);
					edit.commit();
					proxy.setEnabled(true);
				}
			});
        this.btnSubmit = (Button) findViewById(R.id.buttonSearch);
        this.btnSubmit.setOnClickListener(new View.OnClickListener() {


				@Override
				public void onClick(View view) {
					if (bugHost.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(),"Please Fill The URL",0).show();
					} else if (direct.isChecked()) {
						start();
					} else if (proxy.getText().toString().equals("")) {
						Toast.makeText(getApplicationContext(),"fill the proxy if you want to check or select the direct to check the url",0).show();
					} else {
						start();
					}
				}
			});
        if (sharedPreferences.getBoolean("Xen", false)) {
            this.direct.setChecked(true);
            this.proxy.setEnabled(false);
            return;
        }
        this.direct.setChecked(false);
        this.proxy.setEnabled(true);
    }

    public final void showMessage(String str) {
        if (getApplicationContext() != null) {

            Toast.makeText(getApplicationContext(),(Html.fromHtml(str)), 0).show();
        }
    }

    private void savePreferences(String str, String str2) {
        if (this.sp == null) {
            this.sp = PreferenceManager.getDefaultSharedPreferences(this);
        }
        SharedPreferences.Editor edit = this.sp.edit();
        edit.putString(str, str2);
        edit.commit();
    }




    public void start() {
        String editable = this.bugHost.getText().toString();
        String obj = this.spinner.getSelectedItem().toString();
        this.btnSubmit.setEnabled(false);

        String trim = this.proxy.getText().toString().trim();
        if (trim.contains(":")) {
            String[] split = trim.split(":");
            this.ipProxy = split[0];
            this.portProxy = split[1];
        } else {
            this.ipProxy = trim;
            this.portProxy = "80";
        }
        if (this.direct.isChecked()) {
            this.arrayList.add(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(obj).append(" - ").toString()).append("URL: http://").toString()).append(editable).toString());
            this.arrayList.add(new StringBuffer().append(new StringBuffer().append(editable).append(" - ").toString()).append("Direct").toString());
            this.adapter.notifyDataSetChanged();
        } else {
            this.arrayList.add(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(obj).append(" - ").toString()).append("URL: http://").toString()).append(editable).toString());
            this.arrayList.add(new StringBuffer().append(new StringBuffer().append(editable).append(" - ").toString()).append(this.ipProxy).toString());
            this.adapter.notifyDataSetChanged();
        }
        new Worker2(new WorkerAction() {
				private String a;
				private String b;
				private String c;
				private HttpURLConnection conn;
				private String d;
				private String e;
				private String f;
				private String g;
				private String h;
				private String i;
				private String j;
				private String k;
				String response = "";

				public void runLast() {
					Log.d(HostChecker.TAG, "DONE");
					if (this.response.contains("\n")) {
						String[] split = this.response.split("\n");
						for (String add : split) {
							arrayList.add(add);
							adapter.notifyDataSetChanged();
						}
						arrayList.add("");
						arrayList.add("Stopped");
						adapter.notifyDataSetChanged();
						showMessage("Success");
						btnSubmit.setEnabled(true);
						return;
					}
					arrayList.add("");
					arrayList.add("Stopped");
					arrayList.add(("Please make sure that your connected to (Mobile data)"));
					adapter.notifyDataSetChanged();
					showMessage("<u><#ffffff>Please connect to internet</#ffffff></u>");
					btnSubmit.setEnabled(true);

					return;
				}

				public void runFirst() {
					try {
						Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(ipProxy, Integer.parseInt(portProxy)));
						domain = bugHost.getText().toString();
						method = spinner.getSelectedItem().toString();
						if (direct.isChecked()) {
							this.conn = (HttpURLConnection) new URL(new StringBuffer().append("http://").append(domain).toString()).openConnection();
							this.conn.setRequestMethod(method);
							this.conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36");
							this.conn.setReadTimeout(2000);
							this.conn.setConnectTimeout(3000);
							this.conn.setDoInput(true);
							this.conn.connect();
						} else {
							this.conn = (HttpURLConnection) new URL(new StringBuffer().append("http://").append(domain).toString()).openConnection(proxy);
							this.conn.setRequestMethod(method);
							this.conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36");
							this.conn.setReadTimeout(2000);
							this.conn.setConnectTimeout(3000);
							this.conn.setDoInput(true);
							this.conn.connect();
						}
						for (Entry entry : this.conn.getHeaderFields().entrySet()) {
							if (((String) entry.getKey()) == null) {
								this.response = new StringBuffer().append(this.response).append(new StringBuffer().append(((List) entry.getValue()).toString().replace("[", "").replace("]", "")).append("\n").toString()).toString();
							} else {
								this.response = new StringBuffer().append(this.response).append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append((String) entry.getKey()).append(" : ").toString()).append(((List) entry.getValue()).toString().replace("[", "").replace("]", "")).toString()).append("\n").toString()).toString();
							}
						}
					} catch (Exception e) {
					}
				}
			}, this).execute(new Void[0]);
    }
}
