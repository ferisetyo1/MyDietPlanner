package feri.com.mydietplanner.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;
import feri.com.mydietplanner.R;


public class DataProfilActivity extends AppCompatActivity {
    CircleImageView img_profil;
    EditText nama, umur, telp;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_profil);

        img_profil = findViewById(R.id.img_avatar);
        nama = findViewById(R.id.txt_nama);
        umur = findViewById(R.id.txt_umur);
        telp = findViewById(R.id.txt_telp);
        btn_submit = findViewById(R.id.btn_submit);

    }
}
