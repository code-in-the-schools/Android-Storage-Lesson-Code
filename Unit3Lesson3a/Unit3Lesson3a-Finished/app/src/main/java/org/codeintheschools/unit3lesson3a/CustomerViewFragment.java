package org.codeintheschools.unit3lesson3a;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.codeintheschools.unit3lesson3a.databinding.CustomerViewBinding;
import org.codeintheschools.unit3lesson3a.models.Customer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CustomerViewFragment extends Fragment {

  private CustomerViewBinding binding;
  private ArrayList<Customer> myCustomers;
  private int currentIndex;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = CustomerViewBinding.inflate(inflater, container, false);

    binding.buttonNext.setVisibility(View.INVISIBLE);
    binding.buttonPrevious.setVisibility(View.INVISIBLE);

    return binding.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    myCustomers = new ArrayList<Customer>();

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("customers");

    ValueEventListener allCustomerQueryEventListener =
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            // Get all the children in the "Customers" tree
            for (DataSnapshot customerSnapShot : snapshot.getChildren()) {
              Customer myCustomer = customerSnapShot.getValue(Customer.class);
              myCustomers.add(myCustomer);
            }

            if (myCustomers.size() > 0) {
              currentIndex = 0;
              loadCustomerIntoView(myCustomers.get(currentIndex));
            }
            if (myCustomers.size() > 1) {
              binding.buttonNext.setVisibility(View.VISIBLE);
            }
          }

          @Override
          public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        };
    mDatabase.addValueEventListener(allCustomerQueryEventListener);

      // Next Button
      binding.buttonNext.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      //Add 1 to the currentIndex
                      currentIndex = currentIndex + 1;

                      //Get the next customer and load them in the view
                      loadCustomerIntoView(myCustomers.get(currentIndex));

                      //If this is the last customer, turn off the NEXT button
                      if (currentIndex == myCustomers.size() - 1) {
                          binding.buttonNext.setVisibility(View.INVISIBLE);
                      }

                      //If there are previous customers, enable the PREVIOUS button
                      if (currentIndex > 0) {
                          binding.buttonPrevious.setVisibility(View.VISIBLE);
                      }
                  }
              });

      // Previous Button
      binding.buttonPrevious.setOnClickListener(
          new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  //Subtract 1 from the currentIndex
                  currentIndex = currentIndex - 1;

                  //Get the previous customer and load them in the view
                  loadCustomerIntoView(myCustomers.get(currentIndex));

                  //If this is the first item in the index, turn off the PREVIOIUS button
                  if (currentIndex == 0) {
                      binding.buttonPrevious.setVisibility(View.INVISIBLE);
                  }

                  //If there are more customers in the index, turn on the NEXT button
                  if (currentIndex < myCustomers.size() - 1) {
                      binding.buttonNext.setVisibility(View.VISIBLE);
                  }
              }
          });
  }

  private void loadCustomerIntoView(Customer myCustomer) {
    EditText newCustomerName = (EditText) getView().findViewById(R.id.editTextTextCustomerName);
    EditText newCustomerEmail = (EditText) getView().findViewById(R.id.editTextCustomerEmail);
    EditText newCustomerPhone = (EditText) getView().findViewById(R.id.editTextCustomerPhone);
    EditText newCustomerZip = (EditText) getView().findViewById(R.id.editTextZipcode);
    CheckBox newCustomerSendMarketing =
        (CheckBox) getView().findViewById(R.id.checkBoxReceiveMarketing);

    newCustomerName.setText(myCustomer.name);
    newCustomerEmail.setText(myCustomer.email);
    newCustomerPhone.setText(myCustomer.phone);
    newCustomerZip.setText(String.valueOf(myCustomer.zipcode));
    newCustomerSendMarketing.setChecked(myCustomer.receiveMarketing);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
