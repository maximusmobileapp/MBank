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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactsViewHolder> {

    List<Contact> contacts;
    Context context;
    private OnContactSelectedListener listener;

    public ContactAdapter(List<Contact> contacts, Context context,OnContactSelectedListener listener) {
        this.contacts = contacts;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactsViewHolder viewHolder, int position) {
        Contact contact = contacts.get(position);
        viewHolder.contactNameTextView.setText(contact.getName());
        if (contact.getPhoto() != null && !contact.getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(contact.getPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .fallback(android.R.drawable.sym_def_app_icon)
                    .into(viewHolder.contactImageView);
        } else {
            String firstLetter = contact.getName().substring(0, 1);
            int color = new ColorGenerator(context).getColor(contact.getName());
            Bitmap textBitmap = TextDrawableUtil.getTextDrawable(firstLetter, 100, 100, color, Color.WHITE, 1, Color.BLUE);
            viewHolder.contactImageView.setImageBitmap(textBitmap);
        }

        viewHolder.txtAddContact.setOnClickListener(v -> listener.onContactSelected(contact));

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView contactImageView;
        TextView contactNameTextView;
        Button txtAddContact;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtAddContact = itemView.findViewById(R.id.txtAddContact);
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView);
            contactImageView = itemView.findViewById(R.id.contactImageView);
        }
    }
    public interface OnContactSelectedListener {
        void onContactSelected(Contact contact);
    }
}
