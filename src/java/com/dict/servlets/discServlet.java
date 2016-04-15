package com.dict.servlets;

import com.disc.jdbc.DBConnection;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author yubraj
 */
@WebServlet(name = "discServlet", urlPatterns = {"/discServlet"})
public class discServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
        
         System.out.println("inside the servlet");
         ArrayList<Words> map = new ArrayList<Words>();
         String searchedWord = request.getParameter("word");
         System.out.println("Searched Keyword: "+searchedWord);
        
        try {
            Connection conn = DBConnection.getConnection();
            map = getData(conn, request, response, searchedWord);
            write(response, map);
            
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(discServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(discServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void write(HttpServletResponse response, ArrayList<Words> map) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(map));
    }
    
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private ArrayList getData(Connection conn, HttpServletRequest request, HttpServletResponse response, String searchedWord) throws SQLException, Exception {        
        ArrayList messageData = new ArrayList();
        
        String selectSQL = "SELECT * FROM entries WHERE word = ? order by wordtype";
        PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
        preparedStatement.setString(1, searchedWord);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Words words = new Words();
            words.setWord(rs.getString("word"));
            words.setWordtype(rs.getString("wordtype"));
            words.setDefination(rs.getString("definition"));
            messageData.add(words);
        }
        return messageData;
        
        
    }
   

}
