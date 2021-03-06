package pl.training.payments;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import pl.training.commons.FastMoneyMapper;

@Mapper(componentModel = "spring", uses = {FastMoneyMapper.class})
public interface PaymentsMapper {

    PaymentRequest toPaymentRequest(PaymentRequestTransferObject paymentRequestTransferObject);

    PaymentTransferObject toPaymentTransferObject(Payment payment);

    @ValueMapping(source = "STARTED", target = "NOT_CONFIRMED")
    PaymentStatusTransferObject toPaymentStatusTransferObject(PaymentStatus paymentStatus);

}
