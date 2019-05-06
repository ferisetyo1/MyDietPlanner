package feri.com.mydietplanner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import feri.com.mydietplanner.R;

public class EditProfil extends AppCompatActivity {

    EditText nama, bio, alamat, telp,umur, berat, tinggi;
    Button update;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference userRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);
        nama=findViewById(R.id.et_nama);
        bio=findViewById(R.id.et_bio);
        alamat=findViewById(R.id.et_alamat);
        telp=findViewById(R.id.et_telp);
        umur=findViewById(R.id.et_umur);
        berat=findViewById(R.id.et_berat);
        tinggi=findViewById(R.id.et_tinggi);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userRef=firebaseDatabase.getReference("Users").child(firebaseUser.getUid());
        update=findViewById(R.id.btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        load();
    }

    private void update() {
        userRef.child("nama").setValue(nama.getText().toString());
        userRef.child("bio").setValue(bio.getText().toString());
        userRef.child("alamat").setValue(alamat.getText().toString());
        userRef.child("telp").setValue(telp.getText().toString());
        userRef.child("umur").setValue(Integer.parseInt(umur.getText().toString()));
        userRef.child("berat").setValue(Integer.parseInt(berat.getText().toString()));
        userRef.child("tinggi").setValue(Integer.parseInt(tinggi.getText().toString()));
        startActivity(new Intent(EditProfil.this,MainActivity.class));
        finish();
    }

    private void load() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String _nama=(String) dataSnapshot.child("nama").getValue();
                String _bio= (String) dataSnapshot.child("bio").getValue();
                String _alamat = (String) dataSnapshot.child("alamat").getValue();
                String _telp = (String) dataSnapshot.child("telp").getValue();
                String _umur=dataSnapshot.child("umur").getValue()+"";
                String _berat=dataSnapshot.child("berat").getValue()+"";
                String _tinggi=dataSnapshot.child("tinggi").getValue()+"";

                nama.setText(_nama);
                bio.setText(_bio);
                alamat.setText(_alamat);
                telp.setText(_telp);
                umur.setText(_umur);
                berat.setText(_berat);
                tinggi.setText(_tinggi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
