package com.ouchadam.shortstraw;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;

public class NfcWrapper {

    private final onNfcPushListener onNfcPushListener;

    public interface onNfcPushListener {

        void onNfcPush();
        NdefMessage onGetNfcMessage();
    }
    public NfcWrapper(NfcAdapter nfcAdapter, NfcWrapper.onNfcPushListener onNfcPushListener, Activity activity) {
        nfcAdapter.setNdefPushMessageCallback(onCreateMessage, activity);
        nfcAdapter.setOnNdefPushCompleteCallback(onNdefPushCompleteCallback, activity);
        this.onNfcPushListener = onNfcPushListener;
    }

    private NfcAdapter.CreateNdefMessageCallback onCreateMessage = new NfcAdapter.CreateNdefMessageCallback() {
        @Override
        public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
            return onNfcPushListener.onGetNfcMessage();
        }
    };

    private NfcAdapter.OnNdefPushCompleteCallback onNdefPushCompleteCallback = new NfcAdapter.OnNdefPushCompleteCallback() {
        @Override
        public void onNdefPushComplete(NfcEvent nfcEvent) {
            onNfcPushListener.onNfcPush();
        }
    };

    public boolean isNfcAction(String action) {
        return NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action);
    }

    public String getPayload(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        return new String(msg.getRecords()[0].getPayload());
    }

}
