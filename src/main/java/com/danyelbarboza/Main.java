package com.danyelbarboza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.danyelbarboza.service.DatabaseService;

public class Main {
    public static void main(String[] args) {
        DatabaseService dbService = new DatabaseService("database.db");
        dbService.createTableMedicamentos();
        dbService.insertMedicamento("Paracetamol", 10, "2025-12-31", 1);
        dbService.insertMedicamento("Aspirina", 10, "2025-12-31", 1);
        dbService.insertMedicamento("Dipirona", 10, "2025-12-31", 2);
        dbService.selectAllMedicamentos();

    }
}