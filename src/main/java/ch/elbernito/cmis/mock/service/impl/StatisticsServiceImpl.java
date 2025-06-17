package ch.elbernito.cmis.mock.service.impl;

import ch.elbernito.cmis.mock.dto.StatisticsDto;
import ch.elbernito.cmis.mock.model.DocumentModel;
import ch.elbernito.cmis.mock.repository.DocumentRepository;
import ch.elbernito.cmis.mock.repository.FolderRepository;
import ch.elbernito.cmis.mock.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;

    public StatisticsServiceImpl(DocumentRepository documentRepository, FolderRepository folderRepository) {
        this.documentRepository = documentRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public StatisticsDto getCurrentStatistics() {
        StatisticsDto dto = new StatisticsDto();
        long documentCount = documentRepository.count();
        long folderCount = folderRepository.count();

        // Summe ohne Streams
        long totalSize = 0L;
        List<DocumentModel> docs = documentRepository.findAll();
        for (DocumentModel doc : docs) {
            totalSize += doc.getContentSize();
        }

        dto.setDocumentCount(documentCount);
        dto.setFolderCount(folderCount);
        dto.setObjectCount(documentCount + folderCount);
        dto.setStorageSize(totalSize);

        return dto;
    }
}
