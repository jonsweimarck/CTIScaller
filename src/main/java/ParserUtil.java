import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ParserUtil {

    public static Document xmlToDocument(String xmlString) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new InputSource(new StringReader(xmlString)));
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static List<DocumentMetaData> extractDocumentMetaData(Document allDocumentsXMLdoc) {
        List<DocumentMetaData> returnList = new ArrayList<>();

        NodeList nList = allDocumentsXMLdoc.getElementsByTagName("document");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            Element eElement = (Element) nNode;


            var documentUrlElement =  (Element) eElement.getElementsByTagName("documentUrl").item(0);
            var documentIdElement =  (Element) eElement.getElementsByTagName("documentId").item(0);
            var documentTitleElement =  (Element) eElement.getElementsByTagName("title").item(0);
            var documentSysVersionElement =  (Element) eElement.getElementsByTagName("systemVersion").item(0);
            var documentBusVersionElement =  (Element) eElement.getElementsByTagName("businessVersion").item(0);
            var documentFromDateElement =  (Element) eElement.getElementsByTagName("fromDate").item(0);

            String documentApplicationPartElement = eElement.getElementsByTagName("applicationPart").item(0) != null ?
                    eElement.getElementsByTagName("applicationPart").item(0).getTextContent() : "null";

            String  documentSectionElement =  eElement.getElementsByTagName("section").item(0) != null ?
                    eElement.getElementsByTagName("section").item(0).getTextContent() : "null";


            var documentTypeCodeNodeList =  eElement.getElementsByTagName("documentTypeCode");
            var documentTypeCode = (Element)documentTypeCodeNodeList.item(0);
            var displayNameElement = (Element) documentTypeCode.getElementsByTagName("displayName").item(0);

            returnList.add(new DocumentMetaData(
                    documentUrlElement.getTextContent(),
                    displayNameElement.getTextContent(),
                    documentIdElement.getTextContent(),
                    documentTitleElement.getTextContent(),
                    documentSysVersionElement.getTextContent(),
                    documentBusVersionElement.getTextContent(),
                    documentFromDateElement.getTextContent(),
                    documentApplicationPartElement,
                    documentSectionElement));
        }
        return returnList;
    }
}
