package com.ouchadam.shortstraw;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements UiUpdater {

    private int strawTotal = 4;
    private NfcWrapper nfcWrapper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        List<StrawGenerator.Straw> strawList = initStraws(strawTotal);
        initNfc(strawList);
    }

    private List<StrawGenerator.Straw> initStraws(int strawTotal) {
        List<StrawGenerator.Straw> strawList = StrawGenerator.generate(strawTotal);
        updateStrawsLeft(strawTotal);
        return strawList;
    }

    private void initNfc(List<StrawGenerator.Straw> strawList) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcWrapper = new NfcWrapper(nfcAdapter, new NfcHandler(strawList, this), this);
    }

    private void updateStrawsLeft(final int remaining) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView) findViewById(R.id.straws_left);
                textView.setText("Straws remaining : " + remaining);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        processIntent();
    }

    private void processIntent() {
        String action = getIntent().getAction();
        if (nfcWrapper.isNfcAction(action)) {
            handleNfcAction();
        }
    }

    private void handleNfcAction() {
        String message = nfcWrapper.getPayload(getIntent());
        ((TextView) findViewById(R.id.label)).setText(message);
    }

    @Override
    public void onUpdateRemainingStraws(int remaining) {
        updateStrawsLeft(remaining);
    }

}

