package com.example.suicopracticeapplication;

public class EmployeeInfo {

    private String employeeName, employeeAddress, employeeContactNumber;

    public EmployeeInfo() {
    }

//    public EmployeeInfo(String employeeName, String employeeAddress, String employeeContactNumber) {
//        this.employeeName = employeeName;
//        this.employeeAddress = employeeAddress;
//        this.employeeContactNumber = employeeContactNumber;
//    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeContactNumber() {
        return employeeContactNumber;
    }

    public void setEmployeeContactNumber(String employeeContactNumber) {
        this.employeeContactNumber = employeeContactNumber;
    }
}
