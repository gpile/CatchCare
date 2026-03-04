import { Trap } from "@/types/Trap";
import axiosInstance from "./axios-instance";

export async function getTraps(): Promise<Trap[]> {
    const response = await axiosInstance.get<Trap[]>('/traps');
    return response.data;
}
