package pl.training.payments;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PaymentRequestTransferObject {

    @NotEmpty
    @NonNull
    private String value;

}
