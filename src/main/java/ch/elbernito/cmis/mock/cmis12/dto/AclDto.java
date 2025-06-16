package ch.elbernito.cmis.mock.cmis12.dto;

import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * DTO for CMIS ACL.
 */
public class AclDto {
    private List<AceDto> aceList;

    public List<AceDto> getAceList() { return aceList; }
    public void setAceList(List<AceDto> aceList) { this.aceList = aceList; }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static class AceDto {
        private String principal;
        private String permission;

        public String getPrincipal() { return principal; }
        public void setPrincipal(String principal) { this.principal = principal; }
        public String getPermission() { return permission; }
        public void setPermission(String permission) { this.permission = permission; }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }
}
