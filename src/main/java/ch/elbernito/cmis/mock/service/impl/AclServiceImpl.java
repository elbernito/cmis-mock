package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.AclDto;
import ch.elbernito.cmis.mock.exception.AclNotFoundException;
import ch.elbernito.cmis.mock.mapping.AclMapper;
import ch.elbernito.cmis.mock.model.AclModel;
import ch.elbernito.cmis.mock.model.ChangeType;
import ch.elbernito.cmis.mock.repository.AclRepository;
import ch.elbernito.cmis.mock.service.AclService;
import ch.elbernito.cmis.mock.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AclService.
 */
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AclServiceImpl implements AclService {

    private final AclRepository aclRepository;
    private final AclMapper aclMapper;

    private final ChangeLogService changeLogService;

    @Override
    public List<AclDto> getAclForObject(String objectId) {
        log.info("Fetching ACLs for object: {}", objectId);
        List<AclModel> list = aclRepository.findAllByObjectId(objectId);
        List<AclDto> result = new ArrayList<>();
        for (AclModel model : list) {
            result.add(aclMapper.toDto(model));
        }

        return result;
    }

    @Override
    public AclDto setAclForObject(String objectId, AclDto aclDto) {
        log.info("Setting ACL for object {} principal={}", objectId, aclDto.getPrincipal());
        AclModel model = aclMapper.toEntity(aclDto);
        model.setObjectId(objectId);
        AclModel saved = aclRepository.save(model);

        // ChangeLog
        changeLogService.logChange(
                objectId,
                ChangeType.SECURITY,
                "ACL created/set for principal " + aclDto.getPrincipal()
        );

        return aclMapper.toDto(saved);
    }

    @Override
    public AclDto updateAcl(String aclId, AclDto aclDto) {
        log.info("Updating ACL: {}", aclId);
        AclModel existing = aclRepository.findByAclId(aclId)
                .orElseThrow(() -> new AclNotFoundException("ACL not found: " + aclId));
        existing.setPrincipal(aclDto.getPrincipal());
        existing.setPermissions(aclDto.getPermissions());
        AclModel saved = aclRepository.save(existing);

        // ChangeLog
        changeLogService.logChange(
                existing.getObjectId(),
                ChangeType.SECURITY,
                "ACL updated for principal " + aclDto.getPrincipal()
        );

        return aclMapper.toDto(saved);
    }

    @Override
    public void deleteAcl(String aclId) {
        log.info("Deleting ACL: {}", aclId);
        AclModel model = aclRepository.findByAclId(aclId)
                .orElseThrow(() -> new AclNotFoundException("ACL not found: " + aclId));
        aclRepository.delete(model);

        // ChangeLog
        changeLogService.logChange(
                model.getObjectId(),
                ChangeType.SECURITY,
                "ACL deleted for principal " + model.getPrincipal()
        );
    }
}
