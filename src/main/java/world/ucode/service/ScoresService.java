package world.ucode.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScoresService {

    private static final String DB_NAME = "db.sqlite";
    private DbConnection dbConnection;

    public ScoresService() {
        dbConnection = new DbConnection();
    }

    public void add(int score) {
        Connection connection = dbConnection.connect(DB_NAME);

        dbConnection.disconnect(connection);
    }

    public List<Integer> getTop() {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.connect(DB_NAME);

        String sql = "SELECT value FROM scores ORDER BY value DESC LIMIT 10";

        var list = new ArrayList<Integer>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int scoore = rs.getInt("value");
                list.add(scoore);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        dbConnection.disconnect(connection);

        return list;
    }
}
