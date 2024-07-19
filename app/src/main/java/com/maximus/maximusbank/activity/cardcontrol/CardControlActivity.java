package com.maximus.maximusbank.activity.cardcontrol;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.adapter.CardAdapter;
import com.maximus.maximusbank.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardControlActivity extends AppCompatActivity {

    private List<Card> cardList;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;

    TextView headerTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_control);
        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.card_control);
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardList = new ArrayList<>();
        cardList.add(new Card("Card No : 62 XXXX XXX XX 72", false));
        cardList.add(new Card("Card No : 20 XXXX XXX XX 88", false));
        cardList.add(new Card("Card No : 12 XXXX XXX XX 34", false));

        cardAdapter = new CardAdapter(cardList);
        recyclerView.setAdapter(cardAdapter);


    }

}