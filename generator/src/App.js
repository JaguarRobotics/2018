import React from "react";
import ControlBar from "./ControlBar";
import Toolbar from "./Toolbar";
import Sidebar from "./Sidebar";
import FieldView from "./FieldView";
import ViewTransformation from "./ViewTransformation";
import RouteCollection from "./RouteCollection";
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
        this.routes.onUpdate = this.handleUpdate.bind(this);
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

    render() {
        return (
            <div className="app">
                <div className="app-flex-static app-top">
                    <ControlBar routes={this.routes} />
                    <Toolbar transform={this.transform} routes={this.routes} />
                </div>
                <div className="app-flex-grow">
                    <div className="app-main">
                        <div className="app-flex-static">
                            <Sidebar routes={this.routes} />
                        </div>
                        <div className="app-flex-grow">
                            <FieldView transform={this.transform} routes={this.routes} />
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
