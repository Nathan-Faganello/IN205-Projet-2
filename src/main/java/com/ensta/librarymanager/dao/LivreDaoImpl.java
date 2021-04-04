package com.ensta.librarymanager.dao;

import java.util.List;
import java.time.LocalDate;
import java.util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.DeleteDbFiles;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.persistence.*;

import com.ensta.librarymanager.dao.*;

public class LivreDaoImpl implements LivreDao {

	private static LivreDaoImpl instance;
	private LivreDaoImpl(){}
	public static LivreDaoImpl getInstance(){
		if (instance == null) {
			instance = new LivreDaoImpl();
		}
		return instance;
	}

	private static final String GETLIST_QUERY = "SELECT id, titre, auteur, isbn FROM livre;";
	private static final String GETBYID_QUERY = "SELECT id, titre, auteur, isbn FROM livre WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM livre WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM livre;";
	
	@Override
	public List<Livre> getList() throws DaoException {
		List<Livre> listeLivres = new ArrayList<>();
		ResultSet res = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETLIST_QUERY);
            res = preparedStatement.executeQuery();
            while(res.next()) {
            	Livre livre = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            	listeLivres.add(livre);
            }
            System.out.println("GET: " + listeLivres);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des livres", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listeLivres;
	}

	@Override
	public Livre getById(int id) throws DaoException {
		ResultSet res = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Livre livre = null;


		try {
			connection = connectionManager.getConnection();           
            preparedStatement = connection.prepareStatement(GETBYID_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if (res.next()) {
            	livre = new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            }
            System.out.println("GET: " + livre);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du livre n° : "+id, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		ResultSet res = null;
		int result = 0;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
            	result = res.getInt(1);
            }
            System.out.println("CREATE : "+titre+", "+auteur+", "+isbn);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du livre :"+titre, e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	@Override
	public void update(Livre livre) throws DaoException {
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
           	preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE : "+livre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du livre : "+livre, e);
		} finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(int id) throws DaoException {
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
			connection.setAutoCommit(false);
           
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("DELETE livre n°: " + id);

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la suppression du livre n°: " + id, e);
		}  finally {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int count() throws DaoException {
		int result = -1;
		ResultSet res = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_QUERY);
            res = preparedStatement.executeQuery();
            if (res.next()) {
            	result = res.getInt("count");
            }
		} catch (SQLException e) {
			throw new DaoException("Problème lors du dénombrement des membres", e);
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}