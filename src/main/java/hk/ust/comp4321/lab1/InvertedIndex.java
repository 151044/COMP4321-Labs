package hk.ust.comp4321.lab1;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;

public class InvertedIndex implements AutoCloseable {
    private final Connection conn;
    private final PreparedStatement insertStatement, deleteStatement, queryStatement;

    /**
     * Creates a database or loads one at the current working directory with the specified name.
     * @param dbName The name of the database file to create
     * @throws SQLException If creating the database or the tables fail
     */
    public InvertedIndex(String dbName) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
        Statement createTable = conn.createStatement();
        createTable.execute("CREATE TABLE IF NOT EXISTS InvertedFile " +
                "(Word varchar(255), DocId integer, Frequency integer, PRIMARY KEY (Word, DocId));");
        insertStatement = conn.prepareStatement("INSERT OR REPLACE INTO InvertedFile VALUES (?, ?, ?) ");
        deleteStatement = conn.prepareStatement("DELETE FROM InvertedFile WHERE Word=?");
        queryStatement = conn.prepareStatement("SELECT * FROM InvertedFile");
    }

    public void addEntry(String word, int x, int y) throws IOException, SQLException {
        // Add a "docX Y" entry for the key "word" into hashtable
        // ADD YOUR CODES HERE
        insertStatement.setString(1, word);
        insertStatement.setInt(2, x);
        insertStatement.setInt(3, y);
        insertStatement.execute();
    }

    public void delEntry(String word) throws IOException, SQLException {
        // Delete the word and its list from the hashtable
        // ADD YOUR CODES HERE
        deleteStatement.setString(1, word);
        deleteStatement.execute();
    }

    public void printAll() throws IOException, SQLException {
        // Print all the data in the hashtable
        // ADD YOUR CODES HERE
        ResultSet results = queryStatement.executeQuery();
        while (results.next()) {
            System.out.printf("Word = %s, Document Id = %d, Frequency = %d\n",
                    results.getString(1), results.getInt(2), results.getInt(3));
        }
        results.close();
    }

    public static void main(String[] args) {
        // In this case, we make <word, doc num, freq> as each database entry
        try (InvertedIndex idx = new InvertedIndex("database")){
            idx.addEntry("cat", 2, 6);
            idx.addEntry("dog", 1, 33);
            System.out.println("First print");
            idx.printAll();

            idx.addEntry("cat", 8, 3);
            idx.addEntry("dog", 6, 73);
            idx.addEntry("dog", 8, 83);
            idx.addEntry("dog", 10, 5);
            idx.addEntry("cat", 11, 106);
            System.out.println("Second print");
            idx.printAll();

            idx.delEntry("dog");
            System.out.println("Third print");
            idx.printAll();
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }
}
