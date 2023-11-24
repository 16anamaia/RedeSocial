package conexaobd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import model.entities.Usuario;


public class ListarMensagens extends ConexaoPostgre {

    private static final String QUERY_MENSAGENS = "SELECT * FROM tbmensagem where (id_usuario_origem = ? AND id_usuario_destino = ?) OR (id_usuario_origem = ? AND id_usuario_destino = ?)";

    public void listarMensagens(String usuarioLogado, String amigoConversa) {
        System.out.println(QUERY_MENSAGENS);

        ArrayList<String> mensagens = new ArrayList<>();

        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_MENSAGENS)) {

            preparedStatement.setString(1, usuarioLogado);
            preparedStatement.setString(2, amigoConversa);
            preparedStatement.setString(3, amigoConversa);
            preparedStatement.setString(4, usuarioLogado);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String usuarioEnvio = rs.getString("id_usuario_origem");
                String conteudo = rs.getString("conteudo");

                if (usuarioLogado.equals(usuarioEnvio)) {
                    // Se for o remetente, adiciona a mensagem como enviada por ele
                    mensagens.add("Você: " + conteudo);
                } else {
                    // Se for o destinatário, adiciona a mensagem como recebida dele
                    mensagens.add(amigoConversa + ": " + conteudo);
                }
            }

            StringBuilder mensagensText = new StringBuilder();
            for (String mensagem : mensagens) {
                mensagensText.append(mensagem).append("\n");
            }

            JOptionPane.showMessageDialog(null, mensagensText.toString(), "Conversa com "+ amigoConversa, JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.
    }

}