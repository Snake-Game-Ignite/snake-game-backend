# snake-game-backend

1. mvn clean install
2. Start the app

For the calls:

1. Snake move:
   - URL: http://localhost:8080/api/snake/move
   - Body(example): {"playerId": "player1", "direction":0}
3. Get state:
   - http://localhost:8080/api/snake/state
4. reset
   - http://localhost:8080/api/snake/reset
