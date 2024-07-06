package com.maximus.maximusbank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.R;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    private List<String> transactionList;

    public TransactionsAdapter(List<String> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        String transaction = transactionList.get(position);
        holder.transactionTextView.setText(transaction);
        holder.transactionAmountView.setText("+3982");
        holder.transactionTimeView.setText("03:00 PM");
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        public TextView transactionTextView;
        public TextView transactionAmountView;
        public TextView transactionTimeView;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            transactionTextView = itemView.findViewById(R.id.transaction_text);
            transactionAmountView = itemView.findViewById(R.id.transaction_amount);
            transactionTimeView = itemView.findViewById(R.id.transaction_time);
        }
    }
}

