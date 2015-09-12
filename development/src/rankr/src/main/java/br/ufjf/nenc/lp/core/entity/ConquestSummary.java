package br.ufjf.nenc.lp.core.entity;

import java.io.Serializable;

public abstract class ConquestSummary implements Serializable {

    public abstract void summarize(Conquest conquest);

    public abstract void updateSummary(Conquest conquest, String newValue);

    public abstract long getScore();
}
