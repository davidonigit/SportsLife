import { useMutation } from "@tanstack/react-query";
import { login } from "@/api/auth/login";
import { useAuthStore } from "@/stores/auth/auth-store";
import { toast } from "sonner";

export function useLogin() {
  const { setUser, setToken, setAuthenticated } = useAuthStore();

  const mutation = useMutation({
    mutationFn: login,
    onSuccess: (data) => {
      setUser(data.user);
      setToken(data.token);
      setAuthenticated(true);
      localStorage.setItem("token", data.token);
      toast.success("Login realizado com sucesso!");
    },
    onError: () => {
      toast.error("Email ou senha inválidos");
    },
  });

  return mutation;
}
