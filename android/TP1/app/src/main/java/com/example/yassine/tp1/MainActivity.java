package com.example.yassine.tp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabaseHandler db;

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
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                */
            }
        });

        db = new SQLiteDatabaseHandler(MainActivity.this);



        //affichage des element de la base de donnees
        List<Evenement> evenements = db.allEvenements();
        final ListView list = (ListView) findViewById(R.id.listSearched);
        if (evenements != null) {
            String[] itemsNames = new String[evenements.size()];

            for (int i = 0; i < evenements.size(); i++) {
//                EditText et = new EditText(this);
//                et.setId(evenements.get(i).getId());
                itemsNames[i] = evenements.get(i).toString();

            }

            list.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    itemsNames));
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = list.getItemAtPosition(position);

                //et.setText(list.getCount() + "is ID");
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
