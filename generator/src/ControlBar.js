import React from "react";
import "./ControlBar.css";

export default class ControlBar extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = {
            "matchValue": -1,
            "versionValue": -1
        };
    }

    handleChange(ev) {
        const value = parseInt(ev.target.value, 10);
        switch (ev.target) {
            case this.matchSelect:
                switch (value) {
                    case -2:
                        this.props.prompt("Please enter a match name:", str => {
                            if (str) {
                                this.props.routes.selected = this.props.routes.addMatch(str);
                                this.setState({
                                    "matchValue": this.props.routes.getMatchNames().indexOf(str),
                                    "versionValue": -1
                                });
                                this.props.routes.fireUpdate();
                            }
                        });
                        break;
                    case -3:
                        this.props.routes.removeMatch(this.props.routes.getMatchNames()[this.state.matchValue]);
                        this.props.routes.selected = null;
                        this.setState({
                            "matchValue": -1,
                            "versionValue": -1
                        });
                        this.props.routes.fireUpdate();
                        break;
                    case -4:
                        this.props.prompt("Please enter duplicated match name:", str => {
                            if (str) {
                                this.props.routes.selected = this.props.routes.addMatch(str, this.props.routes.selected.toPlainObject());
                                this.setState({
                                    "matchValue": this.props.routes.getMatchNames().indexOf(str),
                                    "versionValue": -1
                                });
                                this.props.routes.fireUpdate();
                            }
                        });
                        break;
                    case -5:
                        this.props.prompt("Please enter new match name:", str => {
                            if (str) {
                                this.props.routes.renameMatch(this.props.routes.getMatchNames()[this.state.matchValue], str);
                                this.props.routes.fireUpdate();
                            }
                        });
                        break;
                    default:
                        this.props.routes.selected = this.props.routes.getMatch(this.props.routes.getMatchNames()[value]);
                        this.setState({
                            "matchValue": value,
                            "versionValue": -1
                        });
                        this.props.routes.fireUpdate();
                        break;
                }
                break;
            case this.versionSelect:
                switch (value) {
                    case -2:
                        this.props.routes.selected.selectedVersion = this.props.routes.selected.addVersion();
                        this.setState({
                            "versionValue": this.props.routes.selected.versions.length - 1
                        });
                        this.props.routes.fireUpdate();
                        break;
                    case -3:
                        this.props.routes.selected.versions.splice(this.state.versionValue, 1);
                        this.props.routes.selected.selectedVersion = null;
                        this.setState({
                            "versionValue": -1
                        });
                        this.props.routes.fireUpdate();
                        break;
                    case -4:
                        this.props.routes.selected.selectedVersion = this.props.routes.selected.addVersion(this.props.routes.selected.selectedVersion.toPlainObject());
                        this.setState({
                            "versionValue": this.props.routes.selected.versions.length - 1
                        });
                        this.props.routes.fireUpdate();
                        break;
                    default:
                        this.setState({
                            "versionValue": value
                        });
                        this.props.routes.selected.selectedVersion = this.props.routes.selected.versions[value];
                        this.props.routes.fireUpdate();
                        break;
                }
                break;
            case this.dataSelect:
                switch (value) {
                    case -2:
                        this.props.prompt("Please enter the supported game data:", str => {
                            if (str) {
                                this.props.routes.selected.selectedVersion.supports.push(str);
                                this.props.routes.fireUpdate();
                            }
                        });
                        break;
                    default:
                        this.props.routes.selected.selectedVersion.supports.splice(value, 1);
                        this.props.routes.fireUpdate();
                        break;
                }
                break;
            default:
                break;
        }
    }

    render() {
        return (
            <div className="control-bar">
                <div className="controls">
                    <label>Match:</label>
                    <select value={this.state.matchValue} onChange={this.handleChange} ref={el => this.matchSelect = el}>
                        {this.state.matchValue === -1 && <option value={-1} disabled />}
                        {this.props.routes.getMatchNames().map((match, i) => (
                            <option value={i} key={`option-${i}`}>
                                {match}
                            </option>
                        ))}
                        <option value={-2}>
                            + Add
                        </option>
                        {this.props.routes.selected && [
                            (
                                <option value={-4} key="duplicate">
                                    + Duplicate "{this.props.routes.getMatchNames()[this.state.matchValue]}"
                                </option>
                            ),
                            (
                                <option value={-5} key="rename">
                                    | Rename "{this.props.routes.getMatchNames()[this.state.matchValue]}"
                                </option>
                            ),
                            (
                                <option value={-3} key="remove">
                                    - Remove "{this.props.routes.getMatchNames()[this.state.matchValue]}"
                                </option>
                            )
                        ]}
                    </select>
                </div>
                <div className="padding" />
                <div className="controls">
                    <label>Version:</label>
                    <select value={this.state.versionValue} onChange={this.handleChange} ref={el => this.versionSelect = el}>
                        {this.props.routes.selected && [
                            this.state.versionValue === -1 && <option value={-1} disabled key="empty" />
                        ].concat(this.props.routes.selected.versions.map((val, i) => (
                            <option value={i} key={`option-${i}`}>
                                Version #{i + 1}
                            </option>
                        ))).concat([
                            <option value={-2} key="add">+ Add</option>
                        ]).concat(this.props.routes.selected.selectedVersion ? [
                            (
                                <option value={-4} key="duplicate">
                                    + Duplicate version #{this.state.versionValue + 1}
                                </option>
                            ),
                            (
                                <option value={-3} key="remove">
                                    - Remove version #{this.state.versionValue + 1}
                                </option>
                            )
                        ] : [])}
                    </select>
                </div>
                <div className="padding" />
                <div className="controls">
                    <label>Game Data:</label>
                    <select value={-1} onChange={this.handleChange} ref={el => this.dataSelect = el}>
                        {this.props.routes.selected && this.props.routes.selected.selectedVersion && [
                            <option value={-1} disabled key="title">Click to View</option>
                        ].concat(this.props.routes.selected.selectedVersion.supports.map((version, i) => (
                            <option value={i} key={`data-${i}`}>
                                {version}
                            </option>
                        ))).concat(
                            <option value={-2} key="add">+ Add</option>
                        )}
                    </select>
                </div>
            </div>
        );
    }
}
