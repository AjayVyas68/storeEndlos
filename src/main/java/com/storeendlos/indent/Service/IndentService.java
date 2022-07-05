package com.storeendlos.indent.Service;

import com.storeendlos.Item.Repository.ItemRepository;
import com.storeendlos.Item.model.StoreItemModel;
import com.storeendlos.exception.ExceptionService.ItemNotFound;
import com.storeendlos.exception.ExceptionService.ResourceNotFound;
import com.storeendlos.indent.Model.Indent;
import com.storeendlos.indent.Model.IndentStatus;
import com.storeendlos.indent.Repository.IndentRepository;
import com.storeendlos.vendor.Repository.VendorRepository;
import com.storeendlos.vendor.Service.VendorService;

import com.storeendlos.vendor.model.VendorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndentService {
    private final IndentRepository indentRepository;
    private final ItemRepository storeItemRepository;
    private final VendorService vendorService;
    private final VendorRepository vendorRepository;
    private static final Logger log = LoggerFactory.getLogger(IndentService.class);

    public IndentService(IndentRepository indentRepository, ItemRepository storeItemRepository, VendorService vendorService, VendorRepository vendorRepository) {
        this.indentRepository = indentRepository;
        this.storeItemRepository = storeItemRepository;
        this.vendorService = vendorService;
        this.vendorRepository = vendorRepository;
    }

    public Indent saveData(Indent indent ) {
        indent.getStoreItem().getVendorDate().addAll(
                indent.getStoreItem().getVendorDate().stream()
                        .map(x->{
                            VendorModel vendorModel=vendorService.FindById(x.getVid());
                            indent.getStoreItem().getVendorDate().add(vendorModel);
                            return x;
                        }).collect(Collectors.toSet())
        );



//        StoreItemModel itemModel=storeItemRepository.findById(indent.getStoreItem().getItemId())
//                .orElseThrow(()-> new ItemNotFound("item not Found"));
//        indent.setTotal((long) (indent.getEstimatedPrice() * indent.getQuantity()));
//        indent.setIncludingTax(((indent.getTotal() * itemModel.getTax() )/100)+indent.getTotal());
        List<StoreItemModel> data=new ArrayList<>();

        return indentRepository.save(indent);
    }

    public Indent findByid(Long id)  {
        return indentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Sorry ! Indent Was Not Found"));
    }

    public Indent updateData(Long id, Map<Object, Object> request) {
        Indent req = indentRepository.findById(id).orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));
        request.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Indent.class, (String) key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, req, value);
        });
        Indent requestData = indentRepository.save(req);
        log.trace("updated Data  {}", requestData);
        return requestData;
    }




    public Object changeStatus(long itemId, Indent dta) {
        Indent itemRequest = indentRepository
                .findById(itemId)
                .orElseThrow(() -> new ItemNotFound("Sorry ! Item Was Not Found"));

        if (itemRequest.getIndentStatus().equals(IndentStatus.ADMIN))
        {
            log.info("vendor id {}",dta.getVendorData().getVid());
            VendorModel vendorModel=vendorService.FindById(dta.getVendorData().getVid());
           // vendorModel.getIndentList().add(itemRequest);
            log.info(" vendor first details {} ",vendorModel);
        //    itemRequest.setVendorData(vendorModel);
            log.info(" vendor details {} ",vendorModel);

        }
//        if (itemRequest.getIndentStatus().equals(IndentStatus.VP)) {
                itemRequest.setIndentStatus(dta.getIndentStatus());

            log.info("item Request {}", itemRequest);

//        }
        return  indentRepository.save(itemRequest);
    }

    public List<Indent> findAll() {
        return indentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Indent::getIndentId).reversed()).collect(Collectors.toList());
    }


    public List<Indent> findByCreatedDate(Date date) {
        return indentRepository.findByCreated(date);
    }
    public List<Indent> findByStatus(IndentStatus indentStatus) {
        return indentRepository.findByIndentStatus(indentStatus)
                .stream()
                .sorted(Comparator.comparing(Indent::getIndentId).reversed())
                .collect(Collectors.toList());
    }
    public List<Indent> findByListDateBetween(Date start,Date end)
    {
        return indentRepository.ffindByDateBEtween(start,end)
                .stream()
                .sorted(Comparator.comparing(Indent::getIndentId))
                .collect(Collectors.toList());
    }
    public Object data(Indent indent )
    {
       return indent.getDataVendorAndIndent();
    }
}
