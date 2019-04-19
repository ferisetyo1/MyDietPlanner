package feri.com.mydietplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference userRef;

    TextView txt_nama, txt_email, txt_umur, txt_berat, txt_tinggi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_umur = (TextView) findViewById(R.id.txt_umur);
        txt_berat = (TextView) findViewById(R.id.txt_berat);
        txt_tinggi = (TextView) findViewById(R.id.txt_tinggi);

        if (mUser != null) {
            userRef = database.getReference("Users").child(mUser.getUid());
            Log.d("uid=", mUser.getUid());
            loadData();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
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

    public void logout(View view) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void cektipsandbmi(View view) {
        startActivity(new Intent(getApplicationContext(),CekBMIandTips.class));
    }

    public void FoodList(View view) {
        startActivity(new Intent(getApplicationContext(),FoodActivity.class));
    }
}
