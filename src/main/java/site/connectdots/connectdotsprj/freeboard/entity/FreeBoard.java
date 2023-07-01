package site.connectdots.connectdotsprj.freeboard.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"member", "freeBoardReplyList"})
@Builder
@Entity
@EqualsAndHashCode(of = "freeBoardIdx")
@Table(name = "tb_freeboard")
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
    @Enumerated(value = EnumType.STRING)
    private FreeBoardCategory freeBoardCategory;
    @CreationTimestamp
    private LocalDateTime freeBoardWriteDate;
    @UpdateTimestamp
    private LocalDateTime freeBoardUpdateDate;
    @Builder.Default
    private Long freeBoardViewCount = 0L;

    @Builder.Default
    private Long freeBoardLikeCount = 0L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @OneToMany(mappedBy = "freeBoard", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<FreeBoardReply> freeBoardReplyList = new ArrayList<>();

    @Formula("(SELECT COUNT(1) FROM TB_FREEBOARDREPLY tb WHERE tb.free_board_idx = free_board_idx)")
    private Long freeBoardReplyCount;
}