package com.maximus.maximusbank.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.maximus.maximusbank.fragments.AccountsFragment;
import com.maximus.maximusbank.fragments.DepositFragment;
import com.maximus.maximusbank.fragments.LoansFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AccountsFragment();
            case 1:
                return new DepositFragment();
            case 2:
                return new LoansFragment();
            default:
                return new AccountsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

