package site.connectdots.connectdotsprj.chat.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "livechatIdx")
@Builder
@Table(name = "TB_LIVECHAT")
@Entity
public class Livechat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livechatIdx;
    @Column(nullable = false, length = 500)
    private String livechatContent;
    @Column(nullable = false, length = 20)
    private String livechatHashtag;
    @CreationTimestamp
    private LocalDateTime livechatCreateDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx", nullable = false, unique = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
}
