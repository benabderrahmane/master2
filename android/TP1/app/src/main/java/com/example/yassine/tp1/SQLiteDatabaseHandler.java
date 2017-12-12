package com.example.yassine.tp1; /**
 * Created by yassine on 08/12/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yassine.tp1.Evenement;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "evenementDB";
    private static final String TABLE_NAME = "evenement";
    private static final String KEY_ID = "id";
    private static final String KEY_TITRE = "titreE";
    private static final String KEY_TYPE = "typeE";
    private static final String KEY_DATE = "dateE";
    private static final String KEY_HEURE = "heureE";
    private static final String KEY_DESC = "descE";
    private static final String[] COLUMNS = {KEY_ID, KEY_TITRE, KEY_TYPE, KEY_DATE, KEY_HEURE, KEY_DESC};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITRE + " VARCHAR2(50), "
                + KEY_TYPE + " VARCHAR2(50), "
                + KEY_DATE + " VARCHAR2(50), "
                + KEY_HEURE + " VARCHAR2(50), "
                + KEY_DESC + " TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Evenement evenement) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(evenement.getId()) });
        db.close();
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public Evenement getEvenement(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //query(table_name, column names, selections, selections args, group by, having, order by, limit)
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Evenement evenement = new Evenement();
        evenement.setId(Integer.parseInt(cursor.getString(0)));
        evenement.setTitreE(cursor.getString(1));
        evenement.setTypeE(cursor.getString(2));
        evenement.setDateE(cursor.getString(3));
        evenement.setHeureE(cursor.getString(4));
        evenement.setDescE(cursor.getString(5));

        return evenement;
    }

    public List<Evenement> allEvenements() {
        List<Evenement> evenements = new LinkedList<Evenement>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Evenement evenement = null;

        if (cursor.moveToFirst()) {
            do {
                evenement = new Evenement();
                evenement.setId(Integer.parseInt(cursor.getString(0)));
                evenement.setTitreE(cursor.getString(1));
                evenement.setTypeE(cursor.getString(2));
                evenement.setDateE(cursor.getString(3));
                evenement.setHeureE(cursor.getString(4));
                evenement.setDescE(cursor.getString(5));
                evenements.add(evenement);
            } while (cursor.moveToNext());
        }
        return evenements;
    }

    public List<Evenement> getEventByTypeDate(String DateE) {
        List<Evenement> evenements = new LinkedList<Evenement>();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + KEY_DATE + " = '" + DateE +
                "' ORDER BY " + KEY_DATE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Evenement evenement = null;

        if (cursor.moveToFirst()) {
            do {
                evenement = new Evenement();
                evenement.setId(Integer.parseInt(cursor.getString(0)));
                evenement.setTitreE(cursor.getString(1));
                evenement.setTypeE(cursor.getString(2));
                evenement.setDateE(cursor.getString(3));
                evenement.setHeureE(cursor.getString(4));
                evenement.setDescE(cursor.getString(5));
                evenements.add(evenement);
            } while (cursor.moveToNext());
        }
        return evenements;
    }

    public void addEvenement(Evenement evenement) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITRE, evenement.getTitreE());
        values.put(KEY_TYPE, evenement.getTypeE());
        values.put(KEY_DATE, evenement.getDateE());
        values.put(KEY_HEURE, evenement.getHeureE());
        values.put(KEY_DESC, evenement.getDescE());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int updateEvenement(Evenement evenement) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITRE, evenement.getTitreE());
        values.put(KEY_TYPE, evenement.getTypeE());
        values.put(KEY_DATE, evenement.getDateE());
        values.put(KEY_HEURE, evenement.getHeureE());
        values.put(KEY_DESC, evenement.getDescE());

        int i = db.update(TABLE_NAME, values, "id = ?", new String[] { String.valueOf(evenement.getId()) });
        db.close();

        return i;
    }
}
