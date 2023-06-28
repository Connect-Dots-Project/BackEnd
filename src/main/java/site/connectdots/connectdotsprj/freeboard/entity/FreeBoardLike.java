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
@ToString
@Builder
@Entity
@EqualsAndHashCode(of = "freeboardLikeIdx")
@Table(name = "tb_freeboardlike")
public class FreeBoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "freeboard_like_idx")
    private Long freeboardLikeIdx;

    @Column(name = "member_account", nullable = false, unique = true)
    private String memberAccount;

    @Column(name = "freeboard_idx", nullable = false)
    private Long freeboardIdx;

}