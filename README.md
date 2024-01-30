# snake-game-backend

1. mvn clean install
2. Start the app

API contract can be found here:
snakegame/src/main/resources/ApiSpecification.yml

For the calls:

1. Snake move:
   - URL: http://localhost:8080/api/snake/move
   - Body(example): {"playerId": "player1", "direction":0}
3. Get state:
   - http://localhost:8080/api/snake/state
4. reset
   - http://localhost:8080/api/snake/reset



   
For the websocket

1) connect websocket to ws://localhost:8080/ws
2) send a move with direction of -1 to create the initial snake for the player 
   ```javascript
   ws.send( '{"playerId": "player1", "direction":null}'  );
   ```

To test:
http://localhost:8080/ in your local browser opens a webpage
to test the current websocket implementation
   
