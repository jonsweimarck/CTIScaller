import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestClient {

    static final String baseURL = "https://euctis-uat.ema.europa.eu/ct-authority-services/services/v1";

    private final String user;
    private final String password;

    public RestClient(String user, String password) {
        this.user = user;
        this.password = password;
    }


    public Document getAllDocumentsXML(String clinicalTrialId, String applicationId) throws UnirestException, ParserConfigurationException, IOException, SAXException {
        var allDocumentsURL = baseURL + "/clinicalTrials/" + clinicalTrialId + "/applications/" + applicationId + "/part1/documents?pagesize=30";
        var response = Unirest.get(allDocumentsURL).basicAuth(user, password).asString();

        return ParserUtil.xmlToDocument(response.getBody());
    }


    public List<Optional<DocumentHttpStatus>> checkDocumentHttpStatus(String clinicalTrialId, String applicationId, List<DocumentMetaData> documentMetaDatas) {
        return documentMetaDatas.stream().map(documentMetaData -> checkSingleDocumentHttpStatus(clinicalTrialId, applicationId, documentMetaData)).collect(Collectors.toList());
    }

    private Optional<DocumentHttpStatus> checkSingleDocumentHttpStatus(String clinicalTrialId, String applicationId, DocumentMetaData documentMetaData) {
        try {
            var singleDocumentURL = baseURL + "/clinicalTrials/" + clinicalTrialId + "/applications/" + applicationId + "/part1/documents/" + documentMetaData.documentUrl();
//            var singleDocumentURL = baseURL + "/clinicalTrials/" + clinicalTrialId + "/documents/" + documentMetaData.documentUrl();
            var response = Unirest.get(singleDocumentURL).basicAuth(user, password).asString();
            return Optional.of(new DocumentHttpStatus(documentMetaData, new HttpStatus(response.getStatus(), response.getStatusText())));
        } catch (UnirestException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
