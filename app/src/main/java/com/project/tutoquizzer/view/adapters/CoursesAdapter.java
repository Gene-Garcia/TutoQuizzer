package com.project.tutoquizzer.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tutoquizzer.R;
import com.project.tutoquizzer.entities.Courses;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseHolder> {

    private List<Courses> courses = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseView = LayoutInflater.from(parent.getContext()).inflate(R.layout._courses_list, parent, false);
        return new CourseHolder(courseView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Courses temp = courses.get(position);
        holder.courseNameTV.setText( temp.getName());
        holder.codeNameTv.setText(temp.getCode());
    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public void setCourse(List<Courses> courses){
        this.courses = courses;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Courses courses);
    }

    public Courses getCourseAtPos(int pos){
        return courses.get(pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView courseNameTV;
        private TextView codeNameTv;

        public CourseHolder(View itemView) {
            super(itemView);

            courseNameTV = itemView.findViewById(R.id.text_view_description);
            codeNameTv = itemView.findViewById(R.id.text_view_code);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION)
                    listener.onItemClick(courses.get(position));
                }
            });

        }

    }

}
