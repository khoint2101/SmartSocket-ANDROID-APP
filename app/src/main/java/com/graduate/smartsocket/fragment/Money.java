package com.graduate.smartsocket.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.graduate.smartsocket.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class Money extends Fragment {
    private View mView;
    private TextView tv_total, elec_price_table;
    private EditText energy, elec_price_lv1, elec_price_lv2, elec_price_lv3, elec_price_lv4, elec_price_lv5,elec_price_lv6;
    private Integer price_lv1, price_lv2, price_lv3, price_lv4, price_lv5, price_lv6;
    private Button btnCalc;
    public String cur_device_Id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.money_fragment, container, false);
        InitUI();
        UpdateElec_Energy_Used();
        InitListener();
        return mView;
    }

    private void InitUI(){
        energy = mView.findViewById(R.id.elec_energy_used);
        elec_price_lv1 = mView.findViewById(R.id.elec_price_lv1);
        elec_price_lv2 = mView.findViewById(R.id.elec_price_lv2);
        elec_price_lv3 = mView.findViewById(R.id.elec_price_lv3);
        elec_price_lv4 = mView.findViewById(R.id.elec_price_lv4);
        elec_price_lv5 = mView.findViewById(R.id.elec_price_lv5);
        elec_price_lv6 = mView.findViewById(R.id.elec_price_lv6);
        elec_price_table = mView.findViewById(R.id.price_table);
        btnCalc = mView.findViewById(R.id.btn_cal);
        tv_total = mView.findViewById(R.id.tv_total);
    }
    private void UpdateElec_Energy_Used(){
        // Read from the database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        //get được current ID -> cur_device
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartsocket-thesis-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference CurrentID = database.getReference("User_Select/" + userID);
        CurrentID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cur_device_Id = snapshot.getValue(String.class);
                DatabaseReference myRefEnergy = database.getReference( cur_device_Id + "/dashboard/energy" );
                myRefEnergy.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Float ener_value = snapshot.getValue(Float.class);
                        energy.setText(ener_value.toString(ener_value));
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

        DatabaseReference price1 = database.getReference("price/lv1");
        DatabaseReference price2 = database.getReference("price/lv2");
        DatabaseReference price3 = database.getReference("price/lv3");
        DatabaseReference price4 = database.getReference("price/lv4");
        DatabaseReference price5 = database.getReference("price/lv5");
        DatabaseReference price6 = database.getReference("price/lv6");

        price1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price_lv1 = snapshot.getValue(Integer.class);
                elec_price_lv1.setText(price_lv1.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        price2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price_lv2 = snapshot.getValue(Integer.class);
                elec_price_lv2.setText(price_lv2.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        price3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price_lv3 = snapshot.getValue(Integer.class);
                elec_price_lv3.setText(price_lv3.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        price4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price_lv4 = snapshot.getValue(Integer.class);
                elec_price_lv4.setText(price_lv4.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        price5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price_lv5 = snapshot.getValue(Integer.class);
                elec_price_lv5.setText(price_lv5.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        price6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                price_lv6 = snapshot.getValue(Integer.class);
                elec_price_lv6.setText(price_lv6.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       // hoàn thiện nốt

    }
    private void InitListener(){
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer lv1 = Integer.parseInt(elec_price_lv1.getText().toString());
                Integer lv2 = Integer.parseInt(elec_price_lv2.getText().toString());
                Integer lv3 = Integer.parseInt(elec_price_lv3.getText().toString());
                Integer lv4 = Integer.parseInt(elec_price_lv4.getText().toString());
                Integer lv5 = Integer.parseInt(elec_price_lv5.getText().toString());
                Integer lv6 = Integer.parseInt(elec_price_lv6.getText().toString());
                Float ee_used = Float.parseFloat(energy.getText().toString());
                Float total = null;
                if (ee_used <= 50) total = ee_used*lv1;
                else if (ee_used > 50 && ee_used <= 100) total = 50*lv1 + (ee_used - 50)*lv2;
                else if (ee_used > 100 && ee_used <= 200) total = 50*lv1 + 50*lv2 + (ee_used - 100)*lv3;
                else if (ee_used > 200 && ee_used <= 300) total = 50*lv1 + 50*lv2 + 100*lv3 + (ee_used - 200)*lv4;
                else if (ee_used > 300 && ee_used <= 400) total = 50*lv1 + 50*lv2 + 100*lv3 + 100*lv4 + (ee_used - 300)*lv5;
                else if (ee_used > 400) total = 50*lv1 + 50*lv2 + 100*lv3 + 100*lv4 + 100*lv5 + (ee_used - 400)*lv6;
                Integer total2 = Math.round(total);
                tv_total.setText(NumberFormat.getInstance(Locale.US).format(total2).toString()+" VND");
            }
        });

        elec_price_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.evn.com.vn/c3/evn-va-khach-hang/Bieu-gia-ban-le-dien-9-79.aspx");
            }
            private void gotoUrl(String s){
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });
    }
}
