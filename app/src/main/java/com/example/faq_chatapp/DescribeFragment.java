package com.example.faq_chatapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DescribeFragment extends AppCompatActivity {
    private Button button1,button2;
    TextView textView1,textView2,textView3;
    private String cat,title,desc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_describe);
       final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cat =  bundle.getString("dataCategory");
            title = bundle.getString("dataTitle");
            desc = bundle.getString("dataDescription");

            //textView1.setText(bundle.getString("dataCategory"));
            //textView2.setText(bundle.getString("dataTitle"));
            //textView3.setText(bundle.getString("dataDescription"));
        }
        //final String getCategory = getIntent().getExtras().getString("dataCategory");
        //final String getTitle = getIntent().getExtras().getString("dataTitle");
        //final String getDesc = getIntent().getExtras().getString("dataDescription");
        button1 = findViewById(R.id.toQuestion);
        button2 = findViewById(R.id.toWebsite);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1= new Bundle();
                bundle1.putString("cat",cat );
                bundle1.putString("tlt",title );
                bundle1.putString("desc",desc );
                Intent intent = new Intent(view.getContext(), MainFeed.class);
                intent.putExtras(bundle1);
                startActivity(intent);
               // ((MainFeed)getActivity()).ChangeToDescribe();
            }
        });

    }
}
