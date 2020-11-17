package pl.training.shop;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderTransferObject {

    @Min(1)
    @NotNull
    private Long clientId;
    @Valid
    @NotEmpty
    private List<IdTransferObject> products;

}
