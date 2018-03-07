import React from "react";
import Tool from "./Tool";
import ToolbarSpace from "./ToolbarSpace";
import CustomBlack from "./images/ic_extension_black_24dp_2x.png";
import CustomWhite from "./images/ic_extension_white_24dp_2x.png";
import LineBlack from "./images/ic_linear_scale_black_24dp_2x.png";
import LineWhite from "./images/ic_linear_scale_white_24dp_2x.png";
import MoveBlack from "./images/ic_open_with_black_24dp_2x.png";
import MoveWhite from "./images/ic_open_with_white_24dp_2x.png";
import RotateScreenBlack from "./images/ic_rotate_90_degrees_ccw_black_24dp_2x.png";
import RotateScreenWhite from "./images/ic_rotate_90_degrees_ccw_white_24dp_2x.png";
import RotateBlack from "./images/ic_rotate_left_black_24dp_2x.png";
import RotateWhite from "./images/ic_rotate_left_white_24dp_2x.png";
import ResetBlack from "./images/ic_settings_overscan_black_24dp_2x.png";
import ResetWhite from "./images/ic_settings_overscan_white_24dp_2x.png";
import SleepBlack from "./images/ic_timer_black_24dp_2x.png";
import SleepWhite from "./images/ic_timer_white_24dp_2x.png";
import ZoomInBlack from "./images/ic_zoom_in_black_24dp_2x.png";
import ZoomInWhite from "./images/ic_zoom_in_white_24dp_2x.png";
import ZoomOutBlack from "./images/ic_zoom_out_black_24dp_2x.png";
import ZoomOutWhite from "./images/ic_zoom_out_white_24dp_2x.png";
import "./Toolbar.css";

const ZOOM_FACTOR = 1.1;

export default class Toolbar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            "selectedTool": 0
        };
    }

    handleClick(tool) {
        tool = this.state.selectedTool === tool ? 0 : tool;
        switch (tool) {
            case Toolbar.TOOLS.Reset:
                this.props.transform.fireReset();
                setTimeout(this.handleClick.bind(this, 0), 100);
                break;
            case Toolbar.TOOLS.RotateScreen:
                this.props.transform.rotation += 90;
                this.props.transform.fireUpdate();
                setTimeout(this.handleClick.bind(this, 0), 100);
                break;
            case Toolbar.TOOLS.ZoomIn:
                this.props.transform.scaleX *= ZOOM_FACTOR;
                this.props.transform.scaleY *= ZOOM_FACTOR;
                this.props.transform.fireUpdate();
                setTimeout(this.handleClick.bind(this, 0), 100);
                break;
            case Toolbar.TOOLS.ZoomOut:
                this.props.transform.scaleX /= ZOOM_FACTOR;
                this.props.transform.scaleY /= ZOOM_FACTOR;
                this.props.transform.fireUpdate();
                setTimeout(this.handleClick.bind(this, 0), 100);
                break;
            default:
                break;
        }
        this.setState({
            "selectedTool": tool
        });
        this.props.transform.tool = tool;
        this.props.transform.fireUpdate();
    }

    render() {
        return (
            <div className="toolbar">
                <div className="padding" />
                <div className="tools">
                    <Tool whiteImage={MoveWhite}
                          blackImage={MoveBlack}
                          alt="Move"
                          active={this.state.selectedTool === Toolbar.TOOLS.Move}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.Move)} />
                    <Tool whiteImage={RotateScreenWhite}
                          blackImage={RotateScreenBlack}
                          alt="Rotate Screen"
                          active={this.state.selectedTool === Toolbar.TOOLS.RotateScreen}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.RotateScreen)} />
                    <Tool whiteImage={ZoomInWhite}
                          blackImage={ZoomInBlack}
                          alt="Zoom In"
                          active={this.state.selectedTool === Toolbar.TOOLS.ZoomIn}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.ZoomIn)} />
                    <Tool whiteImage={ZoomOutWhite}
                          blackImage={ZoomOutBlack}
                          alt="Zoom Out"
                          active={this.state.selectedTool === Toolbar.TOOLS.ZoomOut}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.ZoomOut)} />
                    <Tool whiteImage={ResetWhite}
                          blackImage={ResetBlack}
                          alt="Reset View"
                          active={this.state.selectedTool === Toolbar.TOOLS.Reset}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.Reset)} />
                    <ToolbarSpace />
                    <Tool whiteImage={SleepWhite}
                          blackImage={SleepBlack}
                          alt="Sleep"
                          active={this.state.selectedTool === Toolbar.TOOLS.Sleep}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.Sleep)} />
                    <Tool whiteImage={LineWhite}
                          blackImage={LineBlack}
                          alt="Drive"
                          active={this.state.selectedTool === Toolbar.TOOLS.Line}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.Line)} />
                    <Tool whiteImage={RotateWhite}
                          blackImage={RotateBlack}
                          alt="Turn"
                          active={this.state.selectedTool === Toolbar.TOOLS.Rotate}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.Rotate)} />
                    <Tool whiteImage={CustomWhite}
                          blackImage={CustomBlack}
                          alt="Custom"
                          active={this.state.selectedTool === Toolbar.TOOLS.Custom}
                          onClick={this.handleClick.bind(this, Toolbar.TOOLS.Custom)} />
                </div>
                <div className="padding" />
            </div>
        );
    }
}

Toolbar.TOOLS = {
    "Move": 1,
    "RotateScreen": 2,
    "ZoomIn": 3,
    "ZoomOut": 4,
    "Reset": 5,
    "Sleep": 6,
    "Line": 7,
    "Rotate": 8,
    "Custom": 9
};
