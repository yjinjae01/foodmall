package foodmall.domain;

import foodmall.domain.Paid;
import foodmall.OrderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Payment_table")
@Data

public class Payment  {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long id;
    
    private String orderId;
    
    private Boolean cancel;

    @PostPersist
    public void onPostPersist(){


        Paid paid = new Paid(this);
        paid.publishAfterCommit();

    }

    @PreRemove
    public void onPreRemove(){

    }

    public static PaymentRepository repository(){
        PaymentRepository paymentRepository = OrderApplication.applicationContext.getBean(PaymentRepository.class);
        return paymentRepository;
    }

    public void cancelPayment(CancelPaymentCommand cancelPaymentCommand){
        setCancel(cancelPaymentCommand.getCancel());
    }

    public static void pay(OrderPlaced orderPlaced){

        
        Payment payment = new Payment();
        payment.setOrderId(String.valueOf(orderPlaced.getId()));
        payment.setCancel(false);
        repository().save(payment);
        
          
    }
   
    // 요리사의 주문 취소
    public static void rejectPayment(OrderRejected orderRejected){
        
        repository().findByOrderId(orderRejected.getOrderId()).ifPresent(payment->{
            
            repository().delete(payment);

         });
    }


}
