package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.GroupingLogic;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;



/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {


    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //    @Test
    //    public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //        Person editedPerson = new PersonBuilder().build();
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
    //
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
    //        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    //        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    //        GroupingLogic.groupStudents(expectedModel);
    //        GroupingLogic.groupStudents(model);
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
    //              Messages.format(editedPerson));
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //        // Initial setup
    //        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    //        GroupingLogic.groupStudents(model);
    //
    //        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
    //        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
    //
    //        // Build person with grades to trigger recalculation
    //        PersonBuilder personInList = new PersonBuilder(lastPerson);
    //        Person editedPerson = personInList.withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB)
    //                .withTags(VALID_TAG_HUSBAND)
    //                .withGrade(PersonBuilder.A_GRADES) // Add grades to trigger recalculation
    //                .build();
    //
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
    //                .withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB)
    //                .withTags(VALID_TAG_HUSBAND)
    //                .withGrade(PersonBuilder.A_GRADES) // Add grades to descriptor
    //                .build();
    //
    //        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
    //
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.setPerson(lastPerson, editedPerson);
    //        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    //        GroupingLogic.groupStudents(expectedModel);
    //
    //        editedPerson = expectedModel.getFilteredPersonList().get(indexLastPerson.getZeroBased());
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
    //              Messages.format(editedPerson));
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        /// Test exact duplicate
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand exactDuplicateCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());
        assertCommandFailure(exactDuplicateCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void execute_editGradesRecalculatesGroups_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Prepare edit descriptor with new grades
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(personToEdit.getName().fullName)
                .withPhone(personToEdit.getPhone().value)
                .withEmail(personToEdit.getEmail().value)
                .withAddress(personToEdit.getAddress().value)
                .withGrade(PersonBuilder.A_GRADES)
                .build();

        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        Person editedPerson = new PersonBuilder(personToEdit)
                .withGrade(PersonBuilder.A_GRADES)
                .build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        GroupingLogic.groupStudents(expectedModel);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS,
                Messages.format(editedPerson));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);

        // Verify that the study group has been updated
        Person editedPersonInModel = model.getFilteredPersonList().get(0);
        Person editedPersonInExpectedModel = expectedModel.getFilteredPersonList().get(0);
        assertEquals(editedPersonInExpectedModel.getStudyGroup(), editedPersonInModel.getStudyGroup());
    }

}
