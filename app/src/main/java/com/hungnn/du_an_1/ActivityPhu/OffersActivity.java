package com.hungnn.du_an_1.ActivityPhu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hungnn.du_an_1.R;

public class OffersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());

        ListView listView = findViewById(R.id.listOffers);
        String[] offers = new String[]{"Giảm 1%", "Giảm 3%", "Giảm 5%", "Giảm 10%"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_offer, android.R.id.text1, offers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            int[] percents = new int[]{1,3,5,10};
            Intent data = new Intent();
            data.putExtra("discount_percent", percents[position]);
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}
