package com.example.yassine.tp1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private SQLiteDatabaseHandler db;

    private EditText fromDateEtxt;
    //private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    //private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        findViewsById();
        setDateTimeField();

        String s = getIntent().getStringExtra("EVENT_ID");
        if (s == null) {
            initAll(-1, false);
            addEvent();
        }
        else {
            initAll(Integer.parseInt(s), true);
            fillToUpdate(Integer.parseInt(s));
        }




    }

    public void initAll(final int id, boolean check) {
        //remplir le spinner avec les valeurs à partir de string.xml
        Spinner spinner = (Spinner) findViewById(R.id.typeEvent);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.events_list_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //retour vers l'activité principale
        final Button retournBtn = (Button) findViewById(R.id.retour);
        retournBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retourAct = new Intent(AddActivity.this, MainActivity.class);
                startActivity(retourAct);
            }
        });

        if (check == true) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.parie_btn);
            Button del = new Button(this);
            del.setText("Supprimer");
            ll.addView(del);
            del.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View view) {
                    db = new SQLiteDatabaseHandler(AddActivity.this);
                    db.deleteById(id);

                    Context context = getApplicationContext();
                    CharSequence text = "L'evenement à été supprimer !";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent addAct = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(addAct);
                }
            });
        }
    }
    public void addEvent() {
        //ajout d'un evenement
        final Button addEvent = (Button) findViewById(R.id.save);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                //recupération des informations introduites par l'utilisateur
                EditText titre = (EditText) findViewById(R.id.titre);
                Spinner type = (Spinner) findViewById(R.id.typeEvent);
                EditText dateE = (EditText) findViewById(R.id.etxt_fromdate);
                EditText heureE = (EditText) findViewById(R.id.heureE);
                EditText desc = (EditText) findViewById(R.id.description);

                db = new SQLiteDatabaseHandler(AddActivity.this);
                Evenement evenement = new Evenement(1, titre.getText().toString(), type.getSelectedItem().toString(), dateE.getText().toString(), heureE.getText().toString(), desc.getText().toString());

                db.addEvenement(evenement);

                Intent addAct = new Intent(AddActivity.this, MainActivity.class);
                startActivity(addAct);
            }
        });
    }

    public void fillToUpdate(int id) {
        db = new SQLiteDatabaseHandler(AddActivity.this);
        Evenement event = db.getEvenement(id);

        //recupération des informations introduites par l'utilisateur
        EditText titre = (EditText) findViewById(R.id.titre);
        titre.setText(event.getTitreE());
        Spinner type = (Spinner) findViewById(R.id.typeEvent);
        selectSpinnerItemByValue(type, event.getTypeE());
        EditText dateE = (EditText) findViewById(R.id.etxt_fromdate);
        dateE.setText(event.getDateE());
        EditText heureE = (EditText) findViewById(R.id.heureE);
        heureE.setText(event.getHeureE());
        EditText desc = (EditText) findViewById(R.id.description);
        desc.setText(event.getDescE());
    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spnr.getAdapter();
        for (int position = 0; position < adapter.getCount(); position++) {
            if(adapter.getItem(position).equals(value)) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(AddActivity.this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }
}
