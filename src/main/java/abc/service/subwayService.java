package abc.service;

import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class subwayService {

    public JSONArray getMain(int type){
        try{
            URL url = new URL("http://swopenapi.seoul.go.kr/api/subway/6f58714f6c64686534356359587644/json/realtimeStationArrival/0/8/%ED%95%9C%EB%8C%80%EC%95%9E");
            // url : http://swopenapi.seoul.go.kr/api/subway/인증키/요청파일타입/서비스명/시작위치/끝위치/역이름
            // 2. 스트림 버퍼를 통한 URL내 HTML 읽어오기                      // 호출 개수 : page="시작번호"&perpage="마지막번호"
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            // 3. 읽어온 내용 문자열 담기
            String result = bf.readLine(); // .readLine() : 모든 문자열 읽어오기
            // 3. 읽어온 내용을 json으로 파싱 하기
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            // 1.JSONparser json데이터 넣어서 파싱  // 2.jsonobject 형 변환
            // System.out.println("rul 내용을 json 변환[ json ] : " +  jsonObject );
            JSONArray jsonArray = (JSONArray) jsonObject.get("realtimeArrivalList"); // 실제 지하철 정보 리스트
            // "data" 라는 키 요청 해서 리스트 담기
            // System.out.println("data 키 호출 해서 리스트 담기 : " +  jsonArray );
            JSONArray uplist = new JSONArray();
            JSONArray downlist = new JSONArray();
            JSONArray uplist2 = new JSONArray(); //상행선 결과값이(시간이 추가된) 담긴 리스트
            JSONArray downlist2 = new JSONArray(); //하행선 결과값이(시간이 추가된) 담긴 리스트

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject content = (JSONObject) jsonArray.get(i);
                String 상하행 = (String) content.get("updnLine");
                if (상하행.equals("상행")) {
                    uplist.add((JSONObject) jsonArray.get(i));
                } else {
                    downlist.add(jsonArray.get(i));
                }
            }
            Date date = new Date();
            // 시간 더하기
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            for (int i = 0; i < uplist.size(); i++) {
                JSONObject content = (JSONObject) uplist.get(i);
                int arriveTime = 0;
                String s = (String) content.get("arvlMsg2");
                if (s.indexOf("]") == -1) {
                    arriveTime = 0;
                }else{
                    arriveTime = Integer.parseInt(s.substring(1, s.indexOf("]"))) * 3;
                    cal.add(Calendar.MINUTE, arriveTime);
                    SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                    String time = sdformat.format(cal.getTime());
                    content.put("name2",time);
                    uplist2.add(content);
                    cal.setTime(date);
                }
            }
            for (int i = 0; i < downlist.size(); i++) {
                JSONObject content = (JSONObject) downlist.get(i);
                int arriveTime = 0;
                String s = (String) content.get("arvlMsg2");
                if (s.indexOf("]") == -1) {
                    arriveTime = 0;
                } else {
                    arriveTime = Integer.parseInt(s.substring(1, s.indexOf("]"))) * 3;
                    cal.add(Calendar.MINUTE, arriveTime);
                    SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                    String time = sdformat.format(cal.getTime());
                    content.put("name2", time);
                    downlist2.add(content);
                    cal.setTime(date);
                }
            }
            if(type ==1){
                return uplist2;

            }else if(type==2){
                return downlist2;
            }

        }catch(Exception e){
        }
        return null;
    }

    @SneakyThrows
    public JSONArray getSearch(String inputtext, int type){

        String encode= URLEncoder.encode(inputtext, "utf-8");
        String search = "http://swopenapi.seoul.go.kr/api/subway/6f58714f6c64686534356359587644/json/realtimeStationArrival/0/8/"+encode;
        URL url = new URL(search);
        // url : http://swopenapi.seoul.go.kr/api/subway/인증키/요청파일타입/서비스명/시작위치/끝위치/역이름
        // 2. 스트림 버퍼를 통한 URL내 HTML 읽어오기                      // 호출 개수 : page="시작번호"&perpage="마지막번호"
        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        // 3. 읽어온 내용 문자열 담기
        String result = bf.readLine(); // .readLine() : 모든 문자열 읽어오기
        // 3. 읽어온 내용을 json으로 파싱 하기
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        // 1.JSONparser json데이터 넣어서 파싱  // 2.jsonobject 형 변환
        // System.out.println("rul 내용을 json 변환[ json ] : " +  jsonObject );
        String qwe = String.valueOf(jsonObject.get("total"));
        if(qwe.equals("0")){
            return null;
        }
        JSONArray jsonArray = (JSONArray) jsonObject.get("realtimeArrivalList"); // 실제 지하철 정보 리스트
        // "data" 라는 키 요청 해서 리스트 담기
        // System.out.println("data 키 호출 해서 리스트 담기 : " +  jsonArray );
        JSONArray uplist = new JSONArray();  //상행 리스트
        JSONArray downlist = new JSONArray();//하행 리스트
        JSONArray outlist = new JSONArray(); //외선 리스트
        JSONArray inlist = new JSONArray();  //내선 리스트
        // 시간이 붙은 결과값들이 담긴 리스트
        JSONArray upLineList = new JSONArray(); //상행 리스트2
        JSONArray downLineList = new JSONArray(); //하행 리스트2
        JSONArray outLineList = new JSONArray(); //외선 리스트2
        JSONArray inLineList = new JSONArray(); //내선 리스트2

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject content = (JSONObject) jsonArray.get(i);
            String updnLine = (String) content.get("updnLine"); //상하행인 객체를 담기.
            //System.out.println("상하행 검색 O: " + 상하행);
            if (updnLine.equals("상행")) {
                uplist.add((JSONObject) jsonArray.get(i));
            } else if(updnLine.equals("하행")){
                downlist.add(jsonArray.get(i));
            } else if(updnLine.equals("외선")){
                outlist.add(jsonArray.get(i));
            } else{
                inlist.add(jsonArray.get(i));
            }
        }
        Date date = new Date();
        // 시간 더하기
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        for (int i = 0; i < uplist.size(); i++) {
            JSONObject content = (JSONObject) uplist.get(i);
            int arriveTime = 0; //도착예정시간
            String s = (String) content.get("arvlMsg2");
            if (s.indexOf("]") == -1) {
                arriveTime = 0;
            }else{
                arriveTime = Integer.parseInt(s.substring(1, s.indexOf("]"))) * 3;
                cal.add(Calendar.MINUTE, arriveTime);
                SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                String time = sdformat.format(cal.getTime());
                content.put("name2",time);
                upLineList.add(content);
                cal.setTime(date);
            }

        }

        for(int i = 0; i<outlist.size(); i++){
            JSONObject content = (JSONObject) outlist.get(i);
            String s = (String) content.get("arvlMsg2");
            int arriveTime = 0; //도착예정시간
            if(s.contains("분")) {
                arriveTime = Integer.parseInt(s.substring(0,1));
                cal.add(Calendar.MINUTE, arriveTime);
                SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                String time = sdformat.format(cal.getTime());
                content.put("arvlMsg2",time);
                outLineList.add(content);
                cal.setTime(date);
            }else{
                outLineList.add(content);
            }
        }


        for(int i = 0; i<inlist.size(); i++){
            JSONObject content = (JSONObject) inlist.get(i);
            String s = (String) content.get("arvlMsg2");
            int arriveTime = 0; //도착예정시간
            if(s.contains("분")) {
                arriveTime = Integer.parseInt(s.substring(0,1));
                cal.add(Calendar.MINUTE, arriveTime);
                SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                String time = sdformat.format(cal.getTime());
                content.put("arvlMsg2",time);
                inLineList.add(content);
                cal.setTime(date);
            }else{
                inLineList.add(content);
            }
        }

        for (int i = 0; i < downlist.size(); i++) {
            JSONObject content = (JSONObject) downlist.get(i);
            int arriveTime = 0;
            String s = (String) content.get("arvlMsg2");
            if (s.indexOf("]") == -1) {
                arriveTime = 0;
            } else {
                arriveTime = Integer.parseInt(s.substring(1, s.indexOf("]"))) * 3;
                cal.add(Calendar.MINUTE, arriveTime);
                SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                String time = sdformat.format(cal.getTime());
                content.put("name2", time);
                downLineList.add(content);
                cal.setTime(date);
            }
        }
        if(type == 1){
            return upLineList;
        }else if(type == 2){
            return downLineList;
        }else if(type == 3){
            return outLineList;
        }else if(type == 4){
            return inLineList;
        }
        return null;
    }

}
