package foodmall.infra;
import foodmall.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class PaymentHateoasProcessor implements RepresentationModelProcessor<EntityModel<Payment>>  {

    @Override
    public EntityModel<Payment> process(EntityModel<Payment> model) {
        model.add(Link.of(model.getRequiredLink("self").getHref() + "/cancelpayment").withRel("cancelpayment"));

        
        return model;
    }
    
}
