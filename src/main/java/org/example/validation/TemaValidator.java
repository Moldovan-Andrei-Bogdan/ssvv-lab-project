package org.example.validation;

import org.example.domain.Tema;

public class TemaValidator implements org.example.validation.Validator<Tema> {

    /**
     * Valideaza o tema
     * @param entity - tema pe care o valideaza
     * @throws org.example.validation.ValidationException daca tema nu e valida
     */
    @Override
    public void validate(Tema entity) throws org.example.validation.ValidationException {
        if(entity.getID().equals("") || entity.getID() == null) {
            throw new org.example.validation.ValidationException("Numar tema invalid!");
        }
        if(entity.getDescriere().equals("")){
            throw new org.example.validation.ValidationException("Descriere invalida!");
        }
        if(entity.getDeadline() < 1 || entity.getDeadline() > 14) {
            throw new org.example.validation.ValidationException("Deadlineul trebuie sa fie intre 1-14.");
        }
        if(entity.getPrimire() < 1 || entity.getPrimire() > 14) {
            throw new org.example.validation.ValidationException("Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }
}
