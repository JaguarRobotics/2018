const config = require("./src/game/config.json");
const electron = require("electron");
const path = require("path");
const ssh2SftpClient = require("ssh2-sftp-client");
const url = require("url");

let mainWindow;

function createWindow() {
    mainWindow = new electron.BrowserWindow({
        "width": 800,
        "height": 600,
        "webPreferences": {
            "webSecurity": false
        }
    });
    mainWindow.loadURL(url.format({
        "pathname": path.join(__dirname, "build", "index.html"),
        "protocol": "file:",
        "slashes": true
    }));
    mainWindow.on("closed", () => {
        mainWindow = null;
    });
}

electron.app.on("ready", createWindow);
electron.app.on("window-all-closed", electron.app.quit.bind(electron.app));

let updates = [];

function startService(host) {
    const sftp = new ssh2SftpClient();
    let update;
    const error = err => {
        console.error(err);
        setTimeout(startService, 10000);
        if (update) {
            updates.splice(updates.findIndex(update), 1);
        }
    };
    sftp.connect({
        "host": host,
        "port": 22,
        "username": "lvuser",
        "password": "1"
    }).then(() =>
        sftp.mkdir("/home/lvuser/routes", true)
    ).then(() => {
		console.log(`Connected to ${host}.`);
        let isUpdating = false;
        let updateQueue = {};
        update = (name, value) => {
            updateQueue[name] = value;
            if (!isUpdating) {
                isUpdating = true;
                const loop = () => {
                    const arr = Object.getOwnPropertyNames(updateQueue);
                    if (arr.length > 0) {
                        const n = arr[0];
                        const v = updateQueue[n];
                        delete updateQueue[n];
                        sftp.put(Buffer.from(v), `/home/lvuser/routes/${n}`).then(loop, error);
                    } else {
                        isUpdating = false;
                        console.log("Sync complete.");
                    }
                };
                loop();
            }
        };
        updates.push(update);
    }).catch(error);
}

electron.ipcMain.on("data", (sender, name, value) => {
    updates.forEach(func => func(name, value));
});

startService(`10.${(config.team - (config.team % 100)) / 100}.${config.team % 100}.2`);
startService("172.22.11.2");
startService(`roboRIO-${config.team}-FRC.lan`);
startService(`roboRIO-${config.team}-FRC.frc-field.local`);
startService(`roboRIO-${config.team}-FRC.local`);
