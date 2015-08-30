package br.ufjf.nenc.thautology.exception;

public class QuestionAlreadyAnsweredException extends  RuntimeException {

    public QuestionAlreadyAnsweredException(Throwable cause) {
        super(cause);
    }

    public QuestionAlreadyAnsweredException() {
        super();
    }
}
