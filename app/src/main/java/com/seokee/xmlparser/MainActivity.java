package com.seokee.xmlparser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // 다이어리 리스트
    ArrayList<Hospital> arrayList;
    private HospitalListAdapter diaryAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetXml task = new GetXml(this);
        task.start();

        // 다이어리 리스트
        recyclerView = (RecyclerView)findViewById(R.id.rv_List);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();

        MyNotifyDataSetChanged();
    }


    public void MyNotifyDataSetChanged(){

        diaryAdapter = new HospitalListAdapter(arrayList);
        recyclerView.setAdapter(diaryAdapter);

        diaryAdapter.notifyDataSetChanged();
    }



}