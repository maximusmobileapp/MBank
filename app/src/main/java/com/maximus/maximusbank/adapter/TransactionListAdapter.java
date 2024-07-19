package com.maximus.maximusbank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.maximus.maximusbank.Model.Transaction;
import com.maximus.maximusbank.R;
import java.util.ArrayList;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.CardViewHolder> {

    private List<Transaction> transactionsList = new ArrayList<>();

    public TransactionListAdapter(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }


    @NonNull
    @Override
    public TransactionListAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transhistory, parent, false);
        return new TransactionListAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListAdapter.CardViewHolder holder, int position) {
        Transaction transactions = transactionsList.get(position);
        holder.textViewName.setText(transactions.getName());
        holder.textViewDate.setText(transactions.getDate());
        holder.paymentmethod.setText(transactions.getPayment());
//        holder.textViewAmount.setText(Integer.toString(transactions.getAmount()));
        //to show rupees symbol
        holder.textViewAmount.setText(transactions.getFormattedAmount());
        holder.textstatus.setText(transactions.getStatus());
    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void updateData(List<Transaction> filteredList) {
        transactionsList.clear();
        transactionsList.addAll(filteredList);
        notifyDataSetChanged();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewAmount;
        TextView paymentmethod;
        TextView textstatus;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            userImg = itemView.findViewById(R.id.userImg);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            paymentmethod = itemView.findViewById(R.id.textpayment);
            textstatus = itemView.findViewById(R.id.textstatus);


        }
    }
}
