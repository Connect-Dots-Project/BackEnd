package site.connectdots.connectdotsprj.mypage.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "TB_LIKE",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"member_idx", "item_idx", "type"})
        })
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @Column(name = "item_idx", nullable = false, length = 10)
    private Long itemIdx;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(20)", nullable = false, length = 20)
    private LikeType type;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-HH-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
