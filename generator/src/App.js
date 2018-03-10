import React from "react";
import ControlBar from "./ControlBar";
import Toolbar from "./Toolbar";
import Sidebar from "./Sidebar";
import FieldView from "./FieldView";
import ViewTransformation from "./ViewTransformation";
import RouteCollection from "./RouteCollection";
import Prompt from "./Prompt";
import "./App.css";

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.transform = new ViewTransformation();
        let json = null;
        if (localStorage.routes) {
            try {
                json = JSON.parse(localStorage.routes);
            } catch (ex) {
                console.error(ex);
            }
        }
        this.routes = new RouteCollection(json);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.routes.onUpdate = this.handleUpdate;
        this.state = {
            "prompt": {
                "message": null,
                "callback": null,
                "date": 0
            }
        };
        this.handlePrompt = this.handlePrompt.bind(this);
        window.addUpdateListener(this.handleUpdate);
    }

    handleUpdate() {
        this.forceUpdate();
        localStorage.routes = this.routes.toString();
        if (window.doSave) {
            this.routes.getMatchNames().forEach(name => {
                window.doSave(btoa(name), this.routes.getMatch(name).toSerialString());
            });
        }
    }

    handlePrompt(msg, callback) {
        this.setState({
            "prompt": {
                "message": msg,
                "callback": callback,
                "date": new Date().getTime()
            }
        });
    }

    render() {
        return (
            <div className="app">
                <div className="app-flex-static app-top">
                    <ControlBar routes={this.routes} prompt={this.handlePrompt} />
                    <Toolbar transform={this.transform} routes={this.routes} />
                </div>
                <div className="app-flex-grow app-main">
                    <div className="app-flex-static">
                        <Sidebar routes={this.routes} />
                    </div>
                    <div className="app-flex-grow">
                        <FieldView transform={this.transform} routes={this.routes} />
                    </div>
                </div>
                <Prompt message={this.state.prompt.message} callback={this.state.prompt.callback} date={this.state.prompt.date} />
            </div>
        );
    }
}
