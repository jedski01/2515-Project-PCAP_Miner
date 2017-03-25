package conversation;

/**
 * ConversationID
 * ACIT 2515 Activity name
 * Enter a brief one sentence description of what this class is
 *
 * @author Jed Iquin A00790108
 * @date 2017-03-25
 */

//TODO [jed] : make this a cached map so we don't have to create similar objects
public class ConversationID {

    private String addressA;
    private String addressB;

    public ConversationID() {

    }

    public ConversationID(String addressA, String addressB) {
        this.addressA = addressA;
        this.addressB = addressB;
    }

    public void setAddressA(String addressA) {
        this.addressA = addressA;
    }

    public void setAddressB(String addressB) {
        this.addressB = addressB;
    }

    public void setAddresses(String addressA, String addressB) {
        this.addressA = addressA;
        this.addressB = addressB;
    }


    public ConversationID reverse() {

        return new ConversationID(addressB, addressA);
    }

    public String getAddressA() {
        return addressA;
    }

    public String getAddressB() {
        return addressB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationID that = (ConversationID) o;

        if (!addressA.equals(that.addressA)) return false;
        return addressB.equals(that.addressB);
    }

    @Override
    public int hashCode() {
        int result = addressA.hashCode();
        result = 31 * result + addressB.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String result;

        result = String.format("Address A : %s || Address B: %s ", addressA, addressB);
        return result;
    }
}
