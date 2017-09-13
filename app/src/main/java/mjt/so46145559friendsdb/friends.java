package mjt.so46145559friendsdb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class friends extends AppCompatActivity {
    EditText firstnameinput, lastnameinput, ageinput, addressinput;
    Button addbutton, viewbutton;
    DatabaseHelper dbhlpr;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        context = this;

        firstnameinput = (EditText) findViewById(R.id.firstnameinput);
        lastnameinput = (EditText) findViewById(R.id.lastnameinput);
        ageinput = (EditText) findViewById(R.id.ageinput);
        addressinput = (EditText) findViewById(R.id.addressinput);
        addbutton = (Button) findViewById(R.id.addbutton);
        viewbutton = (Button) findViewById(R.id.viewbutton);

        dbhlpr = new DatabaseHelper(this);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean addOK = true;
                int age_as_int = -1;

                String firstName = firstnameinput.getText().toString();
                String lastName = lastnameinput.getText().toString();
                String age = ageinput.getText().toString();
                String address = addressinput.getText().toString();

                if (firstName.length() < 1) {
                    toastMessage("You must enter something in this field!");
                    firstnameinput.requestFocus();
                    addOK = false;
                }
                if (lastName.length() < 1) {
                    toastMessage("You must enter something in this field!");
                    lastnameinput.requestFocus();
                    addOK = false;
                }
                if (age.length() < 1) {
                    toastMessage("You must enter something in this field!");
                    ageinput.requestFocus();
                    addOK = false;
                }
                if (address.length() < 1) {
                    toastMessage("You must enter something in this field!");
                    addressinput.requestFocus();
                    addOK = false;
                }
                try {
                    age_as_int = Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    toastMessage("You must enter a valid Number in this field!");
                    ageinput.requestFocus();
                    addOK = false;
                }

                if (addOK) {
                    dbhlpr.addData(firstName,lastName,"????",age_as_int,address);
                    toastMessage("Friend Added!");
                }

            }
        });
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,listdata.class);
                startActivity(intent);
            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
