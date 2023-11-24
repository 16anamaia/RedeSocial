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


public class LogarUsuario extends ConexaoPostgre {
    private static final String VALIDAR_USER ="SELECT COUNT(*) AS count FROM tbusuario WHERE nome = ? AND senha = ?";

    public boolean verificarUsuarioBanco(String nome, String senha) throws SQLException {
        boolean encontrado = false;

        try (Connection connection = DriverManager.getConnection(url, user, password);

             PreparedStatement preparedStatement = connection.prepareStatement(VALIDAR_USER)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(preparedStatement);

            // Se houver algum resultado na consulta, significa que o usuário foi encontrado
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                encontrado = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return encontrado;
    }


}
