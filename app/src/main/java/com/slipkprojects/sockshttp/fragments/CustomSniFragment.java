package com.slipkprojects.sockshttp.fragments;


import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.slipkprojects.sockshttp.*;
import android.view.View;
import androidx.appcompat.app.AlertDialog;

import android.widget.EditText;
import com.slipkprojects.sockshttp.SocksHttpMainActivity;
import com.slipkprojects.sockshttp.R;
import android.view.ViewGroup;
import android.widget.Button;
import com.slipkprojects.ultrasshservice.config.Settings;

import androidx.appcompat.widget.AppCompatCheckBox;
import android.content.SharedPreferences;
import android.widget.TextView;

public class CustomSniFragment extends DialogFragment
implements View.OnClickListener {

    private Settings mConfig;

    private boolean usarProxyAutenticacao;
    private boolean usarPayloadPadrao;

    private EditText customSNI;
	private TextView custom;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        mConfig = new Settings(getContext());
        SharedPreferences prefs = mConfig.getPrefsPrivate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getDialog().setCanceledOnTouchOutside(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        LayoutInflater li = LayoutInflater.from(getContext());
        View view = li.inflate(R.layout.custom_sni, null); 

        customSNI = view.findViewById(R.id.fragment_custom_sni);

        Button cancelButton = view.findViewById(R.id.fragment_sni_remoteCancelButton);
        Button saveButton = view.findViewById(R.id.fragment_sni_remoteSaveButton);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        customSNI.setText(mConfig.getPrivString(Settings.CUSTOM_SNI));

        //setCancelable(false);

        return new AlertDialog.Builder(getActivity())
            . setTitle("Server Name Indicator (SNI)")
            .setView(view)

            . show();
    }

    /**
     * onClick implementação
     */

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.fragment_sni_remoteSaveButton:
                String mCustomSNI = customSNI.getEditableText().toString();

                SharedPreferences.Editor edit = mConfig.getPrefsPrivate().edit();

                edit.putString(Settings.CUSTOM_SNI, mCustomSNI);

                edit.apply();


                SocksHttpMainActivity.updateMainViews(getContext());

                dismiss();

                break;

            case R.id.fragment_sni_remoteCancelButton:
                dismiss();
                break;
        }
    }

}
