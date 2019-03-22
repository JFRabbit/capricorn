package com.jfrabbit.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author JasonZhang 2018/9/29 下午3:51
 */
public class ClassLoaderUtil {
    public static String getFilePathFromClassLoader(String fileName) throws FileNotFoundException {
        URL url = ClassLoader.getSystemClassLoader().getResource(fileName);
        if (url == null) {
            throw new FileNotFoundException(fileName);
        }
        return url.getPath();
    }

    public static String getPath(String path) {
        String ab_path;
        try {
            ab_path = getClassLoaderResource(path);
            if (ab_path.contains(".jar!")) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            ab_path = new File("").getAbsolutePath() + "/" + path;
        }
        return ab_path;
    }

    public static String getClassLoaderResource(String path) {
        URL url = ClassLoader.getSystemClassLoader().getResource(path);
        Optional<URL> optional = Optional.ofNullable(url);

        Supplier<RuntimeException> supplier = () -> new RuntimeException("Can't get Resource by: " + path);
        String root = optional.map(URL::getPath).orElseThrow(supplier);

        if (System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("linux")) {
            return root;
        }
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            return root.substring(1).replace("/", "\\");
        }

        return root;
    }
}
