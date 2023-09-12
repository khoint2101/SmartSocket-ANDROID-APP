package com.graduate.smartsocket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduate.smartsocket.MainActivity;
import com.graduate.smartsocket.R;

public class ControlPanel extends Fragment {
    private View mView;
    private MainActivity mActivity;
    private ImageView img_sk1, img_sk2, img_sk3;
    private ToggleButton tg_btn1, tg_btn2, tg_btn3;
    private EditText edt_timer1, edt_timer2, edt_timer3;
    private NumberPicker picker_alert_pwr;
    private String Current_ID = "0";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.control_fragment, container, false);
        InitUI();
//        InitListener();
        RefreshStateSocket();
        return mView;
    }

    private void InitUI(){
        img_sk1 = mView.findViewById(R.id.state_socket1_img);
        img_sk2 = mView.findViewById(R.id.state_socket2_img);
        img_sk3 = mView.findViewById(R.id.state_socket3_img);

        tg_btn1 = mView.findViewById(R.id.one_socket_btn);
        tg_btn2 = mView.findViewById(R.id.two_socket_btn);
        tg_btn3 = mView.findViewById(R.id.three_socket_btn);

        edt_timer1 = mView.findViewById(R.id.timer_picker_1);
        edt_timer2 = mView.findViewById(R.id.timer_picker_2);
        edt_timer3 = mView.findViewById(R.id.timer_picker_3);

        picker_alert_pwr = mView.findViewById(R.id.num_pwr_alarm_picker);
    }

    private void InitListener(){

    }
    private void RefreshStateSocket(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");

        String userUid = user.getUid();
        DatabaseReference CurrentID = database.getReference("User_Select/" + userUid);
        CurrentID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Current_ID = snapshot.getValue(String.class);
                DatabaseReference stt_sk1 = database.getReference(Current_ID + "/control/socket1");
                DatabaseReference stt_sk2 = database.getReference(Current_ID + "/control/socket2");
                DatabaseReference stt_sk3 = database.getReference(Current_ID + "/control/socket3");
                DatabaseReference stt_timer1 = database.getReference(Current_ID + "/control/timer1");
                DatabaseReference stt_timer2 = database.getReference(Current_ID + "/control/timer2");
                DatabaseReference stt_timer3 = database.getReference(Current_ID + "/control/timer3");
                DatabaseReference stt_pwrAlert = database.getReference(Current_ID + "/control/powerAlr");
                stt_sk1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                tg_btn1.setChecked(true);
                                img_sk1.setImageResource(R.drawable.ic_socket_on);
                            }else {
                                tg_btn1.setChecked(false);
                                img_sk1.setImageResource(R.drawable.ic_socket_off);
                            }
                        }else {
                            Toast.makeText(getActivity(),"Không nhận được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                stt_sk2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                tg_btn2.setChecked(true);
                                img_sk2.setImageResource(R.drawable.ic_socket_on);
                            }else {
                                tg_btn2.setChecked(false);
                                img_sk2.setImageResource(R.drawable.ic_socket_off);
                            }
                        }else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                stt_sk3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                tg_btn3.setChecked(true);
                                img_sk3.setImageResource(R.drawable.ic_socket_on);
                            }else {
                                tg_btn3.setChecked(false);
                                img_sk3.setImageResource(R.drawable.ic_socket_off);
                            }
                        }else {

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                stt_timer1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String stt =  snapshot.getValue(String.class);
                            edt_timer1.setText(stt.replaceAll("(.{2})(.{2})(.{2})", "$1:$2:$3"));
                        }else {
                            edt_timer1.setText("null");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                stt_timer2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String stt =  snapshot.getValue(String.class);
                            edt_timer2.setText(stt.replaceAll("(.{2})(.{2})(.{2})", "$1:$2:$3"));
                        }else {
                            edt_timer2.setText("null");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                stt_timer3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String stt =  snapshot.getValue(String.class);
                            edt_timer3.setText(stt.replaceAll("(.{2})(.{2})(.{2})", "$1:$2:$3"));
                        }else {
                            edt_timer3.setText("null");
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(),"Get Data Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                stt_pwrAlert.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            int stt = snapshot.getValue(Integer.class);
                            picker_alert_pwr.setValue(stt);
                        }else {

                        }
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
}
