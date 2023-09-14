package com.graduate.smartsocket.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
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

import java.util.Calendar;

public class ControlPanel extends Fragment {
    private View mView;
    private MainActivity mActivity;
    private ImageView img_sk1, img_sk2, img_sk3;
    private ToggleButton tg_btn1, tg_btn2, tg_btn3;
    private SwitchCompat sw_timer1, sw_timer2, sw_timer3, sw_alert;
    private EditText edt_timer1, edt_timer2, edt_timer3;
    private EditText edt_alert_pwr;
    private String Current_ID = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.control_fragment, container, false);
        InitUI();
        InitListener();
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

        sw_timer1 = mView.findViewById(R.id.enable_timer1);
        sw_timer2 = mView.findViewById(R.id.enable_timer2);
        sw_timer3 = mView.findViewById(R.id.enable_timer3);

        edt_timer1 = mView.findViewById(R.id.timer_picker_1);
        edt_timer2 = mView.findViewById(R.id.timer_picker_2);
        edt_timer3 = mView.findViewById(R.id.timer_picker_3);

        sw_alert = mView.findViewById(R.id.enable_alert_pwr);
        edt_alert_pwr = mView.findViewById(R.id.num_pwr_alarm_input);
    }

    private void InitListener(){  // xử lý các sự kiện nhấn và chọn

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");
        tg_btn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_sk1 = database.getReference(Current_ID + "/control/socket1");
                    set_sk1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_sk1.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_sk1 = database.getReference(Current_ID + "/control/socket1");
                    set_sk1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_sk1.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
        tg_btn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_sk2 = database.getReference(Current_ID + "/control/socket2");
                    set_sk2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_sk2.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_sk2 = database.getReference(Current_ID + "/control/socket2");
                    set_sk2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_sk2.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        tg_btn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_sk3 = database.getReference(Current_ID + "/control/socket3");
                    set_sk3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_sk3.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_sk3 = database.getReference(Current_ID + "/control/socket3");
                    set_sk3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_sk3.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        sw_timer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_stt_timer1 = database.getReference(Current_ID + "/control/stt_timer1");
                    set_stt_timer1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_timer1.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_stt_timer1 = database.getReference(Current_ID + "/control/stt_timer1");
                    set_stt_timer1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_timer1.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        sw_timer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_stt_timer2 = database.getReference(Current_ID + "/control/stt_timer2");
                    set_stt_timer2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_timer2.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_stt_timer2 = database.getReference(Current_ID + "/control/stt_timer2");
                    set_stt_timer2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_timer2.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        sw_timer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_stt_timer3 = database.getReference(Current_ID + "/control/stt_timer3");
                    set_stt_timer3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_timer3.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_stt_timer3 = database.getReference(Current_ID + "/control/stt_timer3");
                    set_stt_timer3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_timer3.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        sw_alert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseReference set_stt_alert = database.getReference(Current_ID + "/control/stt_powerAlr");
                    set_stt_alert.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_alert.setValue(1);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    DatabaseReference set_stt_alert = database.getReference(Current_ID + "/control/stt_powerAlr");
                    set_stt_alert.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                set_stt_alert.setValue(0);
                            }else {
                                Toast.makeText(getActivity(),"Không gửi được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        edt_timer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(edt_timer1);
            }
        });
        edt_timer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(edt_timer2);
            }
        });
        edt_timer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(edt_timer3);
            }
        });
        edt_alert_pwr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = edt_alert_pwr.getText().toString();
                    if (!text.isEmpty()) {
                        int value = Integer.parseInt(text);
                        if (value >= 10 && value <= 2200) {
                            // Giá trị hợp lệ, bạn có thể gửi nó lên Firebase ở đây.
                            DatabaseReference cur_alert = database.getReference(Current_ID + "/control/powerAlr");
                            cur_alert.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        cur_alert.setValue(value);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            // Hiển thị thông báo cho người dùng rằng giá trị không hợp lệ.
                            Toast.makeText(getActivity(), "Giá trị không hợp lệ. Giá trị phải từ 10 đến 2200.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });


    }
    private void showTimePickerDialog(EditText x){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String selectedTime = String.format("%02d:%02d", hour,minute);
                x.setText(selectedTime);
                if (x == edt_timer1){
                    DatabaseReference pick_time1 = database.getReference(Current_ID + "/control/timer1");
                    pick_time1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                pick_time1.setValue(String.format("%02d%02d", hour,minute));
                            }else {
                                x.setText("null");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if (x == edt_timer2){
                    DatabaseReference pick_time2 = database.getReference(Current_ID + "/control/timer2");
                    pick_time2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                pick_time2.setValue(String.format("%02d%02d", hour,minute));
                            }else {
                                x.setText("null");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if (x == edt_timer3){
                    DatabaseReference pick_time3 = database.getReference(Current_ID + "/control/timer3");
                    pick_time3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                pick_time3.setValue(String.format("%02d%02d", hour,minute));
                            }else {
                                x.setText("null");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        },
                hour,
                minute,
                true);
        timePickerDialog.show();
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
                DatabaseReference timer1 = database.getReference(Current_ID + "/control/timer1");
                DatabaseReference timer2 = database.getReference(Current_ID + "/control/timer2");
                DatabaseReference timer3 = database.getReference(Current_ID + "/control/timer3");
                DatabaseReference stt_timer1 = database.getReference(Current_ID + "/control/stt_timer1");
                DatabaseReference stt_timer2 = database.getReference(Current_ID + "/control/stt_timer2");
                DatabaseReference stt_timer3 = database.getReference(Current_ID + "/control/stt_timer3");
                DatabaseReference stt_pwrAlert = database.getReference(Current_ID + "/control/stt_powerAlr");
                DatabaseReference pwrAlert = database.getReference(Current_ID + "/control/powerAlr");

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
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                sw_timer1.setChecked(true);
                            }else {
                                sw_timer1.setChecked(false);
                            }
                        }else {

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
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                sw_timer2.setChecked(true);
                            }else {
                                sw_timer2.setChecked(false);
                            }
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
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                sw_timer3.setChecked(true);
                            }else {
                                sw_timer3.setChecked(false);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                timer1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String stt =  snapshot.getValue(String.class);
                            edt_timer1.setText(stt.replaceAll("(.{2})(.{2})", "$1:$2"));
                        }else {
                            edt_timer1.setText("null");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                timer2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String stt =  snapshot.getValue(String.class);
                            edt_timer2.setText(stt.replaceAll("(.{2})(.{2})", "$1:$2"));
                        }else {
                            edt_timer2.setText("null");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                timer3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String stt =  snapshot.getValue(String.class);
                            edt_timer3.setText(stt.replaceAll("(.{2})(.{2})", "$1:$2"));
                        }else {
                            edt_timer3.setText("null");
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                       // Toast.makeText(getActivity(),"Get Data Failed",Toast.LENGTH_SHORT).show();
                    }
                });
                stt_pwrAlert.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer stt = snapshot.getValue(Integer.class);
                            if (stt == 1){
                                sw_alert.setChecked(true);
                            }else {
                                sw_alert.setChecked(false);
                            }
                        }else {
                            Toast.makeText(getActivity(),"Không nhận được dữ liệu. Kiểm tra lại ID thiết bị",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                pwrAlert.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer stt =  snapshot.getValue(Integer.class);
                            edt_alert_pwr.setText(stt.toString());
                        }else {
                            edt_alert_pwr.setText("0");
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
