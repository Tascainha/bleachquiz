"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { isCompletedToday } from "@/lib/dailyStorage";

const MODES = [
  { mode: "classic", href: "/classic", label: "Clássico" },
  { mode: "description", href: "/descricao", label: "Descrição" },
  { mode: "photo", href: "/foto", label: "Foto" },
];

export default function ModeNav() {
  const pathname = usePathname();
  const [completed, setCompleted] = useState({});

  useEffect(() => {
    const status = {};
    for (const { mode } of MODES) {
      status[mode] = isCompletedToday(mode);
    }
    setCompleted(status);
  }, [pathname]);

  return (
    <nav aria-label="Trocar de minigame" className="flex flex-wrap items-center justify-center gap-2">
      <span className="text-[10px] font-semibold uppercase tracking-wider text-zinc-500">Minigames:</span>
      {MODES.map(({ mode, href, label }) => {
        const active = pathname === href;
        return (
          <Link
            key={mode}
            href={href}
            className={`flex items-center gap-1.5 rounded-full border px-3 py-1 text-xs font-medium transition ${
              active
                ? "border-red-700 bg-red-700 text-white"
                : "border-stone-300 bg-paper-light text-zinc-700 hover:border-red-400 hover:text-red-700"
            }`}
          >
            {label}
            {completed[mode] && (
              <span
                className={`flex h-4 w-4 items-center justify-center rounded-full text-[10px] ${
                  active ? "bg-white text-red-700" : "bg-green-600 text-white"
                }`}
              >
                ✓
              </span>
            )}
          </Link>
        );
      })}
    </nav>
  );
}
