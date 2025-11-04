package utils;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties config = new Properties();

    public static void load(ServletContext context) {
        try (InputStream is = context.getResourceAsStream("/WEB-INF/config.properties")) {
            if (is != null) {
                config.load(is);
            } else {
                throw new RuntimeException("Không tìm thấy file /WEB-INF/config.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi load /WEB-INF/config.properties", e);
        }
    }

    public static String get(String key) {
        return config.getProperty(key);
    }
}
