package org.codeintheschools.unit3lesson3a;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.codeintheschools.unit3lesson3a.databinding.AddCustomerBinding;

public class AddCustomerFragment extends Fragment {

    private AddCustomerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = AddCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                //Return to Customer View Fragment
                NavHostFragment.findNavController(AddCustomerFragment.this)
                        .navigate(R.id.action_AddCustomerFragment_to_CustomerViewFragment);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}