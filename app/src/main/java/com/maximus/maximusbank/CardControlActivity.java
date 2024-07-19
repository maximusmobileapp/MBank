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

    private CardView cardviewno, cardview;
    private Switch cardswitch, cardatm, cardpos, cardecom;
    private TextView headerText, txtCardNo, txtunblock, txtcard1, txtChannel, txtStatus, txtPerDay, txtAvilAmu, txtATM, txtAvail1, txtPOS, txtAvail2, txtecom, txtAvail3;
    private EditText etATM, etPOS, etecom;
    private ImageView imgvector;
    private List<Card> cardList;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private Button btnconfirm;
    LinearLayout linearbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_control);

        cardviewno = findViewById(R.id.cardviewno);
        cardview = findViewById(R.id.cardview);
        cardswitch = findViewById(R.id.cardswitch);
        cardatm = findViewById(R.id.cardatm);
        cardpos = findViewById(R.id.cardpos);
        cardecom = findViewById(R.id.cardecom);
        imgvector = findViewById(R.id.imgvector);
        headerText = findViewById(R.id.headerText);
        txtCardNo = findViewById(R.id.txtCardNo);
        txtunblock = findViewById(R.id.txtunblock);
        txtcard1 = findViewById(R.id.txtcard1);
        txtChannel = findViewById(R.id.txtChannel);
        txtStatus = findViewById(R.id.txtStatus);
        txtPerDay = findViewById(R.id.txtPerDay);
        txtAvilAmu = findViewById(R.id.txtAvilAmu);
        txtATM = findViewById(R.id.txtATM);
        txtAvail1 = findViewById(R.id.txtAvail1);
        txtPOS = findViewById(R.id.txtPOS);
        txtAvail2 = findViewById(R.id.txtAvail2);
        txtecom = findViewById(R.id.txtecom);
        txtAvail3 = findViewById(R.id.txtAvail3);
        etATM = findViewById(R.id.etATM);
        etPOS = findViewById(R.id.etPOS);
        etecom = findViewById(R.id.etecom);
        btnconfirm = findViewById(R.id.btnconfirm);
        linearbtn = findViewById(R.id.linearbtn);
        recyclerView = findViewById(R.id.recyclerView1);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardList = new ArrayList<>();

        cardList.add(new Card("Card No : 62 XXXX XXX XX 72", false));
        cardList.add(new Card("Card No : 20 XXXX XXX XX 88", false));
        cardList.add(new Card("Card No : 12 XXXX XXX XX 34", false));


        cardAdapter = new CardAdapter(cardList);
        recyclerView.setAdapter(cardAdapter);

        imgvector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.cardcontrol);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONObject screenObject = jsonObject.getJSONObject("screen");
            JSONArray components = screenObject.getJSONArray("components");

            TextView[] textViews = new TextView[]{headerText, txtCardNo, txtunblock, txtcard1, txtChannel, txtStatus, txtPerDay, txtAvilAmu, txtATM, txtAvail1, txtPOS, txtAvail2, txtecom, txtAvail3};

            EditText[] editTexts = new EditText[]{etATM, etPOS, etecom};

            CardView[] cardViews = new CardView[]{cardviewno, cardview};

            Button[] buttons = new Button[]{btnconfirm};

            applyTextConfig(components, textViews);

            applyEditTextConfig(components, editTexts);

            applyCardViewConfig(components, cardViews);

            applyButtonConfig(components, buttons);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyEditTextConfig(JSONArray components, EditText[] editTexts) {
    }


    private void applyTextConfig(JSONArray components, TextView[] textViews) {
        try {
            for (int i = 0; i < components.length(); i++) {
                JSONObject config = components.getJSONObject(i);
                TextView textView = textViews[i];
                String text = config.getString("text");
                textView.setText(text);
                textView.setTextColor(Color.parseColor(config.getString("textColor")));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getInt("textSize"));

                if (text.equals("Card Control")) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTypeface(null, Typeface.NORMAL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyCardViewConfig(JSONArray components, CardView[] cardViews) {
        try {
            for (int i = 0; i < components.length(); i++) {
                JSONObject config = components.getJSONObject(i);
                CardView cardView = cardViews[i];
                cardView.setCardBackgroundColor(Color.GRAY);
                cardView.setBackground(getResources().getDrawable(R.drawable.cardview_border));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyButtonConfig(JSONArray components, Button[] buttons) {
        try {
            for (int i = 0; i < components.length(); i++) {
                JSONObject config = components.getJSONObject(i);
                Button button = buttons[i];
                String text = config.getString("Button");
                button.setText(text);
                button.setTextColor(Color.parseColor(config.getString("textColor")));
                button.setTextSize(20);
                button.setBackgroundColor(Color.WHITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}