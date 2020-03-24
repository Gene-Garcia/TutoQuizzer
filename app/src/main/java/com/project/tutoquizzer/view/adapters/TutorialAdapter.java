package com.project.tutoquizzer.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tutoquizzer.R;
import com.project.tutoquizzer.entities.Topics;

import java.util.ArrayList;
import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialHolder> {

    private List<Topics> topics = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public TutorialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tutorialView = LayoutInflater.from(parent.getContext()).inflate(R.layout._topics_description_list, parent, false);
        return new TutorialHolder(tutorialView);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialHolder holder, int position) {
        Topics temp = topics.get(position);
        holder.topicTV.setText( temp.getTopic());
        holder.meaningTv.setText(temp.getMeaning());
    }

    @Override
    public int getItemCount() {
        return this.topics.size();
    }


    public void setTopics(List<Topics> topics){
        this.topics = topics;
        notifyDataSetChanged();
    }

    public Topics getTopicAtPosition(int pos) {
        return topics.get(pos);
    }

    public interface OnItemClickListener{
        void onItemClick(Topics courses);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class TutorialHolder extends RecyclerView.ViewHolder {
        private TextView topicTV;
        private TextView meaningTv;

        public TutorialHolder(View itemView) {
            super(itemView);

            topicTV = itemView.findViewById(R.id.text_view_topic_tutorial_act);
            meaningTv = itemView.findViewById(R.id.text_view_description_tutorial_act);

            itemView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(topics.get(position));
                }
            });

        }
    }
}