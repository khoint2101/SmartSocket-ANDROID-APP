package com.graduate.smartsocket.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.graduate.smartsocket.MainActivity;
import com.graduate.smartsocket.R;

public class Password extends Fragment {
    private View mView;
    private EditText passwordConfirm, passwordConfirm2;
    private Button btnPassChange;
    private MainActivity mMainActivity;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.password_fragment,container,false);
        InitUI();
        mMainActivity = (MainActivity) getActivity();
        InitListener();
        return mView;
    }

    private void InitListener(){
        btnPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidatePassword()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    dialog.show();
                    String newPassword = passwordConfirm.getText().toString().trim();
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Đã đổi mật khẩu. Vui lòng đăng nhập lại",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(getActivity(),"Thất bại. Vui lòng đăng nhập lại để xác thực",Toast.LENGTH_SHORT).show();
                                    }
                                    FirebaseAuth.getInstance().signOut();
                                    mMainActivity.resetApp();
                                }
                            });
                }
            }
        });
    }

    private boolean ValidatePassword(){
        String passwordInput = passwordConfirm.getText().toString().trim();
        String ConfirmPasswordInput = passwordConfirm2.getText().toString().trim();
        if (passwordInput.isEmpty()){
            Toast.makeText(getActivity(),"Mật khẩu không được bỏ trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if (passwordInput.length()<8){
            Toast.makeText(getActivity(),"Mật khẩu có ít nhất 8 kí tự", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!passwordInput.equals(ConfirmPasswordInput)){
            Toast.makeText(getActivity(),"Mật khẩu xác nhận không đúng",Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }
    private void InitUI(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
//        dialog.show(); // to show this dialog
//        dialog.dismiss(); // to hide this dialog

        btnPassChange = mView.findViewById(R.id.btn_password_confirm);
        passwordConfirm = mView.findViewById(R.id.password_confirm);
        passwordConfirm2 = mView.findViewById(R.id.password_confirm2);
    }


}
