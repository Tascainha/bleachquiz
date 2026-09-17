"use client";

import { useEffect, useState } from "react";
import { isCompletedToday } from "@/lib/dailyStorage";

export default function ModeCompletionBadge({ mode }) {
  const [completed, setCompleted] = useState(false);

  useEffect(() => {
    setCompleted(isCompletedToday(mode));
  }, [mode]);

  if (!completed) {
    return null;
  }

  return (
    <span className="absolute right-3 top-3 flex h-6 w-6 items-center justify-center rounded-full bg-green-600 text-xs font-bold text-white shadow-sm">
      ✓
    </span>
  );
}
