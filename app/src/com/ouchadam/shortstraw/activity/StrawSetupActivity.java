package com.ouchadam.shortstraw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.ouchadam.shortstraw.R;

public class StrawSetupActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_STRAW_TOTAL = StrawSetupActivity.class.getSimpleName() + "straw_total";

    private NumberPicker numberPicker;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straw_total);
        initViews();
    }

    private void initViews() {
        findViews();
        initGoButton();
        initNumberPicker();
    }

    private void findViews() {
        goButton = (Button) findViewById(R.id.go_button);
        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
    }

    private void initGoButton() {
        goButton.setOnClickListener(this);
    }

    private void initNumberPicker() {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(30);
    }

    @Override
    public void onClick(View view) {
        startActivity(createStrawDistributor());
    }

    private Intent createStrawDistributor() {
        int strawTotal = numberPicker.getValue();
        Intent intent = new Intent(this, StrawDistributorActivity.class);
        intent.putExtra(EXTRA_STRAW_TOTAL, strawTotal);
        return intent;
    }
}

