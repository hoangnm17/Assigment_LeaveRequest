package model.agenda;

import java.time.LocalDate;

// Lưu trạng thái 1 ngày
public class DailyStatus {
    private LocalDate date;
    private String status; // "none" = trắng, "work" = xanh, "leave" = đỏ

    public DailyStatus(LocalDate date, String status) {
        this.date = date;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
