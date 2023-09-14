package com.graduate.smartsocket.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduate.smartsocket.R;

public class Dashboard extends Fragment {
    private View mView;
    private FloatingActionButton btnEnter, btnDelete, btnAlertLog;
    private TextView voltage, current, power, frequency, energy, powerFactor;
    public TextView yourDeviceID, wifiInfo;
    private Float F_voltage, F_current, F_power, F_freq, F_pf, F_energy;
    public String deviceID = "0", cur_deviceId="null", S_wifiInfo;
    private Boolean state = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.dash_fragment, container, false);
        InitUI();
        InitListener();
        ReadDataFromDatabase();
        return mView;
    }

    private void InitUI() {  // gan bien voi id
        btnEnter = mView.findViewById(R.id.btn_id_enter);
        btnDelete = mView.findViewById(R.id.btn_delete);
        btnAlertLog = mView.findViewById(R.id.btn_alert_statistics);
        yourDeviceID = mView.findViewById(R.id.your_device_id);
        wifiInfo = mView.findViewById(R.id.wifi_infor_dash);
        voltage = mView.findViewById(R.id.voltage_dash);
        current = mView.findViewById(R.id.current_dash);
        power = mView.findViewById(R.id.power_dash);
        frequency = mView.findViewById(R.id.freq_dash);
        energy = mView.findViewById(R.id.energy_dash);
        powerFactor = mView.findViewById(R.id.pf_dash);
    }

    private void InitListener() {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenEnterDialog(Gravity.CENTER);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {   // nut xoa thong so dien
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference del_state = database.getReference(cur_deviceId + "/control/del_state");
                del_state.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            del_state.setValue(10);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        btnAlertLog.setOnClickListener(new View.OnClickListener() {    // nut truy cap sheet bao cao
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void ReadDataFromDatabase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");


        DatabaseReference currentID = database.getReference("User_Select/" + userID);
        currentID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cur_deviceId = snapshot.getValue(String.class);
                yourDeviceID.setText(cur_deviceId);

                // tham so
                DatabaseReference volt = database.getReference(cur_deviceId + "/dashboard/voltage");
                DatabaseReference curr = database.getReference(cur_deviceId+ "/dashboard/current");
                DatabaseReference pow = database.getReference(cur_deviceId + "/dashboard/power");
                DatabaseReference ener = database.getReference(cur_deviceId + "/dashboard/energy");
                DatabaseReference freq = database.getReference(cur_deviceId + "/dashboard/frequency");
                DatabaseReference powF = database.getReference(cur_deviceId + "/dashboard/pf");
                DatabaseReference WiFiIn4 = database.getReference(cur_deviceId + "/dashboard/wifiinfo");

                // xu ly
                volt.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot snapshot) {
                        F_voltage = snapshot.getValue(Float.class);
                        voltage.setText(String.valueOf(F_voltage).replace('.',',')+ " V");
                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                });
                curr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        F_current = snapshot.getValue(Float.class);
                        current.setText(String.valueOf(F_current).replace('.',',')+" A");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                pow.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        F_power = snapshot.getValue(Float.class);
                        power.setText(String.valueOf(F_power).replace('.',',')+ " W");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                ener.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        F_energy = snapshot.getValue(Float.class);
                        energy.setText(String.valueOf(F_energy).replace('.',',')+ " kWh");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                freq.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        F_freq = snapshot.getValue(Float.class);
                        frequency.setText(String.valueOf(F_freq).replace('.',',') + " Hz");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                powF.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        F_pf = snapshot.getValue(Float.class);
                        powerFactor.setText(String.valueOf(F_pf).replace('.',','));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                WiFiIn4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        S_wifiInfo = snapshot.getValue(String.class);
                        wifiInfo.setText(S_wifiInfo);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private void OpenEnterDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_id_dialog);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        EditText edtID = dialog.findViewById(R.id.edit_id_dialog);
        Button btnCancel = dialog.findViewById(R.id.btn_id_dialog_cancel);
        Button btnDongY = dialog.findViewById(R.id.btn_id_dialog_ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtID.getText().toString().isEmpty()){
                    deviceID = edtID.getText().toString().trim();
                }
                UpdateIDtoDatabase();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void UpdateIDtoDatabase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("User_Select/"+ userID);
        myRef.setValue(deviceID);
        yourDeviceID.setText(deviceID);
        ReadDataFromDatabase();
    }
}

