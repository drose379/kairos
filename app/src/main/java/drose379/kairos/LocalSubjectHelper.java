package drose379.kairos;

import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NewApi")
public class LocalSubjectHelper {

    private OkHttpClient httpClient = new OkHttpClient();

    public interface FragCallback {
        public void localData(List<Category> categories);
    }

    private int ownerID;
    private List<Category> localData;

    public LocalSubjectHelper(int ownerID) {
        this.ownerID = ownerID;
    }

    public void initGrab() {
        JSONArray ownerIdJSON = new JSONArray();
        try {
            ownerIdJSON.put(0,ownerID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString = ownerIdJSON.toString();

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), jsonString);
        Request.Builder rBuilder = new Request.Builder();
        rBuilder.post(body);
        rBuilder.url("http://104.236.15.47/selfEducate/index.php/getLocalSubs");
        Request request = rBuilder.build();
        Call httpCall = httpClient.newCall(request);
        httpCall.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject servResponse = new JSONObject(response.body().string());

                    ArrayList<Category> allCategories = stripDuplicates(servResponse.getJSONArray("categories"));
                    ArrayList<Subject> subjects = sortSubjects(servResponse.getJSONArray("fullSubInfo"));

                    //localdata references an arraylist of cateogries with all of their children subjects included in the cateogry object
                    localData = createCategories(allCategories,subjects);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
            @Override
            public void onFailure(Request request,IOException e) {

            }
        });
    }

    public ArrayList<Category> stripDuplicates(JSONArray categories) throws JSONException {
        ArrayList<Category> stripped = new ArrayList<Category>();
        ArrayList<String> categoryaddedNames = new ArrayList<String>();

        for(int i=0;i<categories.length();i++) {
            String categoryName = categories.getJSONObject(i).getString("category");
            String categoryDescription = categories.getJSONObject(i).getString("description");
            if (!categoryaddedNames.contains(categoryName)) {
                categoryaddedNames.add(categoryName);
                stripped.add(new Category(categoryName,categoryDescription));
            }
        }
       return stripped;
    }

    public ArrayList<Subject> sortSubjects(JSONArray rawSubjects) throws JSONException {
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        for(int i=0;i<rawSubjects.length();i++) {
            String subjectName = rawSubjects.getJSONObject(i).getString("name");
            String subjectCategory  = rawSubjects.getJSONObject(i).getString("category");
            subjects.add(new Subject(subjectName,subjectCategory));
        }
        return subjects;
    }

    /**
     * Creates category objects that contain their child subjects
     * @param allCategories
     * @param localSubjects
     * @return
     * @throws JSONException
     */
    public List<Category> createCategories(ArrayList<Category> allCategories,ArrayList<Subject> localSubjects) throws JSONException {
        List<Category> categories = new ArrayList<Category>();

        /*
        Need to remove duplicates from allCategories JSONArray

        Log.i("allCats",allCategories.toString());

        for(int i=0;i<allCategories.length();i++) {
            JSONObject currentCategory = allCategories.getJSONObject(i);
            String category = currentCategory.getString("category");
            String description = currentCategory.getString("description");
            categories.add(new Category(category,description));
        }

        //loop over each subject
        //get category, loop over the list of Categories inside that loop, foreach category,
        // if the current subject cateogory is equal, grab the sub name and run addSubject()

        for (int i=0;i<localSubjects.length();i++) {
            JSONObject currentSubject = localSubjects.getJSONObject(i);
            String subject = currentSubject.getString("name");
            String category = currentSubject.getString("category");

            for (Category currentCategory : categories) {
                if (category.equals(currentCategory.getName())) {
                    currentCategory.addSubject(subject);
                }
            }
        }
        */
        return categories;
    }

}
