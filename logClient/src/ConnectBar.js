import React from "react";
import ControlledComponent from "./ControlledComponent";
import "./ConnectBar.css";

export default class ConnectBar extends ControlledComponent {
    render() {
        return (
            <div className={`connect-bar ${this.props.controller.settingsVisible && "active"}`}>
                <label htmlFor="ip" className="ip">IP Address:</label>
                <label htmlFor="port" className="port">Port:</label>
                <input type="text" className="ip" name="ip" placeholder="0.0.0.0" />
                <input type="number" className="port" name="port" placeholder="5800" />
            </div>
        );
    }
}
