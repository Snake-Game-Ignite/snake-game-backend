package com.snakegame.snakegame.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class WebSocketCommunication {

	private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	
	private static Gson gson=new GsonBuilder().create();
	
	/** 
	 * send an object to all websocket receiver
	 * @param payload
	 */
	public void broadcast(Object payload) throws IOException {
		TextMessage msg=null;
		if (payload instanceof String strPayload) {
			msg=new TextMessage(strPayload);
		} else {
			msg=new TextMessage(gson.toJson(payload));
		}
		for(var webSocketSession : sessions) {
			webSocketSession.sendMessage(msg);
		}
	}
	
	public void addSession(WebSocketSession session) {
		sessions.add(session);
	}
	
}
