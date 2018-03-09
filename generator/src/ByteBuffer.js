export default class ByteBuffer {
    constructor(byteLength) {
        this.buf = new ArrayBuffer(byteLength);
        this.view = new DataView(this.buf);
        this.off = 0;
    }

    arrayBuffer() {
        return this.buf.slice(0, this.off);
    }

    put(b) {
        this.view.setInt8(this.off++, b);
    }

    putShort(value) {
        console.log(value);
        this.view.setInt16(this.off, value, false);
        this.off += 2;
    }

    putFloat(value) {
        this.view.setFloat32(this.off, value, false);
        this.off += 4;
    }
}
