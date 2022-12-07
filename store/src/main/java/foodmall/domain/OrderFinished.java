package foodmall.domain;

import foodmall.domain.*;
import foodmall.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class OrderFinished extends AbstractEvent {

    private Long id;
    private String orderId;
    private String foodId;
    private String status;
    private String address;

    public OrderFinished(FoodCooking aggregate){
        super(aggregate);
    }
    public OrderFinished(){
        super();
    }
}
