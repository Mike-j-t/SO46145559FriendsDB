package mjt.so46145559friendsdb;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class listdata extends AppCompatActivity {

    ListView mListView;
    TextView firstname, lastname, age, address;
    DatabaseHelper dbhlpr;
    Cursor friendlist;
    SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata);

        mListView = (ListView) findViewById(R.id.friendlist);
        firstname = (TextView) findViewById(R.id.firstnamedisplay);
        lastname = (TextView) findViewById(R.id.lastnamedisplay);
        age = (TextView) findViewById(R.id.agedisplay);
        address = (TextView) findViewById(R.id.addressdisplay);
        dbhlpr = new DatabaseHelper(this);
        friendlist = dbhlpr.getData();
        Log.d("CURSORCOUNT","Rows in Cursor is " + friendlist.getCount());

        // make a list of the columns from which the data is to be extracted
        String[] dbcolums = {
                DatabaseHelper.COL1,
                DatabaseHelper.COL2,
                DatabaseHelper.COL4,
                DatabaseHelper.COL5
        };

        // make a list of the view's id where the data is to be placed
        //Note each column will have a respective view
        int[] listviewids = {R.id.firstnamedisplay, R.id.lastnamedisplay, R.id.agedisplay, R.id.addressdisplay};

        // Setup the Adapter
        sca = new SimpleCursorAdapter(
                this,                           // The context
                R.layout.listdataitem,          // the lasyout for an item
                friendlist,                     // cursor with data
                dbcolums,                       // the list of DB columns to get data from
                listviewids,                    // the views in the layout where to place the data
                0                               // don't worry
        );

        // Finally tie the adapter to the ListView
        mListView.setAdapter(sca);
    }
}
