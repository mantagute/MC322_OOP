package gameOrchestrator;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

/**
 * Utilitário estático responsável por persistir e recuperar o estado do jogo
 * em disco, usando serialização JSON via Gson.
 *
 * <p>O arquivo de save é gravado no diretório de trabalho da aplicação com o
 * nome fixo {@value #SAVE_FILE}. Toda a lógica de leitura, escrita e remoção
 * do arquivo é centralizada aqui, mantendo {@link App} desacoplado do mecanismo
 * de persistência.
 *
 * <p>Não deve ser instanciada — todos os métodos são estáticos.
 *
 * @see SaveState
 * @see App
 */
public class SaveManager {

    /** Nome do arquivo de save gravado no diretório de trabalho. */
    private static final String SAVE_FILE = "save.json";

    /**
     * Construtor privado — impede instanciação desta classe utilitária.
     */
    private SaveManager() {}

    /**
     * Verifica se existe um arquivo de save no diretório de trabalho.
     *
     * @return {@code true} se o arquivo {@value #SAVE_FILE} existir;
     *         {@code false} caso contrário
     */
    public static boolean isThereAnySave() {
        return new File(SAVE_FILE).exists();
    }

    /**
     * Serializa o estado do jogo para JSON e o grava no arquivo {@value #SAVE_FILE},
     * sobrescrevendo qualquer save anterior.
     *
     * @param saveState estado do jogo a ser persistido; não deve ser {@code null}
     */
    public static void saveGame(SaveState saveState) {
        Gson gson = new Gson();
        String json = gson.toJson(saveState);
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lê o arquivo {@value #SAVE_FILE} e desserializa seu conteúdo JSON
     * em um {@link SaveState}.
     *
     * @return o {@link SaveState} recuperado do disco,
     *         ou {@code null} se ocorrer qualquer erro de leitura ou parsing
     */
    public static SaveState loadGame() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(SAVE_FILE)) {
            return gson.fromJson(reader, SaveState.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Remove o arquivo de save do disco, descartando o progresso salvo.
     * Chamado ao detectar derrota do herói, para forçar um novo jogo na próxima execução.
     * Não faz nada se o arquivo não existir.
     */
    public static void resetSave() {
        File save = new File(SAVE_FILE);
        if (save.exists()) {
            save.delete();
        }
    }
}
