package com.storeendlos.indent.Controller;


import com.storeendlos.Payload.response.MessageResponse;
import com.storeendlos.Payload.response.PageResponse;
import com.storeendlos.Payload.response.Pagination;
import com.storeendlos.indent.Model.UsageItem;
import com.storeendlos.indent.Service.UsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/unitech/api/v1/itemusage")
public class UsageController {
    private static final Logger log = LoggerFactory.getLogger(UsageController.class);
    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @PostMapping
    public ResponseEntity<?> saveData(@RequestBody UsageItem usageItem) {
        usageService.save(usageItem);
        return new ResponseEntity<>(new MessageResponse("save Data "), HttpStatus.CREATED);
    }

    @GetMapping("/data")
    public ResponseEntity<?> FindAlle() {
        List<UsageItem> data = usageService.FindAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> FindByDeptName(@RequestParam String name) {
        List<UsageItem> data = usageService.findByDepName(name);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data), HttpStatus.OK);
    }

//    @GetMapping("/bDeptname")
//    public ResponseEntity<?> findByCarding(
//            @RequestParam(required = false) String deptname, @RequestParam(required = false) Long id
//            , @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start
//            , @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end
//            ,@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pagesize
//
//    )
//    {
//        Pagination pagination=new Pagination(page,pagesize);
//        Page<?> loadData=usageService.findByCardingDataAndDeptName(deptname,id,start,end,pagination);
//        log.info("Id   {} data ",id);
//        log.info("Date of {}" ,start);
//        log.info("searching {} data ",loadData);
//        return new ResponseEntity<>(PageResponse.pagebleResponse(loadData,pagination),HttpStatus.OK);
//    }

}
