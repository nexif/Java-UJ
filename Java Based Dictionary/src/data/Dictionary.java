package data;

import java.sql.*;

interface DatabaseFunctionality{
    Connection connectwithDB();
    public void createTable(Connection c) throws SQLException;
    public void add(String word, String definition);
    public void remove(String word);
    public boolean isEmpty();
    public String[] showWords();
    public int size();
    public String searchDefinition(String word);
}

public class Dictionary implements DatabaseFunctionality{
    private Connection connection;
    private static final String ERROR = "podane pojęcie nie występuje w słowniku.";

    @Override
    public Connection connectwithDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql:file:dictionaryDB:shutdown=true", "SA", "");
            return connection;
        }catch (Exception e){
            System.err.println("ERROR: failed to load HSQLDB JDBC Drier");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createTable(Connection c) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS DICTIONARY (id INTEGER IDENTITY PRIMARY KEY, word VARCHAR(20), " +
                "definition VARCHAR (1000))");
        stmt.close();
    }

    @Override
    public void add(String word, String definition) {
        Statement stmt;
        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM DICTIONARY\n" +
                            "WHERE WORD='" + word + "';");

            if (rs.next())
            {
                stmt.executeUpdate("UPDATE DICTIONARY SET DEFINITION ='" + definition + "'" +
                        "WHERE WORD = '" + word + "'");
            } else {
                stmt.executeUpdate("INSERT INTO DICTIONARY(WORD, DEFINITION) " +
                        "VALUES ('" + word + "', '" + definition + "')");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String word) {
            Statement stmt;
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate("DELETE FROM DICTIONARY WHERE WORD= \'" + word + "\'");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public boolean isEmpty() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from DICTIONARY");

            return !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String[] showWords() {
        String[] elements = new String[size()];
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DICTIONARY");

            int i = 0;
            while (rs.next()) {
                elements[i++] = rs.getString("WORD");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elements;
    }

    @Override
    public int size() {
        int result = -1;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS ELEMENTS FROM DICTIONARY");

            if (rs.next()) {
                result = rs.getInt("ELEMENTS");
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String searchDefinition(String word) {
        String result = " ";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM DICTIONARY\n" +
                            "WHERE WORD='" + word + "';");

            if (rs.next())
            {
                result = rs.getString("DEFINITION");
            } else {
                result = ERROR;
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String[] words(){
        String[] words = new String[size()];
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DICTIONARY");

            int i = 0;
            while (rs.next()){
                words[i] = rs.getString("WORD");
                i++;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return words;
    }

    public void close(){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
