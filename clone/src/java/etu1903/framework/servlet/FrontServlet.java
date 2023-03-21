/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu1903.framework.servlet;

import static etu1903.framework.Utilitaire.getClasses2;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import etu1903.framework.ClassIdentifier;
import etu1903.framework.Mapping;
import etu1903.framework.Utilitaire;

/**
 *
 * @author ITU
 */
@WebServlet(name = "FrontServlet", urlPatterns = {"/*"})
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    String packageName;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


    public void init() throws ServletException 
    {
        //maka izay rehetra anaty package packageName etu1903 deffinisena tanaty web.xml
        packageName = this.getInitParameter("packageName"); 
        try {
            //mametraka anaty HashMap
            this.mappingUrls=Utilitaire.getAnnotatedMethods(packageName, ClassIdentifier.class);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            //hmtl:5
            out.println("<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">    <title>Document</title></head><body>");
            
            for (Map.Entry<String, Mapping> entry : mappingUrls.entrySet()) {
                String key = entry.getKey();
                Mapping value = entry.getValue();
                out.println("annotation = " + key + "<br>");
                out.println("class Name = " + value.getClassName()+ "<br>");
                out.println("foncttion Name = " + value.getMethod()+ "<br>");
            }

            out.println("</body>");
            out.println("</html>");
                     
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
