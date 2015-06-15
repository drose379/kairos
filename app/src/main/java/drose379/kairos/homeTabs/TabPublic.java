package drose379.kairos.homeTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import drose379.kairos.R;

/**
 * Created by drose379 on 6/13/15.
 */
public class TabPublic extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstance) {
        super.onCreateView(inflater,container,savedInstance);
        View v = inflater.inflate(R.layout.tab_public,container,false);
        return v;
    }


}
