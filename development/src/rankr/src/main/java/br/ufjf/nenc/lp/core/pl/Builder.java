package br.ufjf.nenc.lp.core.pl;

public interface Builder {

    public boolean isResponsibleFor(Feature feature);

    Product build(Feature feature) throws ProductLineException;
}
