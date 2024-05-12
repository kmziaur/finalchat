package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    ImageView logo;
    TextView name,own1,own2;
    Animation topAnim,bottomAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //notification 24/4/2024
//        if (getIntent().getExtras() != null) {
//            String userId=getIntent().getExtras().getString("userId");
//            FirebaseUtil.allChatroomCollectionReference().document(userId).get()
//                    .addOnCompleteListener(task -> {
//                        if(task.isSuccessful()){
//                            //copy from useradapter
//                            Intent intent=new Intent(splash.this,MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//
//
//                        }
//
//                    });
//
//        }
     // else {

        logo = findViewById(R.id.logoimg);
        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

      // }
    }
}