package site.connectdots.connectdotsprj.freeboard.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"member", "freeBoard"})
@Builder
@Entity
@EqualsAndHashCode(of = "freeBoardReplyIdx")
@Table(name = "TB_FREEBOARDREPLY")
public class FreeBoardReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long freeBoardReplyIdx;
    @CreationTimestamp
    private LocalDateTime freeBoardReplyCreateDate;
    @Column(nullable = false, length = 500)
    private String freeBoardReplyContent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private FreeBoard freeBoard;

}