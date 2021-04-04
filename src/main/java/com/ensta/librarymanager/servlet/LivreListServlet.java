package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.*;


public class LivreListServlet extends HttpServlet{

    MembreServiceImpl membreService = MembreServiceImpl.getInstance();
    LivreServiceImpl livreService = LivreServiceImpl.getInstance();
    EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_list.jsp");


        List<Livre> listeLivres = livreService.getList();
        request.setAttribute("listeLivres", listeLivres);

        
        dispatcher.forward(request, response);

        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    

}
