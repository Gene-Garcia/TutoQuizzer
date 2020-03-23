package com.project.tutoquizzer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.tutoquizzer.dashboardFragments.QuarterFragment;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;


public class EditQuarterFragment extends Fragment {

    private QuarterViewModel qvm;

    private Quarters quarter;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_edit_quarter, container, false);

        this.qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);

        Bundle bundle = getArguments();

        quarter = new Quarters(
                bundle.getString(RouteValues.QUARTER_NAME_KEY));

        quarter.setQuarterId(bundle.getInt(RouteValues.QUARTER_ID_KEY));

        init();

        displayData();
        buttonListener();

        return this.rootView;
    }

    private void displayData() {
        quarterNameET.setText( quarter.getName() );
    }

    private void buttonListener(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quarter.setName( quarterNameET.getText().toString() );
                qvm.update(quarter);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                QuarterFragment quarterFragment = new QuarterFragment();

                fragmentTransaction.replace(R.id.fragment_container, quarterFragment);
                fragmentTransaction.commit();
            }
        });
    }

    // Components

    private EditText quarterNameET;

    private Button saveBtn;

    private void init(){
        quarterNameET = rootView.findViewById(R.id.quarterEditQuarterEditText);

        saveBtn = rootView.findViewById(R.id.saveEditBtnEditQuarter);
    }

}
