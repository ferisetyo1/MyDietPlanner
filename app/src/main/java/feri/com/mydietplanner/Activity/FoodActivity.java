package feri.com.mydietplanner.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Adapter.FoodAdapter;
import feri.com.mydietplanner.Model.FoodModel;
import feri.com.mydietplanner.R;

public class FoodActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference foodRef;

    RecyclerView recyclerView;

    FoodAdapter foodAdapter;
    private ArrayList<FoodModel> foodModels = new ArrayList<FoodModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Foods");
        foodAdapter = new FoodAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_foods);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    public void loadData() {
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodModels.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    Log.d("key",key);
                    FoodModel foodModel = dataSnapshot1.getValue(FoodModel.class);
                    foodModel.setFoodKey(key);
                    foodModels.add(foodModel);
                }
                foodAdapter.addItem(foodModels);
                recyclerView.setAdapter(foodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
