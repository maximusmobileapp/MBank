package com.maximus.maximusbank.activity.fundTransfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.DashboardActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class FT_Receipt extends AppCompatActivity {

    ImageButton imgclose, save;
    Button btndone;
    TextView txtrefno, paytime, txtfrom, txtto, txtamount;
    RelativeLayout pdfreceipt;
    InputStream ims;
    static Activity mActivity;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ft_receipt);

        imgclose = findViewById(R.id.imgclose);
        btndone = findViewById(R.id.btndone);
        save = findViewById(R.id.save);
        pdfreceipt = findViewById(R.id.pdfreceipt);
        txtrefno = findViewById(R.id.txtrefno);
        paytime = findViewById(R.id.paytime);
        txtfrom = findViewById(R.id.txtfrom);
        txtto = findViewById(R.id.txtto);
        txtamount = findViewById(R.id.txtamount);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            txtrefno.setText(extras.getString("refNo"));
            paytime.setText(extras.getString("paymentTime"));

            Map<String, String> stepDataMap = (Map<String, String>) extras.getSerializable("stepData");
            if (stepDataMap != null) {
                txtfrom.setText(stepDataMap.get("fromAccount"));
                txtto.setText(stepDataMap.get("payTo"));
                txtamount.setText(stepDataMap.get("amount"));
            }
            //remarksTextView.setText(stepData.get(3));


            imgclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FT_Receipt.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FT_Receipt.this, "Test Save Screenshot", Toast.LENGTH_SHORT).show();
                    saveScreenshot();
                }
            });

            btndone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FT_Receipt.this, DashboardActivity.class);
                    startActivity(intent);
                }
            });

            pdfreceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePDF();

                    Toast.makeText(FT_Receipt.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private void savePDF() {
        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "PDFs");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                Log.d("createPDF", "Directory created: " + dir.getAbsolutePath());
            } else {
                Log.e("createPDF", "Failed to create directory: " + dir.getAbsolutePath());
            }
        }

        String filePath = dir + "/receipt_" + getCurrentDate() + ".pdf";
        Log.d("createPDF", "Saving PDF to: " + filePath);

        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Receipt Details"));
            document.add(new Paragraph("Reference No: " + txtrefno.getText().toString()));
            document.add(new Paragraph("Payment Time: " + paytime.getText().toString()));
            document.add(new Paragraph("From Account: " + txtfrom.getText().toString()));
            document.add(new Paragraph("Pay To: " + txtto.getText().toString()));
            document.add(new Paragraph("Amount: " + txtamount.getText().toString()));

            document.close();
            Log.d("createPDF", "PDF created successfully.");

            // Check if file exists
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                Log.d("createPDF", "PDF file exists: " + filePath);
            } else {
                Log.e("createPDF", "PDF file does not exist: " + filePath);
            }

            Toast.makeText(this, "PDF saved at: " + filePath, Toast.LENGTH_LONG).show();

            // Notify the file manager about the new file
            MediaScannerConnection.scanFile(this, new String[]{filePath}, null, (path, uri) -> {
                Log.d("createPDF", "File scanned: " + path);
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF.", Toast.LENGTH_SHORT).show();
        }
    }


    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        // System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyyHHmmss");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    private void saveScreenshot() {
        try {
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            rootView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
            rootView.setDrawingCacheEnabled(false);

            File screenshotFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png");
            FileOutputStream fileOutputStream = new FileOutputStream(screenshotFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Screenshot", "Screenshot captured by app");

            Toast.makeText(this, "Screenshot saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save screenshot.", Toast.LENGTH_SHORT).show();
        }
    }

}
