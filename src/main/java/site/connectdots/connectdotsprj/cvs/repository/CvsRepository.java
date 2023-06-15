package site.connectdots.connectdotsprj.cvs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.connectdots.connectdotsprj.cvs.entity.Cvs;


public interface CvsRepository extends JpaRepository<Cvs, Long> {

}
