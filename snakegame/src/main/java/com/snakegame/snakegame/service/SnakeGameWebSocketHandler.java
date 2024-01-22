package com.snakegame.snakegame.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SnakeGameWebSocketHandler extends TextWebSocketHandler {

	private WebSocketCommunication webSocket;
	
	public SnakeGameWebSocketHandler(WebSocketCommunication webSocket) {
		super();
		this.webSocket = webSocket;
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		
			// Map value = new Gson().fromJson(message.getPayload(), Map.class);
			String name=message.getPayload();
			webSocket.broadcast("Hello %s".formatted(name));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Client connection request.....");
		webSocket.addSession(session);
	}
	
}
