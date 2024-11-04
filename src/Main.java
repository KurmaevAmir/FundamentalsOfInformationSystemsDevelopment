/**
 * File: Main.java
 * Description: Executing sql queries
 * Authors:
 * - Amir Kurmaev
 * Copyright: (C) 2024 Amir Kurmaev
 */

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL_DB = "jdbc:postgresql://localhost:5432/ORISFirstDB"; // url address to the database
    private static final String USER_DB = "postgres"; // username
    private static final String PASSWORD_DB = "Fvbh"; // database user password

    /**
     * sending a single request of 6 users to a table and displaying users
     who are over 18 y.o.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
        String setRequest = "INSERT INTO driver(name, surname, age) "
                + "VALUES (?, ?, ?), (?, ?, ?), (?, ?, ?)," +
                "(?, ?, ?), (?, ?, ?), (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(setRequest);

        Scanner scanner = new Scanner(System.in);
        String name;
        String surname;
        int age;

        for (int i = 1; i <= 6; i++) {
            System.out.print("Введите имя: ");
            name = scanner.nextLine();
            System.out.print("Введите фамилию: ");
            surname = scanner.nextLine();
            System.out.print("Введите возраст: ");
            age = scanner.nextInt();
            scanner.nextLine();
            preparedStatement.setString(i * 3 - 2, name);
            preparedStatement.setString(i * 3 - 1, surname);
            preparedStatement.setInt(i * 3, age);
        }
        preparedStatement.executeUpdate();

        String getRequest = "SELECT * FROM driver WHERE age >= ?";
        preparedStatement = connection.prepareStatement(getRequest);
        preparedStatement.setInt(1, 18);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("name: " + resultSet.getString("name") +
                    "\tsurname: " + resultSet.getString("surname") +
                    "\tage: " + resultSet.getInt("age"));
        }
    }
}