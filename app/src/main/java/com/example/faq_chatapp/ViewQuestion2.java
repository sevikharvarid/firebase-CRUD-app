package com.example.faq_chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewQuestion2 extends AppCompatActivity {

    TextView title, description, category;
    EditText replyText;
    Button replyButton, changeReplyButton;
    int editingAnswerID,editingQuestionID,ans;
    private Answer editingAnswer;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question2);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            category.setText(bundle.getString("catol"));
            title.setText(bundle.getString("tltii"));
            description.setText(bundle.getString("descol"));
            Toast.makeText(ViewQuestion2.this,"Data Tampil",Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(ViewQuestion2.this,"Data Hilang",Toast.LENGTH_SHORT).show();
        }

       }
}
