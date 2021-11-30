import java.time.format.DateTimeFormatter;

public class DocumentChecker {

    static final String user = "";
    static final String password = "";

    public static void main(String[] args) throws Exception {

        var ids = ClinicalTrialIdMap.get(ClinicalTrialIdMap.AUDIT_JW4);

        var restclient = new RestClient(user, password);

        var allDocumentsXMLdoc = restclient.getAllPart2DocumentsXML(ids.cliniclaTrialId(), ids.applicationId(), ids.part2Id());
        var documentMetaDatas = ParserUtil.extractDocumentMetaData(allDocumentsXMLdoc);
        var documentHttpStatuses = restclient.checkPart2DocumentHttpStatus(ids.cliniclaTrialId(), ids.applicationId(), ids.part2Id(), documentMetaDatas);

        var result = new DocumentCheckerResult(documentHttpStatuses);
        System.out.println(header(ids));
        System.out.println(result.getResult());
    }

    private static String header(ClinicalTrialIds ids) {

        return String.format("\n\n%s\n[%s] Försöker hämta alla dokument för part1 av prövningen '%s' (%s)\n%s",
                "********************************************************************************************************************************",
                java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm")),
                ids.name(),
                ids.cliniclaTrialId(),
                "********************************************************************************************************************************");
    }
}
