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
            const messageLen = data.readInt32BE(0);
            const message = data.toString("utf8", 4, messageLen + 4);
            const date = (data.readInt32BE(messageLen + 4) * 4294967296) + data.readInt32BE(messageLen + 8);
            const level = data.readInt8(messageLen + 12);
            const loggerLen = data.readInt32BE(messageLen + 13);
            const logger = data.toString("utf8", messageLen + 17);
            ev.sender.send("message", message, date, level, logger);
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
