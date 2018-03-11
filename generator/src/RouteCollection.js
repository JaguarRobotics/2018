import AutonomousMatch from "./AutonomousMatch";

export default class RouteCollection {
    constructor(clone = null) {
        this.matches = {};
        if (clone) {
            clone.matches.forEach(mapping => {
                this.matches[mapping.key] = new AutonomousMatch(this, mapping.value);
            });
        }
        this.onUpdate = null;
        this.selected = null;
    }

    toPlainObject() {
        return {
            "matches": this.getMatchNames().map(key => {
                return {
                    "key": key,
                    "value": this.matches[key].toPlainObject()
                };
            })
        };
    }

    toString() {
        return JSON.stringify(this.toPlainObject());
    }

    fireUpdate() {
        if (this.onUpdate) {
            this.onUpdate();
        }
    }

    addMatch(name, clone = null) {
        this.matches[name] = new AutonomousMatch(this, clone);
        return this.matches[name];
    }

    removeMatch(name) {
        delete this.matches[name];
    }

    renameMatch(oldName, newName) {
        this.matches[newName] = this.matches[oldName];
        delete this.matches[oldName];
    }

    getMatchNames() {
        return Object.getOwnPropertyNames(this.matches);
    }

    getMatch(name) {
        return this.matches[name];
    }
}
