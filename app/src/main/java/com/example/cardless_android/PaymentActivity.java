package com.example.cardless_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        backAllowed = true;
        setOnClickListener();
        hideLoadingButton();
    }

    private void showLoadingButton() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        Log.d("HI", "HAHA");
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

        View.OnClickListener cancel = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(thisContext, "cancel", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(thisContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // remove previous activities
                startActivity(intent);
            }
        };

        findViewById(R.id.acceptButton).setOnClickListener(accept);
        findViewById(R.id.cancelButton).setOnClickListener(cancel);
    }

    private void processTransaction() {
        CircleButton cancelButton = findViewById(R.id.cancelButton);
        backAllowed = false;
        cancelButton.setOnClickListener(null); // disable
        cancelButton.setVisibility(View.GONE); // completely remove from layout
        Toast.makeText(this, "accept", Toast.LENGTH_SHORT).show();

//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {}
//        Log.d("Hi", "CRYBABY");
//        loadConfirmation();
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Log.d("Hi", "CRYBABY");
                loadConfirmation();
            }
        }.start();

    }

    private void loadConfirmation() {
        Intent intent = new Intent(this, Confirmation.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //TODO prevent back while processing transaction
        if (backAllowed) {
            super.onBackPressed();
        } // else do nothing (can't go back)
    }

    private boolean backAllowed;
}
