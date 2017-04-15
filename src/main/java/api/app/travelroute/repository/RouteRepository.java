package api.app.travelroute.repository;

import api.app.travelroute.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cc on 2017/4/15.
 */
@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

}
