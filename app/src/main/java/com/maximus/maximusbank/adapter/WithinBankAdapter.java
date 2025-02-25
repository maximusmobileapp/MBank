package com.maximus.maximusbank.adapter;

import android.text.InputType;
import android.text.TextUtils;
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
import com.maximus.maximusbank.activity.fundTransfer.WithinBankTransferActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WithinBankAdapter extends RecyclerView.Adapter<WithinBankAdapter.StepViewHolder> {
    private List<String> steps;
    private List<String> stepData;
    private int currentStep;
    private ArrayAdapter<String> spinnerAdapter;

    public WithinBankAdapter(List<String> steps) {
        this.steps = steps;
        this.stepData = new ArrayList<>(Collections.nCopies(steps.size(), ""));
        this.currentStep = 0;
    }

    public void setSpinnerAdapter(ArrayAdapter<String> adapter) {
        this.spinnerAdapter = adapter;
        notifyItemChanged(0);
    }


    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String step = steps.get(position);
        holder.stepTitle.setText(step);

        switch (position) {
            case 0: // Pay From
                holder.stepInputSpinner.setVisibility(View.VISIBLE);
                holder.stepInputEditText.setVisibility(View.GONE);
                holder.stepInputSpinner.setAdapter(spinnerAdapter);
                break;
            case 1: // Pay To
                holder.stepInputEditText.setHint("Enter Pay To");
                holder.stepInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEditText.setVisibility(View.VISIBLE);
                holder.stepInputSpinner.setVisibility(View.GONE);
                break;
            case 2: // Amount
                holder.stepInputEditText.setHint("Enter Amount");
                holder.stepInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                holder.stepInputEditText.setVisibility(View.VISIBLE);
                holder.stepInputSpinner.setVisibility(View.GONE);
                break;
            case 3: // Remarks
                holder.stepInputEditText.setHint("Enter Remarks (optional)");
                holder.stepInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEditText.setVisibility(View.VISIBLE);
                holder.stepInputSpinner.setVisibility(View.GONE);
                break;
            default:
                holder.stepInputEditText.setHint("Enter Details");
                holder.stepInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEditText.setVisibility(View.VISIBLE);
                holder.stepInputSpinner.setVisibility(View.GONE);
                break;
        }

        if (position == 0) {
            holder.stepInputSpinner.setVisibility(View.VISIBLE);
            holder.stepInputEditText.setVisibility(View.GONE);
            holder.stepInputSpinner.setAdapter(spinnerAdapter);
            String selectedItem = stepData.get(position);
            if (!TextUtils.isEmpty(selectedItem)) {
                int spinnerPosition = spinnerAdapter.getPosition(selectedItem);
                holder.stepInputSpinner.setSelection(spinnerPosition);
                if (!stepData.get(position).isEmpty()) {
                    holder.stepInputSpinner.setEnabled(false);
                }
            }
        } else {
            holder.stepInputEditText.setVisibility(View.VISIBLE);
            holder.stepInputSpinner.setVisibility(View.GONE);
            holder.stepInputEditText.setText(stepData.get(position));
            if (!stepData.get(position).isEmpty()) {
                holder.stepInputEditText.setFocusable(false);
                holder.stepInputEditText.setClickable(false);
                holder.stepInputEditText.setFocusableInTouchMode(false);
            } else {
                holder.stepInputEditText.setFocusable(true);
                holder.stepInputEditText.setClickable(true);
                holder.stepInputEditText.setFocusableInTouchMode(true);
            }
        }

        holder.stepInputEditText.setVisibility(position <= currentStep && position != 0 ? View.VISIBLE : View.GONE);
        holder.stepButton.setVisibility(position == currentStep ? View.VISIBLE : View.GONE);
        holder.stepCircle.setBackgroundResource(stepData.get(position).isEmpty() ? R.drawable.ic_unchecked : R.drawable.ic_check);

        holder.stepButton.setOnClickListener(v -> {
            String input = position == 0 ? holder.stepInputSpinner.getSelectedItem().toString() : holder.stepInputEditText.getText().toString();
            if (!TextUtils.isEmpty(input) || position == 3) {
                stepData.set(position, input);
                currentStep++;
                notifyDataSetChanged();
            }

            if (currentStep == steps.size()) {
                ((WithinBankTransferActivity) v.getContext()).showProceedButton();
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

    static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView stepTitle;
        Spinner stepInputSpinner;
        EditText stepInputEditText;
        Button stepButton;
        View stepCircle;

        StepViewHolder(View itemView) {
            super(itemView);
            stepTitle = itemView.findViewById(R.id.step_title);
            stepInputSpinner = itemView.findViewById(R.id.step_spinner);
            stepInputEditText = itemView.findViewById(R.id.step_input);
            stepButton = itemView.findViewById(R.id.step_button);
            stepCircle = itemView.findViewById(R.id.step_circle);
        }
    }
}



