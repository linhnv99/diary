package com.linhnv.diary.controllers;

import com.linhnv.diary.models.requests.FeelingRequest;
import com.linhnv.diary.models.responses.FeelingResponse;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.services.IFeelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/feelings")
public class FeelingController {

    @Autowired
    private IFeelingService service;

    @GetMapping()
    private ResponseEntity<SystemResponse<List<FeelingResponse>>> getAll() {
        return service.getAll();
    }

    @PostMapping()
    private ResponseEntity<SystemResponse<FeelingResponse>> create(@NotBlank @RequestBody FeelingRequest feelingRequest) {
        return service.create(feelingRequest);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<SystemResponse<FeelingResponse>> delete(@NotBlank @PathVariable("id") int feelingId) {
        return service.delete(feelingId);
    }

}
