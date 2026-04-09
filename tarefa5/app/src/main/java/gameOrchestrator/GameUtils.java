package gameOrchestrator;

public final class GameUtils {
    
    /**
     * Pausa a execução da thread atual pelo tempo especificado.
     *
     * <p>
     * Caso a thread seja interrompida durante a espera, o flag de interrupção
     * é restaurado via {@link Thread#interrupt()} para que o chamador possa
     * tratá-lo.
     *
     * @param ms tempo de espera em milissegundos; valores negativos são tratados
     *           como zero pelo {@link Thread#sleep(long)}
     */
        public static void Wait(int ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    
}
