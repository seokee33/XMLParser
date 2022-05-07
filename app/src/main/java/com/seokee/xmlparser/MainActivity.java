package com.seokee.xmlparser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Hospital> hospitals;
    private HospitalListAdapter diaryAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hospitals = new ArrayList<>();
        GetXml task = new GetXml(this);
        task.execute("https://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=iXusHy2%2FZz7qRhjkwpWBlGWiJaRpGZgroGBlBBow45q4oibueEQnnwMUkAUsOVG3PSlRqQKOYf2SGRxHRJAqoQ%3D%3D&QD=D002&Q0=대구광역시&Q1=북구");
        Log.i("sizeofArr2",String.valueOf(hospitals.size()));
        // 다이어리 리스트
        recyclerView = (RecyclerView)findViewById(R.id.rv_List);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        diaryAdapter = new HospitalListAdapter(hospitals);
        recyclerView.setAdapter(diaryAdapter);

        diaryAdapter.notifyDataSetChanged();


    }


    public void getHosData(ArrayList<Hospital> arrayList)
    {
        Log.i("sizeofArr",String.valueOf(arrayList.size()));
        for(Hospital h : arrayList){
            hospitals.add(new Hospital(h.getDutyName(),h.getDutyTime1c(),h.getDutyAddr()));
        }
        diaryAdapter.notifyDataSetChanged();
    }

    public void MyNotifyDataSetChanged(ArrayList<Hospital> arrayList){

        diaryAdapter = new HospitalListAdapter(arrayList);
        recyclerView.setAdapter(diaryAdapter);

        diaryAdapter.notifyDataSetChanged();
    }



}