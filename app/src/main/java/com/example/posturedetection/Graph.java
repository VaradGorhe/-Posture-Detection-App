package com.example.posturedetection;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class Graph extends AppCompatActivity {

    ScatterChart chart;
    List<Entry> dbValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);

        setTitle("LineChartActivityColored");

        chart = findViewById(R.id.chart);

        getDataFromDB();
    }

    private void getDataFromDB() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Readings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Integer> list = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    list.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {

                        list.add(snapshot1.getValue(Integer.class));

                    }
                    dbValues.add(new Entry(list.get(0), list.get(1)));
                    Log.i("DB", list.toString());
                }
                setUpChart(dbValues);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpChart(List<Entry> values) {

        ScatterDataSet dataSet = new ScatterDataSet(values, "DataSet 1");

        dataSet.setColor(Color.RED);
        dataSet.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
        dataSet.setScatterShapeSize(30);
        dataSet.setValueTextSize(2f);

        List<IScatterDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(dataSet);

        ScatterData lineData = new ScatterData(lineDataSets);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXOffset(5f);


        YAxis yl = chart.getAxisLeft();
        yl.setDrawGridLines(true);
        yl.setTextSize(20);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);

        XAxis xl = chart.getXAxis();
        xl.setDrawGridLines(true);
        xl.setTextSize(20);


        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.BLACK);

        chart.getDescription().setEnabled(false);

        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
//        chart.setMaxHighlightDistance(50f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        chart.setMaxVisibleValueCount(200);
        chart.setPinchZoom(true);
    }


}




