package com.maximus.maximusbank.activity.account;


import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.maximus.maximusbank.Model.AccountStatement;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.adapter.TransactionAdapter;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class AccountStatementActivity extends AppCompatActivity {
    ArrayList<AccountStatement> AccountArrayList;
    private TransactionAdapter transactionAdapter;
    Button buttonPDF, buttonExcel;
    ImageView backButton;
    TextView headerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statement);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAccountStatement);
        buttonPDF = findViewById(R.id.buttonPDF);
        buttonExcel = findViewById(R.id.buttonExcel);
        backButton = findViewById(R.id.backButton);
        headerTextView = findViewById(R.id.headerTextView);

        headerTextView.setText(R.string.Account_statement);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AccountArrayList = new ArrayList<>();

        String json = addItemsFromJSON();
        Log.d("AccountStatementActivity", "Loaded JSON: " + json);

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("accountStatement");
            AccountStatement accountStatement;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject transJson = jsonArray.getJSONObject(i);
                String date = transJson.getString("date");
                String description = transJson.getString("description");
                String transaction = transJson.getString("transaction");
                int amount = transJson.getInt("amount");
                accountStatement = new AccountStatement(date, amount, description, transaction);
                AccountArrayList.add(accountStatement);
            }

            Intent intent = getIntent();
            String fromDateStr = intent.getStringExtra("fromDate");
            String toDateStr = intent.getStringExtra("toDate");

            if (fromDateStr != null && toDateStr != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                try {
                    Date fromDate = dateFormat.parse(fromDateStr);
                    Date toDate = dateFormat.parse(toDateStr);
                    Log.d("AccountStatementActivity", "Filtering from " + fromDate + " to " + toDate);
                    AccountArrayList = filterStatementsByDateRange(AccountArrayList, fromDate, toDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Invalid date format received", Toast.LENGTH_SHORT).show();
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            transactionAdapter = new TransactionAdapter(AccountArrayList, this);
            recyclerView.setAdapter(transactionAdapter);

        } catch (JSONException e) {
            Log.e("AccountStatementActivity", "JSON parsing error: " + e.getMessage());
            e.printStackTrace();
        }

        buttonExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExcel();
            }
        });

        buttonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertToPDF();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private ArrayList<AccountStatement> filterStatementsByDateRange(ArrayList<AccountStatement> accountArrayList, Date fromDate, Date toDate) {
        ArrayList<AccountStatement> filteredList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        for (AccountStatement statement : accountArrayList) {
            try {
                Date statementDate = dateFormat.parse(statement.getDate());
                if ((statementDate.equals(fromDate) || statementDate.after(fromDate)) &&
                        (statementDate.equals(toDate) || statementDate.before(toDate))) {
                    filteredList.add(statement);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return filteredList;
    }

    private void saveExcel() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dir + "/account_statement.xls";

        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(new File(filePath));
            WritableSheet sheet = workbook.createSheet("Account Statement", 0);

            // Create header row
            sheet.addCell(new Label(0, 0, "Date"));
            sheet.addCell(new Label(1, 0, "Description"));
            sheet.addCell(new Label(2, 0, "Transaction"));
            sheet.addCell(new Label(3, 0, "Amount"));

            // Fill data
            int rowNum = 1;
            for (AccountStatement account : AccountArrayList) {
                sheet.addCell(new Label(0, rowNum, account.getDate()));
                sheet.addCell(new Label(1, rowNum, account.getDescription()));
                sheet.addCell(new Label(2, rowNum, account.getTransaction()));
                sheet.addCell(new jxl.write.Number(3, rowNum, account.getAmount()));
                rowNum++;
            }

            // Write to file
            workbook.write();
            Toast.makeText(this, "Excel saved at: " + filePath, Toast.LENGTH_LONG).show();

            // Notify the file manager about the new file
            MediaScannerConnection.scanFile(this, new String[]{filePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.d("convertToExcel", "File scanned: " + path);
                }
            });

        } catch (IOException | WriteException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void convertToPDF() {

        savePDF();

      /*  String filePath = getExternalFilesDir(null) + "/account_statement.pdf";

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Add your data to the PDF
            document.add(new Paragraph("Account Statement"));
            for (AccountStatement account : AccountArrayList) {
                document.add(new Paragraph(account.getDate() + " " + account.getDescription() + " " + account.getTransaction() + " " + account.getAmount()));
            }

            document.close();
            Toast.makeText(this, "PDF saved at: " + filePath, Toast.LENGTH_LONG).show();

            // Notify the file manager about the new file
            MediaScannerConnection.scanFile(this, new String[]{filePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.d("convertToPDF", "File scanned: " + path);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    private void savePDF() {
        Intent intent = getIntent();
        String fromDateStr = intent.getStringExtra("FROM_DATE");
        String toDateStr = intent.getStringExtra("TO_DATE");

        Date fromDate = null;
        Date toDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        if (fromDateStr != null && toDateStr != null) {
            try {
                fromDate = dateFormat.parse(fromDateStr);
                toDate = dateFormat.parse(toDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        ArrayList<AccountStatement> filteredList = (fromDate != null && toDate != null)
                ? filterStatementsByDateRange(AccountArrayList, fromDate, toDate)
                : new ArrayList<>(AccountArrayList);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, "account_statement.pdf");
            values.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
            values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/MyAppFolder");

            Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                    PdfWriter writer = new PdfWriter(outputStream);
                    PdfDocument pdfDocument = new PdfDocument(writer);
                    Document document = new Document(pdfDocument);

                    // Added hardcoded information for a while
                    document.add(new Paragraph("Bank Name: Maximus Bank")
                            .setBold().setFontSize(12));
                    document.add(new Paragraph("Customer Name: Shivanjali Pati")
                            .setBold().setFontSize(12));
                    document.add(new Paragraph("Address: 123 Bank St, Cityville, Country")
                            .setBold().setFontSize(12));
                    document.add(new Paragraph("Account No: 123456789")
                            .setBold().setFontSize(12));
                    document.add(new Paragraph(" ")
                            .setFontSize(12));

                    //date range
                    document.add(new Paragraph("Statement Period: From " + fromDateStr + " to " + toDateStr));


                    document.add(new Paragraph("Account Statement")
                            .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));

                    float[] columnWidths = {1, 3, 3, 2};
                    Table table = new Table(columnWidths);

                    table.addHeaderCell(new Cell().add(new Paragraph("Date")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
                    table.addHeaderCell(new Cell().add(new Paragraph("Description")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
                    table.addHeaderCell(new Cell().add(new Paragraph("Transaction")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
                    table.addHeaderCell(new Cell().add(new Paragraph("Amount")).setBackgroundColor(new DeviceRgb(200, 200, 200)));

                    for (AccountStatement account : filteredList) {
                        table.addCell(new Cell().add(new Paragraph(account.getDate())));
                        table.addCell(new Cell().add(new Paragraph(account.getDescription())));
                        table.addCell(new Cell().add(new Paragraph(account.getTransaction())));
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(account.getAmount()))));
                    }

                    table.setBorder(new SolidBorder(DeviceRgb.BLACK, 1));
                    document.add(table);

                    document.close();
                    Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("PDFSaveError", "Error saving PDF", e);
                    Toast.makeText(this, "Failed to save PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Failed to create file URI", Toast.LENGTH_LONG).show();
            }
        } else {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = dir + "/account_statement.pdf";

            try {
                PdfWriter writer = new PdfWriter(filePath);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                // Added hardcoded information for a while
                document.add(new Paragraph("Bank Name: Maximus Bank")
                        .setBold().setFontSize(12));
                document.add(new Paragraph("Customer Name: Shivanjali Pati")
                        .setBold().setFontSize(12));
                document.add(new Paragraph("Address: 123 Bank St, Cityville, Country")
                        .setBold().setFontSize(12));
                document.add(new Paragraph("Account No: 123456789")
                        .setBold().setFontSize(12));
                document.add(new Paragraph(" ")
                        .setFontSize(12));

                document.add(new Paragraph("Account Statement")
                        .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));

                float[] columnWidths = {1, 3, 3, 2};
                Table table = new Table(columnWidths);

                table.addHeaderCell(new Cell().add(new Paragraph("Date")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
                table.addHeaderCell(new Cell().add(new Paragraph("Description")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
                table.addHeaderCell(new Cell().add(new Paragraph("Transaction")).setBackgroundColor(new DeviceRgb(200, 200, 200)));
                table.addHeaderCell(new Cell().add(new Paragraph("Amount")).setBackgroundColor(new DeviceRgb(200, 200, 200)));

                for (AccountStatement account : filteredList) {
                    table.addCell(new Cell().add(new Paragraph(account.getDate())));
                    table.addCell(new Cell().add(new Paragraph(account.getDescription())));
                    table.addCell(new Cell().add(new Paragraph(account.getTransaction())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(account.getAmount()))));
                }

                table.setBorder(new SolidBorder(DeviceRgb.BLACK, 1));
                document.add(table);

                document.close();
                Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("PDFSaveError", "Error saving PDF", e);
                Toast.makeText(this, "Failed to save PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String addItemsFromJSON() {
        String json = null;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.accountstatement);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
            Log.d("TransactionHistory", "JSON Data: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
