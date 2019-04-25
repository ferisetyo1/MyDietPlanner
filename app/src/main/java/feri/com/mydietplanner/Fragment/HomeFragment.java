package feri.com.mydietplanner.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference userRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= (View) inflater.inflate(R.layout.fragment_home,container,false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        return v;
    }

    public void cektipsandbmi(View view) {
        startActivity(new Intent(getContext(), CekBMIandTipsActivity.class));
    }

    public void FoodList(View view) {
        startActivity(new Intent(getContext(), FoodActivity.class));
    }

    public void logout(View view) {
        mAuth.signOut();
        startActivity(new Intent(getContext(), LoginActivity.class));

    }
}
