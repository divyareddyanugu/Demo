
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
//import org.jdom.output.XMLOutputter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFunctions {

    public String getAllMovies() {
        File file = new File("/home/projxyz/sf-movies.xml");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        String content = null;

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {

                // this statement reads the line from the file and print it to
                // the console.
                content += dis.readLine();
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getAllMovieData() {
        File file = new File("/home/projxyz/sf-movies-compressed.xml");
        //    File file = new File("D:\\sf-movies-compressed.xml");

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        String content = null;

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {

                // this statement reads the line from the file and print it to
                // the console.
                content += dis.readLine();
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;

    }

    public String getMovieData(String name) throws ParserConfigurationException, SAXException {

        try {

            String movieData = this.getAllMovieData();
            movieData = movieData.replace("null", "");
            InputStream is = new ByteArrayInputStream(movieData.getBytes("UTF-8"));

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(is);

            NodeList movieList = doc.getElementsByTagName("movie");

            //Check if there is already an element in the xml file with the same title
            for (int i = 0; i < movieList.getLength(); i++) {
                Node node = movieList.item(i);
                // update staff attribute
                NamedNodeMap attr = node.getAttributes();
                Node nodeAttr = attr.getNamedItem("title");
                if (nodeAttr.getTextContent().equals(name)) {


                    // write the content into xml file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(node);
                    //StreamResult result = new StreamResult(new File(filepath));
                    StringWriter writer = new StringWriter();
                    StreamResult result = new StreamResult(writer);

                    transformer.transform(source, result);

                    return writer.toString();


                }
            }
        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser
            System.out.println("* Transformer Factory error");
            System.out.println("  " + tce.getMessage());

            // Use the contained exception, if any
            Throwable x = tce;
            if (tce.getException() != null) {
                x = tce.getException();
            }
            x.printStackTrace();
        } catch (TransformerException te) {
            // Error generated by the parser
            System.out.println("* Transformation error");
            System.out.println("  " + te.getMessage());

            // Use the contained exception, if any
            Throwable x = te;
            if (te.getException() != null) {
                x = te.getException();
            }
            x.printStackTrace();

        } catch (IOException ex) {
            Logger.getLogger(XMLFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        return "";
    }

    public boolean updateLocCount(String name) throws ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException {

        try {

            String movieData = this.getAllMovies();
            movieData = movieData.replace("null", "");
            InputStream is = new ByteArrayInputStream(movieData.getBytes("UTF-8"));

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(is);

            NodeList movieList = doc.getElementsByTagName("movie");

            //Check if there is already an element in the xml file with the same title
            for (int i = 0; i < movieList.getLength(); i++) {
                Node node = movieList.item(i);
                // update staff attribute
                if(node.getTextContent().equals(name)) {
                NamedNodeMap attr = node.getAttributes();
                Node nodeAttr = attr.getNamedItem("locs");
                int numLocs = Integer.parseInt(nodeAttr.getNodeValue()) + 1;
                nodeAttr.setNodeValue(Integer.toString(numLocs));
                }

            }
 File newFile = new File("/home/projxyz/sf-movies.xml");
        //  File newFile = new File("D:\\sf-movies-compressed.xml");

  /*      BufferedWriter writer1 = null;

            	// write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult(new File(filepath));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            transformer.transform(source, result);
*/
        //    writer1 = new BufferedWriter(new FileWriter(newFile));
          //  writer1.write(writer.toString());
PrintWriter updates = new PrintWriter(new BufferedWriter(new FileWriter(newFile)), true);
updates.println(doc);


        } catch (IOException ex) {
            Logger.getLogger(XMLFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }

        return true;
    }

    public boolean writeFile() {
        InputStream in = null;

        InputStreamReader inputreader = new InputStreamReader(in);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while ((line = buffreader.readLine()) != null) {
                text.append(line);

            }
            FileOutputStream fos = null;
            fos.write(text.toString().getBytes("UTF-8"));
            fos.close();
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    public boolean addMovieTag(String name, String time, String desc, String lat, String lng)
            throws IOException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException {
        File newFile = new File("/home/projxyz/sf-movies-compressed.xml");
        //  File newFile = new File("D:\\sf-movies-compressed.xml");

        BufferedWriter writer1 = null;
        boolean newMovie = true;
        try {

            String movieData = this.getAllMovieData();
            movieData = movieData.replace("null", "");
            InputStream is = new ByteArrayInputStream(movieData.getBytes("UTF-8"));

            //
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Document doc = docBuilder.parse(text.toString());
            org.w3c.dom.Document doc = docBuilder.parse(is);

            Node root = doc.getElementsByTagName("sf-movies").item(0);
            NodeList movieList = doc.getElementsByTagName("movie");

            //Check if there is already an element in the xml file with the same title
            for (int i = 0; i < movieList.getLength(); i++) {
                Node node = movieList.item(i);
                // update staff attribute
                NamedNodeMap attr = node.getAttributes();
                Node nodeAttr = attr.getNamedItem("title");
                if (nodeAttr.getTextContent().equals(name)) {
                    newMovie = false;
                    Element loc = doc.createElement("location");
                    if (lat == null) {
                        lat = "";
                    }
                    loc.setAttribute("lat", lat);
                    if (lng == null) {
                        lng = "";
                    }

                    loc.setAttribute("long", lng);

                    Element timeE = doc.createElement("time");
                    if (time == null) {
                        time = "";
                    }

                    timeE.appendChild(doc.createTextNode(time));

                    Element descE = doc.createElement("description");
                    if (desc == null) {
                        desc = "";
                    }

                    descE.appendChild(doc.createTextNode(desc));

                    loc.appendChild(timeE);
                    loc.appendChild(descE);
                    node.appendChild(loc);
                }
            }

            if (newMovie) {
                Element e = doc.createElement("movie");
                e.setAttribute("title", name);

                Element loc = doc.createElement("location");
                if (lat == null) {
                    lat = "";
                }
                loc.setAttribute("lat", lat);
                if (lng == null) {
                    lng = "";
                }

                loc.setAttribute("long", lng);

                Element timeE = doc.createElement("time");
                if (time == null) {
                    time = "";
                }

                timeE.appendChild(doc.createTextNode(time));

                Element descE = doc.createElement("description");
                if (desc == null) {
                    desc = "";
                }

                descE.appendChild(doc.createTextNode(desc));

                loc.appendChild(timeE);
                loc.appendChild(descE);
                e.appendChild(loc);
                root.appendChild(e);


            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult(new File(filepath));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            transformer.transform(source, result);

            writer1 = new BufferedWriter(new FileWriter(newFile));
            writer1.write(writer.toString());

          //  boolean r= updateLocCount(name);

            // writer.write(data);
        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser
            System.out.println("* Transformer Factory error");
            System.out.println("  " + tce.getMessage());

            // Use the contained exception, if any
            Throwable x = tce;
            if (tce.getException() != null) {
                x = tce.getException();
            }
            x.printStackTrace();
        } catch (TransformerException te) {
            // Error generated by the parser
            System.out.println("* Transformation error");
            System.out.println("  " + te.getMessage());

            // Use the contained exception, if any
            Throwable x = te;
            if (te.getException() != null) {
                x = te.getException();
            }
            x.printStackTrace();

        } catch (IOException ex) {
            Logger.getLogger(XMLFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer1.close();

        }
        return true;
    }
}
