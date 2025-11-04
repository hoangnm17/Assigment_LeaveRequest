<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.*, java.time.format.DateTimeFormatter, java.util.*" %>

<%
    LocalDate start = (LocalDate) request.getAttribute("weekStart");
    LocalDate end = (LocalDate) request.getAttribute("weekEnd");

    // Tạo danh sách ngày trong tuần hiện tại
    List<LocalDate> weekDays = new ArrayList<>();
    for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
        weekDays.add(d);
    }
    request.setAttribute("weekDays", weekDays);

    // ----- Chuẩn bị danh sách tuần của năm hiện tại -----
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

<html>
<head>
    <title>Agenda</title>
    <style>
        body { font-family: sans-serif; }
        table {
            border-collapse: collapse;
            margin-top: 20px;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px 12px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .work { background-color: #90ee90; }
        .leave { background-color: #ff7f7f; }
        .weekend { background-color: #d3d3d3; }
        .none { background-color: #ffffff; }
        select {
            padding: 4px;
            margin: 4px;
        }
    </style>
    <script>
        function updateAgenda() {
            const year = document.getElementById('year').value;
            const week = document.getElementById('week').value;
            if (week)
                window.location.href = 'agenda?weekStart=' + week;
        }
        function reloadWeeks() {
            // Khi đổi năm -> gửi lại trang với tuần đầu tiên của năm đó
            const year = document.getElementById('year').value;
            window.location.href = 'agenda?weekStart=' + year + '-01-01';
        }
    </script>
</head>
<body>
    
    <h2>📅 Lịch làm việc tuần: ${weekStart} → ${weekEnd}</h2>

    <div>
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
</body>
</html>
