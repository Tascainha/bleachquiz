const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";

async function request(path, options) {
  const response = await fetch(`${API_URL}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  if (!response.ok) {
    throw new Error(`Request to ${path} failed with status ${response.status}`);
  }

  return response.json();
}

export function getCharacters() {
  return request("/api/character");
}

export function getGameToday(mode) {
  return request(`/api/game/${mode}/today`);
}

export function postGuess(mode, characterId) {
  return request(`/api/game/${mode}/guess`, {
    method: "POST",
    body: JSON.stringify({ characterId }),
  });
}
