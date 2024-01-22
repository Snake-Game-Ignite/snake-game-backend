package com.snakegame.snakegame.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snakegame.snakegame.controller.MoveRequest;
import com.snakegame.snakegame.model.SnakeGame;

@Component
public class WebSocketCommunication {

	private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	
	private static Gson gson=new GsonBuilder().create();
	
	@Autowired
	private SnakeGameService gameService;
	
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

	/** 
	 * send an object to a specific session
	 * @param payload
	 */
	public void broadcast(WebSocketSession session, Object payload) throws IOException {
		TextMessage msg=null;
		if (payload instanceof String strPayload) {
			msg=new TextMessage(strPayload);
		} else {
			msg=new TextMessage(gson.toJson(payload));
		}
		session.sendMessage(msg);
		
	}
	
	public void connect(WebSocketSession session) throws IOException {
		sessions.add(session);
		broadcast ( session, gameService.getGameState() );
	}

	public void received(String payload) throws IOException {
		var moveRequest=gson.fromJson(payload, MoveRequest.class);
        String playerId = moveRequest.getPlayerId();
        int direction = moveRequest.getDirection();
        gameService.handleUserInput(playerId, direction);
        broadcast( gameService.getGameState());	
	}

	public void disconnect(WebSocketSession session) {
		sessions.remove(session);
		
	}
	
}
