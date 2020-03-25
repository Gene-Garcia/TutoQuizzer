package com.project.tutoquizzer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.view.adapters.QuarterAdapter;
import com.project.tutoquizzer.view.adapters.SchoolYearAdapter;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

import java.util.List;

public class AddQuarterFragment extends Fragment {

    private QuarterViewModel qvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_add_quarter, container, false);
        this.qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);

        init();

        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final QuarterAdapter adapter = new QuarterAdapter();
        recyclerView.setAdapter(adapter);

        this.qvm.getAllQuarters().observe(getViewLifecycleOwner(), new Observer<List<Quarters>>() {
            @Override
            public void onChanged(List<Quarters> quarters) {
                // Update RecyclerView
                adapter.setQuarters(quarters);
            }
        });

        adapterListener(adapter);

        buttonListeners();

        return this.rootView;
    }


    private void adapterListener(QuarterAdapter adapter){
        adapter.setOnItemClickListener(new QuarterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quarters quarter) {

                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.QUARTER_ID_KEY, quarter.getQuarterId());
                bundle.putString    (RouteValues.QUARTER_NAME_KEY, quarter.getName());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditQuarterFragment editQuarterFragment = new EditQuarterFragment();
                editQuarterFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editQuarterFragment);
                fragmentTransaction.commit();

            }
        });
    }

    // Components
    private RecyclerView recyclerView;

    private EditText quarterName;

    private Button addBtn;

    // End

    private void init(){
        recyclerView    = rootView.findViewById(R.id.recycler_view_quarter_fragment);

        quarterName          = rootView.findViewById(R.id.quarterAddQuarterEditText);

        addBtn          = rootView.findViewById(R.id.addQuarterD);
    }

    private void buttonListeners(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = quarterName.getText().toString().trim();

                if(name.isEmpty()){
                    Toast.makeText(getContext(), "Fill out all the fields.", Toast.LENGTH_SHORT).show();
                }else{
                    Quarters quarters = new Quarters(name);
                    qvm.insert(quarters);
                    Toast.makeText(getContext(), "Quarter added.", Toast.LENGTH_SHORT).show();
                    clearTextViews();
                }


            }
        });
    }

    private void  clearTextViews(){
        quarterName.setText("");
    }

}
