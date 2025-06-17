package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.VersionDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.model.VersionModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.VersionRepository;
import ch.elbernito.cmis.mock.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VersionServiceImpl implements VersionService {

    private final Logger log = LoggerFactory.getLogger(VersionServiceImpl.class);

    private final VersionRepository versionRepository;
    private final DocumentRepository documentRepository;

    public VersionServiceImpl(VersionRepository versionRepository, DocumentRepository documentRepository) {
        this.versionRepository = versionRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public VersionDto createVersion(VersionDto dto) {
        log.info("Creating version: {}", dto);

        DocumentModel doc = documentRepository.findById(dto.getDocumentId())
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        VersionModel model = new VersionModel();
        model.setDocument(doc);
        model.setVersionLabel(dto.getVersionLabel());
        model.setMajorVersion(dto.isMajorVersion());
        model.setCreationDate(dto.getCreationDate());
        model.setCreatedBy(dto.getCreatedBy());
        model.setComment(dto.getComment());

        VersionModel saved = versionRepository.save(model);

        VersionDto result = toDto(saved);
        log.debug("Created version: {}", result);
        return result;
    }

    @Override
    public VersionDto getVersion(Long id) {
        VersionModel model = versionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Version not found"));
        return toDto(model);
    }

    @Override
    public List<VersionDto> getVersionsByDocumentId(Long documentId) {
        return versionRepository.findByDocumentId(documentId).stream()
                .map(VersionServiceImpl::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VersionDto updateVersion(Long id, VersionDto dto) {
        VersionModel model = versionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Version not found"));
        model.setVersionLabel(dto.getVersionLabel());
        model.setMajorVersion(dto.isMajorVersion());
        model.setComment(dto.getComment());
        VersionModel saved = versionRepository.save(model);
        return toDto(saved);
    }

    @Override
    public void deleteVersion(Long id) {
        versionRepository.deleteById(id);
    }

    static VersionDto toDto(VersionModel model) {
        VersionDto dto = new VersionDto();
        dto.setId(model.getId());
        dto.setDocumentId(model.getDocument().getId());
        dto.setVersionLabel(model.getVersionLabel());
        dto.setMajorVersion(model.isMajorVersion());
        dto.setCreationDate(model.getCreationDate());
        dto.setCreatedBy(model.getCreatedBy());
        dto.setComment(model.getComment());
        return dto;
    }
}
