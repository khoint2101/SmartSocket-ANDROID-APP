package com.graduate.smartsocket;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText edtEmail, edtPassword1, edtPassword2;
    Button btnSignUp;
    TextView tv_backToSignIn;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
        edtEmail = findViewById(R.id.signup_email);
        edtPassword1 = findViewById(R.id.signup_password1);
        edtPassword2 = findViewById(R.id.signup_password2);
        btnSignUp = findViewById(R.id.signup_button);
        tv_backToSignIn = findViewById(R.id.tv_signin_now);
    }

    public void initListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail() && validatePassword()){
                    Toast.makeText(SignUp.this, "Đăng kí thành công",Toast.LENGTH_SHORT).show();
                    onClickSignUp();
                }
            }
        });

        tv_backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

    public void onClickSignUp(){
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword1.getText().toString().trim();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            // Sign In success, update UI with the signed-in user's infor
                            Intent intent = new Intent(SignUp.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }else {
                            // If signin failed, display a mess to the user
                            Toast.makeText(SignUp.this,"Email đã được đăng kí",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateEmail(){
        String emailInput = edtEmail.getText().toString().trim();
        if (emailInput.isEmpty()){
            Toast.makeText(SignUp.this,"Email không được bỏ trống", Toast.LENGTH_LONG).show();
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(SignUp.this,"Email bạn nhập chưa đúng", Toast.LENGTH_LONG).show();
            return false;
        }else return true;
    }

    private boolean validatePassword(){
        String passwordInput = edtPassword1.getText().toString().trim();
        String confirmpasswordInput = edtPassword2.getText().toString().trim();
        if (passwordInput.isEmpty()){
            Toast.makeText(SignUp.this,"Mật khẩu không được bỏ trống", Toast.LENGTH_LONG).show();
            return false;
        } else if (passwordInput.length() <8){
            Toast.makeText(SignUp.this,"Mật khẩu ít nhất có 8 kí tự", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!passwordInput.equals(confirmpasswordInput)){
            Toast.makeText(SignUp.this,"Mật khẩu xác nhận không đúng", Toast.LENGTH_LONG).show();
            return false;
        }else return true;
    }
}
