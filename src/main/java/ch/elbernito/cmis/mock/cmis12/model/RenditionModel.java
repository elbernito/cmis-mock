package ch.elbernito.cmis.mock.cmis12.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Rendition (e.g. thumbnail, pdf, etc.) of an object.
 */
@Entity
@Table(name = "renditions")
public class RenditionModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(RenditionModel.class);

    /**
     * The id of the object this rendition belongs to.
     */
    @Column(name = "object_id", nullable = false)
    private String objectId;

    /**
     * Kind/type of the rendition (e.g., "thumbnail", "pdf", ...).
     */
    @Column(name = "kind")
    private String kind;

    /**
     * Rendition stream (binary, e.g. image bytes, optional).
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "rendition_stream")
    private byte[] renditionStream;

    // --- Getter/Setter ---
    public String getObjectId() {
        logger.debug("Getting objectId");
        return objectId;
    }
    public void setObjectId(String objectId) {
        logger.debug("Setting objectId: {}", objectId);
        this.objectId = objectId;
    }
    public String getKind() {
        logger.debug("Getting kind");
        return kind;
    }
    public void setKind(String kind) {
        logger.debug("Setting kind: {}", kind);
        this.kind = kind;
    }
    public byte[] getRenditionStream() {
        logger.debug("Getting renditionStream");
        return renditionStream;
    }
    public void setRenditionStream(byte[] renditionStream) {
        logger.debug("Setting renditionStream, length={}", (renditionStream != null ? renditionStream.length : 0));
        this.renditionStream = renditionStream;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
