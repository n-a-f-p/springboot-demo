package com.example.springbootdemo.websocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/room/{roomid}/{userid}")
public class WebSocketServer {

    private Session session;
    private String userid;

    //3、房间id和用户之间的对应
    private static Map<String, List<String>> roomUserMapping =  new ConcurrentHashMap();
    //4、房间id和房间人数的对应
    private static Map<String, Integer> roomPeopleMapping =  new ConcurrentHashMap();
    //5、用户id与WebSocketServer的映射         String 为用户id
    private static ConcurrentHashMap<String, WebSocketServer> userWebMapping = new ConcurrentHashMap<String,WebSocketServer>();
    //6、房间数
    private static int roomNum = 0;



    @OnOpen
    public void onOpen(Session session,@PathParam("roomid") String roomid, @PathParam("userid") String userid){
        List<String> userList = roomUserMapping.get(roomid);
        if(userList==null){
            //房间还没创建,则创建房间
            LinkedList<String> users = new LinkedList<>();
            users.add(userid);
            roomUserMapping.put(roomid,users);
            roomNum ++;
            roomPeopleMapping.put(roomid,1);
        }else{
            //房间已经创建，则该房间加入此用户
            userList.add(userid);
            roomUserMapping.put(roomid,userList);
            roomPeopleMapping.put(roomid,roomPeopleMapping.get(roomid)+1);
        }
        this.session = session;
        this.userid = userid;
        userWebMapping.put(userid,this);
    }


/**
 * @Autowired
 *     private KafkaTemplate<String, Object> kafkaTemplate;
 *
 *     public static Map<String, List<String>> topicUsernameMap = new HashMap<>();
 *     public static Map<String, Session> usernameSessionMap = new HashMap<>();
 *
 *     @OnOpen
 *     public void onOpen(Session session, @PathParam("username") String username, @PathParam("topic") String topic){
 *         System.out.println(username+"连接成功");
 *         List<String> userList = topicUsernameMap.get(username);
 *         if(userList == null){
 *             userList = new ArrayList<>();
 *             userList.add(username);
 *         }else {
 *             userList.add(username);
 *         }
 *         usernameSessionMap.put(username, session);
 *     }
 *
 *     @OnMessage
 *     public void onMessage(String message, Session session){
 *
 *     }
 *
 *     @OnClose
 *     public void onClose(Session session){
 *
 *     }
 *
 *     @OnError
 *     public void onError(Session session){
 *
 *     }
 *
 *     @KafkaListener(topics = "alert", groupId = "admin1")
 *     public void sendImgUrl(ConsumerRecord<String, String> record, Acknowledgment ack) throws IOException {
 *         String value = record.value();
 *         Session session = usernameSessionMap.get(topicUsernameMap.get("alert"));
 *         session.getBasicRemote().sendText(value);
 *     }
 */


}
