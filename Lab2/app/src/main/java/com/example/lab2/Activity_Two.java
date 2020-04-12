package com.example.lab2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Activity_Two extends AppCompatActivity {
    int pos = 0;
    private new_Adapter mAdapter;
    public ArrayList<Tech> techList;
    String[] names;
    String[] grap;
    String[] helps;
    TextView tW;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    ListView lv;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        techList = new ArrayList<Tech>();
        setContentView(R.layout.activity__two);
        //tW = (TextView) findViewById(R.id.textView);
        Bundle intent = getIntent().getExtras();
        names = intent.getStringArray("n");
        grap = intent.getStringArray("g");
        helps = intent.getStringArray("h");
        for (int i = 0; i < names.length; i++)
        {
            Tech t = new Tech();
            t.set_name(names[i]);
            t.set_graphic(grap[i]);
            t.set_helptext(helps[i]);
            techList.add(i, t);
        }


        lv = (ListView) findViewById(R.id.lvMain);
        mAdapter = new new_Adapter(this);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                pos = position;
                lv.setVisibility(View.INVISIBLE);
                viewPager = findViewById(R.id.viewpager);
                adapter = new FragmentAdapter(getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(1);
                viewPager.setVisibility(View.VISIBLE);
            }
        });

    }

    private class new_Adapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        new_Adapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return techList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            public ImageView imageView;
            public TextView textView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mLayoutInflater.inflate(R.layout.item, null);

            new DownloadImageTask((ImageView) convertView.findViewById(R.id.icons))
                    .execute("https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + techList.get(position).get_graphic());
            TextView numberTextView = (TextView) convertView.findViewById(R.id.number);
            numberTextView.setText(techList.get(position).get_name());

            return convertView;
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            BlankFragment blankFragment = new BlankFragment();
            Bundle bundle = new Bundle();


            switch (position) {
                case 0: {
                    bundle.putString("text", techList.get(pos-1).get_name() + "\n" + techList.get(pos-1).get_helptext());
                    bundle.putString("image", "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + techList.get(pos-1).get_graphic());
                    blankFragment.setArguments(bundle);
                    return blankFragment;
                }
                case 1: {
                    bundle.putString("text", techList.get(pos).get_name() + "\n\n" + techList.get(pos).get_helptext());
                    bundle.putString("image", "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + techList.get(pos).get_graphic());
                    blankFragment.setArguments(bundle);
                    return blankFragment;
                }
                case 2: {
                    bundle.putString("text", techList.get(pos+1).get_name() + "\n\n" + techList.get(pos+1).get_helptext());
                    bundle.putString("image", "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + techList.get(pos+1).get_graphic());
                    blankFragment.setArguments(bundle);
                    return blankFragment;
                }
                    default:
                        { bundle.putString("text", techList.get(pos).get_name() + "\n\n" + techList.get(pos).get_helptext());
                    bundle.putString("image", "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + techList.get(pos).get_graphic());
                    blankFragment.setArguments(bundle);
                    return blankFragment;
                    }
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}




