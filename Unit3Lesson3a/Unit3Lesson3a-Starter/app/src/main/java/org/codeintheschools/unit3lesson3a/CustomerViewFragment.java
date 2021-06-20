package org.codeintheschools.unit3lesson3a;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.codeintheschools.unit3lesson3a.databinding.CustomerViewBinding;

public class CustomerViewFragment extends Fragment {

  private CustomerViewBinding binding;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = CustomerViewBinding.inflate(inflater, container, false);

    //Set the initial view of the previous and next buttons to invisible
    binding.buttonNext.setVisibility(View.INVISIBLE);
    binding.buttonPrevious.setVisibility(View.INVISIBLE);

    return binding.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
