
import cohorthub.CohortManager;
import cohorthub.Learner;
import exception.CohortException;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        CohortManager manager = new CohortManager();
        Scanner input = new Scanner(System.in);

        int choice = 0;

        while (choice != 12) {

            System.out.println("\n===== COHORT HUB =====");
            System.out.println("1. Register Learner");
            System.out.println("2. Find Learner");
            System.out.println("3. Update Learner");
            System.out.println("4. Remove Learner");
            System.out.println("5. Display All Learners");
            System.out.println("6. Create Study Group");
            System.out.println("7. Add Learner to Group");
            System.out.println("8. Display Learner Groups");
            System.out.println("9. Add Learner to Support Queue");
            System.out.println("10. Attend Support Request");
            System.out.println("11. Display Statistics");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

             String id;
            String name;
            String email;

            try {

                choice = Integer.parseInt(input.nextLine());

                switch (choice) {

                    case 1:

                        System.out.print("Enter learner ID: ");
                         id = input.nextLine();

                        System.out.print("Enter learner name: ");
                        name = input.nextLine();

                        System.out.print("Enter learner email: ");
                         email = input.nextLine();

                        Learner learner = new Learner(id, name, email);

                        manager.registerLearner(learner);

                        System.out.println("Learner registered successfully!");

                        break;

                    case 2:

                        System.out.print("Enter learner ID: ");
                        id = input.nextLine();

                        Learner found = manager.findLearner(id);

                        if (found != null) {
                            System.out.println(found);
                        } else {
                            System.out.println("Learner not found.");
                        }

                        break;

                    case 3:

                        System.out.print("Enter learner ID: ");
                        id = input.nextLine();

                        System.out.print("Enter new name: ");
                        name = input.nextLine();

                        System.out.print("Enter new email: ");
                        email = input.nextLine();

                        manager.updateLearner(id, name, email);

                        System.out.println("Learner updated successfully!");

                        break;

                    case 4:

                        System.out.print("Enter learner ID: ");
                        id = input.nextLine();

                        if (manager.removeLearner(id)) {
                            System.out.println("Learner removed successfully!");
                        } else {
                            System.out.println("Learner not found.");
                        }

                        break;

                    case 5:

                        List<Learner> learners = manager.getSortedLearners();

                        if (learners.isEmpty()) {
                            System.out.println("No learners registered.");
                        } else {
                            for (Learner current : learners) {
                                System.out.println(
                                        current.getId() + " - " +
                                                current.getName() + " - " +
                                                current.getEmail()
                                );
                            }
                        }

                        break;

                    case 6:

                        System.out.print("Enter group name: ");
                        String groupName = input.nextLine();

                        manager.createGroup(groupName);

                        System.out.println("Study group created successfully!");

                        break;

                    case 7:

                        System.out.print("Enter learner ID: ");
                        id = input.nextLine();

                        System.out.print("Enter group name: ");
                        groupName = input.nextLine();

                        manager.addLearnerToGroup(id, groupName);

                        System.out.println("Learner added to group successfully!");

                        break;

                    case 8:

                        System.out.print("Enter learner ID: ");
                        id = input.nextLine();

                        Set<String> learnerGroups = manager.getLearnerGroups(id);

                        if (learnerGroups.isEmpty()) {
                            System.out.println("Learner does not belong to any group.");
                        } else {
                            System.out.println("Learner groups: " + learnerGroups);
                        }

                        break;

                    case 9:

                        System.out.print("Enter learner ID: ");
                        id = input.nextLine();

                        manager.addToSupportQueue(id);

                        System.out.println("Learner added to support queue!");

                        break;

                    case 10:

                        String attendedId = manager.attendSupportRequest();

                        System.out.println(
                                "Now attending: " +
                                        manager.findLearner(attendedId).getName()
                        );

                        break;

                    case 11:

                        System.out.println("\n===== COHORT HUB STATISTICS =====");

                        System.out.println(
                                "Total learners: " + manager.getTotalLearners()
                        );

                        System.out.println(
                                "Total study groups: " + manager.getTotalGroups()
                        );

                        System.out.println(
                                "Learners waiting for support: " +
                                        manager.getWaitingSupportCount()
                        );

                        System.out.println("\nGROUP MEMBERSHIPS");

                        break;

                    case 12:

                        System.out.println("Thank you for using CohortHub!");

                        break;

                    default:

                        System.out.println("Invalid choice. Try again.");
                }

            } catch (CohortException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");

            }
        }

    }
}
