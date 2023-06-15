package site.connectdots.connectdotsprj.cvs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.css.CSSValue;
import site.connectdots.connectdotsprj.cvs.entity.Cvs;

import java.util.List;


public interface CvsRepository extends JpaRepository<Cvs, Long> {

//    행사 상품 종류로 구분해서 조회
    List<Cvs> findAllByCvsSale(String sale);

    // 편의점 구분해서 조회
    List<Cvs> findAllByCvsType(String cvs);

}
