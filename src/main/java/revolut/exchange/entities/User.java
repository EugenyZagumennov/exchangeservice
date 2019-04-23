package revolut.exchange.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User entity
 *
 * @author Evgenii Zagumennov
 */
@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
}
