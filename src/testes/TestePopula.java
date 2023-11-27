package testes;

import java.util.Scanner;

import dao.AcidoUricoDAO;
import dao.SenhaDAO;
import dao.UsuarioDAO;
import dao.ValoresPadroesDAO;
/*
 * Classe Teste para popular o banco de dados nas tabelas Usuario, Senha, AcidoUrico
 */
public class TestePopula {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			// Populando tabela Usuario (salva o hash da senha)
			String login = "carol";
			System.out.print("login: " + login);
			String senhaUsuario = "1234fgthy789";
			System.out.print("\nsenha: " + senhaUsuario );
			UsuarioDAO dao = new UsuarioDAO();
			dao.adiciona(login, senhaUsuario);
			System.out.println("\nGravação do usuario e senha feita no banco de dados!");
			
			// Populando tabela Senha ( salva a chave criptografa pela senha do Usuário)
			String senhaCriptografia = "fghjy567klo";
			System.out.print("Senha para criptografar a tabela Exame: " + senhaCriptografia);
			SenhaDAO daoSenha = new SenhaDAO();
			daoSenha.adiciona(senhaCriptografia, senhaUsuario);
			System.out.println("\nGravação da senha para criptografar feita no banco de dados!");
			
			// Populando tabela acido_urico (salva o resultado do exame criptografado pela chave)
			
			// 3 gravações na tabela para teste
			AcidoUricoDAO daoExame = new AcidoUricoDAO();
			daoExame.adiciona("28 mg/dL", 1, 1, senhaCriptografia);
			System.out.println("\nGravação do exame de acido urico feita no banco de dados!");
			daoExame.adiciona("26 mg/dL", 2, 1, senhaCriptografia);
			System.out.println("\nGravação do exame de acido urico feita no banco de dados!");
			daoExame.adiciona("30 mg/dL", 3, 2, senhaCriptografia);
			System.out.println("\nGravação do exame de acido urico feita no banco de dados!");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}
