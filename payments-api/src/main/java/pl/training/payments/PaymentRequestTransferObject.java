package pl.training.payments;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaymentRequestTransferObject {

    @NotEmpty
    private String value;

}
