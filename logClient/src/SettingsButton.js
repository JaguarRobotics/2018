import React from "react";
import ControlledComponent from "./ControlledComponent";
import "./SettingsButton.css";

export default class SettingsButton extends ControlledComponent {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        this.props.controller.toggleSettingsVisibility();
    }

    render() {
        return (
            <div className="settings-btn" onClick={this.handleClick}>
                <div className={`material-icons ${this.props.controller.settingsVisible && "active"}`}>
                    settings
                </div>
            </div>
        );
    }
}
