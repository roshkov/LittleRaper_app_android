package com.example.testhandin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class this_fragment_tab_rhyme extends Fragment implements RhymeAdapter.OnListItemClickListener {

    private ArrayList<String> rhymelist;
    private Button searchBtn;
    private EditText word;
    private RecyclerView rhymeLV;
    static final String REQUEST_URL = "https://api.datamuse.com/words?rel_rhy=";
    RecyclerView mRhymeList;
    RecyclerView.Adapter mRhymeAdapter;
    TextView tv ;
    TextView tv2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.this_tab_rhyme,container,false);

        tv = view.findViewById(R.id.textTab2);
        tv2 = view.findViewById(R.id.textTab1);


        ThisSongActivity activityReferenceVarSong = (ThisSongActivity) getActivity();
        tv.setBackgroundColor(activityReferenceVarSong.ColorT);
        tv.setTextColor(activityReferenceVarSong.ColorT);
        Log.i("this fragment==========", " " + activityReferenceVarSong.ColorT);


        rhymelist = new ArrayList<>();

        searchBtn = view.findViewById(R.id.searchButton);
        word = view.findViewById(R.id.wordET);
        rhymeLV = view.findViewById(R.id.rhymeList);

        mRhymeList = view.findViewById(R.id.rhymeList);
        mRhymeList.hasFixedSize();
        mRhymeList.setLayoutManager(new LinearLayoutManager(getActivity()));


        mRhymeAdapter = new RhymeAdapter(rhymelist,this);
        mRhymeList.setAdapter(mRhymeAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                GetRhymeAsync task = new GetRhymeAsync();

                task.execute(REQUEST_URL+word.getText().toString());


            }
        });


        return view;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream is = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                is = urlConnection.getInputStream();
                jsonResponse = readFromStream(is);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (is != null)
                is.close();
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream is) throws IOException {
        StringBuilder output = new StringBuilder();
        if (is != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private class GetRhymeAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String jsonResponse = "";
            try {
                url = new URL(strings[0]);
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject root = null;
            try {

                JSONArray json = new JSONArray(s); //array of rhymes
//                JSONObject e = json.getJSONObject(0);

//                String name = e.getString("word");

//                rhymesTV.setText(name);


                if (json.length()==0){
                    rhymelist.add("No rhymes found");
                }
                else{
                    for(int i=0;i<json.length();i++){

                        JSONObject e = json.getJSONObject(i);
                        rhymelist.add(e.getString("word"));
                        mRhymeAdapter.notifyDataSetChanged();
                        //rhymeLV.add(rhymelist.get(i));
                    } }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
