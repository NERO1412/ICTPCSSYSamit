package com.samit1.sysamit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HistoryActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference referenceTicket;

    private String uid;
    private Long now_ms;
    private BarChart chart;

    private ArrayList <BarEntry> entries;
    private ArrayList< String > labels;
    private BarDataSet barDataSet;
    private BarData barData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();

        entries = new ArrayList < > ();
        labels = new ArrayList < > ();

        chart = findViewById(R.id.statistik_chart);
        String myDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()) + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        now_ms = date.getTime();
        referenceTicket = FirebaseDatabase.getInstance().getReference("log");
        Query query_ticket = referenceTicket.orderByChild("created_date").endAt(now_ms);
        query_ticket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                BreakIterator total_pengunjung_today = null;
                total_pengunjung_today.setText(String.valueOf(total_pengunjung));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");

        try {
            String current_date_time = new SimpleDateFormat("2019-01-01", Locale.getDefault()).format(new Date()) + " 08:00:00";
            Date date_current = sdf.parse(current_date_time);
            Date convertedDate = dateFormat.parse(current_date_time);
            Calendar last_day_current_cal = Calendar.getInstance();
            last_day_current_cal.setTime(convertedDate);
            last_day_current_cal.set(Calendar.DAY_OF_MONTH, last_day_current_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last_day_current_string = format_tanggal.format(last_day_current_cal.getTime()) + " 18:00:00";
            Date last_day_current_date = sdf.parse(last_day_current_string);
            final long current_start_ms = date_current.getTime();
            final long current_last_ms = last_day_current_date.getTime();
            Query query_ticket_filter = referenceTicket.orderByChild("created_date").startAt(current_start_ms).endAt(current_last_ms);
            query_ticket_filter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                    int i = 0;
                    AddValuesToEntry(total_pengunjung, i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String current_date_time = new SimpleDateFormat("2019-02-01", Locale.getDefault()).format(new Date()) + " 08:00:00";
            Date date_current = sdf.parse(current_date_time);
            Date convertedDate = dateFormat.parse(current_date_time);
            Calendar last_day_current_cal = Calendar.getInstance();
            last_day_current_cal.setTime(convertedDate);
            last_day_current_cal.set(Calendar.DAY_OF_MONTH, last_day_current_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last_day_current_string = format_tanggal.format(last_day_current_cal.getTime()) + " 18:00:00";
            Date last_day_current_date = sdf.parse(last_day_current_string);
            final long current_start_ms = date_current.getTime();
            final long current_last_ms = last_day_current_date.getTime();
            Query query_ticket_filter = referenceTicket.orderByChild("created_date").startAt(current_start_ms).endAt(current_last_ms);
            query_ticket_filter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                    int i = 1;
                    AddValuesToEntry(total_pengunjung, i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String current_date_time = new SimpleDateFormat("2019-03-01", Locale.getDefault()).format(new Date()) + " 08:00:00";
            Date date_current = sdf.parse(current_date_time);
            Date convertedDate = dateFormat.parse(current_date_time);
            Calendar last_day_current_cal = Calendar.getInstance();
            last_day_current_cal.setTime(convertedDate);
            last_day_current_cal.set(Calendar.DAY_OF_MONTH, last_day_current_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last_day_current_string = format_tanggal.format(last_day_current_cal.getTime()) + " 18:00:00";
            Date last_day_current_date = sdf.parse(last_day_current_string);
            final long current_start_ms = date_current.getTime();
            final long current_last_ms = last_day_current_date.getTime();
            Query query_ticket_filter = referenceTicket.orderByChild("created_date").startAt(current_start_ms).endAt(current_last_ms);
            query_ticket_filter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                    int i = 1;
                    AddValuesToEntry(total_pengunjung, i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String current_date_time = new SimpleDateFormat("2019-04-01", Locale.getDefault()).format(new Date()) + " 08:00:00";
            Date date_current = sdf.parse(current_date_time);
            Date convertedDate = dateFormat.parse(current_date_time);
            Calendar last_day_current_cal = Calendar.getInstance();
            last_day_current_cal.setTime(convertedDate);
            last_day_current_cal.set(Calendar.DAY_OF_MONTH, last_day_current_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last_day_current_string = format_tanggal.format(last_day_current_cal.getTime()) + " 18:00:00";
            Date last_day_current_date = sdf.parse(last_day_current_string);
            final long current_start_ms = date_current.getTime();
            final long current_last_ms = last_day_current_date.getTime();
            Query query_ticket_filter = referenceTicket.orderByChild("created_date").startAt(current_start_ms).endAt(current_last_ms);
            query_ticket_filter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                    int i = 1;
                    AddValuesToEntry(total_pengunjung, i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String current_date_time = new SimpleDateFormat("2019-05-01", Locale.getDefault()).format(new Date()) + " 08:00:00";
            Date date_current = sdf.parse(current_date_time);
            Date convertedDate = dateFormat.parse(current_date_time);
            Calendar last_day_current_cal = Calendar.getInstance();
            last_day_current_cal.setTime(convertedDate);
            last_day_current_cal.set(Calendar.DAY_OF_MONTH, last_day_current_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last_day_current_string = format_tanggal.format(last_day_current_cal.getTime()) + " 18:00:00";
            Date last_day_current_date = sdf.parse(last_day_current_string);
            final long current_start_ms = date_current.getTime();
            final long current_last_ms = last_day_current_date.getTime();
            Query query_ticket_filter = referenceTicket.orderByChild("created_date").startAt(current_start_ms).endAt(current_last_ms);
            query_ticket_filter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                    int i = 1;
                    AddValuesToEntry(total_pengunjung, i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String current_date_time = new SimpleDateFormat("2019-06-01", Locale.getDefault()).format(new Date()) + " 08:00:00";
            Date date_current = sdf.parse(current_date_time);
            Date convertedDate = dateFormat.parse(current_date_time);
            Calendar last_day_current_cal = Calendar.getInstance();
            last_day_current_cal.setTime(convertedDate);
            last_day_current_cal.set(Calendar.DAY_OF_MONTH, last_day_current_cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last_day_current_string = format_tanggal.format(last_day_current_cal.getTime()) + " 18:00:00";
            Date last_day_current_date = sdf.parse(last_day_current_string);
            final long current_start_ms = date_current.getTime();
            final long current_last_ms = last_day_current_date.getTime();
            Query query_ticket_filter = referenceTicket.orderByChild("created_date").startAt(current_start_ms).endAt(current_last_ms);
            query_ticket_filter.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total_pengunjung = (int) dataSnapshot.getChildrenCount();
                    int i = 1;
                    AddValuesToEntry(total_pengunjung, i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void AddValuesToEntry(int val, int x)
    {
        entries.add(new BarEntry(val, x));
        labels.add("insert label for each entry here respectively");
        barDataSet = new BarDataSet(entries, "12 Bulan");
        barData = new BarData((IBarDataSet) labels, barDataSet);
        chart.notifyDataSetChanged();
        chart.setData(barData);
        chart.animateY(3000);
        chart.invalidate();
    }

}