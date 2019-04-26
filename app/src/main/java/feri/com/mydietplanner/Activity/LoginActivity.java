package feri.com.mydietplanner.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Pattern;

import feri.com.mydietplanner.Adapter.PatternEditableBuilder;
import feri.com.mydietplanner.R;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog pg;
    FirebaseAuth mAuth;
    EditText in_email, in_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        in_email = (EditText) findViewById(R.id.in_email);
        in_password = (EditText) findViewById(R.id.in_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        toRegister();
        toForgetPassword();
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

    }

    public void login(View view) {
        String email = in_email.getText().toString();
        String password = in_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            in_email.setError("Email masih kosong");
            in_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            in_password.setError("Password masih kosong");
            in_password.requestFocus();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
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
                    if (exception.equals("ERROR_WRONG_PASSWORD")) {
                        in_password.setError("password yang anda masukkan salah");
                    } else if (exception.equals("ERROR_USER_NOT_FOUND")) {
                        in_email.setError("email tidak terdaftar, periksa kembali email anda");
                    } else {
                        Log.d("error :", exception);
                    }
                }
            });
        }
        pg = new ProgressDialog(LoginActivity.this);
        pg.setMessage("Loading...");
        pg.setTitle("Harap Tunggu");
        pg.setProgressStyle(pg.STYLE_SPINNER);
        pg.show();
        pg.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pg.dismiss();
            }
        }).start();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( pg!=null && pg.isShowing() ){
            pg.cancel();
        }
    }

    private void toRegister(){
        TextView textView = (TextView) findViewById(R.id.txt_register);
        textView.setText("Belum memiliki akun? Register");
// Membuat span dengan tampilan berbeda dan dapat diklik
        new PatternEditableBuilder().
                addPattern(Pattern.compile("Register"), Color.BLUE,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                            }
                        }).into(textView);
    }

    private void toForgetPassword(){
        TextView textView = (TextView) findViewById(R.id.txt_lupapass);
        textView.setText("Lupa Password");
// Membuat span dengan tampilan berbeda dan dapat diklik
        new PatternEditableBuilder().
                addPattern(Pattern.compile("Lupa Password"), Color.BLUE,
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                            }
                        }).into(textView);
    }

}
