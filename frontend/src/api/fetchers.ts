import { FetchError, Language, NoPromiseServiceResponse, Word, WordsPagFetchParams } from "../types/global";
import { errorHandler } from "../utils/error";
import handleBackendError from "../utils/handleIfError";
import LangaugeService from "./LanguageService";
import WordsService from "./WordsService";
import { AxiosResponse } from "axios";

export const fetchWords = async (
    params : WordsPagFetchParams,
    eFn    : (error : FetchError) => void,
    okFn   : (response : AxiosResponse<Word[]>) => void
) : Promise<void> => {
    try {
        const r : NoPromiseServiceResponse<Word[]> = await WordsService.getAll(params.page, params.size);

        handleBackendError(r, eFn, okFn);
    } catch (e : unknown) {
        errorHandler(e, eFn); 
    }
}

export const fetchLanguages = async (
    eFn : (error : FetchError) => void,
    okFn : (response : AxiosResponse<Language[]>) => void
) : Promise<void> => {
    try {
        const r : NoPromiseServiceResponse<Language[]> = await LangaugeService.getAll();

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}

export const fetchWordById = async (
    id : number,
    eFn : (error : FetchError) => void,
    okFn : (res : AxiosResponse<Word>) => void
) : Promise<void> => {
    try {
        const r : NoPromiseServiceResponse<Word> = await WordsService.getById(Number(id));

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}

export const updateWord = async (
    word : Word,
    eFn : (error : FetchError) => void,
    okFn : (res : AxiosResponse<Word>) => void
) : Promise<void> => {
    try {
        const r : NoPromiseServiceResponse<Word> = await WordsService.update(word);

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}

export const saveWord = async (
    word : Word,
    eFn : (error : FetchError) => void,
    okFn : (res : AxiosResponse<Word>) => void
) : Promise<void> => {
    try {
        const r : NoPromiseServiceResponse<Word> = await WordsService.save(word);

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}

export const find = async (
    eFn : (err : FetchError) => void,
    okFn : (res : AxiosResponse<Word[]>) => void,
    page? : number, 
    size? : number, 
    title? : string, 
    lang? : string,
) : Promise<void> => {
    try {
        const r = await WordsService.find(page, size, title, lang);

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}

export const deleteWordById = async (
    id : number,
    eFn : (err : FetchError) => void,
    okFn : (res : AxiosResponse<void>) => void
) : Promise<void> => {
    try {
        const r = await WordsService.deleteById(id);

        handleBackendError(r, eFn, okFn);
    } catch (e) {
        errorHandler(e, eFn);
    }
}