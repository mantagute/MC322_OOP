# Tarefa 3 — Roguelike Deckbuilder Computeiros 025

Este projeto é um jogo de batalha em turnos inspirado no roguelike deckbuilder *Slay the Spire*. O jogador controla o herói **Didi Marco**, usando cartas de ataque, defesa e efeitos para derrotar os inimigos **Sr. Doutor Cabo Arruda** (Azoide) e **3L** (Bzoide). O jogo implementa o padrão de design **Observer** para gerenciar efeitos acumuláveis aplicados às entidades durante o combate.

A tematização busca caracterizar figuras icônicas do curso de Engenharia de Computação 025 da UNICAMP e suas personalidades.

---

## Compilação e Execução

A partir da raiz do repositório:

```bash
javac -d bin $(find src -name "*.java")
java -cp bin App
```

---

## Estrutura do Projeto

```
src/
├── App.java                            # Ponto de entrada e loop principal de batalha
├── cards/
│   ├── Card.java                       # Classe abstrata base para cartas
│   ├── DamageCard.java                 # Carta que causa dano ao alvo
│   ├── ShieldCard.java                 # Carta que concede escudo ao usuário
│   └── EffectCard.java                 # Carta que aplica um efeito em uma entidade
├── deck/
│   ├── Pile.java                       # Classe abstrata base para coleções de cartas
│   ├── BuyPile.java                    # Pilha de compra (baralho)
│   ├── DiscardPile.java                # Pilha de descarte
│   └── Hand.java                       # Mão do jogador
├── effects/
│   ├── Effect.java                     # Classe abstrata base para efeitos (age como Subscriber)
│   ├── Poison.java                     # Efeito de veneno
│   └── Strength.java                   # Efeito de força
├── entities/
│   ├── Entity.java                     # Classe abstrata base para personagens
│   ├── Hero.java                       # Herói controlado pelo jogador
│   ├── Enemy.java                      # Classe abstrata para inimigos
│   └── enemies/
│       ├── Azoide.java                 # Inimigo ofensivo 
│       └── Bzoide.java                 # Inimigo defensivo
├── gameOrchestrator/
│   └── Data.java                       # Dados estáticos: cartas, heróis e inimigos
└── observer/
    ├── Publisher.java                  # Gerencia inscrições e notificações de eventos
    └── Subscriber.java                 # Interface abstrata para ouvintes de eventos
```

---

## Padrão de Design Observer

O sistema de efeitos é implementado por meio do padrão **Observer**:

- **`Publisher`** mantém uma lista de `Subscriber`s e os notifica sempre que um evento de combate ocorre (ex: `"FIM_TURNO"`). A classe `App` orquestra as notificações ao final de cada turno.
- **`Subscriber`** é uma classe abstrata com o método `beNotified(String event, Entity user, Entity target)`, chamado pelo `Publisher` a cada evento.
- **`Effect`** estende `Subscriber`. Ao ser criado, um efeito se inscreve automaticamente no `Publisher`. Quando seus acúmulos chegam a zero, ele se desincreve automaticamente, evitando efeitos "fantasma".

---

## Sistema de Efeitos

Cada efeito possui um alvo (a entidade afligida) e uma quantidade de **acúmulos**. Aplicar o mesmo efeito a uma entidade que já o possui soma os acúmulos ao invés de criar uma nova instância.

Os efeitos ativos são exibidos no terminal junto com as informações de combate (vida e escudo de cada entidade).

### Poison (Veneno)

- **Trigger:** `FIM_TURNO` da entidade afligida.
- **Efeito:** A entidade sofre dano igual à quantidade atual de acúmulos e perde 1 acúmulo.
- **Encerramento:** Removido automaticamente quando os acúmulos chegam a zero.

> Exemplo: 5 acúmulos de Poison causam 5 de dano no fim do turno, passando para 4 acúmulos na rodada seguinte.

### Strength (Força)

- **Trigger:** Passivo — multiplicador aplicado a todo dano ou escudo gerado pela entidade afligida via `applyEffectMultiplier()`.
- **Efeito:** Multiplica o valor base de dano e escudo pela quantidade de acúmulos.
- **Decaimento:** Reduz 0,25 acúmulos por `FIM_TURNO`, decaindo gradualmente.

> Exemplo: 2 acúmulos de Strength dobram o dano e escudo gerados pela entidade.

---

## Cartas de Efeito

### Cartas do Herói

| Carta | Custo | Efeito | Alvo |
|---|---|---|---|
| The one, the only. | 3 | Aplica 2 acúmulos de **Strength** | Próprio herói |
| Moggar | 1 | Aplica 10 acúmulos de **Poison** | Todos os inimigos |

### Cartas dos Inimigos

| Inimigo | Carta | Custo | Efeito | Alvo |
|---|---|---|---|---|
| Azoide | Ancestrais Paraenses | 3 | Aplica 2 acúmulos de **Strength** | Si mesmo |
| Azoide | AET | 1 | Aplica 10 acúmulos de **Poison** | Herói |
| Bzoide | Girl | 3 | Aplica 2 acúmulos de **Strength** | Si mesmo |
| Bzoide | Curitiba way of life | 1 | Aplica 10 acúmulos de **Poison** a todos | Herói |

---

## Sistema de Baralho

O funcionamento segue o original de *Slay the Spire*:

- **Início do turno:** o jogador compra automaticamente 5 cartas.
- **Durante o turno:** cartas usadas vão imediatamente para o descarte. Cada carta consome energia.
- **Fim do turno:** cartas restantes na mão vão para o descarte.
- **Pilha vazia:** as cartas do descarte são embaralhadas e formam uma nova pilha de compra automaticamente.

Herói e inimigos possuem pilhas independentes.

---

## Fluxo de Batalha

A cada rodada:

1. **Anúncio dos inimigos:** cada inimigo define e anuncia sua estratégia para o turno (dano ou escudo planejado).
2. **Turno do herói:** escudo zerado, 5 cartas compradas, jogador escolhe ações. Ao passar a vez ou ficar sem energia, cartas restantes vão ao descarte.
3. **Ataque dos inimigos:** cada inimigo executa sua estratégia contra o herói, em sequência.
4. **Notificação de fim de turno:** `FIM_TURNO` é disparado para o herói e para cada inimigo, ativando efeitos como Poison e o decaimento de Strength.

O combate termina quando o herói ou todos os inimigos chegam a 0 de vida.

---

## Desafio Extra — Múltiplos Inimigos

Implementado conforme o enunciado. O jogador enfrenta simultaneamente dois inimigos:

- **Sr. Doutor Cabo Arruda** (Azoide) — focado em dano.
- **3L** (Bzoide) — focado em escudo.

Cada inimigo possui vida, escudo, energia e baralho independentes. Quando uma carta tem alvo único e há mais de um inimigo vivo, o jogador escolhe qual atacar. Cartas com `multiTarget` atingem todos os inimigos vivos automaticamente.
