# Tarefa 2 - Roguelike Deckbuilder Computeiros 025

Este projeto é um jogo de batalha em turnos inspirado no roguelike deckbuilder *Slay the Spire*. O jogador controla o herói **Didi Marco**, usando cartas de ataque e defesa para derrotar o temível **Sr. Dr. Cabo Arruda**. O jogo gerencia automaticamente a energia, os escudos e os turnos, com o inimigo tomando decisões automatizadas de ataque e defesa. A tematização do game busca caracterizar figuras icônicas do curso de engenharia de computacao 025 da UNICAMP e suas personalidades.

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
├── App.java                        # Ponto de entrada e loop principal de batalha
├── Data.java                       # Dados estáticos: cartas, heróis e inimigos
├── cards/
│   ├── Card.java                   # Classe abstrata base para cartas
│   ├── DamageCard.java             # Carta que causa dano ao alvo
│   └── ShieldCard.java             # Carta que concede escudo ao usuário
├── deck/
│   ├── Pile.java                   # Classe abstrata base para coleções de cartas
│   ├── BuyPile.java                # Pilha de compra (baralho)
│   ├── DiscardPile.java            # Pilha de descarte
│   └── Hand.java                   # Mão do jogador
└── entities/
    ├── Entity.java                 # Classe abstrata base para personagens
    ├── Hero.java                   # Herói controlado pelo jogador
    ├── Enemy.java                  # Classe abstrata para inimigos
    └── enemies/
        ├── Azoide.java             # Inimigo com alerta ofensivo
        └── Bzoide.java             # Inimigo com alerta defensivo 
```

## Sistema de Baralho

A implementação segue o funcionamento do jogo original *Slay the Spire*:

- **No início de cada turno**, o jogador compra automaticamente 5 cartas do topo da pilha de compra, sem custo de energia e sem ação do jogador. Essas cartas formam a mão do turno.
- **Durante o turno**, o jogador utiliza as cartas que desejar na ordem que preferir, desde que tenha energia suficiente. Cada carta utilizada é imediatamente movida para o topo da pilha de descarte.
- **Ao final do turno** (ao passar a vez ou ficar sem energia), as cartas restantes na mão vão para a pilha de descarte.
- **Se a pilha de compra se esgotar** (inclusive durante a fase de compra), as cartas da pilha de descarte são embaralhadas e passam a formar a nova pilha de compra. Isso ocorre automaticamente, sem intervenção do jogador.

Tanto o herói quanto o inimigo possuem suas próprias pilhas de compra e descarte, independentes entre si.

---

## Fluxo de Batalha

A cada rodada:

1. **Turno do inimigo (anúncio):** O inimigo compra cartas e anuncia sua intenção para o turno (quanto dano planeja causar ou quanto escudo planeja usar).
2. **Turno do herói:** O escudo do herói é zerado. O herói compra 5 cartas e escolhe quais usar. Cartas usadas vão para o descarte. Ao passar a vez ou ficar sem energia, as cartas restantes também vão para o descarte.
3. **Ataque do inimigo:** O escudo do inimigo é zerado (referente ao turno anterior). O inimigo executa a estratégia anunciada. O escudo ganho nessa fase persiste até o início da próxima rodada.

O combate termina quando o herói ou o inimigo chegam a 0 de vida.

## Desafio Extra — Anúncio de Intenções

Implementado conforme o enunciado. O método `announceEnemyStrategy()` é definido como abstrato em `Enemy` e implementado por cada inimigo concreto de acordo com seu estilo de combate:

- **Azoide** anuncia o total de dano planejado para o turno.
- **Bzoide** anuncia o total de escudo planejado para o turno.

O anúncio ocorre no início de cada rodada, antes do turno do herói, permitindo que o jogador adapte sua estratégia.
