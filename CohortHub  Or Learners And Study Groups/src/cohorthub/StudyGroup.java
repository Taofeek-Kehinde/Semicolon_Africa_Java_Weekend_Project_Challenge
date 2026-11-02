package cohorthub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.util.HashSet;
import java.util.Set;

@Data
public class StudyGroup {
    private final String groupName;
    private final Set<String> learnerIds = new HashSet<>();

    public void addLearner(String learnerId) {
        learnerIds.add(learnerId);
    }

    public void removeLearner(String learnerId) {
        learnerIds.remove(learnerId);
    }

    public int getMembershipCount() {
        return learnerIds.size();
    }
}