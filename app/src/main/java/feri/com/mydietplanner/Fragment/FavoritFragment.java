package feri.com.mydietplanner.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Activity.MainActivity;
import feri.com.mydietplanner.Adapter.AdapterFavorit;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.R;

public class FavoritFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView recyclerView;
    ImageButton backbuttonbar;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_favorit, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        recyclerView=v.findViewById(R.id.rv_fav);
        backbuttonbar=v.findViewById(R.id.btn_back);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadData();

        backbuttonbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        return v;
    }

    private void loadData() {
        DatabaseReference wishRef = firebaseDatabase.getReference("Favorit");
        final DatabaseReference foodRef = firebaseDatabase.getReference("Foods");

        wishRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> foodkeys=new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String foodkey =(String) dataSnapshot1.getValue();
                    foodkeys.add(foodkey);
                }
                foodRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<HorizontalFoodModel> horizontalFoodModels=new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (foodkeys.contains(dataSnapshot1.getKey())){
                                //Log.d("cek key",dataSnapshot1.getKey());
                                HorizontalFoodModel horizontalFoodModel=dataSnapshot1.getValue(HorizontalFoodModel.class);
                                horizontalFoodModel.setFoodKey(dataSnapshot1.getKey());
                                horizontalFoodModels.add(horizontalFoodModel);
                            }
                        }
                        AdapterFavorit adapterFavorit=new AdapterFavorit(getContext(),horizontalFoodModels);
                        adapterFavorit.notifyDataSetChanged();
                        recyclerView.setAdapter(adapterFavorit);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
