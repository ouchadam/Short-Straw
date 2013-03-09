package com.ouchadam.shortstraw;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.nfc.NdefRecord.createMime;

public class MainActivity extends Activity {

    private int strawTotal = 4;
    private TextView textView;
    private List<Straw> strawList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        strawList = generateStraws();
        updateStrawsLeft();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        initNfc(nfcAdapter);
        nfcAdapter.setOnNdefPushCompleteCallback(new NfcAdapter.OnNdefPushCompleteCallback() {
            @Override
            public void onNdefPushComplete(NfcEvent nfcEvent) {
                strawTotal--;
                updateStrawsLeft();
            }
        }, this);
    }

    private List<Straw> generateStraws() {
        List<Straw> straws = new ArrayList<Straw>(strawTotal);
        int shortStraw = (int) (Math.random() * (strawTotal + 1));
        for (int i = 0; i < strawTotal; i++) {
            if (i == shortStraw) {
                straws.add(new Straw(true));
            } else {
                straws.add(new Straw(false));
            }
        }
        return straws;
    }

    private void initNfc(NfcAdapter nfcAdapter) {
        if (nfcAdapter != null) {
            nfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
                @Override
                public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
                    if (strawTotal >= 1) {
                        String text = getStraw();
                        NdefMessage msg = new NdefMessage(
                                new NdefRecord[]{createMime(
                                        "application/vnd.com.example.android.beam", text.getBytes())
                                        , NdefRecord.createApplicationRecord("com.ouchadam.shortstraw")
                                });
                        return msg;
                    }
                    return null;
                }
            }, this);
        }
    }

    private String getStraw() {
        String isShort = strawList.get(strawTotal - 1).isShort() ? "short" : "not short";
        String text = "Straw " + strawTotal + " is " + isShort;
        return text;
    }

    private void updateStrawsLeft() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView = (TextView) findViewById(R.id.straws_left);
                textView.setText("Straws remaining : " + strawTotal);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String action = getIntent().getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    void processIntent(Intent intent) {
        textView = (TextView) findViewById(R.id.label);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        textView.setText(new String(msg.getRecords()[0].getPayload()));
    }

    public static class Straw {

        private final boolean isShort;

        public Straw(boolean isShort) {
            this.isShort = isShort;
        }

        public boolean isShort() {
            return isShort;
        }

    }

}

