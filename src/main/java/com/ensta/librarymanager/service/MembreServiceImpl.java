package com.ensta.librarymanager.service;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.MembreDaoImpl;

public class MembreServiceImpl implements MembreService {

	private static MembreServiceImpl instance;
	private MembreServiceImpl(){}
	public static MembreServiceImpl getInstance(){
		if (instance == null) {
			instance = new MembreServiceImpl();
		}
		return instance;
	}

	@Override
	public List<Membre> getList() {
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		List<Membre> listMembres = new ArrayList<>();
		try {
			listMembres = membreDao.getList();
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return listMembres;
	}


	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		List<Membre> listMembres = new ArrayList<>();
		List<Membre> listMembresEmpruntPossible = new ArrayList<>();
		try {
			listMembres = membreDao.getList();
			for (Membre m : listMembres) {
				if (empruntService.isEmpruntPossible(m)) {
					listMembresEmpruntPossible.add(m);
				}
			}

		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors de la recherche", e);
		}
		return listMembresEmpruntPossible;
	}


	@Override
	public Membre getById(int id) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing: id=" + id,e );
		}
		return membre;
	}


	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		int created = -1;
		try {
			if (nom == null || prenom == null) {
				throw new ServiceException("Erreur lors de la creation du membre");
			}
			else {
				created = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
			}
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors lors de la creation du membre", e);
		}
		return created;
	}


	@Override
	public void update(Membre membre) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		try {
			if (membre.getNom() == null || membre.getPrenom() == null) {
				throw new ServiceException("Erreur lors de la mise à jour du membre");
			}
			else {
				String name = membre.getNom();
				membre.setNom(name.toUpperCase());
				membreDao.update(membre);
			}

		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing", e);
		}
	}

	@Override
	public void delete(int id) throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		try {
			membreDao.delete(id);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing", e);
		}
	}


	@Override
	public int count() throws ServiceException{
		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		int total = 0;
		try {
			total = membreDao.count();
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing", e);
		}
		return total;
	}
}