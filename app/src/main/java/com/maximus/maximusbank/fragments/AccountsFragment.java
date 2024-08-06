package com.maximus.maximusbank.fragments;

import                                                                                                                                                                                                                                                                  android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.maximus.maximusbank.Model.Account;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.activity.account.AccountStatementActivity;
import com.maximus.maximusbank.activity.fundTransfer.FundTransferDashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AccountsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Account> accountList;
    private Button btnTransferFunds, txtAccStatement;

    public AccountsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        btnTransferFunds = (Button) view.findViewById(R.id.btnTransferFunds);
        txtAccStatement = (Button) view.findViewById(R.id.txtAccStatement);

        btnTransferFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FundTransferDashboard.class);
                startActivity(intent);

            }
        });

        txtAccStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountStBottomSheet();
            }
        });

        return view;

    }

    private void AccountStBottomSheet() {
        View viewdate = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_accountstatement, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(viewdate);
        bottomSheetDialog.show();

        final Calendar myCalendar = Calendar.getInstance();

        ImageButton btmAccclosedt = viewdate.findViewById(R.id.btmAccclosedt);
        TextView txtAccfrdate = viewdate.findViewById(R.id.txtAccfrdate);
        ImageView datepicker1 = viewdate.findViewById(R.id.datepicker1);
        TextView txtAcctodate = viewdate.findViewById(R.id.txtAcctodate);
        ImageView datepicker2 = viewdate.findViewById(R.id.datepicker2);
        RadioButton rdAccmonth = viewdate.findViewById(R.id.rdAccmonth);
        RadioButton rdAccmdays30 = viewdate.findViewById(R.id.rdAccmdays30);
        RadioButton rdAccmdays90 = viewdate.findViewById(R.id.rdAccmdays90);
        Button Accbtmclear = viewdate.findViewById(R.id.Accbtmclear);
        Button Accbtmapply = viewdate.findViewById(R.id.Accbtmapply);

        DatePickerDialog.OnDateSetListener fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.YEAR, year);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                txtAccfrdate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener toDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.YEAR, year);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                txtAcctodate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        txtAccfrdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, fromDateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        datepicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, fromDateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        txtAcctodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, toDateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        datepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, toDateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btmAccclosedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        Accbtmclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdAccmonth.setChecked(false);
                rdAccmdays30.setChecked(false);
                rdAccmdays90.setChecked(false);
                txtAccfrdate.setText("");
                txtAcctodate.setText("");

            }
        });

        Accbtmapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fromDateString = txtAccfrdate.getText().toString();
                String toDateString = txtAcctodate.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Date fromDate = null;
                Date toDate = null;

                if (!fromDateString.isEmpty() && !toDateString.isEmpty()) {
                    try {
                        fromDate = dateFormat.parse(fromDateString);
                        toDate = dateFormat.parse(toDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("Date Parse Error", "From Date: " + fromDateString + ", To Date: " + toDateString);
                        Toast.makeText(getActivity(), "Invalid date format", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Calendar oneMonthAgo = Calendar.getInstance();
                oneMonthAgo.add(Calendar.MONTH, -1);
                String oneMonthAgoString = dateFormat.format(oneMonthAgo.getTime());

                Calendar thirtyDaysAgo = Calendar.getInstance();
                thirtyDaysAgo.add(Calendar.DAY_OF_YEAR, -30);
                String thirtyDaysAgoString = dateFormat.format(thirtyDaysAgo.getTime());

                Calendar ninetyDaysAgo = Calendar.getInstance();
                ninetyDaysAgo.add(Calendar.DAY_OF_YEAR, -90);
                String ninetyDaysAgoString = dateFormat.format(ninetyDaysAgo.getTime());

                Intent intent = new Intent(getActivity(), AccountStatementActivity.class);
                intent.putExtra("FROM_DATE", fromDateString);
                intent.putExtra("TO_DATE", toDateString);
                intent.putExtra("ONE_MONTH_AGO", oneMonthAgoString);
                intent.putExtra("THIRTY_DAYS_AGO", thirtyDaysAgoString);
                intent.putExtra("NINETY_DAYS_AGO", ninetyDaysAgoString);
                startActivity(intent);

                bottomSheetDialog.dismiss();
            }
        });

    }
}
