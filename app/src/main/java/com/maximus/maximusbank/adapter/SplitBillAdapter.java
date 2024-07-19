package com.maximus.maximusbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maximus.maximusbank.Model.SpiltContact;
import com.maximus.maximusbank.R;
import java.util.List;

public class SplitBillAdapter extends ArrayAdapter<SpiltContact> {

    public SplitBillAdapter(@NonNull Context context, List<SpiltContact> spiltbill) {
        super(context, 0, spiltbill);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SpiltContact spiltContact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_spiltbill, parent, false);
        }

        TextView txtname = convertView.findViewById(R.id.txtname);
        TextView txtnumber = convertView.findViewById(R.id.txtnumber);

        txtname.setText(spiltContact.getName());
        txtnumber.setText(spiltContact.getPhoneNumber());

        return convertView;
    }
}
