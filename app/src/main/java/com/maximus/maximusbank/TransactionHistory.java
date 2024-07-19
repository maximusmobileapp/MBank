package com.maximus.maximusbank;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.maximus.maximusbank.Model.Transaction;
import com.maximus.maximusbank.adapter.TransactionListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TransactionHistory extends AppCompatActivity {
    ImageButton imgback, imgdots, imgfilter;
    TextView txtstate, txtpayment, txtdate, txtamount, txtpaytype;
    EditText search;
    private RecyclerView recyclerView;
    private List<Transaction> allTransactions = new ArrayList<>();
    private TransactionListAdapter transactionListAdapter;
    ArrayList<Transaction> transactionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        //ImageButton
        imgback = findViewById(R.id.imgback);
        imgdots = findViewById(R.id.imgdots);
        imgfilter = findViewById(R.id.imgfilter);

        //TextView
        txtstate = findViewById(R.id.txtstate);
        txtpayment = findViewById(R.id.txtpayment);
        txtdate = findViewById(R.id.txtdate);
        txtamount = findViewById(R.id.txtamount);
        txtpaytype = findViewById(R.id.txtpaytype);
        recyclerView = findViewById(R.id.recyclerview);

        //EditText
        search = findViewById(R.id.search);

        transactionArrayList = new ArrayList<>();


        String transactionJson = addItemsFromJSON();
        try {
            JSONObject jsonObject = new JSONObject(transactionJson);
            JSONArray jsonArray = jsonObject.getJSONArray("Transactions");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject transJson = new JSONObject(String.valueOf(jsonArray.get(i)));
                String name = transJson.getString("name");
                String date = transJson.getString("date");
                int amount = transJson.getInt("amount");
                String payment = transJson.getString("payment");
                String status = transJson.getString("status");
                Transaction transaction = new Transaction(name, date, amount, payment, status);
                transactionArrayList.add(transaction);
            }
            allTransactions.addAll(transactionArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            transactionListAdapter = new TransactionListAdapter(transactionArrayList);
            recyclerView.setAdapter(transactionListAdapter);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgdots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        txtstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBottomSheet();
            }
        });

        imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogfilter();
            }
        });

        txtpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentBottomSheet();
            }
        });

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateBottomSheet();
            }
        });

        txtamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountBottomSheet();
            }
        });
    }

    private void showPopupMenu(View view) {
        android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menudots, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(TransactionHistory.this, "You Clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            Toast.makeText(this, "Bottom sheet of feedback", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private String addItemsFromJSON() {
        String json = null;

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.transactions);
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    private void statusBottomSheet() {
        View viewstatus = LayoutInflater.from(this).inflate(R.layout.bottomsheetstatus, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewstatus);
        bottomSheetDialog.show();

        ImageButton btmclose = viewstatus.findViewById(R.id.btmclose);
        RadioButton txtcomplete = viewstatus.findViewById(R.id.txtcomplete);
        RadioButton txtfail = viewstatus.findViewById(R.id.txtfail);
        RadioButton txtprocess = viewstatus.findViewById(R.id.txtprocess);
        Button btmclear = viewstatus.findViewById(R.id.btmclear);
        Button btmapply = viewstatus.findViewById(R.id.btmapply);

        btmclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        txtcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcomplete.setTextIsSelectable(true);
            }
        });

        btmclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcomplete.setChecked(false);
                txtfail.setChecked(false);
                txtprocess.setChecked(false);

                transactionArrayList.clear();
                transactionArrayList.addAll(allTransactions);
                transactionListAdapter.notifyDataSetChanged();
            }
        });

        btmapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> filteredList = new ArrayList<>();

                boolean isCompleteSelected = txtcomplete.isChecked();
                boolean isFailSelected = txtfail.isChecked();
                boolean isProcessSelected = txtprocess.isChecked();

                for (Transaction transaction : allTransactions) {
                    String status = transaction.getStatus();
                    if ((isCompleteSelected && status.equals("Completed")) ||
                            (isFailSelected && status.equals("Failed")) ||
                            (isProcessSelected && status.equals("Processing"))) {
                        filteredList.add(transaction);
                    }
                }

                transactionArrayList.clear();
                transactionArrayList.addAll(filteredList);
                transactionListAdapter.notifyDataSetChanged();

