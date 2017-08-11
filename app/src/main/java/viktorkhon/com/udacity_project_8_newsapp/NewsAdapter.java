package viktorkhon.com.udacity_project_8_newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Viktor Khon on 8/9/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    static class ViewHolder {
        private TextView title;
        private TextView sectionName;
        private TextView publishedDate;
    }

    public NewsAdapter(Activity context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View listView, ViewGroup parent) {
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list, parent, false);
        }

        News currentNews = getItem(position);

        ViewHolder holder = new ViewHolder();

        holder.title = (TextView) listView.findViewById(R.id.title);
        holder.title.setText(currentNews.getTitle());

        holder.sectionName = (TextView) listView.findViewById(R.id.section);
        holder.sectionName.setText("Section: " + currentNews.getSectionName());

        holder.publishedDate = (TextView) listView.findViewById(R.id.date);
        holder.publishedDate.setText("Date published: " + currentNews.getDatePublished());

        return listView;
    }
}


