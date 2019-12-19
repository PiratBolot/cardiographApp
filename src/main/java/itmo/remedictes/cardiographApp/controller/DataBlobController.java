package itmo.remedictes.cardiographApp.controller;

import itmo.remedictes.cardiographApp.domain.CardiographDataBlob;
import itmo.remedictes.cardiographApp.repos.DataBlobRepository;
import itmo.remedictes.cardiographApp.service.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// TODO: longpoll

@RestController
@RequestMapping("data")
public class DataBlobController {

    private DataBlobRepository dataRepository;

    @Autowired
    public DataBlobController(DataBlobRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    private ResponseEntity<List<CardiographDataBlob>> formResponse(long timestamp,
                                                                   Expression func) {
        if (timestamp == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CardiographDataBlob> blob = func.getData(timestamp);
        if (blob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blob, HttpStatus.OK);
    }

    @GetMapping("singleBlob")
    public ResponseEntity<List<CardiographDataBlob>> getDataByTimestamp(
            @RequestParam(value = "timestamp", required = false, defaultValue = "-1")
                    long timestamp) {
        return formResponse(timestamp, dataRepository::findAllByTimestamp);
    }

    @GetMapping("blobsBetween")
    public ResponseEntity<List<CardiographDataBlob>> getDataBetweenTimestamps(
            @RequestParam(value = "fromTimestamp", required = false, defaultValue = "-1")
                    long fromTimestamp,
            @RequestParam(value = "byTimestamp", required = false, defaultValue = "-1")
                    long byTimestamp) {
        if (fromTimestamp == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (byTimestamp == -1) {
            return new ResponseEntity<>(dataRepository.findAllByTimestampAfter(fromTimestamp),
                    HttpStatus.OK);
        }
        List<CardiographDataBlob> blob = dataRepository
                .findAllByTimestampBetweenOrderByTimestamp(fromTimestamp, byTimestamp);
        if (blob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blob, HttpStatus.OK);
    }

    @GetMapping("blobsFrom")
    public ResponseEntity<List<CardiographDataBlob>> getDataFromTimestamp(
            @RequestParam(value = "fromTimestamp", required = false, defaultValue = "-1")
                    long fromTimestamp) {
        return formResponse(fromTimestamp, dataRepository::findAllByTimestampAfter);
    }

    @PostMapping
    public ResponseEntity<Long> sendData(@Valid @RequestBody CardiographDataBlob dataBlob) {
        return new ResponseEntity<>(dataRepository.save(dataBlob).getId(), HttpStatus.OK);
    }

}
