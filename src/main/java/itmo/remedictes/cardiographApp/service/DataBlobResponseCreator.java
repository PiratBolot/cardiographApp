package itmo.remedictes.cardiographApp.service;

import itmo.remedictes.cardiographApp.domain.CardiographDataBlob;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DataBlobResponseCreator {
    public ResponseEntity<List<CardiographDataBlob>> formResponse(long timestamp, Expression func);
}
