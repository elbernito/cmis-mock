package ch.elbernito.cmis.mock.cmis12.dto;

import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for CMIS QueryResult (search results).
 */
public class QueryResultDto {
    private List<ObjectDataDto> results;
    private Integer numItems;
    private Boolean hasMoreItems;

    public List<ObjectDataDto> getResults() { return results; }
    public void setResults(List<ObjectDataDto> results) { this.results = results; }
    public Integer getNumItems() { return numItems; }
    public void setNumItems(Integer numItems) { this.numItems = numItems; }
    public Boolean getHasMoreItems() { return hasMoreItems; }
    public void setHasMoreItems(Boolean hasMoreItems) { this.hasMoreItems = hasMoreItems; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
