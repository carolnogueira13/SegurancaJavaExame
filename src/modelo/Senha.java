package modelo;

public class Senha {
    private int id;
    private String chaveSecreta;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChaveSecreta() {
        return chaveSecreta;
    }

    public void setChaveSecreta(String chaveSecreta) {
        this.chaveSecreta = chaveSecreta;
    }

    // Método toString para representação em String do objeto
    @Override
    public String toString() {
        return "ID: " + id + ", Chave Secreta: " + chaveSecreta;
    }
}

