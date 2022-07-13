package com.storeendlos.Item.Service;

import com.storeendlos.Item.Repository.ItemRepository;
import com.storeendlos.Item.model.StoreItemModel;
import com.storeendlos.exception.ExceptionService.ItemNotFound;
import com.storeendlos.vendor.Repository.VendorRepository;
import com.storeendlos.vendor.model.VendorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {
    public static final Logger log = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;
    private final VendorRepository vendorRepository;

    public ItemService(ItemRepository itemRepository, VendorRepository vendorRepository) {
        this.itemRepository = itemRepository;
        this.vendorRepository = vendorRepository;
    }

    public StoreItemModel saveData(StoreItemModel storeItemModel) {
        storeItemModel.getVendorDate()
                .addAll(storeItemModel
                        .getVendorDate()
                        .stream()
                        .map(vendoor -> {
                                    VendorModel vendor = vendorRepository.findById(vendoor.getVid()).get();
                                    vendor.getItemData().add(storeItemModel);
                                    return vendoor;
                                }
                        ).collect(Collectors.toSet()));
//        for (VendorModel aLong : storeItemModel.getVendorDate()) {
//            log.info("data in along  {}",aLong);
//            aLong.addItem(storeItemModel);
//            log.info("vendor Store  item Data {} ", aLong.addItem(storeItemModel));
//            storeItemModel.AddItemInStore(aLong);
//            log.info("second Method item In {}", storeItemModel.AddItemInStore(aLong));
//            storeItemModel.addItemDataInVendor(aLong);
//            log.info("Third  Method item In {}", storeItemModel.addItemDataInVendor(aLong));
//            storeItemModel.getVendorDate().add(aLong);
//            log.info("store item Data {} ", storeItemModel.getVendorDate().add(aLong));
//        }

        //     log.info("data view {}",vendorModel);
        return itemRepository.save(storeItemModel);
    }

    public StoreItemModel findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFound("item Not Found"));
    }

    public List<StoreItemModel> findAll() {
        return itemRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(StoreItemModel::getItemId)).collect(Collectors.toList());
    }

    public Object updateItem(long itemId, Map<Object, Object> changes) {
        StoreItemModel storeItemModel = findById(itemId);
        changes.forEach((key, values) -> {
            Field field = ReflectionUtils.findField(StoreItemModel.class, (String) key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, storeItemModel, values);

        });
        return itemRepository.save(storeItemModel);
    }

    public List<?> findByItemIdAndVendorID(Long itemId, Long vendorId) {
        return itemRepository.findByItemIdAndVendorDateVid(itemId, vendorId);
    }

    public List<?> findByItemDateBetween(Date start, Date end) {
        return itemRepository.findByCreatedBetweenGenerateItem(start, end);
    }

    public List<?> findByItemSingleDate(Date date) {
        return itemRepository.findBySingleDate(date);
    }
}
