import React from "react";
import "./Prompt.css";

export default class Prompt extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            "value": "",
            "show": false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }

    componentWillReceiveProps(props) {
        if (props.date !== this.props.date) {
            this.setState({
                "value": "",
                "show": true
            });
        }
    }

    handleChange(ev) {
        this.setState({
            "value": ev.target.value
        });
    }

    handleSubmit(ev) {
        ev.preventDefault();
        this.setState({
            "show": false
        });
        this.props.callback(this.state.value);
    }

    handleCancel() {
        this.props.callback(null);
        this.setState({
            "show": false
        });
    }

    render() {
        return this.state.show ? (
            <div className="prompt">
                <div className="container">
                    <div className="padding" />
                    <div className="box">
                        <label>
                            {this.props.message}
                        </label>
                        <form onSubmit={this.handleSubmit}>
                            <input type="text" value={this.state.value} onChange={this.handleChange} autoFocus />
                            <div className="btns">
                                <button type="button" onClick={this.handleCancel}>Cancel</button>
                                <div className="padding" />
                                <input type="button" value="OK" onClick={this.handleSubmit} />
                            </div>
                        </form>
                    </div>
                    <div className="padding" />
                </div>
            </div>
        ) : <div />;
    }
}
