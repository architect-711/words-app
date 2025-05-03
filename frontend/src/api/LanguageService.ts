import axios, { AxiosResponse } from "axios";
import { Language, ServiceResponse } from "../types/global";
import { HOST } from "./host";

export default class LangaugeService {
    private static readonly GET_ALL : string = `${HOST}/api/languages`;

    public static async getAll() : ServiceResponse<Language[]> {
        return await axios.get(this.GET_ALL);
    }
}