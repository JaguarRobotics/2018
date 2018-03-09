const debug = false;

let callback;

if (window.electron) {
    callback = window.electron.ipcRenderer.send.bind(window.electron.ipcRenderer, "data");
} else {
    callback = () => {};
}

if (debug) {
    const parent = callback;
    callback = (name, value) => {
        console.log(`Saved data - Name: ${name}, Value: ${value}`);
        parent(name, value);
    };
}

window.doSave = callback;
