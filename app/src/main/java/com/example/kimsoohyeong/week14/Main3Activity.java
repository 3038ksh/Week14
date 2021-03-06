package com.example.kimsoohyeong.week14;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main3Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = (ListView)findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Next");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            changeToNextAcitivity();
        }
        return super.onOptionsItemSelected(item);
    }

    Handler handler = new Handler();
    private class MyThread extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("https://news.google.com/news?cf=all&hl=ko&pz=1&ned=kr&topic=m&output=rss");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    int itemCount = readData(urlConnection.getInputStream());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int readData(InputStream is) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            return parseDocument(document);
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseDocument(Document doc) {
        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if ((nodelist != null) && (nodelist.getLength() > 0)) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                String newsItem = getTagData(nodelist, i);
                if (newsItem != null) {
                    data.add(newsItem);
                    count++;
                }
            }
        }
        return count;
    }

    private String getTagData(NodeList nodelist, int index) {
        String newsItem = null;

        try {
            Element entry = (Element) nodelist.item(index);
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);

            String titleValue = null;
            String pubDateValue = null;

            if (title != null) {
                Node firstChild = title.getFirstChild();
                if (firstChild != null) titleValue = firstChild.getNodeValue();
            }

            if (pubDate != null) {
                Node firstChild = pubDate.getFirstChild();
                if (firstChild != null) pubDateValue = pubDate.getFirstChild().getNodeValue();
            }

            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
            newsItem = titleValue + "-" + simpleDateFormat.format(Date.parse(pubDateValue));

        } catch (DOMException e) {
            e.printStackTrace();
        }
        return newsItem;
    }

    public void onClick(View v) {
        MyThread thread = new MyThread();
        thread.start();
    }

    public void changeToNextAcitivity() {
        Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
        startActivity(intent);
    }
}