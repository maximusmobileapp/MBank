package com.maximus.maximusbank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.maximus.maximusbank.Model.AccountStatement;
import com.maximus.maximusbank.R;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<AccountStatement> accountStatementList = new ArrayList<>();
    private Context context;

    public TransactionAdapter(List<AccountStatement> accountStatementList, Context context) {
        this.accountStatementList = accountStatementList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accountstatement, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position) {
        try {
            AccountStatement accountStatement = accountStatementList.get(position);
            holder.textViewDate.setText(accountStatement.getDate());
            holder.textViewDescription.setText(accountStatement.getDescription());
            holder.textViewAmount.setText(String.format("\u20B9%.2f", accountStatement.getAmount()));

            // Set color based on transaction type
            String transactionType = accountStatement.getTransaction().toString();
            Log.d("TransactionAdapter", "Transaction type: " + transactionType);
            Log.d("TransactionAdapter", "AccountStatement: " + accountStatement);
            System.out.println("Shivanjali " + transactionType);

            if (transactionType.equalsIgnoreCase("Credit")) {
                holder.textViewAmount.setTextColor(context.getColor(R.color.credit_color));
            } else if (transactionType.equalsIgnoreCase("Debit")) {
                holder.textViewAmount.setTextColor(context.getColor(R.color.debit_color));
            } else {
                holder.textViewAmount.setTextColor(context.getColor(R.color.black));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return accountStatementList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewDescription, textViewAmount;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
        }
    }
}
