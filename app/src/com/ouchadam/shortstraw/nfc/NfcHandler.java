package com.ouchadam.shortstraw.nfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import com.ouchadam.shortstraw.Straw;
import com.ouchadam.shortstraw.UiUpdater;

import java.util.List;

import static android.nfc.NdefRecord.createMime;

public class NfcHandler implements NfcWrapper.onNfcPushListener {

    private final List<Straw> strawList;
    private final UiUpdater uiUpdater;

    public NfcHandler(List<Straw> strawList, UiUpdater uiUpdater) {
        this.strawList = strawList;
        this.uiUpdater = uiUpdater;
    }

    @Override
    public void onNfcPush() {
        strawList.remove(strawList.size() - 1);
        uiUpdater.onUpdateRemainingStraws(strawList.size());
    }

    @Override
    public NdefMessage onGetNfcMessage() {
        return createMessage();
    }

    private NdefMessage createMessage() {
        if (!strawList.isEmpty()) {
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

    private String getStraw() {
        String isShort = strawList.get(strawList.size() - 1).isShort();
        String text = "Straw " + strawList.size() + " is " + isShort;
        return text;
    }
}
