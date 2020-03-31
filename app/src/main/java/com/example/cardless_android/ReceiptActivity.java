package com.example.cardless_android;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TransactionInfo t = TransactionInfo.getSavedList();
        ((TextView) findViewById(R.id.total_text)).setText(t.getTotal());

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearManager); // default layout is linear
        TransactionAdapter adapter = new TransactionAdapter(t.getList());
        recycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
