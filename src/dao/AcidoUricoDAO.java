package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import javaDB.ConnectionFactory;
import modelo.AcidoUrico;
import modelo.Medico;
import modelo.Paciente;
import seguranca.Criptografia;

public class AcidoUricoDAO {
	
	private Connection con;
	MedicoDAO daoMed = new MedicoDAO();
	
	
	public AcidoUricoDAO() throws SQLException{
		this.con =  ConnectionFactory.getConnection();
	}
	
	
	public void adiciona(String resultado, int id_paciente, int id_medico , String chave) throws Exception {
		
		if (ProcuraMedicoEPaciente(id_paciente, id_medico) ) {
			SecretKey chave2 = Criptografia.criarChaveSecreta(chave);
			
			String sql = "INSERT INTO acido_urico (resultado, "
					+ "id_paciente, id_medico) VALUES (?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, Criptografia.criptografa(resultado, chave2));
			stmt.setInt(2, id_paciente);
			stmt.setInt(3, id_medico);
					
			stmt.execute();
			stmt.close();
		}
	}
	
	public List<AcidoUrico> getLista() throws SQLException{
		String sql = "SELECT a.id, a.resultado, m.id, m.nome, m.cpf, m.especialidade,"
				+ "p.id, p.nome, p.cpf FROM acido_urico a "
				+ "JOIN medico m ON a.id_medico = m.id "
				+ "JOIN paciente p ON a.id_paciente = p.id";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rset = stmt.executeQuery();
		
		List<AcidoUrico> exames = new ArrayList<AcidoUrico>();
		
		while (rset.next()) {
			Medico medico = new Medico(rset.getInt("m.id"),rset.getString("m.nome") ,
					rset.getString("m.cpf"), rset.getString("m.especialidade"));
			Paciente paciente = new Paciente(rset.getInt("p.id"),rset.getString("p.nome") ,
					rset.getString("p.cpf"));
			AcidoUrico exame = new AcidoUrico(rset.getInt("a.id"), rset.getString("a.resultado"), paciente, medico);
			exames.add(exame);
			
		}
		rset.close();
		stmt.close();
		
		return exames;
	}
	
	public AcidoUrico getAcidoUricobyId(int id) throws SQLException {
		String sql = "SELECT a.id, a.resultado, m.id, m.nome, m.cpf, m.especialidade,"
				+ "p.id, p.nome, p.cpf FROM acido_urico a "
				+ "JOIN medico m ON a.id_medico = m.id "
				+ "JOIN paciente p ON a.id_paciente = p.id";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rset = stmt.executeQuery();
		
		if (rset.next()) {
			Medico medico = new Medico(rset.getInt("m.id"),rset.getString("m.nome") ,
					rset.getString("m.cpf"), rset.getString("m.especialidade"));
			Paciente paciente = new Paciente(rset.getInt("p.id"),rset.getString("p.nome") ,
					rset.getString("p.cpf"));
			AcidoUrico exame = new AcidoUrico(rset.getInt("a.id"), rset.getString("a.resultado"), paciente, medico);
			return exame;
		}
		return null;
	}
	
	public void atualiza(int id, String resultado, int id_paciente, int id_medico , String chave) throws Exception {
		
		AcidoUrico ac = getAcidoUricobyId(id);
		
		if (ac != null && ProcuraMedicoEPaciente(id_paciente, id_medico)) {
			SecretKey chave2 = Criptografia.criarChaveSecreta(chave);
			
			String sql = "update acido_urico set resultado = ? , id_paciente = ? , id_paciente = ? where id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, Criptografia.criptografa(resultado, chave2));
			stmt.setInt(2, id_paciente);
			stmt.setInt(3, id_medico);
			stmt.setInt(4,id);
			stmt.execute();
			stmt.close();
		}	
	}
	
	public void deleta(int id) throws Exception {
		
		AcidoUrico ac = getAcidoUricobyId(id);
		
		if (ac != null) {
			String sql = "delete from acido_urico where id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
		}
		
			
	}
	
	private boolean ProcuraMedicoEPaciente(int id_paciente, int id_medico) throws SQLException {
		MedicoDAO daoMed = new MedicoDAO();
		PacienteDAO daoPac = new PacienteDAO();
		
		return daoPac.getPacientebyId(id_paciente) && daoMed.getMedicobyId(id_medico);
	}

}
