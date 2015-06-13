package drose379.kairos;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView) findViewById(R.id.titleText);
        TextView title2 = (TextView) findViewById(R.id.titleText2);
        TextView desc = (TextView) findViewById(R.id.desc);

        Typeface ss = TypeHelper.getTypeface(this);
        title.setTypeface(ss);
        title2.setTypeface(ss);
        desc.setTypeface(ss);

        ListView listView = (ListView) findViewById(R.id.list);
        ListView listView2 = (ListView) findViewById(R.id.list2);

        List<String> testItems = new ArrayList<String>(){{add("Objective-C");add("PHP");add("SQL");add("Java");add("Kotlin");}};
        List<String> testItems2 = new ArrayList<String>(){{add("PhotoShop");add("Illustrator");add("Gimp");add("Web Design");add("Word Art");}};

        SubjectAdapter adapter = new SubjectAdapter(this,testItems);
        SubjectAdapter adapter2 = new SubjectAdapter(this,testItems2);

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
