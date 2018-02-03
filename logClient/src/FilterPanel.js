import React from "react";
import ControlledComponent from "./ControlledComponent";
import LoggingLevel from "./LoggingLevel";
import "./FilterPanel.css";

class LevelFilter extends ControlledComponent {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        this.props.controller.setLevelFilter(this.props.levelNum, !this.props.controller.levelFilters[this.props.levelNum]);
    }

    render() {
        return (
            <li className={`${this.props.controller.levelFilters[this.props.levelNum] && "active"} level-${this.props.level}`} onClick={this.handleClick}>
                {this.props.level}
            </li>
        )
    }
}

export default class FilterPanel extends ControlledComponent {
    render() {
        return (
            <div className="filter-container">
                <div className="padding" />
                <div className={`filter-panel ${this.props.controller.settingsVisible && "active"}`}>
                    <h1>Filters:</h1>
                    <ul>
                        {LoggingLevel.toString.map((level, i) => (
                            <LevelFilter key={`level-${i}`} controller={this.props.controller} level={level} levelNum={LoggingLevel[level]} />
                        ))}
                    </ul>
                </div>
                <div className="padding" />
            </div>
        );
    }
}
