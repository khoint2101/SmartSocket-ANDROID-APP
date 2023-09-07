package com.graduate.smartsocket;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.graduate.smartsocket.fragment.ControlPanel;
import com.graduate.smartsocket.fragment.Dashboard;
import com.graduate.smartsocket.fragment.Money;
import com.graduate.smartsocket.fragment.Password;
import com.graduate.smartsocket.fragment.Profile;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int MY_REQUEST_CODE = 10;
    private static final int FRAGMENT_DASHBOARD = 0;
    private static final int FRAGMENT_CONTROLPANEL = 2;
    private static final int FRAGMENT_MONEY = 3;
    private static final int FRAGMENT_PROFILE = 4;
    private static final int FRAGMENT_PASSWORD = 5;

    public int mCurrentFragment = FRAGMENT_DASHBOARD;

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ImageView profileImg;
    private TextView tvName, tvEmail;
    final private Profile mProfile = new Profile();

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if (intent == null){
                            return;
                        }
                        Uri uri = intent.getData();
                        mProfile.setUri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            mProfile.setBitmapImageView(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        ActionBarDrawerToggle toggle  =  new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        ChangeFragment(new Dashboard());
        navigationView.getMenu().findItem(R.id.nav_dash).setChecked(true);
        ShowUserInfo();
    }

    private void initUI(){
        navigationView = findViewById(R.id.navigation_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        profileImg = navigationView.getHeaderView(0).findViewById(R.id.profile_img);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.profile_name);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.profile_email);
    }
    public void resetApp(){
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
        finish();  // đóng Act và quay lại Act gọi nó
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.nav_dash){
            if (mCurrentFragment != FRAGMENT_DASHBOARD){
                ChangeFragment(new Dashboard());
                mCurrentFragment = FRAGMENT_DASHBOARD;
            }
        } else if (id == R.id.nav_ctrl) {
            if (mCurrentFragment != FRAGMENT_CONTROLPANEL){
                ChangeFragment(new ControlPanel());
                mCurrentFragment = FRAGMENT_CONTROLPANEL;
            }
        } else if (id == R.id.nav_calcElec) {
            if (mCurrentFragment != FRAGMENT_MONEY){
                ChangeFragment(new Money());
                mCurrentFragment = FRAGMENT_MONEY;
            }
        } else if (id == R.id.nav_profile) {
            if (mCurrentFragment != FRAGMENT_PROFILE){
                ChangeFragment(new Profile());
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        } else if (id == R.id.nav_password) {
            if (mCurrentFragment != FRAGMENT_PASSWORD){
                ChangeFragment(new Password());
                mCurrentFragment = FRAGMENT_PASSWORD;
            }
        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();
    }

    public void ChangeFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
    public void ShowUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String sName = user.getDisplayName();
        String sEmail = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();
        tvEmail.setText(sEmail);
        if (sName == null) tvName.setText("Unknown Name");
        else {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(sName);
        }
        Glide.with(this).load(photoUrl).error(R.drawable.no_avatar).into(profileImg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                OpenGallery();
            }
        }
    }

    public void OpenGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent,"Chọn ảnh đại diện"));
    }

}