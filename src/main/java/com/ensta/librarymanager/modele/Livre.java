package com.ensta.librarymanager.modele;

public class Livre {
	private int id;
	private String titre;
	private String auteur;
	private String isbn;

	//liste de tous les getters
	public int getId() {
		return id;
	}

	public String getTitre(){
		return titre;
	}

	public String getAuteur(){
		return auteur;
	}

	public String getIsbn(){
		return isbn;
	}

	//liste de tous les setters
	public void setId(int n){
		this.id=n;
	}

	public void setTitre(String titre) {
		this.titre=titre;
	}

	public void setAuteur(String auteur){
		this.auteur=auteur;
	}

	public void setIsbn(String isbn){
		this.isbn=isbn;
	}

	//Constructeur
	public Livre(int id, String titre, String auteur, String isbn) {
		this.id = id;
		this.titre=titre;
		this.auteur=auteur;
		this.isbn=isbn;
	}

	public Livre() {
		super();
	}

	//toString() redéfini
	@Override
	public String toString(){
		return "Livre n°"+id+", "+titre+", "+auteur+", isbn : "+isbn+"\n";
	}
}