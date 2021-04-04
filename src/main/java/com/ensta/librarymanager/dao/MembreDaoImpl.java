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

import com.ensta.librarymanager.dao.*;

public class MembreDaoImpl implements MembreDao {

	private static MembreDaoImpl instance;
	private MembreDaoImpl(){}
	public static MembreDaoImpl getInstance(){
		if (instance == null) {
			instance = new MembreDaoImpl();
		}
		return instance;
	}

	private static final String GETLIST_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
	private static final String GETBYID_QUERY = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM membre WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM membre;";
	
	@Override
	public List<Membre> getList() throws DaoException {
		List<Membre> listeMembres = new ArrayList<>();
		ResultSet res = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETLIST_QUERY);
            res = preparedStatement.executeQuery();
            while(res.next()) {
            	Membre membre = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
            	listeMembres.add(membre);
            }
            System.out.println("GET: " + listeMembres);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des membres", e);
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
		return listeMembres;
    }


	@Override
	public Membre getById(int id) throws DaoException {
		ResultSet res = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Membre membre = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETBYID_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if (res.next()) {
           		membre = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
           	}
            System.out.println("GET : "+membre);
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
		return membre;
	}


	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		ResultSet res = null;
		int result = 0;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection = connectionManager.getConnection();
			preparedStatement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, "BASIC");
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
            	result = res.getInt(1);
            }
            System.out.println("CREATE : "+nom+", "+prenom+", "+adresse+", "+email+", "+telephone);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création du membre :"+nom+" "+prenom, e);
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
	public void update(Membre membre) throws DaoException {
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().name());
            preparedStatement.setInt(7, membre.getId());
            preparedStatement.executeUpdate();
            System.out.println("UPDATE : "+membre.getNom()+" "+membre.getPrenom());
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du membre : "+membre.getNom()+" "+membre.getPrenom(), e);
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
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
			System.out.println("DELETE membre n°: " + id);

		} catch (SQLException e) {
			throw new DaoException("Problème lors de la suppression du membre n°: " + id, e);
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