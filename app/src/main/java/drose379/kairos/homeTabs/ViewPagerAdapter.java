package drose379.kairos.homeTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import drose379.kairos.R;

/**
 * Created by drose379 on 6/13/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int[] tabIcons = {R.drawable.ic_home_white_24dp,R.drawable.ic_public_white_24dp,R.drawable.ic_bookmark_border_white_24dp};
    private String[] titles = {"Home","Public","Bookmark"};

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int item) {
        switch(item) {
            case 0 :
                TabLocal TabLocal = new TabLocal();
                return TabLocal;
            case 1:
                TabPublic tabPublic = new TabPublic();
                return tabPublic;
            case 2:
                TabBookmarked tabBookmarked = new TabBookmarked();
                return tabBookmarked;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public int getCount() {
        return tabIcons.length;
    }

    public int getDrawableId(int position) {
        return tabIcons[position];
    }

    public String getTabTitle(int position) {
        return titles[position];
    }

}
