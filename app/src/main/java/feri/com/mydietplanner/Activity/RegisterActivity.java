package feri.com.mydietplanner.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import feri.com.mydietplanner.R;
import feri.com.mydietplanner.Model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    Button btn_register;
    EditText in_email, in_password, in_repassword, in_nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btn_register = (Button) findViewById(R.id.btn_register);
        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);
        in_repassword = (EditText) findViewById(R.id.in_repassword);
        in_nama = (EditText) findViewById(R.id.in_nama);

    }

    public void register(View view) {
        checkfield();
        final String nama = in_nama.getText().toString();
        final String email = in_email.getText().toString();
        String password = in_password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //get userid
                            String userid = mAuth.getCurrentUser().getUid();
                            Log.d("userid",userid);

                            //instansi ref databse user
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users").child(userid);

                            //buat data model
                            UserModel userModel = new UserModel(nama,email,null,null,null,null,0,0,0,0.0);

                            //insert data ke databse
                            reference.setValue(userModel);

                            startActivity(new Intent(getApplicationContext(), DataProfilActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String exception = ((FirebaseAuthException) e).getErrorCode();
                if (exception.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                    in_email.setError("email telah digunakan");
                } else {
                    Log.d("error code", exception);
                }
            }
        });

    }

    private void checkfield() {
        String email = in_email.getText().toString();
        String password = in_password.getText().toString();
        String repassword = in_repassword.getText().toString();
        String nama = in_nama.getText().toString();
        if(TextUtils.isEmpty(nama)){
            in_nama.setError("Nama masih kosong");
            in_nama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            in_email.setError("Email masih kosong");
            in_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            in_password.setError("Password masih kosong");
            in_password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            in_repassword.setError("Password masih kosong");
            in_repassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            in_password.setError("password harus lebih dari 6 karakter");
            in_password.requestFocus();
            return;
        }
        if (!password.equals(repassword)) {
            in_repassword.setError("password tidak sama");
            in_repassword.requestFocus();
            return;
        }
    }

    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
