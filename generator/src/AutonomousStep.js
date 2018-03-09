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
}

AutonomousStep.Type = {
    "Sleep": 1,
    "Line": 2,
    "Rotate": 3,
    "Custom": 4
};
