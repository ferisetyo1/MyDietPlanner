package feri.com.mydietplanner.Fragment;

import android.content.Intent;
import android.os.Build;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Activity.MainActivity;
import feri.com.mydietplanner.Adapter.HorizontalFoodAdapter;
import feri.com.mydietplanner.Adapter.PenjualMakananAdapter;
import feri.com.mydietplanner.Model.FoodModel;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.Model.PenjualMakananModel;
import feri.com.mydietplanner.R;

public class detail_foodFragment extends Fragment {

    View v;
    FirebaseDatabase database;
    DatabaseReference foodRef,wishRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String foodkey;

    private TextView nama, kalori,deskripsi, karbohidrat, protein, lemak;
    private ImageView food_img;
    private RecyclerView rv_penjual;
    private PenjualMakananAdapter penjualMakananAdapter;
    private TextView kategori;
    private ImageButton back_img,favorite_img,backbuttonbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detailfood, container, false);
        nama =  v.findViewById(R.id.txt_nama_makanan);
        kalori =  v.findViewById(R.id.txt_kalori);
        karbohidrat = v.findViewById(R.id.txt_karbohidrat);
        protein = v.findViewById(R.id.txt_protein);
        lemak = v.findViewById(R.id.txt_lemak);
        kategori =  v.findViewById(R.id.kategori_makanan);
        deskripsi = v.findViewById(R.id.deskripsi_makanan);
        food_img = v.findViewById(R.id.food_image);
        back_img = v.findViewById(R.id.btn_back);
        favorite_img = v.findViewById(R.id.btn_favorite);
        rv_penjual = v.findViewById(R.id.rv_penjualmakanan);
        rv_penjual.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        penjualMakananAdapter = new PenjualMakananAdapter(getContext());
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        foodRef = database.getReference("Foods");
        Bundle bundle = getArguments();
        if (bundle != null) {
            foodkey=bundle.getString("foodkey");
            Log.d("foodkey",foodkey);
        }
        wishRef = database.getReference("Favorit");
        final String id = firebaseUser.getUid();
        backbuttonbar=v.findViewById(R.id.btn_back);
        backbuttonbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });
        favorite_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.hasChild(foodkey)){
                            wishRef.child(id).child(foodkey).setValue(foodkey);
                            foodRef.child(foodkey).child("userid").child(id).setValue(id);
                            favorite_img.setBackgroundResource(R.drawable.heart_isi);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                favorite_img.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                            }
                            Toast.makeText(getActivity(), "Ditambahkan ke favorit",Toast.LENGTH_LONG).show();
                        }else{
                            wishRef.child(id).child(foodkey).removeValue();
                            foodRef.child(foodkey).child("userid").child(id).removeValue();
                            favorite_img.setBackgroundResource(R.drawable.heart_kosong);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                favorite_img.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                            }
                            Toast.makeText(getActivity(), "Dihapus dar favorit",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
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
                karbohidrat.setText(String.valueOf(foodModel.getKarbohidrat()+" g"));
                protein.setText(String.valueOf(foodModel.getProtein()+" g"));
                lemak.setText(String.valueOf(foodModel.getLemak()+" g"));
                kalori.setText(String.valueOf(foodModel.getKalori()+" kcal"));
                kategori.setText(foodModel.getKategori());
                deskripsi.setText(foodModel.getDeskripsi());
                if (dataSnapshot.child("userid").hasChild(firebaseUser.getUid())){
                    favorite_img.setBackgroundResource(R.drawable.heart_isi);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        favorite_img.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    }
                }
                Glide.with(getContext()).load(foodModel.getImg_url()).into(food_img);
                ArrayList<PenjualMakananModel>penjualMakananModels=new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.child("penjual").getChildren()){
                    PenjualMakananModel penjualMakananModel=dataSnapshot1.getValue(PenjualMakananModel.class);
                    //Log.d("nama",penjualMakananModel.getNama());
                    penjualMakananModels.add(penjualMakananModel);
                    Log.d("longlat3",(double)dataSnapshot1.child("lat").getValue()+" "+(double)penjualMakananModel.getLong());
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
