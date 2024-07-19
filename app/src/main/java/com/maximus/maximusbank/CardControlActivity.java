package com.maximus.maximusbank;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.Model.Card;
import com.maximus.maximusbank.adapter.CardAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CardControlActivity extends AppCompatActivity {

    private List<Card> cardList;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private Button btnconfirm;
    LinearLayout linearbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_control);

        btnconfirm = findViewById(R.id.btnconfirm);
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