package org.codeintheschools.unit3_lesson2_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //This ArrayList will hold our key/value pairs for each of our
    //contacts
    ArrayList<HashMap<String, String>> contactList;
    int currentContactIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();

        try {
            //Create a file in local storage
            createFile();
            //Read that local file back
            readFile();

            String assetContents = readAsset("addressbook.json");
            processJSON(assetContents);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void showContact(View view){
        TextView txtVwContactInfo = findViewById(R.id.txtVwContactInfo);
        txtVwContactInfo.setText("This is a test");

        // generating a random number index using Math.random()
        int index = (int)(Math.random() * contactList.size());

        //Make sure we don't get the same number back-to-back
        while(index == currentContactIndex){
            index = (int)(Math.random() * contactList.size());
        }

        //Get a contact using the random index
        HashMap<String, String> randomContact = contactList.get(index);

        //Create a nice template message
        String contactMessage = "Maybe you should give %s a call. Their home number is %s and their work number is %s.";

        //Fill in the template using our randomContact's info
        contactMessage = String.format(contactMessage, randomContact.get("name"), randomContact.get("home"),randomContact.get("work"));

        //Set the textView to use our new string
        txtVwContactInfo.setText(contactMessage);

        //Set the currentContactIndex to our random number so we don't get it again
        currentContactIndex = index;
    }

    protected void createFile() throws IOException {
        //This creates an empty file in our app's file storage named myfile.txt
        FileOutputStream fOut = openFileOutput("myfile.txt", Context.MODE_PRIVATE);

        //This will be the contents of our file in a moment
        String myStr = "This is a test of file storage";

        //We are getting the Bytes of our string myStr and telling our FileOutputStream to write
        //those bytes to our file
        fOut.write(myStr.getBytes());

        //This ends the connection to the file and closes the connection to it
        fOut.close();
    }

    protected void readFile() throws IOException {
        //This open a file named "myfile.txt" for input
        FileInputStream fin = openFileInput("myfile.txt");

        //This is a temporary variable to store the contents of the file
        String temp = "";

        //This block of code reads every byte of the file, converts those bytes
        //to characaters and adds them to our temporary string
        int c;
        while( (c = fin.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
        Log.d("","Your file contents were:");
        Log.d("",temp);
        //Close our connection to the file
        fin.close();
    }

    protected String readAsset(String filename) throws IOException {
        //This function reads a file located in the assets folder
        //and returns the string contents of that file.

        //Temporary string to hold our value
        String result = "";

        //Open an input stream, we use getAssets() to access the assets folder
        //it is a special folder provided by Android
        InputStream inputStream = getAssets().open(filename);

        //We get the number of bytes the file is (similar to its size)
        int size = inputStream.available();

        //Create a byte array using the size of the bytes determined before
        byte[] buffer = new byte[size];

        //Read the bytes of the file and copy them into the byte array buffer
        inputStream.read(buffer);

        //Convert that buffer to a String
        result = new String(buffer);

        Log.d("", result );

        //Return the result
        return result;
    }

    protected void processJSON(String jsonString) throws JSONException {
        //This function takes in a string containing JSON and adds
        // each contact to our contactList

        //Gets the top level object of our string
        //and processes the rest of the tree
        JSONObject root = new JSONObject(jsonString);

        //Get the item named "contacts" in the JSON tree and
        //treat it as an array. In JSON, arrays are notated with
        // square brackets []
        JSONArray contacts = root.getJSONArray("contacts");

        //Now loop through all of the contacts
        for (int i = 0; i < contacts.length(); i++) {
            //Objects in JSON are notated with curly braces {}
            //So we retrieve the contact as an object
            JSONObject contact = contacts.getJSONObject(i);

            //Because it is a JSON object, we can get it's properties
            //So here we are getting the name key/value pair
            String name = contact.getString("name");

            //Get phone numbers
            JSONObject contactPhones = contact.getJSONObject("phones");
            String personalPhone = contactPhones.getString("personal");
            String workPhone = contactPhones.getString("work");

            //Create a temporary key/value hash map for a single contact
            HashMap<String, String> newContact = new HashMap<>();
            newContact.put("name", name);
            newContact.put("home", personalPhone);
            newContact.put("work", workPhone);

            // add contact to contact list
            contactList.add(newContact);
        }
    }
}