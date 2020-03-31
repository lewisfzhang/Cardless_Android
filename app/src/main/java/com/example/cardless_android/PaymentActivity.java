package com.example.cardless_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading); // start loading

        findViewById(R.id.cancelLoading).setOnClickListener(CANCEL);
        backAllowed = true;

        final String key = getIntent().getStringExtra("key");
        CountDownTimer timer = new CountDownTimer(1500, 1000) {

            @Override
            public void onTick(long milliTillFinish) {

            }

            @Override
            public void onFinish() {
                fetchData(key);
            }
        }.start();
    }

    // FIXME calls completeLoading() after complete
    private void fetchData(String key) {
        transactionInfo = new TransactionInfo();
        // FIXME: assumes all prices are valid ###.## (ie. $3 = 3.00)
        transactionInfo.addItem(key, "0.00");
        for (int i = 0; i < 50; i++) {
            transactionInfo.addItem("Meat"+i, "9.99");
        }
        TransactionInfo.setSavedList(transactionInfo);

        completeLoading();
    }

    private void completeLoading() {
        setContentView(R.layout.activity_payment);
        hideLoadingButton();
        displayPrice();
        setOnClickListener();
    }

    private void displayPrice() {
        // FIXME
        String price = "$99.98"; // transactionInfo.getTotal();
        ((TextView) findViewById(R.id.priceText)).setText(price);
    }

    private void displayData() {
        // FIXME: calls a fragment displayed the receipt
    }

    private void showLoadingButton() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    private void hideLoadingButton() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    private void setOnClickListener() {
        final Context thisContext = this;

        View.OnClickListener accept = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoadingButton();
                processTransaction();
            }
        };

        findViewById(R.id.acceptButton).setOnClickListener(accept);
        findViewById(R.id.cancelButton).setOnClickListener(CANCEL);
    }

    private void processTransaction() {
        CircleButton cancelButton = findViewById(R.id.cancelButton);
        backAllowed = false;
        cancelButton.setOnClickListener(null); // disable
        cancelButton.setVisibility(View.GONE); // completely remove from layout

        CountDownTimer timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                loadConfirmation();
            }
        }.start();

    }

    private void loadConfirmation() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //TODO prevent back while processing transaction
        if (backAllowed) { // go back to scanning
            goHome();
        } // else do nothing (can't go back)
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // remove previous activities
        startActivity(intent);
    }

    private boolean backAllowed;
    private TransactionInfo transactionInfo;

    final private View.OnClickListener CANCEL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goHome();
        }
    };

}
