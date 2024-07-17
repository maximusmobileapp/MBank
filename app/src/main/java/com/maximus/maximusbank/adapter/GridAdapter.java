package com.maximus.maximusbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.model.GridModel;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<GridModel> {

    public GridAdapter(@NonNull Context context, ArrayList<GridModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        GridModel gridModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.txtView);
        ImageView courseIV = listitemView.findViewById(R.id.imgGrid);

        courseTV.setText(gridModel.grid_name);
        courseIV.setImageResource(gridModel.imgid);
        return listitemView;
    }
}

