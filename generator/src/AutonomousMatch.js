import AutonomousVersion from "./AutonomousVersion";

export default class AutonomousMatch {
    constructor(routes, clone = null) {
        this.routes = routes;
        if (clone) {
            this.startX = clone.startX;
            this.startY = clone.startY;
            this.versions = clone.versions.map(version => new AutonomousVersion(this, version));
        } else {
            this.startX = 0;
            this.startY = 0;
            this.versions = [];
        }
        this.selectedVersion = null;
    }

    toPlainObject() {
        return {
            "startX": this.startX,
            "startY": this.startY,
            "versions": this.versions.map(version => version.toPlainObject())
        };
    }

    fireUpdate() {
        this.routes.fireUpdate();
    }

    addVersion() {
        const version = new AutonomousVersion(this);
        this.versions.push(version);
        return version;
    }

    getVersions() {
        return this.versions;
    }
}
