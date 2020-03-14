package com.example.posturedetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView Ax,Ay,Az;
    Button button;
    DatabaseReference obj1,obj2;
    List<Double> list1=new ArrayList<>();
    List<Double> list2=new ArrayList<>();
    DatabaseReference database;
    String status1,status2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ax=findViewById(R.id.Ax);
        Ay=findViewById(R.id.Ay);
        Az=findViewById(R.id.Az);
        final Button start=findViewById(R.id.start);
        final Button stop=findViewById(R.id.stop);

        database = FirebaseDatabase.getInstance().getReference("Status");

        database.child("status1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null)
                {
                    status1=dataSnapshot.getValue().toString();

                    if(status1.equals("0"))
                    {
                        start.setEnabled(true);
                        stop.setEnabled(false);
                    }
                    else
                    {
                        start.setEnabled(false);
                        stop.setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        obj1=FirebaseDatabase.getInstance().getReference("Readings");
        obj1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list1.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    for(DataSnapshot snapshot1: snapshot.getChildren()) {
                        list1.add(snapshot1.getValue(Double.class));
                    }
                }
                if(!list1.isEmpty()) {
                    Ax.setText(String.valueOf(list1.get(0)));
                    Ay.setText(String.valueOf(list1.get(1)));
                    Az.setText(String.valueOf(list1.get(2)));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        obj1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list1.clear();
//               for (DataSnapshot snapshot: dataSnapshot.getChildren())
//               {
//                   list1.add(snapshot.getValue(Double.class));
//               }
//               Ax.setText(String.valueOf(list1.get(0)));
//               Ay.setText(String.valueOf(list1.get(1)));
//               Az.setText(String.valueOf(list1.get(2)));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start.setEnabled(false);
                stop.setEnabled(true);

                database.child("status1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null)
                        {
                            database.child("status1").setValue("1");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start.setEnabled(true);
                stop.setEnabled(false);

                database.child("status1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null)
                        {
                            database.child("status1").setValue("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ACal");
                databaseReference.child("Axp").setValue(list1.get(0) + (0.2 * list1.get(0)));
                databaseReference.child("Axn").setValue(list1.get(0) - (0.2 * list1.get(0)));
                databaseReference.child("Ayp").setValue(list1.get(1) + (0.2 * list1.get(1)));
                databaseReference.child("Ayn").setValue(list1.get(1) - (0.2 * list1.get(1)));
                databaseReference.child("Azp").setValue(list1.get(2) + (0.2 * list1.get(2)));
                databaseReference.child("Azn").setValue(list1.get(2) - (0.2 * list1.get(2)));
            }
        });


        findViewById(R.id.graph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Graph.class));
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
            }
        });

    }
}
