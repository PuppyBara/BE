package com.amatta.findog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final ShelterDogService shelterDogService;

    @Scheduled(cron = "0 0 * * * ?")
    public void reloadAbandonedDogOpenApiData() throws UnsupportedEncodingException, ParseException {
        LocalDateTime beginDate = LocalDate.now().atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atStartOfDay();
        shelterDogService.reloadAbandonedDogApiData(beginDate, endDate);
        log.info("{} : 유기 동물 정보 공공 데이터 DB 업로드", beginDate);
    }
}
