"use client";

import { useMemo, useState } from "react";

export default function CharacterPicker({ characters, triedIds = [], onConfirm, disabled = false }) {
  const [query, setQuery] = useState("");
  const [selected, setSelected] = useState(null);

  const triedSet = useMemo(() => new Set(triedIds), [triedIds]);

  const suggestions = useMemo(() => {
    const normalized = query.trim().toLowerCase();
    if (!normalized) {
      return [];
    }

    function matchRank(name) {
      const words = name.toLowerCase().split(/\s+/);
      const firstWordIndex = words.findIndex((word, i) => i === 0 && word.startsWith(normalized));
      if (firstWordIndex === 0) return 0;
      const anyWordIndex = words.findIndex((word) => word.startsWith(normalized));
      return anyWordIndex === -1 ? null : 1;
    }

    return characters
      .map((character) => ({ character, rank: matchRank(character.name) }))
      .filter((entry) => entry.rank !== null)
      .sort((a, b) => a.rank - b.rank || a.character.name.localeCompare(b.character.name))
      .map((entry) => entry.character);
  }, [characters, query]);

  function handleSelect(character) {
    setSelected(character);
    setQuery(character.name);
  }

  function handleConfirm() {
    if (!selected || disabled) {
      return;
    }
    onConfirm(selected);
    setSelected(null);
    setQuery("");
  }

  return (
    <div className="w-full max-w-md">
      <div className="flex gap-2">
        <div className="relative flex-1">
          <input
            type="text"
            value={query}
            disabled={disabled}
            onChange={(event) => {
              setQuery(event.target.value);
              setSelected(null);
            }}
            placeholder="Digite o nome de um personagem..."
            className="w-full rounded-lg border border-stone-300 bg-paper-light px-3 py-2 text-sm text-zinc-900 outline-none focus:border-red-600 disabled:opacity-50"
          />
          {suggestions.length > 0 && !selected && (
            <ul className="absolute z-10 mt-1 w-full max-h-64 overflow-y-auto rounded-lg border border-stone-200 bg-paper-light shadow-lg shadow-stone-400/30">
              {suggestions.map((character) => {
                const alreadyTried = triedSet.has(character.id);
                return (
                  <li key={character.id}>
                    <button
                      type="button"
                      onClick={() => handleSelect(character)}
                      className="flex w-full items-center gap-2 px-3 py-2 text-left text-sm hover:bg-paper"
                    >
                      <img
                        src={character.avatar}
                        alt=""
                        className="h-8 w-8 rounded-full object-cover"
                      />
                      <span className="flex-1 text-zinc-900">{character.name}</span>
                      {alreadyTried && (
                        <span className="text-xs text-zinc-500">já tentado</span>
                      )}
                    </button>
                  </li>
                );
              })}
            </ul>
          )}
        </div>
        <button
          type="button"
          onClick={handleConfirm}
          disabled={!selected || disabled}
          className="shrink-0 rounded-lg bg-red-700 px-4 py-2 text-sm font-medium text-white transition hover:bg-red-800 disabled:cursor-not-allowed disabled:opacity-40"
        >
          Confirmar
        </button>
      </div>
      {selected && (
        <div className="mt-2 flex items-center gap-2 text-sm text-zinc-700">
          <img src={selected.avatar} alt="" className="h-10 w-10 rounded-full object-cover" />
          <span>{selected.name}</span>
        </div>
      )}
    </div>
  );
}
