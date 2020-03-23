package com.project.tutoquizzer.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tutoquizzer.R;
import com.project.tutoquizzer.entities.SchoolYear;

import java.util.ArrayList;
import java.util.List;

public class SchoolYearAdapter extends  RecyclerView.Adapter<SchoolYearAdapter.SchoolYearHolder> {

    private List<SchoolYear> schoolYears = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public SchoolYearHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View schoolYearView = LayoutInflater.from(parent.getContext()).inflate(R.layout._school_year_list, parent, false);
        return new SchoolYearHolder(schoolYearView);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolYearHolder holder, int position) {
        SchoolYear temp = schoolYears.get(position);
        holder.termTV.setText( "Term " + temp.getTerm() );
        holder.yearTV.setText("Year " + temp.getYear() );
    }

    @Override
    public int getItemCount() {
        return schoolYears.size();
    }

    public void setSchoolYears(List<SchoolYear> schoolYears){
        this.schoolYears = schoolYears;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(SchoolYear schoolYear);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class SchoolYearHolder extends RecyclerView.ViewHolder {

        private TextView termTV;
        private TextView yearTV;

        public SchoolYearHolder(View itemView){
            super(itemView);

            termTV = itemView.findViewById(R.id.text_view_term);
            yearTV = itemView.findViewById(R.id.text_view_year);

            itemView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(schoolYears.get(position));
                }
            });

        }

    }

}
