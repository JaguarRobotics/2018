import React from "react";
import config from "../game/config.json";

export default class CustomStepView extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeRaw = this.handleChangeRaw.bind(this);
    }

    handleChange(ev) {
        if (ev.target.id !== this.props.step.arg.id) {
            this.props.step.arg = {
                "id": parseInt(ev.target.value, 10)
            };
            this.props.routes.fireUpdate();
        }
    }

    handleChangeRaw(ev) {
        this.props.step.arg.data = ev.target.value;
    }

    render() {
        let args;
        if (config.customCommands[this.props.step.arg.id].data && config.customCommands[this.props.step.arg.id].data.type) {
            switch (config.customCommands[this.props.step.arg.id].data.type) {
                case "select":
                    if (!this.props.step.arg.data) {
                        this.props.step.arg.data = config.customCommands[this.props.step.arg.id].data.values[0];
                    }
                    args = (
                        <div>
                            <label className="label">
                                {config.customCommands[this.props.step.arg.id].data.name}
                            </label>
                            <select value={this.props.step.arg.data} onChange={this.handleChangeRaw}>
                                {config.customCommands[this.props.step.arg.id].data.values.map((val, i) => (
                                    <option key={`option-${i}`} value={val}>
                                        {val}
                                    </option>
                                ))}
                            </select>
                        </div>
                    );
                    break;
                case "number":
                    args = (
                        <div>
                            <label className="label">
                                {config.customCommands[this.props.step.arg.id].data.name}
                            </label>
                            <div className="input">
                                <input type="number" value={this.props.step.arg.data} onChange={this.handleChangeRaw} />
                                <label className="unit">
                                    {config.customCommands[this.props.step.arg.id].data.unit}
                                </label>
                            </div>
                        </div>
                    );
                    break;
                default:
                    args = (
                        <div>
                            <label className="label">
                                {config.customCommands[this.props.step.arg.id].data.name}
                            </label>
                            <textarea value={this.props.step.arg.data} onChange={this.handleChangeRaw} />
                        </div>
                    );
                    break;
            }
        }
        return (
            <div>
                <label className="label">Command:</label>
                <select value={this.props.step.arg.id} onChange={this.handleChange}>
                    {config.customCommands.map((cmd, i) => (
                        <option key={`cmd-${i}`} value={cmd.id}>
                            {cmd.name}
                        </option>
                    ))}
                </select>
                {args}
            </div>
        );
    }
}
