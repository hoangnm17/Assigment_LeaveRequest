package utils;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties config = new Properties();

    public static void load(ServletContext context) {
        try (InputStream is = context.getResourceAsStream("/WEB-INF/config.properties")) {
            if (is == null) {
                throw new RuntimeException("Không tìm thấy file /WEB-INF/config.properties");
            }
            config.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file /WEB-INF/config.properties", e);
        }
    }

    public static String get(String key) {
        return config.getProperty(key);
    }
}
