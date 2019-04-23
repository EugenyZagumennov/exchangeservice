package revolut.exchange.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Balance entity
 *
 * @author Evgenii Zagumennov
 */
@Data
@AllArgsConstructor
public class Balance {
    private Long userId;
    private BigDecimal amount;
}
