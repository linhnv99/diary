package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.entities.Feeling;
import com.linhnv.diary.models.requests.FeelingRequest;
import com.linhnv.diary.models.responses.FeelingResponse;
import com.linhnv.diary.repositories.FeelingRepository;
import com.linhnv.diary.services.IFeelingService;
import com.linhnv.diary.services.mappers.FeelingMapper;
import com.linhnv.diary.utils.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeelingService implements IFeelingService {

    @Autowired
    private FeelingRepository repository;

    @Autowired
    private FeelingMapper mapper;

    @Override
    public ResponseEntity<SystemResponse<List<FeelingResponse>>> getAll() {

        List<Feeling> feelings = repository.findAllByStatus(StatusEnum.ACTIVE.name());

        List<FeelingResponse> feelingResponses = mapper.map(feelings);

        return Response.ok(feelingResponses);
    }

    @Override
    public ResponseEntity<SystemResponse<FeelingResponse>> delete(int feelingId) {

        Feeling feeling = repository.findByIdAndStatus(feelingId, StatusEnum.ACTIVE.name());

        if (feeling == null) return Response.badRequest(StringResponse.INVALID_FEELING);

        feeling.setStatus(StatusEnum.DELETED);

        repository.save(feeling);

        return Response.ok();
    }

    @Override
    public ResponseEntity<SystemResponse<FeelingResponse>> create(FeelingRequest feelingRequest) {

        Feeling feeling = mapper.map(feelingRequest);

        feeling = repository.save(feeling);

        FeelingResponse feelingResponse = mapper.map(feeling);

        return Response.ok(feelingResponse);
    }




}
