import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;

public class DocumentCheckerResult {

    private List<DocumentMetaData> documentMetaDatas;
    private List<Optional<DocumentHttpStatus>> documentHttpStatuses;

    public DocumentCheckerResult(List<DocumentMetaData> documentMetaDatas, List<Optional<DocumentHttpStatus>> documentHttpStatuses) {
        this.documentMetaDatas = documentMetaDatas;
        this.documentHttpStatuses = documentHttpStatuses;
    }

    public String getResult() {
        var header = formatLine("documentType", "docId", "documentUrl", "title", "sysVers", "busVers", "httpStatus");
        var unsortedMap = documentMetaDatas.stream().collect(Collectors.toMap(metaData -> metaData, metaData -> findHttpStatus(metaData,documentHttpStatuses ) ));
        var unsortedList = unsortedMap.entrySet().stream()
                .map(entry -> formatResultLine(entry)).toList()
                .stream().sorted().toList();
        return header + "\n" + unsortedList.stream().collect(Collectors.joining("\n"));

    }

    private String formatLine(String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
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
        return String.format("%s%s%s%s%s%s%s",
                padwithSpaces(s1, 85),
                padwithSpaces(s2, 10), // docId
                padwithSpaces(s3, 40),
                padwithSpaces(s4, 50), // title
                padwithSpaces(s5, 15),
                padwithSpaces(s6, 15),
                s7);
    }

    private String formatResultLine(Map.Entry<DocumentMetaData, Optional<DocumentHttpStatus>> entry) {
        return formatLine(
                entry.getKey().displayName(),
                entry.getKey().documentId(),
                entry.getKey().documentUrl(),
                entry.getKey().title(),
                entry.getKey().systemVersion(),
                entry.getKey().businessVersion(),
                entry.getValue().isPresent() ? entry.getValue().get().httpStatus().toString() : "***** Kunde inte ansluta *****");
    }

    private Optional<DocumentHttpStatus> findHttpStatus(DocumentMetaData metaData, List<Optional<DocumentHttpStatus>> documentHttpStatuses) {
        return documentHttpStatuses.stream().filter(dhs-> dhs.isPresent() && dhs.get().documentMetaData().equals(metaData)).findFirst().orElse(Optional.empty());
    }

    private String padwithSpaces(String inputString, int finalLength){
        return String.format("%1$-" + finalLength + "s", inputString);
    }
}
