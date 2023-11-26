package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.SecretKey;

import javaDB.ConnectionFactory;
import modelo.Senha;
import seguranca.Criptografia;

public class SenhaDAO {

private Connection con;
	
	public SenhaDAO() throws SQLException{
		this.con =  ConnectionFactory.getConnection();
	}
	
	public void adiciona(String senha, String chave) throws Exception {
		
		SecretKey chave2 = Criptografia.criarChaveSecreta(chave);
		String sql = "INSERT INTO senha (chave_secreta) VALUES (?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, Criptografia.criptografa(senha, chave2));
		stmt.execute();
		stmt.close();
		con.close();
	}
	
	public Senha getSenhabyId(int id) throws SQLException {
		String sql = "SELECT * FROM senha WHERE id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rset = stmt.executeQuery();
		
		if (rset.next()) {
			Senha senha = new Senha();
			senha.setChaveSecreta(rset.getString("chave_secreta"));
			return senha;
		}
		return null;
	}
}
