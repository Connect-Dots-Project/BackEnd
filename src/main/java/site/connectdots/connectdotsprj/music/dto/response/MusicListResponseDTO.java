package site.connectdots.connectdotsprj.music.dto.response;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@ToString
@EqualsAndHashCode
public class MusicListResponseDTO {

    private Long boardNo;
    private  String title; // 10자 이상 줄임
    private String songTitle;
    private  String singer;
    private  Long viewCount; //아니면 entity삭제하고 scrap으로?


}
