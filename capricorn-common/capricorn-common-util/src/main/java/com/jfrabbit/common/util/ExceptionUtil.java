package com.jfrabbit.common.util;

import java.util.Optional;

public class ExceptionUtil {

    public static String getErrorMsg(Throwable throwable) {

        StringBuilder builder = new StringBuilder();

        builder.append("\nException:\n\t").append(throwable.getClass().getName()).append("\n");

        Optional<Throwable> optional = Optional.of(throwable);

        optional.map(Throwable::getCause).map(arg -> builder.append("Cause:\n\t").append(getErrorMsg(arg)).append("\n"));

        optional.map(Throwable::getMessage).map(arg -> builder.append("Message:\n\t").append(arg).append("\n"));

        builder.append("StackTrace: \n");
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            builder.append("\t").append(stackTraceElement.toString()).append("\n");
        }

        return builder.toString();
    }
}
