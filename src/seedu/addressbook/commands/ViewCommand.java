package seedu.addressbook.commands;

import static seedu.addressbook.ui.TextUi.DISPLAYED_INDEX_OFFSET;

import java.util.List;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.ReadOnlyPerson;


/**
 * Shows details of the person identified using the last displayed index.
 * Private contact details are not shown.
 */
public class ViewCommand {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the non-private details of the person "
            + "identified by the index number in the last shown person listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_DETAILS = "Viewing person: %1$s";

    private int targetVisibleIndex = -1;

    public ViewCommand(int targetVisibleIndex) {
        this.targetVisibleIndex = targetVisibleIndex;
    }

    public CommandResult execute(AddressBook addressBook, List<? extends ReadOnlyPerson> relevantPersons) {
        try {
            final ReadOnlyPerson target = getTargetPerson(relevantPersons);
            if (!addressBook.containsPerson(target)) {
                return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
            }
            return new CommandResult(String.format(MESSAGE_VIEW_PERSON_DETAILS, target.getAsTextHidePrivate()));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    public int getTargetIndex() {
        return targetVisibleIndex;
    }

    public void setTargetIndex(int targetVisibleIndex) {
        this.targetVisibleIndex = targetVisibleIndex;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    private ReadOnlyPerson getTargetPerson(List<? extends ReadOnlyPerson> relevantPersons)
            throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }
}
