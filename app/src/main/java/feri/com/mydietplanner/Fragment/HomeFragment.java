package feri.com.mydietplanner.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import feri.com.mydietplanner.Model.UserModel;
import feri.com.mydietplanner.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    View v;
    TextView txt_hasil,txt_tips,txt_resiko,txt_kategori, txt_member;
    EditText berat,tinggi;
    Button btn_hitungbmi,btn_cekTips;
    LinearLayout layout_tips;
    CircleImageView circleImageView;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef,tipsRef;
    private double bmi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_home, container, false);
        berat=v.findViewById(R.id.et_berat);
        tinggi=v.findViewById(R.id.et_tinggi);
        txt_hasil=v.findViewById(R.id.txt_hasil);
        txt_kategori=v.findViewById(R.id.txt_Kategori);
        txt_resiko=v.findViewById(R.id.txt_Resiko);
        txt_tips=v.findViewById(R.id.txt_tips);
        txt_member=v.findViewById(R.id.txt_member);
        circleImageView=v.findViewById(R.id.user_img);
        btn_hitungbmi=v.findViewById(R.id.btn_hitungBMI);
        btn_cekTips=v.findViewById(R.id.btn_cekTips);
        layout_tips = v.findViewById(R.id.lyt_tips);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("Users").child(mUser.getUid());
        tipsRef = firebaseDatabase.getReference("Tips");
        btn_hitungbmi.setOnClickListener(this);
        btn_cekTips.setOnClickListener(this);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                txt_member.setText("Hello, "+userModel.getNama()+"!");
                Glide.with(getContext()).load(userModel.getImg_url()).into(circleImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_hitungBMI:
                hitungBMI();
                break;
            case R.id.btn_cekTips:
                Log.d("cektipsclik","clicked");
                cekTips();
                break;
        }
    }

    private void cekTips() {
        if (bmi==0){
            Toast.makeText(getContext(),"Hitung BMI terlebih dahulu",Toast.LENGTH_LONG);
            return;
        }
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                final int umur = user.getUmur();

                //cek txt_kategori bmi
                String kategori;
                if (bmi < 18.5) {
                    kategori = "tips1";
                } else if (bmi < 25) {
                    kategori = "tips2";
                } else if (bmi < 30) {
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
                        String kategori = dataSnapshot.child("nama").getValue().toString();
                        String resiko = dataSnapshot.child("resiko").getValue().toString();

                        txt_resiko.setText(resiko);
                        txt_kategori.setText(kategori);

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
                                String Tips=dataSnapshot.child("tips").getValue().toString();
                                txt_tips.setText(Tips);
                                Log.d("tips",Tips);
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
                layout_tips.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("error","error ambil data user");
            }
        });
    }

    private void hitungBMI() {
        String txt_berat=berat.getText().toString();
        String txt_tinggi=tinggi.getText().toString();
        if (TextUtils.isEmpty(txt_berat)) {
            berat.setError("field berat masih kosong");
            berat.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(txt_tinggi)) {
            tinggi.setError("field tinggi masih kosong");
            tinggi.requestFocus();
            return;
        }

        userRef.child("berat").setValue(Integer.parseInt(txt_berat));
        userRef.child("tinggi").setValue(Integer.parseInt(txt_tinggi));

        double _berat=Double.parseDouble(txt_berat);
        double _tinggi=Double.parseDouble(txt_tinggi);

        this.bmi=_berat*10000/(_tinggi*_tinggi);
        txt_hasil.setText(""+this.bmi);
    }
}
