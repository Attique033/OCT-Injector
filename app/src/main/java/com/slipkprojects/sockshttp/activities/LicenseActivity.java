package com.slipkprojects.sockshttp.activities;


import android.content.*;
import android.content.res.*;
import android.os.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.webkit.*;

import com.slipkprojects.sockshttp.R;

import java.io.*;

public class LicenseActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView htmlWebView = (WebView)findViewById(R.id.license_content);
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        String htmlFilename = "license.html";
        AssetManager mgr = getBaseContext().getAssets();
        try {
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            String htmlContentInStringFormat = StreamToString(in);
            in.close();
            htmlWebView.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String StreamToString(InputStream in) throws IOException {
        if (in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        }
        finally {
        }
        return writer.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.addFlags(131072);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}


