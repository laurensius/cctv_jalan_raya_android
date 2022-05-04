package com.laurensius_dede_suhardiman.cctvjalanraya.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laurensius_dede_suhardiman.cctvjalanraya.R;
import com.laurensius_dede_suhardiman.cctvjalanraya.model.CCTVPoint;

import java.util.List;


public class CCTVPointAdapter extends RecyclerView.Adapter<CCTVPointAdapter.holderCCTVPoint> {

    List<CCTVPoint> listCCTVPoint;
    Context context;

    public CCTVPointAdapter(Context context, List<CCTVPoint> listCCTVPoint){
        this.listCCTVPoint = listCCTVPoint;
        this.context = context;
    }

    @NonNull
    @Override
    public holderCCTVPoint onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cctv_point,viewGroup,false);
        return new holderCCTVPoint(v);
    }

    @Override
    public void onBindViewHolder(holderCCTVPoint holderCCTVPoint,int i){
        holderCCTVPoint.tvNamaCCTVPoint.setText(listCCTVPoint.get(i).getNamaPoint());
    }


    @Override
    public int getItemCount(){
        return listCCTVPoint.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class holderCCTVPoint extends  RecyclerView.ViewHolder{
        LinearLayout llCCTVPoint;
        TextView tvNamaCCTVPoint;
        holderCCTVPoint(View itemView){
            super(itemView);
            llCCTVPoint = itemView.findViewById(R.id.ll_point);
            tvNamaCCTVPoint = itemView.findViewById(R.id.tv_nama_point);

        }

    }

}