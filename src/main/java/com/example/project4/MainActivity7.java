package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity7 extends AppCompatActivity {
RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        layout=findViewById(R.id.relativeLayout);
        PieChart chart=findViewById(R.id.piechart);

        chart.setDrawHoleEnabled(true);
        chart.setUsePercentValues(true);
       chart.setEntryLabelTextSize(12);
        chart.setEntryLabelColor(Color.BLACK);
      chart.setCenterText("result");
        chart.setCenterTextSize(24);
        chart.getDescription().setEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);








        Intent c=getIntent();
        Serializable a= c.getSerializableExtra("data");
        HashMap<String,Object> ab=(HashMap<String,Object>) c.getSerializableExtra("data");

        ArrayList<Object> value=new ArrayList<>(ab.values());
        ArrayList<PieEntry> value1=new ArrayList<>();
        for(int i=0;i<value.size();i++){
             float cs=   Float.valueOf(value.get(i).toString());
String cyon="option".concat(String.valueOf(i+1));
              value1.add(new PieEntry(cs,cyon));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }


        PieDataSet set=new PieDataSet(value1,"POLL RESULT");
        set.setColors(colors);


        PieData aa=new PieData(set);


        chart.setData(aa);

        chart.setUsePercentValues(true);


       chart.animateY(1400, Easing.EaseInOutQuad);




        }
    }
