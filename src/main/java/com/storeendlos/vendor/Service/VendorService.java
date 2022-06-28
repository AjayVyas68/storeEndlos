package com.storeendlos.vendor.Service;

import com.storeendlos.Payload.response.Pagination;
import com.storeendlos.exception.ExceptionService.UserNotFound;
import com.storeendlos.vendor.Repository.VendorRepository;
import com.storeendlos.vendor.model.VendorModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;

    }

    public VendorModel SaveData(VendorModel vendorModel) {
        return vendorRepository.save(vendorModel);
    }

    public VendorModel FindById(Long id) {
        return vendorRepository.findById(id).orElseThrow(() -> new RuntimeException("Sorry ! Vendor Not Found in System"));
    }

    public Object FindAll() {
        return vendorRepository.findAll();
    }

    public Optional<VendorModel> DeleteData(Long id) {
        Optional<VendorModel> vendorModel = vendorRepository.findById(id);
        if (!vendorModel.isPresent()) {
            throw new UserNotFound("Sorry ! User Not Found in System");
        } else
            vendorRepository.deleteById(id);
        return Optional.empty();
    }

    public Page<VendorModel> FindDateByData(Date start, Date end, Pagination pagination) {
        java.util.Date date = new java.util.Date();
        if (date.before(start)) {
            throw new RuntimeException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new RuntimeException(" you can not enter -> " + date + "  -> " + end);
        }
        return vendorRepository.findByCreatedAtBetween(start, end, pagination.getpageble());
    }

    public Page<VendorModel> FindByParticularDate(Date pdate, Pagination pagination) {
        java.util.Date date = new java.util.Date();
        if (date.before(pdate)) {
            throw new RuntimeException(" you can not enter -> " + date + "  -> " + pdate);
        }
        return vendorRepository.findByCreatedAt(pdate, pagination.getpageble());
    }

    public Object updateVendor(long vendorId, Map<Object, Object> changes) {
        VendorModel vendorModel = FindById(vendorId);
        changes.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(VendorModel.class, (String) (key));
            field.setAccessible(true);
            ReflectionUtils.setField(field, vendorModel, value);
        });
        return vendorRepository.save(vendorModel);
    }
}
