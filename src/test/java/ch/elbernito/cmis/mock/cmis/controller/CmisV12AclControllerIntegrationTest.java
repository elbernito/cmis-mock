package ch.elbernito.cmis.mock.cmis.controller;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.model.AclModel;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.repository.AclRepository;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CmisV12AclControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AclRepository aclRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DocumentModel testObj;
    private AclModel acl1;
    private AclModel acl2;

    @BeforeEach
    void setup() {
        aclRepository.deleteAll();
        documentRepository.deleteAll();

        // Dokument anlegen
        testObj = new DocumentModel();
        testObj.setDocumentId("object-1");
        testObj.setName("Test Object");
        testObj.setMimeType("text/plain");
        testObj.setCreatedAt(LocalDateTime.now());
        documentRepository.save(testObj);

        // ACL-Einträge anlegen (jetzt als Liste)
        acl1 = AclModel.builder()
                .aclId(UUID.randomUUID().toString())
                .objectId("object-1")
                .principal("userA")
                .permissions(List.of("cmis:read", "cmis:write")) // List!
                .creationDate(LocalDateTime.now())
                .build();
        aclRepository.save(acl1);

        acl2 = AclModel.builder()
                .aclId(UUID.randomUUID().toString())
                .objectId("object-1")
                .principal("userB")
                .permissions(List.of("cmis:read")) // List!
                .creationDate(LocalDateTime.now())
                .build();
        aclRepository.save(acl2);
    }

    @Test
    void testWhenGettingAclForExistingObjectThenReturnAllAcls() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/{objectId}/acl", "object-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.aclId == '%s')]", acl1.getAclId()).exists())
                .andExpect(jsonPath("$[?(@.aclId == '%s')]", acl2.getAclId()).exists());
    }

    @Test
    void testWhenGettingAclForNonExistingObjectThenReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/cmis/v1.2/objects/{objectId}/acl", "does-not-exist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testWhenSettingAclThenReturnUpdatedAcl() throws Exception {
        // Erzeuge neuen AclDto für userC (als Liste, CMIS-konform)
        AclDto aclDto = new AclDto();
        aclDto.setObjectId("object-1");
        aclDto.setPrincipal("userC");
        aclDto.setPermissions(List.of("cmis:read", "cmis:write", "cmis:all"));

        String response = mockMvc.perform(put("/api/cmis/v1.2/objects/{objectId}/acl", "object-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aclDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.objectId").value("object-1"))
                .andExpect(jsonPath("$.principal").value("userC"))
                .andExpect(jsonPath("$.permissions", containsInAnyOrder("cmis:read", "cmis:write", "cmis:all")))
                .andExpect(jsonPath("$.aclId").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        // Prüfe, dass der neue ACL in der DB steht
        List<AclModel> found = aclRepository.findByObjectIdAndPrincipal("object-1", "userC");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getPermissions()).containsExactlyInAnyOrder("cmis:read", "cmis:write", "cmis:all");
    }
}
