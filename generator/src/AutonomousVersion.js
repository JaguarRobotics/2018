import AutonomousStep from "./AutonomousStep";

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

    fireUpdate() {
        this.match.fireUpdate();
    }
}
