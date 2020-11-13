package data;

import java.util.NoSuchElementException;

public enum Option {
    ADD(0, "Dodaj pojęcie"),
    REMOVE(1, "Usuń pojęcie"),
    ELEMENTS(2, "Wypisz pojęcia"),
    SEARCH(3, "Wyszukaj pojęcie"),
    EXPORT(4,"Eksportuj do pliku tekstowego"),
    IMPORT(5, "Importuj z pliku"),
    EXPORT_XML(6, "Eksportuj do pliku XML"),
    IMPORT_XML(7, "Importuj z pliku XML"),
    EXIT(8, "Wyjdź z programu");





    private int value;
    private String description;

    Option(int value, String description) {
        this.value = value;
        this.description = description;

    }

    public static Option getByDescription(String description) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].description.equals(description)) {
                System.out.println(description);
                return values()[i];
            }
        }

        throw new NoSuchElementException("No option available");
    }

    public String getDescription(){return description;}



}
