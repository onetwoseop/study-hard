import Reveal from "./Reveal";
import SectionTitle from "./SectionTitle";

export default function NoteEditorSection() {
  return (
    <section className="mx-auto flex min-h-screen max-w-5xl flex-col justify-center gap-10 px-6 py-24 md:px-16">
      <Reveal>
        <SectionTitle title="공부 기록을 작성하세요." />
        <p className="mt-3 max-w-xl text-slate-600">
          오늘 하루 공부한 것을 자유롭게 작성하세요. 그리기, 사진 첨부 등 모두 가능합니다.
        </p>
      </Reveal>

      <Reveal delay={150}>
        <div className="mx-auto w-full max-w-3xl overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-lg">
          <div className="flex items-center gap-2 border-b border-slate-100 bg-slate-50 px-4 py-3">
            <span className="h-3 w-3 rounded-full bg-red-300" />
            <span className="h-3 w-3 rounded-full bg-yellow-300" />
            <span className="h-3 w-3 rounded-full bg-green-300" />
            <span className="ml-3 text-xs text-slate-400">오늘의 공부 기록 — 2026.09.17</span>
          </div>
          <div className="flex items-center gap-4 border-b border-slate-100 px-4 py-2 text-slate-400">
            <span className="text-sm font-bold">B</span>
            <span className="text-sm italic">I</span>
            <span className="text-sm underline">U</span>
            <span className="text-sm">A⁺</span>
            <span className="text-sm">🖼️</span>
            <span className="text-sm">✏️</span>
          </div>
          <div className="px-6 py-8">
            <textarea
              rows={10}
              placeholder="오늘 하루 공부한 것을 자유롭게 작성하세요. 그리기, 사진 첨부 등 모두 가능합니다."
              className="w-full resize-none border-none p-0 text-sm leading-relaxed text-slate-700 outline-none placeholder:text-slate-400"
            />
          </div>
        </div>
      </Reveal>
    </section>
  );
}
