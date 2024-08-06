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
import com.maximus.maximusbank.activity.fundTransfer.OtherBankActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OtherbkStepAdapter extends RecyclerView.Adapter<OtherbkStepAdapter.StepViewHolderOther> {
    private List<String> steps;
    private List<String> stepData;
    private int currentStep;
    private ArrayAdapter<String> spinnerAdapter;

    public OtherbkStepAdapter(List<String> steps ) {
        this.steps = steps;
        this.stepData = new ArrayList<>(Collections.nCopies(steps.size(), ""));
        this.currentStep = 0;
    }

    public void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter) {
        this.spinnerAdapter = spinnerAdapter;
        notifyItemChanged(0);
    }

    @NonNull
    @Override
    public StepViewHolderOther onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otherbank, parent, false);
        return new StepViewHolderOther(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StepViewHolderOther holder, int position) {
        String step = steps.get(position);
        holder.stepTitleOther.setText(step);

        // Set hints and input types based on position
        switch (position) {
            case 0: // Pay From
                holder.stepSpinnerOther.setVisibility(View.VISIBLE);
                holder.stepInputEtOther.setVisibility(View.GONE);
                holder.stepSpinnerOther.setAdapter(spinnerAdapter);
                break;
            case 1: // Pay To
                holder.stepInputEtOther.setHint("Enter Pay To");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
            case 2: // Bank Name
                holder.stepInputEtOther.setHint("Enter Bank Name");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
            case 3: // Account No
                holder.stepInputEtOther.setHint("Enter Account No");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
            case 4 : // IFSC
                holder.stepInputEtOther.setHint("Enter IFSC No");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
            case 5 : //Amount
                holder.stepInputEtOther.setHint("Enter Amount");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
            case 6 : //Remarks
                holder.stepInputEtOther.setHint("Enter Remarks (optional)");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
            default:
                holder.stepInputEtOther.setHint("Enter Details");
                holder.stepInputEtOther.setInputType(InputType.TYPE_CLASS_TEXT);
                holder.stepInputEtOther.setVisibility(View.VISIBLE);
                holder.stepSpinnerOther.setVisibility(View.GONE);
                break;
        }

        if (position == 0) {
            holder.stepSpinnerOther.setVisibility(View.VISIBLE);
            holder.stepInputEtOther.setVisibility(View.GONE);
            holder.stepSpinnerOther.setAdapter(spinnerAdapter);
            String selectedItem = stepData.get(position);
            if (!TextUtils.isEmpty(selectedItem)) {
                int spinnerPosition = spinnerAdapter.getPosition(selectedItem);
                holder.stepSpinnerOther.setSelection(spinnerPosition);
                if (!stepData.get(position).isEmpty()) {
                    holder.stepSpinnerOther.setEnabled(false);
                }
            }
        } else {
            holder.stepInputEtOther.setVisibility(View.VISIBLE);
            holder.stepSpinnerOther.setVisibility(View.GONE);
            holder.stepInputEtOther.setText(stepData.get(position));
            if (!stepData.get(position).isEmpty()){
                holder.stepInputEtOther.setFocusable(false);
                holder.stepInputEtOther.setClickable(false);
                holder.stepInputEtOther.setFocusableInTouchMode(false);
            } else {
                holder.stepInputEtOther.setFocusable(true);
                holder.stepInputEtOther.setClickable(true);
                holder.stepInputEtOther.setFocusableInTouchMode(true);
            }
        }
        holder.stepInputEtOther.setVisibility(position <= currentStep && position != 0 ? View.VISIBLE : View.GONE);
        holder.stepButtonOther.setVisibility(position == currentStep ? View.VISIBLE : View.GONE);
        holder.stepCircleOther.setBackgroundResource(stepData.get(position).isEmpty() ? R.drawable.ic_unchecked : R.drawable.ic_check);

        holder.stepButtonOther.setOnClickListener(v -> {
            String input = position == 0 ? holder.stepSpinnerOther.getSelectedItem().toString() : holder.stepInputEtOther.getText().toString();
            if (!TextUtils.isEmpty(input) || position == 6) {
                stepData.set(position, input);
                currentStep++;
                notifyDataSetChanged();
            }

            if (currentStep == steps.size()) {
                ((OtherBankActivity) v.getContext()).showProceedButtonOther();
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

    static class StepViewHolderOther extends RecyclerView.ViewHolder {

        TextView stepTitleOther;
        Spinner stepSpinnerOther;
        EditText stepInputEtOther;
        Button stepButtonOther;
        View stepCircleOther;

        public StepViewHolderOther(@NonNull View itemView) {
            super(itemView);

            stepTitleOther = itemView.findViewById(R.id.step_title_other);
            stepSpinnerOther = itemView.findViewById(R.id.step_spinner_other);
            stepInputEtOther = itemView.findViewById(R.id.step_input_other);
            stepButtonOther = itemView.findViewById(R.id.step_button_other);
            stepCircleOther = itemView.findViewById(R.id.step_circle_other);
        }
    }
}
