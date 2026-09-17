const PREFIX = "bleachquiz";

function key(mode) {
  return `${PREFIX}:${mode}`;
}

const EMPTY_STATE = { date: null, attempts: [], won: false };

export function loadDailyState(mode, date) {
  if (typeof window === "undefined") {
    return EMPTY_STATE;
  }

  try {
    const raw = window.localStorage.getItem(key(mode));
    if (!raw) {
      return EMPTY_STATE;
    }

    const parsed = JSON.parse(raw);
    if (parsed.date !== date) {
      return EMPTY_STATE;
    }

    return parsed;
  } catch {
    return EMPTY_STATE;
  }
}

export function saveDailyState(mode, state) {
  if (typeof window === "undefined") {
    return;
  }

  try {
    window.localStorage.setItem(key(mode), JSON.stringify(state));
  } catch {
    // localStorage indisponível (modo privado etc.) — progresso não persiste, sem quebrar o jogo.
  }
}

// Mesmo fuso usado pelo backend (DailyCharacterService) pra decidir o "hoje" dos jogos.
export function todaySaoPaulo() {
  return new Intl.DateTimeFormat("en-CA", { timeZone: "America/Sao_Paulo" }).format(new Date());
}

export function isCompletedToday(mode) {
  return loadDailyState(mode, todaySaoPaulo()).won === true;
}
