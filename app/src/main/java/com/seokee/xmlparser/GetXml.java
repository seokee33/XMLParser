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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetXml extends AsyncTask<String, Void, Document> {
    private ArrayList<Hospital> arrayList = new ArrayList<>();
    private MainActivity context;

    public GetXml(Context context) {
        this.context = (MainActivity) context;
    }

    @Override
    protected Document doInBackground(String... strings) {
        Document doc = null;
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=iXusHy2%2FZz7qRhjkwpWBlGWiJaRpGZgroGBlBBow45q4oibueEQnnwMUkAUsOVG3PSlRqQKOYf2SGRxHRJAqoQ%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("Q0","UTF-8") + "=" + URLEncoder.encode("대구광역시", "UTF-8")); /*주소(시도)*/
            urlBuilder.append("&" + URLEncoder.encode("Q1","UTF-8") + "=" + URLEncoder.encode("북구", "UTF-8")); /*주소(시군구)*/
            urlBuilder.append("&" + URLEncoder.encode("QD","UTF-8") + "=" + URLEncoder.encode("D002", "UTF-8")); /*CODE_MST의'D000' 참조(D001~D029)*/

            URL url = new URL(urlBuilder.toString());
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
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            Log.i("sb",sb.toString());
            doc = loadXMLFromString(sb.toString());

        }
        catch (Exception e) {
            Log.d("hack4ork", "에러 : " + e.getMessage());
            e.printStackTrace();
        }
        return doc;
    }
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        //Log.i("document",document.getTextContent());
        String s = "";
        NodeList nodeList = document.getElementsByTagName("item");

        for (int i=0; i<nodeList.getLength(); i++) {
            //s += i + ":";
            s="";
            Node node = nodeList.item(i);
            Element fstElmnt = (Element) node;

            NodeList addrList = fstElmnt.getElementsByTagName("dutyAddr");
            Element addrElement = (Element) addrList.item(0);
            addrList = addrElement.getChildNodes();
            String strDutyAddr = addrList.item(0).getNodeValue();

            //s +=hour+"시, ";

            NodeList nameList = fstElmnt.getElementsByTagName("dutyName");
            Element nameElement = (Element) nameList.item(0);
            nameList = nameElement.getChildNodes();
            String strDutyName = nameList.item(0).getNodeValue();
            //s += "온도:" + temp+", ";

            NodeList timeList = fstElmnt.getElementsByTagName("dutyTime1c");
            Element timeElement = (Element) timeList.item(0);
            timeList = timeElement.getChildNodes();
            String strDutyTime1c = timeList.item(0).getNodeValue();
            //s += "습도:" + reh+", ";


            arrayList.add(new Hospital(strDutyAddr,strDutyName,strDutyTime1c));
        }
        context.getHosData(arrayList);
        //context.MyNotifyDataSetChanged(arrayList);
    }

    public ArrayList<Hospital> getArrayList() {
        return arrayList;
    }
}
