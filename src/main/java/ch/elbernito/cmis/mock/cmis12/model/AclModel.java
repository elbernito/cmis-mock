package ch.elbernito.cmismock.cmis12.model;

import ch.elbernito.cmis.mock.cmis12.model.CmisObjectModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Access Control List (ACL) entry.
 */
@Entity
@Table(name = "acls")
public class AclModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(AclModel.class);

    /**
     * The principal (user or group).
     */
    @Column(name = "principal", nullable = false)
    private String principal;

    /**
     * The permission string (e.g., "cmis:read").
     */
    @Column(name = "permission", nullable = false)
    private String permission;

    // --- Getter/Setter ---
    public String getPrincipal() {
        logger.debug("Getting principal");
        return principal;
    }
    public void setPrincipal(String principal) {
        logger.debug("Setting principal: {}", principal);
        this.principal = principal;
    }
    public String getPermission() {
        logger.debug("Getting permission");
        return permission;
    }
    public void setPermission(String permission) {
        logger.debug("Setting permission: {}", permission);
        this.permission = permission;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
