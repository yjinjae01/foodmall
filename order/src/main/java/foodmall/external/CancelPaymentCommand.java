package foodmall.external;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Data
public class CancelPaymentCommand {

    private Boolean cancel;
}
