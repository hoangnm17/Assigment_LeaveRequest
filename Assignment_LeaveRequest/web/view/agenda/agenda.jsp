<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.*, java.time.format.DateTimeFormatter, java.util.*" %>

<%
    LocalDate start = (LocalDate) request.getAttribute("weekStart");
    LocalDate end = (LocalDate) request.getAttribute("weekEnd");

    // Tạo danh sách ngày trong tuần
    List<LocalDate> weekDays = new ArrayList<>();
    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
        weekDays.add(d);
    }
    request.setAttribute("weekDays", weekDays);

    // Danh sách tuần của năm hiện tại
    int selectedYear = start.getYear();
    List<Map<String, String>> weeks = new ArrayList<>();
    LocalDate firstMonday = LocalDate.of(selectedYear, 1, 1)
        .with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM");

    for (LocalDate monday = firstMonday; monday.getYear() <= selectedYear; monday = monday.plusWeeks(1)) {
        LocalDate sunday = monday.plusDays(6);
        if (sunday.getYear() > selectedYear && monday.getYear() < selectedYear) break;

        Map<String, String> week = new HashMap<>();
        week.put("label", fmt.format(monday) + " → " + fmt.format(sunday));
        week.put("value", monday.toString());
        weeks.add(week);
    }
    request.setAttribute("weeks", weeks);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch làm việc</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8fafc;
            color: #333;
            display: flex;
        }

        /* Sidebar */
        .sidebar {
            width: 220px;
            background: #1e293b;
            color: white;
            height: 100vh;
            position: fixed;
            left: 0;
            top: 0;
        }

        .content {
            margin-left: 220px;
            padding: 20px 40px;
            flex-grow: 1;
        }

        h2 {
            color: #1e293b;
        }

        /* Bảng lịch */
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        th, td {
            border: 1px solid #e2e8f0;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #2563eb;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f1f5f9;
        }

        .work { background-color: #86efac; }
        .leave { background-color: #fca5a5; }
        .weekend { background-color: #cbd5e1; }
        .none { background-color: #ffffff; }

        select {
            padding: 6px 10px;
            margin: 8px;
            border-radius: 6px;
            border: 1px solid #cbd5e1;
        }

        .toolbar {
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }
        .toolbar label {
            margin-right: 6px;
            font-weight: 500;
        }

        .sidebar a {
            color: #cbd5e1;
            text-decoration: none;
            display: block;
            padding: 12px 20px;
        }
        .sidebar a:hover {
            background-color: #334155;
            color: #fff;
        }
        .sidebar h3 {
            padding: 20px;
            margin: 0;
            font-size: 18px;
            background-color: #111827;
        }
    </style>

    <script>
        function updateAgenda() {
            const week = document.getElementById('week').value;
            if (week)
                window.location.href = 'agenda?weekStart=' + week;
        }

        function reloadWeeks() {
            const year = document.getElementById('year').value;
            window.location.href = 'agenda?weekStart=' + year + '-01-01';
        }
    </script>
</head>
<body>

    <!-- Sidebar -->
    <div class="sidebar">
        <%@ include file="/common/sidebar.jsp" %>
    </div>

    <!-- Nội dung chính -->
    <div class="content">
        <h2>📅 Lịch làm việc: ${weekStart} → ${weekEnd}</h2>

        <div class="toolbar">
            <label>Năm:</label>
            <select id="year" onchange="reloadWeeks()">
                <c:forEach var="y" begin="2023" end="2026">
                    <option value="${y}" ${y == weekStart.year ? 'selected' : ''}>${y}</option>
                </c:forEach>
            </select>

            <label>Tuần:</label>
            <select id="week" onchange="updateAgenda()">
                <c:forEach var="w" items="${weeks}">
                    <option value="${w.value}" ${w.value == weekStart ? 'selected' : ''}>
                        ${w.label}
                    </option>
                </c:forEach>
            </select>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Nhân viên</th>
                    <c:forEach var="d" items="${weekDays}">
                        <th>
                            <%
                                LocalDate date = (LocalDate) pageContext.getAttribute("d");
                                String dayName = date.getDayOfWeek()
                                    .getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.forLanguageTag("vi"));
                                out.print(dayName + " (" + date.getDayOfMonth() + ")");
                            %>
                        </th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="agenda" items="${agendaList}">
                    <tr>
                        <td>${agenda.employee.employeeName}</td>
                        <c:forEach var="status" items="${agenda.weekStatus}">
                            <td class="${status.status}"></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
