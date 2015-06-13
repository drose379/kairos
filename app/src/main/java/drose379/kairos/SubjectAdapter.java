package drose379.kairos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SubjectAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> subjects;

    public SubjectAdapter(Context context,List<String> subjects) {
        this.context = context;
        this.subjects = subjects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return subjects.size();
    }
    @Override
    public String getItem(int item) {
        return subjects.get(item);
    }
    @Override
    public long getItemId(int item) {return item;}

    @Override
    public View getView(int position,View recycledView,ViewGroup parent) {
        View v = recycledView;
        if (v == null) {
            v = inflater.inflate(R.layout.simple_list_child_1,parent,false);
        }

        TextView subName = (TextView) v.findViewById(R.id.subName);
        Typeface tf = TypeHelper.getTypeface(context);
        subName.setTypeface(tf);
        subName.setText(subjects.get(position));

        return v;
    }

}
