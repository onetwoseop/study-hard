"use client";

import Reveal from "./Reveal";
import SectionTitle from "./SectionTitle";

export default function StudyWrapUpSection() {
  return (
    <section className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center gap-10 bg-slate-50 px-6 py-24 md:px-16">
      <Reveal>
        <SectionTitle title="공부 현황을 파악하세요." />
      </Reveal>

      <Reveal delay={150}>
        <div className="mx-auto w-full max-w-md rounded-2xl border border-slate-200 bg-white p-8 shadow-lg">
          <p className="mb-6 text-sm font-semibold text-slate-500">공부 기록 마무리</p>
          <label className="mb-5 block">
            <span className="mb-1 block text-sm font-medium text-slate-700">
              오늘의 순공 시간은?
            </span>
            <input
              type="text"
              placeholder="예: 5시간 30분"
              className="w-full rounded-lg border border-slate-300 px-3 py-2.5 text-sm outline-none focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
            />
          </label>
          <label className="mb-6 block">
            <span className="mb-1 block text-sm font-medium text-slate-700">
              공부한 종류는?
            </span>
            <input
              type="text"
              placeholder="예: 전공 공부, 자격증, 어학"
              className="w-full rounded-lg border border-slate-300 px-3 py-2.5 text-sm outline-none focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
            />
          </label>
          <button
            type="button"
            onClick={() => console.log("[demo] 기록 저장 클릭")}
            className="w-full rounded-lg bg-indigo-600 py-2.5 text-sm font-semibold text-white transition hover:bg-indigo-700"
          >
            기록 저장
          </button>
        </div>
      </Reveal>
    </section>
  );
}
