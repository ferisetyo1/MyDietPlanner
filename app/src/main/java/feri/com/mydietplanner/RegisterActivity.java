package feri.com.mydietplanner;

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

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    Button btn_register;
    EditText in_email, in_password, in_repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btn_register = (Button) findViewById(R.id.btn_register);
        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);
        in_repassword = (EditText) findViewById(R.id.in_repassword);

    }

    public void register(View view) {
        String email = in_email.getText().toString();
        String password = in_password.getText().toString();
        String repassword = in_repassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty((password)) || TextUtils.isEmpty(repassword)) {
            if (TextUtils.isEmpty(email)) {
                in_email.setError("Email masih kosong");
            } else if (TextUtils.isEmpty(password)) {
                in_password.setError("Password masih kosong");
            } else if (TextUtils.isEmpty(repassword)) {
                in_repassword.setError("Password masih kosong");
            } else {
                in_email.setError("Email masih kosong");
                in_password.setError("Password masih kosong");
                in_repassword.setError("Password masih kosong");
            }
        }else if (password.length()<6){
            in_password.setError("password harus lebih dari 6 karakter");
        }else if (!password.equals(repassword)) {
            //Log.d("password,repassword",password+","+repassword);
            in_repassword.setError("password tidak sama");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String exception = ((FirebaseAuthException) e).getErrorCode();
                    if (exception.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                        in_email.setText("email telah digunakan");
                    } else {
                        Log.d("error code", exception);
                    }
                }
            });
        }
    }
}
