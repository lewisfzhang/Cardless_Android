package com.example.cardless_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {
    private static final int MIN_TIME = 3000;
    private boolean backDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        backDone = false;
        setOnClickListeners();
        setBackTimer(MIN_TIME);
    }

    private void setOnClickListeners() {
        View.OnClickListener cancel = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        };
        findViewById(R.id.cancelImage).setOnClickListener(cancel);

        View.OnClickListener receipt = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReceipt2();
            }
        };
        findViewById(R.id.showReceipt).setOnClickListener(receipt);
    }

    private void showReceipt2() {
        Intent intent = new Intent(this, ReceiptActivity.class);
        startActivity(intent);
    }

    // FIXME to enforce user to verify their transaction succeeded
    private void goHome() {
        if (backDone) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // remove previous activities
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        goHome();
    }

    private void setBackTimer(long timeLeft) {
        CountDownTimer backTimer = new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long milliTillFinish) {

            }

            @Override
            public void onFinish() {
                backDone = true;
            }
        }.start();
    }
}