//                updateRecyclerView(filteredList);
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void paymentBottomSheet() {
        View viewpayment = LayoutInflater.from(this).inflate(R.layout.bottomsheetpaymet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewpayment);
        bottomSheetDialog.show();

        ImageButton btmclosepay = viewpayment.findViewById(R.id.btmclosepay);
        Button btmapply = viewpayment.findViewById(R.id.btmapply);
        Button btmclear = viewpayment.findViewById(R.id.btmclear);
        CheckBox btmwithin = viewpayment.findViewById(R.id.btmwithin);
        CheckBox btmOtherbank = viewpayment.findViewById(R.id.btmOtherbank);
        CheckBox btmself = viewpayment.findViewById(R.id.btmself);


        btmapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> filteredList = new ArrayList<>();

                boolean isWithinBankSelected = btmwithin.isChecked();
                boolean isOtherBankSelected = btmOtherbank.isChecked();
                boolean isSelfBankSelected = btmself.isChecked();


                for (Transaction transaction : allTransactions) {
                    String payment = transaction.getPayment();

                    if ((isWithinBankSelected && payment.equals("Within Bank")) ||
                            (isOtherBankSelected && payment.equals("Other Bank")) ||
                            (isSelfBankSelected && payment.equals("Self Bank"))) {
                        filteredList.add(transaction);
                    }
                }

                transactionArrayList.clear();
                transactionArrayList.addAll(filteredList);
                transactionListAdapter.notifyDataSetChanged();

                bottomSheetDialog.dismiss();
            }
        });


        btmclosepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btmclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btmwithin.setChecked(false);
                btmOtherbank.setChecked(false);
                btmself.setChecked(false);

                transactionArrayList.clear();
                transactionArrayList.addAll(allTransactions);
                transactionListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void dateBottomSheet() {
        View viewdate = LayoutInflater.from(this).inflate(R.layout.bottomsheetdate, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewdate);
        bottomSheetDialog.show();

        final Calendar myCalendar = Calendar.getInstance();

        TextView fromdate = viewdate.findViewById(R.id.fromdate);
        TextView todate = viewdate.findViewById(R.id.todate);
        ImageView datepicker1 = viewdate.findViewById(R.id.datepicker1);
        ImageView datepicker2 = viewdate.findViewById(R.id.datepicker2);
        RadioButton btmonth = viewdate.findViewById(R.id.btmonth);
        RadioButton btmdays30 = viewdate.findViewById(R.id.btmdays30);
        RadioButton btmdays90 = viewdate.findViewById(R.id.btmdays90);
        ImageButton btmclosedate = viewdate.findViewById(R.id.btmclosedate);
        Button btmclear = viewdate.findViewById(R.id.btmclear);
        Button btmapply = viewdate.findViewById(R.id.btmapply);


        DatePickerDialog.OnDateSetListener frommdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.YEAR, year);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                fromdate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener toodate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.YEAR, year);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                todate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TransactionHistory.this, frommdate,
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(TransactionHistory.this, frommdate,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TransactionHistory.this, toodate,
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(TransactionHistory.this, toodate,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btmclosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btmclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btmonth.setChecked(false);
                btmdays30.setChecked(false);
                btmdays90.setChecked(false);
                fromdate.setText("");
                todate.setText("");

            }
        });

        btmapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDateString = fromdate.getText().toString();
                String toDateString = todate.getText().toString();
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
                        Toast.makeText(TransactionHistory.this, "Invalid date format", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                List<Transaction> filteredList = new ArrayList<>();

                for (Transaction transaction : allTransactions) {
                    try {
                        Date transactionDate = dateFormat.parse(transaction.getDate());


                        boolean isDateInRange = fromDate != null && toDate != null &&
                                !transactionDate.before(fromDate) &&
                                !transactionDate.after(toDate);

                        if (isDateInRange) {
                            filteredList.add(transaction);
                        } else if (btmonth.isChecked() && isCurrentMonth(transactionDate)) {
                            filteredList.add(transaction);
                        } else if (btmdays30.isChecked() && isWithinLastDays(transactionDate, 30)) {
                            filteredList.add(transaction);
                        } else if (btmdays90.isChecked() && isWithinLastDays(transactionDate, 90)) {
                            filteredList.add(transaction);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                transactionArrayList.clear();
                transactionArrayList.addAll(filteredList);
                transactionListAdapter.notifyDataSetChanged();

                bottomSheetDialog.dismiss();
            }

            private boolean isCurrentMonth(Date date) {
                Calendar cal = Calendar.getInstance();
                int currentMonth = cal.get(Calendar.MONTH);
                int currentYear = cal.get(Calendar.YEAR);

                cal.setTime(date);
                int transactionMonth = cal.get(Calendar.MONTH);
                int transactionYear = cal.get(Calendar.YEAR);

                return transactionMonth == currentMonth && transactionYear == currentYear;
            }

            private boolean isWithinLastDays(Date date, int days) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -days);
                return date.after(cal.getTime());
            }
        });
    }

   /* private void updateRecyclerView(List<Transaction> filteredList) {
      //  transactionListAdapter.updateData(filteredList);
        TransactionListAdapter adapter = new TransactionListAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }*/

    private void amountBottomSheet() {
        View viewamount = LayoutInflater.from(this).inflate(R.layout.bottomsheetamount, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewamount);
        bottomSheetDialog.show();

        ImageButton btmcloseamount = viewamount.findViewById(R.id.btmcloseamount);
        Button btmapply = viewamount.findViewById(R.id.btmapply);
        Button btmclear = viewamount.findViewById(R.id.btmclear);
        CheckBox btmruppee1 = viewamount.findViewById(R.id.btmruppee1);
        CheckBox btmruppee2 = viewamount.findViewById(R.id.btmruppee2);
        CheckBox btmruppee3 = viewamount.findViewById(R.id.btmruppee3);
        CheckBox btmruppee4 = viewamount.findViewById(R.id.btmruppee4);

        btmapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Transaction> filteredTransactions = new ArrayList<>();

                Log.d("FilterDebug", "btmruppee1 isChecked: " + btmruppee1.isChecked());
                Log.d("FilterDebug", "btmruppee2 isChecked: " + btmruppee2.isChecked());
                Log.d("FilterDebug", "btmruppee3 isChecked: " + btmruppee3.isChecked());
                Log.d("FilterDebug", "btmruppee4 isChecked: " + btmruppee4.isChecked());

                for (Transaction transaction : allTransactions) {
                    int amount = transaction.getAmount();
                    Log.d("FilterDebug", "Transaction Amount: " + amount);

                    if (btmruppee1.isChecked() && amount >= 0 && amount <= 200) {
                        Log.d("FilterDebug", "Amount between 0 and 200");
                        filteredTransactions.add(transaction);
                    } else if (btmruppee2.isChecked() && amount > 200 && amount <= 500) {
                        Log.d("FilterDebug", "Amount between 200 and 500");
                        filteredTransactions.add(transaction);
                    } else if (btmruppee3.isChecked() && amount > 500 && amount <= 2000) {
                        Log.d("FilterDebug", "Amount between 500 and 2000");
                        filteredTransactions.add(transaction);
                    } else if (btmruppee4.isChecked() && amount > 2000) {
                        Log.d("FilterDebug", "Amount above 2000");
                        filteredTransactions.add(transaction);

                    }
                }

                Log.d("FilterDebug", "Filtered Transactions Size: " + filteredTransactions.size());

                if (filteredTransactions.isEmpty()) {
                    transactionArrayList.clear();
                    transactionArrayList.addAll(allTransactions);
                } else {
                    transactionArrayList.clear();
                    transactionArrayList.addAll(filteredTransactions);
                }
                transactionListAdapter.notifyDataSetChanged();

                bottomSheetDialog.dismiss();
            }
        });

        btmclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btmruppee1.setChecked(false);
                btmruppee2.setChecked(false);
                btmruppee3.setChecked(false);
                btmruppee4.setChecked(false);

                transactionArrayList.clear();
                transactionArrayList.addAll(allTransactions);
                transactionListAdapter.notifyDataSetChanged();
            }
        });

        btmcloseamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void showdialogfilter() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransactionHistory.this, R.style.myFullscreenAlertDialogStyle);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filters, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        ImageButton imgclose = dialogView.findViewById(R.id.imgclose);
        TextView txtStatus = dialogView.findViewById(R.id.txtStatus);
        Button btnapply = dialogView.findViewById(R.id.btnapply);
        Button btnclear = dialogView.findViewById(R.id.btnclear);
        RadioButton txtcomplete = dialogView.findViewById(R.id.txtcomplete);
        RadioButton txtfail = dialogView.findViewById(R.id.txtfail);
        RadioButton txtprocess = dialogView.findViewById(R.id.txtprocess);
        CheckBox txtwithin = dialogView.findViewById(R.id.txtwithin);
        CheckBox txtOtherbank = dialogView.findViewById(R.id.txtOtherbank);
        CheckBox txtself = dialogView.findViewById(R.id.txtself);
        RadioButton txtmonth = dialogView.findViewById(R.id.txtmonth);
        RadioButton txtdays30 = dialogView.findViewById(R.id.txtdays30);
        RadioButton txtdays90 = dialogView.findViewById(R.id.txtdays90);
        CheckBox txtruppee1 = dialogView.findViewById(R.id.txtruppee1);
        CheckBox txtruppee2 = dialogView.findViewById(R.id.txtruppee2);
        CheckBox txtruppee3 = dialogView.findViewById(R.id.txtruppee3);
        CheckBox txtruppee4 = dialogView.findViewById(R.id.txtruppee4);


        txtStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> filteredList = new ArrayList<>();

                //Status
                boolean isCompleteSelected = txtcomplete.isChecked();
                boolean isFailSelected = txtfail.isChecked();
                boolean isProcessSelected = txtprocess.isChecked();

                for (Transaction transaction : allTransactions) {
                    String status = transaction.getStatus();
                    if ((isCompleteSelected && status.equals("Completed")) ||
                            (isFailSelected && status.equals("Failed")) ||
                            (isProcessSelected && status.equals("Processing"))) {
                        filteredList.add(transaction);
                    }
                }

                transactionArrayList.clear();
                transactionArrayList.addAll(filteredList);
                transactionListAdapter.notifyDataSetChanged();


                //Payment method
                boolean isWithinBankSelected = txtwithin.isChecked();
                boolean isOtherBankSelected = txtOtherbank.isChecked();
                boolean isSelfBankSelected = txtself.isChecked();

                for (Transaction transaction : allTransactions) {
                    String payment = transaction.getPayment();

                    if ((isWithinBankSelected && payment.equals("Within Bank")) ||
                            (isOtherBankSelected && payment.equals("Other Bank")) ||
                            (isSelfBankSelected && payment.equals("Self Bank"))) {
                        filteredList.add(transaction);
                    }
                }

                transactionArrayList.clear();
                transactionArrayList.addAll(filteredList);
                transactionListAdapter.notifyDataSetChanged();


                //amount
                List<Transaction> filteredTransactions = new ArrayList<>();

                for (Transaction transaction : allTransactions) {
                    int amount = transaction.getAmount();
                    if ((txtruppee1.isChecked() && amount >= 0 && amount <= 200) ||
                            (txtruppee2.isChecked() && amount >= 200 && amount <= 500) ||
                            (txtruppee3.isChecked() && amount >= 500 && amount <= 2000) ||
                            (txtruppee4.isChecked() && amount >= 2000)) {
                        filteredTransactions.add(transaction);
                    }
                }

                if (filteredTransactions.isEmpty()) {
                    transactionArrayList.clear();
                    transactionArrayList.addAll(allTransactions);
                } else {
                    transactionArrayList.clear();
                    transactionArrayList.addAll(filteredTransactions);
                }
                transactionListAdapter.notifyDataSetChanged();


                //date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

                for (Transaction transaction : allTransactions) {
                    try {
                        Date transactionDate = dateFormat.parse(transaction.getDate());

                        if (txtmonth.isChecked() && isCurrentMonth(transactionDate)) {
                            filteredList.add(transaction);
                        } else if (txtdays30.isChecked() && isWithinLastDays(transactionDate, 30)) {
                            filteredList.add(transaction);
                        } else if (txtdays90.isChecked() && isWithinLastDays(transactionDate, 90)) {
                            filteredList.add(transaction);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                transactionArrayList.clear();
                transactionArrayList.addAll(filteredList);
                transactionListAdapter.notifyDataSetChanged();

                alertDialog.dismiss();
            }

            private boolean isCurrentMonth(Date date) {
                Calendar cal = Calendar.getInstance();
                int currentMonth = cal.get(Calendar.MONTH);
                int currentYear = cal.get(Calendar.YEAR);

                cal.setTime(date);
                int transactionMonth = cal.get(Calendar.MONTH);
                int transactionYear = cal.get(Calendar.YEAR);

                return transactionMonth == currentMonth && transactionYear == currentYear;
            }

            private boolean isWithinLastDays(Date date, int days) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -days);
                return date.after(cal.getTime());
            }
        });


        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcomplete.setChecked(false);
                txtfail.setChecked(false);
                txtprocess.setChecked(false);
                txtwithin.setChecked(false);
                txtOtherbank.setChecked(false);
                txtself.setChecked(false);
                txtmonth.setChecked(false);
                txtdays30.setChecked(false);
                txtdays90.setChecked(false);
                txtruppee1.setChecked(false);
                txtruppee2.setChecked(false);
                txtruppee3.setChecked(false);
                txtruppee4.setChecked(false);

            }
        });

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}