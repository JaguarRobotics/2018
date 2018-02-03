import React from "react";
import ControlledComponent from "./ControlledComponent";
import "./ConnectBar.css";

export default class ConnectBar extends ControlledComponent {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange() {
        this.props.controller.setServer(this.ip.value, parseInt(this.port.value, 10));
    }

    render() {
        return (
            <div className={`connect-bar ${this.props.controller.settingsVisible && "active"}`}>
                <label htmlFor="ip" className="ip">IP Address:</label>
                <label htmlFor="port" className="port">Port:</label>
                <input type="text" className="ip" name="ip" placeholder="0.0.0.0" value={this.props.controller.ip} onChange={this.handleChange} ref={ip => this.ip = ip} />
                <input type="number" className="port" name="port" placeholder="5800" value={this.props.controller.port} onChange={this.handleChange} ref={port => this.port = port} />
            </div>
        );
    }
}
