import Link from "next/link";
import Grain from "@/components/Grain";
import ModeNav from "@/components/ModeNav";
import { COVER_IMAGE_URL } from "@/lib/theme";

export default function PageShell({ title, subtitle, maxWidth = "max-w-2xl", children }) {
  return (
    <div className="relative min-h-screen w-full bg-zinc-950">
      <div
        className="absolute inset-0 scale-110 bg-cover bg-center opacity-25 blur-md"
        style={{ backgroundImage: `url(${COVER_IMAGE_URL})` }}
      />
      <div className="absolute inset-0 bg-gradient-to-b from-zinc-950/70 via-zinc-950/85 to-zinc-950" />
      <Grain opacity={0.05} />

      <div
        className={`relative z-10 mx-auto flex min-h-screen flex-col items-center gap-6 border-x border-red-900/30 bg-gradient-to-b from-paper-light via-paper to-paper-dark px-4 py-10 text-zinc-900 shadow-[0_0_90px_-10px_rgba(0,0,0,0.85)] ${maxWidth}`}
      >
        <Grain opacity={0.07} />
        <div className="relative flex w-full flex-col items-center gap-3 sm:flex-row sm:justify-between">
          <Link href="/" className="text-sm text-zinc-500 transition hover:text-red-700">
            ← Voltar
          </Link>
          <ModeNav />
        </div>
        <div className="relative text-center">
          <p className="text-xs font-semibold uppercase tracking-[0.3em] text-red-700">Minigame</p>
          <h1 className="mt-1 font-display text-4xl tracking-wide text-zinc-900">{title}</h1>
          {subtitle && <p className="mt-2 text-sm text-zinc-600">{subtitle}</p>}
        </div>
        <div className="relative flex w-full flex-col items-center gap-6">{children}</div>
      </div>
    </div>
  );
}
