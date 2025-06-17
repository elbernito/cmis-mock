package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.model.AclModel;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.repository.AclRepository;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.service.AclService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AclServiceImpl implements AclService {

    private final Logger log = LoggerFactory.getLogger(AclServiceImpl.class);
    private final AclRepository aclRepository;
    private final DocumentRepository documentRepository;

    @Override
    public AclDto createAcl(AclDto dto) {
        log.info("Creating ACL: {}", dto);
        AclModel acl = new AclModel();
        acl.setPrincipal(dto.getPrincipal());
        acl.setPermission(dto.getPermission());

        // Documents
        Set<DocumentModel> docs = new HashSet<DocumentModel>();
        if (dto.getDocumentIds() != null) {
            for (Long docId : dto.getDocumentIds()) {
                Optional<DocumentModel> doc = documentRepository.findById(docId);
                if (doc.isPresent()) {
                    docs.add(doc.get());
                }
            }
        }
        acl.setDocuments(docs);

        acl = aclRepository.save(acl);
        dto.setId(acl.getId());
        return dto;
    }

    @Override
    public AclDto getAcl(Long id) {
        AclModel acl = aclRepository.findById(id).orElseThrow();
        AclDto dto = new AclDto();
        dto.setId(acl.getId());
        dto.setPrincipal(acl.getPrincipal());
        dto.setPermission(acl.getPermission());
        Set<Long> docIds = new HashSet<Long>();
        for (DocumentModel doc : acl.getDocuments()) {
            docIds.add(doc.getId());
        }
        dto.setDocumentIds(docIds);
        return dto;
    }

    @Override
    public List<AclDto> getAllAcls() {
        List<AclDto> list = new ArrayList<AclDto>();
        List<AclModel> all = aclRepository.findAll();
        for (AclModel acl : all) {
            list.add(getAcl(acl.getId()));
        }
        return list;
    }

    @Override
    public void deleteAcl(Long id) {
        aclRepository.deleteById(id);
    }

    @Override
    public AclDto updateAcl(Long id, AclDto dto) {
        AclModel acl = aclRepository.findById(id).orElseThrow();
        acl.setPrincipal(dto.getPrincipal());
        acl.setPermission(dto.getPermission());
        Set<DocumentModel> docs = new HashSet<DocumentModel>();
        if (dto.getDocumentIds() != null) {
            for (Long docId : dto.getDocumentIds()) {
                Optional<DocumentModel> doc = documentRepository.findById(docId);
                if (doc.isPresent()) {
                    docs.add(doc.get());
                }
            }
        }
        acl.setDocuments(docs);
        aclRepository.save(acl);
        return getAcl(id);
    }
}
