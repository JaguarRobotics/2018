import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import "./Proxy";
import registerServiceWorker from "./registerServiceWorker";

ReactDOM.render(<App />, document.getElementById("root"));
if (!window.electron) {
    registerServiceWorker();
}
