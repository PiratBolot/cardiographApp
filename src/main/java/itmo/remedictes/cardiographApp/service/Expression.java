package itmo.remedictes.cardiographApp.service;

import itmo.remedictes.cardiographApp.domain.CardiographDataBlob;

import java.util.List;

public interface Expression {
    List<CardiographDataBlob> getData(long timestamp);
}
