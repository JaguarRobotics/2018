import React from "react";
import AutonomousStep from "./AutonomousStep";
import SleepStepView from "./StepView/SleepStepView";
import LineStepView from "./StepView/LineStepView";
import RotateStepView from "./StepView/RotateStepView";
import CustomStepView from "./StepView/CustomStepView";
import config from "./game/config.json";
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
                title = `Drive ${this.props.step.arg >= 0 ? "forward" : "backward"} for ${Math.abs(this.props.step.arg)} in.`;
                body = <LineStepView step={this.props.step} routes={this.props.routes} />;
                break;
            case AutonomousStep.Type.Rotate:
                title = `Turn ${Math.abs(this.props.step.arg)}\u00b0 ${this.props.step.arg >= 0 ? "right" : "left"}`;
                body = <RotateStepView step={this.props.step} routes={this.props.routes} />;
                break;
            case AutonomousStep.Type.Custom:
                title = `${config.customCommands.find(cmd => cmd.id === this.props.step.arg.id).name}`;
                body = <CustomStepView step={this.props.step} routes={this.props.routes} />;
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
