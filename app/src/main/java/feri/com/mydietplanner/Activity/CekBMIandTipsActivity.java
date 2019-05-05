package feri.com.mydietplanner.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import feri.com.mydietplanner.Model.UserModel;
import feri.com.mydietplanner.R;

public class CekBMIandTipsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef,tipsRef;
    TextView tips, txt_resiko, bmi_result, txt_kategori;
    Button showtips;
    int umur=0, berat=0, tinggi=0;
    double _bmi_result=0;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_bmiand_tips);
        tips = findViewById(R.id.tips);
        txt_kategori = findViewById(R.id.kategori);
        txt_resiko = findViewById(R.id.resiko);
        bmi_result = findViewById(R.id.bmi_result);
        showtips=findViewById(R.id.btn_showtips);
        dialog = new Dialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("Users").child(mUser.getUid());
        tipsRef = firebaseDatabase.getReference("Tips");
        loadData();
    }

    public void loadData() {
        //ambil data user
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                umur = user.getUmur();
                berat = user.getBerat();
                tinggi = user.getTinggi();

                Log.d("data user ", tinggi+" "+umur+" "+berat);

                //hitung bmi
                double tinggikuadrat = (double)tinggi * (double)tinggi/10000;
                Log.d("teks",tinggikuadrat+"");
                if (tinggi!=0&&berat!=0){
                    _bmi_result = (double)berat / (double)tinggikuadrat;
                }

                //set bmi
                bmi_result.setText(String.valueOf(_bmi_result));

                //cek txt_kategori bmi
                String kategori;
                if (_bmi_result < 18.5) {
                    kategori = "tips1";
                } else if (_bmi_result < 25) {
                    kategori = "tips2";
                } else if (_bmi_result < 30) {
                    kategori = "tips3";
                } else {
                    kategori = "tips4";
                }
                Log.d("kategori",kategori);

                //ambil data txt_kategori dan txt_resiko
                DatabaseReference kategoriref = tipsRef.child(kategori);
                kategoriref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String kategori = dataSnapshot.child("alamat").getValue().toString();
                        String resiko = dataSnapshot.child("resiko").getValue().toString();
                        txt_kategori.setText(kategori);
                        txt_resiko.setText(resiko);

                        int key_umur=0;
                        if(umur<20){
                            key_umur=10;
                        }else if(umur<30){
                            key_umur=20;
                        }else if(umur<40){
                            key_umur=30;
                        }else{
                            key_umur=40;
                        }

                        DatabaseReference _tipsRef=tipsRef.child(dataSnapshot.getKey()).child("umur")
                                .child(String.valueOf(key_umur));

                        _tipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String Tips=dataSnapshot.child("tips").getValue().toString();
                                showtips.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showPopup(Tips);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("error","error ambil data kategori");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error","error ambil data user");
            }
        });
    }

    public void showPopup(String s) {
        dialog.setContentView(R.layout.popup_tips);
        TextView tips = (TextView) dialog.findViewById(R.id.tips);
        tips.setText(s);
        Button close_btn = (Button) dialog.findViewById(R.id.close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
