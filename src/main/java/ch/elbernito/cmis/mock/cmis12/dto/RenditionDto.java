package ch.elbernito.cmis.mock.cmis12.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for CMIS Rendition.
 */
public class RenditionDto {
    private String renditionId;
    private String kind;
    private String mimeType;
    private Long length;
    private String title;
    private String streamId;

    public String getRenditionId() { return renditionId; }
    public void setRenditionId(String renditionId) { this.renditionId = renditionId; }
    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public Long getLength() { return length; }
    public void setLength(Long length) { this.length = length; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStreamId() { return streamId; }
    public void setStreamId(String streamId) { this.streamId = streamId; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
