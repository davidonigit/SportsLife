"use client";

import { useState } from "react";
import Link from "next/link";

export default function RegisterPage() {
  const [form, setForm] = useState({ name: "", email: "", password: "" });
  const [success, setSuccess] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
    setSuccess("");
  }

  async function handleSubmit(e) {
    e.preventDefault();
    // Aqui você pode enviar os dados via API
    setSuccess("Cadastro realizado com sucesso!");
    setForm({ name: "", email: "", password: "" });
  }

  return (
    <div
      className="w-full min-h-screen flex flex-col bg-[var(--gray-bg)]"
      style={{ minHeight: "100vh" }}
    >
      {/* Header */}
      <header className="w-full flex justify-center items-center py-5">
        <h1>SportsLife</h1>
      </header>

      {/* Centralização por flex */}
      <div className="flex-1 flex flex-col items-center justify-center">
        <div className="form-wrapper">
          <div className="form-title">Crie sua conta</div>
          <div className="form-desc">
            Preencha os campos abaixo para se registrar
          </div>
          <form onSubmit={handleSubmit} className="w-full space-y-3">
            <div>
              <label htmlFor="name">Nome</label>
              <input
                id="name"
                type="text"
                name="name"
                value={form.name}
                onChange={handleChange}
                placeholder="Seu nome"
                required
                autoFocus
              />
            </div>
            <div>
              <label htmlFor="email">E-mail</label>
              <input
                id="email"
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                placeholder="Seu e-mail"
                required
              />
            </div>
            <div>
              <label htmlFor="password">Senha</label>
              <input
                id="password"
                type="password"
                name="password"
                value={form.password}
                onChange={handleChange}
                placeholder="Crie uma senha"
                required
              />
            </div>
            <button type="submit" className="main-button">
              Registrar
            </button>
            {success && <div className="form-success">{success}</div>}
          </form>
          <Link href="/login" className="secondary-button">
            Já tem conta? Fazer Login
          </Link>
        </div>
      </div>
    </div>
  );
}
