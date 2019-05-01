package feri.com.mydietplanner.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Adapter.PenjualMakananAdapter;
import feri.com.mydietplanner.Model.FoodModel;
import feri.com.mydietplanner.Model.PenjualMakananModel;
import feri.com.mydietplanner.R;

public class detail_foodFragment extends Fragment {

    View v;
    FirebaseDatabase database;
    DatabaseReference foodRef;
    String foodkey;

    private TextView nama, kalori,deskripsi;
    private ImageView food_img;
    private RecyclerView rv_penjual;
    private PenjualMakananAdapter penjualMakananAdapter;
    private TextView kategori;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_detailfood, container, false);
        nama = (TextView) v.findViewById(R.id.nama_makanan);
        kalori = (TextView) v.findViewById(R.id.kalori_makanan);
        kategori = (TextView) v.findViewById(R.id.kategori_makanan);
        deskripsi=(TextView)v.findViewById(R.id.deskripsi_makanan);
        food_img=(ImageView)v.findViewById(R.id.food_image);
        rv_penjual=(RecyclerView)v.findViewById(R.id.rv_penjualmakanan);
        rv_penjual.setLayoutManager(new LinearLayoutManager(getContext()));
        penjualMakananAdapter=new PenjualMakananAdapter(getContext());
        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Foods");
        Bundle bundle = getArguments();
        if (bundle != null) {
            foodkey=bundle.getString("foodkey");
            Log.d("foodkey",foodkey);
        }
        loadData();
        return v;
    }

    private void loadData() {
        //Log.d("foodkey 2",foodkey);
        foodRef.child(foodkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FoodModel foodModel=dataSnapshot.getValue(FoodModel.class);
                //Log.d("text",foodModel.getKategori());
                nama.setText(foodModel.getNama());
                kalori.setText(String.valueOf(foodModel.getKalori()));
                kategori.setText(foodModel.getKategori());
                deskripsi.setText(foodModel.getDeskripsi());
                Glide.with(getContext()).load(foodModel.getImg_url()).into(food_img);
                ArrayList<PenjualMakananModel>penjualMakananModels=new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.child("penjual").getChildren()){
                    PenjualMakananModel penjualMakananModel=dataSnapshot1.getValue(PenjualMakananModel.class);
                    //Log.d("nama",penjualMakananModel.getNama());
                    penjualMakananModels.add(penjualMakananModel);
                }
                penjualMakananAdapter.addItem(penjualMakananModels);
                rv_penjual.setAdapter(penjualMakananAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            foodkey=bundle.getString("foodkey");
//            Log.d("foodkey",foodkey);
//        }
    }
}
