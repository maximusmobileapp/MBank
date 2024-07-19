package com.maximus.maximusbank;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.maximus.maximusbank.Model.SpiltContact;
import com.maximus.maximusbank.adapter.SplitBillAdapter;

import java.util.ArrayList;

public class SpiltBill extends AppCompatActivity {
    ImageView imgvector;
    Button btnsearch;
    final int PICK_CONTACT = 1234;
    private static final int REQUEST_READ_CONTACTS = 123;
    ListView listitems;
    EditText etamount;
    ArrayList<SpiltContact> spiltContacts;
    SplitBillAdapter splitBillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spilt_bill);

        imgvector = findViewById(R.id.imgvector);
        btnsearch = findViewById(R.id.btnsearch);
        listitems = findViewById(R.id.listitems);
        etamount = findViewById(R.id.etamount);

        spiltContacts = new ArrayList<>();
        splitBillAdapter = new SplitBillAdapter(this, spiltContacts);
        listitems.setAdapter(splitBillAdapter);

        imgvector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    if (ContextCompat.checkSelfPermission(SpiltBill.this, android.Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SpiltBill.this, new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
                    } else {
                        pickContact();
                    }
                } else {
                    Toast.makeText(SpiltBill.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                /*if (validateFields()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                } else {
                    Toast.makeText(SpiltBill.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void pickContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    private boolean validateFields() {
        boolean isvalid = true;
        if (etamount == null || etamount.getText().toString().trim().isEmpty()) {
            etamount.setError("Enter Valid Amount");
            isvalid = false;
        } else {
            System.out.println("test");
        }
        return isvalid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.HAS_PHONE_NUMBER};

            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(contactUri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range")
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range")
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range")
                int hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            @SuppressLint("Range")
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            spiltContacts.add(new SpiltContact(contactName, phoneNumber));
                        }
                        phoneCursor.close();
                    }
                }
                cursor.close();
                splitBillAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickContact();
            } else {
                Toast.makeText(this, "Permission denied to read contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}