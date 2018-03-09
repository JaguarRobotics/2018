import config from "./game/config.json";

export default class AutonomousStep {
    constructor(type, arg, clone = null) {
        if (clone) {
            this.type = clone.type;
            this.arg = JSON.parse(JSON.stringify(clone.arg));
        } else {
            this.type = type;
            this.arg = arg;
        }
    }

    toPlainObject() {
        return {
            "type": this.type,
            "arg": this.arg
        };
    }

    serialize(ser) {
        switch (this.type) {
            case AutonomousStep.Type.Sleep:
                ser.put(0);
                ser.putShort(this.arg);
                break;
            case AutonomousStep.Type.Line:
                ser.put(1);
                ser.putFloat(this.arg / config.scale);
                break;
            case AutonomousStep.Type.Rotate:
                ser.put(2);
                ser.putFloat(this.arg * Math.PI / 180);
                break;
            case AutonomousStep.Type.Custom:
                ser.put(3);
                ser.put(this.arg.id);
                const proto = config.customCommands.find(cmd => cmd.id === this.arg.id);
                if (proto.data) {
                    let str;
                    if (proto.data.type) {
                        str = this.arg.data;
                    } else {
                        str = proto.data;
                    }
                    ser.putShort(proto.data.length);
                    for (let i = 0; i < str.length; ++i) {
                        ser.put(str.charCodeAt(i));
                    }
                } else {
                    ser.putShort(0);
                }
                break;
            default:
                break;
        }
    }
}

AutonomousStep.Type = {
    "Sleep": 1,
    "Line": 2,
    "Rotate": 3,
    "Custom": 4
};
