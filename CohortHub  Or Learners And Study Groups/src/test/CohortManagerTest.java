
package test;

import cohorthub.CohortManager;
import cohorthub.Learner;
import exception.CohortException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CohortManagerTest {

    private CohortManager manager;

    @BeforeEach
    void setUp() {
        manager = new CohortManager();
    }

    @Test
    void shouldRegisterLearner() {
        Learner learner = new Learner("L001", "Kehinde", "kenny@gmail.com");
        manager.registerLearner(learner);
        assertEquals(1, manager.getTotalLearners());
        assertEquals(learner, manager.findLearner("L001"));
    }

    @Test
    void shouldRejectDuplicateLearnerId() {
        manager.registerLearner(
                new Learner("L001", "Kehinde", "kenny@gmail.com")
        );

        assertThrows(CohortException.class, () ->
                manager.registerLearner(
                        new Learner("L001", "Mary", "mary@gmail.com")
                )
        );

        assertEquals(1, manager.getTotalLearners());
    }

    @Test
    void shouldRejectDuplicateEmailIgnoringCase() {
        manager.registerLearner(
                new Learner("L001", "Kehinde", "kehinde@gmail.com")
        );

        assertThrows(CohortException.class, () ->
                manager.registerLearner(
                        new Learner("L002", "Mary", "KEHINDE@gmail.com")
                )
        );

        assertEquals(1, manager.getTotalLearners());
    }

    @Test
    void shouldFindLearnerById() {
        Learner learner = new Learner("L001", "kehinde", "kenny@gmail.com");
        manager.registerLearner(learner);

        assertEquals(learner, manager.findLearner("L001"));
    }

    @Test
    void shouldReturnNullForUnknownLearnerId() {
        assertNull(manager.findLearner("L110"));
    }

    @Test
    void shouldUpdateLearnerNameAndEmail() {
        manager.registerLearner(
                new Learner("L001", "kehinde", "kehinde@gmail.com")
        );

        manager.updateLearner("L001", "David", "david@gmail.com");

        Learner updated = manager.findLearner("L001");

        assertEquals("L001", updated.getId());
        assertEquals("David", updated.getName());
        assertEquals("david@gmail.com", updated.getEmail());
    }

    @Test
    void shouldRejectUpdateWithEmailAlreadyInUse() {
        manager.registerLearner(
                new Learner("L001", "kehinde", "kehinde@gmail.com")
        );

        manager.registerLearner(
                new Learner("L002", "Mary", "mary@gmail.com")
        );

        assertThrows(CohortException.class, () ->
                manager.updateLearner("L002", "Mary", "kehinde@gmail.com")
        );
    }

    @Test
    void shouldRejectUpdateForUnregisteredLearner() {
        assertThrows(CohortException.class, () ->
                manager.updateLearner("L999", "David", "david@gmail.com")
        );
    }

    @Test
    void shouldRemoveLearner() {
        manager.registerLearner(
                new Learner("L001", "kehinde", "kehinde@gmail.com")
        );

        assertTrue(manager.removeLearner("L001"));
        assertNull(manager.findLearner("L001"));
        assertEquals(0, manager.getTotalLearners());
    }

    @Test
    void shouldReturnFalseWhenRemovingUnknownLearner() {
        assertFalse(manager.removeLearner("L999"));
    }


    @Test
    void shouldKeepLearnerLookupUnchangedAfterSorting() {
        Learner learner = new Learner("L001", "kehinde", "kehinde@gmail.com");
        manager.registerLearner(learner);

        manager.getSortedLearners();

        assertEquals(learner, manager.findLearner("L001"));
        assertEquals(1, manager.getTotalLearners());
    }

    @Test
    void shouldCreateStudyGroup() {
        manager.createGroup("Java");

        assertEquals(1, manager.getTotalGroups());
    }

    @Test
    void shouldRejectDuplicateGroupName() {
        manager.createGroup("Java");

        assertThrows(CohortException.class, () ->
                manager.createGroup("Java")
        );
    }

}
