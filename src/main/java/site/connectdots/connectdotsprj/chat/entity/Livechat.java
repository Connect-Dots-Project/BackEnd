package site.connectdots.connectdotsprj.chat.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "livechat_idx")
@Builder
@Table(name = "TB_LIVECHAT")
@Entity
public class Livechat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livechat_idx;
    @Column(nullable = false, length = 500)

    private String livechat_content;
    @Column(nullable = false, length = 20)
    private String livechatHashtag;
    @CreationTimestamp
    private LocalDateTime livechatCreateDate;
    @Column(unique = true)
    private Long memberIdx;
}
