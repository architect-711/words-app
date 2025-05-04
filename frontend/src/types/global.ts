import { AxiosResponse } from "axios"

export interface Word {
    id: number | null,
    title: string,
    translation: string,
    description: string,
    language: string,
    useCases: string[],
    localDateTime : string
}

export interface Language {
    id : number,
    title : string
}

export type ServiceResponse<T> = Promise<AxiosResponse<T> | BackendErrorResponse>;
export type NoPromiseServiceResponse<T> = AxiosResponse<T> | BackendErrorResponse;

export interface BackendErrorResponse {
    localDateTime : string,
    message : string,
    description : string
}

export interface FetchError {
    message : string,
    description : string,
    name? : string,
    url? : string,
    localDateTime? : string
}

export interface WordsPagFetchParams {
    page? : number,
    size? : number,
    lang? : string,
    title? : string
}

export interface WordForm extends Word {}