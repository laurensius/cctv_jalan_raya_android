package com.laurensius_dede_suhardiman.cctvjalanraya.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developer.kalert.KAlertDialog;
import com.google.gson.Gson;
import com.laurensius_dede_suhardiman.cctvjalanraya.R;
import com.laurensius_dede_suhardiman.cctvjalanraya.adapter.CCTVRegionAdapter;
import com.laurensius_dede_suhardiman.cctvjalanraya.controller.AppController;
import com.laurensius_dede_suhardiman.cctvjalanraya.model.CCTVRegion;
import com.laurensius_dede_suhardiman.cctvjalanraya.utilities.CustomListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvRegion;
    private CCTVRegionAdapter cctvRegionAdapter;
    private RecyclerView.LayoutManager lmRvRegoion;
    private List<CCTVRegion> cctvRegionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvRegion = findViewById(R.id.rv_cctv_region);
        rvRegion.addOnItemTouchListener(new CustomListener(MainActivity.this, new CustomListener.OnItemClickListener() {
            @Override
            public void onItemClick(View childVew, int childAdapterPosition) {
                Intent intentOut = new Intent(MainActivity.this,VideoActivity.class);
                intentOut.putExtra("idRegion",cctvRegionList.get(childAdapterPosition).getIdRegion());
                startActivity(intentOut);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRegion();
    }

    @Override
    public void onBackPressed() {
        new KAlertDialog(MainActivity.this,KAlertDialog.NORMAL_TYPE)
        .setTitleText("Konfirmasi")
        .setContentText("Keluar dari aplikasi?")
        .setConfirmText("Ya")
        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                kAlertDialog.dismiss();
                finish();
            }
        })
        .setCancelText("Tidak")
        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                kAlertDialog.dismiss();
            }
        }).show();
    }

    void loadRegion(){
        String requestTag = "load_region";
        String url = "https://cctv-jalan-raya.laurensius-dede-suhardiman.com/api/get_region/";
        final KAlertDialog pDialog = new KAlertDialog(MainActivity.this,KAlertDialog.PROGRESS_TYPE);
        pDialog.setCancelable(false);
        pDialog.setTitleText("Loading");
        pDialog.setContentText("Loading list ruas jalan . . .");
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
        url, null,
        new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                Log.d("Debug",response.toString());
                try {
                    int code = response.getInt("code");
                    JSONArray body = response.getJSONArray("body");
                    cctvRegionList = new ArrayList<>();
                    for(int x=0;x<body.length();x++){
                        Gson gson = new Gson();
                        String strObj = String.valueOf(body.getJSONObject(x));
                        CCTVRegion cctvRegion = gson.fromJson(strObj,CCTVRegion.class);
                        cctvRegionList.add(cctvRegion);
                    }

                    rvRegion.setAdapter(null);
                    rvRegion.setHasFixedSize(true);
                    lmRvRegoion = new LinearLayoutManager(MainActivity.this);
                    rvRegion.setLayoutManager(lmRvRegoion);
                    cctvRegionAdapter = new CCTVRegionAdapter(MainActivity.this,cctvRegionList);
                    cctvRegionAdapter.notifyDataSetChanged();
                    rvRegion.setAdapter(cctvRegionAdapter);
                }catch (JSONException ex){
                    new KAlertDialog(MainActivity.this,KAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText(ex.getMessage())
                        .setConfirmText("OK")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismiss();
                            }
                        }).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, requestTag);
    }
}