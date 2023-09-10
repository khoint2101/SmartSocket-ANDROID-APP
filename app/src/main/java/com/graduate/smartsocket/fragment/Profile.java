package com.graduate.smartsocket.fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.graduate.smartsocket.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.graduate.smartsocket.MainActivity;
import com.graduate.smartsocket.R;

import java.util.Set;

public class Profile extends Fragment {
    private View mView;
    private Uri mUri;
    private ImageView imgAvatar;
    private EditText userName;
    private Button confirm;
    private MainActivity mMainActivity;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mView  = inflater.inflate(R.layout.activity_profile,container,false);
        InitUI();
        mMainActivity = (MainActivity) getActivity();
        SetUserIn4();
        InitListener();
        return mView;
    }
    private void InitUI(){
        imgAvatar = mView.findViewById(R.id.img_avatar);
        userName = mView.findViewById(R.id.username_change);
        confirm = mView.findViewById(R.id.btn_confirm);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
//        dialog.show(); // to show this dialog
//        dialog.dismiss(); // to hide this dialog
    }
    private void SetUserIn4(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        userName.setText(user.getDisplayName());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.no_avatar).into(imgAvatar);
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }
    public void setBitmapImageView(Bitmap bitmapImageView) {
        imgAvatar.setImageBitmap(bitmapImageView);
    }
    private void InitListener(){
//        imgAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickRequestPermission();
//            }
//        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateProfile();
            }
        });
    }

//    private void onClickRequestPermission(){
//        if (mMainActivity == null){
//            return;
//        }
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
//            mMainActivity.OpenGallery();
//            return;
//        }
//        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            mMainActivity.OpenGallery();
//
//        }else {
//            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            getActivity().requestPermissions(permission,MY_REQUEST_CODE );
//        }
//    }

    private void onClickUpdateProfile(){
        String displayName = userName.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        dialog.show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .setPhotoUri(mUri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(),"Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                            mMainActivity.ShowUserInfo();
                            mMainActivity.ChangeFragment(new Dashboard());
                            mMainActivity.mCurrentFragment = 0;
                        }
                    }
                });
    }
}
