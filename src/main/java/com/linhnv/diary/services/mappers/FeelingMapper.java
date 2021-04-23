package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.bos.StatusEnum;
import com.linhnv.diary.models.entities.Feeling;
import com.linhnv.diary.models.requests.FeelingRequest;
import com.linhnv.diary.models.responses.FeelingResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeelingMapper {
    public Feeling map(FeelingRequest feelingRequest) {

        Feeling feeling = new Feeling();
        feeling.setName(feelingRequest.getName());
        feeling.setStatus(StatusEnum.ACTIVE);

        return feeling;
    }

    public FeelingResponse map(Feeling feeling) {

        FeelingResponse response = new FeelingResponse();
        response.setId(feeling.getId());
        response.setName(feeling.getName());
        response.setStatus(feeling.getStatus());

        return response;
    }

    public List<FeelingResponse> map(List<Feeling> feelings) {
        return feelings.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
