package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;


public class EmpruntServiceImpl implements EmpruntService {

	private static EmpruntServiceImpl instance;
	private EmpruntServiceImpl(){}
	public static EmpruntServiceImpl getInstance(){
		if (instance == null) {
			instance = new EmpruntServiceImpl();
		}
		return instance;
	}


	@Override
	public List<Emprunt> getList() {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> listeEmprunt = new ArrayList<>();
		try {
			listeEmprunt = empruntDao.getListCurrent();
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return listeEmprunt;
	}



	@Override
	public List<Emprunt> getListCurrent() {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> listeEmprunt = new ArrayList<>();
		try {
			listeEmprunt = empruntDao.getListCurrent();
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return listeEmprunt;
	}


	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> listeEmprunt = new ArrayList<>();
		try {
			listeEmprunt = empruntDao.getListCurrentByMembre(idMembre);
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return listeEmprunt;
	}


	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> listeEmprunt = new ArrayList<>();
		try {
			listeEmprunt = empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return listeEmprunt;
	}


	@Override
	public Emprunt getById(int id) throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt = new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing: id=" + id, e);
		}
		return emprunt;
	}



	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		try {
			empruntDao.create(idMembre, idLivre, dateEmprunt);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing", e);
		}
	}




	@Override
	public void returnBook(int id) throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		try {
			Emprunt emprunt = empruntDao.getById(id);
			emprunt.setDateRetour(LocalDate.now());
			empruntDao.update(emprunt);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing", e);
		}
	}

	@Override
	public int count() throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		int total = 0;
		try {
			total = empruntDao.count();
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du dénombrement des emprunts", e);
		}
		return total;
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		boolean result = false;
		try {
			List<Emprunt> listeEmprunt = getListCurrentByLivre(idLivre);
			if (!listeEmprunt.isEmpty()) {
				result = true;
			}
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors de la recherche", e);
		}
		return result;
	}
	
	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		boolean result = false;
		try {
			List<Emprunt> listeEmprunt = getListCurrentByMembre(membre.getId());
			String abonnement = membre.getAbonnement().name();
			int nbEmprunts = listeEmprunt.size();
			if (abonnement.equals("BASIC")) {
				if (nbEmprunts<2) {
					result = true;
				}
			}
			else if (abonnement.equals("PREMIUM")) {
				if (nbEmprunts <5) {
					result = true;
				}
			}
			else {
				if (nbEmprunts < 20) {
					result = true;
				}
			}
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors de la recherche", e);
		}
		return result;
	}

}