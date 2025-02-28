package org.mushroom.currencyexchanger.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mushroom.currencyexchanger.exception.InternalServerException;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new InternalServerException("Не возможно прочитать файл resources");
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
