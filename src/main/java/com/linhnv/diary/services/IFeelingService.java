package com.linhnv.diary.services;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.FeelingRequest;
import com.linhnv.diary.models.responses.FeelingResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFeelingService {
    ResponseEntity<SystemResponse<List<FeelingResponse>>> getAll();

    ResponseEntity<SystemResponse<FeelingResponse>> create(FeelingRequest feelingRequest);

    ResponseEntity<SystemResponse<FeelingResponse>> delete(int feelingId);
}
