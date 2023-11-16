package Classes;

import java.util.Random;

/**
 *
 * @author B-St
 */
public class Document {

    private String ID;
    private int numPages;
    private String content;
    private boolean isPriority;
    
    public Document(String content) {
        this.content = content;
        this.generateId();
    }
    
    public Document(String content, int numPages) {
        this.content = content;
        this.setNumPages(numPages);
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

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
