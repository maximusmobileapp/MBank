package com.maximus.maximusbank.activity.splitBill;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maximus.maximusbank.Model.Contact;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Constants;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.ContactAdapter;
import com.maximus.maximusbank.adapter.SelectedContactsAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiltBillAmount extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    protected Map<Long, List<String>> phones = new HashMap<>();
    protected List<Contact> contacts = new ArrayList<>();
    private RecyclerView contactsRecyclerView;
    private RecyclerView selectedContactsRecyclerView;
    private SelectedContactsAdapter selectedContactsAdapter;
    private List<Contact> selectedContacts = new ArrayList<>();
    private int totalBillAmount;
    Button splitNowButton;
    EditText totalAmountEditText;
    TextView noSelectedContactTxt;
    ProgressBar contactProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilt_bill);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.split_bill);
        splitNowButton = findViewById(R.id.splitNowButton);
        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        selectedContactsRecyclerView = findViewById(R.id.selectedContactsRecyclerView);
        totalAmountEditText = findViewById(R.id.totalAmountEditText);
        contactProgressBar = findViewById(R.id.contactProgressBar);
        noSelectedContactTxt = findViewById(R.id.noSelectedContactTxt);
        noSelectedContactTxt.setVisibility(View.VISIBLE);
        selectedContactsRecyclerView.setVisibility(View.GONE);
        selectedContactsAdapter = new SelectedContactsAdapter(SpiltBillAmount.this, selectedContacts, totalBillAmount, new SelectedContactsAdapter.OnAmountChangedListener() {
            @Override
            public void onAmountChanged(Contact contact) {
                if (selectedContacts.contains(contact)) {
                    selectedContacts.remove(contact);
                    selectedContactsAdapter.notifyDataSetChanged();
                }
            }
        });
        selectedContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedContactsRecyclerView.setAdapter(selectedContactsAdapter);
        splitNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountText = totalAmountEditText.getText().toString().trim();
                amountText = amountText.replace("₹", "").trim();
                if (!amountText.isEmpty()) {
                    try {
                        totalBillAmount = Integer.parseInt(amountText);
                        if (selectedContacts.isEmpty()) {
                            Utils.showSnackbar(SpiltBillAmount.this, "Please select at least one contact");
                        } else {
                            Intent intent = new Intent(SpiltBillAmount.this, SplitAmountActivity.class);
                            intent.putExtra("selectedContacts", new ArrayList<>(selectedContacts));
                            intent.putExtra("totalBillAmount", totalBillAmount);
                            startActivity(intent);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utils.showSnackbar(SpiltBillAmount.this, "Please enter the amount");
                }

            }
        });
        totalAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("₹ ")) {
                    totalAmountEditText.setText("₹ ");
                    Selection.setSelection(totalAmountEditText.getText(), totalAmountEditText.getText().length());
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {
            case 0:
                return new CursorLoader(
                        this,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        Constants.PROJECTION_NUMBERS,
                        null,
                        null,
                        null
                );
            default:
                return new CursorLoader(
                        this,
                        ContactsContract.Contacts.CONTENT_URI,
                        Constants.PROJECTION_DETAILS,
                        null,
                        null,
                        null
                );
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 0:
                phones = new HashMap<>();
                if (data != null) {
                    while (!data.isClosed() && data.moveToNext()) {
                        long contactId = data.getLong(0);
                        String phone = data.getString(1);
                        List<String> list;
                        if (phones.containsKey(contactId)) {
                            list = phones.get(contactId);
                        } else {
                            list = new ArrayList<>();
                            phones.put(contactId, list);
                        }
                        list.add(phone);
                    }
                    data.close();
                }
                LoaderManager.getInstance(this).initLoader(1, null, this);
                break;
            case 1:
                if (data != null) {
                    while (!data.isClosed() && data.moveToNext()) {
                        long contactId = data.getLong(0);
                        String name = data.getString(1);
                        String photo = data.getString(2);
                        List<String> contactPhones = phones.get(contactId);
                        if (contactPhones != null) {
                            for (String phone :
                                    contactPhones) {
                                addContact(new Contact(contactId, name, phone, photo, 0));
                            }
                        }
                    }
                    data.close();
                    loadAdapter();
                }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    protected void loadAdapter() {
        if (contacts.size() > 0) {
            contactsRecyclerView.setVisibility(View.VISIBLE);
            contactProgressBar.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            contactsRecyclerView.setLayoutManager(layoutManager);
            contactsRecyclerView.setAdapter(new ContactAdapter(contacts, SpiltBillAmount.this, contact -> {
                if (!selectedContacts.contains(contact)) {
                    selectedContacts.add(contact);
                    selectedContactsAdapter.notifyDataSetChanged();
                }
                updateSelectedContactsVisibility();
            }));
        } else {
            contactsRecyclerView.setVisibility(View.GONE);
            contactProgressBar.setVisibility(View.VISIBLE);
        }

    }

    private void updateSelectedContactsVisibility() {
        if (selectedContacts.size() > 0) {
            selectedContactsRecyclerView.setVisibility(View.VISIBLE);
            noSelectedContactTxt.setVisibility(View.GONE);
        } else {
            selectedContactsRecyclerView.setVisibility(View.GONE);
            noSelectedContactTxt.setVisibility(View.VISIBLE);
        }
    }


    protected void addContact(Contact contact) {
        contacts.add(contact);
    }

}