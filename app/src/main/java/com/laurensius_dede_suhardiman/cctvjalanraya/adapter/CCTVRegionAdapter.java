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
import com.laurensius_dede_suhardiman.cctvjalanraya.model.CCTVRegion;

import java.util.List;


public class CCTVRegionAdapter extends RecyclerView.Adapter<CCTVRegionAdapter.holderCCTVRegion> {

    List<CCTVRegion> listCCTVRegion;
    Context context;

    public CCTVRegionAdapter(Context context, List<CCTVRegion> listCCTVRegion){
        this.listCCTVRegion = listCCTVRegion;
        this.context = context;
    }

    @NonNull
    @Override
    public holderCCTVRegion onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cctv_region,viewGroup,false);
        return new holderCCTVRegion(v);
    }

    @Override
    public void onBindViewHolder(holderCCTVRegion holderCCTVRegion,int i){
        holderCCTVRegion.tvNamaCCTVRegion.setText(listCCTVRegion.get(i).getNamaRegion());
    }


    @Override
    public int getItemCount(){
        return listCCTVRegion.size();
    }

    public CCTVRegion getItem(int position){
        return listCCTVRegion.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class holderCCTVRegion extends  RecyclerView.ViewHolder{
        LinearLayout llCCTVRegion;
        TextView tvNamaCCTVRegion;
        holderCCTVRegion(View itemView){
            super(itemView);
            llCCTVRegion = (LinearLayout) itemView.findViewById(R.id.ll_region);
            tvNamaCCTVRegion = (TextView)itemView.findViewById(R.id.tv_nama_region);

        }

    }

}