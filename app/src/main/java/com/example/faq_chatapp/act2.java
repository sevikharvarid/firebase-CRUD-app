package com.example.faq_chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class act2 extends AppCompatActivity {

    private Button btnLogin;
    private EditText text;
    //DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);
        btnLogin = findViewById(R.id.btnLogin);

        final AskFragment askFragment = new AskFragment();
        //text = findViewById(R.id.edt);
      //  databaseReference = FirebaseDatabase.getInstance().getReference("SpinnerData");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(act2.this,MainFeed.class);
                startActivity(i);
            }
        });
        /*btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textdata = text.getText().toString().trim();
                databaseReference.push().setValue(textdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        text.setText("");
                        //askFragment.spinnerDataList.clear();
                      //  askFragment.adapter.notifyDataSetChanged();
                        Toast.makeText(act2.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    */
    }

}