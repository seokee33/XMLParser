package com.seokee.xmlparser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetXml2 extends Thread{

    private ArrayList<Hospital> hospitalList;

    @Override
    public void run() {
        super.run();
        hospitalList = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire"); /*URL*/
        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=iXusHy2%2FZz7qRhjkwpWBlGWiJaRpGZgroGBlBBow45q4oibueEQnnwMUkAUsOVG3PSlRqQKOYf2SGRxHRJAqoQ%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("QD", "UTF-8") + "=" + URLEncoder.encode("D002", "UTF-8")); /*CODE_MST의'D000' 참조(D001~D029)*/
            urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode("대구광역시", "UTF-8")); /*주소(시도)*/
            urlBuilder.append("&" + URLEncoder.encode("Q1", "UTF-8") + "=" + URLEncoder.encode("북구", "UTF-8")); /*주소(시군구)*/

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            Hospital hospital = null;
            while ((line = rd.readLine()) != null) {
                Log.i("GetXml",line);


            }
            rd.close();
            conn.disconnect();
            for (Hospital h: hospitalList) {
                Log.i("GetXml", h.getDutyName()+h.getDutyAddr()+h.getDutyTime1c());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Hospital> getArrayList() {
        return hospitalList;
    }
}




//public class GetXml extends AsyncTask<String, Void, Document> {
//    private ArrayList<Hospital> arrayList = new ArrayList<>();
//    private MainActivity context;
//
//    public GetXml(Context context) {
//        this.context = (MainActivity) context;
//    }
//
//    @Override
//    protected Document doInBackground(String... strings) {
//        Document doc = null;
//        URL url;
//        try {
//            url = new URL(strings[0]);
//            DocumentBuilderFactory dbf =
//                    DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            doc = db.parse(new InputSource(url.openStream()));
//            doc.getDocumentElement().normalize();
//        } catch (Exception e) {
//            Log.d("GetXml", "에러 : " + e.getMessage());
//            e.printStackTrace();
//        }
//        return doc;
//    }
//
//    @Override
//    protected void onPostExecute(Document document) {
//        super.onPostExecute(document);
//        NodeList nodeList = document.getElementsByTagName("item");
//
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node = nodeList.item(i);
//            Element fstElmnt = (Element) node;
//
//            // 날짜, 시간, 온도, 습도, 강수확률, 날씨
//             NodeList nameList = fstElmnt.getElementsByTagName("dutyName");
//             Element namElement = (Element) nameList.item(0);
//             nameList = namElement.getChildNodes();
//             String name = nameList.item(0).getNodeValue();
//
//            NodeList timeList = fstElmnt.getElementsByTagName("dutyTime1c");
//            Element timeElement = (Element) timeList.item(0);
//            timeList = timeElement.getChildNodes();
//            String time = timeList.item(0).getNodeValue();
//
//            NodeList addressList = fstElmnt.getElementsByTagName("dutyAddr");
//            Element addressElement = (Element) addressList.item(0);
//            addressList = addressElement.getChildNodes();
//            String location = addressList.item(0).getNodeValue();
//
//            arrayList.add(new Hospital(name,time,location));
//        }
//
//        context.MyNotifyDataSetChanged(arrayList);
//    }
//
//
//    public ArrayList<Hospital> getArrayList() {
//        return arrayList;
//    }
//}