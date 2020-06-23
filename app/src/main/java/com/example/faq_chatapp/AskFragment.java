package com.example.faq_chatapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment {
    EditText emailNotify, description;
    Spinner spinner;
    Button btnPost;
    long num=0;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    FirebaseAuth auth;

    public AskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        spinner = view.findViewById(R.id.questionCategory);
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        spinner.setAdapter(adapter);

        auth = FirebaseAuth.getInstance(); //Mendapakan Instance Firebase Autentifikasi


        // title = view.findViewById(R.id.questionTitle);
        description = view.findViewById(R.id.questionDescription);
        emailNotify = view.findViewById(R.id.emailNotif);
        btnPost = view.findViewById(R.id.btnPostNewQuestion);
        retreiveData();
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getUserID = auth.getCurrentUser().getUid();

                //Mendapatkan Instance dari Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference getReference;

                //Menyimpan Data yang diinputkan User kedalam Variable
                final String getCategory = spinner.getSelectedItem().toString();
                final String getTitle = emailNotify.getText().toString();
                final String getDescription = description.getText().toString();

                getReference = database.getReference(); // Mendapatkan Referensi dari Database

                // Mengecek apakah ada data yang kosong
                if(isEmpty(getCategory) && isEmpty(getTitle) && isEmpty(getDescription)){
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(getActivity().getApplicationContext(), "Description field cannot be left empty!", Toast.LENGTH_SHORT).show();

                }else {
                    final String key =  getReference.child("Question").child(getUserID).push().getKey();
                    getReference.child("Question").child(getUserID).push()
                            .setValue(new Question2(getCategory, getTitle, getDescription))
                            .addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    emailNotify.setText("");
                                    description.setText("");
                                    Toast.makeText(getActivity().getApplicationContext(), "Post Berhasil!", Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("nilai",key);
                                    bundle.putString("dataCategory", getCategory);
                                    bundle.putString("dataTitle", getTitle);
                                    bundle.putString("dataDescription", getDescription);
                                    HomeFragment fragmentobj = new HomeFragment();
                                    fragmentobj.setArguments(bundle);
                                    ((MainFeed)getActivity()).ChangeToDescribe();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),"Post Gagal!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
               /* if(description.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Description field cannot be left empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = database.getReference("posts");
                //TEST






                //TEST
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        num = dataSnapshot.getChildrenCount();

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts").child(Long.toString(num));

                        Question q = new Question((int)num, spinner.getSelectedItem().toString(),currentUser.getUid(), description.getText().toString());

                        ref.setValue(q);
                        Toast.makeText(getActivity().getApplicationContext(), "Posted Successfully!", Toast.LENGTH_SHORT).show();
                        ((MainFeed)getActivity()).ChangeToHome();
                    //    title.setText("");
                        description.setText("");
                        emailNotify.setText("");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    public void retreiveData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SpinnerData");
        listener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    spinnerDataList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
