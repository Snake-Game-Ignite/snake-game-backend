let tileSize=32
let boardSize=6

function connect() {
	ws = new WebSocket('ws://localhost:8080/ws');
	
	ws.onopen = function(e) {
		  console.log("[open] Connection established");
		  setConnected(true);
	//	  socket.send("My name is John");
	};
	
	ws.onmessage = function(event){
		console.log("received:"+event.data)
		showGreeting(event.data);

		drawPlayfield(JSON.parse(event.data))
	}
	
	ws.onclose = function(event) {
	  if (event.wasClean) {
	    console.log('[close] Connection closed cleanly');
	  } else {
	    // e.g. server process killed or network down
	    // event.code is usually 1006 in this case
	    alert('[close] Connection died, code=${event.code} reason=${event.reason}');
	    setConnected(false);
	  }
	}
	
	ws.onerror = function(error) {
	  console.log('[error]');
	};
	
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}


function sendName() {
    ws.send($("#name").val());
}

function clearPlayfield() {
	var  can=document.getElementById("playfield");
	var ctx=can.getContext("2d");
	ctx.clearRect(0,0,192,192);
}

function drawPlayfield(gamestate) {
	clearPlayfield();

	var  can=document.getElementById("playfield");
	var ctx=can.getContext("2d");
	
	var player1=gamestate.snakes['player1']
	ctx.fillStyle="rgba(150,255,60,0.8)";
	player1.forEach( p => {
		ctx.fillRect(p.x*tileSize,p.y*tileSize,tileSize,tileSize)
	})

	var fruits=gamestate.fruits
	ctx.fillStyle="rgba(255,40,80,0.4)";
	fruits.forEach( p => {
		ctx.fillRect(p.x*tileSize,p.y*tileSize,tileSize,tileSize)
	})
	
}

function sendMoveLeft() {
    ws.send( '{"playerId": "player1", "direction":0}'  );
}

function sendMoveRight() {
    ws.send( '{"playerId": "player1", "direction":2}'  );
}

function sendMoveUp() {
    ws.send( '{"playerId": "player1", "direction":3}'  );
}

function sendMoveDown() {
    ws.send( '{"playerId": "player1", "direction":1}'  );
}


function showGreeting(message) {
    $("#greetings").append(" " + message + "");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#left" ).click(() => sendMoveLeft());
    $( "#right" ).click(() => sendMoveRight());
    $( "#up" ).click(() => sendMoveUp());
    $( "#down" ).click(() => sendMoveDown());

});