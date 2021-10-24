import java.util.Objects;

public record DocumentMetaData(String documentUrl, String displayName, String documentId, String title, String systemVersion, String businessVersion, String fromDate) implements Comparable {



    @Override
    public int compareTo(Object o) {
        return this.displayName().compareTo(((DocumentMetaData)o).displayName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentMetaData that = (DocumentMetaData) o;
        return documentUrl.equals(that.documentUrl) &&
                displayName.equals(that.displayName) &&
                documentId.equals(that.documentId) &&
                title.equals(that.title) &&
                systemVersion.equals(that.systemVersion) &&
                businessVersion.equals(that.businessVersion) &&
                fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentUrl, displayName, documentId, title, systemVersion, businessVersion, fromDate);
    }

}
