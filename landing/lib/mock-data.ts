export const quotes = [
  "오늘 하루도 최선을 다한 당신, 정말 멋져요.",
  "작은 기록이 쌓여 큰 성장을 만듭니다.",
  "어제보다 나은 오늘, 그것으로 충분합니다.",
  "공부는 배신하지 않는다.",
  "지금의 1시간이 내일의 나를 바꿉니다.",
  "완벽하지 않아도 괜찮아요. 계속하는 것이 중요합니다.",
  "기록하는 사람이 결국 성장합니다.",
];

export function pickRandomQuote() {
  return quotes[Math.floor(Math.random() * quotes.length)];
}

export const weekdayLabels = ["월", "화", "수", "목", "금", "토", "일"];

export interface StudyDataPoint {
  label: string;
  hours: number;
}

export function generateWeeklyStudyData(): StudyDataPoint[] {
  return weekdayLabels.map((day) => ({
    label: day,
    hours: Math.round((Math.random() * 7 + 1) * 10) / 10,
  }));
}

export function generateMonthlyStudyData(): StudyDataPoint[] {
  return Array.from({ length: 5 }, (_, i) => ({
    label: `${i + 1}주`,
    hours: Math.round((Math.random() * 30 + 10) * 10) / 10,
  }));
}

export function getWeekRangeLabel(reference: Date = new Date()): string {
  const day = reference.getDay();
  const mondayOffset = day === 0 ? -6 : 1 - day;
  const monday = new Date(reference);
  monday.setDate(reference.getDate() + mondayOffset);
  const sunday = new Date(monday);
  sunday.setDate(monday.getDate() + 6);
  const fmt = (d: Date) => `${d.getMonth() + 1}월 ${d.getDate()}일`;
  return `${fmt(monday)} ~ ${fmt(sunday)}`;
}

export function getMonthLabel(reference: Date = new Date()): string {
  return `${reference.getFullYear()}년 ${reference.getMonth() + 1}월`;
}

export interface StudyLogSummary {
  date: string;
  title: string;
}

export const studyLogSummaries: StudyLogSummary[] = [
  { date: "9/12", title: "운영체제 프로세스 동기화 정리" },
  { date: "9/13", title: "네트워크 TCP/IP 3-way handshake" },
  { date: "9/15", title: "자료구조 트리 정리" },
  { date: "9/16", title: "알고리즘 DP 문제풀이" },
  { date: "9/17", title: "데이터베이스 정규화 복습" },
];

export interface HeatmapDayValue {
  date: string;
  day: number;
  count: number;
  title?: string;
  hours?: number;
}

const HEATMAP_TITLE_POOL = [
  "자료구조 트리 정리",
  "알고리즘 DP 문제풀이",
  "운영체제 프로세스 동기화",
  "네트워크 TCP/IP 정리",
  "데이터베이스 정규화",
  "영어 단어 암기",
  "선형대수 과제",
  "알고리즘 그래프 탐색",
];

export function generateMonthHeatmapValues(year: number, month: number): HeatmapDayValue[] {
  const daysInMonth = new Date(year, month, 0).getDate();
  const values: HeatmapDayValue[] = [];
  for (let day = 1; day <= daysInMonth; day += 1) {
    const roll = Math.random();
    let count = 0;
    if (roll > 0.75) count = 4;
    else if (roll > 0.55) count = 3;
    else if (roll > 0.35) count = 2;
    else if (roll > 0.2) count = 1;
    const mm = String(month).padStart(2, "0");
    const dd = String(day).padStart(2, "0");
    values.push({
      date: `${year}-${mm}-${dd}`,
      day,
      count,
      title:
        count > 0
          ? HEATMAP_TITLE_POOL[Math.floor(Math.random() * HEATMAP_TITLE_POOL.length)]
          : undefined,
      hours: count > 0 ? Math.round((count * 1.1 + Math.random() * 1.5) * 10) / 10 : undefined,
    });
  }
  return values;
}
