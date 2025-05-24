"use client";

import { useAuthStore } from "@/stores/auth/auth-store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Home() {
  const { user, isAuthenticated } = useAuthStore();
  const router = useRouter();

  console.log("User:", user);

  useEffect(() => {
    if (isAuthenticated) {
      router.push("/home");
    }
  }, []);

  return (
    <div className="grid grid-rows-[20px_1fr_20px] p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <main className="flex flex-col gap-[32px] row-start-2 items-center">
        <h1>Seja bem-vindo ao Sportslife!</h1>
        <p>Faça login ou se cadastre para acessar sua conta.</p>
      </main>
    </div>
  );
}
