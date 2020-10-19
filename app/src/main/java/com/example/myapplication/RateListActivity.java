package com.example.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RateListActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{
    Handler handler;
    private static final String TAG = "RateListActivity";
    SharedPreferences sp;
    String date;
    Set rate;
    int length = 3;

    public ArrayList<HashMap<String,String>> getRate2() {
        String url = "https://www.usd-cny.com/bankofchina.htm";
        Document doc = null;
        //List<String> list = new ArrayList<>();
        ArrayList<HashMap<String,String>>listItems = new ArrayList<HashMap<String,String>>();
        try {
            doc = (Document) Jsoup.connect(url).get();
            Log.i(TAG, "getRate2: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(0);//因为本网页只有一个table，等价于.first()
            Elements tds = table.getElementsByTag("td");//从table中找<td>，即列
            for (int i = 0; i < tds.size(); i += 6) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                //list.add(str1 + "==>" + val);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",val);
                listItems.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listItems;
    }

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(){
            public void handleMessage(Message msg){
                if (msg.what == 6) {
                    sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sp.edit();
                    editor.putStringSet("allRate",new HashSet<String>((List<String>)msg.obj));
                    editor.putString("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    editor.apply();

                    //List<String> str1 =  (List<String>)msg.obj;

                    //ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,str1);
                    ArrayList<HashMap<String, String>>listItems = (ArrayList<HashMap<String, String>>)msg.obj;

                    MyAdapter myAdapter = new MyAdapter(RateListActivity.this,
                            R.layout.list_item,listItems
                            );
                    setListAdapter(myAdapter);
                    getListView().setOnItemClickListener(RateListActivity.this);

                }
                super.handleMessage(msg);
            }
        };
        sp = getSharedPreferences("rate",Activity.MODE_PRIVATE);
        date = sp.getString("date","");
        Date date2 = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        String now = simple.format(date2);

        if (date == now) {
            rate = sp.getStringSet("allRate", null);

            //ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            ArrayList<HashMap<String, String>>listItems = new ArrayList<HashMap<String, String>>(rate);

            MyAdapter myAdapter = new MyAdapter(RateListActivity.this,
                    R.layout.list_item,listItems
            );
            setListAdapter(myAdapter);
        } else {
            Thread t = new Thread(this);
            t.start();
        }
    }



    public void run(){
        Log.i(TAG,"run:");
        URL url = null;
        ArrayList<String> arrayList;
        ArrayList<HashMap<String, String>> list = getRate2();
        Message msg = handler.obtainMessage(6);
        msg.obj=list;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object itemAtPosition = getListView().getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);
        TextView title = (TextView) view.findViewById(R.id.ItemTitle);
        TextView detail = (TextView) view.findViewById(R.id.ItemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);
        Intent intent = new Intent(this,Now_rate_calculate.class);
        intent.putExtra("titleStr",titleStr);
        intent.putExtra("rateStr",detailStr);
        startActivity(intent);
    }
}
