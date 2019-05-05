package feri.com.mydietplanner.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import feri.com.mydietplanner.Adapter.AdapterWishlist;
import feri.com.mydietplanner.Model.HorizontalFoodModel;
import feri.com.mydietplanner.R;

public class WishlistActivity extends AppCompatActivity {
    RecyclerView rv_wish;
    List<HorizontalFoodModel> listWhist;
    AdapterWishlist AW;
    private Activity context;
    private DatabaseReference dbwish;

    @Override
    protected void onStart() {
        super.onStart();
        dbwish.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot wishsnap : dataSnapshot.getChildren()){
                    HorizontalFoodModel modelwish = wishsnap.getValue(HorizontalFoodModel.class);
//                   modelBarang mod = new modelBarang(modelwish.getLogo(),modelwish.getNama());
//                    listWhist.add(modelwish);
                }
                AW = new AdapterWishlist(WishlistActivity.this,listWhist);
                rv_wish.setAdapter(AW);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorit);
        rv_wish = (RecyclerView) findViewById(R.id.rv_wish);

        dbwish = FirebaseDatabase.getInstance().getReference("wishlist");

    }
}

