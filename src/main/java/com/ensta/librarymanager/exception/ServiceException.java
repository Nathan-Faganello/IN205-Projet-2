package com.ensta.librarymanager.exception;

public class ServiceException extends Exception {
	public ServiceException(){
		super();
	}
	public ServiceException(String param) {
		super(param);
	}
	public ServiceException(String param, Throwable e) {
		super(param, e);
	}
}