
package cohorthub;

import exception.CohortException;
import java.util.*;

public class CohortManager {

    private final Map<String, Learner> learners = new HashMap<>();
    private final Map<String, StudyGroup> groups = new HashMap<>();
    private Queue<String> supportQueue = new LinkedList<>();

    public void registerLearner(Learner learner) {

        String id = learner.getId();
        String email = learner.getEmail();

        if (learners.containsKey(id)) {
            throw new CohortException("Learner ID already exists");
        }

        for (Learner existing : learners.values()) {
            if (existing.getEmail().equalsIgnoreCase(email)) {
                throw new CohortException("Email already exists");
            }
        }

        learners.put(id, learner);
    }

    public Learner findLearner(String id) {
        return learners.get(id);
    }

    public int getTotalLearners() {
        return learners.size();
    }

    public void updateLearner(String id, String name, String email) {

        Learner learner = learners.get(id);

        if (learner == null) {
            throw new CohortException("Learner not found");
        }

        for (Learner existing : learners.values()) {
            if (!existing.getId().equals(id)) {
                if (existing.getEmail().equalsIgnoreCase(email)) {
                    throw new CohortException("Email already exists");
                }
            }
        }

        learner.setName(name);
        learner.setEmail(email);
    }

    public boolean removeLearner(String id) {

        if (!learners.containsKey(id)) {
            return false;
        }

        learners.remove(id);

        for (StudyGroup group : groups.values()) {
            group.removeLearner(id);
        }

        Queue<String> newQueue = new LinkedList<>();

        while (!supportQueue.isEmpty()) {
            String currentId = supportQueue.poll();

            if (!currentId.equals(id)) {
                newQueue.offer(currentId);
            }
        }

        supportQueue = newQueue;

        return true;
    }

    public List<Learner> getSortedLearners() {

        List<Learner> sorted = new ArrayList<>();

        for (Learner learner : learners.values()) {
            sorted.add(learner);
        }

        Comparator<Learner> comparator =
                Comparator.comparing(Learner::getName)
                        .thenComparing(Learner::getId);

        Collections.sort(sorted, comparator);

        return sorted;
    }

    public void createGroup(String groupName) {

        if (groups.containsKey(groupName)) {
            throw new CohortException("Group already exists");
        }

        StudyGroup group = new StudyGroup(groupName);

        groups.put(groupName, group);
    }

    public void addLearnerToGroup(String learnerId, String groupName) {

        if (!learners.containsKey(learnerId)) {
            throw new CohortException("Learner not registered");
        }

        if (!groups.containsKey(groupName)) {
            throw new CohortException("Group not found");
        }

        StudyGroup group = groups.get(groupName);

        group.addLearner(learnerId);
    }

    public Set<String> getLearnerGroups(String learnerId) {

        if (!learners.containsKey(learnerId)) {
            throw new CohortException("Learner not found");
        }

        Set<String> learnerGroups = new TreeSet<>();

        for (StudyGroup group : groups.values()) {

            if (group.getLearnerIds().contains(learnerId)) {
                learnerGroups.add(group.getGroupName());
            }
        }

        return learnerGroups;
    }

    public void addToSupportQueue(String learnerId) {

        if (!learners.containsKey(learnerId)) {
            throw new CohortException("Learner not registered");
        }

        if (!supportQueue.contains(learnerId)) {
            supportQueue.offer(learnerId);
        }
    }

    public String attendSupportRequest() {

        if (supportQueue.isEmpty()) {
            throw new CohortException("No learners are waiting for support");
        }

        return supportQueue.poll();
    }

    public int getWaitingSupportCount() {
        return supportQueue.size();
    }

    public int getTotalGroups() {
        return groups.size();
    }

    public int getGroupMembershipCount(String groupName) {

        if (!groups.containsKey(groupName)) {
            throw new CohortException("Group not found");
        }

        return groups.get(groupName).getMembershipCount();
    }

}
