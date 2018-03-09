import AutonomousStep from "./AutonomousStep";
import config from "./game/config.json";

export default class AutonomousVersion {
    constructor(match, clone = null) {
        this.match = match;
        if (clone) {
            this.supports = clone.supports.slice(0);
            this.steps = clone.steps.map(step => new AutonomousStep(null, null, step));
        } else {
            this.supports = [];
            this.steps = [];
        }
        this.selectedStep = null;
    }

    toPlainObject() {
        return {
            "supports": this.supports.slice(0),
            "steps": this.steps.map(step => step.toPlainObject())
        };
    }

    serialize(ser) {
        ser.put(this.supports.length);
        this.supports.forEach(config => {
            ser.put(config.length);
            for (let i = 0; i < config.length; ++i) {
                ser.put(config.charCodeAt(i));
            }
        });
        ser.put(this.steps.length);
        this.steps.forEach(step => {
            step.serialize(ser);
        });
        ser.putFloat(this.match.startX / config.scale);
        ser.putFloat(this.match.startY / config.scale);
        ser.putFloat(this.match.startAngle);
    }

    fireUpdate() {
        this.match.fireUpdate();
    }
}
