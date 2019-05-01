package feri.com.mydietplanner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import feri.com.mydietplanner.Activity.MapsMakananActivity;
import feri.com.mydietplanner.Model.PenjualMakananModel;
import feri.com.mydietplanner.R;

public class PenjualMakananAdapter extends RecyclerView.Adapter<PenjualMakananAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<PenjualMakananModel> penjualMakananModels;
    private LayoutInflater layoutInflater;

    public PenjualMakananAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.penjual_makanan, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {
        final String nama = penjualMakananModels.get(i).getNama();
        final int lat=penjualMakananModels.get(i).getLat();
        final int long_=penjualMakananModels.get(i).getLong();
        String harga = String.valueOf(penjualMakananModels.get(i).getHarga());

        customViewHolder.txt_nama.setText(nama);
        customViewHolder.txt_harga.setText(harga);
        customViewHolder.lihat_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapsMakananActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("long",long_);
                intent.putExtra("nama",nama);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return penjualMakananModels.size();
    }

    public void addItem(ArrayList<PenjualMakananModel> mData){
        this.penjualMakananModels = mData;
        //Log.d("testtt",penjualMakananModels.get(0).getNama());
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private Button lihat_map;
        private TextView txt_nama,txt_harga;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            lihat_map=(Button) itemView.findViewById(R.id.btn_lihat_map);
            txt_nama=(TextView)itemView.findViewById(R.id.nama_penjual);
            txt_harga=(TextView)itemView.findViewById(R.id.harga);
        }
    }
}
