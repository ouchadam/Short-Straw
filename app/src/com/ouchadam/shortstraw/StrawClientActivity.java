package com.ouchadam.shortstraw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StrawClientActivity extends Activity implements View.OnClickListener {

    public static final String ACTION_LAST_STRAW = "last_straw";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straw_client);
        findViewById(R.id.activity_straw_client_reveal).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        processIntent();
    }

    private void processIntent() {
        String action = getIntent().getAction();
        if (NfcWrapper.isNfcAction(action)) {
            handleNfcAction();
        } else if (isServerLastStrawAction(action)) {
            handleLastStrawAction();
        }
    }

    private void handleNfcAction() {
        String message = NfcWrapper.getPayload(getIntent());
        setStrawMessage(message);
    }

    private boolean isServerLastStrawAction(String action) {
        return ACTION_LAST_STRAW.equals(action);
    }

    private void handleLastStrawAction() {
        String message = getIntent().getStringExtra(ACTION_LAST_STRAW);
        setStrawMessage("Straw 0 is " + message);
    }

    private void setStrawMessage(String message) {
        ((TextView) findViewById(R.id.activity_straw_client_message)).setText(message);
    }

    @Override
    public void onClick(View view) {
        revealStraw();
    }

    private void revealStraw() {
        findViewById(R.id.activity_straw_client_message).setVisibility(View.VISIBLE);
    }

}

