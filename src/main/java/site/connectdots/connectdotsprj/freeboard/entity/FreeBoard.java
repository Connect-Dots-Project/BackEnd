package site.connectdots.connectdotsprj.freeboard.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@EqualsAndHashCode(of = "freeBoardIdx")
@Table(name = "TB_FREEBOARD")
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeBoardIdx;
    @Column(length = 200)
    private String freeBoardImg;
    @Column(nullable = false, length = 50)
    private String freeBoardTitle;
    @Column(nullable = false, length = 2000)
    private String freeBoardContent;
    @Column(nullable = false, length = 20)
    private String freeBoardLocation;
    @Column(nullable = false, length = 20)
    private String freeBoardCategory;
    @CreationTimestamp
    private LocalDateTime freeBoardWriteDate;
    @UpdateTimestamp
    private LocalDateTime freeBoardUpdateDate;
    @ColumnDefault("0")
    private Long freeBoardViewCount;
    @ColumnDefault("0")
    private Long freeBoardReplyCount;
    @ColumnDefault("0")
    private Long freeBoardLikeCount;

    @Column(nullable = false)
    private Long memberIdx;
}