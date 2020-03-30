package com.example.cardless_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Confirmation extends AppCompatActivity { //implements View.OnClickListener {
    private static final int WAIT_TIME = 5000; // time before this activity self-destructs
    private CountDownTimer currentTimer;
    private long milliLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        View.OnClickListener dummy = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hi", "YEET");
                timerReset();
            }
        };
        findViewById(R.id.confirmation).setOnClickListener(dummy);

        Log.d("Hi", "DANKMEMES");
        timerStart(WAIT_TIME);
    }

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // remove previous activities
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //TODO cannot go back, do nothing
    }

//    @Override
//    public void onClick(View v) {
//        timerReset();
//    }

    private void timerStart(long timeLeft) {
        currentTimer = new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long milliTillFinish) {
                milliLeft = milliTillFinish;
//                timer.setText(String.format("%d",milliTillFinish / 1000));
            }

            @Override
            public void onFinish() {
                goHome();
            }
        }.start();
    }

    private void timerReset() {
        currentTimer.cancel();
        timerStart(WAIT_TIME);
    }
}
