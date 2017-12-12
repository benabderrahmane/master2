package com.example.yassine.tp1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabaseHandler db;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAct = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addAct);
            }
        });

        db = new SQLiteDatabaseHandler(MainActivity.this);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayoutMain);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



        //affichage des element de la base de donnees
        List<Evenement> evenements = db.allEvenements();

        if (evenements != null) {

            for (int i = 0; i < evenements.size(); i++) {

                final TextView et = new TextView(this);
                et.setLayoutParams(p);

                et.setTextColor(R.color.textColor);
                et.setTextSize(17);

                et.setId(evenements.get(i).getId());
                et.setText(evenements.get(i).toString());

                et.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent addAct = new Intent(MainActivity.this, AddActivity.class);
                        addAct.putExtra("EVENT_ID", Integer.toString(et.getId()));
                        startActivity(addAct);
                    }
                });

                View v = new View(this);
                v.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        5
                ));
                v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                ll.addView(v);
                ll.addView(et);
            }
        }

    }

}
