package com.ensta.librarymanager.modele;
import java.time.LocalDate;

public class Emprunt {

	private int id;
	private Membre membre;
	private Livre livre;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;

	//liste de tous les getters
	public int getId() {
		return this.id;
	}

	public Membre getMembre(){
		return this.membre;
	}

	public Livre getLivre(){
		return this.livre;
	}

	public LocalDate getDateEmprunt(){
		return this.dateEmprunt;
	}

	public LocalDate getDateRetour(){
		return this.dateRetour;
	}

	//liste de tous les setters
	public void setId(int n) {
		this.id=n;
	}

	public void setMembre(Membre member) {
		this.membre=member;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt=dateEmprunt;
	}

	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour=dateRetour;
	}

	//Constructeur

	public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
		this.id = id;
		this.membre=membre;
		this.livre=livre;
		this.dateEmprunt=dateEmprunt;
		this.dateRetour=dateRetour;
	}


	public Emprunt() {
		super();
	}

	@Override
	//toString() redéfini
	public String toString() {
		return "Emprunt n°"+id+" :\n     Membre : "+membre+"\n     Livre : "+livre+"\n     Date d'emprunt : "+dateEmprunt+"\n     Date de retour : "+dateRetour+"\n";
	}
}