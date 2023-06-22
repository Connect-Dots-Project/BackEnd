package site.connectdots.connectdotsprj.jwt.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_AUTH")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authIdx;
    @Column(nullable = false, length = 1500)
    private String refreshToken;
    @CreationTimestamp
    private LocalDateTime authDate;
    @Column(nullable = false, length = 50, unique = true)
    private String account;

}