package com.storeendlos.vendor.Controller;

import com.storeendlos.Payload.response.PageResponse;
import com.storeendlos.Payload.response.Pagination;
import com.storeendlos.vendor.Service.VendorService;
import com.storeendlos.vendor.model.VendorModel;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/store/api/v1/vendor")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping()
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> SaveData(@RequestBody VendorModel vendorModel) {
        VendorModel vendorModeldata = vendorService.SaveData(vendorModel);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModeldata), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> FindByIdData(@PathVariable Long id) {
        VendorModel vendorModel = vendorService.FindById(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModel), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> FindAll() {
        Object vendor = vendorService.FindAll();

        return new ResponseEntity<>(PageResponse.SuccessResponse(vendor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteData(@PathVariable Long id) {
        Object vendorModel = vendorService.DeleteData(id);
        return new ResponseEntity<>(PageResponse.SuccessResponse(vendorModel), HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{vendorId}")
    public ResponseEntity<?> updateData(@PathVariable Long vendorId, @RequestBody Map<Object, Object> field) {
        return new ResponseEntity<>(vendorService.updateVendor(vendorId, field), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> FIndyDate(@RequestParam(required = false) Date start, @RequestParam(required = false)
    Date end, @RequestParam int page, @RequestParam int size) {
        Pagination pagination = new Pagination(page, size);
        Page<VendorModel> vendorModels = vendorService.FindDateByData(start, end, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(vendorModels, pagination), HttpStatus.OK);
    }

    @GetMapping("/searchSingle")
    public ResponseEntity<?> FindByParticularDate(@RequestParam Date pdate, @RequestParam int page, @RequestParam int pagesize) {
        Pagination pagination = new Pagination(page, pagesize);
        Page<VendorModel> vendorModels = vendorService.FindByParticularDate(pdate, pagination);
        return new ResponseEntity<>(PageResponse.pagebleResponse(vendorModels, pagination), HttpStatus.OK);
    }

}
