package ch.elbernito.cmismock.cmis12.model;

import ch.elbernito.cmis.mock.cmis12.model.CmisObjectModel;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity representing a CMIS 1.2 Policy object.
 */
@Entity
@Table(name = "policies")
public class PolicyModel extends CmisObjectModel {
    private static final Logger logger = LoggerFactory.getLogger(PolicyModel.class);

    /**
     * Policy text (optional).
     */
    @Column(name = "policy_text")
    private String policyText;

    // --- Getter/Setter ---
    public String getPolicyText() {
        logger.debug("Getting policyText");
        return policyText;
    }
    public void setPolicyText(String policyText) {
        logger.debug("Setting policyText: {}", policyText);
        this.policyText = policyText;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
