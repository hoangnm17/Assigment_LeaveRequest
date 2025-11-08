package utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            ConfigLoader.load(context);
        } catch (Exception e) {
            throw new RuntimeException("Không thể load file cấu hình.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Không cần làm gì khi ứng dụng dừng
    }
}
