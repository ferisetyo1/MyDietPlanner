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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText in_email, in_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        in_email = (EditText) findViewById(R.id.in_email) ;
        in_password = (EditText) findViewById(R.id.in_password);
        btn_login = (Button) findViewById(R.id.btn_login);

    }

    public void login(View view) {
        String email=in_email.getText().toString();
        String password=in_password.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            in_email.setError("Masukan email anda");
            in_password.setError("Masukan password anda");
        }else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthException e) {
                                    Log.e("print error :", e.getErrorCode());
                                    if (e.getErrorCode() == "ERROR_WRONG_PASSWORD") {
                                        in_password.setError("password yang anda masukkan salah");
                                    } else if (e.getErrorCode() == "ERROR_USER_NOT_FOUND") {
                                        in_email.setError("email tidak terdaftar, periksa kembali email anda");
                                    }
                                } catch (FirebaseNetworkException e) {
                                    Log.e("print error :", e.getMessage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }

    public void register(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    public void lupapassword(View view) {

    }
}
