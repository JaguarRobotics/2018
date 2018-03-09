import AutonomousVersion from "./AutonomousVersion";
import ByteBuffer from "./ByteBuffer";
import config from "./game/config.json";

const MAX_FILE_SIZE = 65536;
const MODEL_VERSION = 1;

export default class AutonomousMatch {
    constructor(routes, clone = null) {
        this.routes = routes;
        if (clone) {
            this.startX = clone.startX;
            this.startY = clone.startY;
            this.startAngle = clone.startAngle;
            this.versions = clone.versions.map(version => new AutonomousVersion(this, version));
        } else {
            this.startX = 0;
            this.startY = 0;
            this.startAngle = 0;
            this.versions = [];
        }
        this.selectedVersion = null;
    }

    toPlainObject() {
        return {
            "startX": this.startX,
            "startY": this.startY,
            "startAngle": this.startAngle,
            "versions": this.versions.map(version => version.toPlainObject())
        };
    }

    serialize(ser) {
        ser.put(this.versions.length);
        this.versions.forEach(version => {
            version.serialize(ser);
        });
        ser.put(MODEL_VERSION);
        ser.putShort(config.year);
        ser.putFloat(config.scale);
    }

    toArrayBuffer() {
        let ser = new ByteBuffer(MAX_FILE_SIZE);
        this.serialize(ser);
        return ser.arrayBuffer();
    }

    toSerialString() {
        return btoa(String.fromCharCode(...new Uint8Array(this.toArrayBuffer())));
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
