package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ArrayList<Tech> mist;
    String[] names;
    String[] graphics;
    String[] helps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Activity_Two.class);
        mist = new ArrayList<Tech>();
        JSONparse text = new JSONparse();
        try {
            String t = text.execute("https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json").get();
            parse(t);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        names = new String[87];
        graphics = new String[87];
        helps = new String[87];
        for(int i = 0; i < 87; i++)
        {
            names[i] = mist.get(i).get_name();
            graphics[i] = mist.get(i).get_graphic();
            helps[i] = mist.get(i).get_helptext();
        }

        intent.putExtra("n", names);
        intent.putExtra("g", graphics);
        intent.putExtra("h", helps);
        startActivity(intent);
        finish();
    }

    public void parse(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 1; i < jsonArray.length(); i++) {
                JSONObject JO = jsonArray.getJSONObject(i);
                Tech tech = new Tech();
                tech.set_graphic(JO.getString("graphic"));
                tech.set_name(JO.getString("name"));
                if (JO.has("helptext"))
                    tech.set_helptext(JO.getString("helptext"));
                else
                    tech.set_helptext(" ");
                mist.add(i-1, tech);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
