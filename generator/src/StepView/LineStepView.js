import React from "react";

export default class LineStepView extends React.Component {
    constructor(props) {
        super(props);
        this.handleDistanceChange = this.handleDistanceChange.bind(this);
        this.handleDirectionChange = this.handleDirectionChange.bind(this);
    }

    sign(val) {
        return val >= 0 ? 1 : -1;
    }

    handleDistanceChange(ev) {
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
                <label className="label">Distance:</label>
                <div className="input">
                    <input type="number" value={Math.abs(this.props.step.arg)} onChange={this.handleDistanceChange} />
                    <label className="unit">in.</label>
                </div>
                <label className="label">Direction:</label>
                <select value={this.sign(this.props.step.arg)} onChange={this.handleDirectionChange}>
                    <option value={1}>
                        Forward
                    </option>
                    <option value={-1}>
                        Backward
                    </option>
                </select>
            </div>
        );
    }
}
