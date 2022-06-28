package com.storeendlos.issue.Service;


import com.storeendlos.Item.Service.ItemService;
import com.storeendlos.Item.model.StoreItemModel;
import com.storeendlos.exception.ExceptionService.OutOfStock;
import com.storeendlos.exception.ExceptionService.ResourceNotFound;
import com.storeendlos.issue.Repository.IssueRepository;
import com.storeendlos.issue.model.IssueItem;
import com.storeendlos.issue.model.IssueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final ItemService itemService;

    public static final Logger log = LoggerFactory.getLogger(IssueService.class);

    public IssueService(IssueRepository issueRepository, ItemService itemService) {
        this.issueRepository = issueRepository;
        this.itemService = itemService;
    }

    public IssueItem saveData(IssueItem issueItem) {
        return issueRepository.save(issueItem);
    }

    public IssueItem findByIdIssue(@PathVariable Long id) {
        return issueRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Issue Not Found"));
    }

    public List<IssueItem> findAll() {
        return issueRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(IssueItem::getIssueId).reversed())
                .collect(Collectors.toList());
    }

    public IssueItem deleteIssueId(Long id) {
        Optional<?> dta = Optional.ofNullable(issueRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Issue Not Found")));
        if (dta.isPresent()) {
            issueRepository.deleteById(id);
        }
        return null;
    }

    public Object changeStatus(long issueId, IssueItem dta) {

        IssueItem itemRequest = findByIdIssue(issueId);
        StoreItemModel storeItemModel = itemService.findById(itemRequest.getStoreItemModel().getItemId());

        if (itemRequest.getStatus().equals(IssueStatus.PENDING)) {
            itemRequest.setStatus(dta.getStatus());
            if (itemRequest.getStatus().equals(IssueStatus.OPEN)) {
                if (itemRequest.getQuantity() >= dta.getQuantity()) {
                    storeItemModel.setQuantity(storeItemModel.getQuantity() - dta.getQuantity());
                    log.info("store item {}", storeItemModel);
                    itemRequest.setQuantity(dta.getQuantity());
                } else {
                    throw new OutOfStock("can't get any Resource");
                }
            }
//            log.info("store item {}", storeItemModel.getQuantity());
//            issueRepository.save(itemRequest);
        } else {
            itemRequest.setStatus(dta.getStatus());
        }
//        log.info("store item {}", storeItemModel.getQuantity());
        issueRepository.save(itemRequest);
        return null;
    }

    public List<IssueItem> FindByStatus(IssueStatus status) {

        return issueRepository.findByStatus(status)
                .stream()
                .sorted(Comparator.comparing(IssueItem::getIssueId).reversed())
                .collect(Collectors.toList());
    }

    public List<IssueItem> findByIssueData(Date dateTime) {
        return issueRepository.findByIssueDate(dateTime);
    }

    public List<IssueItem> FindByRaisedtrue(Boolean data) {
        return issueRepository.findByisRaised(data);
    }

    public List<IssueItem> FindByIssueDateBetween(Date start, Date end, Long itemId) {
        return issueRepository.findByIssueDateBetween(start, end, itemId);
    }

    public Optional<IssueItem> FindByCloseId(Long id) {
        return Optional.ofNullable(issueRepository.findByCloseresid(id).orElseThrow(() -> new ResourceNotFound("Not found Aby Type Of Data")));
    }
}

