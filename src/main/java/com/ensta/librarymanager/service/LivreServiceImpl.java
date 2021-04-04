package com.ensta.librarymanager.service;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.service.*;;


public class LivreServiceImpl implements LivreService {

	private static LivreServiceImpl instance;
	private LivreServiceImpl(){}
	public static LivreServiceImpl getInstance(){
		if (instance == null) {
			instance = new LivreServiceImpl();
		}
		return instance;
	}

	@Override
	public List<Livre> getList() {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		List<Livre> listLivres = new ArrayList<>();
		try {
			listLivres = livreDao.getList();
			
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
		return listLivres;
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException{
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		List<Livre> listLivres = new ArrayList<>();
		List<Livre> listLivresDispo = new ArrayList<>();
		try {
			listLivres = livreDao.getList();
			for (Livre l : listLivres) {
				if(empruntService.isLivreDispo(l.getId())){
					listLivresDispo.add(l);
				}
			}

		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors de la recherche", e);
		}
		return listLivres;
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		Livre livre = new Livre();
		try {
			livre = livreDao.getById(id);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing: id=" + id, e);
		}
		return livre;
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		int created = -1;
		try {
			if (titre == null) {
				throw new ServiceException("Erreur lors de la creation");
			}
			else {
				created = livreDao.create(titre, auteur, isbn);
			}
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing", e);
		}
		return created;
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		try {
			if (livre.getTitre() == null) {
				throw new ServiceException("Erreur lors de la creation");
			}
			else {
				livreDao.update(livre);
			}
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing: id=" + livre.getId(), e);
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		try {
			livreDao.delete(id);
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du parsing: id=" + id, e);
		}
	}

	@Override
	public int count() throws ServiceException {
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		int total = 0;
		try {
			total = livreDao.count();
		} catch (DaoException e) {
			System.out.println(e.getMessage());			
		} catch (NumberFormatException e) {
			throw new ServiceException("Erreur lors du dénombrement des livres", e);
		}
		return total;
	}
}

