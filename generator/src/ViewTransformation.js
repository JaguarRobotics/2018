export default class ViewTransformation {
    constructor() {
        this.tool = 0;
        this.x = 0;
        this.y = 0;
        this.scaleX = 0;
        this.scaleY = 0;
        this.rotation = 0;
        this.onUpdate = null;
        this.onReset = null;
    }

    fireUpdate() {
        if (this.onUpdate) {
            this.onUpdate();
        }
    }

    fireReset() {
        if (this.onReset) {
            this.onReset();
        }
    }
}
