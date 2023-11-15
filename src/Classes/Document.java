package Classes;

import java.util.Random;

/**
 *
 * @author B-St
 */
public class Document {

    private String ID;
    private String content;

    public Document(String content) {
        this.content = content;
        this.generateId();
    }

    private void generateId() {

        String newID = "";

        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            char letter = (char) (random.nextInt(26) + 'A');
            newID += letter;
        }

        newID += "-";

        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10);
            newID += digit;

        }
        
        this.ID = newID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
