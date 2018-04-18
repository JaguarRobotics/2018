import React from "react";
import ConnectBar from "./ConnectBar";
import FilterPanel from "./FilterPanel";
import LogController from "./LogController";
import MessageView from "./MessageView";
import SettingsButton from "./SettingsButton";

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            "controller": new LogController()
        };
    }

    render() {
        return (
            <div>
                <MessageView controller={this.state.controller} />
                <ConnectBar controller={this.state.controller} />
                <FilterPanel controller={this.state.controller} />
                <SettingsButton controller={this.state.controller} />
            </div>
        );
    }
}
