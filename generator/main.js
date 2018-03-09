const electron = require("electron");
const path = require("path");
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

electron.ipcMain.on("data", (sender, name, value) => {
    console.log(`${name}: ${value}`);
});
