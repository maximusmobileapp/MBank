package com.maximus.maximusbank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.List;

public class SelectedContactsAdapter extends RecyclerView.Adapter<SelectedContactsAdapter.ViewHolder> {

    private List<Contact> selectedContacts;
    private OnAmountChangedListener onAmountChangedListener;
    private int totalBillAmount;
    Context context;
    public interface OnAmountChangedListener {
        void onAmountChanged(Contact contact);
    }

    public SelectedContactsAdapter(Context context,List<Contact> selectedContacts, int totalBillAmount, OnAmountChangedListener onAmountChangedListener) {
        this.context = context;
        this.selectedContacts = selectedContacts;
        this.totalBillAmount = totalBillAmount;
        this.onAmountChangedListener = onAmountChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = selectedContacts.get(position);
        holder.contactNameTextView.setText(contact.getName());

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
        /*Glide.with(context)
                .load(contact.getPhoto())
                .apply(RequestOptions.circleCropTransform())
                .fallback(android.R.drawable.sym_def_app_icon)
                .into(holder.selectedContactImageView);*/

        holder.btnRemoveSelected.setOnClickListener(v -> {
            onAmountChangedListener.onAmountChanged(contact);
        });
    }

    @Override
    public int getItemCount() {
        return selectedContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contactNameTextView;
        Button btnRemoveSelected;
        ShapeableImageView selectedContactImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.selectedContactNameTextView);
            btnRemoveSelected = itemView.findViewById(R.id.btnRemoveSelected);
            selectedContactImageView = itemView.findViewById(R.id.selectedContactImageView);
        }
    }
}
