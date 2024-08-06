package com.maximus.maximusbank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.maximus.maximusbank.Model.Card;
import com.maximus.maximusbank.R;


import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> cardList;

    public CardAdapter(List<Card> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cardcontrol, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.txtCardNo.setText(card.getCardNumber());
        holder.cardswitch.setChecked(card.isCardEnabled());

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        TextView txtCardNo;
        SwitchCompat cardatm,cardpos,cardecom;
        SwitchCompat cardswitch;
        EditText etATM,etPOS,etecom;
        CardView cardview;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCardNo = itemView.findViewById(R.id.txtcard1);
            cardswitch = itemView.findViewById(R.id.cardswitch);
            cardatm = itemView.findViewById(R.id.cardatm);
            cardpos = itemView.findViewById(R.id.cardpos);
            cardecom = itemView.findViewById(R.id.cardecom);
            etATM = itemView.findViewById(R.id.etATM);
            etPOS = itemView.findViewById(R.id.etPOS);
            etecom = itemView.findViewById(R.id.etecom);
            cardview = itemView.findViewById(R.id.cardview);


            cardswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cardview.setVisibility(View.VISIBLE);
                    } else {
                        cardview.setVisibility(View.GONE);
                    }
                }
            });

            cardatm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        etATM.setEnabled(true);
                        etATM.setFocusable(true);
                        etATM.setFocusableInTouchMode(true);
                    } else {
                        etATM.setEnabled(false);
                        etATM.setFocusable(false);
                        etATM.setFocusableInTouchMode(false);
                    }
                }
            });

            cardpos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        etPOS.setEnabled(true);
                        etPOS.setFocusable(true);
                        etPOS.setFocusableInTouchMode(true);
                    } else {
                        etPOS.setEnabled(false);
                        etPOS.setFocusable(false);
                        etPOS.setFocusableInTouchMode(false);
                    }
                }
            });

            cardecom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        etecom.setEnabled(true);
                        etecom.setFocusable(true);
                        etecom.setFocusableInTouchMode(true);
                    } else {
                        etecom.setEnabled(false);
                        etecom.setFocusable(false);
                        etecom.setFocusableInTouchMode(false);
                    }
                }
            });
        }
    }
}
