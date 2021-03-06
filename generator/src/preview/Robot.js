import React from "react";
import Step from "./Step";
import config from "../game/config.json";
import "./Robot.css";

export default class Robot extends React.Component {
    constructor(props) {
        super(props);
        this.handleDragStart = this.handleDragStart.bind(this);
        this.handleDragOver = this.handleDragOver.bind(this);
        this.handleDoubleClick = this.handleDoubleClick.bind(this);
    }

    handleDragStart(ev) {
        this.props.onDragStart(ev, this.handleDragOver);
        this.lastX = ev.clientX;
        this.lastY = ev.clientY;
    }

    handleDragOver(ev) {
        const dx = ev.clientX - this.lastX;
        const dy = ev.clientY - this.lastY;
        this.lastX = ev.clientX;
        this.lastY = ev.clientY;
        this.props.routes.selected.startX += dx * config.scale / this.props.fieldWidth;
        this.props.routes.selected.startY += dy * config.scale / this.props.fieldWidth;
        this.props.routes.fireUpdate();
    }

    handleDoubleClick() {
        this.props.routes.selected.startAngle = 180 - this.props.routes.selected.startAngle;
        this.props.routes.fireUpdate();
    }

    render() {
        return this.props.routes.selected ? (
            <div className="robot"
                 style={{
                     "width": `${config.robot.size.frame[0] / config.scale * this.props.fieldWidth}px`,
                     "height": `${config.robot.size.frame[1] / config.scale * this.props.fieldWidth}px`,
                     "borderSize": `${config.robot.size.bumpers / config.scale * this.props.fieldWidth}px`,
                     "left": `${this.props.routes.selected.startX / config.scale * this.props.fieldWidth}px`,
                     "top": `${this.props.routes.selected.startY / config.scale * this.props.fieldWidth}px`,
                     "transform": `rotate(${this.props.routes.selected.startAngle}deg)`
                 }}
                 onDoubleClick={this.handleDoubleClick}
                 onDragStart={this.handleDragStart}
                 draggable>
                {this.props.routes.selected.selectedVersion && (
                    <Step routes={this.props.routes}
                        steps={this.props.routes.selected.selectedVersion.steps}
                        i={0}
                        fieldWidth={this.props.fieldWidth}
                        fieldHeight={this.props.fieldHeight} />
                )}
            </div>
        ) : (
            <div />
        );
    }
}
