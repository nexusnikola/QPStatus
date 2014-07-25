package database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
 
    // Contacts table name
    private static final String TABLE_GRADOVI = "gradovi";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_IME = "ime";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_GRADOVI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IME + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADOVI);
        onCreate(db);		
	}

	public void addContact(Gradovi gradovi) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_IME, gradovi.getIme()); 
	 
	    db.insert(TABLE_GRADOVI, null, values);
	    db.close(); 
	}
	 

	public Gradovi getGrad(int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = db.query(TABLE_GRADOVI, new String[] { KEY_ID,
	            KEY_IME}, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Gradovi grad = new Gradovi(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
	    return grad;
	}
	 
	public List<Gradovi> getAllGradovi() {
	    List<Gradovi> gradoviList = new ArrayList<Gradovi>();
	    String selectQuery = "SELECT  * FROM " + TABLE_GRADOVI;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    if (cursor.moveToFirst()) {
	        do {
	        	Gradovi grad = new Gradovi();
	        	grad.setId(Integer.parseInt(cursor.getString(0)));
	        	grad.setIme(cursor.getString(1));
	            
	            gradoviList.add(grad);
	        } while (cursor.moveToNext());
	    }
	 
	    return gradoviList;
	}
	
	public int getGradoviCount() {
	    String selectQuery = "SELECT  * FROM " + TABLE_GRADOVI;
		 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
		return cursor.getCount();
	}

	public int updateContact(Gradovi grad) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put(KEY_IME, grad.getIme());
	 
	    return db.update(TABLE_GRADOVI, values, KEY_ID + " = ?", new String[] { String.valueOf(grad.getId()) });
	}
	 
	public void deleteGrad(Gradovi grad) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_GRADOVI, KEY_ID + " = ?", new String[] { String.valueOf(grad.getId()) });
	    db.close();
	}	
}
