# Solution Steps:

1. Import SharedPreferences class in MainActivity.java:

        import android.content.SharedPreferences;
        
2. Create private variables to reference the SharedPrefences object, the SharedPreferences filename, and a key name for our key/value pair. This can go at the top of the class declaration, right after `public class MainActivity extends AppCompatActivity {` 

        private SharedPreferences mPreferences;
        private String sharedPrefFile = "org.codeintheschools.android.unit3_lesson1_a";
        private final String COLOR_KEY = "COLOR";

3. Inside the method `protected void onCreate(Bundle savedInstanceState) {` add the following:
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

4. Inside the `public void selectColor(View view) {` method, add the following just above the line containing `//Switch based on  which button was pressed`:
        //Created SharedPreferences editor object
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //Write the id of the selected button to our SharedPreferences file
        //this is an int Key/Value pair where the:
        //key = COLOR_KEY = "COLOR"
        //value = view.getID() = "red_button", "blue_button", etc.
        preferencesEditor.putInt(COLOR_KEY, view.getId());

        //Commit the value and save the file.
        preferencesEditor.apply();
        
5. You should now be able to build and run the project again.