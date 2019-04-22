package feri.com.mydietplanner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        String nama = penjualMakananModels.get(i).getNama();
        int lat=penjualMakananModels.get(i).getLat();
        int long_=penjualMakananModels.get(i).getLong();

        customViewHolder.txt_nama.setText(nama);
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
        private TextView txt_nama;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            lihat_map=(Button) itemView.findViewById(R.id.btn_lihat_map);
            txt_nama=(TextView)itemView.findViewById(R.id.nama_penjual);
        }
    }
}
