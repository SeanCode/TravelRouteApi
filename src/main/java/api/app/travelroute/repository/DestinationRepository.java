package api.app.travelroute.repository;

import api.app.travelroute.entity.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cc on 2017/4/17.
 */
@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Long>, JpaSpecificationExecutor<DestinationEntity> {

    List<DestinationEntity> findByDestLikeOrderByUpdateTimeDesc(String dest);

}
