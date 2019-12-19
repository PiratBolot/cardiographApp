package itmo.remedictes.cardiographApp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;

@Entity
@Table(name = "data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardiographDataBlob {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private long length;

    @NonNull
    private long timestamp;

    @NonNull
    private SerialBlob dataBlob;
}
