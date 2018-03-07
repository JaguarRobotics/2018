import React from "react";
import Toolbar from "./Toolbar";
import field from "./game/field.png";
import "./FieldView.css";

export default class FieldView extends React.Component {
    constructor(props) {
        super(props);
        this.handleDragStart = this.handleDragStart.bind(this);
        this.handleDragOver = this.handleDragOver.bind(this);
        this.handleDrop = this.handleDrop.bind(this);
    }

    reset() {
        const iid = setInterval(() => {
            if (this.container && this.img) {
                const scaleX = this.container.clientWidth / this.img.naturalWidth;
                const scaleY = this.container.clientHeight / this.img.naturalHeight;
                const scale = Math.min(scaleX, scaleY);
                this.props.transform.rotation = 0;
                this.props.transform.scaleX = scale / scaleX * this.container.clientWidth;
                this.props.transform.scaleY = scale / scaleY * this.container.clientHeight;
                this.props.transform.x = (this.container.clientWidth - this.props.transform.scaleX) / 2;
                this.props.transform.y = (this.container.clientHeight - this.props.transform.scaleY) / 2;
                if (isFinite(scale) && this.props.transform.scaleX > 0 && this.props.transform.scaleY > 0) {
                    this.props.transform.fireUpdate();
                    clearInterval(iid);
                }
            }
        }, 50);
    }

    componentDidMount() {
        this.props.transform.onUpdate = this.forceUpdate.bind(this);
        this.props.transform.onReset = this.reset.bind(this);
        this.reset();
    }

    componentWillUnmount() {
        this.props.transform.onUpdate = null;
        this.props.transform.onReset = null;
    }

    handleDragStart(ev) {
        switch (this.props.transform.tool) {
            case Toolbar.TOOLS.Move:
                this.lastX = ev.clientX;
                this.lastY = ev.clientY;
                ev.dataTransfer.setData("text", "");
                break;
            default:
                break;
        }
    }

    handleDragOver(ev) {
        switch (this.props.transform.tool) {
            case Toolbar.TOOLS.Move:
                ev.preventDefault();
                const dx = ev.clientX - this.lastX;
                const dy = ev.clientY - this.lastY;
                this.lastX = ev.clientX;
                this.lastY = ev.clientY;
                this.props.transform.x += dx;
                this.props.transform.y += dy;
                this.props.transform.fireUpdate();
                break;
            default:
                break;
        }
    }

    handleDrop(ev) {
        switch (this.props.transform.tool) {
            case Toolbar.TOOLS.Move:
                ev.preventDefault();
                break;
            default:
                break;
        }
    }

    render() {
        return (
            <div className="field-view"
                 onDragOver={this.handleDragOver}
                 onDrop={this.handleDrop}
                 ref={el => this.container = el}>
                <div className="field-container"
                     style={{
                         "left": `${this.props.transform.x}px`,
                         "top": `${this.props.transform.y}px`,
                         "width": `${this.props.transform.scaleX}px`,
                         "height": `${this.props.transform.scaleY}px`,
                         "transform": `rotate(-${this.props.transform.rotation}deg)`
                     }}>
                    <div className="cover"
                         onDragStart={this.handleDragStart}
                         draggable />
                    <img src={field} alt="" className="background" ref={el => this.img = el} />
                </div>
            </div>
        );
    }
}
