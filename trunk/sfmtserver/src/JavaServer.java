
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.*;
import org.apache.xmlrpc.server.XmlRpcServer;

/**
 * @author Graham O'Regan @ <a href="http://www.ellisonbrookes.com">Ellison Brookes</a>
 */
public class JavaServer extends HttpServlet {

    /**
     * Create an instance of an XML-RPC server and register the
     * EchoHandler with it under the name "echoHandler".
     *
     * Then handle a request to the XML-RPC server, gather the data posted
     * to the servlet and then write the response to the output stream
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        XmlRpcServer server = new XmlRpcServer();
        server.addHandler("xmlHandler", new XMLHandler());

        byte[] result = server.execute(request.getInputStream());

        String res = new String(result);
        response.setContentType("text/xml");
        response.setContentLength(result.length);
        OutputStream out = response.getOutputStream();
        out.write(result);
        out.flush();
    }

}