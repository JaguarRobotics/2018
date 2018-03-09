import React from "react";
import config from "../game/config.json";
import "./Line.css";

export default class Line extends React.Component {
    render() {
        return (
            <div className="line" style={{
                "width": `${this.props.step.arg / config.scale * this.props.fieldWidth}px`
            }}>
                <div style={{
                    "left": `${this.props.step.arg / config.scale * this.props.fieldWidth}px`
                }}>
                    {this.props.children}
                </div>
            </div>
        );
    }
}
