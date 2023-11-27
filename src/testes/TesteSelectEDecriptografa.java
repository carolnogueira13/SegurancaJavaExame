package testes;

import java.util.List;
import java.util.Scanner;

import dao.AcidoUricoDAO;
import dao.SenhaDAO;
import dao.UsuarioDAO;
import modelo.AcidoUrico;
import modelo.Senha;
import modelo.Usuario;
import seguranca.Criptografia;
/*
 * Classe Teste só para averiguar como os dados foram salvos no banco, e como você consegue 
 * recuperar as informações originais através da decriptografia.
 * 
 */
public class TesteSelectEDecriptografa {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			
			// Campos para a busca e a senha do usuario para iniciar a decriptografia
			int id = 1;
			String senhaUsuario = "1234fgthy789";
			
			// Para conferir que foi salvo o HASH da senha do usuário no banco
			UsuarioDAO daoUsuario = new UsuarioDAO();
			Usuario usuario = daoUsuario.getusuariobyId(id);
			System.out.println("Hash da senha do Usuario (banco de dados): " + usuario.getSenha());
			
			AcidoUricoDAO daoExame = new AcidoUricoDAO();
			SenhaDAO daoSenha = new SenhaDAO();
			
			
			// Select na tabela Senha do banco, para conferir como a chave foi salva e a decriptografia dela 
			Senha senha = daoSenha.getSenhabyId(id);
			System.out.println("Chave criptografada da tabela senha (banco de dados): " + senha.getChaveSecreta());
			String senhaCriptografia = Criptografia.decriptografa(
					senha.getChaveSecreta(), 
					Criptografia.criarChaveSecreta(senhaUsuario));
			System.out.println("Chave decriptografada: " + senhaCriptografia);
			
			// Select na tabela acido_urico do banco, para conferir como o resultado do exame foi salvo e a decriptografia dele 
			List<AcidoUrico> exames = daoExame.getLista();
			
			for (AcidoUrico exame : exames) {
				System.out.println("-----------------------------------------------------------");
				System.out.println("Resultado do exame de acido urico do paciente: " + exame.getPaciente() + " solicitado "
						+ "pelo médico: " + exame.getMedico());
				System.out.println("Resultado do exame criptografado (banco de dados): " + exame.getResultado());
				String nomeDoExame = Criptografia.decriptografa(
						exame.getResultado(), 
						Criptografia.criarChaveSecreta(senhaCriptografia));
				System.out.println("Resultado do exame decriptografado: " + nomeDoExame);
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
