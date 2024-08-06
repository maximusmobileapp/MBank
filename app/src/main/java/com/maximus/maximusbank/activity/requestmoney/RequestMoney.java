package com.maximus.maximusbank.activity.requestmoney;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.maximus.maximusbank.Model.Contact;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.DashboardActivity;
import com.maximus.maximusbank.adapter.ContactAdapter;
import com.maximus.maximusbank.adapter.RequestContactAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestMoney extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 1;
    private String contactName;
    private String photoUri;
    private RequestContactAdapter contactsAdapter;
    private List<Contact> contacts = new ArrayList<>();
    EditText et_amu, et_reason;
    private BottomSheetDialog bottomsheetContacts;
    private BottomSheetDialog bottomsheetRequestSend;
    TextView headerTextView;
    private String amount;
    private ShapeableImageView imgContact;
    private TextView txtContactName;
    private ImageButton imgadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_money);

        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.request_money);

        bottomsheetContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    @SuppressLint("MissingInflatedId")
    private void bottomsheetContacts() {
        View viewContact = LayoutInflater.from(this).inflate(R.layout.bottomsheetrequest, null);
        bottomsheetContacts = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomsheetContacts.setContentView(viewContact);
        bottomsheetContacts.setCancelable(false);
        bottomsheetContacts.show();

        ImageButton btmclose = viewContact.findViewById(R.id.btmclose);
        et_amu = viewContact.findViewById(R.id.et_amu);
        et_reason = viewContact.findViewById(R.id.et_reason);
        imgadd = viewContact.findViewById(R.id.imgadd);
        Button btnsend = viewContact.findViewById(R.id.btnsend);
        final RecyclerView recyclerContacts = viewContact.findViewById(R.id.recycler_contacts);
        imgContact = viewContact.findViewById(R.id.imgContact);
        txtContactName = viewContact.findViewById(R.id.txtContactName);

        recyclerContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        contactsAdapter = new RequestContactAdapter(this, contacts);
        recyclerContacts.setAdapter(contactsAdapter);

        btmclose.setOnClickListener(v -> {
            Intent intent = new Intent(RequestMoney.this, DashboardActivity.class);
            startActivity(intent);
        });

        imgadd.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(RequestMoney.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                openContactPicker();
            } else {
                checkPermissions();
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    amount = et_amu.getText().toString().trim();
                    dismissBottomSheet();
                    BottomsheetRequestSend();
                } else {
                    Toast.makeText(RequestMoney.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dismissBottomSheet() {
        if (bottomsheetContacts != null && bottomsheetContacts.isShowing()) {
            bottomsheetContacts.dismiss();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (et_amu == null || et_amu.getText().toString().trim().isEmpty()) {
            et_amu.setError("Enter Amount");
            isValid = false;
        } else if (et_reason == null || et_reason.getText().toString().trim().isEmpty()) {
            et_reason.setError("Enter Reason");
            isValid = false;
        }
        return isValid;
    }

    @SuppressLint("MissingInflatedId")
    private void BottomsheetRequestSend() {
        View viewSend = LayoutInflater.from(this).inflate(R.layout.bottomsheetsend, null);
        bottomsheetRequestSend = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomsheetRequestSend.setContentView(viewSend);
        bottomsheetRequestSend.setCancelable(false);
        bottomsheetRequestSend.show();

        ImageButton btmclose = viewSend.findViewById(R.id.btmclose);
        Button btnok = viewSend.findViewById(R.id.btnok);
        TextView txtto = viewSend.findViewById(R.id.txtto);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView amountview = viewSend.findViewById(R.id.amountview);
        imgContact = viewSend.findViewById(R.id.imgContact);
        txtContactName = viewSend.findViewById(R.id.txtContactName);

        amountview.setText(amount);

        // Set the contact image and name
        imgContact.setVisibility(View.VISIBLE);
        txtContactName.setVisibility(View.VISIBLE);

        if (contactName != null) {
            txtContactName.setVisibility(View.VISIBLE);
            txtContactName.setText(contactName);
        } else {
            txtContactName.setVisibility(View.GONE);
        }

        if (photoUri != null) {
            Glide.with(this)
                    .load(photoUri)
                    .placeholder(R.drawable.person)
                    .error(R.drawable.person)
                    .circleCrop()
                    .into(imgContact);
        } else {
            imgContact.setImageResource(R.drawable.person);
        }


        amountview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*amountview.post(new Runnable() {
                    @Override
                    public void run() {
                        int amountWidth = amountview.getWidth();
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) txtto.getLayoutParams();
                        params.addRule(RelativeLayout.RIGHT_OF, R.id.amountview);
                        params.leftMargin = amountWidth;
                        txtto.setLayoutParams(params);
                    }
                });*/
            }
        });

        amountview.setText(amount);

        btmclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestMoney.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestMoney.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            openContactPicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openContactPicker();
            } else {
                Toast.makeText(this, "Permission required to access contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openContactPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        contactPickerLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> contactPickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                handleContactResult(data.getData());
            }
        }
    });

    private void handleContactResult(Uri contactUri) {
        if (contactUri != null) {
            Log.d("RequestMoney", "Contact URI: " + contactUri.toString());

            String[] projection = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.PHOTO_URI};

            ContentResolver contentResolver = getContentResolver();

            Cursor cursor = null;
            try {
                cursor = contentResolver.query(contactUri, projection, null, null, null);

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int nameIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
                         contactName = cursor.getString(nameIndex);

                        int photoUriIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI);
                         photoUri = cursor.getString(photoUriIndex);

                        Log.d("RequestMoney", "Contact Name: " + contactName);

                        if (contactName != null) {
                            imgContact.setVisibility(View.VISIBLE);
                            txtContactName.setVisibility(View.VISIBLE);

                            txtContactName.setText(contactName);

                            if (photoUri != null) {
                                Glide.with(this)
                                        .load(photoUri)
                                        .placeholder(R.drawable.person)
                                        .error(R.drawable.person)
                                        .circleCrop()
                                        .into(imgContact);
                            } else {
                                imgContact.setImageResource(R.drawable.person);
                            }
//                            imgadd.setVisibility(View.GONE);

                            Contact contact = new Contact(contactName, null);
                            contacts.add(contact);
                            contactsAdapter.notifyDataSetChanged();

                            Log.d("RequestMoney", "Contact added to list");
                        } else {
                            Log.d("RequestMoney", "Contact name is null");
                        }
                    } else {
                        Log.d("RequestMoney", "Cursor is empty");
                    }
                } else {
                    Log.d("RequestMoney", "Cursor is null");
                }
            } catch (SecurityException e) {
                Log.e("RequestMoney", "Security exception while accessing contacts", e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else {
            Log.d("RequestMoney", "Contact URI is null");
        }
    }

  /*  @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }*/
}
