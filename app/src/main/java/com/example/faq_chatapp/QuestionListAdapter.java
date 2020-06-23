package com.example.faq_chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.List;

import androidx.annotation.NonNull;

public class QuestionListAdapter extends ArrayAdapter<Question> {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public QuestionListAdapter(Context context, List<Question> questions) {

        super(context, 0, questions);

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        Question question = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_layout, parent, false);

        }

        // Lookup view for data population
        final TextView authorTextView = convertView.findViewById(R.id.question_author);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                authorTextView.setText(user.getUid());
            }
        };
        TextView titleTextView = convertView.findViewById(R.id.question_title);
       // TextView questionViews = convertView.findViewById(R.id.views);

        // Populate the data into the template view using the data object
        titleTextView.setText(question.getTitle());
       // questionViews.setText(question.getViews());

        // Return the completed view to render on screen

        return convertView;

    }
}
