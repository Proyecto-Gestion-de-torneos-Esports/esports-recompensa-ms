package esports.Recompensas.exception;

public class RecompensaNotFoundException extends  RuntimeException{
    public RecompensaNotFoundException(String mensaje){
        super(mensaje);
    }
}
