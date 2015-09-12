package br.ufjf.nenc.lp.core.pl;

public class InvalidFeatureName extends RuntimeException{

    private static final String PATTERN_NAME_ASSERTION_FAILURE = "Nome de classe para modelo inválido '%s': " +
            "Uma classe que representa uma feature deve possuir 'Feature' exatamente uma vez no nome.";


    public InvalidFeatureName(Class<? extends Feature> clazz) {
        super(String.format(PATTERN_NAME_ASSERTION_FAILURE, clazz.getName()));
    }
}
