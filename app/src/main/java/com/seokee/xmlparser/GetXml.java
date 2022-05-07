package com.seokee.xmlparser;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GetXml extends Thread{

    private MainActivity activity;
    private ArrayList<Hospital> hospitalList;
    public GetXml(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void run() {

        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=iXusHy2%2FZz7qRhjkwpWBlGWiJaRpGZgroGBlBBow45q4oibueEQnnwMUkAUsOVG3PSlRqQKOYf2SGRxHRJAqoQ%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("QD", "UTF-8") + "=" + URLEncoder.encode("D002", "UTF-8")); /*CODE_MST의'D000' 참조(D001~D029)*/
            urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode("대구광역시", "UTF-8")); /*주소(시도)*/
            urlBuilder.append("&" + URLEncoder.encode("Q1", "UTF-8") + "=" + URLEncoder.encode("북구", "UTF-8")); /*주소(시군구)*/

            URL url = new URL(urlBuilder.toString());

            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlPullParserFactory.newPullParser();

            InputStream is = url.openStream();
            parser.setInput(new InputStreamReader(is, "UTF-8"));
            String tagName="";

            //event type얻어오기
            int eventType = parser.getEventType();

            Hospital hospital = new Hospital();
            hospitalList = new ArrayList<>();
            //xml문서의 끝까지 읽기
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //태그가 시작
                    case XmlPullParser.START_TAG:
                        tagName=parser.getName();
                        if (parser.getName().equals("busLocationList")) {
                            //객체 생성
                            hospital=new Hospital();
                        }
                        break;
                    //태그의 끝
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("busLocationList")) {
                            //객체를 리스트에 추가
                            hospitalList.add(hospital);
                        }
                        break;
                    //태그 안의 텍스트
                    case XmlPullParser.TEXT:

                        switch(tagName) {
                            case "dutyAddr":{
                                hospital.setDutyAddr(parser.getText());
                                break;
                            }
                            case "dutyName":{
                                hospital.setDutyName(parser.getText());
                                break;
                            }
                            case "dutyTime1c":{
                                hospital.setDutyTime1c(parser.getText());
                                break;
                            }
                        }
                        break;
                }
                //다음으로 이동
                eventType = parser.next();
            }
        activity.arrayList = hospitalList;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}