import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { ServiceResponse, Word } from "../types/global";
import { HOST } from "./host";

export default class WordsService {
    public static readonly GET       : string = `${HOST}/api/words`
    public static readonly GET_BY_ID : string = this.GET + '/'
    public static readonly UDPATE    : string = `${HOST}/api/words`
    public static readonly POST      : string = `${HOST}/api/words`
    public static readonly FIND      : string = `${HOST}/api/words/find`

    public static async getAll(page : number = 0, size : number = 10): ServiceResponse<Word[]> {
        return this._get(this.GET, {
            params: {
                'size' : size,
                'page' : page
            }
        }); 
    }

    public static async getById(id : number) : ServiceResponse<Word> { 
        return this._get(this.GET_BY_ID + id);
    }

    public static async update(word : Word): ServiceResponse<Word> {
        return await axios.put(this.UDPATE, word);
    }

    public static async save(word : Word) : ServiceResponse<Word> {
        return await axios.post(this.POST, word);
    }

    public static async find(page? : number, size? : number, title? : string, lang? : string) : ServiceResponse<Word[]> {
        return this._get(this.FIND, {
            params : {
                page : page,
                size : size,
                title : title,
                lang : lang
            }
        });
    }

    private static async _get<T>(url : string, params? : AxiosRequestConfig) : ServiceResponse<T> {
        return await axios.get<T>(url, params);
    }
}
