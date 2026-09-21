"use client";

import { useEffect, useState } from "react";
import {
  Bar,
  BarChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import Reveal from "./Reveal";
import SectionTitle from "./SectionTitle";
import {
  generateMonthlyStudyData,
  generateWeeklyStudyData,
  getMonthLabel,
  getWeekRangeLabel,
  type StudyDataPoint,
} from "@/lib/mock-data";

type Range = "weekly" | "monthly";

export default function StudyGraphSection() {
  const [range, setRange] = useState<Range>("weekly");
  const [weeklyData, setWeeklyData] = useState<StudyDataPoint[]>([]);
  const [monthlyData, setMonthlyData] = useState<StudyDataPoint[]>([]);

  useEffect(() => {
    // Randomize only after mount so SSR and the first client render match (avoids hydration mismatch).
    /* eslint-disable react-hooks/set-state-in-effect */
    setWeeklyData(generateWeeklyStudyData());
    setMonthlyData(generateMonthlyStudyData());
    /* eslint-enable react-hooks/set-state-in-effect */
  }, []);

  const data = range === "weekly" ? weeklyData : monthlyData;
  const periodLabel = range === "weekly" ? getWeekRangeLabel() : getMonthLabel();

  return (
    <section className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center gap-8 px-6 py-24 md:px-16">
      <Reveal className="flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
        <div>
          <SectionTitle title="공부 현황 그래프" />
          <p className="mt-2 text-sm text-slate-500">
            저번 주 공부기록, 이런 식으로 지금까지의 공부 현황을 한눈에 볼 수 있어요.
          </p>
        </div>
        <div className="inline-flex self-start rounded-full border border-slate-200 bg-white p-1 shadow-sm">
          {(["weekly", "monthly"] as Range[]).map((r) => (
            <button
              key={r}
              type="button"
              onClick={() => setRange(r)}
              className={`rounded-full px-4 py-1.5 text-sm font-medium transition ${
                range === r
                  ? "bg-indigo-600 text-white"
                  : "text-slate-500 hover:text-slate-800"
              }`}
            >
              {r === "weekly" ? "주간" : "월간"}
            </button>
          ))}
        </div>
      </Reveal>

      <Reveal delay={150} className="flex flex-col gap-3">
        <p className="text-sm font-semibold text-indigo-600">{periodLabel}</p>
        <div className="h-80 w-full rounded-2xl border border-slate-200 bg-white p-6 shadow-lg">
          <ResponsiveContainer width="100%" height="100%">
          <BarChart data={data} margin={{ top: 10, right: 10, left: 0, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0" />
            <XAxis
              dataKey="label"
              tick={{ fill: "#64748b", fontSize: 12 }}
              axisLine={false}
              tickLine={false}
            />
            <YAxis
              tick={{ fill: "#64748b", fontSize: 12 }}
              axisLine={false}
              tickLine={false}
              unit="h"
            />
            <Tooltip
              cursor={{ fill: "#eef2ff" }}
              formatter={(value) => [`${value}시간`, "공부 시간"]}
            />
            <Bar dataKey="hours" fill="#6366f1" radius={[6, 6, 0, 0]} />
          </BarChart>
          </ResponsiveContainer>
        </div>
      </Reveal>
    </section>
  );
}
