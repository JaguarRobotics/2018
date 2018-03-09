import React from "react";

export default class SleepStepView extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(ev) {
        this.props.step.arg = parseInt(ev.target.value, 10) || 0;
        this.props.routes.fireUpdate();
    }

    render() {
        return (
            <div>
                <label className="label">Time:</label>
                <div className="input">
                    <input type="number" value={this.props.step.arg} onChange={this.handleChange} />
                    <label className="unit">ms</label>
                </div>
            </div>
        );
    }
}
