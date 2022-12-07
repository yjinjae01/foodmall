package foodmall.infra;

import foodmall.domain.*;
import foodmall.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryInfoViewHandler {

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPicked_then_CREATE_1 (@Payload Picked picked) {
        try {

            if (!picked.validate()) return;

            // view 객체 생성
            DeliveryInfo deliveryInfo = new DeliveryInfo();
            // view 객체에 이벤트의 Value 를 set 함
            deliveryInfo.setOrderId(picked.getOrderId());
            deliveryInfo.setStatus(picked.getStatus());
            deliveryInfo.setAddress(picked.getAddress());
            // view 레파지 토리에 save
            deliveryInfoRepository.save(deliveryInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenDelivered_then_UPDATE_1(@Payload Delivered delivered) {
        try {
            if (!delivered.validate()) return;
                // view 객체 조회

                List<DeliveryInfo> deliveryInfoList = deliveryInfoRepository.findByOrderId(delivered.getOrderId());
                for(DeliveryInfo deliveryInfo : deliveryInfoList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    deliveryInfo.setStatus(delivered.getStatus());
                // view 레파지 토리에 save
                deliveryInfoRepository.save(deliveryInfo);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

