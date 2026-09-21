import CalendarHeatmapSection from "@/components/CalendarHeatmapSection";
import IntroLoginSection from "@/components/IntroLoginSection";
import LandingSection from "@/components/LandingSection";
import NoteEditorSection from "@/components/NoteEditorSection";
import ReviewSection from "@/components/ReviewSection";
import StudyGraphSection from "@/components/StudyGraphSection";
import StudyWrapUpSection from "@/components/StudyWrapUpSection";

export default function Home() {
  return (
    <main>
      <LandingSection />
      <IntroLoginSection />
      <NoteEditorSection />
      <StudyWrapUpSection />
      <StudyGraphSection />
      <CalendarHeatmapSection />
      <ReviewSection />
    </main>
  );
}
