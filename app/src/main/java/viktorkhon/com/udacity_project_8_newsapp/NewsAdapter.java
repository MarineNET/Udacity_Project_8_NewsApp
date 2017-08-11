package viktorkhon.com.udacity_project_8_newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Viktor Khon on 8/9/2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{

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

        TextView title = (TextView) listView.findViewById(R.id.title);
        title.setText(currentNews.getTitle());

        TextView sectionName = (TextView) listView.findViewById(R.id.section);
        sectionName.setText(currentNews.getSectionName());

        TextView publishedDate = (TextView) listView.findViewById(R.id.date);
        // Create a new Date object and pass the value in millisecs
        Date newDate = new Date(currentNews.getDatePublished());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        // Attach the formatter to the date, and store it in a String
        String dateToDisplay = dateFormat.format(newDate);
        publishedDate.setText(dateToDisplay);

        return listView;
    }
}


