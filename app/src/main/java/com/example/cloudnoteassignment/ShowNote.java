package com.example.cloudnoteassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorBoundsInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cloudnoteassignment.adapter.noteAdapter;
import com.example.cloudnoteassignment.model.note;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowNote extends AppCompatActivity implements noteAdapter.ItemClickListener ,noteAdapter.EditClickListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    noteAdapter nadapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    ArrayList<note> items;
    ImageView delete;

    EditText add;
    EditText body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        recyclerView = findViewById(R.id.recyler);
        items = new ArrayList<note>();
        nadapter = new noteAdapter(items, this, this,this);
        recyclerView.setAdapter(nadapter);
        GetNotes();

        delete = findViewById(R.id.delete);

        add=findViewById(R.id.address_edit);
        body=findViewById(R.id.body_edit);


    }

    /////////////////////////////get note/////////////////////////////////////////////

    private void GetNotes() {

        db.collection("notes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String address = documentSnapshot.getString("address");
                                    String body = documentSnapshot.getString("body");


                                    note user = new note(id, address, body);
                                    items.add(user);

                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(nadapter);
                                    ;
                                    nadapter.notifyDataSetChanged();
                                    Log.e("get", items.toString());

                                }
                            }
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("get", "get failed ");


                    }
                });
    }

    ///////////////////////////////////delete//////////////////////////////////////////
    public void deleteNote(final note nott) {
        db.collection("notes").document(nott.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          public void onSuccess(Void aVoid) {
                                              items.remove(nott);
                                              nadapter.notifyDataSetChanged();
                                          }
                                      }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("delete", "failed with delete");

                    }
                });

    }
    /////////////////////////////edit/////////////////////////////////////////////////
    public void updateNote(final note n) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Your Note");
        final View customLayout = getLayoutInflater().inflate(R.layout.edit_item, null);
        builder.setView(customLayout);
        builder.setPositiveButton(
                "Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        add = customLayout.findViewById(R.id.update_address);
                        body = customLayout.findViewById(R.id.update_body);


                        db.collection("notes").document(n.getId()).update("Address", add.getText().toString() ,body.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("edit", "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("edit", "Error updating document", e);
                                    }
                                });
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    ////////////////////////////option menu///////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addddd:
                Intent intent = new Intent(ShowNote.this, CreateNote.class);
                startActivity(intent);
                return true;

        }
        return true;
    }


    @Override
    public void onItemClick(int position, String id) {

        deleteNote(items.get(position));
    }

    @Override
    public void onItemClick2(int position, String id) {
        updateNote(items.get(position));
    }
}