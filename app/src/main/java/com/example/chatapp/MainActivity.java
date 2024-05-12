package com.example.chatapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainRecyclerView;
    UserAdapter adapter;
    FirebaseDatabase database;
    ArrayList<Users>usersArrayList;
    ImageView imglogout;
    //FirebaseUtil FirebaseUtil;


    ImageView setbut;

    //ImageView cumbut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        // cumbut = findViewById(R.id.cambut);
         setbut = findViewById(R.id.settingbut);



        DatabaseReference reference=database.getReference().child("user");


        usersArrayList=new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                     usersArrayList.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imglogout=findViewById(R.id.logoutimg);
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(MainActivity.this,R.style.dialoge);
                dialog.setContentView(R.layout.dialog_layout);
                Button no,yes;
                yes=dialog.findViewById(R.id.yesbtn);
                no=dialog.findViewById(R.id.nobtn);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       FirebaseAuth.getInstance().signOut();
                       Intent intent=new Intent(MainActivity.this, login.class);
                       startActivity(intent);
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
             }
        });



        mainRecyclerView=findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new UserAdapter(MainActivity.this,usersArrayList);
        mainRecyclerView.setAdapter(adapter);


        setbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
            }
        });


//        cumbut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,10);
//            }
//        });

        //for notification 24/4/2024
        getFCMToken();




        if(auth.getCurrentUser()==null){
            Intent intent=new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        }
        
    }

     void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token=task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);


            }
        });
    }
}