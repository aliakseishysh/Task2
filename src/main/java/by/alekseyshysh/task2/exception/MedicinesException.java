package by.alekseyshysh.task2.exception;

public class MedicinesException extends Exception {

	private static final long serialVersionUID = 8829831764924172401L;

	public MedicinesException() {
        super();
    }

    public MedicinesException(String message) {
        super(message);
    }

    public MedicinesException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedicinesException(Throwable cause) {
        super(cause);
    }
}
