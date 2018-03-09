import React from "react";

export default class RotateStepView extends React.Component {
    constructor(props) {
        super(props);
        this.handleAngleChange = this.handleAngleChange.bind(this);
        this.handleDirectionChange = this.handleDirectionChange.bind(this);
    }

    sign(val) {
        return val >= 0 ? 1 : -1;
    }

    handleAngleChange(ev) {
        this.props.step.arg = this.sign(this.props.step.arg) * parseInt(ev.target.value, 10) || 0;
        this.props.routes.fireUpdate();
    }

    handleDirectionChange(ev) {
        this.props.step.arg = Math.abs(this.props.step.arg) * parseInt(ev.target.value, 10) || 0;
        this.props.routes.fireUpdate();
    }

    render() {
        return (
            <div>
                <label className="label">Angle:</label>
                <div className="input">
                    <input type="number" value={Math.abs(this.props.step.arg)} onChange={this.handleAngleChange} />
                    <label className="unit">&deg;</label>
                </div>
                <label className="label">Direction:</label>
                <select value={this.sign(this.props.step.arg)} onChange={this.handleDirectionChange}>
                    <option value={1}>
                        Left
                    </option>
                    <option value={-1}>
                        Right
                    </option>
                </select>
            </div>
        );
    }
}
