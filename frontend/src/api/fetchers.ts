import {
    FetchError,
    Language,
    NoPromiseServiceResponse,
    ServiceResponse,
    Word,
    WordsPagFetchParams
} from "../types/global";
import { errorHandler } from "../utils/error";
import handleBackendError from "../utils/handleIfError";
import LangaugeService from "./LanguageService";
import WordsService from "./WordsService";
import { AxiosResponse } from "axios";
import {exec} from "node:child_process";

interface Funs<T> {
    eFn   : (e : FetchError) => void,
    okFn  : (r : AxiosResponse<T>) => void
}

export function buildFuns<T>(eFn : (e : FetchError) => void, okFn : (r : AxiosResponse<T>) => void) : Funs<T> {
    return {
        eFn : eFn,
        okFn : okFn
    }
}

export async function apiRequestWrapper<T>(
    serviceCall : () => ServiceResponse<T>,
    {okFn, eFn} : Funs<T>
) : Promise<void> {
    try {
        const r : NoPromiseServiceResponse<T> = await serviceCall();

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}

export function fetchLanguages( funs : Funs<Language[]>) : void {
    apiRequestWrapper(() => LangaugeService.getAll(), funs);
}

export function fetchWordById(id : number, funs : Funs<Word>) : void {
    apiRequestWrapper(() => WordsService.getById(id), funs);
}

export function updateWord(word : Word, funs : Funs<Word>) : void {
    apiRequestWrapper(() => WordsService.update(word), funs);
}

export function saveWord(word : Word, funs : Funs<Word>) : void{
    apiRequestWrapper(() => WordsService.save(word), funs);
}

export function find(
    funs : Funs<Word[]>,
    page? : number,
    size? : number, 
    title? : string, 
    lang? : string,
) : void {
    apiRequestWrapper(() => WordsService.find(page, size, title, lang), funs);
}

export function deleteWordById(id : number, funs : Funs<void>) : void {
    apiRequestWrapper(() => WordsService.deleteById(id), funs);
}