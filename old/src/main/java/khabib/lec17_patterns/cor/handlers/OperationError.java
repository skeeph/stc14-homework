package khabib.lec17_patterns.cor.handlers;

/**
 * Ошибка возникающая в отсутствии прав для операции
 */
public class OperationError extends Exception {
    public OperationError(String message) {
        super(message);
    }
}
