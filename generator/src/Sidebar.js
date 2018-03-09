import React from "react";
import StepView from "./StepView";
import Left from "./images/ic_keyboard_arrow_left_black_24dp_2x.png";
import Right from "./images/ic_keyboard_arrow_right_black_24dp_2x.png";
import Delete from "./images/ic_delete_black_24dp_2x.png";
import ReallyDelete from "./images/ic_delete_forever_white_24dp_2x.png";
import "./Sidebar.css";

export default class Sidebar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            "expanded": false,
            "active": -1,
            "trashcan": 0
        };
        this.dragIndex = -1;
        this.handleClick = this.handleClick.bind(this);
        this.handleDrop = this.handleDrop.bind(this);
        this.handleTrashDragOver = this.handleTrashDragOver.bind(this);
        this.handleTrashDrop = this.handleTrashDrop.bind(this);
    }

    handleClick() {
        this.setState({
            "expanded": !this.state.expanded
        });
    }

    handleSelect(i) {
        if (i === this.state.active) {
            this.setState({
                "active": -1
            });
            this.props.routes.selected.selectedVersion.selectedStep = null;
        } else {
            this.setState({
                "active": i
            });
            this.props.routes.selected.selectedVersion.selectedStep = this.props.routes.selected.selectedVersion.steps[i];
        }
        this.props.routes.fireUpdate();
    }

    handleDragStart(i, ev) {
        ev.dataTransfer.setData("text", "");
        this.dragIndex = i;
        this.setState({
            "trashcan": 1
        });
    }

    handleDragOver(i, ev) {
        ev.preventDefault();
        this.setState({
            "trashcan": 1
        });
        if (i >= 0 && Math.abs(i - this.dragIndex) === 1) {
            const tmp = this.props.routes.selected.selectedVersion.steps[i];
            this.props.routes.selected.selectedVersion.steps[i] = this.props.routes.selected.selectedVersion.steps[this.dragIndex];
            this.props.routes.selected.selectedVersion.steps[this.dragIndex] = tmp;
            if (this.state.active >= i) {
                this.setState({
                    "active": this.state.active - 1
                });
            }
            this.dragIndex = i;
            this.props.routes.fireUpdate();
        }
    }

    handleDrop(ev) {
        ev.preventDefault();
        this.setState({
            "trashcan": 0
        });
    }

    handleTrashDragOver(ev) {
        ev.preventDefault();
        this.setState({
            "trashcan": 2
        });
    }

    handleTrashDrop(ev) {
        ev.preventDefault();
        this.props.routes.selected.selectedVersion.steps.splice(this.dragIndex, 1);
        this.props.routes.fireUpdate();
        this.setState({
            "trashcan": 0
        });
    }

    render() {
        return this.props.routes.selected && this.props.routes.selected.selectedVersion ? (
            <div className={`sidebar ${this.state.expanded && "active"}`}>
                <div className="container">
                    <label>Steps:</label>
                    <div className="step-container" onDrop={this.handleDrop}>
                        <div className="steps" onDragOver={this.handleDragOver.bind(this, -1)}>
                            {this.props.routes.selected.selectedVersion.steps.map((step, i) => (
                                <StepView key={`step-${i}`}
                                        active={i === this.state.active}
                                        step={step}
                                        routes={this.props.routes}
                                        onClick={this.handleSelect.bind(this, i)}
                                        onDragStart={this.handleDragStart.bind(this, i)}
                                        onDragOver={this.handleDragOver.bind(this, i)} />
                            ))}
                        </div>
                        <div className={`trashcan trashcan-${this.state.trashcan}`}
                            onDragOver={this.handleTrashDragOver}
                            onDrop={this.handleTrashDrop}>
                            <img className="delete" src={Delete} alt="Delete" />
                            <img className="really-delete" src={ReallyDelete} alt="Delete" />
                        </div>
                    </div>
                </div>
                <div className="resize" onClick={this.handleClick}>
                    <img src={this.state.expanded ? Left : Right} alt="" />
                </div>
            </div>
        ) : <div />;
    }
}
