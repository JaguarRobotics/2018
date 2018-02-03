const electron = require("electron");
const net = require("net");
let servers = [];

electron.ipcMain.on("connectServer", (ev, ip, port, id) => {
    try {
        const sock = servers[id] = net.connect({
            "host": ip,
            "port": port
        });
        sock.on("data", data => {
            console.log(data.toString());
            ev.sender.send("message", data.toString());
        });
    } catch (e) {
        console.error(e);
    }
});

electron.ipcMain.on("disconnectServer", (ev, id) => {
    try {
        servers[id].end();
    } catch (e) {
        console.error(e);
    }
});
