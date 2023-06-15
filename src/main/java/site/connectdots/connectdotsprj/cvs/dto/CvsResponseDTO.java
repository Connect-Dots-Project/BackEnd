package site.connectdots.connectdotsprj.cvs.dto;


import lombok.*;
import site.connectdots.connectdotsprj.cvs.entity.Cvs;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CvsResponseDTO {
    private String img;
    private String title;
    private String price;
    private String sale;
    private String cvs;

    public CvsResponseDTO(Cvs cvs){
        this.img  = cvs.getCvsImg();
        this.title = cvs.getCvsTitle();
        this.price = cvs.getCvsPrice();
        this.sale = cvs.getCvsSale();
        this.cvs = cvs.getCvsType();
    }

}
