package modelo;

public class Usuario {
    private int id;
    private String login;
    private String senha;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Método toString para representação em String do objeto
    @Override
    public String toString() {
        return "ID: " + id + ", Login: " + login + ", Senha: " + senha;
    }
}

