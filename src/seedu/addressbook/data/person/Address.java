package seedu.addressbook.data.person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address extends Contact{

    public static final String EXAMPLE = "123, some street";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Person addresses has to be in this"
                                                            + "format : block, street, unit, postal code";
    public static final String ADDRESS_VALIDATION_REGEX = "(.+),(.+),(.+),(.+)";

    public final String value;
    
    private Block block;
    private Unit unit;
    private Street street;
    private PostalCode postalCode;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address, boolean isPrivate) throws IllegalValueException {
        this.isPrivate = isPrivate;
        this.value = address;
        splitAddress(address);
    }

    private void splitAddress(String address) throws IllegalValueException{
        Pattern pattern = Pattern.compile(ADDRESS_VALIDATION_REGEX);
        Matcher m = pattern.matcher(address);
        if (m.find()){
            setBlock(new Block(m.group(1)));
            setStreet(new Street(m.group(2)));
            setUnit(new Unit(m.group(3)));
            setPostalCode(new PostalCode(m.group(4)));
        } else {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid person address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    class Block{
        private String blockDetails;
        
        Block(String blockDetails){
            this.blockDetails = blockDetails;
        }
        
        public String getBlock(){
            return this.blockDetails;
        }
        
    }
    
    class Street{
        private String streetDetails;
        
        Street(String streetDetails){
            this.streetDetails = streetDetails;
        }
        
        public String getStreet(){
            return this.streetDetails;
        }
    }
    
    class Unit{
        private String unitDetails;
        
        Unit(String unitDetails){
            this.unitDetails = unitDetails;
        }
        
        public String getUnit(){
            return this.unitDetails;
        }
    }
    
    class PostalCode{
        private String postalCodeDetails;
        
        PostalCode(String postalCodeDetails){
            this.postalCodeDetails = postalCodeDetails;
        }
        
        public String getPostalCode(){
            return this.postalCodeDetails;
        }
    }
}