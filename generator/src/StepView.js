import React from "react";
import AutonomousStep from "./AutonomousStep";
import SleepStepView from "./StepView/SleepStepView";
import "./StepView.css";

export default class StepView extends React.Component {
    render() {
        let title;
        let body;
        switch (this.props.step.type) {
            case AutonomousStep.Type.Sleep:
                title = `Sleep for ${this.props.step.arg} ms`;
                body = <SleepStepView step={this.props.step} routes={this.props.routes} />;
                break;
            case AutonomousStep.Type.Line:
                title = `Drive forward for ${this.props.step.arg} in`;
                body = null;
                break;
            case AutonomousStep.Type.Rotate:
                title = `Turn ${this.props.step.arg} deg`;
                body = null;
                break;
            case AutonomousStep.Type.Custom:
                title = `Custom step`;
                body = null;
                break;
            default:
                title = null;
                body = null;
                break;
        }
        return (
            <div className={`step ${this.props.active && "active"}`}
                 onDragStart={this.props.onDragStart}
                 onDragOver={this.props.onDragOver}
                 draggable>
                <div className="title" onClick={this.props.onClick}>
                    {title}
                </div>
                <div className="body">
                    {body}
                </div>
            </div>
        );
    }
}
