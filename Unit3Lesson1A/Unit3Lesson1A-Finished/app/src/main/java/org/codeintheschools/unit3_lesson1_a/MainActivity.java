package org.codeintheschools.unit3_lesson1_a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.util.Log;



public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "org.codeintheschools.android.unit3_lesson1_a";
    private final String COLOR_KEY = "COLOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the shared preferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Read initial value
        int initialColor = mPreferences.getInt(COLOR_KEY, 0);

        //grab the main activity's id
        View layout = findViewById(R.id.mainView);
        switch (initialColor) {
            case R.id.red_button:
                layout.setBackgroundColor(Color.parseColor("red"));
                Log.d(null,"Red");
                break;
            case R.id.green_button:
                layout.setBackgroundColor(Color.parseColor("green"));
                break;
            case R.id.blue_button:
                layout.setBackgroundColor(Color.parseColor("blue"));
                break;
        }
    }

    /** Called when the user taps the Send button */
    public void selectColor(View view) {
        // Do something in response to button
        Log.d(null, "Button pressed");

        //grab the main activity's id
        View layout = findViewById(R.id.mainView);

        //Created SharedPreferences editor object
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //Write the id of the selected button to our SharedPreferences file
        //this is an int Key/Value pair where the:
        //key = COLOR_KEY = "COLOR"
        //value = view.getID() = "red_button", "blue_button", etc.
        preferencesEditor.putInt(COLOR_KEY, view.getId());

        //Commit the value and save the file.
        preferencesEditor.apply();


        //Switch based on  which button was pressed
        switch (view.getId()) {
            case R.id.red_button:
                //Set background color of main activity
                layout.setBackgroundColor(Color.parseColor("red"));
                Log.d(null,"Red");
                break;
            case R.id.green_button:
                layout.setBackgroundColor(Color.parseColor("green"));
                break;
            case R.id.blue_button:
                layout.setBackgroundColor(Color.parseColor("blue"));
                break;
        }
    }
}