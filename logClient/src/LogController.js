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
        this.shortLogger = logger.length < shortLoggerLength ? logger.padStart(shortLoggerLength, "\u00a0") : `...${logger.substr(logger.length - shortLoggerLength + 3)}`;
    }
}

export default class LogController {
    constructor() {
        this.mountedComponents = [];
        this.messages = [];
        this.settingsVisible = true;
        this.levelFilters = LoggingLevel.toString.map(() => true);
        let i = 0;
        setInterval(() => {
            this.messageReceived(new Message(`Hello, world #${++i}!`, new Date().getTime(), LoggingLevel.STDOUT, "LogController.Hello"));
        }, 1000);
        setInterval(() => {
            this.messageReceived(new Message("This is a trace message", new Date().getTime(), LoggingLevel.TRACE, "LogController"));
            this.messageReceived(new Message("This is a debug message", new Date().getTime(), LoggingLevel.DEBUG, "LogController"));
            this.messageReceived(new Message("This is a info message", new Date().getTime(), LoggingLevel.INFO, "LogController"));
            this.messageReceived(new Message("This is a stdout message", new Date().getTime(), LoggingLevel.STDOUT, "LogController"));
            this.messageReceived(new Message("This is a warn message", new Date().getTime(), LoggingLevel.WARN, "LogController"));
            this.messageReceived(new Message("This is a stderr message", new Date().getTime(), LoggingLevel.STDERR, "LogController"));
            this.messageReceived(new Message("This is a error message", new Date().getTime(), LoggingLevel.ERROR, "LogController"));
            this.messageReceived(new Message("This is a fatal message", new Date().getTime(), LoggingLevel.FATAL, "LogController"));
        }, 12345);
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
}
