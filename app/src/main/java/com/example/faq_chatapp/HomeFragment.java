package com.example.faq_chatapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<Question2> questionList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnSearch;
    private ListView mainListView;
    private FirebaseAuth auth;
    private Context context;
    private EditText editText;
    private String desc,tilt,cat;
    private Spinner spinner;
    private SearchableSpinner searchableSpinner;
    IFirebaseLoadDone iFirebaseLoadDone;
    List<Question2> question2s;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    ValueEventListener listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainListView = view.findViewById(R.id.question_listview);
        auth = FirebaseAuth.getInstance();
      //  editText = view.findViewById(R.id.searchfilter);
        btnSearch = view.findViewById(R.id.search_button);
        spinner = view.findViewById(R.id.spinner_search);
        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        spinner.setAdapter(adapter);
 /*       searchableSpinner = view.findViewById(R.id.search_spinner);
        iFirebaseLoadDone = this;
*/      retreiveData();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference dbRef = database.getReference("Question");

  /*      dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Question2> question2s = new ArrayList<>();
                for(DataSnapshot questionSnapshot:dataSnapshot.getChildren()){
                    question2s.add(questionSnapshot.getValue(Question2.class));
                }
                iFirebaseLoadDone.onFirebaseLoadSuccess(question2s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });

*/
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = spinner.getSelectedItem().toString();
                firebaseSearch(searchText);
            }
        });

       // final DatabaseReference dbRef = database.getReference("posts");
        final FirebaseListAdapter<Question2> firebaseListAdapter = new FirebaseListAdapter<Question2>(
                this.getActivity(),
                Question2.class,
                R.layout.question_layout,
                dbRef.child(auth.getUid())
                //dbRef
        ) {
            @Override
            protected void populateView(final View v, final Question2 model, int position) {

                TextView questionTitle = v.findViewById(R.id.question_title);
                TextView questionAuthor = v.findViewById(R.id.question_author);
    //            TextView questionViews = v.findViewById(R.id.views);

                questionAuthor.setText(model.getTitle());
      //          questionViews.setText(String.valueOf(model.getViews()));
                questionTitle.setText(model.getCategory());
                desc = model.getDescription();
                tilt = model.getTitle();
                cat = model.getCategory();
            }
        };

        mainListView.setAdapter(firebaseListAdapter);



        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("dataCategory", cat);
                bundle.putString("dataTitle", tilt);
                bundle.putString("dataDescription", desc);
                //bundle.putString("getPrimaryKey", listQuestion.get(position).getKey());
                Intent intent = new Intent(view.getContext(), ViewQuestion.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
 /*       mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,final View view, int i, long l) {
                //final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("posts").child(String.valueOf(i));
                final String strtext = getArguments().getString("nilai");
                final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Question").child(auth.getUid());
                String key = dbRef.push().getKey();
                dbRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dataCategory", dataSnapshot.child());
                        bundle.putString("dataTitle", listQuestion.get(position).getTitle());
                        bundle.putString("dataDescription", listQuestion.get(position).getDescription());
                        bundle.putString("getPrimaryKey", listQuestion.get(position).getKey());
                        Intent intent = new Intent(view.getContext(), ViewQuestion.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                           Intent viewQuestion = new Intent(getActivity(), ViewQuestion.class);
                           startActivity(viewQuestion);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
*/
        // Inflate the layout for this fragment
        return view;
    }
/*
    @Override
    public void onFirebaseLoadSuccess(List<Question2> question2List){
        question2s = questionList;
        List<String> name_list = new ArrayList<>();
        for(Question2 question2:question2List){
            name_list.add(question2.getCategory());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,name_list);
        searchableSpinner.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message){

    }*/

    private void firebaseSearch(String searchText) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRef = database.getReference("Question");
        final DatabaseReference db = dbRef.child(auth.getUid());
        Query firebaseSearchView = db.orderByChild("category").startAt(searchText).endAt(searchText+ "\uf8ff");
        final FirebaseListAdapter<Question2> firebaseListAdapter = new FirebaseListAdapter<Question2>(
                this.getActivity(),
                Question2.class,
                R.layout.question_layout,
                //dbRef.child(auth.getUid())
                firebaseSearchView
        ) {
            @Override
            protected void populateView(final View v, final Question2 model, int position) {

                TextView questionTitle = v.findViewById(R.id.question_title);
                TextView questionAuthor = v.findViewById(R.id.question_author);
//                TextView questionViews = v.findViewById(R.id.views);

                questionAuthor.setText(model.getTitle());
  //              questionViews.setText(String.valueOf(model.getViews()));
                questionTitle.setText(model.getCategory());
                desc = model.getDescription();
                tilt = model.getTitle();
                cat = model.getCategory();
            }
        };

        mainListView.setAdapter(firebaseListAdapter);
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
