package pl.training.shop.payments;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import pl.training.payments.PaymentStatusTransferObject;
import pl.training.payments.PaymentTransferObject;

@Mapper(componentModel = "spring")
public interface PaymentsMapper {

    Payment toPayment(PaymentTransferObject paymentTransferObject);

    @ValueMapping(source = "NOT_CONFIRMED", target = "STARTED")
    @ValueMapping(source = "CANCELED", target = "FAILED")
    PaymentStatus toPaymentStatus(PaymentStatusTransferObject paymentStatusTransferObject);

}
