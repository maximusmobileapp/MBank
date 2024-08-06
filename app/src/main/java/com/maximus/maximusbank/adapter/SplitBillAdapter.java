package com.maximus.maximusbank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Model.Contact;
import com.maximus.maximusbank.textToImage.ColorGenerator;
import com.maximus.maximusbank.textToImage.TextDrawableUtil;

import java.util.ArrayList;
import java.util.List;

public class SplitBillAdapter extends RecyclerView.Adapter<SplitBillAdapter.SplitBillHolder> {
    Context context;
    List<Contact> selectedContacts;
    int totalBillAmount;
    int[] amounts;

    public SplitBillAdapter(Context context, List<Contact> selectedContacts, int totalBillAmount) {
        this.context = context;
        this.selectedContacts = selectedContacts;
        this.totalBillAmount = totalBillAmount;
        this.amounts = new int[selectedContacts.size()];
        divideAmountEqually();
    }

    private void divideAmountEqually() {
        int equalAmount = totalBillAmount / selectedContacts.size();
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = equalAmount;
        }
    }

    @NonNull
    @Override
    public SplitBillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SplitBillHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_split_bill_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SplitBillHolder holder, int position) {
        Contact contact = selectedContacts.get(position);
        holder.selectedContactNameTextView.setText(contact.getName());
        holder.selectedContactAmountTextView.setText(String.valueOf(amounts[position]));

        holder.selectedContactSeekBar.setMax(totalBillAmount);
        holder.selectedContactSeekBar.setProgress(amounts[position]);
        if (contact.getPhoto() != null && !contact.getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(contact.getPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .fallback(android.R.drawable.sym_def_app_icon)
                    .into(holder.selectedContactImageView);
        } else {
            String firstLetter = contact.getName().substring(0, 1);
            int color = new ColorGenerator(context).getColor(contact.getName());
            Bitmap textBitmap = TextDrawableUtil.getTextDrawable(firstLetter, 100, 100, color, Color.WHITE, 1, Color.BLUE);
            holder.selectedContactImageView.setImageBitmap(textBitmap);
        }
        holder.selectedContactSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    List<Integer> updatedPositions = adjustAmounts(position, progress);
                    for (int pos : updatedPositions) {
                        notifyItemChanged(pos);
                    }
                    holder.selectedContactAmountTextView.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private List<Integer> adjustAmounts(int changedPosition, int newAmount) {
        int remainingAmount = totalBillAmount - newAmount;
        int remainingContacts = selectedContacts.size() - 1;
        List<Integer> updatedPositions = new ArrayList<>();

        if (remainingContacts > 0) {
            int newEqualAmount = remainingAmount / remainingContacts;

            for (int i = 0; i < amounts.length; i++) {
                if (i != changedPosition) {
                    amounts[i] = newEqualAmount;
                } else {
                    amounts[i] = newAmount;
                }
                updatedPositions.add(i);
            }

            int distributedAmount = newEqualAmount * remainingContacts;
            int leftoverAmount = remainingAmount - distributedAmount;
            for (int i = 0; i < leftoverAmount; i++) {
                if (i != changedPosition) {
                    amounts[i]++;
                }
            }
        } else {
            amounts[changedPosition] = newAmount;
            updatedPositions.add(changedPosition);
        }

        return updatedPositions;
    }

    @Override
    public int getItemCount() {
        return selectedContacts.size();
    }

    public class SplitBillHolder extends RecyclerView.ViewHolder {
        ShapeableImageView selectedContactImageView;
        TextView selectedContactNameTextView, selectedContactAmountTextView;
        SeekBar selectedContactSeekBar;

        public SplitBillHolder(@NonNull View itemView) {
            super(itemView);
            selectedContactSeekBar = itemView.findViewById(R.id.selectedContactSeekBar);
            selectedContactImageView = itemView.findViewById(R.id.selectedContactImageView);
            selectedContactNameTextView = itemView.findViewById(R.id.selectedContactNameTextView);
            selectedContactAmountTextView = itemView.findViewById(R.id.selectedContactAmountTextView);
        }
    }
}
