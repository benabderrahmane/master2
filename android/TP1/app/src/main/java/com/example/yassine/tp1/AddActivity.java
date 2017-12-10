package com.example.yassine.tp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {
    private SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //remplir le spinner avec les valeurs à partir de string.xml
        Spinner spinner = (Spinner) findViewById(R.id.typeEvent);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.events_list_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //ajout d'un evenement
        final Button addEvent = (Button) findViewById(R.id.save);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recupération des informations introduites par l'utilisateur
                EditText titre = (EditText) findViewById(R.id.titre);
                Spinner type = (Spinner) findViewById(R.id.typeEvent);
                EditText dateE = (EditText) findViewById(R.id.dateE);
                EditText heureE = (EditText) findViewById(R.id.heureE);
                EditText desc = (EditText) findViewById(R.id.description);

                db = new SQLiteDatabaseHandler(AddActivity.this);
                Evenement evenement = new Evenement(1, titre.getText().toString(), type.getSelectedItem().toString(), dateE.getText().toString(), heureE.getText().toString(), desc.getText().toString());

                db.addEvenement(evenement);

                Intent addAct = new Intent(AddActivity.this, MainActivity.class);
                startActivity(addAct);
            }
        });

        //retour vers l'activité principale
        final Button retournBtn = (Button) findViewById(R.id.retour);
        retournBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retourAct = new Intent(AddActivity.this, MainActivity.class);
                startActivity(retourAct);
            }
        });
    }
}
