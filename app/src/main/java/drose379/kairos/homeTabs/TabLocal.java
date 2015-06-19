package drose379.kairos.homeTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import drose379.kairos.Category;
import drose379.kairos.LocalSubjectHelper;
import drose379.kairos.OwnerIdUtil;
import drose379.kairos.R;

public class TabLocal extends Fragment {

    private LocalSubjectHelper subHelper;

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstance) {
        super.onCreateView(inflater,container,savedInstance);
        View v = inflater.inflate(R.layout.tab_local,container,false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (subHelper == null) {
            subHelper = new LocalSubjectHelper(this,OwnerIdUtil.getOwnerID(getActivity()));
        }
        subHelper.initGrab();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("fragmentStat", "Fragment destroyed");
    }

    public void getLocalData(ArrayList<Category> categories) {
        /*use categories with subjects to generate view
            * First, create custom adapter that creates the Category card views
            * Then, work with the ListView inside each card to display the cards subjects
        */
        ListView categoryContainer = (ListView) getView().findViewById(R.id.categoryHolder);
        categoryContainer.setAdapter(new CategoryCardAdapter(getActivity(), categories));

        //also need to initialize the FAB with the categories items
        FloatingActionButton subjectCreateButton = (FloatingActionButton) getView().findViewById(R.id.fab);
        initSubMenu(subjectCreateButton,categories);
    }

    public void initSubMenu(FloatingActionButton button,ArrayList<Category> categories) {
        final String[] menuItems = new String[categories.size()];

        for(int i=0;i<categories.size();i++) {
            Category current = categories.get(i);
            menuItems[i] = current.getName();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                        .items(menuItems)
                        .title("Choose a Category")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog,View view,int which,CharSequence text) {
                                //dismiss this, open dialog to add subject to selected category
                            }
                        });
                MaterialDialog categoryChooser = builder.build();
                categoryChooser.show();
            }
        });
    }


}
