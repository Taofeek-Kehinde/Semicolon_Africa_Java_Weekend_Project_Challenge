package cohorthub;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Learner {
    private final String id;
    private String name;
    private String email;
}


