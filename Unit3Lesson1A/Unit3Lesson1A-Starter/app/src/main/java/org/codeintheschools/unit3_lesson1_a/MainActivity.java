package org.codeintheschools.unit3_lesson1_a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void selectColor(View view) {
        // Do something in response to button
        Log.d(null, "Button pressed");

        //grab the main activity's id
        View layout = findViewById(R.id.mainView);

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