package com.project.tutoquizzer.dashboardFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.project.tutoquizzer.EditQuarterFragment;
import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.view.adapters.QuarterAdapter;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;

import java.util.List;

public class QuarterFragment extends Fragment {

    private QuarterViewModel qvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_quarter, container, false);

        qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);

        init();

        displayPersonalDetails(GSONHelper.loadData(getContext()));

        initRecyclerView();

        return this.rootView;
    }

    private void displayPersonalDetails(User user){
        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        schoolTV.setText(user.getSchool());
    }

    private void adapterListener(QuarterAdapter adapter){
        adapter.setOnItemClickListener(new QuarterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quarters quarters) {

                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.QUARTER_ID_KEY, quarters.getQuarterId());
                bundle.putString    (RouteValues.QUARTER_NAME_KEY, quarters.getName());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditQuarterFragment editQuarterFragment = new EditQuarterFragment();
                editQuarterFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editQuarterFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final QuarterAdapter quarterAdapter = new QuarterAdapter();
        recyclerView.setAdapter(quarterAdapter);

        this.qvm.getAllQuarters().observe(getViewLifecycleOwner(), new Observer<List<Quarters>>() {
            @Override
            public void onChanged(List<Quarters> quarters) {
                // Update RecyclerView
                quarterAdapter.setQuarters(quarters);
                quarterCountTV.setText( Integer.toString(quarters.size()) );
            }
        });

        adapterListener(quarterAdapter);
    }

    // Components

    private EditText quarterCountTV;

    private RecyclerView recyclerView;

    private TextView nameTV, schoolTV;

    private void init() {
        quarterCountTV = rootView.findViewById(R.id.quarterCountTV);

        recyclerView = rootView.findViewById(R.id.recycler_view_quarter_fragment);

        nameTV = rootView.findViewById(R.id.tvNameD);
        schoolTV = rootView.findViewById(R.id.tvSchoolD);
    }

}
