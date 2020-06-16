package com.samit1.sysamit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WritePdfActivity extends AppCompatActivity {

    private Bitmap generateBimap(View view) {

        // Create a bitmap with same dimensions as view
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a canvas using bitmap
        Canvas canvas = new Canvas(bitmap);

        /**
         We need to check if view as backround image.
         It is important in case of overlays like photo frame feature
         */
        Drawable baground = view.getBackground();
        if (baground != null) {
            baground.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }

        // draw the view on the canvas
        view.draw(canvas);

        // final bitmap
        return bitmap;
    }

    private String saveImage(Bitmap bitmap) {

        // Get the destination folder for saved images defined in strings.xml
        String dstFolder = getString(R.string.dst_folder);

        // Create Destination folder in external storage. This will require EXTERNAL STORAGE permission
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File imgDir = new File(externalStorageDirectory.getAbsolutePath(), dstFolder);
        imgDir.mkdirs();

        // Generate a random file name for image
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpeg";
        File localFile = new File(imgDir, imageName);
        localFile.renameTo(localFile);
        String path = "file://" + externalStorageDirectory.getAbsolutePath() + "/" + dstFolder;

        try {
            FileOutputStream fos = new FileOutputStream(localFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(path))));
        } catch (Exception e)  {
            e.printStackTrace();
        }

        // Local path to be shown to User to tell where the Image has been saved.
        return path;
    }

    private void share(Bitmap bitmap) {
        try {
            Uri shareUri = getImageUri(this, bitmap);
            Intent localIntent = new Intent();
            localIntent.setAction("android.intent.action.SEND");
            localIntent.putExtra("android.intent.extra.STREAM", shareUri);
            localIntent.setType("image/jpg");
            localIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            this.startActivity(localIntent);
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    AnyChartView anyChartView;
    Button printBtn;
    String itemType[] = {"HARDWARE", "SOFTWARE"};
    int totalCase[] = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_pdf);

        final DatabaseReference checkFBvalue = FirebaseDatabase.getInstance()
                .getReference().child("april");


        // DatabaseReference checkFBvalue = FirebaseDatabase.getInstance()
        // .getReference("/Monthly Complaint/JANUARY");

        checkFBvalue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int xHardware = (int) dataSnapshot.child("hardware").getChildrenCount();
                int xSoftware = (int) dataSnapshot.child("software").getChildrenCount();

                //checkFBvalue.child("harware").setValue(xHardware);
                //checkFBvalue.child("software").setValue(xSoftware);

                int totalCase[] = {xHardware, xSoftware};

                anyChartView = findViewById(R.id.any_chart_january);

                Pie pie = AnyChart.pie();
                List<DataEntry> dataentries = new ArrayList<>();

                for (int i = 0; i<itemType.length; i++){
                    dataentries.add(new ValueDataEntry(itemType[i], totalCase[i]));

                }

                pie.data(dataentries);
                anyChartView.setChart(pie);
            }

            //@Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        printBtn = findViewById(R.id.preportBtn);


        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



generateBimap(v);

            }
        });

    }



}

