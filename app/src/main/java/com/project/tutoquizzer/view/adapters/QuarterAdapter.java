package com.project.tutoquizzer.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tutoquizzer.R;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;

import java.util.ArrayList;
import java.util.List;

public class QuarterAdapter extends RecyclerView.Adapter<QuarterAdapter.QuarterHolder> {

    private List<Quarters> quarters = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public QuarterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView = LayoutInflater.from(parent.getContext()).inflate(R.layout._quarters_list, parent, false);
        return new QuarterHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuarterHolder holder, int position) {
        Quarters temp = quarters.get(position);
        holder.quarterTV.setText(temp.getName());
    }

    @Override
    public int getItemCount() {
        return this.quarters.size();
    }

    public void setQuarters(List<Quarters> quarters){
        this.quarters = quarters;
        notifyDataSetChanged();
    }

    public Quarters getQuarterAtPos(int pos) {
        return this.quarters.get(pos);
    }

    public interface OnItemClickListener{
        void onItemClick(Quarters quarters);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class QuarterHolder extends RecyclerView.ViewHolder {

        private TextView quarterTV;

        public QuarterHolder(View itemView) {
            super(itemView);

            quarterTV = itemView.findViewById(R.id.text_view_quarter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(quarters.get(position));
                }
            });

        }
    }

}
