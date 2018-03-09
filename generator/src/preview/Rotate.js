import React from "react";

export default class Rotate extends React.Component {
    render() {
        return (
            <div style={{
                "transform": `rotate(${-this.props.step.arg}deg)`
            }}>
                {this.props.children}
            </div>
        );
    }
}
