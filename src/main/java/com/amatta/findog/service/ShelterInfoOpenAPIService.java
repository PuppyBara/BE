package com.amatta.findog.service;

import com.amatta.findog.dto.ShelterDogDto;
import com.amatta.findog.dto.ShelterDto;
import com.amatta.findog.enums.Sex;
import com.amatta.findog.enums.ShelterType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class ShelterInfoOpenAPIService {

    @Value("${openapi.shelter-info.key}")
    String SERVICE_KEY;

    private final String url = "http://apis.data.go.kr/1543061/animalShelterSrvc/shelterInfo";
    private final String TYPE = "json";
    private final String NUMBER_OF_ROWS = "1000";

    private List<ShelterDto> shelterDtos;

    public List<ShelterDto> getShelterList() throws ParseException, UnsupportedEncodingException {
        this.shelterDtos = new ArrayList<>();
        getPageResult(1);
        return shelterDtos;
    }

    private void getPageResult(int page) throws ParseException, UnsupportedEncodingException {
        String responseStr = response(page);
        JSONParser parser = new JSONParser();
        Object object = parser.parse(responseStr);
        JSONObject jsonObject = (JSONObject) object;
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");
        int totalCount = Integer.parseInt(body.get("totalCount").toString());

        if(item != null) {
            for(Object obj: item) {
                JSONObject shelter = (JSONObject) obj;
                ShelterDto dto = ShelterDto.builder()
                        .shelterType(shelter.get("divisionNm").toString().equals("동물병원") ? ShelterType.H : ShelterType.S)
                        .name(shelter.get("careNm").toString())
                        .tel(shelter.get("careTel").toString())
                        .location(shelter.get("careAddr").toString())
                        .build();
                shelterDtos.add(dto);
            }
        }

        if(totalCount == page) {
            return;
        }
        getPageResult(page+1);
    }

    private String response(int page) throws UnsupportedEncodingException {
        // 쿼리 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("serviceKey", SERVICE_KEY);
        params.set("pageNo", String.valueOf(page));
        params.set("_type", TYPE);
        params.set("numOfRows", NUMBER_OF_ROWS);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        builder.queryParams(params);

        // api 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.getForEntity(builder.build().toUriString(), String.class);
        return response.getBody();
    }

}
