/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author Divya
 */
public class SFServer extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
             */            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet XMLFunctions</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet XMLFunctions at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        // PrintWriter out = response.getWriter();
        XMLFunctions xml = new XMLFunctions();
        byte[] bytes = null;
        String command = request.getParameter("command");
        ServletOutputStream out = response.getOutputStream();
        if (command.equals("getAllMovies")) {

            response.setContentType("text/xml");
            bytes = xml.getAllMovies().getBytes();
            response.setContentLength(bytes.length);
            out.write(bytes, 0, bytes.length);

        } else if (command.equals("getMovieData")) {
            response.setContentType("text/xml");
            String title = request.getParameter("movieName");
            if (title == null) {
                bytes = xml.getAllMovieData().getBytes();
            } else {
                try {
                    bytes = xml.getMovieData(title).getBytes();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            response.setContentLength(bytes.length);
            out.write(bytes, 0, bytes.length);


        } else if (command.equals("addMovieTag")) {
            String movieName = request.getParameter("movieName");
            String time = request.getParameter("time");
            String desc = request.getParameter("desc");
            String lat = request.getParameter("lat");
            String lng = request.getParameter("lng");
            try {
                boolean res = xml.addMovieTag(movieName, time, desc, lat, lng);
                out.print("results");
                //  out.write("");
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
        out.flush();
        out.close();
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             //for saving the file name
String saveFileTo   =  "/var/lib/tomcat6/webapps/sfmtServer/img/";
String error = "/home/projxyz/img/error.txt";
FileWriter fstream = new FileWriter(error);
  BufferedWriter out = new BufferedWriter(fstream);
  List<FileItem> items = null;
        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            //out.write(items.size());
       for (FileItem item : items) {
        //   f.write(item.getFieldName().getBytes());
        if (item.getFieldName().contains("myImage")) {

            //String fileName = FilenameUtils.getName(item.getName());
          //  String fileContentType = item.getContentType();
            InputStream fileContent = item.getInputStream();
            
final BufferedImage bufferedImage = ImageIO.read(item.getInputStream());
String fileName = item.getFieldName();
String names[] = fileName.split("tt");
fileName = names[1];
out.write(fileName);
if (fileName == null || fileName.equals("null")) {
    fileName = "temp.jpg";
} else {
    fileName = "tt"+fileName + ".jpg";

}
File imageFile = new File(saveFileTo+fileName);
ImageIO.write(bufferedImage, "jpg",imageFile);
    
        }
    }
       out.flush();
       out.close();
       fstream.close();

     } catch (FileUploadException ex) {
            Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
        }
   catch (Exception ex) {
            Logger.getLogger(SFServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
