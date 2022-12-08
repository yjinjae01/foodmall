package foodmall.domain;

import foodmall.domain.OrderStarted;
import foodmall.domain.OrderFinished;
import foodmall.StoreApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="FoodCooking_table")
@Data

public class FoodCooking  {
 
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long id;
    
    private String orderId;
    
    private String foodId;
    
    private String status;
    
    private String address;

    @PostPersist
    public void onPostPersist(){


    }

    public static FoodCookingRepository repository(){
        FoodCookingRepository foodCookingRepository = StoreApplication.applicationContext.getBean(FoodCookingRepository.class);
        return foodCookingRepository;
    }



    public void accept(AcceptCommand acceptCommand){

        if (acceptCommand.getAccept()){
            
            setStatus("OrderAccept");
            OrderAccepted orderAccepted = new OrderAccepted(this);
            orderAccepted.publishAfterCommit();
        } else {
            setStatus("OrderReject");
            OrderRejected orderRejected = new OrderRejected(this);
            orderRejected.publishAfterCommit();
        }
    }

    public void start(){
        // 요리 시작
        setStatus("OrderStart");
        OrderStarted orderStarted = new OrderStarted(this);
        orderStarted.publishAfterCommit();

    }
    
    public void finish(){
        // 요리 종료
        setStatus("OrderFinish");
        OrderFinished orderFinished = new OrderFinished(this);
        orderFinished.publishAfterCommit();

    }

    public static void orderInfo(OrderPlaced orderPlaced){

        FoodCooking foodCooking = new FoodCooking();
        foodCooking.setFoodId(orderPlaced.getFoodId());
        foodCooking.setOrderId(String.valueOf(orderPlaced.getId()));
        foodCooking.setAddress(orderPlaced.getAddress());
        foodCooking.setStatus(orderPlaced.getStatus());
        
        repository().save(foodCooking);

       
    }

    // 결제 완료
    public static void updateStatus(Paid paid){

        
        repository().findByOrderId(paid.getOrderId()).ifPresent(foodCooking->{
            
            foodCooking.setStatus("Paid");
            repository().save(foodCooking);

         });
        
    }

    // 주문 취소
    public static void updateStatus(OrderCanceled orderCanceled){


        repository().findByOrderId(String.valueOf(orderCanceled.getId())).ifPresent(foodCooking->{
            
            repository().delete(foodCooking);


         });
        
    }


}
