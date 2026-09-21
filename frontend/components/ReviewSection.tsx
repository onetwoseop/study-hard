"use client";

import { useState } from "react";
import Reveal from "./Reveal";
import SectionTitle from "./SectionTitle";
import { studyLogSummaries } from "@/lib/mock-data";

export default function ReviewSection() {
  const [selected, setSelected] = useState(studyLogSummaries[0].date);

  return (
    <section className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center gap-10 px-6 py-24 md:px-16">
      <Reveal>
        <SectionTitle title="오늘 공부를 점검하세요." />
      </Reveal>

      <Reveal delay={150}>
        <div className="mx-auto w-full max-w-lg rounded-2xl border border-slate-200 bg-white p-8 shadow-lg">
          <p className="mb-6 text-base font-medium text-slate-700">
            공부한 날 내용을 골라 AI 요약과 문제를 생성하세요.
          </p>
          <label className="mb-6 block">
            <span className="mb-1 block text-sm font-medium text-slate-500">날짜 선택</span>
            <select
              value={selected}
              onChange={(e) => setSelected(e.target.value)}
              className="w-full rounded-lg border border-slate-300 px-3 py-2.5 text-sm text-slate-700 outline-none focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
            >
              {studyLogSummaries.map((log) => (
                <option key={log.date} value={log.date}>
                  {log.date} — {log.title}
                </option>
              ))}
            </select>
          </label>
          <button
            type="button"
            onClick={() => console.log("[demo] AI 요약/문제 생성 요청:", selected)}
            className="w-full rounded-lg bg-indigo-600 py-2.5 text-sm font-semibold text-white transition hover:bg-indigo-700"
          >
            AI 요약 · 문제 생성
          </button>
        </div>
      </Reveal>
    </section>
  );
}
