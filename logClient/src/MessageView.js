import React from "react";
import ControlledComponent from "./ControlledComponent";
import "./MessageView.css";

export default class MessageView extends ControlledComponent {
    constructor(props) {
        super(props);
        this.state = {
            "locked": true
        };
        this.handleScroll = this.handleScroll.bind(this);
    }

    handleScroll() {
        if (this.view.scrollTop === this.view.scrollHeight - this.view.clientHeight) {
            this.setState({
                "locked": true
            });
        } else {
            this.setState({
                "locked": false
            });
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (this.state.locked) {
            this.view.scrollTop = this.view.scrollHeight - this.view.clientHeight;
        }
    }

    render() {
        return (
            <div className="message-view" onScroll={this.handleScroll} ref={view => this.view = view}>
                {this.props.controller.messages.map((message, i) => this.props.controller.levelFilters[message.level] ? (
                    <div key={`message-${i}`} className={`message level-${message.levelStr}`}>
                        [
                        {message.date}
                        ] [
                        <abbr title={message.logger}>
                            {message.shortLogger}
                        </abbr>
                        /
                        {message.levelStrPad}
                        {"] "}
                        {message.message}
                    </div>
                ) : null)}
            </div>
        );
    }
}
