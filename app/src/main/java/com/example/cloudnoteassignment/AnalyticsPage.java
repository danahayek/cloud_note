package com.example.cloudnoteassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class AnalyticsPage extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button food ;
    Calendar calender = Calendar.getInstance();
    int hour =calender.get(Calendar.HOUR);
    int min =calender.get(Calendar.MINUTE);
    int sec =calender.get(Calendar.SECOND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_page);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        food = findViewById(R.id.food);
        screenTrack("AnalyticsPage");
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEvent("food@1","food button","Button");
            }
        });
    }
    //register when user click on button
    public void btnEvent (String id , String name , String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID , id);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE , content);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME , name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);



    }
    // register when open screen
    public void screenTrack(String screen){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME , screen);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS , "AnalyticsPage");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,bundle);


    }

    @Override
    protected void onPause() {
        Calendar calender = Calendar.getInstance();
        int hour2 =calender.get(Calendar.HOUR);
        int min2 =calender.get(Calendar.MINUTE);
        int sec2 =calender.get(Calendar.SECOND);

        int h = hour2-hour;
        int m = min2-min;
        int s = sec2-sec;

        HashMap<String , Object> users = new HashMap<>();
        users.put("name","dana");
        users.put("hours",h);
        users.put("minutes",m);
        users.put("seconds",s);
        db.collection("users").add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("dana", "true");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("dana", "false");
                    }
                });
        Log.e("hour",String.valueOf(h));
        Log.e("minute",String.valueOf(m));
        Log.e("second",String.valueOf(s));

        super.onPause();
    }
}