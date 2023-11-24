package conexaobd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoPostgre {
    public final String url = "jdbc:postgresql://localhost/RedeSocial";
    public final String user = "postgres";
    public final String password = "123456";

    private static final String VALIDAR_QUERY_NOME ="SELECT COUNT(*) AS count FROM tbusuario WHERE nome =?";
    private static final String VALIDAR_QUERY_EMAIL ="SELECT COUNT(*) AS count FROM tbusuario WHERE email =?";


    public boolean validarUsuarioBanco(String nome) throws SQLException {
        boolean encontrado = false;

        try (Connection connection = DriverManager.getConnection(url, user, password);

             PreparedStatement preparedStatement = connection.prepareStatement(VALIDAR_QUERY_NOME)) {

            preparedStatement.setString(1, nome);

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

    public boolean validarUsuarioBancoEmail(String email) throws SQLException {
        boolean encontrado = false;

        try (Connection connection = DriverManager.getConnection(url, user, password);

             PreparedStatement preparedStatement = connection.prepareStatement(VALIDAR_QUERY_EMAIL)) {

            preparedStatement.setString(1, email);

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

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}