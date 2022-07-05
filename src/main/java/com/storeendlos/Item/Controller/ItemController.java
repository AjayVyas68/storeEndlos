package com.storeendlos.Item.Controller;

import com.storeendlos.Item.Service.ItemService;
import com.storeendlos.Item.model.StoreItemModel;
import com.storeendlos.Payload.response.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/store/api/v1/item")
public class ItemController {
    public static final Logger log= LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<?> saveItem(@RequestBody StoreItemModel storeItemModel) {
        return new ResponseEntity<>(itemService.saveData(storeItemModel), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<?> data=itemService.findAll();
        log.info("get All Data From Item {} ",data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @PatchMapping(value = "/{itemId}")
    public ResponseEntity<?> updateItemData(@PathVariable long itemId, @RequestBody Map<Object, Object> changes) {
        return new ResponseEntity<>(itemService.updateItem(itemId, changes), HttpStatus.OK);
    }
    @GetMapping(value = "/{itemId}/getData/{vendorId}")
    public ResponseEntity<?> findByItemAndVendor(@PathVariable Long itemId,@PathVariable Long vendorId)
    {
        List<?> data=itemService.findByItemIdAndVendorID(itemId, vendorId);
        log.info("data between item id And vendorId {}",data );
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/getDataTwoDate")
    public ResponseEntity<?> findByDateTwoDateBetween(
            @RequestParam (required = false) Date start,@RequestParam (required = false) Date end
            )
    {
        List<?> data=itemService.findByItemDateBetween(start, end);
        log.info("data Between Two Date {}",data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
}
