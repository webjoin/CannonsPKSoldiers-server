package com.viewt.games.server.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * TODO 用户不能自主选择角色
 * 
 * 1.登陆生成UUID  返给客户端
 * 2.系统自动分配角色
 * 3.自动配对 找出等待玩家的用户
 * 4.横6竖6
 * 
 * @author Elijah
 */
@ServerEndpoint(value = "/game/ws")
public class HelloWebSocket {
	
	public HelloWebSocket(){
		System.out.println("hello--"+this);
	}
	
	static Set<HelloWebSocket> set = new HashSet<HelloWebSocket>();
	
	static Map<String,User> userMap = new HashMap<>();
	static List<String> userFreeList = new ArrayList<>(); //
	static Map<Session,String> userMap1 = new HashMap<>(); //
	
	
	Session session1 = null;  
	@OnMessage
    public void onMessage(String message, Session session)   
        throws IOException, InterruptedException {  
		System.out.println(message);
		if( "".equals(message.trim())){
			System.out.println("heartbeat-------------"+session.getId());
			return ;
		}
			JSONObject o = JSON.parseObject(message);
			int ox = o.getIntValue("ox");
			int oy = o.getIntValue("oy");
			int nx = o.getIntValue("nx");
			int ny = o.getIntValue("ny");
			String uuid = o.getString("uuid");     //sender 
			String otherUuid = o.getString("otherUuid");
			User sender = userMap.get(uuid);      //sender 
			User receiver = userMap.get(otherUuid); //receiver
			sender.setOx(ox);
			sender.setOy(oy);
			sender.setNx(nx);
			sender.setNy(ny);
			sender.setStatus(2);
			receiver.getSession().getBasicRemote().sendText(JSON.toJSONString(sender).toString());
			
		
    }
      
    @OnOpen  
    public void onOpen (Session session) {
    	this.session1 = session;
    	set.add(this);
//    	RemoteEndpoint.Async async = session.getAsyncRemote();
//    	RemoteEndpoint.Basic base = session.getBasicRemote();
//        System.out.println("Client connected------"+this+"-------"+set.size()+"---"+this.session1.getId());
    	String uuid = UUID.randomUUID().toString().replace("-", "");
    	try {
			User u = new User();
			u.setUuid(uuid);
			u.setProle(1); //1 soldier  2 cannon  player's role .
			System.out.println(JSON.toJSON(u));
			u.setSession(session);
			session.getBasicRemote().sendText(JSON.toJSONString(u).toString());
			userMap.put(uuid, u);
//			userFreeMap.put(uuid, u);
			userFreeList.add(uuid);
			userMap1.put(session,uuid);
			if (userFreeList.size() % 2 == 0) {
				String u2 = userFreeList.get(userFreeList.size()-2);  //
				String u1 = userFreeList.get(userFreeList.size()-1);  //last
				User user1 = userMap.get(u1);
				User user2 = userMap.get(u2);
				user1.setStatus(1); // playing
				user2.setStatus(1);  // playing
				user1.setProle(2);  //  // 1 soldier  2 cannon  player's role .
				user1.setTurn(1);//   prole == turn whose turn .
				user2.setTurn(1);//
				user1.setOtherUuid(u2);
				user2.setOtherUuid(u1);
				session.getBasicRemote().sendText(JSON.toJSONString(user1).toString());  // send a message to uuid1
				user2.getSession().getBasicRemote().sendText(JSON.toJSONString(user2).toString()); //send a message to uuid2
				System.out.println(user2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
  
    @OnClose  
    public void onClose (Session session) {
//        set.remove(session);
    	String uuidString = userMap1.get(session);
    	userMap1.remove(session);
    	userMap.remove(uuidString);
    	userFreeList.remove(uuidString);
    	System.out.println("onClose");
    }  

    @OnError
    public void onError(Session session, Throwable error){
    	set.remove(session);
    	 System.out.println("发生错误");
         error.printStackTrace();
    }
}


