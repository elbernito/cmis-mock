package ch.elbernito.cmis.mock.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class StatisticsDto {
    private long documentCount;
    private long folderCount;
    private long objectCount;
    private long storageSize;

    // Getter & Setter

    public long getDocumentCount() { return documentCount; }
    public void setDocumentCount(long documentCount) { this.documentCount = documentCount; }

    public long getFolderCount() { return folderCount; }
    public void setFolderCount(long folderCount) { this.folderCount = folderCount; }

    public long getObjectCount() { return objectCount; }
    public void setObjectCount(long objectCount) { this.objectCount = objectCount; }

    public long getStorageSize() { return storageSize; }
    public void setStorageSize(long storageSize) { this.storageSize = storageSize; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
