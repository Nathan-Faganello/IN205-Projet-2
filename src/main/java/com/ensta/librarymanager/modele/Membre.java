package com.ensta.librarymanager.modele;

public class Membre {

	public enum Abonnement{
		BASIC, PREMIUM, VIP
	}

	private int id;
	private String nom;
	private String prenom;
	private String adresse;
	private String email;
	private String telephone;
	private Abonnement abonnement;

	//liste de tous les getters
	public int getId(){
		return id;
	}

	public String getNom(){
		return nom;
	}

	public String getPrenom(){
		return prenom;
	}

	public String getAdresse(){
		return adresse;
	}

	public String getEmail(){
		return email;
	}

	public String getTelephone(){
		return telephone;
	}

	public Abonnement getAbonnement(){
		return abonnement;
	}

	//liste de tous les setters
	public void setId(int n){
		this.id=n;
	}

	public void setNom(String nom){
		this.nom=nom;
	}

	public void setPrenom(String prenom){
		this.prenom=prenom;
	}

	public void setAdresse(String adresse){
		this.adresse=adresse;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public void setTelephone(String telephone){
		this.telephone=telephone;
	}

	public void setAbonnement(Abonnement abonnement){
		this.abonnement=abonnement;
	}

	

	//constructeur
	public Membre(int id, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) {
		this.id = id;
		this.nom=nom;
		this.prenom=prenom;
		this.adresse=adresse;
		this.email=email;
		this.telephone=telephone;
		this.abonnement=abonnement;
	}

	public Membre() {
		super();
	}

	//toString() redéfini
	@Override
	public String toString(){
		return "Client n°"+id+"\n    Nom : "+nom+"\n    Prenom : "+prenom+"\n    adresse : "+adresse+"\n    email : "+email+"\n    telephone : "+telephone+"\n    abonnement: "+abonnement+"\n";
	}

}