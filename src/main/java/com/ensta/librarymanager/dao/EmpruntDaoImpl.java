package com.ensta.librarymanager.dao;

import java.util.List;
import java.time.LocalDate;
import java.sql.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.DeleteDbFiles;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.persistence.*;

public class EmpruntDaoImpl implements EmpruntDao{

	private static EmpruntDaoImpl instance;
	private EmpruntDaoImpl(){}
	public static EmpruntDaoImpl getInstance(){
		if (instance == null) {
			instance = new EmpruntDaoImpl();
		}
		return instance;
	}

	private static final String GETLIST_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String GETLISTCURRENT_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private static final String GETLISTCURRENT_BY_MEMBRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String GETLISTCURRENT_BY_LIVRE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String GETBYID_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email,telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";

	@Override
	public List<Emprunt> getList() throws DaoException {
		List<Emprunt> listeEmprunts = new ArrayList<>();
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		ResultSet res = null;
		PreparedStatement preparedStatement = null;


		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETLIST_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
            	Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
            	Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            	if (res.getDate("dateRetour") == null) {
            		Emprunt emprunt = new Emprunt();
            		emprunt.setId(res.getInt("id"));
            		emprunt.setMembre(membre);
            		emprunt.setLivre(livre);
            		emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            	else {
            		Emprunt emprunt = new Emprunt(res.getInt("id"), membre, livre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            }
            System.out.println("GET: " + listeEmprunts);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
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
		return listeEmprunts;
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		List<Emprunt> listeEmprunts = new ArrayList<>();
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		ResultSet res = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETLISTCURRENT_QUERY);
            res = preparedStatement.executeQuery();
            while (res.next()) {
            	Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
            	Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            	if (res.getDate("dateRetour") == null) {
            		Emprunt emprunt = new Emprunt();
            		emprunt.setId(res.getInt("id"));
            		emprunt.setMembre(membre);
            		emprunt.setLivre(livre);
            		emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            	else {
            		Emprunt emprunt = new Emprunt(res.getInt("id"), membre, livre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            }
            System.out.println("GET: " + listeEmprunts);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
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
		return listeEmprunts;
	}


	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		List<Emprunt> listeEmprunts = new ArrayList<>();
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		ResultSet res = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETLISTCURRENT_BY_MEMBRE_QUERY);
            preparedStatement.setInt(1, idMembre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
            	Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
            	Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            	if (res.getDate("dateRetour") == null) {
            		Emprunt emprunt = new Emprunt();
            		emprunt.setId(res.getInt("id"));
            		emprunt.setMembre(membre);
            		emprunt.setLivre(livre);
            		emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            	else {
            		Emprunt emprunt = new Emprunt(res.getInt("id"), membre, livre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            }
            System.out.println("GET: " + listeEmprunts);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
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
		return listeEmprunts;
	}


	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		List<Emprunt> listeEmprunts = new ArrayList<>();
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		ResultSet res = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETLISTCURRENT_BY_LIVRE_QUERY);
            preparedStatement.setInt(1,idLivre);
            res = preparedStatement.executeQuery();
            while (res.next()) {
            	Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
            	Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            	if (res.getDate("dateRetour") == null) {
            		Emprunt emprunt = new Emprunt();
            		emprunt.setId(res.getInt("id"));
            		emprunt.setMembre(membre);
            		emprunt.setLivre(livre);
            		emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            	else {
            		Emprunt emprunt = new Emprunt(res.getInt("id"), membre, livre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
            		listeEmprunts.add(emprunt);
            	}
            }
            System.out.println("GET: " + listeEmprunts);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
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
		return listeEmprunts;
	}
	public Emprunt getById(int id) throws DaoException {
		ResultSet res = null;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Emprunt emprunt = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GETBYID_QUERY);
            preparedStatement.setInt(1,id);
            res = preparedStatement.executeQuery();
            if (res.next()) {
            	Membre membre = new Membre(res.getInt("idMembre"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Membre.Abonnement.valueOf(res.getString("abonnement")));
            	Livre livre = new Livre(res.getInt("idLivre"), res.getString("titre"), res.getString("auteur"), res.getString("isbn"));
            	if (res.getDate("dateRetour") == null) {
            		emprunt = new Emprunt();
            		emprunt.setId(res.getInt("id"));
            		emprunt.setMembre(membre);
            		emprunt.setLivre(livre);
            		emprunt.setDateEmprunt(res.getDate("dateEmprunt").toLocalDate());
            	}
            	else {
            		emprunt = new Emprunt(res.getInt("id"), membre, livre, res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate());
            	}
            }
            System.out.println("GET: " + emprunt);
        } catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du livre n° : "+id, e);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
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
		return emprunt;
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		ResultSet res = null;
		int result = 0;
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setInt(1,idMembre);
            preparedStatement.setInt(2,idLivre);
            preparedStatement.setDate(3,Date.valueOf(dateEmprunt));
            preparedStatement.setDate(4,Date.valueOf(LocalDate.MAX));
            preparedStatement.executeUpdate();
            System.out.println("CREATE emprunt : Membre :"+ idMembre+", Livre : "+idLivre);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la création de l'emprunt n°:"+result, e);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
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
    }
	

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1,emprunt.getMembre().getId());
            preparedStatement.setInt(2,emprunt.getLivre().getId());
            preparedStatement.setDate(3,Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4,Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5,emprunt.getId());
            preparedStatement.executeUpdate();

            System.out.println("UPDATE : "+emprunt);
		} catch (SQLException e) {
			throw new DaoException("Problème lors de la mise à jour du livre : "+emprunt, e);
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