package Program;

import java.util.List;
import java.util.Scanner;

import Controller.ControlaUsuario;
import Entities.Usuario;
import Enuns.TipoUsuario;

public class Main {

	public static void main(String[] args) {

		Usuario usuario = new Usuario();
		Scanner scanner = new Scanner(System.in);

		boolean continuar = true;
		while (continuar) {
			System.out.println("Digite uma das opções abaixo:");
			System.out.println("1 - Cadastrar novo usuário");
			System.out.println("2 - Listar usuários cadastrados");
			System.out.println("3 - Excluir usuário");
			System.out.println("4 - Editar nome do usuário");
			System.out.println("5 - Validar acesso do usuário");
			System.out.println("6 - Sair");

			int opcao = scanner.nextInt();

			switch (opcao) {
			case 1:
				criarUsuario();
				break;
			case 2:
				listarUsuario();
				break;
			case 3:
				excluirUsuario();
				break;
			case 4:
				editarNomeUsuario();
				break;
			case 5:
				validarSenhaUsuario();
				break;
			case 6:
				System.out.println("Saindo...");
				continuar = false;
				break;
			default:
				System.out.println("Opção inválida!");
			}
		}

		scanner.close();

		System.out.println("Finalizado.");

	}

	private static void criarUsuario() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o nome do usuário:");
			String nome = scanner.next();

			System.out.println("Digite a senha do usuário:");
			String senha = scanner.next();

			System.out.println("Digite o tipo de usuário (1 - ADMINISTRADOR, 2 - VENDEDOR, 3 - MECÂNICO");
			int tipo = scanner.nextInt();

			TipoUsuario tipoUsuario = TipoUsuario.ADMINISTRADOR;
			if (tipo == 2) {
				tipoUsuario = TipoUsuario.VENDEDOR;
			}if (tipo == 3) {
				tipoUsuario = TipoUsuario.MECANICO;
			}

			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setSenha(senha);
			usuario.setTipo(tipoUsuario);

			ControlaUsuario controller = new ControlaUsuario();
			controller.adicionar(usuario);
			System.out.println("Usuário cadastrado com sucesso!");

		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao criar o usuário: " + e.getMessage());
		}
	}


	private static void listarUsuario() {
		ControlaUsuario controller = new ControlaUsuario();
		List<Usuario> usuarios = controller.listar();

		if (usuarios.isEmpty()) {
			System.out.println("Nenhum usuário cadastrado.");
		} else {
			System.out.println("Lista de usuários cadastrados:");

			for (Usuario usuario : usuarios) {
				System.out.println(usuario.toString());
			}
		}
	}

	private static void validarSenhaUsuario() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o nome do usuário:");
			String nome = scanner.next();
			System.out.println("Digite a senha do usuário:");
			String senha = scanner.next();

			ControlaUsuario controller = new ControlaUsuario();
			Usuario usuario = new Usuario(nome, null, senha);
			boolean acesso = controller.validarAcesso(usuario);

			if (acesso) {
				System.out.println("Acesso permitido!");
			} else {
				System.out.println("Acesso negado!");
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao validar acesso do usuário: " + e.getMessage());
		}
	}

	private static void editarNomeUsuario() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o nome do usuário a ser alterado:");
			String nomeAntigo = scanner.next();

			ControlaUsuario controller = new ControlaUsuario();
			List<Usuario> usuarios = controller.listar();

			Usuario usuarioSelecionado = null;
			for (Usuario usuario : usuarios) {
				if (usuario.getNome().equals(nomeAntigo)) {
					usuarioSelecionado = usuario;
					break;
				}
			}

			if (usuarioSelecionado != null) {
				System.out.println("Digite o novo nome do usuário:");
				String novoNome = scanner.next();
				usuarioSelecionado.setNome(novoNome);
				controller.atualizar(usuarioSelecionado);
				System.out.println("Usuário atualizado com sucesso!");
			} else {
				System.out.println("Usuário não encontrado.");
			}

		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao editar nome do usuário: " + e.getMessage());
		}
	}

	private static void excluirUsuario() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o nome do usuário que deseja excluir:");
			String nome = scanner.next();

			ControlaUsuario controller = new ControlaUsuario();
			List<Usuario> usuarios = controller.listar();

			for (Usuario usuario : usuarios) {
				if (usuario.getNome().equals(nome)) {
					controller.excluir(usuario);
					System.out.println("Usuário excluído com sucesso!");
					return;
				}
			}
		}
	}
}
