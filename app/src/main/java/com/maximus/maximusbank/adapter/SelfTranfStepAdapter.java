package com.maximus.maximusbank.adapter;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.activity.fundTransfer.SelfTransferActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelfTranfStepAdapter extends RecyclerView.Adapter<SelfTranfStepAdapter.StepViewHolderSelf> {
    private List<String> steps;
    private List<String> stepData;
    private int currentStep;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayAdapter<String> payFromAdapter;
    private ArrayAdapter<String> payToAdapter;

    public SelfTranfStepAdapter(List<String> steps) {
        this.steps = steps;
        this.stepData = new ArrayList<>(Collections.nCopies(steps.size(), ""));
        this.currentStep = 0;
    }

//    public void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter) {
//        this.spinnerAdapter = spinnerAdapter;
//        notifyItemChanged(0);
//    }


    public void setPayFromAdapter(ArrayAdapter<String> payFromAdapter) {
        this.payFromAdapter = payFromAdapter;
        notifyItemChanged(0);
    }

    public void setPayToAdapter(ArrayAdapter<String> payToAdapter) {
        this.payToAdapter = payToAdapter;
        notifyItemChanged(2);
    }

    @NonNull
    @Override
    public StepViewHolderSelf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selftransfer, parent, false);
        return new StepViewHolderSelf(view);
    }


    public void onBindViewHolder(@NonNull StepViewHolderSelf holder, int position) {
        String step = steps.get(position);
        holder.StepTitleSelf.setText(step);

        Log.d("onBindViewHolder", "Position: " + position);

        switch (position) {
            case 0: // Pay From
                holder.StepSpinnerSelf.setVisibility(View.VISIBLE);
                holder.StepInputSelf.setVisibility(View.GONE);
                holder.StepSpinnerSelf.setAdapter(spinnerAdapter);
                break;
            case 1: // Available Balance
                holder.StepInputSelf.setHint("Show Available balance");
                holder.StepInputSelf.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.StepInputSelf.setVisibility(View.VISIBLE);
                holder.StepSpinnerSelf.setVisibility(View.GONE);
                break;
            case 2: // Pay To
                holder.StepInputSelf.setHint("Enter Pay To");
                holder.StepInputSelf.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.StepInputSelf.setVisibility(View.VISIBLE);
                holder.StepSpinnerSelf.setVisibility(View.GONE);
                break;
            case 3: // Amount
                holder.StepInputSelf.setHint("Enter Amount");
                holder.StepInputSelf.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                holder.StepInputSelf.setVisibility(View.VISIBLE);
                holder.StepSpinnerSelf.setVisibility(View.GONE);
                break;

            case 4 : //Remarks
                holder.StepInputSelf.setHint("Enter Remarks (optional)");
                holder.StepInputSelf.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.StepInputSelf.setVisibility(View.VISIBLE);
                holder.StepSpinnerSelf.setVisibility(View.GONE);
                break;
            default:
                holder.StepInputSelf.setHint("Enter Details");
                holder.StepInputSelf.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.StepInputSelf.setVisibility(View.VISIBLE);
                holder.StepSpinnerSelf.setVisibility(View.GONE);
                break;
        }

        if (position == 0) {
            holder.StepSpinnerSelf.setVisibility(View.VISIBLE);
            holder.StepInputSelf.setVisibility(View.GONE);
            holder.StepSpinnerSelf.setAdapter(payFromAdapter);
            String selectedItem = stepData.get(position);
            if (!TextUtils.isEmpty(selectedItem)) {
                int spinnerPosition = payFromAdapter.getPosition(selectedItem);
                holder.StepSpinnerSelf.setSelection(spinnerPosition);
                if (!stepData.get(position).isEmpty()) {
                    holder.StepSpinnerSelf.setEnabled(false);
                }
            }
        }

        else {
            holder.StepInputSelf.setVisibility(View.VISIBLE);
            holder.StepSpinnerSelf.setVisibility(View.GONE);
            holder.StepInputSelf.setText(stepData.get(position));
            if (!stepData.get(position).isEmpty()){
                holder.StepInputSelf.setFocusable(false);
                holder.StepInputSelf.setClickable(false);
                holder.StepInputSelf.setFocusableInTouchMode(false);
            } else {
                holder.StepInputSelf.setFocusable(true);
                holder.StepInputSelf.setClickable(true);
                holder.StepInputSelf.setFocusableInTouchMode(true);
            }
        }
        Log.d("currentStep" ,""+currentStep);
        holder.StepInputSelf.setVisibility(position <= currentStep && position != 0 ? View.VISIBLE : View.GONE);
        if (2 == currentStep && position == 2) holder.StepInputSelf.setVisibility(View.GONE);
        if (2 == currentStep && position == 2) holder.StepSpinnerSelf.setVisibility(View.VISIBLE);
        holder.StepButtonSelf.setVisibility(position == currentStep ? View.VISIBLE : View.GONE);
        holder.stepCircleSelf.setBackgroundResource(stepData.get(position).isEmpty() ? R.drawable.ic_unchecked : R.drawable.ic_check);

        holder.StepButtonSelf.setOnClickListener(v -> {
            String input = position == 0 || position == 2 ? holder.StepSpinnerSelf.getSelectedItem().toString() : holder.StepInputSelf.getText().toString();
            if (!TextUtils.isEmpty(input) || position == 4) {
                stepData.set(position, input);
                currentStep++;

            }
            notifyDataSetChanged();
            if (currentStep == steps.size()) {
                ((SelfTransferActivity) v.getContext()).showProceedButtonSelf();
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public List<String> getStepData() {
        return stepData;
    }

    static class StepViewHolderSelf extends RecyclerView.ViewHolder {

        TextView StepTitleSelf;
        Spinner StepSpinnerSelf;
        EditText StepInputSelf;
        Button StepButtonSelf;
        View stepCircleSelf;

        public StepViewHolderSelf(@NonNull View itemView) {
            super(itemView);
            StepTitleSelf = itemView.findViewById(R.id.step_title_self);
            StepSpinnerSelf = itemView.findViewById(R.id.step_spinner_self);
            StepInputSelf = itemView.findViewById(R.id.step_input_self);
            StepButtonSelf = itemView.findViewById(R.id.step_button_self);
            stepCircleSelf = itemView.findViewById(R.id.step_circle_self);

        }
    }
}
