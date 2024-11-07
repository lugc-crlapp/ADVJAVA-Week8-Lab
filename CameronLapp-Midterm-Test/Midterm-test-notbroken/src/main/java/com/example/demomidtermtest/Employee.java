package com.example.demomidtermtest;

import java.util.Date;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date hireDate;
    private String jobCode;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        if(employeeId < 0) {
            throw new IllegalArgumentException("Employee ID must be greater than 0");
        }
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.length() < 2) {
            throw new IllegalArgumentException("First name must be at least 2 characters long");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.length() < 2) {
            throw new IllegalArgumentException("Last name must be at least 2 characters long");
        }
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(!phoneNumber.matches("[2-9]\\d{2}[.,\\s][0-9]{3}[.,\\s][0-9]{4}")) {
            throw new IllegalArgumentException("Phone number must match the North American dialing plan");
        }

        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        if(hireDate.after(new Date())) {
            throw new IllegalArgumentException("Hire date cannot be later in the future");
        }
        this.hireDate = hireDate;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        //if(!jobCode.matches("\\d{2}_[A-Za-z]+")) {
            //throw new IllegalArgumentException("Job code must match the pattern");
        //}
        this.jobCode = jobCode;
    }

}