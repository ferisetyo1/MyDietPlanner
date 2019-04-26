package feri.com.mydietplanner.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import feri.com.mydietplanner.Activity.CekBMIandTipsActivity;
import feri.com.mydietplanner.Activity.FoodActivity;
import feri.com.mydietplanner.Activity.LoginActivity;
import feri.com.mydietplanner.R;

public class HomeFragment extends Fragment {
    View v;

    Button cekbmi, foodlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_home, container, false);

        cekbmi=v.findViewById(R.id.cekbmi);
        foodlist=v.findViewById(R.id.foodlist);
        cekbmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cektipsandbmi();
            }
        });

        foodlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodList();
            }
        });

        return v;
    }

    public void cektipsandbmi() {
        startActivity(new Intent(getContext(), CekBMIandTipsActivity.class));
    }

    public void FoodList() {
        startActivity(new Intent(getContext(), FoodActivity.class));
    }

}
