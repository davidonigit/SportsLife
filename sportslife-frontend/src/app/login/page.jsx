"use client";

import { useState } from "react";
import Link from "next/link";

export default function LoginPage() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
    setSuccess("");
    setError("");
  }

  async function handleSubmit(e) {
    e.preventDefault();
    // Aqui você pode adicionar sua lógica de autenticação
    if (form.email && form.password) {
      setSuccess("Login efetuado com sucesso!");
      setForm({ email: "", password: "" });
      setError("");
    } else {
      setError("Preencha todos os campos.");
    }
  }

  return (
    <div className="w-full min-h-screen flex flex-col bg-[var(--gray-bg)]">
      {/* Header */}
      <header className="w-full flex justify-center items-center py-5">
        <h1>SportsLife</h1>
      </header>
      {/* Centralização por flex */}
      <div className="flex-1 flex flex-col items-center justify-center">
        <div className="form-wrapper">
          <div className="form-title">Entrar</div>
          <div className="form-desc">Acesse sua conta</div>
          <form onSubmit={handleSubmit} className="w-full space-y-3">
            <div>
              <label htmlFor="email">E-mail</label>
              <input
                id="email"
                type="email"
                name="email"
                autoComplete="username"
                value={form.email}
                onChange={handleChange}
                placeholder="Seu e-mail"
                required
                autoFocus
              />
            </div>
            <div>
              <label htmlFor="password">Senha</label>
              <input
                id="password"
                type="password"
                name="password"
                autoComplete="current-password"
                value={form.password}
                onChange={handleChange}
                placeholder="Sua senha"
                required
              />
            </div>
            <button type="submit" className="main-button">
              Entrar
            </button>
            {success && <div className="form-success">{success}</div>}
            {error && (
              <div className="form-success" style={{ color: "salmon" }}>
                {error}
              </div>
            )}
          </form>
          <Link href="/register" className="secondary-button">
            Ainda não tem conta? Criar agora
          </Link>
        </div>
      </div>
    </div>
  );
}
