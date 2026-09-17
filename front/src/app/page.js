import Link from "next/link";
import Grain from "@/components/Grain";
import ModeCompletionBadge from "@/components/ModeCompletionBadge";
import { COVER_IMAGE_URL } from "@/lib/theme";

const MODES = [
  {
    mode: "classic",
    href: "/classic",
    title: "Clássico",
    description: "Compare atributos até descobrir o personagem do dia.",
  },
  {
    mode: "description",
    href: "/descricao",
    title: "Descrição",
    description: "Adivinhe o personagem pela descrição do dia.",
  },
  {
    mode: "photo",
    href: "/foto",
    title: "Foto",
    description: "Cada erro revela mais da imagem do personagem.",
  },
];

export default function Home() {
  return (
    <div className="relative min-h-screen w-full bg-zinc-950">
      <div
        className="absolute inset-0 scale-110 bg-cover bg-center opacity-30 blur-md"
        style={{ backgroundImage: `url(${COVER_IMAGE_URL})` }}
      />
      <div className="absolute inset-0 bg-gradient-to-b from-zinc-950/60 via-zinc-950/85 to-zinc-950" />
      <Grain opacity={0.05} />

      <div className="relative z-10 mx-auto flex min-h-screen max-w-3xl flex-col items-center justify-center gap-12 border-x border-red-900/30 bg-gradient-to-b from-paper-light via-paper to-paper-dark px-4 py-20 text-zinc-900 shadow-[0_0_90px_-10px_rgba(0,0,0,0.85)]">
        <Grain opacity={0.07} />
        <div className="relative text-center">
          <p className="text-xs uppercase tracking-[0.4em] text-red-700">3 minigames diários</p>
          <h1 className="mt-3 font-display text-6xl tracking-wide text-zinc-900 drop-shadow-[0_1px_0_rgba(255,255,255,0.6)]">
            Bleach<span className="text-red-700">Quiz</span>
          </h1>
          <p className="mt-3 text-zinc-600">Escolha um dos minigames abaixo e tente acertar o personagem do dia.</p>
        </div>

        <div className="relative grid w-full gap-5 sm:grid-cols-3">
          {MODES.map((mode) => (
            <Link
              key={mode.href}
              href={mode.href}
              className="group relative rounded-2xl bg-gradient-to-br from-stone-400/60 via-stone-300/50 to-red-800/40 p-[1px] transition duration-300 hover:from-red-600 hover:via-red-700/60 hover:to-orange-600 hover:shadow-[0_0_30px_-5px_rgba(220,38,38,0.35)]"
            >
              <div className="relative flex h-full flex-col gap-2 overflow-hidden rounded-2xl bg-paper-light p-6 transition group-hover:bg-paper">
                <Grain opacity={0.06} />
                <ModeCompletionBadge mode={mode.mode} />
                <span className="relative text-[10px] font-semibold uppercase tracking-[0.2em] text-red-700">
                  Minigame
                </span>
                <span className="relative font-display text-xl tracking-wide text-zinc-900">{mode.title}</span>
                <span className="relative text-sm text-zinc-600">{mode.description}</span>
                <span className="relative mt-3 text-xs font-medium uppercase tracking-wider text-red-700 opacity-0 transition group-hover:opacity-100">
                  Jogar →
                </span>
              </div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}
