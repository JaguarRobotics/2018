import React from "react";
import Toolbar from "./Toolbar";
import FieldView from "./FieldView";
import ViewTransformation from "./ViewTransformation";
import "./App.css";

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.transform = new ViewTransformation();
    }

    render() {
        return (
            <div className="app">
                <div className="app-flex-static">
                    <Toolbar transform={this.transform} />
                </div>
                <div className="app-flex-grow">
                    <FieldView transform={this.transform} />
                </div>
            </div>
        );
    }
}
