package site.connectdots.connectdotsprj.music.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter @Setter
@Builder @NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "music_board")
public class Music {

    @Column(name = "")
    private Long boardNo;

    private String singer;

    @Column(name = "")
    private String songTitle;

    private String lyrics;

    private String genre; //enum?

    private String scrap;
    //아니면 boolean

    private  Long viewCount;


}
//    게시글 key / INT(10)
//        노래 제목 / VARCHAR(50)
//        노래 가수 / VARCHAR(20)
//        노래 가사 / VARCHAR(2000)
//        좋아요 수 / INT(10)
//        장르     /  VARCHAR(10)
//        클릭 수(글) / INT(10)
//         장르, 조회수(클릭수) 추가?
//
//        작성자 key / VARCHAR(2000)
//        스크랩 / VARCHAR(1) ex) 'Y' or 'N'