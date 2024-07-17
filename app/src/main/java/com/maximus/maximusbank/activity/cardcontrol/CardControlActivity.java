package com.maximus.maximusbank.activity.cardcontrol;

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
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.adapter.CardAdapter;
import com.maximus.maximusbank.model.Card;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CardControlActivity extends AppCompatActivity {

    private CardView cardviewno,cardview;
    private Switch cardswitch,cardatm,cardpos,cardecom;
    private TextView headerText,txtCardNo,txtunblock,txtcard1,txtChannel,txtStatus,txtPerDay,txtAvilAmu,txtATM,txtAvail1,txtPOS,txtAvail2,txtecom,txtAvail3;
    private EditText etATM,etPOS,etecom;
    private ImageView imgvector;
    private List<Card> cardList;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private Button btnconfirm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_control);

        cardviewno = findViewById(R.id.cardviewno);
        cardview = findViewById(R.id.cardview);
        cardswitch = findViewById(R.id.cardswitch);
        cardatm = findViewById(R.id.cardatm);
        cardpos = findViewById(R.id.cardpos);
        cardecom = findViewById(R.id.cardecom);
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


        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardList = new ArrayList<>();

        cardList.add(new Card("Card No : 62 XXXX XXX XX 72", false));
        cardList.add(new Card("Card No : 20 XXXX XXX XX 88", false));
        cardList.add(new Card("Card No : 12 XXXX XXX XX 34", false));

       // cardList.add(new Card("Card No : 42 XXXX XXX XX 44", true));


        cardAdapter = new CardAdapter(cardList);
        recyclerView.setAdapter(cardAdapter);



    }
    
}