package model.entities;

import java.awt.TextField;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import conexaobd.*;
import model.exceptions.DomainException;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String naturalidade;
    private String nascimento;
    private String genero;
    private CadastrarUsuario novoUsuario = null;
    private ConexaoPostgre cc = new ConexaoPostgre();
    private LogarUsuario login = null;
    private AdicionarAmigo aa = null;
    private ExcluirAmigo ea = null;
    private ListarAmigos la = new ListarAmigos();
    private EnviarMensagem em = new EnviarMensagem();
    private ListarMensagens lm = new ListarMensagens();

    ArrayList<Usuario> usuarios = new ArrayList<>();

    public Usuario() {
        this.novoUsuario = new CadastrarUsuario();
        this.login = new LogarUsuario();
        this.aa = new AdicionarAmigo();
        this.ea = new ExcluirAmigo();
        this.la = new ListarAmigos();
    }

    public Usuario(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario(String nome, String email, String senha, String naturalidade, String nascimento, String genero) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.naturalidade = naturalidade;
        this.nascimento = nascimento;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Realizar o cadastro de um usuario
    public void cadastrarUsuario() throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nascimentoData = null;
            nome = JOptionPane.showInputDialog(null, "Digite seu nome:", "", JOptionPane.PLAIN_MESSAGE);
            while (nome.length() < 1 || nome.isEmpty() || nome.isBlank()) {
                nome = JOptionPane.showInputDialog(null, "Nome inválido! \nDigite novamente:", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            email = JOptionPane.showInputDialog(null, "Digite seu e-mail:", "", JOptionPane.PLAIN_MESSAGE);
            while (email.length() < 1 || email.isEmpty() || email.isBlank()) {
                email = JOptionPane.showInputDialog(null, "e-mail inválido! \nDigite novamente:", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            senha = JOptionPane.showInputDialog(null, "Insira uma senha:", "", JOptionPane.PLAIN_MESSAGE);
            while (senha.length() < 1 || senha.isEmpty() || senha.isBlank()) {
                senha = JOptionPane.showInputDialog(null, "Senha inválida! \nDigite novamente:", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            naturalidade = JOptionPane.showInputDialog(null, "Insira sua naturalidade:", "", JOptionPane.PLAIN_MESSAGE);
            while (naturalidade.length() < 1 || naturalidade.isEmpty() || naturalidade.isBlank()) {
                naturalidade = JOptionPane.showInputDialog(null, "Naturalidade inválida! \nDigite novamente:", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            boolean dataInvalida = true;
            while (dataInvalida) {
                nascimento = JOptionPane.showInputDialog(null, "Digite sua data de nascimento (ano-mês-dia):", "", JOptionPane.PLAIN_MESSAGE);
                try {
                    nascimentoData = dateFormat.parse(nascimento);
                    dataInvalida = false;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Digite uma data válida!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            genero = JOptionPane.showInputDialog(null, "Digite seu gênero (M / F):", "", JOptionPane.PLAIN_MESSAGE);
            while (genero.length() != 1 || genero.charAt(0) != 'M' && genero.charAt(0) != 'F' && genero.charAt(0) != 'm' && genero.charAt(0) != 'f') {
                genero = JOptionPane.showInputDialog(null, "Genero inválido! \nDigite novamente (M / F):", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            genero = genero.toUpperCase();

            Date nascimentoSql = new Date(nascimentoData.getTime());

            if (cc.validarUsuarioBanco(nome) == true) {
                JOptionPane.showMessageDialog(null, "Nome inserido em uso! \nPor favor, defina um novo.", "", JOptionPane.ERROR_MESSAGE);
            } else if (cc.validarUsuarioBancoEmail(email) == true) {
                JOptionPane.showMessageDialog(null, "O email digitado já está em uso. \nPor favor, selecione outro email.", "", JOptionPane.ERROR_MESSAGE);
            } else {
                novoUsuario.inserirUsuarioBanco(nome, email, senha, naturalidade, nascimentoData, genero);
                Usuario user = new Usuario(nome, email, senha, naturalidade, nascimento, genero);
                usuarios.add(user);
            }
        } catch (NullPointerException e) {
            System.err.println("Operação cancelada");
        }

    }

    public boolean logarUsuario(String nomeX, String senhaX) throws SQLException, DomainException {

        try {

            boolean encontradoBanco = login.verificarUsuarioBanco(nomeX, senhaX);

            if (encontradoBanco) {
                JOptionPane.showMessageDialog(null, "Bem vindo a Newtwork!", "", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Acesso negado! \nUsuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (DomainException e) {
            e.getMessage();
        }
        return false;

    }

    public void adicionarAmigo(String usuarioLogado) throws SQLException {
        try {
            String amigoNovo = JOptionPane.showInputDialog(null, "Qual o nome do amigo que deseja adicionar?", "Indique um usuário", JOptionPane.PLAIN_MESSAGE);
            if (!amigoNovo.isEmpty()) {// se não for vazio
                if (amigoNovo != null) {// se ele clicar em ok
                    if (!usuarioLogado.equals(amigoNovo)) {// se ele tentar adicionar a sí mesmo
                        boolean encontradoBanco = cc.validarUsuarioBanco(amigoNovo);

                        if (encontradoBanco) {
                            aa.adicionarAmigoBanco(usuarioLogado, amigoNovo);
                            JOptionPane.showMessageDialog(null, "Amigo adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {//se ele não for encontrado
                            JOptionPane.showMessageDialog(null, "Usuário inexistente!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Você não pode adicionar a sí mesmo", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {// se ele clicou em cancelar
                    System.err.println("Operação cancelada");
                }
            } else {//se for vazio
                System.err.println("Digite algum usuário.");
                JOptionPane.showMessageDialog(null, "Digite algum usuário", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DomainException e) {
            e.getMessage();
        } catch (NullPointerException e) {
            e.getMessage();
        }

    }

    public void excluirAmigo(String usuarioLogado) throws SQLException {
        try {
            String amigoExcluir = JOptionPane.showInputDialog(null, "Quem você deseja excluir?", "Indique seu amigo", JOptionPane.PLAIN_MESSAGE);
            if(!amigoExcluir.isEmpty()) {
                boolean encontradoBanco = cc.validarUsuarioBanco(amigoExcluir);
                if (encontradoBanco) {
                    ea.excluirAmigoBanco(usuarioLogado, amigoExcluir);
                    JOptionPane.showMessageDialog(null, "Amigo excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Amigo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                System.err.println("Você deve digitar algum usuário.");
                JOptionPane.showMessageDialog(null, "Digite algum amigo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(DomainException e) {
            e.getMessage();
        }catch(NullPointerException e){
            e.getMessage();
        }

    }

    public void listarAmigos(String usuarioLogado) throws SQLException {
        try {
            la.listarAmigos(usuarioLogado);
        }
        catch(DomainException e) {
            e.getMessage();
        }

    }

    public void enviarMensagens(String usuarioLogado) throws SQLException {
        String amigoMensagem = JOptionPane.showInputDialog(null, "Para quem deseja enviar a mensagem?","Indique seu amigo", JOptionPane.QUESTION_MESSAGE);
        boolean encontrado = cc.validarUsuarioBanco(amigoMensagem);
        try {
            if(amigoMensagem != null) {
                if (encontrado) {
                    String conteudo = JOptionPane.showInputDialog(null, "Digite a mensagem:", "", JOptionPane.PLAIN_MESSAGE);
                    if(!conteudo.isEmpty()) {
                        em.enviarMensagemBanco(usuarioLogado, amigoMensagem, conteudo);
                        JOptionPane.showMessageDialog(null, "Mensagem enviada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        System.err.println("Conteúdo da mensagem não pode estar vazio.");
                        JOptionPane.showMessageDialog(null, "Digite alguma mensagem!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Amigo não encontrado.", "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                System.err.println("Operação cancelada.");
            }
        }
        catch(DomainException e) {
            e.getMessage();
        }catch(NullPointerException e){
            e.getMessage();
        }
    }

    public void listarMensagens(String usuarioLogado) throws SQLException {
        try {
            String amigoConversa = JOptionPane.showInputDialog(null, "Deseja ver a conversa com qual amigo?", "Indique seu amigo", JOptionPane.PLAIN_MESSAGE);
            if(!amigoConversa.isEmpty()) {
                boolean encontradoBanco = cc.validarUsuarioBanco(amigoConversa);

                if (encontradoBanco) {
                    lm.listarMensagens(usuarioLogado, amigoConversa);
                } else {
                    JOptionPane.showMessageDialog(null, "Não há registro de mensagens com esse usuário.", "Conversa vazia", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                System.err.println("Insira algum amigo");
                JOptionPane.showMessageDialog(null,"Insira algum amigo", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(DomainException e) {
            e.getMessage();
        }catch(NullPointerException e){
            e.getMessage();
        }

    }
}