package conexaobd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JOptionPane;

import model.entities.Usuario;


public class AdicionarAmigo extends ConexaoPostgre {

    private static final String INSERT_AMIGO = "INSERT INTO tbamizade" +
            " (id_usuario1, id_usuario2) VALUES " +
            " (?, ?);";

    public void adicionarAmigoBanco(String usuarioLogado, String novoAmigo) throws SQLException {
        System.out.println(INSERT_AMIGO);
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AMIGO)) {
            preparedStatement.setString(1, usuarioLogado);
            preparedStatement.setString(2, novoAmigo);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }


}