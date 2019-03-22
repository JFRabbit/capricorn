package com.jfrabbit.common.rest.util.compare;

public interface Placeholder {

    String NULL = "NULL_PLACEHOLDER";

    String JSON_IGNORE = "${json-unit.ignore}";
    String JSON_REGEX = "${json-unit.regex}";
    String JSON_ANY_STRING = "${json-unit.any-string}";
    String JSON_ANY_BOOLEAN = "${json-unit.any-boolean}";
    String JSON_ANY_NUMBER = "${json-unit.any-number}";
}
