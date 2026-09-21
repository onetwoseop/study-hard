"use client";

import { useEffect, useMemo, useState } from "react";
import Reveal from "./Reveal";
import SectionTitle from "./SectionTitle";
import { generateMonthHeatmapValues, type HeatmapDayValue } from "@/lib/mock-data";

const YEAR_MONTH_OPTIONS = [
  { year: 2026, month: 7, label: "2026년 7월" },
  { year: 2026, month: 8, label: "2026년 8월" },
  { year: 2026, month: 9, label: "2026년 9월" },
];

const WEEKDAY_HEADERS = ["일", "월", "화", "수", "목", "금", "토"];

function cellColorClasses(count: number) {
  switch (count) {
    case 1:
      return "bg-indigo-200 text-indigo-900";
    case 2:
      return "bg-indigo-400 text-white";
    case 3:
      return "bg-indigo-600 text-white";
    case 4:
      return "bg-indigo-800 text-white";
    default:
      return "bg-slate-100 text-slate-400";
  }
}

export default function CalendarHeatmapSection() {
  const [selected, setSelected] = useState(YEAR_MONTH_OPTIONS[2]);
  const [values, setValues] = useState<HeatmapDayValue[]>([]);

  useEffect(() => {
    // Randomize only after mount/selection change so SSR and the first client render match.
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setValues(generateMonthHeatmapValues(selected.year, selected.month));
  }, [selected]);

  const leadingBlanks = useMemo(
    () => new Date(selected.year, selected.month - 1, 1).getDay(),
    [selected]
  );

  return (
    <section className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center gap-8 bg-slate-50 px-6 py-24 md:px-16">
      <Reveal className="flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
        <SectionTitle title="캘린더" />
        <select
          value={`${selected.year}-${selected.month}`}
          onChange={(e) => {
            const found = YEAR_MONTH_OPTIONS.find(
              (o) => `${o.year}-${o.month}` === e.target.value
            );
            if (found) setSelected(found);
          }}
          className="self-start rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm text-slate-700 shadow-sm outline-none focus:border-indigo-400"
        >
          {YEAR_MONTH_OPTIONS.map((o) => (
            <option key={o.label} value={`${o.year}-${o.month}`}>
              {o.label}
            </option>
          ))}
        </select>
      </Reveal>

      <Reveal
        delay={150}
        className="rounded-2xl border border-slate-200 bg-white p-6 shadow-lg"
      >
        <div className="grid grid-cols-7 gap-2">
          {WEEKDAY_HEADERS.map((weekday) => (
            <div
              key={weekday}
              className="pb-1 text-center text-xs font-medium text-slate-400"
            >
              {weekday}
            </div>
          ))}

          {Array.from({ length: leadingBlanks }).map((_, i) => (
            <div key={`blank-${i}`} />
          ))}

          {values.map((value) => (
            <div key={value.date} className="group relative">
              <div
                className={`flex aspect-square items-center justify-center rounded-lg text-xs font-semibold transition ${cellColorClasses(
                  value.count
                )}`}
              >
                {value.day}
              </div>
              <div className="pointer-events-none absolute bottom-full left-1/2 z-10 mb-2 w-max max-w-[10rem] -translate-x-1/2 rounded-lg bg-slate-900 px-3 py-2 text-xs text-white opacity-0 shadow-lg transition group-hover:opacity-100">
                {value.count > 0 ? (
                  <>
                    <p className="font-semibold">{value.title}</p>
                    <p className="text-slate-300">{value.hours}시간 공부</p>
                  </>
                ) : (
                  <p className="text-slate-300">기록 없음</p>
                )}
              </div>
            </div>
          ))}
        </div>
      </Reveal>
    </section>
  );
}
