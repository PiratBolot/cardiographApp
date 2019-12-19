package itmo.remedictes.cardiographApp.repos;

import itmo.remedictes.cardiographApp.domain.CardiographDataBlob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataBlobRepository extends JpaRepository<CardiographDataBlob, Long> {

    List<CardiographDataBlob> findAllByTimestamp(long timestamp);

    List<CardiographDataBlob> findAllByTimestampBetweenOrderByTimestamp(long fromTimestamp,
                                                                        long byTimestamp);

    List<CardiographDataBlob> findAllByTimestampAfter(long timestamp);
}
