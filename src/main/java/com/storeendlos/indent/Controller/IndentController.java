package com.storeendlos.indent.Controller;

import com.storeendlos.Payload.response.PageResponse;
import com.storeendlos.indent.Model.Indent;
import com.storeendlos.indent.Model.IndentStatus;
import com.storeendlos.indent.Service.IndentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/store/api/v1/store/req")
public class IndentController {
    private final IndentService indentService;

    public IndentController(IndentService indentService) {
        this.indentService = indentService;
    }
    @PostMapping()
    public ResponseEntity<?> saveData(@RequestBody Indent indent )
    {
        Indent requestData= indentService.saveData(indent);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findByAll()
    {
        List<Indent> requestData= indentService.findAll();
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData),HttpStatus.OK);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<?> findById(@PathVariable long itemId)
    {
        Indent requestData= indentService.findByid(itemId);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData),HttpStatus.OK);
    }
    @PatchMapping(value = " /{itemId}")
    public ResponseEntity<?> updateData(@PathVariable Long itemId, @RequestBody Map<Object ,Object> request)
    {
        Indent requestData= indentService.updateData(itemId,request);
        return new ResponseEntity<>(PageResponse.SuccessResponse(requestData),HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,@RequestBody Indent data)
    {
        Object request= indentService.changeStatus(id,data);
        return new ResponseEntity<>(PageResponse.SuccessResponse(request),HttpStatus.OK);
    }

    @GetMapping(value = "/cDate")
    public ResponseEntity<?> findByReqCreated(@RequestParam Date date)
    {
        List<Indent> data= indentService.findByCreatedDate(date);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/Istatus")
    public ResponseEntity<?> findByIndentStatus(@RequestParam IndentStatus indentStatus)
    {
        List<Indent> data= indentService.findByStatus(indentStatus);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }
    @GetMapping(value = "/show")
    public ResponseEntity<?> findByVendorIdAndIndentId(@PathVariable Date start , @RequestParam Date end)
    {
        List<Indent> data=indentService.findByListDateBetween(start,end);
        return new ResponseEntity<>(PageResponse.SuccessResponse(data),HttpStatus.OK);
    }

}
