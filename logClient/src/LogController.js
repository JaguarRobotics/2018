import LoggingLevel from "./LoggingLevel";
const shortLoggerLength = 16;

class Message {
    constructor(message, date, level, logger) {
        this.message = message;
        const d = new Date(date);
        this.date = `${d.getHours().toString().padStart(2, "0")}:${d.getMinutes().toString().padStart(2, "0")}:${d.getSeconds().toString().padStart(2, "0")}.${d.getMilliseconds().toString().padStart(3, "0")}`
        this.level = level;
        this.levelStr = LoggingLevel.toString[level];
        this.levelStrPad = this.levelStr.padEnd(LoggingLevel.maxLength, "\u00a0");
        this.logger = logger;
        this.shortLogger = logger.length <= shortLoggerLength ? logger.padStart(shortLoggerLength, "\u00a0") : `...${logger.substr(logger.length - shortLoggerLength + 3)}`;
    }
}

export default class LogController {
    constructor() {
        this.mountedComponents = [];
        this.messages = [];
        this.settingsVisible = true;
        this.levelFilters = LoggingLevel.toString.map(() => true);
        this.ip = "127.0.0.1";
        this.port = 5800;
        window.addMessageListener((message, date, level, logger) => {
            this.messageReceived(new Message(message, date, level, logger));
        });
        this.server = window.connectServer(this.ip, this.port);
    }

    triggerUpdates() {
        this.mountedComponents.forEach(component => component.forceUpdate());
    }

    componentDidMount(component) {
        if (this.mountedComponents.indexOf(component) < 0) {
            this.mountedComponents.push(component);
        }
    }

    componentWillUnmount(component) {
        const index = this.mountedComponents.indexOf(component);
        if (index >= 0) {
            this.mountedComponents.splice(index, 1);
        }
    }

    messageReceived(message) {
        this.messages.push(message);
        this.triggerUpdates();
    }

    toggleSettingsVisibility() {
        this.settingsVisible = !this.settingsVisible;
        this.triggerUpdates();
    }

    setLevelFilter(level, value) {
        this.levelFilters[level] = value;
        this.triggerUpdates();
    }

    setServer(ip, port) {
        this.ip = ip;
        this.port = port;
        this.triggerUpdates();
        window.disconnectServer(this.server);
        this.server = window.connectServer(ip, port);
    }
}
