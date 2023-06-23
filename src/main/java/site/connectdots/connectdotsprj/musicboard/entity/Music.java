//package site.connectdots.connectdotsprj.musicboard.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.*;
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Getter @Setter
//@Builder @NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode(of = "musicBoardIdx")
//
//@Entity
//@Table(name = "TB_MUSICBOARD")
//public class Music {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long musicBoardIdx;
//
//    @Column(nullable = false, length = 50)
//    private String musicBoardTrack;
//
//    @Column(nullable = false, length = 2000)
//    private String musicBoardTrackImg;
//
//
//    @Column(nullable = false, length = 50)
//    private String musicBoardTitle;
//
//    @Column(nullable = false, length =2000)
//    private String musicBoardTitleImg;
//
//    @Column(nullable = false, length =50)
//    private String musicBoardSinger;
//
//    @Builder.Default
//    private  Long musicBoardViewCount = 0L;
//
//    @Column(nullable = false, length =2000)
//    private String clientId;
//    @Column(nullable = false, length =2000)
//    private String clientSecret;
//    @Column(nullable = false, length =2000)
//    private String redirectUri;
//}
