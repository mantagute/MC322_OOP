# Tarefa 4 — Ferramentas para Desenvolvimento
## Roguelike Deckbuilder Computeiros 025

Este projeto é um jogo de batalha em turnos inspirado no roguelike deckbuilder *Slay the Spire*. O jogador controla o herói **Didi Marco**, usando cartas de ataque, defesa e efeitos para derrotar os inimigos **Sr. Doutor Cabo Arruda** (Azoide) e **3L** (Bzoide).

---

## Compilação e Execução

A partir da raiz do repositório (tarefa4):

```bash
./gradlew build
./gradlew run
```

Use a flag --console=plain na execução para maior clareza visual.

O projeto está configurado com Gradle utilizando a estrutura padrão `src/main/java`. A classe principal é `gameOrchestrator.App`, configurada no `build.gradle.kts`.

---

## Estrutura do Projeto (Gradle)

```
tarefa4/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew / gradlew.bat
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
└── app/
    └── src/
        └── main/
            └── java/
                ├── cards/
                │   ├── Card.java
                │   ├── DamageCard.java
                │   ├── ShieldCard.java
                │   └── EffectCard.java
                ├── deck/
                │   ├── Pile.java
                │   ├── BuyPile.java
                │   ├── DiscardPile.java
                │   └── Hand.java
                ├── effects/
                │   ├── Effect.java
                │   ├── Poison.java
                │   └── Strength.java
                ├── entities/
                │   ├── Entity.java
                │   ├── Hero.java
                │   ├── Enemy.java
                │   └── enemies/
                │       ├── Azoide.java
                │       └── Bzoide.java
                ├── gameOrchestrator/
                │   ├── App.java
                │   ├── Data.java
                │   └── UserInterface.java
                └── observer/
                    ├── Publisher.java
                    └── Subscriber.java
```

---

## Novas Cartas Implementadas (Tarefa 4)

Foram adicionadas **novas cartas** ao jogo, explorando uma decisão de design intencional: discernir os perfis dos inimigos em relação às classes de cartas disponíveis.

A lógica foi a seguinte:
- O **Azoide**, originalmente focado em dano, recebeu novas **cartas de dano** — as quais inicialmente eram de escudo.
- O **Bzoide**, originalmente focado em escudo, recebeu novas **cartas de escudo e veneno** — as quais inicialmente eram de dano.

Essa escolha demonstra uma das vantagens práticas do sistema orientado a objetos já construído: como `DamageCard`, `ShieldCard` e `EffectCard` são classes genéricas e reutilizáveis, atribuir qualquer tipo de carta a qualquer inimigo é trivial — basta instanciá-la em `Data.java` e adicioná-la ao baralho correspondente, sem nenhuma alteração nas classes existentes.

A **Caipirinha de Morango** é a carta que satisfaz o requisito de **interação com efeitos**, aplicando Poison via o sistema Observer já existente.

---

## Documentação com Javadoc

Todas as principais classes do projeto estão documentadas com comentários Javadoc, incluindo:

- Descrição de propósito de cada classe
- Documentação de todos os métodos públicos com `@param` e `@return`
- Atributos não triviais documentados inline

Para gerar a documentação HTML:

```bash
./gradlew javadoc
```

A documentação gerada estará em `app/build/docs/javadoc/index.html`. A task `javadoc` também é executada automaticamente como dependência do `./gradlew build`.

---

## Padrão de Design Observer

O sistema de efeitos é implementado via padrão **Observer**:

- **`Publisher`** mantém uma lista de `Subscriber`s e os notifica ao fim de cada turno com o evento `"FIM_TURNO"`.
- **`Subscriber`** é classe abstrata com o método `beNotified(String event, Entity user, Entity target)`.
- **`Effect`** estende `Subscriber`. Ao ser criado, se inscreve automaticamente no `Publisher`; ao expirar (acúmulos zerados), se desinscreve.

---

## Sistema de Efeitos

### Poison (Veneno)
- **Trigger:** `FIM_TURNO` da entidade afligida.
- **Efeito:** causa dano igual aos acúmulos atuais e perde 1 acúmulo por turno.

### Strength (Força)
- **Trigger:** passivo — multiplica dano e escudo gerados pela entidade.
- **Decaimento:** perde 0,25 acúmulos por `FIM_TURNO`.

---

## Fluxo de Batalha

1. **Inimigos anunciam** sua estratégia para o turno.
2. **Turno do herói:** compra 5 cartas, escolhe ações até passar a vez ou ficar sem energia.
3. **Inimigos atacam** executando a estratégia planejada.
4. **Fim de turno:** evento `FIM_TURNO` disparado para todas as entidades, ativando efeitos.

O combate termina quando o herói ou todos os inimigos chegam a 0 de vida.

---

## Desafio Extra — Interface Visual

A saída do terminal foi reformulada com:

- **Arte ASCII** na tela de introdução (título "DIDI MARCO vs SR. DR. CABO ARRUDA")
- **Texto colorido** via escape ANSI (vermelho para dano, azul para escudo, amarelo para energia, magenta para efeitos)
- **Limpeza de tela** entre turnos com `\033[H\033[2J`
- **Pausas entre prints** via `Thread.sleep()` para dar tempo de leitura ao jogador
- **Cabeçalhos de turno** diferenciados por cor para herói (ciano) e inimigos (vermelho/amarelo)
- **Cartas tachadas** quando o jogador não tem energia suficiente para jogá-las
- Toda a lógica visual encapsulada na classe `UserInterface`, separada da lógica de negócio

A classe `UserInterface` foi elaborada com o auxílio do **Claude Code**.