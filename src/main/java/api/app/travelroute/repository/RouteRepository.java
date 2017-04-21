package api.app.travelroute.repository;

import api.app.travelroute.entity.RouteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cc on 2017/4/15.
 */
@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

    Page<RouteEntity> findByDestId(long destId, Pageable pageable);

}
