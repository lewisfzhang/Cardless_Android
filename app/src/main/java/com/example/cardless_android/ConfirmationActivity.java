package com.example.cardless_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConfirmationActivity extends AppCompatActivity { //implements View.OnClickListener {
    private static final int WAIT_TIME = 15000; // time before this activity self-destructs
    private static final int MIN_TIME = 3000;
    private CountDownTimer currentTimer, backTimer;
    private boolean backDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        backDone = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        setOnClickListeners();
        timerStart(WAIT_TIME);
        setBackTimer(MIN_TIME);
    }

    private void setOnClickListeners() {
        View.OnClickListener dummy = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerReset();
            }
        };
        findViewById(R.id.confirmation).setOnClickListener(dummy);

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
                showReceipt();
            }
        };
        findViewById(R.id.showReceipt).setOnClickListener(receipt);
    }

    private void showReceipt() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.fragment_receipt, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timerReset();
            }
        });

        View.OnClickListener dismiss = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        };
        popupView.findViewById(R.id.closeButton).setOnClickListener(dismiss);

        // setting up Recycler view
        TransactionInfo t = TransactionInfo.getSavedList();

        RecyclerView recycler = (RecyclerView) popupView.findViewById(R.id.recycler);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearManager); // default layout is linear
        Adapter adapter = new Adapter(this, t.getList());
        recycler.setAdapter(adapter);

        TransactionInfo.clearSavedList();
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

    private void timerStart(long timeLeft) {
        currentTimer = new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long milliTillFinish) {

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

    private void setBackTimer(long timeLeft) {
        backTimer = new CountDownTimer(timeLeft, 1000) {

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
