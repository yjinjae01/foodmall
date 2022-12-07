package foodmall.domain;

import foodmall.domain.*;
import foodmall.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderStarted extends AbstractEvent {

    private Long id;
    private String orderId;
    private String foodId;
    private String status;
    private String address;

    public OrderStarted(FoodCooking aggregate){
        super(aggregate);
    }
    public OrderStarted(){
        super();
    }
}
