import "./LoggingLevel.css";

const LoggingLevel = {
    "FATAL": 0,
    "ERROR": 1,
    "STDERR": 2,
    "WARN": 3,
    "STDOUT": 4,
    "INFO": 5,
    "DEBUG": 6,
    "TRACE": 7,
    "toString": [
        "FATAL",
        "ERROR",
        "STDERR",
        "WARN",
        "STDOUT",
        "INFO",
        "DEBUG",
        "TRACE"
    ],
    "maxLength": 0
};

LoggingLevel.toString.forEach(level => LoggingLevel.maxLength = Math.max(LoggingLevel.maxLength, level.length));

export default LoggingLevel;
