package feri.com.mydietplanner.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.com.mydietplanner.Adapter.HorizontalFoodAdapter;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.R;

public class FragmentMorefood extends Fragment {
    View v;
    private FirebaseDatabase database;
    DatabaseReference foodRef;
    private RecyclerView vertikalRecycle;
    private String kategori;
    final ArrayList<HorizontalFoodModel> horizontalFoodModels = new ArrayList<>();
    EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_food_gridview, container, false);

        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Foods");
        search=v.findViewById(R.id.et_search);
        vertikalRecycle=v.findViewById(R.id.rv_vertical);
        vertikalRecycle.setHasFixedSize(true);
        vertikalRecycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Bundle bundle=getArguments();
        if (bundle!=null){
            kategori=bundle.getString("kategori");
        }

        loadData();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0){
                    getSearchData(search.getText().toString());
                }else{
                    loadData();
                }
            }
        });

        return v;
    }

    private void getSearchData(final String s) {
        horizontalFoodModels.clear();
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String nama = (String) dataSnapshot1.child("nama").getValue().toString();
                    String _kategori = (String) dataSnapshot1.child("kategori").getValue().toString();
                    if (nama.toLowerCase().contains(s) && _kategori.equalsIgnoreCase(kategori)) {
                        //Log.d("test",dataSnapshot1.getValue().toString());
                        HorizontalFoodModel horizontalFoodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                        horizontalFoodModel.setFoodKey(dataSnapshot1.getKey());
                        horizontalFoodModels.add(horizontalFoodModel);
                    }
                }
                HorizontalFoodAdapter horizontalFoodAdapter = new HorizontalFoodAdapter(getActivity(), horizontalFoodModels);
                horizontalFoodAdapter.notifyDataSetChanged();
                vertikalRecycle.setAdapter(horizontalFoodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadData() {
        horizontalFoodModels.clear();
        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String _kategori = (String) dataSnapshot1.child("kategori").getValue().toString();
                    if (_kategori.equalsIgnoreCase(kategori)) {
                        //Log.d("test",dataSnapshot1.getValue().toString());
                        HorizontalFoodModel horizontalFoodModel = dataSnapshot1.getValue(HorizontalFoodModel.class);
                        horizontalFoodModel.setFoodKey(dataSnapshot1.getKey());
                        horizontalFoodModels.add(horizontalFoodModel);
                    }
                }
                HorizontalFoodAdapter horizontalFoodAdapter = new HorizontalFoodAdapter(getActivity(), horizontalFoodModels);
                horizontalFoodAdapter.notifyDataSetChanged();
                vertikalRecycle.setAdapter(horizontalFoodAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}