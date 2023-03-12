package com.example.cloudnoteassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNote extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editText;
    EditText editText2;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        // add note in firebase
        editText = findViewById(R.id.text_note);
        editText2 = findViewById(R.id.address_note);

        button = findViewById(R.id.add_note);

    }

    public void addNote (View v){
        String note = editText.getText().toString();
        String address = editText2.getText().toString();
        Map<String, Object> notes = new HashMap<>();
        if(!note.isEmpty() && !address.isEmpty()) {

            notes.put("address",address);
            notes.put("body",note);

            db.collection("notes")
                    .add(notes)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e("dana", "Note added successfully ");
                             openSecondSecreen();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("dana", "Failed to add note", e);
                        }
                    });

        }else {
            Toast.makeText(this,"please fill feild" ,Toast.LENGTH_SHORT).show();
        }

    }
    public  void  openSecondSecreen(){
        Intent intent = new Intent(this, ShowNote.class);
        startActivity(intent);
    }
}