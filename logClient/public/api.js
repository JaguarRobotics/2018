var serverId = 0;

if (window.require) {
    var electron = require("electron");

    function connectServer(ip, port) {
        electron.ipcRenderer.send("connectServer", ip, port, ++serverId);
        return serverId;
    }

    function disconnectServer(server) {
        electron.ipcRenderer.send("disconnectServer", server);
    }

    function addMessageListener(listener) {
        electron.ipcRenderer.on("message", function(ev, message, date, level, logger) {
            listener(message, date, level, logger);
        });
    }
} else {
    var servers = [];

    function connectServer(ip, port) {
        servers[serverId] = ip + ":" + port;
        console.log("Connecting to " + servers[serverId]);
        return serverId++;
    }

    function disconnectServer(server) {
        console.log("Disconnecting from " + servers[server]);
    }

    function addMessageListener(listener) {
        var i = 0;
        setInterval(function() {
            listener("Hello, world #" + ++i + "!", new Date().getTime(), 4, "Default");
        }, 1000);
    }
}
