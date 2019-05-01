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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Adapter.FoodAdapter;
import feri.com.mydietplanner.Adapter.HorizontalFoodAdapter;
import feri.com.mydietplanner.Adapter.VerticalFoodAdapter;
import feri.com.mydietplanner.Model.FoodModel;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.Model.VerticalFoodModel;
import feri.com.mydietplanner.R;

public class FoodFragment extends Fragment {
    View v;
    FirebaseDatabase database;
    DatabaseReference foodRef;
    RecyclerView verticalRecycler;
    VerticalFoodAdapter vFoodAdapter;
    HorizontalFoodAdapter hFoodAdapter;
    private ArrayList<VerticalFoodModel> vFoodModels = new ArrayList<VerticalFoodModel>();
    private ArrayList<HorizontalFoodModel> hFoodModels = new ArrayList<HorizontalFoodModel>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_food, container, false);

        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Foods");
//        foodAdapter = new FoodAdapter(getContext());

        verticalRecycler = v.findViewById(R.id.rv_vertical);
        verticalRecycler.setHasFixedSize(true);

        verticalRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        vFoodAdapter = new VerticalFoodAdapter(getContext(), vFoodModels);
        hFoodAdapter = new HorizontalFoodAdapter(getContext(), hFoodModels);
        setTitle();
        loadData();

        return v;
    }

    public void loadData() {
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hFoodModels.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    Log.d("key",key);
                    HorizontalFoodModel foodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                    foodModel.setFoodKey(key);
                    hFoodModels.add(foodModel);
                }
                hFoodAdapter.addItem(hFoodModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setTitle(){
        String[] title = {"Breakfast", "Lunch", "Dinner", "Snack"};

        for(int i = 0; i < title.length; i++){
            VerticalFoodModel verticalFoodModel = new VerticalFoodModel();
            verticalFoodModel.setTitle("Title "+i);
            ArrayList<HorizontalFoodModel> horizontalFoodModels =  new ArrayList<>();

            for(int j = 0; j < vFoodAdapter.getItemCount(); j++){
                HorizontalFoodModel horizontalFoodModel = new HorizontalFoodModel();
                horizontalFoodModels.add(horizontalFoodModel);
            }
            verticalFoodModel.setArrayList(horizontalFoodModels);
            vFoodModels.add(verticalFoodModel);
        }

        vFoodAdapter.notifyDataSetChanged();
    }
}
