package com.amatta.findog.dto.response;

import com.amatta.findog.domain.Shelter;
import com.amatta.findog.dto.ShelterInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShelterResponse {

    List<ShelterInfo> seoul;

    List<ShelterInfo> gyeonggi;

    List<ShelterInfo> incheon;

    List<ShelterInfo> daejeonSejongChungcheong;

    List<ShelterInfo> busanDaeguGyeongsang;

    List<ShelterInfo> gwangjuJeolla;

    List<ShelterInfo> gangwon;

    List<ShelterInfo> jeju;

    public static ShelterResponse fromEntity(List<Shelter> 서울, List<Shelter> 경기, List<Shelter> 인천, List<Shelter> 대전세종충청, List<Shelter> 부산대구경상, List<Shelter> 광주전라, List<Shelter> 강원, List<Shelter> 제주) {
        return ShelterResponse.builder()
                .seoul(서울.stream().map(ShelterInfo::fromEntity).toList())
                .gyeonggi(경기.stream().map(ShelterInfo::fromEntity).toList())
                .incheon(인천.stream().map(ShelterInfo::fromEntity).toList())
                .daejeonSejongChungcheong(대전세종충청.stream().map(ShelterInfo::fromEntity).toList())
                .busanDaeguGyeongsang(부산대구경상.stream().map(ShelterInfo::fromEntity).toList())
                .gwangjuJeolla(광주전라.stream().map(ShelterInfo::fromEntity).toList())
                .gangwon(강원.stream().map(ShelterInfo::fromEntity).toList())
                .jeju(제주.stream().map(ShelterInfo::fromEntity).toList())
                .build();
    }
}
