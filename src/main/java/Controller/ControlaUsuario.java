package Controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entities.Usuario;

public class ControlaUsuario {

	
	
	public void adicionar(Usuario usuario) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pneuSystem-jpa");
		EntityManager em = emf.createEntityManager();
		
	    try {
	        em.getTransaction().begin();
	        em.persist(usuario);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw new RuntimeException("Erro ao adicionar usuário: " + e.getMessage());
	    }
	}
	
	public void salvar(Usuario usuario) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pneuSystem-jpa");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

	public List<Usuario> listar() {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pneuSystem-jpa");
	    EntityManager em = emf.createEntityManager();

	    List<Usuario> listaUsuarios = null;

	    try {
	        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
	        listaUsuarios = query.getResultList();
	    } finally {
	        em.close();
	        emf.close();
	    }

	    return listaUsuarios;
	}


	public boolean validarAcesso(Usuario usuario) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pneuSystem-jpa");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Usuario usuarioBanco = em.find(Usuario.class, usuario.getNome());
		em.getTransaction().commit();

		em.close();
		emf.close();
		return false;

	}

	public void excluir(Usuario usuario) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pneuSystem-jpa");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.remove(usuario);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

	public void atualizar(Usuario usuario) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pneuSystem-jpa");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Usuario usuarioAtualizado = em.merge(usuario);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

}
