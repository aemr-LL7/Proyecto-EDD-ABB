/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Classes;

import DataStructureClasses.SimpleList;

/**
 *
 * @author B-St
 */
public interface User {

    public String getName();

    public void setName(String name);

    public int getPriority();

    public SimpleList getFiles_list();

    public void setFiles_list(SimpleList files_list);

    public int getDni();

    public boolean hasPriorityOver(User comparingUser);

    public double getPriorityModifier();

    public String toString();

    public boolean isNameAvailable(String documentName);

    public void addDocument(Document document);

    public String getDocumentNames();

    public void deleteAllDocuments();

}
