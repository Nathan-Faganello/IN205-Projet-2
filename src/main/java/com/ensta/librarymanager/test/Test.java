package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.sql.Date;


public class Test{

	public static void main(String[] args) {
		Membre nathan = new Membre(1, "Faganello", "Nathan", "21 rue Maximilien Robespierre, 91120, Palaiseau", "faganello.nathan@gmail.com", "0679410117", Membre.Abonnement.VIP);
		Livre livre1 = new Livre(1, "La ferme des animaux", "G.Orwell", "123456789");
		Emprunt emprunt1 = new Emprunt(1, nathan, livre1, LocalDate.of(2021,3,9), LocalDate.of(2021,3,15));

		/*System.out.println(nathan);
		System.out.println(livre1);
		System.out.println(emprunt1);*/



		MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();

		List<Membre> listMembres = new ArrayList<>();
		Membre membre = null;
		Livre livre = null;
		Emprunt emprunt = null;
		int created = -1;
		List<Emprunt> listEmprunts = new ArrayList<>();
		List<Livre> listLivres = new ArrayList<>();
		int count = -1;
		try {
			/* Tests sur MembreDao */
			//listMembres = membreDao.getList();
			//membre = membreDao.getById(1);
			//created = membreDao.create("Faganello", "Nathan", "21 rue Maximilien Robespierre, 91120, Palaiseau", "faganello.nathan@gmail.com", "0679410117");
			//membreDao.update(membre);
			//membreDao.delete(1);
			//count = membreDao.count();

			/* Tests sur livreDao */

			//listLivres = livreDao.getList();
			//livre = livreDao.getById(1);
			//created = livreDao.create("La ferme des animaux", "G.Orwell", "123456789");
			//livreDao.update(livre);
			//livreDao.delete(11);
			//count = livreDao.count();

			/* Test sur EmpruntDao */
			//listEmprunts = empruntDao.getList();
			//listEmprunts = empruntDao.getListCurrent();
			//listEmprunts = empruntDao.getListCurrentByMembre(5);
			//listEmprunts = empruntDao.getListCurrentByMembre(1);
			//listEmprunts = empruntDao.getListCurrentByLivre(3);
			//listEmprunts = empruntDao.getListCurrentByLivre(1);
			//emprunt = empruntDao.getById(1);
			//empruntDao.create(1,1, LocalDate.now());
			//empruntDao.update(emprunt);
			count = empruntDao.count();
		}
		catch(DaoException e){
			System.out.println(e.getMessage());
		}

		//System.out.println(created);
		System.out.println(count);

	}
}