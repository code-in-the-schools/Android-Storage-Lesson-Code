package org.codeintheschools.unit3lesson3a;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.*;

import org.codeintheschools.unit3lesson3a.databinding.AddCustomerBinding;
import org.codeintheschools.unit3lesson3a.models.Customer;

import static android.content.ContentValues.TAG;

public class AddCustomerFragment extends Fragment {

    private AddCustomerBinding binding;

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = AddCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initiates database connection
        mDatabase = FirebaseDatabase.getInstance().getReference("customers");

        //Adds an event listener to the Cancel button
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return to the CustomerViewFragment
                NavHostFragment.findNavController(AddCustomerFragment.this)
                        .navigate(R.id.action_AddCustomerFragment_to_CustomerViewFragment);
            }
        });

        //Adds an event listener to the Save button
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get each of the editText fields so we can reference their value later
                EditText newCustomerName = (EditText)getView().findViewById(R.id.editTextAddCustomerName);
                EditText newCustomerEmail = (EditText)getView().findViewById(R.id.editTextAddCustomerEmailAddress);
                EditText newCustomerPhone = (EditText)getView().findViewById(R.id.editTextAddCustomerPhone);
                EditText newCustomerZip = (EditText)getView().findViewById(R.id.editTextAddCustomerZipcode);
                CheckBox newCustomerSendMarketing = (CheckBox) getView().findViewById(R.id.checkBoxAddSendMarketing);

                try{
                    //Try to create a new customer
                    Customer newCustomer = new Customer(newCustomerName.getText().toString() ,
                            newCustomerEmail.getText().toString(),
                            newCustomerPhone.getText().toString(),
                            Integer.parseInt(newCustomerZip.getText().toString()),
                            newCustomerSendMarketing.isChecked());

                    //Get an automatically generated "key" for the customer
                    String key = mDatabase.push().getKey();

                    //Add the customer to the datbase with the newly generated key
                    mDatabase.child(key).setValue(newCustomer);

                    //Return to Customer View Fragment
                    NavHostFragment.findNavController(AddCustomerFragment.this)
                            .navigate(R.id.action_AddCustomerFragment_to_CustomerViewFragment);

                }catch(Exception e) {
                    //Show an error message at the bottom of the screen.
                    Snackbar.make(view, "Error adding customer. " + e.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}