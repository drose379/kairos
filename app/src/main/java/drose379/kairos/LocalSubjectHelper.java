package drose379.kairos;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import drose379.kairos.homeTabs.TabLocal;

@SuppressWarnings("NewApi")
public class LocalSubjectHelper {

    private OkHttpClient httpClient = new OkHttpClient();

    private int ownerID;
    private TabLocal parentFrag;
    private Handler handler = new Handler();

    public LocalSubjectHelper(Fragment frag,int ownerID) {
        this.ownerID = ownerID;
        parentFrag = (TabLocal) frag;
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
                    final ArrayList<Category> fullCategories = createFullCategories(allCategories,subjects);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            parentFrag.getLocalData(fullCategories);
                        }
                    });

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
    public ArrayList<Category> createFullCategories(ArrayList<Category> allCategories,ArrayList<Subject> localSubjects) throws JSONException {
        ArrayList<Category> categories = new ArrayList<Category>();

        for(Category currentCategory : allCategories) {
            String name = currentCategory.getName();
            for(Subject currentSubject : localSubjects) {
                String subCategory = currentSubject.getCategory();
                String subName = currentSubject.getName();
                if (name.equals(subCategory)) {
                    currentCategory.addSubject(subName);
                }
            }
            categories.add(currentCategory);
        }
        return categories;
    }

}
