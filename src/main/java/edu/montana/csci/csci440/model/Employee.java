package edu.montana.csci.csci440.model;

import edu.montana.csci.csci440.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Employee extends Model {

    private long employeeId;
    private String firstName;
    private String lastName;
    private String email;

    public Employee() {
        // new employee for insert
    }

    private Employee(ResultSet results) throws SQLException {
        firstName = results.getString("FirstName");
        lastName = results.getString("LastName");
        email = results.getString("Email");
        employeeId = results.getLong("EmployeeId");
    }

    public static List<Employee.SalesSummary> getSalesSummaries() {
        //TODO - a GROUP BY query to determine the sales, using the SalesSummary class
        return Collections.emptyList();
    }

    @Override
    public boolean verify() {
        if (firstName == null || "".equals(firstName)) {
            addError("FirstName can't be null or blank!");
        }
        if (lastName == null || "".equals(lastName)) {
            addError("LastName can't be null!");
        }
        return !hasErrors();
    }

    @Override
    public boolean update() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "UPDATE employees SET FirstName=?, LastName=?, Email=? WHERE EmployeeId=?")) {
                stmt.setString(1, this.getFirstName());
                stmt.setString(2, this.getLastName());
                stmt.setString(3, this.getEmail());
                stmt.setLong(4, this.getEmployeeId());
                stmt.executeUpdate();
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean create() {
        if (verify()) {
            try (Connection conn = DB.connect();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO employees (FirstName, LastName, Email) VALUES (?, ?, ?)")) {
                stmt.setString(1, this.getFirstName());
                stmt.setString(2, this.getLastName());
                stmt.setString(3, this.getEmail());
                stmt.executeUpdate();
                employeeId = DB.getLastID(conn);
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            return false;
        }
    }

    @Override
    public void delete() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM employees WHERE EmployeeID=?")) {
            stmt.setLong(1, this.getEmployeeId());
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public List<Customer> getCustomers() {
        return Customer.forEmployee(employeeId);
    }

    public List<Employee> getReports() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM employees  WHERE employees.ReportsTo=?")) {
            stmt.setLong(1, this.getEmployeeId());
            ResultSet results = stmt.executeQuery();
            List<Employee> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Employee(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }
    public Employee getBoss() {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT bosses.* FROM employees JOIN employees AS bosses ON employees.ReportsTo = bosses.EmployeeId WHERE employees.EmployeeId=?")) {
            stmt.setLong(1, this.getEmployeeId());
            ResultSet results = stmt.executeQuery();
            return new Employee(results);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public static List<Employee> all() {
        return all(0, Integer.MAX_VALUE);
    }

    public static List<Employee> all(int page, int count) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM employees LIMIT ? OFFSET ?")) {
            stmt.setInt(1, count);
            stmt.setInt(2, (page - 1) * 10);
            ResultSet results = stmt.executeQuery();
            List<Employee> resultList = new LinkedList<>();
            while (results.next()) {
                resultList.add(new Employee(results));
            }
            return resultList;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public static Employee findByEmail(String newEmailAddress) {
        throw new UnsupportedOperationException("Implement me");
    }

    public static Employee find(long employeeId) {
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE EmployeeId=?")) {
            stmt.setLong(1, employeeId);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                return new Employee(results);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public static class SalesSummary {
        String firstName;
        String lastName;
        String email;
        Long salesCount;
        BigDecimal salesTotals;
        private SalesSummary(ResultSet results) throws SQLException {
            firstName = results.getString("FirstName");
            lastName = results.getString("LastName");
            email = results.getString("Email");
            salesCount = results.getLong("SalesCount");
            salesTotals = results.getBigDecimal("SalesTotal");
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public Long getSalesCount() {
            return salesCount;
        }

        public BigDecimal getSalesTotals() {
            return salesTotals;
        }
    }
}
