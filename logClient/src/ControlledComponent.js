import React from "react";

export default class ControlledComponent extends React.Component {
    componentDidMount() {
        this.props.controller.componentDidMount(this);
    }

    componentDidUpdate() {
        this.props.controller.componentWillUnmount(this);
    }

    componentWillReceiveProps(newProps) {
        this.props.controller.componentWillUnmount(this);
        newProps.controller.componentDidMount(this);
    }

    // eslint-disable-next-line
    componentDidUpdate(prevProps, prevState) {
    }
}
