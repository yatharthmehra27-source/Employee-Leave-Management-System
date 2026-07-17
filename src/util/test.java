package util;

import java.sql.Connection;

public class test {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        if (con != null) {
            System.out.println("Database Connected Successfully!");
            DBConnection.closeConnection(con);
        } else {
            System.out.println("Connection Failed!");
        }
    }
}