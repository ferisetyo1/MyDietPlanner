package feri.com.mydietplanner.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import feri.com.mydietplanner.Activity.LoginActivity;
import feri.com.mydietplanner.R;

public class ResetPasswordActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText in_resetpassword;
    Button btn_resetpassword;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        in_resetpassword = (EditText) findViewById(R.id.in_resetpassword);
        btn_resetpassword = (Button) findViewById(R.id.btn_resetpassword);
        dialog = new Dialog(this);
    }

    public void resetPassword(View view) {
        mAuth.sendPasswordResetEmail(in_resetpassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showPopup();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String exception = ((FirebaseAuthException) e).getErrorCode();
                Log.d("error", exception);
            }
        });
    }

    public void showPopup() {
        dialog.setContentView(R.layout.popup_emailterkirim);
        TextView txt_emailtekirim = (TextView) dialog.findViewById(R.id.txt_emailTerkirim);
        Button btn_backLogin = (Button) dialog.findViewById(R.id.btn_backLogin);
        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
