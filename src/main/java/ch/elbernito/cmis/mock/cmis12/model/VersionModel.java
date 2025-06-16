package ch.elbernito.cmismock.cmis12.model;

import ch.elbernito.cmis.mock.cmis12.model.CmisObjectModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Version object.
 */
@Entity
@Table(name = "versions")
public class VersionModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(VersionModel.class);

    /**
     * The id of the document this version belongs to.
     */
    @Column(name = "document_id", nullable = false)
    private String documentId;

    /**
     * Version label (optional).
     */
    @Column(name = "version_label")
    private String versionLabel;

    // --- Getter/Setter ---
    public String getDocumentId() {
        logger.debug("Getting documentId");
        return documentId;
    }
    public void setDocumentId(String documentId) {
        logger.debug("Setting documentId: {}", documentId);
        this.documentId = documentId;
    }
    public String getVersionLabel() {
        logger.debug("Getting versionLabel");
        return versionLabel;
    }
    public void setVersionLabel(String versionLabel) {
        logger.debug("Setting versionLabel: {}", versionLabel);
        this.versionLabel = versionLabel;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
