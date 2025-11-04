package utils; // Hoặc package listener của bạn

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// Đánh dấu class này là một Listener
@WebListener 
public class AppLifecycleListener implements ServletContextListener {

    /**
     * Phương thức này được gọi KHI ứng dụng web khởi động.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Lấy ServletContext từ sự kiện
        ServletContext context = sce.getServletContext();
        
        // Gọi hàm load config của bạn
        try {
            ConfigLoader.load(context);
            // In ra log để xác nhận
            context.log("=========================================");
            context.log("Đã load config.properties thành công!");
            context.log("=========================================");
        } catch (Exception e) {
            context.log("LỖI NGHIÊM TRỌNG: Không thể load config.properties", e);
        }
    }

    /**
     * Phương thức này được gọi KHI ứng dụng web dừng lại.
     * (Không cần làm gì ở đây)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Có thể dọn dẹp tài nguyên ở đây nếu cần
    }
}