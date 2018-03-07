import React from "react";
import "./Tool.css";

export default class Tool extends React.Component {
    render() {
        return (
            <div className={`tool ${this.props.active && "active"}`} onClick={this.props.onClick}>
                <img src={this.props.blackImage} alt={this.props.alt} />
            </div>
        );
    }
}
