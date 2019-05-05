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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Adapter.VerticalFoodAdapter;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.Model.VerticalFoodModel;
import feri.com.mydietplanner.R;

public class FoodFragment extends Fragment {
    View v;
    FirebaseDatabase database;
    DatabaseReference foodRef;
    RecyclerView verticalRecycler;
    VerticalFoodAdapter vFoodAdapter;
    private ArrayList<VerticalFoodModel> vFoodModels = new ArrayList<VerticalFoodModel>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_food, container, false);

        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Foods");

        verticalRecycler = v.findViewById(R.id.rv_vertical);
        verticalRecycler.setHasFixedSize(true);

        verticalRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        loadData();

        return v;
    }

    public void loadData() {
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] _title = {"Breakfast", "Lunch", "Dinner", "Snack"};
                ArrayList<HorizontalFoodModel> breakfast = new ArrayList<>();
                ArrayList<HorizontalFoodModel> lunch = new ArrayList<>();
                ArrayList<HorizontalFoodModel> dinner = new ArrayList<>();
                ArrayList<HorizontalFoodModel> snack = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String title = (String) dataSnapshot1.child("kategori").getValue().toString();
                    String key = dataSnapshot1.getKey();
                    if (title.equalsIgnoreCase("breakfast") && breakfast.size() <= 5) {
                        HorizontalFoodModel foodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                        foodModel.setFoodKey(key);
                        breakfast.add(foodModel);
                    }

                    if (title.equalsIgnoreCase("lunch") && lunch.size() <= 5) {
                        HorizontalFoodModel foodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                        foodModel.setFoodKey(key);
                        lunch.add(foodModel);
                    }

                    if (title.equalsIgnoreCase("dinner") && dinner.size() <= 5) {
                        HorizontalFoodModel foodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                        foodModel.setFoodKey(key);
                        dinner.add(foodModel);
                    }

                    if (title.equalsIgnoreCase("snack") && snack.size() <= 5) {
                        HorizontalFoodModel foodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                        foodModel.setFoodKey(key);
                        snack.add(foodModel);
                    }
                }

                VerticalFoodModel v_breakfast = new VerticalFoodModel(_title[0],breakfast);
                VerticalFoodModel v_lunch = new VerticalFoodModel(_title[1],lunch);
                VerticalFoodModel v_dinner = new VerticalFoodModel(_title[2],dinner);
                VerticalFoodModel v_snack = new VerticalFoodModel(_title[3],snack);

                vFoodModels.add(v_breakfast);
                vFoodModels.add(v_lunch);
                vFoodModels.add(v_dinner);
                vFoodModels.add(v_snack);

                vFoodAdapter = new VerticalFoodAdapter(getContext(), vFoodModels);
                vFoodAdapter.notifyDataSetChanged();
                verticalRecycler.setAdapter(vFoodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    private void setTitle(){
//        String[] title = {"Breakfast", "Lunch", "Dinner", "Snack"};
//
//        for(int i = 0; i < title.length; i++){
//            VerticalFoodModel verticalFoodModel = new VerticalFoodModel();
//            verticalFoodModel.setTitle("Title "+i);
//            ArrayList<HorizontalFoodModel> horizontalFoodModels =  new ArrayList<>();
//
//            for(int j = 0; j < vFoodAdapter.getItemCount(); j++){
//                HorizontalFoodModel horizontalFoodModel = new HorizontalFoodModel();
//                horizontalFoodModels.add(horizontalFoodModel);
//            }
//            verticalFoodModel.setArrayList(horizontalFoodModels);
//            vFoodModels.add(verticalFoodModel);
//        }
//
//        vFoodAdapter.notifyDataSetChanged();
//    }
}
