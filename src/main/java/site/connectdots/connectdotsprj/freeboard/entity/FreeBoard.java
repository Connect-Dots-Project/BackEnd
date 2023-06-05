package site.connectdots.connectdotsprj.freeboard.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"member"})
@Builder
@Entity
@EqualsAndHashCode(of = "freeBoardIdx")
@Table(name = "TB_FREEBOARD")
public class FreeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeBoardIdx;
    @Column(nullable = false, length = 50)
    private String freeBoardTitle;
    @Column(nullable = false, length = 2000)
    private String freeBoardContent;
    @Column(length = 200)
    private String freeBoardImg;
    @Column(nullable = false, length = 20)
    private String freeBoardLocation;
    @Column(nullable = false, length = 20)
    private String freeBoardCategory;
    @CreationTimestamp
    private LocalDateTime freeBoardWriteDate;
    @UpdateTimestamp
    private LocalDateTime freeBoardUpdateDate;
    @Builder.Default
    private Long freeBoardViewCount = 0L;
    @Builder.Default
    private Long freeBoardReplyCount = 0L;
    @Builder.Default
    private Long freeBoardLikeCount = 0L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
}