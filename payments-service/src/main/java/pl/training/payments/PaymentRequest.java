package pl.training.payments;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;

import java.util.Map;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NonNull
    private FastMoney value;
    private Map<String, String> properties;

}
