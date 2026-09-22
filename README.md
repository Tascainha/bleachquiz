# BleachQuiz

BleachQuiz é um jogo diário de adivinhação inspirado em Bleach, no estilo Wordle/LoLdle: todo dia um personagem do anime é sorteado e o jogador tem que descobrir quem é através de tentativas, com dicas diferentes em cada minigame.

O projeto é dividido em duas partes:

- **`bleachdle/`** — API em Java (Spring Boot) que guarda os personagens, escolhe o personagem do dia e valida os palpites.
- **`front/`** — interface em Next.js/React onde o jogador realmente joga.

## Como o jogo funciona

Todo dia existe **um personagem do dia para cada minigame**, escolhido automaticamente pelo backend (não há como trapacear vendo o personagem antes de acertar). A seleção é determinística: cada modo usa a data atual (fuso `America/Sao_Paulo`) somada a um deslocamento próprio (`GameMode`) para escolher um índice dentro da lista de personagens, então descrição, foto e clássico raramente sorteiam o mesmo personagem no mesmo dia.

Em qualquer um dos três modos, o jogador digita o nome do personagem em um autocomplete e confirma o palpite; o progresso do dia fica salvo no navegador (`localStorage`), então dá pra fechar a aba e continuar depois sem perder as tentativas.

### 🧩 Clássico
Você escolhe um personagem e o jogo compara os atributos dele com os do personagem secreto, mostrando o que bate e o que não bate:

- **Raça** — igual, parcial (quando o personagem tem múltiplas raças e pelo menos uma coincide) ou diferente.
- **Gênero** — igual ou diferente.
- **Habilidades** — compara os tipos de habilidade (ex.: shikai, bankai, fullbring), igual/parcial/diferente.
- **Primeira aparição** — indica se o personagem secreto apareceu antes ou depois do seu palpite.
- **Status** (vivo/morto) — igual ou diferente.
- **Altura** — indica se o personagem secreto é mais alto ou mais baixo, além de saber se é exatamente igual.

O jogador vai eliminando possibilidades a cada tentativa até acertar o nome exato.

### 📝 Descrição
O jogo mostra a descrição/biografia do personagem do dia, mas com o nome dele censurado (substituído por blocos ████) para não entregar a resposta. O jogador lê as pistas do texto e tenta adivinhar quem é.

### 🖼️ Foto
Uma imagem do personagem do dia aparece extremamente ampliada e cortada em um ponto aleatório (mas fixo para o dia), praticamente irreconhecível. A cada palpite errado a imagem vai "afastando o zoom" e revelando mais detalhes, até o jogador acertar ou a imagem ficar totalmente visível.

## Stack técnica

**Backend (`bleachdle/`)**
- Java 17 + Spring Boot 3
- Spring Data JPA + MySQL (schema criado/atualizado automaticamente via `ddl-auto=update`)
- Personagens carregados a partir de arquivos JSON em `src/main/resources/data/characters/` (um arquivo por raça: shinigami, arrancar, quincy, humanos etc.)

**Frontend (`front/`)**
- Next.js 15 (App Router) + React 19
- Tailwind CSS para estilo

## Estrutura do projeto

```
bleachdle/
  src/main/java/com/bleachquiz/bleachdle/
    character/   -> entidade Character, repositório e endpoint de listagem
    game/        -> lógica de cada minigame (clássico, descrição, foto) e sorteio diário
    ability/     -> habilidades dos personagens (shikai, bankai, etc.)
  src/main/resources/data/characters/  -> base de personagens em JSON

front/
  src/app/
    page.js        -> tela inicial com os 3 minigames
    classic/        -> minigame Clássico
    descricao/       -> minigame Descrição
    foto/            -> minigame Foto
  src/components/    -> autocomplete de personagens, layout, indicadores de progresso
  src/lib/           -> chamadas à API e persistência do progresso diário no navegador
```

## Rodando o projeto localmente

### Backend
1. Tenha um MySQL rodando localmente e crie/ajuste as credenciais em `bleachdle/src/main/resources/application.properties` (por padrão espera um banco `bleachquiz` em `localhost:3306`, usuário `root`).
2. Na pasta `bleachdle/`, rode:
   ```
   ./mvnw spring-boot:run
   ```
3. A API sobe em `http://localhost:8080`.

### Frontend
1. Na pasta `front/`, instale as dependências e suba o servidor de desenvolvimento:
   ```
   npm install
   npm run dev
   ```
2. Acesse `http://localhost:3000`. Se a API estiver em outro endereço, defina `NEXT_PUBLIC_API_URL` no ambiente do frontend.

## Principais endpoints da API

- `GET /api/character` — lista todos os personagens (usado pelo autocomplete).
- `GET /api/game/{modo}/today` — dados do desafio do dia para o modo (`classic`, `description` ou `photo`).
- `POST /api/game/{modo}/guess` — envia um palpite (`characterId`) e recebe o resultado da comparação.
