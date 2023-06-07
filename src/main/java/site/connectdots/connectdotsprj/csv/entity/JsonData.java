package site.connectdots.connectdotsprj.csv.entity;


import lombok.*;


@Getter @Setter
@EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class JsonData {
    private String img;
    private String title;
    private String price;
    private String sale;
    private String csv;

}
