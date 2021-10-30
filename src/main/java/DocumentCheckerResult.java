import java.util.List;
import java.util.stream.Collectors;

public class DocumentCheckerResult {

    private List<DocumentHttpStatus> documentHttpStatuses;

    public DocumentCheckerResult(List<DocumentHttpStatus> documentHttpStatuses) {
        this.documentHttpStatuses = documentHttpStatuses;
    }


    public String getResult() {
        var header = formatLine("documentType", "docId", "documentUrl", "title", "sysVers", "busVers", "appPart", "section", "httpStat");
        var unsortedList = documentHttpStatuses.stream()
                .map(entry -> formatResultLine(entry)).toList()
                .stream().sorted().toList();
        return header + "\n" + unsortedList.stream().collect(Collectors.joining("\n"));

    }

    private String formatLine(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9) {
//        return String.format("%s%s%s",
//                padwithSpaces(s1, 85),
//                padwithSpaces(s2, 10), // docId
//                padwithSpaces(s3, 40));

//        return String.format("%s%s%s%s%s%s",
//                padwithSpaces(s1, 85),
//                padwithSpaces(s2, 10), // docId
//                padwithSpaces(s3, 40),
//                padwithSpaces(s4, 50), // title
//                padwithSpaces(s5, 15),
//                padwithSpaces(s6, 15));
        return String.format("%s%s%s%s%s%s%s%s%s",
                padwithSpaces(s1, 85), // title
                padwithSpaces(s2, 10), // docId
                padwithSpaces(s3, 40), // documentUrl
                padwithSpaces(s4, 50), // title
                padwithSpaces(s5, 15), // sysVers
                padwithSpaces(s6, 15), // busVers
                padwithSpaces(s9, 10), // httpStatus
                padwithSpaces(s7, 10), // appPart
                padwithSpaces(s8, 10)); // section

    }

    private String formatResultLine(DocumentHttpStatus dhs) {
        return formatLine(
                dhs.documentMetaData().displayName(),
                dhs.documentMetaData().documentId(),
                dhs.documentMetaData().documentUrl(),
                dhs.documentMetaData().title(),
                dhs.documentMetaData().systemVersion(),
                dhs.documentMetaData().businessVersion(),
                dhs.documentMetaData().applicationPart(),
                dhs.documentMetaData().section(),
                dhs.httpStatus() != null ? String.valueOf(dhs.httpStatus().status()) : "***** Kunde inte ansluta *****");
    }


    private String padwithSpaces(String inputString, int finalLength){
        return String.format("%1$-" + finalLength + "s", inputString);
    }
}
