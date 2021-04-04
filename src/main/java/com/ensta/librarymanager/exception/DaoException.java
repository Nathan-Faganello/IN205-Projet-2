package com.ensta.librarymanager.exception;

public class DaoException extends Exception {
	public DaoException(){
		super();
	}
	public DaoException(String param) {
		super(param);
	}
	public DaoException(String param, Throwable e) {
		super(param, e);
	}
}
