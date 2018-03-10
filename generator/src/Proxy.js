const debug = false;

if (window.electron) {
    window.doSave = window.electron.ipcRenderer.send.bind(window.electron.ipcRenderer, "data");
    window.addUpdateListener = window.electron.ipcRenderer.on.bind(window.electron.ipcRenderer, "update");
} else {
    window.doSave = () => {};
    window.addUpdateListener = () => {};
}

if (debug) {
    const parent = window.doSave;
    window.doSave = (name, value) => {
        console.log(`Saved data - Name: ${name}, Value: ${value}`);
        parent(name, value);
    };
}
