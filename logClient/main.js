const electron = require("electron");
const path = require("path");
const url = require("url");

let mainWindow;

function createWindow() {
    mainWindow = new electron.BrowserWindow({
        width: 800,
        height: 600
    });
    mainWindow.loadURL(url.format({
        pathname: path.join(__dirname, "build", "index.html"),
        protocol: "file:",
        slashes: true
    }));
    mainWindow.on("closed", function() {
        mainWindow = null;
    });
}

electron.app.on("ready", createWindow);

electron.app.on("window-all-closed", function() {
    if (process.platform !== "darwin") {
        electron.app.quit();
    }
});

electron.app.on("activate", function() {
    if (mainWindow === null) {
        createWindow();
    }
});
