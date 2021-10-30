import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

public class RestClient {

    static final String baseURL = "https://euctis-uat.ema.europa.eu/ct-authority-services/services/v1";

    private final String user;
    private final String password;

    public RestClient(String user, String password) {
        this.user = user;
        this.password = password;

        makeClientIgnoreServerCertificates();
    }


    public Document getAllDocumentsXML(String clinicalTrialId, String applicationId) throws UnirestException, ParserConfigurationException, IOException, SAXException {

        var allDocumentsURL = baseURL + "/clinicalTrials/" + clinicalTrialId + "/applications/" + applicationId + "/part1/documents?pagesize=30";
        var response = Unirest.get(allDocumentsURL).basicAuth(user, password).asString();

        return ParserUtil.xmlToDocument(response.getBody());
    }


    public List<DocumentHttpStatus> checkDocumentHttpStatus(String clinicalTrialId, String applicationId, List<DocumentMetaData> documentMetaDatas) {
        return documentMetaDatas.stream().map(documentMetaData -> checkSingleDocumentHttpStatus(clinicalTrialId, applicationId, documentMetaData)).collect(Collectors.toList());
    }

    private DocumentHttpStatus checkSingleDocumentHttpStatus(String clinicalTrialId, String applicationId, DocumentMetaData documentMetaData) {
        try {
            var singleDocumentURL = baseURL + "/clinicalTrials/" + clinicalTrialId + "/applications/" + applicationId + "/part1/documents/" + documentMetaData.documentUrl();
            var response = Unirest.get(singleDocumentURL).basicAuth(user, password).asString();
            return new DocumentHttpStatus(documentMetaData, new HttpStatus(response.getStatus(), response.getStatusText()));
        } catch (UnirestException e) {
            e.printStackTrace();
            return new DocumentHttpStatus(documentMetaData, null);
        }
    }

    // This will make Unirest ignore certificates,
    // to avoid having to import the server cert to the local java keystore at lib\security\cacerts.
    // Taken from one of the answers at https://stackoverflow.com/questions/23242197/how-to-make-unirestjava-ignore-certificate-error
    // This is of course not recommended to do ...
    private void makeClientIgnoreServerCertificates() {
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        Unirest.setHttpClient(httpclient);
    }
}
