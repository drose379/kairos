package drose379.kairos.homeTabs;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import drose379.kairos.Category;
import drose379.kairos.R;

public class CategoryCardAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Category> categories;

    public CategoryCardAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }
    @Override
    public Category getItem(int item) {
        return categories.get(item);
    }
    @Override
    public long getItemId(int item) {
        return 0;
    }
    @Override
    public View getView(int position,View recycledView,ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = recycledView;
        if (v == null) {
            v = inflater.inflate(R.layout.card_layout, parent, false);

            TextView cardTitle = (TextView) v.findViewById(R.id.titleText);
            TextView cardDescription = (TextView) v.findViewById(R.id.desc);
            LinearLayout subjectContainer = (LinearLayout) v.findViewById(R.id.subjectList);

            Category currentCategory = categories.get(position);

            cardTitle.setText(currentCategory.getName());
            cardDescription.setText(currentCategory.getDescription());

            for (int i=0;i<currentCategory.getSubjects().size();i++) {
                int size = currentCategory.getSubjects().size();

                String subject = currentCategory.getSubjects().get(i);

                TextView subText = (TextView) inflater.inflate(R.layout.simple_list_child_1,null);

                View divider = new View(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                params.setMargins(0, 0, 0, 0);
                divider.setLayoutParams(params);
                divider.setBackgroundColor(Color.parseColor("#B3B6B6B6"));

                //Need to compare subject number to the amount of subjects in the list, if it is last subject in list, do not show divider

                subText.setText(subject);
                subjectContainer.addView(subText);
                if(i+1<size) {subjectContainer.addView(divider);}
            }

        }
        return v;
    }
}

