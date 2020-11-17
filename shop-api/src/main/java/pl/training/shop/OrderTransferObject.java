package pl.training.shop;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderTransferObject {

    private Long clientId;
    @NotEmpty
    private List<IdTransferObject> products;

}
