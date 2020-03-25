package com.project.tutoquizzer.dashboardFragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tutoquizzer.EditQuarterFragment;
import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.view.adapters.QuarterAdapter;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.List;

public class QuarterFragment extends Fragment {

    private QuarterViewModel qvm;
    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_quarter, container, false);

        qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);
        tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

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

        addDataToRecyclerView(quarterAdapter);

        adapterListener(quarterAdapter);
        swiperListener(quarterAdapter);
    }

    private void addDataToRecyclerView(final QuarterAdapter adapter){
        this.qvm.getAllQuarters().observe(getViewLifecycleOwner(), new Observer<List<Quarters>>() {
            @Override
            public void onChanged(List<Quarters> quarters) {
                // Update RecyclerView
                adapter.setQuarters(quarters);
                quarterCountTV.setText( Integer.toString(quarters.size()) );
            }
        });
    }

    private void swiperListener(final QuarterAdapter adapter){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                int quarterId = adapter.getQuarterAtPos( viewHolder.getAdapterPosition() ).getQuarterId();
                tvm.getReferenceTopicCountByQuarter(quarterId).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (Integer.parseInt( integer.toString() ) >= 1){
                            Toast.makeText(getContext(), "Error. This quarter has topic assigned to it. Delete topic(s) and try again.", Toast.LENGTH_SHORT).show();
                            addDataToRecyclerView(adapter);
                        }else{
                            new AlertDialog.Builder(getContext()).setTitle("Confirm Delete").setMessage("Are you sure you want to delete this quarter?")
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            qvm.delete( adapter.getQuarterAtPos( viewHolder.getPosition() ) );
                                            Toast.makeText(getContext(), "Quarter Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            addDataToRecyclerView(adapter);
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert).show();
                        }
                    }
                });
            }

        }).attachToRecyclerView(recyclerView);

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
