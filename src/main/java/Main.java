import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);

        String json = listToJson(list);
        writeString(json, "data.json");
    }

    private static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                Element element = (Element) node_;
                list.add(new Employee
                        (Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()),
                                element.getElementsByTagName("firstName").item(0).getTextContent(),
                                element.getElementsByTagName("lastName").item(0).getTextContent(),
                                element.getElementsByTagName("country").item(0).getTextContent(),
                                Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent())));
            }
        }
        return list;
    }


        private static String listToJson (List < Employee > list) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Type listType = new TypeToken<List<Employee>>() {
            }.getType();
            return gson.toJson(list, listType);
        }

        public static void writeString (String fileName, String json){
            try (FileWriter file = new
                    FileWriter(fileName)) {
                file.write(json);
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





