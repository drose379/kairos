package drose379.kairos;

import android.support.v4.app.Fragment;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;

import java.io.IOException;

import drose379.kairos.homeTabs.TabLocal;

public class LocalSubjectCreator {
    private OkHttpClient httpClient = new OkHttpClient();
    private TabLocal parentFrag;
    private int ownerID;

    public LocalSubjectCreator(Fragment frag,int ownerID) {
        parentFrag = (TabLocal) frag;
        this.ownerID = ownerID;
    }

    public void newLocalSubject(final String category,final String name,final String privacy) {
        //create the new subject
        //on response use parentFrag instance and create a method to tell the fragment that the subject was created successfully (snackbar)
        JSONArray values = new JSONArray() {{put(category);put(ownerID);put(name);put(privacy);}};

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),values.toString());

        Request request = new Request.Builder()
                .post(body)
                .url("http://104.236.15.47/selfEducate/index.php/newSubject")
                .build();
        Call rCall = httpClient.newCall(request);
        rCall.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                parentFrag.newSubCreated();
            }
            @Override
            public void onFailure(Request request,IOException e) {

            }
        });
    }

    public void newCategory(final String name, final String description) {
        JSONArray values = new JSONArray() {{put(name);put(description);}};
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"),values.toString());
        Request request = new Request.Builder()
                .post(body)
                .url("http://104.236.15.47/selfEducate/index.php/newCategory")
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                parentFrag.newCategoryCreated();
            }
            @Override
            public void onFailure(Request request,IOException e) {

            }
        });
    }
}
