package com.project.tutoquizzer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;


public class EditPersonalInformationFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_edit_personal_information, container, false);

        init();

        buttonListeners();

        User user = GSONHelper.loadData(getContext());
        setTexts(user);

        return this.rootView;
    }

    private void setTexts(User user){
        fNameET.setText(user.getFirstName());
        lNameET.setText(user.getLastName());
        schoolET.setText(user.getSchool());
    }

    private void buttonListeners(){
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fNameET.getText().toString().equals("") || lNameET.getText().toString().equals("") || schoolET.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Personal information cannot be empty.", Toast.LENGTH_SHORT).show();
                }else {
                    GSONHelper.saveData(getContext(), new User(fNameET.getText().toString(), lNameET.getText().toString(), schoolET.getText().toString()));

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_container, new DashboardFragment());
                    fragmentTransaction.commit();
                }

            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fNameET.setText("");
                lNameET.setText("");
                schoolET.setText("");
            }
        });
    }

    // Components

    private EditText fNameET, lNameET, schoolET;

    private Button updateBtn, clearBtn;

    private void init(){
        fNameET     = rootView.findViewById(R.id.fNameETEditPersonal);
        lNameET     = rootView.findViewById(R.id.lNameETEditPersonal);
        schoolET    = rootView.findViewById(R.id.schoolETEditPersonal);

        updateBtn   = rootView.findViewById(R.id.updatePersonalInfo);
        clearBtn    = rootView.findViewById(R.id.clearPersonalInfo);
    }

}
