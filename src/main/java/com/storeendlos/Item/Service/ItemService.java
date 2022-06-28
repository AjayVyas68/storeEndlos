package com.storeendlos.Item.Service;

import com.storeendlos.Item.Repository.ItemRepository;
import com.storeendlos.Item.model.StoreItemModel;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public StoreItemModel saveData(StoreItemModel storeItemModel)
    {
        return itemRepository.save(storeItemModel);
    }
    public StoreItemModel findById(Long id)
    {
        return itemRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("item Not Found"));
    }
    public List<StoreItemModel> findAll()
    {
        return itemRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(StoreItemModel::getItemId)).collect(Collectors.toList());
    }
    public Object updateItem(long itemId, Map<Object ,Object> changes)
    {
        StoreItemModel storeItemModel =findById(itemId);
        changes.forEach((key,values)->{
            Field field= ReflectionUtils.findField(StoreItemModel.class,(String) key);
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, storeItemModel,values);

        });
        return itemRepository.save(storeItemModel);
    }

}
