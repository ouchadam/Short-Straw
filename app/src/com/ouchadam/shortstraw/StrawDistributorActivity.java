package com.ouchadam.shortstraw;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class StrawDistributorActivity extends Activity implements UiUpdater {

    private final StrawListGenerator strawListGenerator;

    private List<Straw> strawList;
    private TextView remainingStraws;

    public StrawDistributorActivity() {
        strawListGenerator = new StrawListGenerator();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straw_distributor);
        findViews();
        int strawTotal = getStrawTotal(getIntent());
        strawList = initStraws(strawTotal);
        updateStrawsLeft(strawTotal);
        initNfc(strawList);
    }

    private int getStrawTotal(Intent intent) {
        return intent.getIntExtra(StrawSetupActivity.EXTRA_STRAW_TOTAL, 1);
    }

    private void findViews() {
        remainingStraws = (TextView) findViewById(R.id.straws_left);
    }

    private List<Straw> initStraws(int strawTotal) {
        return strawListGenerator.generate(strawTotal);
    }

    private void initNfc(List<Straw> strawList) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        NfcWrapper nfcWrapper = new NfcWrapper(nfcAdapter, new NfcHandler(strawList, this), this);
    }

    @Override
    public void onUpdateRemainingStraws(int remaining) {
        updateStrawsLeft(remaining);
    }

    private void updateStrawsLeft(final int remaining) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setRemainingStrawsText(remaining);
                if (remaining == 1) {
                    setRemainingStrawsText(0);
                    showLastStraw();
                }
            }
        });
    }

    private void setRemainingStrawsText(int remaining) {
        remainingStraws.setText("Straws remaining : " + remaining);
    }

    private void showLastStraw() {
        Intent intent = createLastStrawIntent();
        startActivity(intent);
        finish();
    }

    private Intent createLastStrawIntent() {
        Intent intent = new Intent(this, StrawClientActivity.class);
        intent.setAction(StrawClientActivity.ACTION_LAST_STRAW);
        intent.putExtra(StrawClientActivity.ACTION_LAST_STRAW, strawList.get(0).isShort());
        return intent;
    }

}

