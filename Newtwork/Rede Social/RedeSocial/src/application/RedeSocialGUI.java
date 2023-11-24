package application;

import model.entities.Usuario;
import model.exceptions.DomainException;
import conexaobd.LogarUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;



public class RedeSocialGUI extends JFrame {
    private JPanel painelLogin = new JPanel();
    private JButton login = new JButton("Login");
    private JButton cadastro = new JButton("Cadastrar");
    private JTextField campoTexto1;
    private JTextField campoTexto2;
    private JLabel labelTexto1;
    private JLabel labelTexto2;
    private JPanel painelLogado = new JPanel();
    private JButton adicionar = new JButton("Adicionar amigos");
    private JButton listar = new JButton("Listar amigos");
    private JButton mensagem = new JButton("Conversar");
    private JButton listarMensagem = new JButton("Mensagens");
    private JButton excluir = new JButton("Excluir amigos");
    private JButton sair = new JButton("Logout");
    private Usuario user = new Usuario();
    private String nomeX = "";
    private String senhaX = "";

    private LogarUsuario lu = new LogarUsuario();


    public RedeSocialGUI() throws SQLException {
        configurarPainelLogin();
        configurarPainelLogado();
        exibirPainelLogin();
    }
    private void configurarPainelLogin() {
        LogarUsuario logarUsuario = new LogarUsuario();
        this.setTitle("Newtwork");
        this.setSize(500, 300);
        this.setResizable(false);

        painelLogin.setLayout(null);
        painelLogin.setBackground(new Color(255, 255, 255));

        labelTexto1 = new JLabel("Usuário:");
        Font fonte = labelTexto1.getFont();
        labelTexto1.setFont(new Font(fonte.getName(), Font.BOLD, 17));
        labelTexto1.setBounds(100, 50, 350, 25);

        labelTexto2 = new JLabel("Senha: ");
        Font fonte2 = labelTexto2.getFont();
        labelTexto2.setFont(new Font(fonte2.getName(), Font.BOLD, 17));
        labelTexto2.setBounds(100, 100, 350, 25);


        campoTexto1 = new JTextField();
        campoTexto1.setBounds(200, 50, 150, 25);

        campoTexto2 = new JTextField();
        campoTexto2.setBounds(200, 100, 150, 25);


        login.setBounds(100, 170, 100, 25);
        cadastro.setBounds(250, 170, 100, 25);


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    nomeX = campoTexto1.getText();
                    senhaX = campoTexto2.getText();
                    if(!campoTexto1.getText().isBlank() || !campoTexto2.getText().isBlank() || !campoTexto1.getText().isEmpty() || !campoTexto2.getText().isEmpty()) {
                        boolean resultado = logarUsuario.verificarUsuarioBanco(nomeX,senhaX);
                        if(resultado) {
                            user.logarUsuario(nomeX, senhaX);
                            exibirPainelLogado();
                            getContentPane().add(painelLogado);
                            setVisible(true);
                            getContentPane().remove(painelLogin);
                            painelLogin.setVisible(false);
                            painelLogado.setVisible(true);
                            revalidate();
                            repaint();
                        }else{
                            JOptionPane.showMessageDialog(null, "Acesso negado! \nUsuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Acesso negado! \nUtulize algum usuário e senha para continuar.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(DomainException exception){
                    exception.getMessage();
                }catch(SQLException exception){
                    exception.printStackTrace();
                }
            }
        });

        cadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.cadastrarUsuario();
                }catch(SQLException exception){
                    exception.getMessage();
                }catch(DomainException exception){
                    exception.getMessage();
                }
            }
        });

        painelLogin.add(labelTexto1);
        painelLogin.add(labelTexto2);
        painelLogin.add(campoTexto1);
        painelLogin.add(campoTexto2);
        painelLogin.add(login);
        painelLogin.add(cadastro);
        this.setLocationRelativeTo(null);
    }
    private void configurarPainelLogado() throws SQLException {
        this.setTitle("Newtwork");
        this.setSize(500, 300);
        this.setResizable(false);

        painelLogado.setLayout(null);
        painelLogado.setBackground(new Color(255,255,255));

        adicionar.setBounds(85,145,150,25);
        excluir.setBounds(260,145,150,25);
        listar.setBounds(260,55, 150,25);
        mensagem.setBounds(85,55,150,25);
        listarMensagem.setBounds(170,100,150,25);
        sair.setBounds(360,200,100,25);

        adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.adicionarAmigo(nomeX);
                }catch (DomainException exception){
                    exception.getMessage();
                }catch (SQLException exception){
                    exception.getMessage();
                }
            }
        });
        excluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.excluirAmigo(nomeX);
                }catch (DomainException exception){
                    exception.getMessage();
                }catch (SQLException exception){
                    exception.getMessage();
                }
            }
        });
        listar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.listarAmigos(nomeX);
                }catch (DomainException exception){
                    exception.getMessage();
                }catch (SQLException exception){
                    exception.getMessage();
                }
            }
        });
        mensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.enviarMensagens(nomeX);
                }catch (DomainException exception){
                    exception.getMessage();
                }catch (SQLException exception){
                    exception.getMessage();
                }
            }
        });
        listarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    user.listarMensagens(nomeX);
                }catch (DomainException exception){
                    exception.getMessage();
                }catch (SQLException exception){
                    exception.getMessage();
                }
            }
        });
        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    painelLogin.setVisible(true);
                    painelLogado.setVisible(false);

                    getContentPane().remove(painelLogado);
                    getContentPane().add(painelLogin);

                    campoTexto1.setText("");
                    campoTexto2.setText("");

                    revalidate();
                    repaint();
                }catch (DomainException exception){
                    exception.getMessage();
                }
            }
        });

        painelLogado.add(adicionar);
        painelLogado.add(excluir);
        painelLogado.add(listar);
        painelLogado.add(mensagem);
        painelLogado.add(listarMensagem);
        painelLogado.add(sair);
        this.setLocationRelativeTo(null);
    }

    private void exibirPainelLogin() {
        getContentPane().removeAll();
        getContentPane().add(painelLogin);
        revalidate();
        repaint();
    }
    private void exibirPainelLogado() {
        getContentPane().removeAll();
        getContentPane().add(painelLogado);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RedeSocialGUI gui = null;
            try {
                gui = new RedeSocialGUI();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            gui.setSize(500, 300);
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setVisible(true);
        });
    }
}
