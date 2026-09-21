export default function SectionTitle({
  title,
  className = "",
}: {
  title: string;
  className?: string;
}) {
  return (
    <h2
      className={`text-3xl font-bold tracking-tight text-slate-900 md:text-4xl ${className}`}
    >
      {title}
    </h2>
  );
}
