package com.example.demomidtermtest;

import java.util.ArrayList;
import java.sql.*;

public class DBUtility {
    private static String user = "student";
    private static String password = "student";
    private static String connectionURL = "jdbc:mysql://localhost:3306/javatest";

    public static ArrayList<Employee> getEmployees(String... sql) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        String query = (sql.length > 0) ? sql[0] : "SELECT * FROM employee";

        try (Connection conn = DriverManager.getConnection(connectionURL, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setPhoneNumber(rs.getString("phoneNumber"));
                employee.setHireDate(rs.getDate("hireDate"));
                employee.setJobCode(rs.getString("jobCode"));
                employees.add(employee);
            }
        }
        return employees;
    }

    public static ArrayList<String> getAreaCodes() throws SQLException {
        ArrayList<String> areaCodes = new ArrayList<>();
        String query = "SELECT DISTINCT SUBSTRING(phoneNumber, 1, 3) AS areaCode FROM employee";

        try (Connection conn = DriverManager.getConnection(connectionURL, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                areaCodes.add(rs.getString("areaCode"));
            }
        }
        return areaCodes;
    }

    public static ArrayList<Employee> filterEmployees(boolean isSenior, boolean isIT, String areaCode) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM employee WHERE 1=1");

        if (isSenior) {
            query.append(" AND hireDate < DATE_SUB(NOW(), INTERVAL 10 YEAR)");
        }
        if (isIT) {
            query.append(" AND jobCode = 'IT_PROG'");
        }
        if (areaCode != null && !areaCode.equals("All")) {
            query.append(" AND phoneNumber LIKE '").append(areaCode).append("%'");
        }

        return getEmployees(query.toString());
    }
}