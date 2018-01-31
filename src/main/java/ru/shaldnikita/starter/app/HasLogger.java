package ru.shaldnikita.starter.app;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author n.shaldenkov on 19.11.2017
 */
public interface HasLogger {
    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
