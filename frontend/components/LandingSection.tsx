"use client";

import { useEffect, useState } from "react";
import { pickRandomQuote } from "@/lib/mock-data";

export default function LandingSection() {
  const [quote, setQuote] = useState("오늘 하루도 최선을 다한 당신, 정말 멋져요.");

  useEffect(() => {
    // Randomize only after mount so SSR and the first client render match (avoids hydration mismatch).
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setQuote(pickRandomQuote());
  }, []);

  return (
    <section className="relative flex min-h-screen flex-col items-center justify-center overflow-hidden bg-gradient-to-b from-indigo-50 via-white to-white px-6 py-10 text-center">
      <div
        aria-hidden
        className="pointer-events-none absolute -right-24 -top-24 h-72 w-72 rounded-full bg-indigo-200/40 blur-3xl"
      />
      <div
        aria-hidden
        className="pointer-events-none absolute -left-24 top-1/3 h-72 w-72 rounded-full bg-sky-200/40 blur-3xl"
      />

      <div className="relative z-10 flex flex-col items-center gap-8">
        <span className="text-6xl font-extrabold tracking-tight text-indigo-600 md:text-8xl">
          studyHard
        </span>
        <a
          href="#intro-login"
          className="rounded-full bg-indigo-600 px-8 py-3 text-base font-semibold text-white shadow-lg shadow-indigo-200 transition hover:bg-indigo-700"
        >
          로그인하기
        </a>
      </div>

      <div className="relative z-10 mt-16">
        <p className="text-sm italic text-slate-500">&ldquo;{quote}&rdquo;</p>
      </div>
    </section>
  );
}
