"use client";

import { useEffect, useState } from "react";
import CharacterPicker from "@/components/CharacterPicker";
import PageShell from "@/components/PageShell";
import { getCharacters, getGameToday, postGuess } from "@/lib/api";
import { loadDailyState, saveDailyState } from "@/lib/dailyStorage";

const MODE = "classic";

const STATUS_CLASSES = {
  EQUAL: "bg-green-600 text-white",
  PARTIAL: "bg-yellow-500 text-white",
  NONE: "bg-red-700 text-white",
};

const STATUS_LABELS = {
  Alive: "Vivo",
  Deceased: "Morto",
};

const GENDER_LABELS = {
  Male: "Masculino",
  Female: "Feminino",
  Unknown: "Desconhecido",
  Genderless: "Sem gênero",
};

function Cell({ status, children, animate, delay }) {
  return (
    <div
      className={`flex h-16 w-24 shrink-0 flex-col items-center justify-center rounded-md p-1 text-center text-xs font-medium ${STATUS_CLASSES[status] ?? "bg-stone-200 text-stone-500"} ${animate ? "animate-reveal-in" : ""}`}
      style={animate ? { animationDelay: `${delay}ms` } : undefined}
    >
      {children}
    </div>
  );
}

function trendArrow(trend) {
  if (trend === "HIGHER") return " ↑";
  if (trend === "LOWER") return " ↓";
  return "";
}

function appearanceLabel(media) {
  return media === "manga" ? "Capítulo" : "Episódio";
}

export default function ClassicPage() {
  const [characters, setCharacters] = useState([]);
  const [date, setDate] = useState(null);
  const [attempts, setAttempts] = useState([]);
  const [won, setWon] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function load() {
      try {
        const [characterList, today] = await Promise.all([getCharacters(), getGameToday(MODE)]);
        setCharacters(characterList);
        setDate(today.date);

        const saved = loadDailyState(MODE, today.date);
        setAttempts(saved.attempts);
        setWon(saved.won);
      } catch {
        setError("Não foi possível carregar o jogo. Verifique se o backend está rodando.");
      }
    }
    load();
  }, []);

  async function handleGuess(character) {
    if (!date || won) {
      return;
    }

    const result = await postGuess(MODE, character.id);
    const nextAttempts = [...attempts, result];
    const nextWon = result.correct;

    setAttempts(nextAttempts);
    setWon(nextWon);
    saveDailyState(MODE, { date, attempts: nextAttempts, won: nextWon });
  }

  const newestIndex = attempts.length - 1;

  return (
    <PageShell
      maxWidth="max-w-5xl"
      title="Clássico"
      subtitle="Adivinhe o personagem do dia comparando atributos. Verde = igual, amarelo = parcial, vermelho = nada em comum, cinza = sem dado pra comparar."
    >
      {error && <p className="text-red-600">{error}</p>}

      {won ? (
        <div className="flex flex-col items-center gap-2 rounded-2xl border border-green-600 bg-green-100 p-4 text-green-800">
          <span className="font-semibold">Acertou! O personagem era {attempts.at(-1)?.answerName}.</span>
        </div>
      ) : (
        <CharacterPicker
          characters={characters}
          triedIds={attempts.map((attempt) => attempt.guessed.id)}
          onConfirm={handleGuess}
        />
      )}

      {attempts.length > 0 && (
        <div className="w-full overflow-x-auto">
          <div className="flex w-fit min-w-full flex-col items-center gap-3">
            <div className="flex gap-2 px-1 text-center text-[11px] font-semibold text-zinc-500">
              <span className="w-32 shrink-0">Personagem</span>
              <span className="w-24 shrink-0">Raça</span>
              <span className="w-24 shrink-0">Gênero</span>
              <span className="w-24 shrink-0">Habilidade</span>
              <span className="w-24 shrink-0">1ª Aparição</span>
              <span className="w-24 shrink-0">Status</span>
              <span className="w-24 shrink-0">Altura</span>
            </div>
            {attempts
              .map((attempt, i) => ({ attempt, i }))
              .slice()
              .reverse()
              .map(({ attempt, i }) => {
                const isNewest = i === newestIndex;
                return (
                  <div key={`${i}-${attempt.guessed.id}`} className="flex gap-2">
                    <div
                      className={`flex h-16 w-32 shrink-0 flex-col items-center justify-center gap-1 rounded-md border border-stone-300 bg-paper-light p-1 text-center text-xs ${isNewest ? "animate-reveal-in" : ""}`}
                      style={isNewest ? { animationDelay: "0ms" } : undefined}
                    >
                      <img src={attempt.guessed.avatar} alt="" className="h-8 w-8 rounded-full object-cover" />
                      <span className="line-clamp-2 w-full leading-tight">{attempt.guessed.name}</span>
                    </div>
                    <Cell status={attempt.comparison.race} animate={isNewest} delay={70}>
                      {attempt.guessed.race.join(", ")}
                    </Cell>
                    <Cell status={attempt.comparison.gender} animate={isNewest} delay={140}>
                      {GENDER_LABELS[attempt.guessed.gender] ?? attempt.guessed.gender}
                    </Cell>
                    <Cell status={attempt.comparison.abilities} animate={isNewest} delay={210}>
                      {attempt.guessed.abilityTypes.length > 0 ? attempt.guessed.abilityTypes.join(", ") : "Não possui"}
                    </Cell>
                    <Cell
                      status={attempt.comparison.firstAppearanceValue === "EQUAL" ? "EQUAL" : "NONE"}
                      animate={isNewest}
                      delay={280}
                    >
                      {appearanceLabel(attempt.guessed.firstAppearanceMedia)} {attempt.guessed.firstAppearanceValue}
                      {trendArrow(attempt.comparison.firstAppearanceValue)}
                    </Cell>
                    <Cell status={attempt.comparison.status} animate={isNewest} delay={350}>
                      {STATUS_LABELS[attempt.guessed.status] ?? attempt.guessed.status ?? "—"}
                    </Cell>
                    <Cell
                      status={attempt.guessed.height ? attempt.comparison.height : "NONE"}
                      animate={isNewest}
                      delay={420}
                    >
                      {attempt.guessed.height ? `${attempt.guessed.height} cm` : "Desconhecida"}
                      {trendArrow(attempt.comparison.heightTrend)}
                    </Cell>
                  </div>
                );
              })}
          </div>
        </div>
      )}
    </PageShell>
  );
}
