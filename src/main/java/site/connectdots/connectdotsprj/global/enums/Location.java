package site.connectdots.connectdotsprj.global.enums;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Location {
    도봉구, 노원구, 강북구, 은평구, 종로구, 성북구, 중랑구, 서대문구, 동대문구,
    강서구, 마포구, 중구, 성동구, 광진구, 강동구, 영등포구, 용산구, 양천구,
    구로구, 동작구, 송파구, 강남구, 서초구, 관악구, 금천구;

    private static final Map<String, Location> LOCATION_MAP =
            Collections.unmodifiableMap(
                    Stream.of(values())
                            .collect(Collectors.toMap(Location::name, Function.identity()))
            );

    public static final Location of(String location) {
        if (LOCATION_MAP.containsKey(location)) {
            return LOCATION_MAP.get(location);
        }

        throw new IllegalArgumentException(location + "존재하지 않습니다");

    }

}
