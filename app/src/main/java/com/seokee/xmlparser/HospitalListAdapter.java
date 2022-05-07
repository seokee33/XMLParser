package com.seokee.xmlparser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.CustomViewHolder> {
    private ArrayList<Hospital> arrayList;

    public HospitalListAdapter(ArrayList<Hospital> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public HospitalListAdapter.CustomViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull HospitalListAdapter.CustomViewHolder holder, int position) {
        holder.tv_Hos_Name.setText(arrayList.get(position).getDutyName());
        holder.tv_Hos_Time.setText(arrayList.get(position).getDutyTime1c());
        holder.tv_Hos_Location.setText(arrayList.get(position).getDutyAddr());
    }

    @Override
    public int getItemCount() {
        //return arrayList.size();
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_Hos_Name;
        protected TextView tv_Hos_Time;
        protected TextView tv_Hos_Location;

        public CustomViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            this.tv_Hos_Name = (TextView)itemView.findViewById(R.id.tv_Hos_Name);
            this.tv_Hos_Time = (TextView)itemView.findViewById(R.id.tv_Hos_Time);
            this.tv_Hos_Location = (TextView)itemView.findViewById(R.id.tv_Hos_Location);
        }
    }
}
