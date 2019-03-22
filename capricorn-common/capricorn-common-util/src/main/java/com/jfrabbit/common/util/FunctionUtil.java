package com.jfrabbit.common.util;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface FunctionUtil {

    Predicate<Object> IS_NULL = n -> n == null;
    Predicate<Object> NOT_NULL = n -> IS_NULL.negate().test(n);

    Predicate<String> STR_NULL = n -> n == null;
    Predicate<String> STR_EMPTY = n -> n.trim().length() == 0;

    Predicate<List<?>> LIST_NULL = n -> n == null;
    Predicate<List<?>> LIST_EMPTY = n -> n.size() == 0;

    Predicate<Map<?, ?>> MAP_NULL = n -> n == null;
    Predicate<Map<?, ?>> MAP_EMPTY = n -> n.size() == 0;

    Predicate<String> EMPTY_STR = n -> STR_NULL.or(STR_EMPTY).test(n);
    Predicate<String> NOT_EMPTY_STR = n -> EMPTY_STR.negate().test(n);

    Predicate<List<?>> EMPTY_LIST = n -> LIST_NULL.or(LIST_EMPTY).test(n);
    Predicate<List<?>> NOT_EMPTY_LIST = n -> EMPTY_LIST.negate().test(n);

    Predicate<Map<?, ?>> EMPTY_MAP = n -> MAP_NULL.or(MAP_EMPTY).test(n);
    Predicate<Map<?, ?>> NOT_EMPTY_MAP = n -> EMPTY_MAP.negate().test(n);

    static void NULL_THROW(Object obj, Exception e) throws Exception {
        if (IS_NULL.test(obj))
            throw e;
    }

    static void NULL_THROW(Object obj, RuntimeException e) {
        if (IS_NULL.test(obj))
            throw e;
    }

}
