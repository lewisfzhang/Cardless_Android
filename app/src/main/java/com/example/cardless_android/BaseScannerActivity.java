package com.example.cardless_android;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseScannerActivity extends AppCompatActivity {
    public void setupToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        final ActionBar ab = getSupportActionBar();
//        if(ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
