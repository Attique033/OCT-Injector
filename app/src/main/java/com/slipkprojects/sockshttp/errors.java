package com.slipkprojects.sockshttp;

import android.os.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import android.text.*;
import android.widget.*;
import android.widget.LinearLayout.*;

import androidx.appcompat.widget.Toolbar;

public class errors extends AppCompatActivity {
    TextView error;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        setContentView(linearLayout);
        toolbar = new Toolbar(this);
        setSupportActionBar(this.toolbar);
        toolbar.setTitle(Html.fromHtml("KF Tech VPN"));
		toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        ScrollView sv = new ScrollView(this);
        TextView error = new TextView(this);
        sv.addView(error);
        linearLayout.addView(toolbar);
        linearLayout.addView(sv);
        error.setText(getIntent().getStringExtra("error"));
    }
}






