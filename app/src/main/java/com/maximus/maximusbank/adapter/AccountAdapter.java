package com.maximus.maximusbank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Model.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<Account> accountList;

    public AccountAdapter(List<Account> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.accountNo.setText(account.getAccountNo());
        holder.balance.setText(account.getBalance());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView accountNo;
        public TextView balance;

        public ViewHolder(View view) {
            super(view);
            accountNo = view.findViewById(R.id.tvAccountNo);
            balance = view.findViewById(R.id.tvBalance);
        }
    }
}

