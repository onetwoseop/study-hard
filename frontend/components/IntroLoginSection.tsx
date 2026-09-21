"use client";

import { useState, type FormEvent } from "react";

export default function IntroLoginSection() {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");

  const handleLogin = (e: FormEvent) => {
    e.preventDefault();
    console.log("[demo] 로그인 시도:", { id, pw });
  };

  return (
    <section
      id="intro-login"
      className="flex min-h-screen flex-col items-center justify-center gap-12 bg-white px-6 py-24 md:px-16"
    >
      <div className="max-w-xl text-center">
        <h1 className="text-4xl font-bold leading-tight text-slate-900 md:text-5xl">
          공부 기록부터
          <br />
          AI 요약, 문제 생성까지
        </h1>
        <p className="mt-4 text-slate-600">
          매일의 공부를 기록하고, 흐름을 한눈에 확인하고, AI로 정리까지 — studyHard와
          함께하세요.
        </p>
      </div>

      <form
        onSubmit={handleLogin}
        className="w-full max-w-sm rounded-2xl border border-slate-200 bg-white p-6 shadow-sm"
      >
        <p className="mb-4 text-sm font-semibold text-slate-500">로그인</p>
        <label className="mb-3 block">
          <span className="mb-1 block text-xs font-medium text-slate-500">아이디</span>
          <input
            value={id}
            onChange={(e) => setId(e.target.value)}
            type="text"
            placeholder="studyhard_id"
            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
          />
        </label>
        <label className="mb-5 block">
          <span className="mb-1 block text-xs font-medium text-slate-500">비밀번호</span>
          <input
            value={pw}
            onChange={(e) => setPw(e.target.value)}
            type="password"
            placeholder="••••••••"
            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100"
          />
        </label>
        <button
          type="submit"
          className="w-full rounded-lg bg-indigo-600 py-2.5 text-sm font-semibold text-white transition hover:bg-indigo-700"
        >
          로그인
        </button>
      </form>
    </section>
  );
}
