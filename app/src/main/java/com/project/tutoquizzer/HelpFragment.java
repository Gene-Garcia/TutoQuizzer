package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;

public class HelpFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_help, container, false);

        init();
        displayName(GSONHelper.loadData(getContext()));

        return this.rootView;
    }

    private void displayName(User user){
        nameTV.setText("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
    }

    private TextView nameTV;

    private void init(){
        nameTV = rootView.findViewById(R.id.tvNameD);
    }

}
