"use client";

import { useEffect, useState } from "react";
import CharacterPicker from "@/components/CharacterPicker";
import Grain from "@/components/Grain";
import PageShell from "@/components/PageShell";
import { getCharacters, getGameToday, postGuess } from "@/lib/api";
import { loadDailyState, saveDailyState } from "@/lib/dailyStorage";

const MODE = "description";

export default function DescricaoPage() {
  const [characters, setCharacters] = useState([]);
  const [description, setDescription] = useState("");
  const [date, setDate] = useState(null);
  const [attempts, setAttempts] = useState([]);
  const [won, setWon] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function load() {
      try {
        const [characterList, today] = await Promise.all([getCharacters(), getGameToday(MODE)]);
        setCharacters(characterList);
        setDescription(today.description);
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
    const nextAttempts = [...attempts, { ...result.guessed, correct: result.correct }];
    const nextWon = result.correct;

    setAttempts(nextAttempts);
    setWon(nextWon);
    saveDailyState(MODE, { date, attempts: nextAttempts, won: nextWon });
  }

  const newestIndex = attempts.length - 1;

  return (
    <PageShell title="Descrição" subtitle="Quem é o personagem descrito abaixo?">
      {error && <p className="text-red-600">{error}</p>}

      {description && (
        <div className="relative overflow-hidden w-full max-w-md rounded-2xl border border-stone-300 bg-paper-light p-5 text-center text-lg shadow-md shadow-stone-300/40">
          <Grain opacity={0.06} />
          <span className="relative">{description}</span>
        </div>
      )}

      {won ? (
        <div className="flex flex-col items-center gap-2 rounded-2xl border border-green-600 bg-green-100 p-4 text-green-800">
          <span className="font-semibold">Acertou! O personagem era {attempts.at(-1)?.name}.</span>
        </div>
      ) : (
        <CharacterPicker
          characters={characters}
          triedIds={attempts.map((attempt) => attempt.id)}
          onConfirm={handleGuess}
        />
      )}

      {attempts.length > 0 && (
        <div className="flex w-full max-w-md flex-col gap-2">
          <h2 className="text-sm font-semibold text-zinc-500">Tentativas</h2>
          {attempts
            .map((attempt, i) => ({ attempt, i }))
            .slice()
            .reverse()
            .map(({ attempt, i }) => (
              <div
                key={`${i}-${attempt.id}`}
                className={`flex items-center gap-2 rounded-xl border p-2 text-sm ${i === newestIndex ? "animate-reveal-in" : ""} ${
                  attempt.correct
                    ? "border-green-600 bg-green-100 text-green-800"
                    : "border-red-600 bg-red-100 text-red-800"
                }`}
              >
                <img src={attempt.avatar} alt="" className="h-8 w-8 rounded-full object-cover" />
                <span>{attempt.name}</span>
              </div>
            ))}
        </div>
      )}
    </PageShell>
  );
}
