package com.graduate.smartsocket;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    private TextView textView, forgotPassword;
    private EditText  edtEmail, edtPassword;
    private Button  btnSignIn;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUI();
        initListener();
    }


    public void initUI(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
//        dialog.show(); // to show this dialog
//        dialog.dismiss(); // to hide this dialog

        textView = findViewById(R.id.tv_signup_now);
        edtEmail = findViewById(R.id.signin_email);
        edtPassword = findViewById(R.id.signin_password);
        btnSignIn = findViewById(R.id.signin_button);
        forgotPassword = findViewById(R.id.forgot_password);
    }

    public void initListener(){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtEmail.getText().toString().isEmpty() && !edtPassword.getText().toString().isEmpty())
                    onClickSignIn();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty()){
                    Toast.makeText(SignIn.this,"Vui lòng nhập Email",Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    dialog.show();
                    String emailAddress = edtEmail.getText().toString().trim();
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignIn.this, "Đã gửi tới Email của bạn", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(SignIn.this,"Kiểm tra lại Email đã nhập", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void onClickSignIn(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        dialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SignIn.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else {
                            Toast.makeText(SignIn.this, "Email hoặc Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
