package com.example.lab_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class ActivityTwo extends Activity {
    private new_Adapter mAdapter;
    public final static int DG_POWER = 3;
    private final static String[][] a_power = new String[][]{
            {"0", "", "", ""},  // 1
            {"1", "тысяча ", "тысячи ","тысяч "},  // 2
            {"0", "миллион "},  // 3
    };

    private final static String[][] digit = new String[][] {
            {""       ,""       , "десять "      , ""            ,""          },
            {"один "  ,"одна "  , "одиннадцать " , "десять "     ,"сто "      },
            {"два "   ,"две "   , "двенадцать "  , "двадцать "   ,"двести "   },
            {"три "   ,"три "   , "тринадцать "  , "тридцать "   ,"триста "   },
            {"четыре ","четыре ", "четырнадцать ", "сорок "      ,"четыреста "},
            {"пять "  ,"пять "  , "пятнадцать "  , "пятьдесят "  ,"пятьсот "  },
            {"шесть " ,"шесть " , "шестнадцать " , "шестьдесят " ,"шестьсот " },
            {"семь "  ,"семь "  , "семнадцать "  , "семьдесят "  ,"семьсот "  },
            {"восемь ","восемь ", "восемнадцать ", "восемьдесят ","восемьсот "},
            {"девять ","девять ", "девятнадцать ", "девяносто "  ,"девятьсот "}
    };
    String[] numbers = new String[1000000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        mAdapter = new new_Adapter(this);
        lvMain.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }

    public static String numToString(int num)
    {
        int res;
        StringBuffer result = new StringBuffer("");
        long divisor = 1; //делитель
        int temp = num;

        int one  = 1;
        int four = 2;
        int many = 3;
        int hundreds  = 4;
        int dec1  = 3;
        int dec2 = 2;

        for(int i = 0; i < DG_POWER; i++)
            divisor *= 1000;

        for(int i = DG_POWER-1; i >= 0; i--){
            divisor /= 1000;
            res = (int)(temp / divisor);
            temp %= divisor;
            if (res == 0){
                if(i > 0) continue;
                result.append( a_power[i][one] );
            }
            else {
                if (res >= 100) {
                    result.append(digit[res/100][hundreds]);
                    res %= 100;
                }
                if (res >= 20) {
                    result.append(digit[res/10][dec1]);
                    res %=10;
                }
                if (res >= 10){
                    result.append(digit[res-10][dec2]);
                }
                else {
                    if(res >= 1)
                        result.append(digit[res][ "0".equals(a_power[i][0]) ? 0 : 1 ] );
                }
                switch(res){
                    case  1: result.append( a_power[i][one] ); break;
                    case  2:
                    case  3:
                    case  4: result.append( a_power[i][four]); break;
                    default: result.append( a_power[i][many]); break;
                };
            }
        }
        return result.toString();
    }

    private class new_Adapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        new_Adapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return numbers.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);

            ImageView image = (ImageView) convertView.findViewById(R.id.icons);
            image.setImageResource(R.drawable.icon1);

            TextView numberTextView = (TextView) convertView.findViewById(R.id.number);
            numberTextView.setText(numToString(position + 1));
            convertView.setBackgroundColor((position & 1) == 1 ? Color.GRAY : Color.WHITE);

            return convertView;
        }
    }
}


