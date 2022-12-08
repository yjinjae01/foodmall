package foodmall.infra;
import foodmall.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class OrderListHateoasProcessor implements RepresentationModelProcessor<EntityModel<OrderList>>  {

    @Override
    public EntityModel<OrderList> process(EntityModel<OrderList> model) {
       // model.add(Link.of(model.getRequiredLink("self").getHref() + "/cancel").withRel("cancel"));

        
        return model;
    }
    
}
