package net.weg.taskmanager.model.property;

import net.weg.taskmanager.model.Projeto;

import java.util.Collection;

public class SelectProperty extends Propriedade {

    private Collection<TextoProperty> opcoesSelecionadas;
    private Collection<TextoProperty> opcoesPossiveis;
    private Boolean valorUnico;

}
