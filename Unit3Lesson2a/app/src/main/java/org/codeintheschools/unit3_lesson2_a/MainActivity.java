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

        // generating the index using Math.random()
        int index = (int)(Math.random() * contactList.size());
        while(index == currentContactIndex){
            index = (int)(Math.random() * contactList.size());
        }
        HashMap<String, String> randomContact = contactList.get(index);

        String contactMessage = "Maybe you should give %s a call. Their home number is %s and their work number is %s.";
        contactMessage = String.format(contactMessage, randomContact.get("name"), randomContact.get("home"),randomContact.get("work"));
        txtVwContactInfo.setText(contactMessage);

        currentContactIndex = index;
    }

    protected void createFile() throws IOException {
        FileOutputStream fOut = openFileOutput("filename.txt", Context.MODE_PRIVATE);

        String myStr = "This is a test of file storage";
        fOut.write(myStr.getBytes());
        fOut.close();
    }

    protected void readFile() throws IOException {
        FileInputStream fin = openFileInput("filename.txt");
        int c;
        String temp = "";
        while( (c = fin.read()) != -1){
            temp = temp + Character.toString((char)c);
        }
        fin.close();
    }

    protected String readAsset(String filename) throws IOException {

        //Read a file from Assets
        String string = "";
        InputStream inputStream = getAssets().open(filename);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        string = new String(buffer);
        Log.d("", string );

        return string;
    }

    protected void processJSON(String jsonString) throws JSONException {
        JSONObject reader = new JSONObject(jsonString);

        JSONArray contacts = reader.getJSONArray("contacts");
        //loop through all of the contacts
        for (int i = 0; i < contacts.length(); i++) {
            JSONObject contact = contacts.getJSONObject(i);

            String name = contact.getString("name");

            //Get phone numbers
            JSONObject contactPhones = contact.getJSONObject("phones");
            String personalPhone = contactPhones.getString("personal");
            String workPhone = contactPhones.getString("work");

            // tmp hash map for single contact
            HashMap<String, String> newContact = new HashMap<>();
            newContact.put("name", name);
            newContact.put("home", personalPhone);
            newContact.put("work", workPhone);

            // adding contact to contact list
            contactList.add(newContact);
        }

    }
}