"use client";

import { useAuthStore } from "@/stores/auth/auth-store";

export default function Home() {
  const { user, isAuthenticated } = useAuthStore();

  console.log("User:", user);

  return (
    <div className="grid grid-rows-[20px_1fr_20px] p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <main className="flex flex-col gap-[32px] row-start-2 items-center">
        <h1>
          Seja bem-vindo ao Sportslife!
        </h1>
        <p>
          {isAuthenticated
            ? `Olá, ${user?.name}!`
            : "Faça login ou se cadastre para acessar sua conta."}
        </p>
      </main>
    </div>
  );
}
