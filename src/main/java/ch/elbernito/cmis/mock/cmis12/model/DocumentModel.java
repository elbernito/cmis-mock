package ch.elbernito.cmismock.cmis12.model;

import ch.elbernito.cmis.mock.cmis12.model.CmisObjectModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Document object.
 */
@Entity
@Table(name = "documents")
public class DocumentModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(DocumentModel.class);

    /**
     * The parent folder id (optional, null for unfiled).
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * The content stream (binary content, optional).
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content_stream")
    private byte[] contentStream;

    /**
     * MIME type (optional).
     */
    @Column(name = "mime_type")
    private String mimeType;

    /**
     * Version label (CMIS property: cmis:versionLabel, optional).
     */
    @Column(name = "version_label")
    private String versionLabel;

    /**
     * Is this document the latest version?
     */
    @Column(name = "is_latest_version")
    private Boolean isLatestVersion;

    /**
     * Document version series id.
     */
    @Column(name = "version_series_id")
    private String versionSeriesId;

    // --- Getter/Setter ---
    public String getParentId() {
        logger.debug("Getting parentId");
        return parentId;
    }
    public void setParentId(String parentId) {
        logger.debug("Setting parentId: {}", parentId);
        this.parentId = parentId;
    }
    public byte[] getContentStream() {
        logger.debug("Getting contentStream");
        return contentStream;
    }
    public void setContentStream(byte[] contentStream) {
        logger.debug("Setting contentStream, length={}", (contentStream != null ? contentStream.length : 0));
        this.contentStream = contentStream;
    }
    public String getMimeType() {
        logger.debug("Getting mimeType");
        return mimeType;
    }
    public void setMimeType(String mimeType) {
        logger.debug("Setting mimeType: {}", mimeType);
        this.mimeType = mimeType;
    }
    public String getVersionLabel() {
        logger.debug("Getting versionLabel");
        return versionLabel;
    }
    public void setVersionLabel(String versionLabel) {
        logger.debug("Setting versionLabel: {}", versionLabel);
        this.versionLabel = versionLabel;
    }
    public Boolean getIsLatestVersion() {
        logger.debug("Getting isLatestVersion");
        return isLatestVersion;
    }
    public void setIsLatestVersion(Boolean isLatestVersion) {
        logger.debug("Setting isLatestVersion: {}", isLatestVersion);
        this.isLatestVersion = isLatestVersion;
    }
    public String getVersionSeriesId() {
        logger.debug("Getting versionSeriesId");
        return versionSeriesId;
    }
    public void setVersionSeriesId(String versionSeriesId) {
        logger.debug("Setting versionSeriesId: {}", versionSeriesId);
        this.versionSeriesId = versionSeriesId;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
