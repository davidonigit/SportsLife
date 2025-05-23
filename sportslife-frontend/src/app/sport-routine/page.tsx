"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useAuthStore } from "@/stores/auth/auth-store";
import { Console } from "console";

// Esportes disponíveis
const sportsList = [
  "Futebol",
  "Basquete",
  "Corrida",
  "Natação",
  "Ciclismo",
  "Yoga",
  "Tênis",
  "Vôlei",
  "Musculação",
  "Boxe",
];

// Converter strings do enum para nome do dia
const dayOfWeekEnum: Record<string, string> = {
  SUNDAY: "Domingo",
  MONDAY: "Segunda-feira",
  TUESDAY: "Terça-feira",
  WEDNESDAY: "Quarta-feira",
  THURSDAY: "Quinta-feira",
  FRIDAY: "Sexta-feira",
  SATURDAY: "Sábado",
};

const sortedWeeklyAvailability = [
  "SUNDAY",
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
];

// Tipos para dados de disponibilidade
interface DailyAvailability {
  id: number;
  dayOfWeek: string;
  morningAvailable: boolean;
  afternoonAvailable: boolean;
  eveningAvailable: boolean;
}

interface SportRoutine {
  sportName: string;
  weeklyAvailability: DailyAvailability[];
}

export default function SportRoutinePage() {
  const [selectedSport, setSelectedSport] = useState<string>(sportsList[0]);
  const [sportName, setSportName] = useState<string>("");
  const [weeklyAvailability, setWeeklyAvailability] = useState<
    DailyAvailability[]
  >([]);
  const [statusMessage, setStatusMessage] = useState<string>("");
  const { user, token, isAuthenticated } = useAuthStore();

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetch(
          "http://localhost:8080/api/sport-routine",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        // console.log("Token:", token, "User:", user);
        const data: SportRoutine = await response.json();
        console.log("Dados recebidos:", data);
        setSportName(data.sportName);
        setSelectedSport(data.sportName);
        const sortedAvailability = data.weeklyAvailability.sort((a, b) => {
          const indexA = sortedWeeklyAvailability.indexOf(a.dayOfWeek);
          const indexB = sortedWeeklyAvailability.indexOf(b.dayOfWeek);
          return indexA - indexB;
        });
        setWeeklyAvailability(sortedAvailability);
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    }
    fetchData();
  }, []);

  function toggleAvailability(dayIndex: number, time: keyof DailyAvailability) {
    console.log("Toggling availability for day:", dayIndex, "time:", time);

    setWeeklyAvailability((prev) => {
      const updated = prev.map((item, index) =>
        index === dayIndex ? { ...item, [time]: !item[time] } : item
      );
      return updated;
    });
  }

  async function saveSchedule() {
    console.log("Salvando rotina com os dados:", {
      sport: selectedSport,
      weeklyAvailability,
    });
    try {
      const response = await fetch("http://localhost:8080/api/sport-routine", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ sport: selectedSport, weeklyAvailability }),
      });
      if (response.ok) {
        setStatusMessage("Rotina salva com sucesso!");
      } else {
        setStatusMessage("Erro ao salvar rotina.");
      }
    } catch (error) {
      setStatusMessage("Erro de conexão.");
    }
  }

  return (
    <div className="min-h-screen bg-[var(--gray-bg)] pt-8 p-4 flex flex-col items-center">
      <div className="mb-8">
        <label className="text-xl font-semibold mb-2 block section-header">
          Escolha seu esporte:
        </label>
        <select
          value={selectedSport}
          onChange={(e) => setSelectedSport(e.target.value)}
          className="p-2 border border-gray-300 rounded-lg w-full"
        >
          {sportsList.map((sport) => (
            <option key={sport} value={sport}>
              {sport}
            </option>
          ))}
        </select>
      </div>

      <h2 className="section-header">Sua Rotina</h2>

      <table className="w-full max-w-sm mb-6">
        <thead>
          <tr>
            <th className="p-2"></th>
            {weeklyAvailability.map((item) => (
              <th key={item.id} className="p-2 text-center">
                {dayOfWeekEnum[item.dayOfWeek as keyof typeof dayOfWeekEnum]}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {["morningAvailable", "afternoonAvailable", "eveningAvailable"].map(
            (time, timeIndex) => (
              <tr key={time}>
                <td className="p-2 text-center font-medium">
                  {time === "morningAvailable"
                    ? "Manhã"
                    : time === "afternoonAvailable"
                    ? "Tarde"
                    : "Noite"}
                </td>
                {weeklyAvailability.map((item, index) => (
                  <td key={index} className="p-2 text-center">
                    <button
                      onClick={() =>
                        toggleAvailability(
                          index,
                          time as keyof DailyAvailability
                        )
                      }
                      className={`${
                        item[time as keyof DailyAvailability]
                          ? "bg-green-500"
                          : "bg-red-500"
                      } p-2 rounded`}
                    >
                      {item[time as keyof DailyAvailability]
                        ? "Disponível"
                        : "Ocupado"}
                    </button>
                  </td>
                ))}
              </tr>
            )
          )}
        </tbody>
      </table>

      <button onClick={saveSchedule} className="main-button mb-4">
        Salvar Rotina
      </button>

      {statusMessage && (
        <div className="mt-4 text-green-500 font-medium">{statusMessage}</div>
      )}
    </div>
  );
}
