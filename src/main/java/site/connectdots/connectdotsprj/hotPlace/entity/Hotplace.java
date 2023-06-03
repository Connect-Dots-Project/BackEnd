package site.connectdots.connectdotsprj.hotPlace.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of= "hotplaceIdx")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_hotplace")
public class Hotplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotplaceIdx ;

    @Column(nullable = false, length = 200)
    private String hotplaceImg;

    @Column(nullable = false, length = 100)
    private String hotplaceContent;

    @Column(nullable = false, length = 20)
    private HotplaceLocation hotplaceLocation;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime hotplaceWriteDate;

    @ColumnDefault("0")
    private Long hotplaceLikeCount;

    @Column(nullable = false, length = 20)
    @ColumnDefault("0.000000")
    private String hotplaceLatitude;

    @Column(nullable = false, length = 20)
    @ColumnDefault("0.000000")
    private String hotplaceLongitude;

    @Column(nullable = false, length = 10)
    private Long memberIdx;
}
