package conexaobd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JOptionPane;

import model.entities.Usuario;


public class CadastrarUsuario extends ConexaoPostgre {

    private static final String INSERT_USER = "INSERT INTO tbusuario" +
            " (nome, email, senha, naturalidade, data_nascimento, genero) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    public void inserirUsuarioBanco(String nome, String email, String senha, String naturalidade, Date nascimento, String genero) throws SQLException {
        System.out.println(INSERT_USER);
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, naturalidade);
            preparedStatement.setDate(5, java.sql.Date.valueOf(nascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            preparedStatement.setString(6, genero);

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