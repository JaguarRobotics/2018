import React from "react";
import AutonomousStep from "../AutonomousStep";
import Line from "./Line";
import Rotate from "./Rotate";

export default class Step extends React.Component {
    render() {
        const next = (
            <Step routes={this.props.routes}
                  steps={this.props.steps}
                  i={this.props.i + 1}
                  fieldWidth={this.props.fieldWidth}
                  fieldHeight={this.props.fieldHeight} />
        );
        if (this.props.i < this.props.steps.length) {
            const step = this.props.steps[this.props.i];
            switch (step.type) {
                case AutonomousStep.Type.Sleep:
                    return next;
                case AutonomousStep.Type.Line:
                    return (
                        <Line routes={this.props.routes}
                              step={step}
                              fieldWidth={this.props.fieldWidth}
                              fieldHeight={this.props.fieldHeight}>
                            {next}
                        </Line>
                    );
                case AutonomousStep.Type.Rotate:
                    return (
                        <Rotate routes={this.props.routes}
                              step={step}
                              fieldWidth={this.props.fieldWidth}
                              fieldHeight={this.props.fieldHeight}>
                            {next}
                        </Rotate>
                    );
                case AutonomousStep.Type.Custom:
                    return next;
                default:
                    return next;
            }
        } else {
            return <div />;
        }
    }
}
