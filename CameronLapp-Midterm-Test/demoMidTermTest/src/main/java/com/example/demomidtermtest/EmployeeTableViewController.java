package com.example.demomidtermtest;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeTableViewController implements Initializable {

    @FXML
    private CheckBox checkIT;

    @FXML
    private CheckBox checkSenior;

    @FXML
    private TableColumn<Employee, Integer> colEmployeeId;

    @FXML
    private TableColumn<Employee, String> colFirstName;

    @FXML
    private TableColumn<Employee, Date> colHireDate;

    @FXML
    private TableColumn<Employee, String> colJobCode;

    @FXML
    private TableColumn<Employee, String> colLastName;

    @FXML
    private TableColumn<Employee, String> colPhoneNumber;

    @FXML
    private ComboBox<String> combAreaCode;

    @FXML
    private Label lableTotal;

    @FXML
    private TableView<Employee> tableEmployees;

    private FilteredList<Employee> filteredEmployees;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Fetch all employees from the database
            ArrayList<Employee> employees = DBUtility.getEmployees();

            // Initialize FilteredList with the list of employees
            filteredEmployees = new FilteredList<>(FXCollections.observableArrayList(employees), p -> true);

            // Set up the columns in the TableView
            colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
            colJobCode.setCellValueFactory(new PropertyValueFactory<>("jobCode"));

            // Bind the filtered list to the TableView
            tableEmployees.setItems(filteredEmployees);

            // Initialize the ComboBox with area codes
            ArrayList<String> areaCodes = DBUtility.getAreaCodes();
            combAreaCode.getItems().addAll(areaCodes);
            combAreaCode.getItems().add("All");

            // Show the total number of employees
            lableTotal.setText("Total Employees: " + employees.size());

            // Set up listeners for filters
            combAreaCode.setOnAction(event -> updateTableView());
            checkSenior.setOnAction(event -> updateTableView());
            checkIT.setOnAction(event -> updateTableView());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateTableView() {
        boolean isSenior = checkSenior.isSelected();
        boolean isIT = checkIT.isSelected();
        String areaCode = combAreaCode.getValue();

        filteredEmployees.setPredicate(employee -> {
            // Apply seniority filter
            if (isSenior && employee.getHireDate().after(Date.from(LocalDate.now().minusYears(10).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                return false;
            }

            // Apply job code filter for IT
            if (isIT && !employee.getJobCode().equals("IT_PROG")) {
                return false;
            }

            // Apply area code filter
            if (areaCode != null && !areaCode.equals("All") && !employee.getPhoneNumber().startsWith(areaCode)) {
                return false;
            }

            return true;
        });

        // Update total employees count label
        lableTotal.setText("Total Employees: " + filteredEmployees.size());
    }
}
