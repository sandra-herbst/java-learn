package de.hdmstuttgart.java_learn_app.ui.nav_code.topic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;
import de.hdmstuttgart.java_learn_app.ui.nav_code.CodeFragmentDirections;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> implements Filterable {

    private List<TopicEntity> topics = new ArrayList<>();
    private List<TopicEntity> topicsFull;

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        TopicEntity topic = topics.get(position);

        // TODO: 30.04.2021 add background drawable
        //holder.topic_image.setBackground();
        holder.topic_titleText.setText(topic.title);
        holder.topic_descText.setText(topic.description);

        holder.itemView.setOnClickListener(v -> {
            // Add transition names to Viewholders
            //topic_image.setTransitionName(topic.imageURL);
            holder.topic_titleText.setTransitionName(topic.title);
            holder.topic_descText.setTransitionName(topic.description);

            FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                    .addSharedElement(holder.topic_titleText, topic.title)
                    .addSharedElement(holder.topic_descText, topic.description)
                    .build();

            if (topics.size() != topicsFull.size()) {
                SearchView searchView = holder.itemView.findViewById(R.id.searchTopicSearchView);
                searchView.setQuery("", true);
            }

            Navigation.findNavController(holder.itemView).navigate(CodeFragmentDirections.actionNavigationCodeToSubtopicFragment(topics.get(position).topicId, topic.title, topic.description), extras);
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    public void setTopics(List<TopicEntity> topics){
        this.topics = topics;
        this.topicsFull = new ArrayList<>(topics);
        // do not use this method in recyclerview
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    // automatically starts background thread
    private final Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // return result of filtering here, chars is input of user
            List<TopicEntity> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(topicsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TopicEntity topic : topicsFull) {
                    if (topic.title.toLowerCase().contains(filterPattern)){
                        filteredList.add(topic);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            topics.clear();
            topics.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class TopicViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout topic_image;
        public TextView topic_titleText;
        public TextView topic_descText;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            topic_image = itemView.findViewById(R.id.topic_image);
            topic_titleText = itemView.findViewById(R.id.topic_titleText);
            topic_descText = itemView.findViewById(R.id.topic_descText);
        }
    }
}
