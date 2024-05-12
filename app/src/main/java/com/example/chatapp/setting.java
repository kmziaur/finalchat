package com.example.chatapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class setting extends AppCompatActivity {
    ImageView setprofile;
    EditText setname, setstatus;
    Button donebut;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri setImageUri;
    String email,password;
    ProgressDialog progressDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        setprofile = findViewById(R.id.settingprofile);
        setname = findViewById(R.id.settingname);
        setstatus = findViewById(R.id.settingstatus);
        donebut = findViewById(R.id.donebut);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.child("mail").getValue(String.class);
                password = snapshot.child("password").getValue(String.class);
                String name = snapshot.child("userName").getValue(String.class);
                String profile = snapshot.child("profilepic").getValue(String.class);
                String status = snapshot.child("status").getValue(String.class);
//                setname.setText(name);
//                setstatus.setText(status);
//                Picasso.get().load(profile).into(setprofile);

                //edit 25/4/2024
                if (name != null) {
                    setname.setText(name);
                }
                if (status != null) {
                    setstatus.setText(status);
                }
                if (profile != null) {
                    Picasso.get().load(profile).into(setprofile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });

//        donebut.setOnClickListener(view -> {
//            progressDialog.show();
//
//            String name = setname.getText().toString();
//            String Status = setstatus.getText().toString();
//
//            if (setImageUri!=null){
//                storageReference.putFile(setImageUri).addOnCompleteListener(task ->
//                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                            String finalImageUri = uri.toString();
//                            Users users = new Users(auth.getUid(), name,email,password,finalImageUri,Status);
//                            reference.setValue(users).addOnCompleteListener(task1 -> {
//                                if (task1.isSuccessful()){
//                                    progressDialog.dismiss();
//                                    Toast.makeText(setting.this, "Data Is save ", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(setting.this,MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }else {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(setting.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }));
//            }else {
//                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                    String finalImageUri = uri.toString();
//                    Users users = new Users(auth.getUid(), name,email,password,finalImageUri,Status);
//                    reference.setValue(users).addOnCompleteListener(task -> {
//                        if (task.isSuccessful()){
//                            progressDialog.dismiss();
//                            Toast.makeText(setting.this, "Data Is save ", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(setting.this,MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }else {
//                            progressDialog.dismiss();
//                            Toast.makeText(setting.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                });
//            }
//
//        });

        //edited 2024

        donebut.setOnClickListener(view -> {
            progressDialog.show();

            String name = setname.getText().toString();
            String status = setstatus.getText().toString();

            DatabaseReference userRef = reference.child(auth.getUid());
            Map<String, Object> updates = new HashMap<>();
            updates.put("userName", name);
            updates.put("status", status);

            if (setImageUri != null) {
                storageReference.putFile(setImageUri).addOnCompleteListener(task ->
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String finalImageUri = uri.toString();
                            updates.put("profilepic", finalImageUri);

                            userRef.updateChildren(updates).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(setting.this, "Data Is saved", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(setting.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(setting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }));
            } else {
                userRef.updateChildren(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(setting.this, "Data Is saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(setting.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(setting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                setImageUri = data.getData();
                setprofile.setImageURI(setImageUri);
            }
        }


    }
}