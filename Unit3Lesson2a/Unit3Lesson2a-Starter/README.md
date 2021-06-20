# Steps:

1. First we are going to create a function that will a create a file for us with some sample output.

2. After the method `protected void onCreate(Bundle savedInstanceState)` create a new method:
                
        protected void createFile() throws IOException {
                
        }
3. Within this method add the following code, taking time to understand each section:

        //This creates an empty file in our app's file storage named myfile.txt
        FileOutputStream fOut = openFileOutput("myfile.txt", Context.MODE_PRIVATE);

        //This will be the contents of our file in a moment
        String myStr = "This is a test of file storage";

        //We are getting the Bytes of our string myStr and telling our FileOutputStream to write
        //those bytes to our file
        fOut.write(myStr.getBytes());

        //This ends the connection to the file and closes the connection to it
        fOut.close();

4. Now, add another method:

        protected void readFile() throws IOException {
        }

5. Within this method add the following code, taking time to understand each section:

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

6. Finally, go up to the top and find the `protected void onCreate(Bundle savedInstanceState) {` method, inside of that method add the code below that calls our two methods. Don't worry about the JSONException piece, that is used in the future.

        try {
            //Create a file in local storage
            createFile();
            //Read that local file back
            readFile();
        } catch (IOException) {
             e.printStackTrace();
        }

7. Run the application and monitor the Debug Console for the contents of the file to be shown.

8. Stop the app.

9. Now we are going to read from an asset file. This is a file that is loaded with our application and stored in the assets folder.

10. Within Android Studio, have the students open and review addressbook.json in the `assets` folder of the project. This is the file we will be working with. This format is called [JSON](https://www.json.org/json-en.html) or JavaScript Object Notation. It is generally considered the successor to XML and is very heavily used. 

11. Go back to MainActivity.java and create a new method:

        protected String readAsset(String filename) throws IOException {

        }

12. Within this method add the following code, taking time to understand each section:

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

13. Just after the line that reads `readFile();` in the `protected void onCreate(Bundle savedInstanceState) {` method, add the following line:
        String assetContents = readAsset("addressbook.json");
        Log.d("",assetContents);

14. Again, run the app and take time to make sure the addressbook.json contents are being output to the Debug Console.

15. Create a class variable by adding the following lines after the line that reads `public class MainActivity extends AppCompatActivity {`:
 
        //This ArrayList will hold our key/value pairs for each of our
        //contacts
        ArrayList<HashMap<String, String>> contactList;

16. Within the method `protected void onCreate(Bundle savedInstanceState) {`, find the line that reads `setContentView(R.layout.activity_main);` and add the following below it:

        contactList = new ArrayList<>();


16. Create a new method:
        
        //This function takes in a string containing JSON and adds 
        // each contact to our contactList
        protected void processJSON(String jsonString) throws JSONException {

        }

17. Within this method add the following code, taking time to understand each section:

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

18. Add the following line just below the line that reads: `Log.d("",assetContents);`, it should be around line number 39ish:

        processJSON(assetContents);

19. You will also need to replace this line 
        
        } catch (IOException e) {
    with this: 
        
        } catch (IOException | JSONException e) {

20. At this point we have all of our contacts from the JSON file into an array and we are ready to start displaying them in the text view.

21. Find the function `public void showContact(View view){` and delete this line: `txtVwContactInfo.setText("This is a test");` and add the following, making sure to understand each section of code:
        
        //Get a contact using the random index
        HashMap<String, String> randomContact = contactList.get(index);

        //Create a nice template message
        String contactMessage = "Maybe you should give %s a call. Their home number is %s and their work number is %s.";
        
        //Fill in the template using our randomContact's info
        contactMessage = String.format(contactMessage, randomContact.get("name"), randomContact.get("home"),randomContact.get("work"));
        
        //Set the textView to use our new string
        txtVwContactInfo.setText(contactMessage);

22. You can run the app now and you should see the text updating in the textview with a message using a random contact's info. Because there are only a few samples in the addressbook.json file, you may see the same result back-to-back making things not feel very random. One fix to this would be to save the most recent contact's index and then generate a random index until it is different from the most recent index. Let's try that now...

23. After the line that reads `ArrayList<HashMap<String, String>> contactList;` add a new variable to track the most recent random contact: `int currentContactIndex = 0;`

24. Now, find the line that reads `int index = (int)(Math.random() * contactList.size());` and add the following:

        //Make sure we don't get the same number back-to-back
        while(index == currentContactIndex){
            index = (int)(Math.random() * contactList.size());
        }

25. And finally, we need to save the new currentContactIndex, so below the line that reads `txtVwContactInfo.setText(contactMessage);`, add:

        //Set the currentContactIndex to our random number so we don't get it again
        currentContactIndex = index;

26. Run the app. You should get a new contact everytime you press the button now.