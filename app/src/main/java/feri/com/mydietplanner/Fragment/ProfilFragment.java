package feri.com.mydietplanner.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import feri.com.mydietplanner.Activity.LoginActivity;
import feri.com.mydietplanner.Model.UserModel;
import feri.com.mydietplanner.R;

public class ProfilFragment extends Fragment {
    View v;

    TextView txt_nama, txt_email, txt_umur, txt_berat, txt_tinggi;
    Button btn_logout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference userRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= (View) inflater.inflate(R.layout.fragment_profil,container,false);
        txt_nama = (TextView) v.findViewById(R.id.txt_nama);
        txt_email = (TextView) v.findViewById(R.id.txt_email);
        txt_umur = (TextView) v.findViewById(R.id.txt_umur);
        txt_berat = (TextView) v.findViewById(R.id.txt_berat);
        txt_tinggi = (TextView) v.findViewById(R.id.txt_tinggi);
        btn_logout=v.findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        userRef = database.getReference("Users").child(mUser.getUid());
        loadData();
        return v;
    }

    public void loadData() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel value = dataSnapshot.getValue(UserModel.class);
                txt_nama.setText(value.getNama());
                txt_email.setText(value.getEmail());
                txt_umur.setText(String.valueOf(value.getUmur()));
                txt_berat.setText(String.valueOf(value.getBerat()));
                txt_tinggi.setText(String.valueOf(value.getTinggi()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Failed to read value.", databaseError.getMessage());
            }
        });
    }
    public void logout() {
        mAuth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
