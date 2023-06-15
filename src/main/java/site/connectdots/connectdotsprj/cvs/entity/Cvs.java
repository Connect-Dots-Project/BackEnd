package site.connectdots.connectdotsprj.cvs.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.connectdots.connectdotsprj.cvs.dto.CvsResponseDTO;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_cvs")
public class Cvs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvsIdx;

    @Column(nullable = false, length = 200)
    private String cvsImg;

    @Column(nullable = false, length = 50)
    private String cvsTitle;

    @Column(nullable = false, length = 10)
    private String cvsPrice;

    @Column(nullable = false, length = 5)
    private String cvsSale;

    @Column(nullable = false, length = 10)
    private String cvsType;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime cvsRegDate;


}
