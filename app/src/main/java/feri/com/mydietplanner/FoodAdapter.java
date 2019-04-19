package feri.com.mydietplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<FoodModel> foodModels;
    private LayoutInflater layoutInflater;

    public FoodAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public FoodAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.food_item, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.CustomViewHolder customViewHolder, int i) {
        final String namaMakanan=this.foodModels.get(i).getNama();
        final Long hargaMakanan=this.foodModels.get(i).getHarga();
        final CustomViewHolder cvh=customViewHolder;
        Log.d("namaMakanan",this.foodModels.get(i).getFoodKey());
        customViewHolder.nama.setText(namaMakanan);
        customViewHolder.harga.setText(String.valueOf(hargaMakanan));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference PenjualRef = database.getReference("Foods").child(this.foodModels.get(i).getFoodKey()).child("penjual");
        PenjualRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <PenjualMakananModel> penjualMakananModels = new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    PenjualMakananModel penjualMakananModel = dataSnapshot1.getValue(PenjualMakananModel.class);
                    penjualMakananModels.add(penjualMakananModel);
                }
                cvh.penjualMakananAdapter.addItem(penjualMakananModels);
                //Log.d("hmm",GetPenjual(this.foodModels.get(i).getFoodKey()).get(0).getNama());
                cvh.rv_penjual.setAdapter(cvh.penjualMakananAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addItem(ArrayList<FoodModel> mData){
        this.foodModels = mData;
        Log.d("testtt",foodModels.get(0).getNama());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView nama,harga;
        private RecyclerView rv_penjual;
        private PenjualMakananAdapter penjualMakananAdapter;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.nama_makanan);
            harga=(TextView) itemView.findViewById(R.id.harga);
            rv_penjual=(RecyclerView) itemView.findViewById(R.id.rv_penjualmakanan);
            rv_penjual.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            penjualMakananAdapter=new PenjualMakananAdapter(itemView.getContext());
        }
    }
}
