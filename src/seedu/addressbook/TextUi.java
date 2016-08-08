package seedu.addressbook;

import seedu.addressbook.model.person.ReadOnlyPerson;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Encapsulates the textual user interface (both input and output).
 * Should not be too tightly couple
 */
public class TextUi {

    /**
     * A decorative prefix added to the beginning of lines printed by AddressBook
     */
    public static final String LINE_PREFIX = "|| ";

    /**
     * A platform independent line separator.
     */
    public static final String LS = System.lineSeparator() + LINE_PREFIX;

    public static final String DIVIDER = "===================================================";

    /*
     * ==============NOTE TO STUDENTS======================================
     * These messages shown to the user are defined in one place for convenient
     * editing and proof reading. Such messages are considered part of the UI
     * and may be subjected to review by UI experts or technical writers. Note
     * that Some of the strings below include '%1$s' etc to mark the locations
     * at which java String.format(...) method can insert values.
     * ====================================================================
     */

    public static final String MESSAGE_CHANGES_SAVED_TO_STORAGE_FILE = "Changes saved to storage file.";
    public static final String MESSAGE_ERROR_MISSING_STORAGE_FILE = "Storage file missing: %1$s";
    public static final String MESSAGE_ERROR_READING_FROM_FILE = "Unexpected error: unable to read from file: %1$s";
    public static final String MESSAGE_ERROR_WRITING_TO_FILE = "Unexpected error: unable to write to file: %1$s";
    public static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";
    public static final String MESSAGE_GOODBYE = "Exiting Address Book... Good bye!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! " + LS + "%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_STORAGE_FILE_CONTENT = "Storage file has invalid content";;
    public static final String MESSAGE_INVALID_STORAGE_FILE_PATH = "Storage file should be valid and end with \".txt\"";
    public static final String MESSAGE_PERSON_NOT_IN_ADDRESSBOOK = "Person could not be found in address book";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_WELCOME = "Welcome to your Address Book!";
    public static final String MESSAGE_USING_STORAGE_FILE = "Using storage file : %1$s";

    /**
     * Offset required to convert between 1-indexing and 0-indexing.COMMAND_
     */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    /**
     * Format of a comment input line. Comment lines are silently consumed when reading user input.
     */
    public static final String COMMENT_LINE_FORMAT_REGEX = "#.*";

    private final Scanner in;
    private final PrintStream out;
    
    /**
     * preserves the last showed listing of persons for understanding user person references
     * based on the the last listing they saw
     */
    private List<? extends ReadOnlyPerson> lastShownPersonListing = new ArrayList<>();

    /**
     * latest input line retrieved from {@link #getUserCommand()}
     */
    private String lastEnteredCommand = "";
    
    public TextUi(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Checks if the user input line should be ignored.
     * Input should be ignored if it is parsed as a comment, is only whitespace, or is empty.
     *
     * @param rawInputLine full raw user input line.
     * @return true if the entire user input line should be ignored.
     */
    public boolean shouldIgnore(String rawInputLine) {
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    /**
     * Checks if the user input line is a comment line.
     *
     * @param rawInputLine full raw user input line.
     * @return true if input line is a comment.
     */
    public boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches(COMMENT_LINE_FORMAT_REGEX);
    }

    /**
     * Prompts for the command and reads the text entered by the user.
     * Ignores empty, pure whitespace, and comment lines.
     *
     * @see #shouldIgnore(String)
     * @return full line entered by the user
     */
    public String getUserCommand() {
        out.print(LINE_PREFIX + "Enter command: ");
        String fullInputLine = in.nextLine();
        // silently consume all ignored lines
        while (shouldIgnore(fullInputLine)) {
            fullInputLine = in.nextLine();
        }
        lastEnteredCommand = fullInputLine;
        return fullInputLine;
    }

    /**
     * Retrieves the latest entered non-ignored input line read from the user by {@link #getUserCommand()}
     */
    public String getLastEnteredCommand() {
        return lastEnteredCommand;
    }

    public void showWelcomeMessage(String version) {
        showToUser(DIVIDER, DIVIDER, version, MESSAGE_WELCOME, DIVIDER);
    }

    public void showGoodbyeMessage() {
        showToUser(MESSAGE_GOODBYE, DIVIDER, DIVIDER);
    }

    /**
     * Echoes the user input back to the user.
     */
    public void echoLastEnteredUserCommand() {
        showToUser("[Command entered:" + lastEnteredCommand + "]");
    }

    /**
     * Shows message(s) to the user
     */
    public void showToUser(String... message) {
        for (String m : message) {
            out.println(LINE_PREFIX + m);
        }
    }

    /**
     * Shows the result of a command execution to the user. Includes additional formatting to demarcate different
     * command execution segments.
     */
    public void showResultToUser(String result) {
        showToUser(result, DIVIDER);
    }

    /**
     * Retrieves the person from the last person listing view specified by the displayed list index.
     * 
     * @param displayedIndex the index of the target person as shown to user
     * @return the person in the last viewed listing
     */
    public ReadOnlyPerson getPersonFromLastShownListing(int displayedIndex) throws IndexOutOfBoundsException {
        return lastShownPersonListing.get(displayedIndex - DISPLAYED_INDEX_OFFSET);
    }
    
    /**
     * Shows a list of persons to the user, formatted as an indexed list.
     * Private contact details are hidden.
     */
    public void showPersonListView(List<? extends ReadOnlyPerson> persons) {
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            formattedPersons.add(person.getAsTextHidePrivate());
        }
        showToUserAsIndexedList(formattedPersons);
        updateLastShownPersonListing(persons);
    }

    /**
     * Updates and tracks the last viewed person listing to the user.
     */
    private void updateLastShownPersonListing(List<? extends ReadOnlyPerson> persons) {
        lastShownPersonListing = new ArrayList<>(persons); // copy to insulate from changes in original list
    }
    
    /**
     * Shows a list of strings to the user, formatted as an indexed list.
     */
    public void showToUserAsIndexedList(List<String> list) {
        showToUser(getIndexedListForViewing(list));
    }

    /**
     * Formats a list of strings as a viewable indexed list.
     */
    public static String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append(LS);
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Formats a string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    public static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

}
