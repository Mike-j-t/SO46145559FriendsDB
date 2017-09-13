package mjt.so46145559friendsdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final String TABLE_NAME = "friends_table";
    public static final String IDCOL = "_id";
    public static final String COL1 = "firstname";
    public static final String COL2 = "lastname";
    public static final String COL3 = "malefemale";
    public static final String COL4 = "age";
    public static final String COL5 = "address";


    public DatabaseHelper (Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + IDCOL+ " TEXT PRIMARY KEY, " +
                COL1 + " TEXT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT, " +
                COL4 + " INTEGER, " +
                COL5 + " TEXT " +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String firstname, String lastname, String gender, int age, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, firstname);
        contentValues.put(COL2, lastname);
        contentValues.put(COL3, gender);
        contentValues.put(COL4, age);
        contentValues.put(COL5, address);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data as inserted inorrectly it will return -1
        if (result == 1)
            return false;
        else {
            return true;
        }
    }

    /**
     * Returns all data from DB
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }

    /**
     * Returns ID that matches the name
     * searches the DB and returns the ID associated with that name
     */
    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +  // SELECT ID FROM DB
                " WHERE " + COL2 + " = '" + name + "'";             // WHERE THE LAST NAME = NAME SELECTED

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Alternative means of getting ID return it as a long rather in a cursor
     */
    public long getID(String firstname) {
        long rv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor csr = db.query(TABLE_NAME,null,COL2 + "=?",new String[]{firstname},null,null,null);
        if (csr.moveToFirst()) {
            rv = csr.getLong(csr.getColumnIndex(IDCOL));
        }
        csr.close();
        return rv;
    }

    /**
     * UPDATES THE NAME
     *
     * UPDATE TABLE > SET LASTNNAME(COL2) = newName = WHERE id = id in Question = AND LASTNAME(COL2) = oldName (was previously) >
     */
    public void updateName (String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";

        //LOGS THE NEW NAME
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName); // NEW NAME CHANGING IT TO
        db.execSQL(query); // EXECUTE QUERY
    }

    /**
     * DELETE FROM DATABASE
     * >>> DELETE FROM TABLE WHERE id = id passed AND name = name passed
     *
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";

        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query); // EXECUTE QUERY
    }
}
