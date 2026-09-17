"use client";

import { useEffect, useState } from "react";
import CharacterPicker from "@/components/CharacterPicker";
import PageShell from "@/components/PageShell";
import { getCharacters, getGameToday, postGuess } from "@/lib/api";
import { loadDailyState, saveDailyState } from "@/lib/dailyStorage";

const MODE = "photo";
const ZOOM_LEVELS = [600, 400, 280, 190, 130, 100];

export default function FotoPage() {
  const [characters, setCharacters] = useState([]);
  const [photo, setPhoto] = useState(null);
  const [date, setDate] = useState(null);
  const [attempts, setAttempts] = useState([]);
  const [won, setWon] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function load() {
      try {
        const [characterList, today] = await Promise.all([getCharacters(), getGameToday(MODE)]);
        setCharacters(characterList);
        setPhoto(today);
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

  const wrongAttempts = attempts.filter((attempt) => !attempt.correct).length;
  const zoom = won ? 100 : ZOOM_LEVELS[Math.min(wrongAttempts, ZOOM_LEVELS.length - 1)];
  const newestIndex = attempts.length - 1;

  return (
    <PageShell title="Foto" subtitle="Cada erro revela mais da imagem. Quem é?">
      {error && <p className="text-red-600">{error}</p>}

      {photo && (
        <div
          className="h-80 w-80 rounded-2xl border border-stone-300 shadow-lg shadow-stone-400/30"
          style={{
            backgroundImage: `url(${photo.imageUrl})`,
            backgroundSize: `${zoom}%`,
            backgroundPosition: `${photo.cropX}% ${photo.cropY}%`,
            backgroundRepeat: "no-repeat",
          }}
        />
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
