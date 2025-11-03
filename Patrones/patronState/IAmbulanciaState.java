package patronState;

public interface IAmbulanciaState {
    boolean puedeAtencionDomicilio();
    void solicitarAtencionDomicilio();

    boolean puedeTrasladoClinica();
    void solicitarTrasladoClinica();

    boolean puedeRetornoAutomatico();
    void retornoAutomatico();

    boolean puedeMantenimiento();
    void solicitarMantenimiento();
}
