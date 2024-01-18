function connect() {
	ws = new WebSocket('ws://localhost:8080/ws');
	
	ws.onopen = function(e) {
		  console.log("[open] Connection established");
		  setConnected(true);
	//	  socket.send("My name is John");
	};
	
	ws.onmessage = function(data){
		showGreeting(data.data);
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

function showGreeting(message) {
    $("#greetings").append(" " + message + "");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});