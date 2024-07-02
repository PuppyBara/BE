package com.amatta.findog.service;

import com.amatta.findog.dto.ShelterDogDto;
import com.amatta.findog.enums.Sex;
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
public class AbandonedDogOpenAPIService {

    @Value("${openapi.abandoned-dog.key}")
    String SERVICE_KEY;

    private final String url = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic";
    private final String TYPE = "json";
    private final String NUMBER_OF_ROWS = "1000";

    private final String UPKIND = "417000";

    private List<ShelterDogDto> shelterDogDtos;

    public List<ShelterDogDto> getShelterDogList(LocalDateTime beginDate, LocalDateTime endDate) throws ParseException, UnsupportedEncodingException {
        this.shelterDogDtos = new ArrayList<>();
        getPageResult(beginDate, endDate, 1);
        return shelterDogDtos;
    }

    private void getPageResult(LocalDateTime beginDate, LocalDateTime endDate, int page) throws ParseException, UnsupportedEncodingException {
        String responseStr = response(beginDate, endDate, page);
        if(responseStr.isEmpty() || responseStr.isBlank()) return;
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
                JSONObject dog = (JSONObject) obj;
                ShelterDogDto dto = ShelterDogDto.builder()
                        .noticeNo(dog.get("noticeNo").toString())
                        .noticeSdt(LocalDate.parse(dog.get("noticeSdt").toString(), DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay())
                        .noticeEdt(LocalDate.parse(dog.get("noticeEdt").toString(), DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay())
                        .image(dog.get("filename").toString())
                        .sex(Sex.ofString(dog.get("sexCd").toString()))
                        .color(dog.get("colorCd").toString())
                        .isNeutering(dog.get("neuterYn").toString().equals("Y"))
                        .age(LocalDate.now().getYear() - Integer.parseInt(dog.get("age").toString().substring(0,4)) + 1)
                        .feature(dog.get("specialMark").toString())
                        .breed(dog.get("kindCd").toString())
                        .location(dog.get("happenPlace").toString())
                        .dateTime(LocalDate.parse(dog.get("happenDt").toString(), DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay())
                        .shelterName(dog.get("careNm").toString())
                        .build();
                shelterDogDtos.add(dto);
            }
        }

        if(totalCount == page) {
            return;
        }
        getPageResult(beginDate, endDate, page+1);
    }

    private String response(LocalDateTime beginDate, LocalDateTime endDate, int page) throws UnsupportedEncodingException {
        // 쿼리 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("serviceKey", SERVICE_KEY);
        params.set("bgnde", beginDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        params.set("endde", endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        params.set("upkind", UPKIND);
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
